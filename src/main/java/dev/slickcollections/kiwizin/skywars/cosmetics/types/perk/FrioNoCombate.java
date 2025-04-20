package dev.slickcollections.kiwizin.skywars.cosmetics.types.perk;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.api.SWEvent;
import dev.slickcollections.kiwizin.skywars.api.event.player.SWPlayerDeathEvent;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.perk.PerkLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class FrioNoCombate extends Perk {
  
  private final int index;
  
  public FrioNoCombate(int index, String key) {
    super(8, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    this.register();
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
  
  public void handleEvent(SWEvent evt2) {
    if (evt2 instanceof SWPlayerDeathEvent) {
      SWPlayerDeathEvent evt = (SWPlayerDeathEvent) evt2;
      if (evt.hasKiller()) {
        AbstractSkyWars game = (AbstractSkyWars) evt.getGame();
        Profile profile = evt.getKiller();
        
        Player player = profile.getPlayer();
        if (!game.isSpectator(player) && game.getMode().getCosmeticIndex() == this.getIndex() && this.has(profile) && this.canBuy(player)) {
          PerkLevel perkLevel = this.getCurrentLevel(profile);
          player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, perkLevel.getValue("time", int.class, 0), perkLevel.getValue("level", int.class, 1) - 1));
        }
      }
    }
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return Collections.singletonList(SWPlayerDeathEvent.class);
  }
}
