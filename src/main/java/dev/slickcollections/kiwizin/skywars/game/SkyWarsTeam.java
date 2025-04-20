package dev.slickcollections.kiwizin.skywars.game;

import dev.slickcollections.kiwizin.game.GameTeam;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.IslandBalloon;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Balloon;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Cage;
import dev.slickcollections.kiwizin.utils.BukkitUtils;

import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

public class SkyWarsTeam extends GameTeam {
  
  private final int index;
  private IslandBalloon islandBalloon;
  
  public SkyWarsTeam(AbstractSkyWars game, String location, int size) {
    super(game, location, size);
    this.index = game.listTeams().size();
  }
  
  @Override
  public void reset() {
    super.reset();
    if (this.islandBalloon != null) {
      this.islandBalloon.despawn();
      this.islandBalloon = null;
    }
  }
  
  public void startGame() {
    this.breakCage();
    String location = ((AbstractSkyWars) getGame()).getConfig().getBalloonLocation(this.index);
    if (location != null) {
      Balloon balloon = null;
      List<Profile> profiles = this.listPlayers().stream().map(player -> Profile.getProfile(player.getName()))
          .filter(profile -> profile.getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).getSelected(CosmeticType.BALLOON, Balloon.class) != null)
          .collect(Collectors.toList());
      if (profiles.size() > 0) {
        balloon = profiles.get(ThreadLocalRandom.current().nextInt(profiles.size())).getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class)
            .getSelected(CosmeticType.BALLOON, Balloon.class);
      }
      
      if (balloon != null) {
        this.islandBalloon = new IslandBalloon(BukkitUtils.deserializeLocation(location), balloon);
      }
    }
  }
  
  
  public void buildCage(Cage cage) {
    if (cage == null || this.getTeamSize() > 1) {
      Cage.applyCage(this.getLocation().clone().add(0, -1, 0), this.getTeamSize() > 1);
      return;
    }
    
    cage.apply(this.getLocation().clone().add(0, -1, 0));
  }
  
  public void breakCage() {
    Cage.destroyCage(this.getLocation().clone().add(0, -1, 0));
  }
}
