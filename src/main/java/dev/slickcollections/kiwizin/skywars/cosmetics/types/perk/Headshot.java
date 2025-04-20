package dev.slickcollections.kiwizin.skywars.cosmetics.types.perk;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.api.SWEvent;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.perk.PerkLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class Headshot extends Perk implements Listener {
  
  private final int index;
  
  public Headshot(int index, String key) {
    super(15, key, CONFIG.getString(key + ".permission"), CONFIG.getString(key + ".name"), CONFIG.getString(key + ".icon"), new ArrayList<>());
    this.index = index;
    this.setupLevels(key);
    
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onEntityDamageByEntity(EntityDamageByEntityEvent evt) {
    if (evt.isCancelled()) {
      return;
    }
    
    if (evt.getEntity() instanceof Player && evt.getDamager() instanceof Arrow) {
      Player damaged = (Player) evt.getEntity();
      Arrow arrow = (Arrow) evt.getDamager();
      
      if (arrow.getShooter() instanceof Player) {
        Player player = (Player) arrow.getShooter();
        
        Profile profile = Profile.getProfile(player.getName());
        if (profile != null) {
          AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
          if (game != null && !game.isSpectator(player) && game.getMode().getCosmeticIndex() == getIndex() && isSelectedPerk(profile) && has(profile) && canBuy(player)) {
            PerkLevel perkLevel = this.getCurrentLevel(profile);
            int percentage = getCurrentLevel(profile).getValue("percentage", int.class, 0);
            if (isHeadShot(damaged, arrow) && ThreadLocalRandom.current().nextInt(100) < percentage) {
              player.sendMessage("§6[Headshot] §aVocê aplicou naúsea no seu oponente!");
              // Aplicar efeito.
              damaged.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION,
                  perkLevel.getValue("time", int.class, 0), perkLevel.getValue("level", int.class, 1) - 1));
            }
          }
        }
      }
    }
  }
  
  private boolean isHeadShot(Player player, Arrow arrow) {
    double arrowY = arrow.getLocation().getY();
    double damagedY = player.getLocation().getY();
    return arrowY - damagedY > 1.4D;
  }
  
  @Override
  public long getIndex() {
    return this.index;
  }
  
  @Override
  public void handleEvent(SWEvent evt) {
  }
  
  @Override
  public List<Class<?>> getEventTypes() {
    return null;
  }
}