package dev.slickcollections.kiwizin.skywars.cmd.sw;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.Hotbar;
import dev.slickcollections.kiwizin.skywars.cmd.SubCommand;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.object.SkyWarsChest;
import dev.slickcollections.kiwizin.skywars.menus.MenuChestEdit;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import org.bukkit.entity.Player;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import java.util.HashMap;
import java.util.Map;

public class ChestCommand extends SubCommand {
  
  public static final Map<Player, Object[]> CHEST = new HashMap<>();
  
  public ChestCommand() {
    super("bau", "bau [nome] [criar/editar/alterar]", "Criar/Editar/Alterar tipo(s) de(os) bau(s).", true);
  }
  
  public static void handleClick(Profile profile, String display, PlayerInteractEvent evt) {
    Player player = profile.getPlayer();
    AbstractSkyWars game = AbstractSkyWars.getByWorldName(player.getWorld().getName());
    
    switch (display) {
      case "§aVarinha": {
        evt.setCancelled(true);
        SkyWarsChest chest = null;
        if (evt.getClickedBlock() != null) {
          chest = game.getConfig().getChest(evt.getClickedBlock());
        }
        
        if (evt.getAction().name().contains("CLICK_BLOCK")) {
          if (chest == null) {
            player.sendMessage("§cEste bloco não é considerado um baú dessa arena.");
            return;
          }
          
          if (evt.getAction().name().contains("LEFT")) {
            game.getConfig().changeChestType(chest, (SkyWarsChest.ChestType) CHEST.get(player)[1]);
            player.sendMessage(" \n§aVocê alterou o tipo do baú para: §f" + chest.getChestType() + "\n ");
          } else {
            player.sendMessage(" \n§aTipo do baú: §f" + chest.getChestType() + "\n ");
          }
        } else if (evt.getAction() == Action.RIGHT_CLICK_BLOCK) {
          if (chest == null) {
            player.sendMessage("");
          }
        } else {
          player.sendMessage("§cClique em um bloco.");
        }
        break;
      }
      case "§cSair": {
        evt.setCancelled(true);
        if (BuildCommand.hasBuilder(player)) {
          player.performCommand("sw build");
        }
        profile.setHotbar(Hotbar.getHotbarById("lobby"));
        profile.refresh();
        break;
      }
    }
  }
  
  @Override
  public void perform(Player player, String[] args) {
    if (args.length <= 1) {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
      return;
    }
    
    String action = args[1];
    if (action.equalsIgnoreCase("criar")) {
      SkyWarsChest.ChestType chestType = SkyWarsChest.ChestType.createChestType(args[0]);
      if (chestType == null) {
        player.sendMessage("§cJá existe um tipo de baú com este nome.");
        return;
      }
      
      player.sendMessage("§aTipo de baú criado.");
      player.performCommand("sw bau " + chestType.getName() + " editar");
    } else if (action.equalsIgnoreCase("editar")) {
      SkyWarsChest.ChestType chestType = SkyWarsChest.ChestType.getByName(args[0]);
      if (chestType == null) {
        player.sendMessage("§cTipo de baú não encontrado.");
        return;
      }
      
      if (!BuildCommand.hasBuilder(player)) {
        player.performCommand("sw build");
      }
      new MenuChestEdit(player, chestType);
    } else if (action.equalsIgnoreCase("alterar")) {
      SkyWarsChest.ChestType chestType = SkyWarsChest.ChestType.getByName(args[0]);
      if (chestType == null) {
        player.sendMessage("§cTipo de baú não encontrado.");
        return;
      }
      
      if (AbstractSkyWars.getByWorldName(player.getWorld().getName()) == null) {
        player.sendMessage("§cNão existe uma sala neste mundo.");
        return;
      }
      
      Object[] array = new Object[2];
      array[0] = player.getWorld();
      array[1] = chestType;
      CHEST.put(player, array);
      
      player.getInventory().clear();
      player.getInventory().setArmorContents(null);
      
      player.getInventory().setItem(0, BukkitUtils.deserializeItemStack(
          "BLAZE_ROD : 1 : nome>&aVarinha : desc>&7Clique com o botão esquerdo para\n&7alterar o tipo do baú.\n \n&7Clique com o botão direito para\n&7visualizar o tipo do baú."));
      player.getInventory().setItem(1, BukkitUtils.deserializeItemStack("STAINED_CLAY:14 : 1 : nome>&cSair"));
      
      player.updateInventory();
      if (!BuildCommand.hasBuilder(player)) {
        player.performCommand("sw build");
      }
      
      Profile.getProfile(player.getName()).setHotbar(null);
      player.sendMessage("§aUtilize a varinha para alterar e visualizar os tipos de baú.");
    } else {
      player.sendMessage("§cUtilize /sw " + this.getUsage());
    }
  }
}
