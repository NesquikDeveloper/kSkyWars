package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class BalloonsCommand extends SubCommand {
  
  public static final Map<Player, Object[]> BALLOONS = new HashMap<>();
  
  public BalloonsCommand() {
    super("baloes", "baloes", "Setar localização dos balões.", true);
  }
  
  public static void handleClick(Profile profile, String display, PlayerInteractEvent evt) {
    Player player = profile.getPlayer();
    AbstractSkyWars game = AbstractSkyWars.getByWorldName(player.getWorld().getName());
    
    switch (display) {
      case "§aSpawn do Balão": {
        evt.setCancelled(true);
        if (evt.getClickedBlock() != null) {
          BALLOONS.get(player)[1] = BukkitUtils.serializeLocation(evt.getClickedBlock().getLocation().clone().add(0.5, 0, 0.5));
          player.sendMessage("§aLocalização do balão foi salva.");
        } else {
          player.sendMessage("§cClique em um bloco.");
        }
        break;
      }
      case "§aConfirmar Spawn": {
        evt.setCancelled(true);
        if (BALLOONS.get(player)[1] == null) {
          player.sendMessage("§cSelecione a localização para o balão utilizando a cerca.");
          return;
        }
        
        game.getConfig().addBalloon((String) BALLOONS.get(player)[1]);
        BALLOONS.get(player)[1] = null;
        int nextId;
        for (nextId = 0; nextId <= game.listTeams().size(); nextId++) {
          if (game.getConfig().getBalloonLocation(nextId) == null) {
            break;
          }
        }
        
        if (nextId == game.listTeams().size()) {
          BALLOONS.remove(player);
          profile.setHotbar(Hotbar.getHotbarById("lobby"));
          profile.refresh();
          player.sendMessage("§aTodos os balões foram setados.");
          return;
        }
        
        player.teleport(game.listTeams().get(nextId).getLocation());
        EnumSound.NOTE_PLING.play(player, 1.0F, 1.0F);
        break;
      }
      case "§cCancelar": {
        evt.setCancelled(true);
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        profile.refresh();
        break;
      }
    }
  }
  
  @Override
  public void perform(Player player, String[] args) {
    AbstractSkyWars game = AbstractSkyWars.getByWorldName(player.getWorld().getName());
    if (game == null) {
      player.sendMessage("§cNão existe uma sala neste mundo.");
      return;
    }
    
    int nextId;
    for (nextId = 0; nextId <= game.listTeams().size(); nextId++) {
      if (game.getConfig().getBalloonLocation(nextId) == null) {
        break;
      }
    }
    
    if (nextId == game.listTeams().size()) {
      player.sendMessage("§cEsta sala já está com todos balões setados.");
      return;
    }
    
    Object[] arr = new Object[2];
    arr[0] = player.getWorld();
    BALLOONS.put(player, arr);
    
    Location location = game.listTeams().get(nextId).getLocation();
    player.sendMessage(
        " \n §6§lMANUAL DE INSTRUÇõES\n \n §7Os itens em sua hotbar serão utilizados para setar o Spawn dos balões de cada ilha, toda vez que você confirmar um Spawn você será teletransportado para a ilha em que será requisitado o balão. Ou seja, sete o balão da ilha em que você se encontra utilizando a cerca e clique no \"Confirmar Spawn\", após isso você será teletransportado para o spawn seguinte.\n ");
    EnumSound.LEVEL_UP.play(player, 1.0F, 2.0F);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> {
      player.getInventory().clear();
      player.getInventory().setArmorContents(null);
      
      player.getInventory().setItem(0, BukkitUtils.deserializeItemStack("FENCE : 1 : nome>&aSpawn do Balão"));
      player.getInventory().setItem(1, BukkitUtils.deserializeItemStack("STAINED_CLAY:5 : 1 : nome>&aConfirmar Spawn"));
      
      player.getInventory().setItem(8, BukkitUtils.deserializeItemStack("BED : 1 : nome>&cCancelar"));
      
      player.updateInventory();
      
      player.teleport(location);
      Profile.getProfile(player.getName()).setHotbar(null);
      EnumSound.NOTE_PLING.play(player, 1.0F, 1.0F);
    }, 40);
  }
}
