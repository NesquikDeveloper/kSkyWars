package dev.slickcollections.kiwizin.skywars.menus.cosmetics;

import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
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

public class MenuBuyCosmetic<T extends Cosmetic> extends PlayerMenu {
  
  private String name;
  private T cosmetic;
  private Class<? extends Cosmetic> cosmeticClass;
  public MenuBuyCosmetic(Profile profile, String name, T cosmetic, Class<? extends Cosmetic> cosmeticClass) {
    super(profile.getPlayer(), "Confirmar compra", 3);
    this.name = name;
    this.cosmetic = cosmetic;
    this.cosmeticClass = cosmeticClass;
    
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "STAINED_GLASS_PANE:13 : 1 : nome>&aConfirmar : desc>&7Comprar \"" + cosmetic.getName() + "\"\n&7por &6" + StringUtils.formatNumber(cosmetic.getCoins()) + " Coins&7."));
    
    this.setItem(15, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:14 : 1 : nome>&cCancelar : desc>&7Voltar para " + name + "."));
    
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
              if (profile.getCoins("kCoreSkyWars") < this.cosmetic.getCoins()) {
                EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                new MenuCosmetics<>(profile, this.name, this.cosmeticClass);
                return;
              }
              
              EnumSound.LEVEL_UP.play(this.player, 0.5F, 2.0F);
              profile.removeCoins("kCoreSkyWars", this.cosmetic.getCoins());
              this.cosmetic.give(profile);
              this.player.sendMessage("§aVocê comprou '" + this.cosmetic.getName() + "'");
              new MenuCosmetics<>(profile, this.name, this.cosmeticClass);
            } else if (evt.getSlot() == 15) {
              EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              new MenuCosmetics<>(profile, this.name, this.cosmeticClass);
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
