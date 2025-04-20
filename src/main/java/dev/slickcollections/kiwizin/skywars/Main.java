package dev.slickcollections.kiwizin.skywars;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.libraries.MinecraftVersion;
import dev.slickcollections.kiwizin.plugin.KPlugin;
import dev.slickcollections.kiwizin.skywars.cmd.Commands;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Promotion;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Seasons;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.object.SkyWarsChest;
import dev.slickcollections.kiwizin.skywars.game.object.SkyWarsLeague;
import dev.slickcollections.kiwizin.skywars.hook.SWCoreHook;
import dev.slickcollections.kiwizin.skywars.listeners.Listeners;
import dev.slickcollections.kiwizin.skywars.lobby.*;
import dev.slickcollections.kiwizin.skywars.utils.tagger.TagUtils;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileInputStream;

public class Main extends KPlugin {
  
  public static boolean kMysteryBox, kCosmetics, kClans;
  public static String currentServerName;
  private static Main instance;
  private static boolean validInit;
  
  public static Main getInstance() {
    return instance;
  }
  
  @Override
  public void start() {
    instance = this;
  }
  
  @Override
  public void load() {}
  
  @Override
  public void enable() {
    if (MinecraftVersion.getCurrentVersion().getCompareId() != 183) {
      this.setEnabled(false);
      return;
    }
    
    saveDefaultConfig();
    currentServerName = getConfig().getString("lobby");
    kMysteryBox = Bukkit.getPluginManager().getPlugin("kMysteryBox") != null;
    kCosmetics = Bukkit.getPluginManager().getPlugin("kCosmetics") != null;
    kClans = Bukkit.getPluginManager().getPlugin("kClans") != null;
    if (getConfig().getString("spawn") != null) {
      Core.setLobby(BukkitUtils.deserializeLocation(getConfig().getString("spawn")));
    }
    
    AbstractSkyWars.setupGames();
    Language.setupLanguage();
    SWCoreHook.setupHook();
    Listeners.setupListeners();
    SkyWarsLeague.setupLeagues();
    Commands.setupCommands();
    Leaderboard.setupLeaderboards();
    
    DeliveryNPC.setupNPCs();
    Cosmetic.setupCosmetics();
    Promotion.setupPromotions();
    PlayNPC.setupNPCs();
    StatsNPC.setupNPCs();
    PromotionNPC.setupNPCs();
    SkyWarsLeague.LeagueDelivery.setupDeliveries();
    SkyWarsChest.ChestType.setupChestTypes();
    Lobby.setupLobbies();
    Seasons.setupSeasons();
  
    validInit = true;
    this.getLogger().info("O plugin foi ativado.");
  }
  
  @Override
  public void disable() {
    if (validInit) {
      DeliveryNPC.listNPCs().forEach(DeliveryNPC::destroy);
      PlayNPC.listNPCs().forEach(PlayNPC::destroy);
      StatsNPC.listNPCs().forEach(StatsNPC::destroy);
      Leaderboard.listLeaderboards().forEach(Leaderboard::destroy);
      TagUtils.reset();
    }
    this.getLogger().info("O plugin foi desativado.");
  }
}
