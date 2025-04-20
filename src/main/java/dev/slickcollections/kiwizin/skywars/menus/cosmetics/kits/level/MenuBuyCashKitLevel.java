package dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.level;

import dev.slickcollections.kiwizin.cash.CashException;
import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.container.CosmeticsContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.MenuKitUpgrade;
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

public class MenuBuyCashKitLevel<T extends Kit> extends PlayerMenu {
  
  private String name;
  private T cosmetic;
  private KitLevel kitLevel;
  private long level;
  public MenuBuyCashKitLevel(Profile profile, String name, T cosmetic, KitLevel kitLevel, long level) {
    super(profile.getPlayer(), "Confirmar compra", 3);
    this.name = name;
    this.cosmetic = cosmetic;
    this.kitLevel = kitLevel;
    this.level = level;
    
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "GOLD_INGOT : 1 : nome>&aConfirmar : desc>&7Comprar \"" + kitLevel.getName() + "\"\n&7por &6" + StringUtils.formatNumber(kitLevel.getCoins()) + " Coins&7."));
    
    this.setItem(13, BukkitUtils.deserializeItemStack(
        "DIAMOND : 1 : nome>&aConfirmar : desc>&7Comprar \"" + kitLevel.getName() + "\"\n&7por &b" + StringUtils.formatNumber(kitLevel.getCash()) + " Cash&7."));
    
    this.setItem(15, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:14 : 1 : nome>&cCancelar : desc>&7Voltar para o Kit " + cosmetic.getName() + "."));
    
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
            if (evt.getSlot() == 11) {
              if (profile.getCoins("kCoreSkyWars") < this.kitLevel.getCoins()) {
                EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                this.player.sendMessage("§cVocê não possui Coins suficientes para completar esta transação.");
                return;
              }
              
              EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
              profile.removeCoins("kCoreSkyWars", this.kitLevel.getCoins());
              profile.getAbstractContainer("kCoreSkyWars", "cosmetics", CosmeticsContainer.class).setLevel(this.cosmetic, this.level);
              this.player.sendMessage("§aVocê comprou '" + this.kitLevel.getName() + "'");
              new MenuKitUpgrade<>(profile, this.name, this.cosmetic);
            } else if (evt.getSlot() == 13) {
              if (profile.getStats("kCoreProfile", "cash") < this.kitLevel.getCash()) {
                EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                this.player.sendMessage("§cVocê não possui Cash suficiente para completar esta transação.");
                return;
              }
              
              try {
                CashManager.removeCash(profile, this.kitLevel.getCash());
                profile.getAbstractContainer("kCoreSkyWars", "cosmetics", CosmeticsContainer.class).setLevel(this.cosmetic, this.level);
                this.player.sendMessage("§aVocê comprou '" + this.kitLevel.getName() + "'");
                EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
              } catch (CashException ignore) {
              }
              new MenuKitUpgrade<>(profile, this.name, this.cosmetic);
            } else if (evt.getSlot() == 15) {
              EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              new MenuKitUpgrade<>(profile, this.name, this.cosmetic);
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
    this.kitLevel = null;
    this.level = 0;
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
