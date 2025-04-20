package dev.slickcollections.kiwizin.skywars.menus;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.libraries.menu.PagedPlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Promotion;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.menus.MenuShop;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.MenuBuyCashKit;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.MenuBuyKit;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.MenuKitUpgrade;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.MenuBuyCashPerk;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.MenuBuyPerk;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.MenuPerkUpgrade;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MenuPromotionNPC extends PagedPlayerMenu {
  
  private String mode;
  private Map<ItemStack, Cosmetic> cosmetics = new HashMap<>();
  
  public MenuPromotionNPC(Profile profile) {
    super(profile.getPlayer(), "Itens em promoção", (int) ((Promotion.size() / 7) + 4));
    this.mode = name;
    this.previousPage = (this.rows * 9) - 9;
    this.nextPage = (this.rows * 9) - 1;
    this.onlySlots(10, 11, 12, 13, 14, 15, 16, 19, 20, 21, 22, 23, 24, 25, 28, 29, 30, 31, 32, 33, 34);
    
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cFechar"), (this.rows * 9) - 5);
    
    List<ItemStack> items = new ArrayList<>();
    List<Kit> cosmetics = new ArrayList<>();
    cosmetics.addAll(Promotion.kitsPromotions.keySet());
    cosmetics.addAll(Promotion.kitsPromotionsCash.keySet());
    List<Perk> cosmetics2 = new ArrayList<>();
    cosmetics2.addAll(Promotion.perkPromotions.keySet());
    cosmetics2.addAll(Promotion.perkPromotionsCash.keySet());
    for (Kit cosmetic : cosmetics) {
      if (this.cosmetics.containsValue(cosmetic)) {
        continue;
      }
      ItemStack icon = cosmetic.getIcon(profile);
      items.add(icon);
      this.cosmetics.put(icon, cosmetic);
    }
    for (Perk cosmetic : cosmetics2) {
      if (this.cosmetics.containsValue(cosmetic)) {
        continue;
      }
      ItemStack icon = cosmetic.getIcon(profile, true, false);
      items.add(icon);
      this.cosmetics.put(icon, cosmetic);
    }
    
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
            } else if (evt.getSlot() == (this.rows * 9) - 5) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              this.player.closeInventory();
            } else {
              Cosmetic cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                if (!cosmetic.has(profile)) {
                  if (!cosmetic.canBuy(this.player) || (profile.getCoins("kCoreSkyWars") < cosmetic.getCoins() && (CashManager.CASH && profile
                      .getStats("kCoreProfile", "cash") < cosmetic.getCash()))) {
                    EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                    return;
                  }
  
                  EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                  if (cosmetic instanceof Kit) {
                    if (!CashManager.CASH && cosmetic.getCash() == 0) {
                      new MenuBuyKit<>(profile, "Solo e Dupla", (Kit) cosmetic);
                    } else {
                      new MenuBuyCashKit<>(profile, "Solo e Dupla", (Kit) cosmetic);
                    }
                  } else if (cosmetic instanceof Perk) {
                    if (!CashManager.CASH && cosmetic.getCash() == 0) {
                      new MenuBuyPerk<>(profile, "Solo e Dupla", (Perk) cosmetic, Perk.class);
                    } else {
                      new MenuBuyCashPerk<>(profile, "Solo e Dupla", (Perk) cosmetic, Perk.class);
                    }
                  }
                  return;
                }
  
                if (!cosmetic.canBuy(this.player)) {
                  EnumSound.ENDERMAN_TELEPORT.play(this.player, 0.5F, 1.0F);
                  this.player.sendMessage("§cVocê não possui permissão suficiente para continuar.");
                  return;
                }
  
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                if (cosmetic instanceof Kit) {
                  new MenuKitUpgrade<>(profile, this.mode, (Kit) cosmetic);
                } else if (cosmetic instanceof Perk) {
                  new MenuPerkUpgrade<>(profile, this.mode, (Perk) cosmetic, Perk.class);
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
    this.mode = null;
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
