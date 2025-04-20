package dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits;

import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.container.KitConfigContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitConfig;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

public class MenuKitConfigSlot<T extends Kit> extends PlayerMenu {
  
  private String name;
  private T cosmetic;
  private KitConfig config;
  private int index;
  public MenuKitConfigSlot(Profile profile, String name, T cosmetic, int index) {
    super(profile.getPlayer(), "Escolher slot", 6);
    this.name = name;
    this.cosmetic = cosmetic;
    this.index = index;
    this.config = profile.getAbstractContainer("kCoreSkyWars", "kitconfig", KitConfigContainer.class).getOrLoadConfig(cosmetic);
    
    int id = 1;
    for (ItemStack item : cosmetic.getCurrentLevel(profile).getItems()) {
      int slot = config.getSlot(id);
      if (!this.config.isAutoEquipArmor() || !KitConfig.isArmor(item)) {
        this.setItem(slot == -1 ? (id - 1) : KitConfig.convertConfigSlot(slot), BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:14 : 1 : nome>&cSlot em uso!"));
      }
      id++;
    }
    
    for (int glass = 0; glass < 45; glass++) {
      if (glass >= 27 && glass < 36) {
        this.setItem(glass, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:14 : 1 : nome>&8↑ Inventário : desc>&8↓ Hotbar"));
        continue;
      }
      
      ItemStack current = getItem(glass);
      if (current != null && current.getType() != Material.AIR) {
        continue;
      }
      
      this.setItem(glass, BukkitUtils.deserializeItemStack("STAINED_GLASS_PANE:5 : 1 : nome>&aSelecionar slot"));
    }
    
    this.setItem(49, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para Customizar o Kit " + cosmetic.getName() + "."));
    
    this.register(Main.getInstance());
    this.open();
  }
  
  @EventHandler(priority = EventPriority.LOW)
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
            if (evt.getSlot() == 49) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuKitConfig<>(profile, this.name, this.cosmetic);
            } else if (item.getType() == Material.STAINED_GLASS_PANE) {
              if (item.getDurability() != 5) {
                EnumSound.VILLAGER_NO.play(this.player, 1.0F, 1.0F);
                return;
              }
              
              EnumSound.NOTE_PLING.play(this.player, 0.5F, 1.0F);
              this.config.setSlot(this.index, KitConfig.convertInventorySlot(evt.getSlot()));
              this.save(profile);
              new MenuKitConfig<>(profile, this.name, this.cosmetic);
            }
          }
        }
      }
    }
  }
  
  private void save(Profile profile) {
    profile.getAbstractContainer("kCoreSkyWars", "kitconfig", KitConfigContainer.class).saveConfig(this.cosmetic, this.config);
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.name = null;
    this.cosmetic = null;
    this.config = null;
    this.index = 0;
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
