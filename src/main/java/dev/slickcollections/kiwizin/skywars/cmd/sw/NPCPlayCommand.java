package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.game.enums.SkyWarsMode;
import dev.slickcollections.kiwizin.skywars.lobby.PlayNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NPCPlayCommand extends SubCommand {
  
  public NPCPlayCommand() {
    super("npcjogar", "npcjogar", "Adicione/remova NPCs de Jogar.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage(" \n§eAjuda - NPC Jogar\n \n§6/sw npcjogar adicionar [id] [modo] §f- §7Adicionar NPC.\n§6/sw npcjogar remover [id] §f- §7Remover NPC.\n ");
      return;
    }
    
    String action = args[0];
    if (action.equalsIgnoreCase("adicionar")) {
      if (args.length <= 2) {
        player.sendMessage("§cUtilize /sw npcjogar adicionar [id] [solo/dupla]");
        return;
      }
      
      String id = args[1];
      if (PlayNPC.getById(id) != null) {
        player.sendMessage("§cJá existe um NPC de Jogar utilizando \"" + id + "\" como ID.");
        return;
      }
      
      SkyWarsMode mode = SkyWarsMode.fromName(args[2]);
      if (mode == null) {
        player.sendMessage("§cUtilize /sw npcjogar adicionar [id] [solo/dupla]");
        return;
      }
      
      Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
      location.setYaw(player.getLocation().getYaw());
      location.setPitch(player.getLocation().getPitch());
      PlayNPC.add(id, location, mode);
      player.sendMessage("§aNPC de Jogar adicionado com sucesso.");
    } else if (action.equalsIgnoreCase("remover")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw npcjogar remover [id]");
        return;
      }
      
      String id = args[1];
      PlayNPC npc = PlayNPC.getById(id);
      if (npc == null) {
        player.sendMessage("§cNão existe um NPC de Jogar utilizando \"" + id + "\" como ID.");
        return;
      }
      
      PlayNPC.remove(npc);
      player.sendMessage("§cNPC de Jogar removido com sucesso.");
    } else {
      player.sendMessage(" \n§eAjuda - NPC Jogar\n \n§6/sw npcjogar adicionar [id] [modo] §f- §7Adicionar NPC.\n§6/sw npcjogar remover [id] §f- §7Remover NPC.\n ");
    }
  }
}
