package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

public class TeleportCommand extends SubCommand {
  
  public TeleportCommand() {
    super("tp", "tp [mundo]", "Teleporte-se até um mundo.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    
    World world = Bukkit.getWorld(args[0]);
    if (world != null) {
      player.teleport(new Location(world, 0.5, world.getHighestBlockYAt(0, 0), 0.5));
      player.sendMessage("§aTeleportado com sucesso.");
    } else {
      player.sendMessage("§cMundo não encontrado.");
    }
  }
}
