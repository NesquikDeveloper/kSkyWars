package dev.slickcollections.kiwizin.skywars.cosmetics.object;


import dev.slickcollections.kiwizin.skywars.cosmetics.types.Balloon;
import dev.slickcollections.kiwizin.skywars.nms.NMS;
import dev.slickcollections.kiwizin.skywars.nms.interfaces.BalloonEntity;
import org.bukkit.Location;

public class IslandBalloon {
  
  private BalloonEntity leash;
  private BalloonEntity bat;
  private BalloonEntity giant;
  
  public IslandBalloon(Location location, Balloon balloon) {
    this.leash = NMS.createBalloonLeash(location);
    
    Location batLocation = location.clone();
    batLocation.setX(batLocation.getX() - 4.0);
    batLocation.setY(batLocation.getY() + 18.0);
    batLocation.setZ(batLocation.getZ() + 5.5);
    this.bat = NMS.createBalloonBat(batLocation, this.leash);
    
    Location giantLocation = location.clone();
    giantLocation.setX(giantLocation.getX() - 2.0);
    giantLocation.setY(giantLocation.getY() + 9.0);
    giantLocation.setZ(giantLocation.getZ() + 3.0);
    
    this.giant = NMS.createBalloonGiant(giantLocation, balloon.getTextures());
  }
  
  public void despawn() {
    if (this.leash != null) {
      this.leash.kill();
      this.leash = null;
    }
    if (this.bat != null) {
      this.bat.kill();
      this.bat = null;
    }
    if (this.giant != null) {
      this.giant.kill();
      this.giant = null;
    }
  }
}