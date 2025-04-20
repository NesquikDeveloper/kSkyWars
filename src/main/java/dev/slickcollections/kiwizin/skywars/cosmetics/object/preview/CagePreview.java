package dev.slickcollections.kiwizin.skywars.cosmetics.object.preview;

import dev.slickcollections.kiwizin.collectibles.api.CosmeticsAPI;
import dev.slickcollections.kiwizin.game.FakeGame;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractPreview;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Cage;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.MenuCosmetics;
import dev.slickcollections.kiwizin.skywars.nms.NMS;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class CagePreview extends AbstractPreview<Cage> implements Listener {
  
  private static final Location[] LOCATIONS = new Location[3];

  static {
    createLocations();
  }
  
  private Entity cart;
  private Location oldLocation;
  
  public CagePreview(Profile profile, Cage cosmetic) {
    super(profile, cosmetic);
    
    cosmetic.preview(this.player, LOCATIONS[0], false);
    
    this.oldLocation = this.player.getLocation();
    profile.setGame(FakeGame.FAKE_GAME);
    profile.setHotbar(null);
    for (Player players : Bukkit.getOnlinePlayers()) {
      players.hidePlayer(player);
    }
    if (Main.kCosmetics) {
      CosmeticsAPI.disable(player);
    }
    
    this.cart = NMS.createAttachedCart(this.player.getName(), LOCATIONS[1]);
    this.player.teleport(LOCATIONS[1]);
    
    this.runTaskLater(Main.getInstance(), 3L);
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  public static void createLocations() {
    if (CONFIG.contains("cage")) {
      for (int index = 0; index < 3; index++) {
        String value = CONFIG.getString("cage." + (index + 1));
        if (value != null) {
          LOCATIONS[index] = BukkitUtils.deserializeLocation(value);
        }
      }
    }
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerQuit(PlayerQuitEvent evt) {
    if (evt.getPlayer().equals(this.player)) {
      this.stop();
    }
  }
  
  public void stop() {
    this.oldLocation = null;
    if (this.cart != null) {
      this.cart.remove();
      this.cart = null;
    }
    HandlerList.unregisterAll(this);
  }
  
  @Override
  public void run() {
    NMS.sendFakeSpectator(this.player, this.cart);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
      this.returnToMenu();
      this.stop();
    }, 57L);
  }
  
  @Override
  public void returnToMenu() {
    Profile profile = Profile.getProfile(this.player.getName());
    if (profile != null) {
      cosmetic.preview(this.player, LOCATIONS[0], true);
      NMS.sendFakeSpectator(this.player, null);
      this.player.setAllowFlight(this.player.hasPermission("kcore.fly"));
      profile.setGame(null);
      profile.setHotbar(Hotbar.getHotbarById("lobby"));
      profile.refreshPlayers();
      this.player.teleport(this.oldLocation);
      if (Main.kCosmetics) {
        CosmeticsAPI.enable(player);
      }
      
      new MenuCosmetics<>(profile, "Jaulas", Cage.class);
    }
  }
}
