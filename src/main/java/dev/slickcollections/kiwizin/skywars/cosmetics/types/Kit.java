package dev.slickcollections.kiwizin.skywars.cosmetics.types;


import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.role.Role;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.container.CosmeticsContainer;
import dev.slickcollections.kiwizin.skywars.container.KitConfigContainer;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Promotion;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitConfig;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.kits.NormalKit;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumRarity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class Kit extends Cosmetic {
  
  private final String name;
  private final List<Integer> slots;
  private final String icon;
  private final List<KitLevel> levels;
  
  public Kit(long id, EnumRarity rarity, String permission, String name, List<Integer> slots, String icon, List<KitLevel> levels) {
    super(id, CosmeticType.KIT, 0.0D, permission);
    this.name = name;
    this.slots = slots;
    this.icon = icon;
    this.levels = levels;
    this.rarity = rarity;
  }
  
  public static void setupKits() {
    NormalKit.setupNormalKits();
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @Override
  public double getCoins() {
    return Promotion.applyPromotion(this.getFirstLevel().getCoins(), this);
  }
  
  @Override
  public long getCash() {
    return Long.parseLong(String.valueOf(Promotion.applyCashPromotion(this.getFirstLevel().getCash(), this)).split("\\.")[0].replace("D", ""));
  }
  
  public void apply(Profile profile) {
    Player player = profile.getPlayer();
    KitConfig config = profile.getAbstractContainer("kCoreSkyWars", "kitconfig", KitConfigContainer.class).getOrLoadConfig(this);
    int index = 1;
    for (ItemStack item : this.getCurrentLevel(profile).getItems()) {
      int slot = config.getSlot(index);
      if (!config.isAutoEquipArmor() || !KitConfig.isArmor(item)) {
        if (slot == -1) {
          player.getInventory().addItem(item);
        } else {
          player.getInventory().setItem(slot, item);
        }
      } else {
        if (item.getType().name().contains("_HELMET")) {
          player.getInventory().setHelmet(item);
        } else if (item.getType().name().contains("_CHESTPLATE")) {
          player.getInventory().setChestplate(item);
        } else if (item.getType().name().contains("_LEGGINGS")) {
          player.getInventory().setLeggings(item);
        } else if (item.getType().name().contains("_BOOTS")) {
          player.getInventory().setBoots(item);
        }
      }
      
      index++;
    }
  }
  
  public List<Integer> getSlots() {
    return this.slots;
  }
  
  public KitLevel getFirstLevel() {
    return this.levels.get(0);
  }
  
  public KitLevel getCurrentLevel(Profile profile) {
    return this.levels.get((int) (profile.getAbstractContainer("kCoreSkyWars", "cosmetics", CosmeticsContainer.class).getLevel(this) - 1));
  }
  
  public List<KitLevel> getLevels() {
    return this.levels;
  }
  
  @Override
  public ItemStack getIcon(Profile profile) {
    return this.getIcon(profile, false);
  }
  
  public ItemStack getIcon(Profile profile, boolean select) {
    return this.getIcon(profile, true, select);
  }
  
  public ItemStack getIcon(Profile profile, boolean useDesc, boolean select) {
    double coins = profile.getCoins("kCoreSkyWars");
    long cash = profile.getStats("kCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelected(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }
    
    Role role = Role.getRoleByPermission(this.getPermission());
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = "";
    if (useDesc) {
      desc = (has && canBuy ?
          (select ? "\n \n" + (isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) : Language.cosmetics$kit$icon$has_desc$start) :
          select ?
              "" :
              canBuy ?
                  Language.cosmetics$kit$icon$buy_desc$start.replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ?
                      Language.cosmetics$icon$buy_desc$click_to_buy :
                      Language.cosmetics$icon$buy_desc$enough) :
                  Language.cosmetics$kit$icon$perm_desc$start
                      .replace("{perm_desc_status}", role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName())))
          .replaceFirst("Coins ", (Promotion.hasPromotion(this) && !has ? "" : "Coins "))
          .replaceFirst("Cash", (Promotion.hasPromotionCash(this) && !has ? "" : "Cash"))
          .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins()) + (Promotion.hasPromotion(this) && !has ? " §6Coins §a§l" + String.valueOf(Promotion.getPromotion(this)).split("\\.")[0] + "% OFF§6" : ""))
          .replace("{cash}", StringUtils.formatNumber(this.getCash()) + (Promotion.hasPromotionCash(this) && !has ? " §bCash §a§l" + String.valueOf(Promotion.getPromotionCash(this)).split("\\.")[0] + "% OFF§b" : ""));
    }
    String items = has ? this.getCurrentLevel(profile).getDesc() : this.getFirstLevel().getDesc();
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon.replace("{items}", items) + desc + " : nome>" + ((Promotion.hasPromotion(this) || Promotion.hasPromotionCash(this)) && !has && useDesc ? "§6§lPROMOÇÃO " : "") + color + this.name);
    if (select && isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }
    
    return item;
  }
}
