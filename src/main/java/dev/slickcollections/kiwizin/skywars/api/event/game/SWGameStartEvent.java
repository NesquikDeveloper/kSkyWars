package dev.slickcollections.kiwizin.skywars.api.event.game;

import dev.slickcollections.kiwizin.game.Game;
import dev.slickcollections.kiwizin.game.GameTeam;
import dev.slickcollections.kiwizin.skywars.api.SWEvent;

public class SWGameStartEvent extends SWEvent {
  
  private final Game<? extends GameTeam> game;
  
  public SWGameStartEvent(Game<? extends GameTeam> game) {
    this.game = game;
  }
  
  public Game<? extends GameTeam> getGame() {
    return this.game;
  }
}
