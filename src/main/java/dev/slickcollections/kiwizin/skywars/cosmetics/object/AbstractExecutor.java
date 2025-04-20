package dev.slickcollections.kiwizin.skywars.cosmetics.object;

import org.bukkit.entity.Player;

public abstract class AbstractExecutor {
  
  protected Player player;
  
  public AbstractExecutor(Player player) {
    this.player = player;
  }
  
  public void tick() {
  }
  
  public void cancel() {
  }
  
  public Player getPlayer() {
    return this.player;
  }
}
