package dev.slickcollections.kiwizin.skywars.game.enums;

import dev.slickcollections.kiwizin.reflection.Accessors;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.interfaces.LoadCallback;
import dev.slickcollections.kiwizin.skywars.game.types.NormalSkyWars;

public enum SkyWarsMode {
  RANKED("Ranked", "ranked", 1, NormalSkyWars.class, 1),
  SOLO("Solo", "1v1", 1, NormalSkyWars.class, 1),
  DUPLA("Duplas", "2v2", 2, NormalSkyWars.class, 1);
  
  private static final SkyWarsMode[] VALUES = values();
  
  private final int size;
  private final String stats;
  private final String name;
  private final Class<? extends AbstractSkyWars> gameClass;
  private final int cosmeticIndex;
  
  SkyWarsMode(String name, String stats, int size, Class<? extends AbstractSkyWars> gameClass, int cosmeticIndex) {
    this.name = name;
    this.stats = stats;
    this.size = size;
    this.gameClass = gameClass;
    this.cosmeticIndex = cosmeticIndex;
  }
  
  public static SkyWarsMode fromName(String name) {
    for (SkyWarsMode mode : VALUES) {
      if (name.equalsIgnoreCase(mode.name())) {
        return mode;
      }
    }
    
    return null;
  }
  
  public AbstractSkyWars buildGame(String name, LoadCallback callback) {
    return Accessors.getConstructor(this.gameClass, String.class, LoadCallback.class).newInstance(name, callback);
  }
  
  public int getSize() {
    return this.size;
  }
  
  public String getStats() {
    return this.stats;
  }
  
  public String getName() {
    return this.name;
  }
  
  public int getCosmeticIndex() {
    return cosmeticIndex;
  }
}
