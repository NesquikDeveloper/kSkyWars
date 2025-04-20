package dev.slickcollections.kiwizin.skywars.lobby.leaderboards;

import dev.slickcollections.kiwizin.database.Database;
import dev.slickcollections.kiwizin.database.MySQLDatabase;
import dev.slickcollections.kiwizin.database.data.DataContainer;
import dev.slickcollections.kiwizin.database.exception.ProfileLoadException;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.lobby.Leaderboard;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Location;

import java.util.*;

public class KillsLeaderboard extends Leaderboard {
  
  public KillsLeaderboard(Location location, String id) {
    super(location, id);
  }
  
  @Override
  public String getType() {
    return "abates";
  }
  
  @Override
  public List<String[]> getSplitted() {
    List<String[]> list = Database.getInstance().getLeaderBoard("kCoreSkyWars", (this.canSeeMonthly() ?
        Collections.singletonList("monthlykills") : Arrays.asList("1v1kills", "2v2kills", "rankedkills")).toArray(new String[0]));
  
    
    while (list.size() < 10) {
      list.add(new String[]{Language.lobby$leaderboard$empty, "0"});
    }
    return list;
  }
  
  @Override
  public List<String> getHologramLines() {
    return Language.lobby$leaderboard$kills$hologram;
  }
}
