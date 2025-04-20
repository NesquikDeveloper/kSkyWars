package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.WorldCreator;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.logging.Level;

import static dev.slickcollections.kiwizin.skywars.utils.VoidChunkGenerator.VOID_CHUNK_GENERATOR;

public class LoadCommand extends SubCommand {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("LOAD_WORLD");
  
  public LoadCommand() {
    super("load", "load [mundo]", "Carregue um mundo.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    
    if (Bukkit.getWorld(args[0]) != null) {
      sender.sendMessage("§cMundo já existente.");
      return;
    }
    
    File map = new File(args[0]);
    if (!map.exists() || !map.isDirectory()) {
      sender.sendMessage("§cPasta do Mundo não encontrada.");
      return;
    }
    
    try {
      sender.sendMessage("§aCarregando...");
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
        WorldCreator wc = WorldCreator.name(map.getName());
        wc.generateStructures(false);
        wc.generator(VOID_CHUNK_GENERATOR);
        World world = wc.createWorld();
        world.setTime(0L);
        world.setStorm(false);
        world.setThundering(false);
        world.setAutoSave(false);
        world.setAnimalSpawnLimit(0);
        world.setWaterAnimalSpawnLimit(0);
        world.setKeepSpawnInMemory(false);
        world.setGameRuleValue("doMobSpawning", "false");
        world.setGameRuleValue("doDaylightCycle", "false");
        world.setGameRuleValue("mobGriefing", "false");
        sender.sendMessage("§aMundo carregado com sucesso.");
      });
    } catch (Exception ex) {
      LOGGER.log(Level.WARNING, "Cannot load world \"" + args[0] + "\"", ex);
      sender.sendMessage("§cNão foi possível carregar o mundo.");
    }
  }
}
