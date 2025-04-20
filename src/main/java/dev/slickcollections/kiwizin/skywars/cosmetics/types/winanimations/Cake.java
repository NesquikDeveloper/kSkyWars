package dev.slickcollections.kiwizin.skywars.cosmetics.types.winanimations;

import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.winanimations.CakeExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class Cake extends WinAnimation {
  
  public Cake(ConfigurationSection section) {
    super(section.getLong("id"), "cake", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new CakeExecutor(player);
  }
}
