package dev.slickcollections.kiwizin.skywars.game.events;

import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.SkyWarsEvent;

public class EndEvent extends SkyWarsEvent {
  
  @Override
  public void execute(AbstractSkyWars game) {
    game.stop(null);
  }
  
  @Override
  public String getName() {
    return Language.options$events$end;
  }
}
