package dev.slickcollections.kiwizin.skywars.lobby.leaderboards;

import dev.slickcollections.kiwizin.database.Database;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.lobby.Leaderboard;
import org.bukkit.Location;

import java.util.List;

public class PointsLeaderboard extends Leaderboard {
  
  public PointsLeaderboard(Location location, String id) {
    super(location, id);
  }
  
  @Override
  public String getType() {
    return "pontos";
  }
  
  @Override
  public List<String[]> getSplitted() {
    List<String[]> list = Database.getInstance().getLeaderBoard("kCoreSkyWars", "rankedpoints");
    while (list.size() < 10) {
      list.add(new String[]{Language.lobby$leaderboard$empty, "0"});
    }
    return list;
  }
  
  @Override
  public List<String> getHologramLines() {
    return Language.lobby$leaderboard$points$hologram;
  }
}
