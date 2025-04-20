package dev.slickcollections.kiwizin.skywars.menus;

import dev.slickcollections.kiwizin.libraries.menu.UpdatablePlayerMenu;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Seasons;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.enums.SkyWarsMode;
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
import java.util.Locale;

public class MenuPlay extends UpdatablePlayerMenu {
  
  private final SkyWarsMode mode;
  
  public MenuPlay(Profile profile, SkyWarsMode mode) {
    super(profile.getPlayer(), "Sky Wars " + mode.getName(), mode == SkyWarsMode.RANKED && Seasons.listSeasons().size() > 0 ? 4 : 3);
    this.mode = mode;
    
    this.update();
    this.register(Main.getInstance(), 20);
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
            if (evt.getSlot() == 12) {
              EnumSound.ITEM_PICKUP.play(this.player, 0.5F, 2.0F);
              AbstractSkyWars game = AbstractSkyWars.findRandom(this.mode);
              if (game != null) {
                this.player.sendMessage(Language.lobby$npc$play$connect);
                game.join(profile);
              }
            } else if (evt.getSlot() == 14) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuMapSelector(profile, this.mode);
            } else if ((this.getInventory().getSize()) > 30 && evt.getSlot() == 31) {
              EnumSound.CLICK.play(this.player, 0.5F, 2.0F);
              new MenuSeasons(profile);
            }
          }
        }
      }
    }
  }
  
  private static final SimpleDateFormat SDF = new SimpleDateFormat("d 'de' MMMM. yyyy 'às' HH:mm", Locale.forLanguageTag("pt-BR"));
  
  @Override
  public void update() {
    int players = this.mode == SkyWarsMode.SOLO || this.mode == SkyWarsMode.DUPLA ? this.mode.getSize() * 12 : 4;
    int waiting = AbstractSkyWars.getWaiting(this.mode);
    int playing = AbstractSkyWars.getPlaying(this.mode);
    
    this.setItem(12, BukkitUtils.deserializeItemStack("ENDER_PEARL : 1 : nome>&aSky Wars " +
        this.mode.getName() + " : desc>&7" + (this.mode == SkyWarsMode.SOLO || this.mode == SkyWarsMode.DUPLA ? "12" : "4")
        + " ilhas, " + players
        + " jogadores.\n \n &8▪ &fEm espera: &7" + StringUtils.formatNumber(waiting) + "\n &8▪ &fJogando: &7" + StringUtils.formatNumber(playing) + "\n \n&eClique para jogar!"));
    
    this.setItem(14, BukkitUtils.deserializeItemStack("MAP : 1 : nome>&aSelecione um Mapa : desc> &8▪ &fMapas: &7" +
        (AbstractSkyWars.listByMode(this.mode).size()) + "\n \n&eClique para jogar em um mapa específico."));
    if (this.mode == SkyWarsMode.RANKED && Seasons.listSeasons().size() > 0) {
      Seasons m = Seasons.listSeasons().get(Seasons.listSeasons().size() - 1);
      this.setItem(31, BukkitUtils.deserializeItemStack("GOLD_INGOT : 1 : nome>&aTemporada " + m.getId()
          + " : desc>&dInformações:\n &8▪ &fInício: &7" + SDF.format(m.getCreatedTime()) + "\n &8▪ &fAcab" + (m.isEnded() ? "ou" : "a") + " em: &7" + SDF.format(m.getEndTime()) + "\n \n&eClique para ver o histórico de temporadas!"));
    }
  }
  
  public void cancel() {
    super.cancel();
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
