package dev.slickcollections.kiwizin.skywars.cosmetics.object.winanimations;

import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractExecutor;
import dev.slickcollections.kiwizin.skywars.nms.NMS;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;

public class EnderDragonExecutor extends AbstractExecutor {
  
  public EnderDragonExecutor(Player player) {
    super(player);
    NMS.createMountableEnderDragon(player);
  }
  
  @Override
  public void tick() {
    player.launchProjectile(Fireball.class);
  }
}