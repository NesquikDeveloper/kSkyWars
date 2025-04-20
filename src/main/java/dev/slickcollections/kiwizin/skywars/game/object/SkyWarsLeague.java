package dev.slickcollections.kiwizin.skywars.game.object;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.booster.Booster;
import dev.slickcollections.kiwizin.database.data.DataContainer;
import dev.slickcollections.kiwizin.database.data.interfaces.AbstractContainer;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SkyWarsLeague {
  
  private static final List<SkyWarsLeague> LEAGUES = new ArrayList<>();
  private static final KConfig CONFIG = Main.getInstance().getConfig("leagues");
  protected String name;
  protected String tag;
  protected String symbol;
  protected long points;
  protected String key;
  
  public SkyWarsLeague(String key, String name, String symbol, String tag, long points) {
    this.name = StringUtils.formatColors(name);
    this.points = points;
    this.symbol = symbol;
    this.tag = StringUtils.formatColors(tag.replace("{symbol}", symbol).replace("{name}", name));
    this.key = key;
  }
  
  public static long getPoints(Profile profile) {
    return profile.getDataContainer("kCoreSkyWars", "rankedpoints").getAsLong();
  }
  
  public static SkyWarsLeague fromKey(String key) {
    return listLeagues().stream().filter(k -> k.getKey().equals(key)).findFirst().orElse(null);
  }
  
  public static SkyWarsLeague fromPoints(long compare) {
    return listLeagues().stream().filter(a -> compare >= Long.parseLong(String.valueOf(a.getPoints()))).findFirst()
        .orElse(listLeagues().get(listLeagues().size() - 1));
  }
  
  public static SkyWarsLeague getLeague(Profile profile) {
    return listLeagues().stream().
        filter(f -> getPoints(profile) >= f.getPoints()).findFirst().orElse(listLeagues().get(listLeagues().size() - 1));
  }
  
  public static void setupLeagues() {
    ConfigurationSection section = CONFIG.getSection("leagues");
    section.getKeys(false).forEach(key -> LEAGUES
        .add(new SkyWarsLeague(key, section.getString(key + ".name"), section.getString(key + ".symbol"),
            section.getString(key + ".tag"), section.getLong(key + ".points"))));
    LEAGUES.sort((l1, l2) -> Long.compare(l2.getPoints(), l1.getPoints()));
  }
  
  public static List<SkyWarsLeague> listLeagues() {
    return LEAGUES;
  }
  
  public String getName() {
    return this.name;
  }
  
  public String getTag() {
    return this.tag;
  }
  
  public String getSymbol() {
    return this.symbol;
  }
  
  public String getKey() {
    return this.key;
  }
  
  public long getPoints() {
    return this.points;
  }
  
  public static class LeagueReward {
  
    private RewardType type;
    private Object[] values;
  
    public LeagueReward(String reward) {
      if (reward == null) {
        reward = "";
      }
    
      String[] splitter = reward.split(">");
      RewardType type = RewardType.from(splitter[0]);
      if (type == null || reward.replace(splitter[0] + ">", "").split(":").length < type.getParameters()) {
        this.type = RewardType.COMANDO;
        this.values = new Object[]{"tell {name} §cPrêmio \"" + reward + "\" inválido!"};
        return;
      }
    
      this.type = type;
      try {
        this.values = type.parseValues(reward.replace(splitter[0] + ">", ""));
      } catch (Exception ex) {
        ex.printStackTrace();
        this.type = RewardType.COMANDO;
        this.values = new Object[]{"tell {name} §cPrêmio \"" + reward + "\" inválido!"};
      }
    }
  
    public void dispatch(Profile profile) {
      if (this.type == RewardType.COMANDO) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ((String) this.values[0]).replace("{name}", profile.getName()));
      } else if (this.type == RewardType.CASH) {
        profile.setStats("kCoreProfile", profile.getStats("kCoreProfile", "cash") + (long) this.values[0], "cash");
      } else if (this.type.name().contains("_COINS")) {
        profile.addCoins("kCore" + this.type.name().replace("_COINS", ""), (double) this.values[0]);
      } else if (this.type.name().contains("_BOOSTER")) {
        for (int i = 0; i < (int) this.values[0]; i++) {
          profile.getBoostersContainer().addBooster(Booster.BoosterType.valueOf(this.type.name().replace("_BOOSTER", "")), (double) this.values[1], (long) this.values[2]);
        }
      }
    }
  
    private enum RewardType {
      COMANDO(1),
      CASH(1),
      SkyWars_COINS(1),
      BedWars_Coins(1),
      TheBridge_COINS(1),
      Murder_COINS(1),
      PRIVATE_BOOSTER(3),
      NETWORK_BOOSTER(3);
    
      private int parameters;
    
      RewardType(int parameters) {
        this.parameters = parameters;
      }
    
      public int getParameters() {
        return this.parameters;
      }
    
      public Object[] parseValues(String value) throws Exception {
        if (this == COMANDO) {
          return new Object[]{value};
        } else if (this == CASH) {
          return new Object[]{Long.parseLong(value)};
        } else if (this.name().contains("_COINS")) {
          return new Object[]{Double.parseDouble(value)};
        } else if (this.name().contains("_BOOSTER")) {
          String[] values = value.split(":");
          return new Object[]{Integer.parseInt(values[0]), Double.parseDouble(values[1]), Long.parseLong(values[2])};
        }
      
        throw new Exception();
      }
    
      public static RewardType from(String name) {
        for (RewardType type : values()) {
          if (type.name().equalsIgnoreCase(name)) {
            return type;
          }
        }
      
        return null;
      }
    }
  }
  
  public static class LeagueDelivery {
  
    private long id;
    private int slot;
    private List<LeagueReward> rewards;
    public String key;
    private String icon;
  
    public LeagueDelivery(int slot, List<LeagueReward> rewards, String icon, String key) {
      this.id = DELIVERIES.size();
      this.slot = slot;
      this.rewards = rewards;
      this.key = key;
      this.icon = icon;
    }
  
    public long getId() {
      return this.id;
    }
  
    public int getSlot() {
      return this.slot;
    }
  
    public List<LeagueReward> listRewards() {
      return this.rewards;
    }
  
    public boolean hasPermission(Profile profile) {
      SkyWarsLeague league = SkyWarsLeague.fromKey(this.key);
      long levelingPoint = 1;
      if (SkyWarsLeague.getLeague(profile).getKey() != null) {
        try {
          levelingPoint = Long.parseLong(SkyWarsLeague.getLeague(profile).getKey());
        } catch (NumberFormatException ex) {
          ((KLogger) Main.getInstance().getLogger()).getModule("LEAGUE")
              .warning(SkyWarsLeague.getLeague(profile).getName() + " a 'chave' precisa ser um número.");
          return false;
        }
      }
      return SkyWarsLeague.getLeague(profile) == league || (league != null && Long.parseLong(league.getKey()) <= levelingPoint);
    }
  
    public ItemStack getIcon(Profile profile) {
      Player player = profile.getPlayer();
    
      String desc = "";
      SkyWarsLeague league = SkyWarsLeague.fromKey(this.key);
      boolean permission = this.hasPermission(profile);
      boolean alreadyClaimed = profile.getAbstractContainer("kCoreSkyWars", "leveling", LeagueContainer.class)
          .hasRewarded(this.key);
      if (alreadyClaimed) {
        desc = "\n \n&cVocê já coletou este prêmio!";
      } else {
        desc = "\n \n&aClique para coletar!";
      }
      if (!permission) {
        desc = "\n \n&cVocê não possui a liga necessária!";
      }
    
      ItemStack item = BukkitUtils.deserializeItemStack(this.icon.replace("{color}", permission && !alreadyClaimed ? "&a" : "&c") + desc);
      if (alreadyClaimed) {
        if (item.getType() == Material.STORAGE_MINECART) {
          item.setType(Material.MINECART);
          item.setDurability((short) 0);
        } else if (item.getType() == Material.POTION) {
          item.setType(Material.GLASS_BOTTLE);
          item.setDurability((short) 0);
        }
      }
      return item;
    }
  
    private static final List<LeagueDelivery> DELIVERIES = new ArrayList<>();
  
    public static void setupDeliveries() {
      KConfig config = Main.getInstance().getConfig("leveling");
    
      for (String key : config.getSection("rewards").getKeys(false)) {
        int slot = config.getInt("rewards." + key + ".slot");
        String icon = config.getString("rewards." + key + ".icon");
        List<LeagueReward> rewards = new ArrayList<>();
        for (String reward : config.getStringList("rewards." + key + ".rewards")) {
          rewards.add(new LeagueReward(reward));
        }
      
        DELIVERIES.add(new LeagueDelivery(slot, rewards, icon, config.getString("rewards." + key + ".league")));
      }
    }
  
    public static Collection<LeagueDelivery> listDeliveries() {
      return DELIVERIES;
    }
  }
  
  public static class LeagueContainer extends AbstractContainer {
  
    public LeagueContainer(DataContainer dataContainer) {
      super(dataContainer);
      JSONObject cosmetics = this.dataContainer.getAsJsonObject();
      if (!cosmetics.containsKey("last")) {
        cosmetics.put("last", new JSONArray());
      }
    
      this.dataContainer.set(cosmetics.toString());
      cosmetics.clear();
    }
  
    public void addReward(String id) {
      JSONObject cosmetics = this.dataContainer.getAsJsonObject();
      ((JSONArray) cosmetics.get("last")).add(id);
    
      this.dataContainer.set(cosmetics.toString());
      cosmetics.clear();
    }
  
    public boolean hasRewarded(String id) {
      JSONObject cosmetics = this.dataContainer.getAsJsonObject();
      return ((JSONArray) cosmetics.get("last")).contains(id);
    }
  }
  
}
