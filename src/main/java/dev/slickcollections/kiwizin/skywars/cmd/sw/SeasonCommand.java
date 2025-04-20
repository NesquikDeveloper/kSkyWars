package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Seasons;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.TimeUtils;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class SeasonCommand extends SubCommand {
  
  
  public SeasonCommand() {
    super("season", "season [criar/deletar/finalizar]", "Criar/deletar/finalizar uma temporada.", true);
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length == 0) {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    String type = args[0];
    if (type.equalsIgnoreCase("criar")) {
      Seasons.createSeason(player);
    } else if (type.equalsIgnoreCase("deletar")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw " + this.getUsage() + " [id]");
        return;
      }
      int id = 1;
      try {
        id = Integer.parseInt(args[1]);
      } catch (NumberFormatException ex) {
        player.sendMessage("§cUtilize apenas números nos ids.");
        return;
      }
      int finalId = id;
      Seasons search = Seasons.listSeasons().stream().filter(a -> a.getId() == finalId).findFirst().orElse(null);
      if (search == null) {
        player.sendMessage("§cNenhuma temporada encontrada com o id '" + id + "'.");
        return;
      }
      Seasons.listSeasons().remove(search);
      Seasons.deleteSeason(player, search);
    } else if (type.equalsIgnoreCase("finalizar")) {
      if (args.length <= 1) {
        player.sendMessage("§cUtilize /sw " + this.getUsage() + " [id]");
        return;
      }
      int id = 1;
      try {
        id = Integer.parseInt(args[1]);
      } catch (NumberFormatException ex) {
        player.sendMessage("§cUtilize apenas números nos ids.");
        return;
      }
      int finalId = id;
      Seasons search = Seasons.listSeasons().stream().filter(m -> m.getId() == finalId).findFirst().orElse(null);
      if (search == null) {
        player.sendMessage("§cNenhuma temporada encontrada com o id '" + id + "'.");
        return;
      }
      search.setEnded(true);
      player.sendMessage("§aVocê finalizou a temporada '" + id + "'.");
    } else {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
    }
  }
}
