package dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks;


import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.container.CosmeticsContainer;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.perk.PerkLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.level.MenuBuyCashPerkLevel;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.level.MenuBuyPerkLevel;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.Map;

public class MenuPerkUpgrade<T extends Perk> extends PlayerMenu {
  
  private static final int[] SLOTS = {11, 12, 13, 14, 15, 16, 20, 21, 22, 23, 24, 25};
  private String name;
  private T cosmetic;
  private Class<T> cosmeticClass;
  private Map<ItemStack, Integer> intLevels = new HashMap<>();
  private Map<ItemStack, PerkLevel> perkLevels = new HashMap<>();
  public MenuPerkUpgrade(Profile profile, String name, T cosmetic, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Habilidade " + cosmetic.getName(), 5);
    this.name = name;
    this.cosmetic = cosmetic;
    this.cosmeticClass = cosmeticClass;
    
    this.setItem(10, cosmetic.getIcon(profile, false, false));
    
    double coins = profile.getCoins("kCoreSkyWars");
    long cash = profile.getStats("kCoreProfile", "cash");
    long currentLevel = profile.getAbstractContainer("kCoreSkyWars", "cosmetics", CosmeticsContainer.class).getLevel(cosmetic);
    int slot = 0;
    for (PerkLevel perkLevel : cosmetic.getLevels()) {
      int level = slot + 1;
      
      String id = level <= currentLevel ? "13" : (level - 1) == currentLevel ? "4" : "14";
      String color = id.equals("13") ? "&a" : id.equals("4") ? "&e" : "&c";
      String levelName = " " + (level > 3 ? level == 4 ? "IV" : "V" : StringUtils.repeat("I", level));
      String desc = id.equals("13") ?
          "\n \n&aVocê já possui este upgrade." :
          id.equals("4") ?
              Language.cosmetics$perk$icon$buy_desc$start.replace("{rarity}", cosmetic.getRarity().getName()).replace("{cash}", StringUtils.formatNumber(perkLevel.getCash()))
                  .replace("{coins}", StringUtils.formatNumber(perkLevel.getCoins())).replace("{buy_desc_status}",
                  ((coins >= perkLevel.getCoins() || (CashManager.CASH && cash >= perkLevel.getCash())) ?
                      Language.cosmetics$icon$buy_desc$click_to_buy :
                      Language.cosmetics$icon$buy_desc$enough)) :
              "\n \n&cVocê não pode comprar este upgrade.";
      ItemStack item =
          BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:" + id + " : 1 : nome>" + color + cosmetic.getName() + levelName + " : desc>&8" + perkLevel.getDescription() + desc);
      this.setItem(SLOTS[slot++], item);
      if ((level - 1) == currentLevel) {
        this.intLevels.put(item, level);
        this.perkLevels.put(item, perkLevel);
      }
    }
    
    this.setItem(40, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para Habilidades " + this.name + "."));
    this.setItem(41, BukkitUtils.deserializeItemStack("INK_SACK:" +
        (!cosmetic.isSelectedPerk(profile) ? "8" : "10") + " : 1 : nome>" + (cosmetic.isSelectedPerk(profile) ?
        "&aSelecionado : desc>&eClique para deselecionar!" : "&eClique para selecionar!")));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();
          
          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == 40) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuPerks<>(profile, this.name, this.cosmeticClass);
            } else if (evt.getSlot() == 41) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              if (cosmetic.isSelectedPerk(profile)) {
                profile.getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).
                    setSelected(cosmetic.getType(), 0,
                        profile.getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).
                            getIndex(cosmetic));
              } else {
                profile.getAbstractContainer("kCoreSkyWars", "selected",
                    SelectedContainer.class).setSelected(cosmetic.getType(),
                    cosmetic.getId(), cosmetic.getAvailableSlot(profile));
              }
              new MenuPerkUpgrade<>(profile, name, this.cosmetic, cosmeticClass);
            } else {
              PerkLevel perkLevel = this.perkLevels.get(item);
              if (perkLevel != null) {
                long level = this.intLevels.get(item);
                if (profile.getCoins("kCoreSkyWars") < perkLevel.getCoins() && (CashManager.CASH && profile.getStats("kCoreProfile", "cash") < perkLevel.getCash())) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  return;
                }
                
                if (!CashManager.CASH || cosmetic.getCash() == 0) {
                  new MenuBuyPerkLevel<>(profile, this.name, this.cosmetic, perkLevel, level, this.cosmeticClass);
                } else {
                  new MenuBuyCashPerkLevel<>(profile, this.name, this.cosmetic, perkLevel, level, this.cosmeticClass);
                }
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.name = null;
    this.cosmetic = null;
    this.cosmeticClass = null;
    this.intLevels.clear();
    this.intLevels = null;
    this.perkLevels.clear();
    this.perkLevels = null;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getInventory())) {
      this.cancel();
    }
  }
}
