package dev.slickcollections.kiwizin.skywars.cosmetics.types.perk;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.api.SWEvent;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;

import java.util.ArrayList;
import java.util.List;

public class MestreDoFim extends Perk {
  
  private final int index;
  
  public MestreDoFim(int index, String key) {
    super(7, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    Bukkit.getPluginManager().registerEvents(new Listener() {
      @EventHandler(priority = EventPriority.MONITOR)
      public void onPlayerTeleport(PlayerTeleportEvent evt) {
        if (evt.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
          Profile profile = Profile.getProfile(evt.getPlayer().getName());
          if (profile != null) {
            Player player = evt.getPlayer();
            AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
            if (game != null && !game.isSpectator(player) && isSelectedPerk(profile) && game.getMode().getCosmeticIndex() == getIndex()) {
              evt.setCancelled(true);
              double damage = 5.0;
              if (has(profile) && canBuy(player)) {
                damage = damage - ((getCurrentLevel(profile).getValue("percentage", int.class, 0) * damage) / 100.0);
              }
              
              player.setFallDistance(0.0F);
              player.teleport(evt.getTo());
              if (damage > 0.0) {
                player.damage(damage);
              }
            }
          }
        }
      }
    }, Main.getInstance());
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
  
  @Override
  public void handleEvent(SWEvent evt) {
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return null;
  }
}
