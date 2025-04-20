package dev.slickcollections.kiwizin.skywars.menus;

import dev.slickcollections.kiwizin.libraries.menu.PlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.*;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.kits.NormalKit;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.MenuCosmetics;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.animations.MenuAnimations;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.MenuKits;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.MenuPerks;
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
import java.util.List;

public class MenuShop extends PlayerMenu {
  
  public MenuShop(Profile profile) {
    super(profile.getPlayer(), "Loja - Sky Wars", 5);
    
    List<Perk> perks = Cosmetic.listByType(Perk.class);
    long max = perks.size();
    long owned = perks.stream().filter(perk -> perk.has(profile)).count();
    long percentage = max == 0 ? 100 : (owned * 100) / max;
    String color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    perks.clear();
    this.setItem(11, BukkitUtils.deserializeItemStack(
        "EXP_BOTTLE : 1 : nome>&aHabilidades : desc>&7Tenha vantagens únicas para\n&7auxiliar você nas partidas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou evoluir!"));
  
    List<NormalKit> normalkits = Cosmetic.listByType(NormalKit.class);
    max = normalkits.size();
    owned = normalkits.stream().filter(kit -> kit.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    normalkits.clear();
    this.setItem(13, BukkitUtils.deserializeItemStack(
        "IRON_SWORD : 1 : esconder>tudo : nome>&aKits &f(Solo e Dupla) : desc>&7Um verdadeiro guerreiro sempre estará\n&7preparado para o combate.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou evoluir!"));

    List<Balloon> balloons = Cosmetic.listByType(Balloon.class);
    max = balloons.size();
    owned = balloons.stream().filter(cage -> cage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    balloons.clear();
    this.setItem(15, BukkitUtils.deserializeItemStack(
        "LEASH : 1 : nome>&aBalões : desc>&7Decore sua ilha com maravilhosos balões.\n \n&8Em modos de times maiores o balão\n&8será escolhido aleatoriamente pelo\n&8sistema. Um pré-requesito é o jogador\n&8ter um balão selecionado. Sendo assim,\n&8você ou seu companheiro poderá ter o\n&8balão spawnado em sua ilha.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<DeathMessage> messages = Cosmetic.listByType(DeathMessage.class);
    max = messages.size();
    owned = messages.stream().filter(deathMessage -> deathMessage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    messages.clear();
    this.setItem(29, BukkitUtils.deserializeItemStack(
        "BOOK_AND_QUILL : 1 : nome>&aMensagens de Morte : desc>&7Anuncie o abate do seu inimigo de\n&7uma forma estilosa com mensagens de morte.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<Cage> cages = Cosmetic.listByType(Cage.class);
    max = cages.size();
    owned = cages.stream().filter(cage -> cage.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    cages.clear();
    this.setItem(30, BukkitUtils.deserializeItemStack(
        "IRON_FENCE : 1 : nome>&aJaulas : desc>&7Esbanje estilo antes mesmo da partida\n&7começar com as suas jaulas.\n \n&8Lembrando que as jaulas só funcionam\n&8no modo solo. Em modo de time maiores a\n&8jaula será padronizada.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<WinAnimation> animations = Cosmetic.listByType(WinAnimation.class);
    max = animations.size();
    owned = animations.stream().filter(animation -> animation.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    animations.clear();
    this.setItem(32, BukkitUtils.deserializeItemStack(
        "DRAGON_EGG : 1 : nome>&aComemorações de Vitória : desc>&7Esbanje estilo nas suas vitórias\n&7com comemorações exclusivas.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    List<DeathCry> deathcries = Cosmetic.listByType(DeathCry.class);
    max = deathcries.size();
    owned = deathcries.stream().filter(deathcry -> deathcry.has(profile)).count();
    percentage = max == 0 ? 100 : (owned * 100) / max;
    color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
    deathcries.clear();
    this.setItem(33, BukkitUtils.deserializeItemStack(
        "GHAST_TEAR : 1 : nome>&aGritos de Morte : desc>&7Gritos de mortes são sons que\n&7irão ser reproduzidos toda vez\n&7que você morrer.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
    
    this.setItem(44, BukkitUtils.deserializeItemStack("INK_SACK:10 : 1 : nome>&aPágina 2"));
    
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
            if (evt.getSlot() == 15) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Balões", Balloon.class);
            } else if (evt.getSlot() == 13) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuKits<>(profile, "Solo e Dupla", NormalKit.class);
            } else if (evt.getSlot() == 11) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuPerks<>(profile, "Solo e Dupla", Perk.class);
            } else if (evt.getSlot() == 29) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Mensagens de Morte", DeathMessage.class);
            } else if (evt.getSlot() == 30) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Jaulas", Cage.class);
            } else if (evt.getSlot() == 32) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Comemorações", WinAnimation.class);
            } else if (evt.getSlot() == 33) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuCosmetics<>(profile, "Gritos de Morte", DeathCry.class);
            } else if (evt.getSlot() == 44) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuShopSecondPage(profile);
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
  
  static class MenuShopSecondPage extends PlayerMenu {
    
    public MenuShopSecondPage(Profile profile) {
      super(profile.getPlayer(), "Loja - Sky Wars", 4);
      
      this.setItem(11, BukkitUtils.deserializeItemStack(
          "DIAMOND : 1 : nome>&aRecompensas do Nível : desc>&7Recompensas ao atingir uma liga\n&7do modo ranqueado!\n \n&eClique para coletar!"));
  
      List<Cosmetic> totaleffects = new ArrayList<>();
      totaleffects.addAll(Cosmetic.listByType(KillEffect.class));
      totaleffects.addAll(Cosmetic.listByType(ProjectileEffect.class));
      totaleffects.addAll(Cosmetic.listByType(FallEffect.class));
      totaleffects.addAll(Cosmetic.listByType(TeleportEffect.class));
      long max = totaleffects.size();
      long owned = totaleffects.stream().filter(killEffect -> killEffect.has(profile)).count();
      long percentage = max == 0 ? 100 : (owned * 100) / max;
      String color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
      totaleffects.clear();
      this.setItem(13, BukkitUtils.deserializeItemStack(
          "321 : 1 : nome>&aAnimações : desc>&7Adicione animações às suas ações\n&7para se destacar dentro do jogo.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
  
  
      List<DeathHologram> deathHolograms = Cosmetic.listByType(DeathHologram.class);
      max = deathHolograms.size();
      owned = deathHolograms.stream().filter(deathcry -> deathcry.has(profile)).count();
      percentage = max == 0 ? 100 : (owned * 100) / max;
      color = (owned == max) ? "&a" : (owned > max / 2) ? "&7" : "&c";
      deathHolograms.clear();
      this.setItem(15, BukkitUtils.deserializeItemStack(
          "259 : 1 : nome>&aHologramas de Morte : desc>&7Hologramas de morte são mensagens que\n&7irão ser colocados toda vez na sua\n&7localização após você morrer.\n \n&fDesbloqueados: " + color + owned + "/" + max + " &8(" + percentage + "%)\n \n&eClique para comprar ou selecionar!"));
      
      this.setItem(27, BukkitUtils.deserializeItemStack("INK_SACK:8 : 1 : nome>&aPágina 1"));
      
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
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuRewards(profile);
              } else if (evt.getSlot() == 13) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuAnimations(profile);
              } else if (evt.getSlot() == 15) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuCosmetics<>(profile, "Hologramas de Morte", DeathHologram.class); 
              } else if (evt.getSlot() == 27) {
                EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
                new MenuShop(profile);
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
}
