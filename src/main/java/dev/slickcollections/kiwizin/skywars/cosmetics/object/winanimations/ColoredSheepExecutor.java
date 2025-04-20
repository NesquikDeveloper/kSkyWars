package dev.slickcollections.kiwizin.skywars.cosmetics.object.winanimations;

import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractExecutor;
import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ColoredSheepExecutor extends AbstractExecutor implements Listener {
  
  private final List<DyeColor> dyes = Arrays.asList(DyeColor.WHITE, DyeColor.ORANGE, DyeColor.MAGENTA, DyeColor.LIGHT_BLUE,
      DyeColor.YELLOW, DyeColor.LIME, DyeColor.PINK, DyeColor.GRAY, DyeColor.SILVER, DyeColor.CYAN, DyeColor.PURPLE, DyeColor.BLUE,
      DyeColor.BROWN, DyeColor.GREEN, DyeColor.RED, DyeColor.BLACK);
  
  private final List<Sheep> entities = new ArrayList<>();
  
  public ColoredSheepExecutor(Player player) {
    super(player);
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  @Override
  public void tick() {
    Sheep sheep = this.player.getWorld().spawn(player.getLocation().clone().add(Math.floor(Math.random() * 3.0D), 5,
        Math.floor(Math.random() * 3.0D)), Sheep.class);
    sheep.setColor(dyes.get((int) Math.floor(Math.random() * 16.0D)));
    entities.add(sheep);
  }
  
  @Override
  public void cancel() {
    entities.forEach(Entity::remove);
    HandlerList.unregisterAll(this);
  }
  
  @EventHandler(priority = EventPriority.HIGHEST)
  public void onEntityDamage(EntityDamageEvent evt) {
    if (evt.getEntity() instanceof Sheep) {
      evt.setCancelled(true);
    }
  }
}
