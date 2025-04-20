package dev.slickcollections.kiwizin.skywars.game.types;

import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.interfaces.LoadCallback;

public class NormalSkyWars extends AbstractSkyWars {
  
  public NormalSkyWars(String name, LoadCallback callback) {
    super(name, callback);
  }
}
