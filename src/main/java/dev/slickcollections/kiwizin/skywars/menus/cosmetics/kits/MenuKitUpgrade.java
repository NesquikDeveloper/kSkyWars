package dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits;

import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.container.CosmeticsContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.level.MenuBuyCashKitLevel;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.level.MenuBuyKitLevel;
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

public class MenuKitUpgrade<T extends Kit> extends PlayerMenu {
  
  private static final int[] SLOTS = {11, 12, 13, 14, 15, 16, 20, 21, 22, 23, 24, 25};
  private String name;
  private T cosmetic;
  private Map<ItemStack, Integer> intLevels = new HashMap<>();
  private Map<ItemStack, KitLevel> kitLevels = new HashMap<>();
  public MenuKitUpgrade(Profile profile, String name, T cosmetic) {
    super(profile.getPlayer(), "Kit " + cosmetic.getName(), 5);
    this.name = name;
    this.cosmetic = cosmetic;
    
    this.setItem(10, cosmetic.getIcon(profile, false, false));
    
    double coins = profile.getCoins("kCoreSkyWars");
    long cash = profile.getStats("kCoreProfile", "cash");
    long currentLevel = profile.getAbstractContainer("kCoreSkyWars", "cosmetics", CosmeticsContainer.class).getLevel(cosmetic);
    int slot = 0;
    for (KitLevel kitLevel : cosmetic.getLevels()) {
      int level = slot + 1;
      
      String id = level <= currentLevel ? "13" : (level - 1) == currentLevel ? "4" : "14";
      String color = id.equals("13") ? "&a" : id.equals("4") ? "&e" : "&c";
      String desc = id.equals("13") ?
          "\n \n&aVocê já possui este upgrade." :
          id.equals("4") ?
              Language.cosmetics$kit$icon$buy_desc$start.replace("{rarity}", cosmetic.getRarity().getName()).replace("{cash}", StringUtils.formatNumber(kitLevel.getCash()))
                  .replace("{coins}", StringUtils.formatNumber(kitLevel.getCoins())).replace("{buy_desc_status}",
                  ((coins >= kitLevel.getCoins() || (CashManager.CASH && cash >= kitLevel.getCash())) ?
                      Language.cosmetics$icon$buy_desc$click_to_buy :
                      Language.cosmetics$icon$buy_desc$enough)) :
              "\n \n&cVocê não pode comprar este upgrade.";
      ItemStack item = BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:" + id + " : 1 : nome>" + color + kitLevel.getName() + " : desc>" + kitLevel.getDesc() + desc);
      this.setItem(SLOTS[slot++], item);
      if ((level - 1) == currentLevel) {
        this.intLevels.put(item, level);
        this.kitLevels.put(item, kitLevel);
      }
    }
    
    this.setItem(39, BukkitUtils.deserializeItemStack(
        "ANVIL : 1 : nome>&aCustomizar Kit : desc>&7Customize como você irá receber\n&7os itens do kit no inventário.\n \n&eClique para customizar!"));
    this.setItem(40, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para Kits " + this.name + "."));
    
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
            if (evt.getSlot() == 39) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuKitConfig<>(profile, this.name, this.cosmetic);
            } else if (evt.getSlot() == 40) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuKits<>(profile, this.name, this.cosmetic.getClass());
            } else {
              KitLevel kitLevel = this.kitLevels.get(item);
              if (kitLevel != null) {
                long level = this.intLevels.get(item);
                if (profile.getCoins("kCoreSkyWars") < kitLevel.getCoins() && (CashManager.CASH && profile.getStats("kCoreProfile", "cash") < kitLevel.getCash())) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  return;
                }
                
                if (!CashManager.CASH || cosmetic.getCash() == 0) {
                  new MenuBuyKitLevel<>(profile, this.name, cosmetic, kitLevel, level);
                } else {
                  new MenuBuyCashKitLevel<>(profile, this.name, cosmetic, kitLevel, level);
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
    this.intLevels.clear();
    this.intLevels = null;
    this.kitLevels.clear();
    this.kitLevels = null;
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
