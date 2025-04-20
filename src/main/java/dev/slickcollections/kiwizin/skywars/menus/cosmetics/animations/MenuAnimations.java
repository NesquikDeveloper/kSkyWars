package dev.slickcollections.kiwizin.skywars.menus.cosmetics.animations;

import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.FallEffect;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.KillEffect;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.ProjectileEffect;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.TeleportEffect;
import dev.slickcollections.kiwizin.skywars.menus.MenuShop;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.MenuCosmetics;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import java.util.List;

public class MenuAnimations extends PlayerMenu {
  
  public MenuAnimations(Profile profile) {
    super(profile.getPlayer(), "Sky Wars - Animações", 4);
    
    List<KillEffect> killeffects = Cosmetic.listByType(KillEffect.class);
    long max = killeffects.size();
    long owned = killeffects.stream().filter(killEffect -> killEffect.has(profile)).count();
    long percentage = max == 0 ? 100 : (owned * 100) / max;
    String color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    killeffects.clear();
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "BONE : 1 : nome>&aAnimações de Abate : desc>&7Deixa a sua marca quando abater\n&7os seus oponentes.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<ProjectileEffect> projectileeffects = Cosmetic.listByType(ProjectileEffect.class);
    max = projectileeffects.size();
    owned = projectileeffects.stream().filter(kprojectileEffect -> kprojectileEffect.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    projectileeffects.clear();
    this.setItem(12, BukkitUtils.deserializeItemStack(
        "EGG : 1 : nome>&aAnimações de Projétil : desc>&7Esbanje estilo nos seus projéteis\n&7com partículas maravilhosas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<FallEffect> falleffects = Cosmetic.listByType(FallEffect.class);
    max = falleffects.size();
    owned = falleffects.stream().filter(kprojectileEffect -> kprojectileEffect.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    falleffects.clear();
    this.setItem(13, BukkitUtils.deserializeItemStack(
        "DIAMOND_BOOTS : 1 : nome>&aAnimações de Queda : desc>&7Quando você levar dano de queda\n&7irá aparecer partículas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<TeleportEffect> teleporteffects = Cosmetic.listByType(TeleportEffect.class);
    max = teleporteffects.size();
    owned = teleporteffects.stream().filter(tpe -> tpe.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    teleporteffects.clear();
    this.setItem(14, BukkitUtils.deserializeItemStack(
        "ENDER_PEARL : 1 : nome>&aAnimações de Teleporte : desc>&7Quando você teletransportar-se\n&7irá aparecer partículas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    this.setItem(31, BukkitUtils.deserializeItemStack("INK_SACK:1 : 1 : nome>&cVoltar : desc>&7Para a loja."));
    
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
            if (evt.getSlot() == 31) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuShop(profile);
            } else if (evt.getSlot() == 11) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Animações de Abate", KillEffect.class);
            } else if (evt.getSlot() == 12) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Animações de Projétil", ProjectileEffect.class);
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Animações de Queda", FallEffect.class);
            } else if (evt.getSlot() == 14) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Animações de Teleport", TeleportEffect.class);
            }
          }
        }
      }
    }
  }
  
  public void cancel() {
    HandlerList.unregisterAll(this);
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
