package dev.slickcollections.kiwizin.skywars.cosmetics.types.winanimations;


import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.winanimations.FireworksExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Fireworks extends WinAnimation {
  
  public Fireworks(ConfigurationSection section) {
    super(0, "fireworks", 0.0, "", section.getString("name"), section.getString("icon"));
  }
  
  @Override
  public boolean has(Profile profile) {
    return true;
  }
  
  public AbstractExecutor execute(Player player) {
    return new FireworksExecutor(player);
  }
}
