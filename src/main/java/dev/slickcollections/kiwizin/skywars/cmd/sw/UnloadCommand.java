package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandSender;

import java.util.logging.Level;

public class UnloadCommand extends SubCommand {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("UNLOAD_WORLD");
  
  public UnloadCommand() {
    super("unload", "unload [world]", "Descarregue um mundo.", false);
  }
  
  @Override
  public void perform(CommandSender sender, String[] args) {
    if (args.length == 0) {
      sender.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    
    World world = Bukkit.getWorld(args[0]);
    if (world != null) {
      try {
        Bukkit.unloadWorld(world, true);
        sender.sendMessage("§aMundo descarregado com sucesso.");
      } catch (Exception ex) {
        LOGGER.log(Level.WARNING, "Cannot unload world \"" + world.getName() + "\"", ex);
        sender.sendMessage("§cErro ao descarregar mundo.");
      }
    } else {
      sender.sendMessage("§cMundo não encontrado.");
    }
  }
}
