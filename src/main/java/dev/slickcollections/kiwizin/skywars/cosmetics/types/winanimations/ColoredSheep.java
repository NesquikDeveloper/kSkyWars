package dev.slickcollections.kiwizin.skywars.cosmetics.types.winanimations;

import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.winanimations.ColoredSheepExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.WinAnimation;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;

public class ColoredSheep extends WinAnimation {
  
  public ColoredSheep(ConfigurationSection section) {
    super(section.getLong("id"), "rainbow", section.getDouble("coins"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  public AbstractExecutor execute(Player player) {
    return new ColoredSheepExecutor(player);
  }
}
