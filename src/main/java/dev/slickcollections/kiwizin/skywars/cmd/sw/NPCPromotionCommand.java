package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.lobby.DeliveryNPC;
import dev.slickcollections.kiwizin.skywars.lobby.PromotionNPC;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class NPCPromotionCommand extends SubCommand {
  
  public NPCPromotionCommand() {
    super("npcpromocoes", "npcpromocoes", "Adicione/remova NPCs de Promoções.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage(" \n§eAjuda\n \n§6/sw npcpromocoes adicionar [id] §f- §7Adicionar NPC.\n§6/sw npcpromocoes remover [id] §f- §7Remover NPC.\n ");
      return;
    }
    
    String action = args[0];
    if (action.equalsIgnoreCase("adicionar")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw npcpromocoes adicionar [id]");
        return;
      }
      
      String id = args[1];
      if (PromotionNPC.getById(id) != null) {
        player.sendMessage("§cJá existe um NPC de Promoções utilizando \"" + id + "\" como ID.");
        return;
      }
      
      Location location = player.getLocation().getBlock().getLocation().add(0.5, 0, 0.5);
      location.setYaw(player.getLocation().getYaw());
      location.setPitch(player.getLocation().getPitch());
      PromotionNPC.add(id, location);
      player.sendMessage("§aNPC de Promoções adicionado com sucesso.");
    } else if (action.equalsIgnoreCase("remover")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw npcpromocoes remover [id]");
        return;
      }
      
      String id = args[1];
      PromotionNPC npc = PromotionNPC.getById(id);
      if (npc == null) {
        player.sendMessage("§cNão existe um NPC de Promoções utilizando \"" + id + "\" como ID.");
        return;
      }
  
      PromotionNPC.remove(npc);
      player.sendMessage("§cNPC de Promoções removido com sucesso.");
    } else {
      player.sendMessage(" \n§eAjuda\n \n§6/sw npcpromocoes adicionar [id] §f- §7Adicionar NPC.\n§6/sw npcpromocoes remover [id] §f- §7Remover NPC.\n ");
    }
  }
}
