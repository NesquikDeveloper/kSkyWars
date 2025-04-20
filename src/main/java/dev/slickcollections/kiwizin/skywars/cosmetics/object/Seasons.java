package dev.slickcollections.kiwizin.skywars.cosmetics.object;

import dev.slickcollections.kiwizin.database.Database;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.utils.TimeUtils;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.*;
import java.util.stream.Collectors;

public class Seasons {
  
  protected long id;
  protected Map<String, Integer> winners = new HashMap<>();
  protected long created, ends;
  protected boolean ended;
  
  public Seasons(long created, long ends, boolean ended, Map<String, Integer> winners) {
    this.id = SEASONS.size() + 1;
    config.set("seasons." + id + ".id", id);
    this.created = created;
    this.ends = ends;
    this.ended = ended;
    if (winners != null) {
      this.winners = winners;
    }
  }
  
  public static void deleteSeason(Player player, Seasons toDelete) {
    config.set("seasons." + toDelete.getId(), null);
    player.sendMessage("§cA temporada '" + toDelete.getId() + "' foi excluída!");
  }
  
  public static void createSeason(Player player) {
    SEASONS.add(new Seasons(System.currentTimeMillis(), TimeUtils.getExpireIn(30), false, null));
    Seasons create = SEASONS.get(SEASONS.size() - 1);
    config.set("seasons." + create.getId() + ".created", create.getCreatedTime());
    config.set("seasons." + create.getId() + ".ends", create.getEndTime());
    config.set("seasons." + create.getId() + ".ended", create.isEnded());
    player.sendMessage("§aA temporada '" + create.getId() + "' foi criada!");
  }
  
  public Map<String, Integer> getWinners() {
    Comparator<Map.Entry<String, Integer>> valueComparator =
        Map.Entry.comparingByValue();
    Map<String, Integer> sortedMap = winners.entrySet().stream().sorted(valueComparator).
            collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    return sortedMap;
  }
  
  public void setEnded(boolean flag) {
    this.ended = flag;
    if (flag) {
      // Se for finalizada, atualizar os vencedores.
      this.updateWinners();
    }
    config.set("seasons." + this.getId() + ".ended", flag);
  }
  
  public Map<String, Integer> updateWinners() {
    for (String[] key : Database.getInstance().getLeaderBoard("kCoreSkyWars", "rankedpoints")) {
      winners.put(key[0], Integer.valueOf(key[1].replace(".", "").replace(",", "")));
    }
    Comparator<Map.Entry<String, Integer>> valueComparator =
        Map.Entry.comparingByValue();
    Map<String, Integer> sortedMap = winners.entrySet().stream().sorted(valueComparator).
            collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (e1, e2) -> e1, LinkedHashMap::new));
    List<Map<String, Integer>> array = new ArrayList<>();
    array.add(sortedMap);
    // Atualizar os Vencedores.
    config.set("seasons." + this.id + ".winners", array);
    return sortedMap;
  }
  
  public long getCreatedTime() {
    return this.created;
  }
  
  public boolean isEnded() {
    return this.ended;
  }
  
  public long getId() {
    return this.id;
  }
  
  public long getEndTime() {
    return this.ends;
  }
  
  private static final List<Seasons> SEASONS = new ArrayList<>();
  public static final KConfig config = Main.getInstance().getConfig("season");
  
  public static void setupSeasons() {
    if (config.getSection("seasons") != null) {
      for (String key : config.getSection("seasons").getKeys(false)) {
        SEASONS.add(new Seasons(Long.parseLong(config.getString("seasons." + key + ".created")),
            Long.parseLong(config.getString("seasons." + key + ".ends")), config.getBoolean("seasons." + key + ".ended"),
            config.getRawConfig().getMapList("seasons." + key + ".winners").size() < 1 ? null : (Map<String, Integer>) config.getRawConfig().getMapList("seasons." + key + ".winners").get(0)));
      }
    }
    
    new BukkitRunnable() {
      @Override
      public void run() {
        listSeasons().stream().filter(a -> !a.isEnded() && System.currentTimeMillis() >= a.getEndTime()).findFirst().ifPresent(m -> m.setEnded(true));
      }
    }.runTaskTimerAsynchronously(Main.getInstance(), 0, 40);
  }
  
  public static List<Seasons> listSeasons() {
    return SEASONS;
  }
}
