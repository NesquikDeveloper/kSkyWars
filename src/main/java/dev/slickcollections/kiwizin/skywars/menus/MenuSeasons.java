package dev.slickcollections.kiwizin.skywars.menus;

import com.google.common.collect.ImmutableSet;
import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.libraries.menu.PagedPlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.role.Role;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractPreview;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Seasons;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.preview.CagePreview;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.preview.KillEffectPreview;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.preview.ProjectileEffectPreview;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.*;
import dev.slickcollections.kiwizin.skywars.game.enums.SkyWarsMode;
import dev.slickcollections.kiwizin.skywars.game.object.SkyWarsLeague;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.MenuBuyCashCosmetic;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.MenuBuyCosmetic;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.animations.MenuAnimations;
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

import java.text.SimpleDateFormat;
import java.util.*;

public class MenuSeasons extends PagedPlayerMenu {
  
  private static final SimpleDateFormat SDF = new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"));
  private Map<ItemStack, Seasons> cosmetics = new HashMap<>();
  
  public MenuSeasons(Profile profile) {
    super(profile.getPlayer(), "Sky Wars Ranked - Temporadas", 4);
    this.previousPage = 30;
    this.nextPage = 32;
    this.onlySlots(10, 12, 14, 16);
    
    this.removeSlotsWith(BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para Sky Wars Ranked."), 31);
    
    List<ItemStack> items = new ArrayList<>();
    List<Seasons> cosmetics = new ArrayList<>(Seasons.listSeasons());
    for (Seasons cosmetic : cosmetics) {
      int init = 9;
      List<String> array = new ArrayList<>();
      if (cosmetic.isEnded()) {
        for (Map.Entry<String, Integer> a : cosmetic.getWinners().entrySet()) {
          SkyWarsLeague league = SkyWarsLeague.fromPoints(a.getValue());
          String leagueName = StringUtils.getFirstColor(league.getTag()) + "[" + league.getTag() + "]";
          array.add("\n &8" +  init + ". " + leagueName + " " + a.getKey() + " &7- " + StringUtils.formatNumber(a.getValue()));
          init--;
        }
      }
      StringBuilder sb = new StringBuilder();
      Collections.reverse(array);
      array.forEach(sb::append);
      
      ItemStack icon = BukkitUtils.deserializeItemStack("GRASS : 1 : nome>&cTemporada " + cosmetic.getId() + " : desc>&dInformações:\n &8▪ &fInício: &7" +
          SDF.format(cosmetic.getCreatedTime()) + "\n &8▪ &f" + (cosmetic.isEnded() ? "Acabou" : "Acaba") + " em: &7" + SDF.format(cosmetic.getEndTime()) +
          "\n \n&dLíderes:" + (!cosmetic.isEnded() ? "\n &8▪ &fA temporada ainda não foi concluída." : sb.toString()));
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
            } else if (evt.getSlot() == 31) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuPlay(profile, SkyWarsMode.RANKED);
            } else {
              Seasons cosmetic = this.cosmetics.get(item);
              if (cosmetic != null) {
                EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              }
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
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