package dev.slickcollections.kiwizin.skywars.listeners.player;

import dev.slickcollections.kiwizin.game.GameState;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.cmd.sw.BuildCommand;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;

public class InventoryClickListener implements Listener {
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onInventoryClick(InventoryClickEvent evt) {
    if (evt.getWhoClicked() instanceof Player) {
      Player player = (Player) evt.getWhoClicked();
      Profile profile = Profile.getProfile(player.getName());
      
      if (profile != null) {
        AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
        if (game == null) {
          evt.setCancelled(!BuildCommand.hasBuilder(player));
        } else if (game.isSpectator(player) || game.getState() != GameState.EMJOGO) {
          evt.setCancelled(true);
        }
      }
    }
  }
}
