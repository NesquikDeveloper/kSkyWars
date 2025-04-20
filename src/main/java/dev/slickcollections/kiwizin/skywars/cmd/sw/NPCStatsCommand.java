package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.lobby.StatsNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NPCStatsCommand extends SubCommand {
  
  public NPCStatsCommand() {
    super("npcstatus", "npcstatus", "Adicione/remova NPCs de Status.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage(" \n§eAjuda\n \n§6/sw npcstatus adicionar [id] §f- §7Adicionar NPC.\n§6/sw npcstatus remover [id] §f- §7Remover NPC.\n ");
      return;
    }
    
    String action = args[0];
    if (action.equalsIgnoreCase("adicionar")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw npcstatus adicionar [id]");
        return;
      }
      
      String id = args[1];
      if (StatsNPC.getById(id) != null) {
        player.sendMessage("§cJá existe um NPC de Status utilizando \"" + id + "\" como ID.");
        return;
      }
      
      Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
      location.setYaw(player.getLocation().getYaw());
      location.setPitch(player.getLocation().getPitch());
      StatsNPC.add(id, location);
      player.sendMessage("§aNPC de Status adicionado com sucesso.");
    } else if (action.equalsIgnoreCase("remover")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw npcstatus remover [id]");
        return;
      }
      
      String id = args[1];
      StatsNPC npc = StatsNPC.getById(id);
      if (npc == null) {
        player.sendMessage("§cNão existe um NPC de Status utilizando \"" + id + "\" como ID.");
        return;
      }
      
      StatsNPC.remove(npc);
      player.sendMessage("§cNPC de Status removido com sucesso.");
    } else {
      player.sendMessage(" \n§eAjuda - NPC Status\n \n§6/sw npcstatus adicionar [id] §f- §7Adicionar NPC.\n§6/sw npcstatus remover [id] §f- §7Remover NPC.\n ");
    }
  }
}
