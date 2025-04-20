package dev.slickcollections.kiwizin.skywars.listeners.player;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.cmd.sw.BuildCommand;
import dev.slickcollections.kiwizin.skywars.cmd.sw.ChestCommand;
import dev.slickcollections.kiwizin.skywars.cmd.sw.CreateCommand;
import dev.slickcollections.kiwizin.skywars.utils.tagger.TagUtils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerQuitListener implements Listener {
  
  @EventHandler
  public void onPlayerQuit(PlayerQuitEvent evt) {
    evt.setQuitMessage(null);
    Profile profile = Profile.getProfile(evt.getPlayer().getName());;
    BuildCommand.remove(evt.getPlayer());
    TagUtils.reset(evt.getPlayer().getName());
    CreateCommand.CREATING.remove(evt.getPlayer());
    ChestCommand.CHEST.remove(evt.getPlayer());
  }
}
