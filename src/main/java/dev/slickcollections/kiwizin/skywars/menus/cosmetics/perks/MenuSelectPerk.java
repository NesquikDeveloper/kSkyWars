package dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.libraries.menu.PagedPlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.menus.MenuShop;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuSelectPerk<T extends Perk> extends PagedPlayerMenu {
  
  private Class<T> cosmeticClass;
  private Map<ItemStack, T> cosmetics = new HashMap<>();
  public MenuSelectPerk(Profile profile, Class<T> cosmeticClass) {
    super(profile.getPlayer(), "Habilidades", 6);
    this.cosmeticClass = cosmeticClass;
    this.previousPage = 45;
    this.nextPage = 53;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34, 37, 38, 39, 40, 41, 42, 43);
    
    List<ItemStack> items = new ArrayList<>(), sub = new ArrayList<>();
    List<T> cosmetics = Cosmetic.listByType(cosmeticClass);
    for (T cosmetic : cosmetics) {
      ItemStack icon = cosmetic.getIcon(profile, true);
      if (cosmetic.has(profile)) {
        items.add(icon);
      } else {
        icon.setType(Material.STAINED_GLASS_PANE);
        ItemMeta meta = icon.getItemMeta();
        meta.getEnchants().forEach((key, value) -> meta.removeEnchant(key));
        icon.setDurability((short) 14);
        sub.add(icon);
      }
      this.cosmetics.put(icon, cosmetic);
    }
    
    items.addAll(sub);
    sub.clear();
    this.setItems(items);
    cosmetics.clear();
    items.clear();
    
    this.register(Core.getInstance());
    this.open();
  }
  
  @EventHandler
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getInventory().equals(this.getCurrentInventory())) {
      evt.setCancelled(true);
      
      if (evt.getWhoClicked().equals(this.player)) {
        Profile profile = Profile.getProfile(this.player.getName());
        if (profile == null) {
          this.player.closeInventory();
          return;
        }
        
        if (evt.getClickedInventory() != null && evt.getClickedInventory().equals(this.getCurrentInventory())) {
          ItemStack item = evt.getCurrentItem();
          
          if (item != null && item.getType() != Material.AIR) {
            if (evt.getSlot() == this.previousPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.openPrevious();
            } else if (evt.getSlot() == this.nextPage) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.openNext();
            } else if (evt.getSlot() == 49) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuShop(profile);
            } else {
              T cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                if (!cosmetic.has(profile)) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  return;
                }
                
                if (!cosmetic.canBuy(this.player)) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                  return;
                }
                
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
                
                new MenuSelectPerk<>(profile, this.cosmeticClass);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
    this.cosmeticClass = null;
    this.cosmetics.clear();
    this.cosmetics = null;
  }
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.cancel();
    }
  }
  
  @EventHandler
  public void onInventoryClose(InventoryCloseEvent evt) {
    if (evt.getPlayer().equals(this.player) && evt.getInventory().equals(this.getCurrentInventory())) {
      this.cancel();
    }
  }
}
