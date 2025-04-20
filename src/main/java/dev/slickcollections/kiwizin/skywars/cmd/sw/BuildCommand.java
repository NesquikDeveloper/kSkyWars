package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class BuildCommand extends SubCommand {
  
  private static final List<String> BUILDERS = new ArrayList<>();
  
  public BuildCommand() {
    super("build", "build", "Ativar/Desativar modo construtor.", true);
  }
  
  public static void remove(Player player) {
    BUILDERS.remove(player.getName());
  }
  
  public static boolean hasBuilder(Player player) {
    return BUILDERS.contains(player.getName());
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (hasBuilder(player)) {
      BUILDERS.remove(player.getName());
      player.setGameMode(GameMode.ADVENTURE);
      player.sendMessage("§cModo construtor desativado.");
    } else {
      BUILDERS.add(player.getName());
      player.setGameMode(GameMode.CREATIVE);
      player.sendMessage("§aModo construtor ativado.");
    }
  }
}
