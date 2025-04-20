package dev.slickcollections.kiwizin.skywars.cmd;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SpectateCommand extends Commands {
  
  public SpectateCommand() {
    super("assistir");
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (sender instanceof Player) {
      Player player = (Player) sender;
      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        if (!player.hasPermission("kskywars.cmd.spectate")) {
          player.sendMessage("§cVocê não possui permissão para utilizar esse comando.");
          return;
        }
        
        if (args.length == 0) {
          player.sendMessage("§cUtilize /assistir [jogador]");
          return;
        }
        
        Player target = Bukkit.getPlayerExact(args[0]);
        if (target == null || (profile = Profile.getProfile(target.getName())) == null) {
          player.sendMessage("§cUsuário não encontrado.");
          return;
        }
        
        if (!profile.playingGame()) {
          player.sendMessage("§cUsuário não se encontra em uma partida.");
          return;
        }
        
        player.sendMessage(Language.lobby$npc$play$connect);
        profile.getGame(AbstractSkyWars.class).spectate(player, target);
      }
    }
  }
}
