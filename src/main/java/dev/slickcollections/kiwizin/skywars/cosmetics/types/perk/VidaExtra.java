package dev.slickcollections.kiwizin.skywars.cosmetics.types.perk;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.api.SWEvent;
import dev.slickcollections.kiwizin.skywars.api.event.game.SWGameStartEvent;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class VidaExtra extends Perk {
  
  protected int index;
  
  public VidaExtra(int index, String key) {
    super(14, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    this.register();
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
  
  @Override
  public void handleEvent(SWEvent evt2) {
    if (evt2 instanceof SWGameStartEvent) {
      SWGameStartEvent evt = (SWGameStartEvent) evt2;
      AbstractSkyWars game = (AbstractSkyWars) evt.getGame();
      game.listPlayers().forEach(player -> {
        Profile profile = Profile.getProfile(player.getName());
        if (has(profile) && isSelectedPerk(profile) && canBuy(player)) {
          player.setMaxHealth(20.0D + Double.parseDouble(String.valueOf(getCurrentLevel(profile)
              .getValue("extra_health", int.class, 1))) * 2);
          player.setHealth(player.getMaxHealth());
        }
      });
    }
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return Collections.singletonList(SWGameStartEvent.class);
  }
}
