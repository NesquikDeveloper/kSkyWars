package dev.slickcollections.kiwizin.skywars.cmd.sw;


import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Cage;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.entity.Player;

public class CageCommand extends SubCommand {
  
  public CageCommand() {
    super("cage", "cage [criar/deletar] [nome]", "Criar/Deletar cage.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length <= 1) {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    
    String action = args[0];
    String name = StringUtils.join(args, 1, "");
    if (action.equalsIgnoreCase("criar")) {
      if (Cosmetic.listByType(Cage.class).stream().anyMatch(c -> c.getName().equals(name))) {
        player.sendMessage("§cJá existe uma jaula com este nome.");
        return;
      }
      
      Cage.createCage(player, name);
      player.sendMessage("§aA jaula foi criada com base nos blocos ao seu redor.");
    } else if (action.equalsIgnoreCase("deletar")) {
      Cage cage = Cosmetic.listByType(Cage.class).stream().filter(c -> c.getName().equals(name)).findFirst().orElse(null);
      if (cage == null) {
        player.sendMessage("§cNão foi encontrada nenhuma jaula com este nome.");
        return;
      }
      
      Cage.removeCage(cage);
      player.sendMessage(
          " \n§cVocê removeu a jaula de ID " + cage.getId() + ".\n \n§6§lAVISO: §7É fato que se outra cage utilizar este ID e alguém tiver essa cage, ele terá ela automaticamente.\n ");
    } else {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
    }
  }
}