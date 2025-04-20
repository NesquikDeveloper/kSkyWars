package dev.slickcollections.kiwizin.skywars.menus;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.libraries.menu.UpdatablePlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.game.object.SkyWarsLeague;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
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

public class MenuRewards extends UpdatablePlayerMenu {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("leveling");
  
  private Profile profile;
  private Map<ItemStack, SkyWarsLeague.LeagueDelivery> deliveries;
  
  public MenuRewards(Profile profile) {
    super(profile.getPlayer(), CONFIG.getString("title"), CONFIG.getInt("rows"));
    this.profile = profile;
    this.deliveries = new HashMap<>();
    
    this.setItem((CONFIG.getInt("rows") * 9) - 5, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para a Loja."));
    this.update();
    this.register(Core.getInstance(), 20);
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getInventory())) {
          ItemStack item = evt.getCurrentItem();
          
          if (item != null && item.getType() != Material.AIR) {
            SkyWarsLeague.LeagueDelivery delivery = this.deliveries.get(item);
            if (delivery != null) {
              SkyWarsLeague.LeagueContainer container =
                  profile.getAbstractContainer("kCoreSkyWars", "leveling", SkyWarsLeague.LeagueContainer.class);
              if (container.hasRewarded(delivery.key) || !delivery.hasPermission(profile)) {
                EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
              } else {
                EnumSound.LEVEL_UP.play(this.player, 1.0F, 1.0F);
                container.addReward(delivery.key);
                delivery.listRewards().forEach(reward -> reward.dispatch(this.profile));
                this.player.sendMessage("§aVocê coletou sua recompensa!");
                this.player.closeInventory();
              }
            } else if (evt.getSlot() == (CONFIG.getInt("rows") * 9) - 5) {
              EnumSound.CLICK.play(player, 0.5F, 2.0F);
              new MenuShop(profile);
            }
          }
        }
      }
    }
  }
  
  @Override
  public void update() {
    this.deliveries.clear();
    for (SkyWarsLeague.LeagueDelivery delivery : SkyWarsLeague.LeagueDelivery.listDeliveries()) {
      ItemStack item = delivery.getIcon(this.profile);
      this.setItem(delivery.getSlot(), item);
      this.deliveries.put(item, delivery);
    }
    
    this.player.updateInventory();
  }
  
  public void cancel() {
    super.cancel();
    HandlerList.unregisterAll(this);
    this.profile = null;
    this.deliveries.clear();
    this.deliveries = null;
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