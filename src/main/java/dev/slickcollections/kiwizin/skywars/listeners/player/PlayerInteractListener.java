package dev.slickcollections.kiwizin.skywars.listeners.player;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.game.GameState;
import dev.slickcollections.kiwizin.libraries.npclib.api.event.NPCLeftClickEvent;
import dev.slickcollections.kiwizin.libraries.npclib.api.event.NPCRightClickEvent;
import dev.slickcollections.kiwizin.libraries.npclib.api.npc.NPC;
import dev.slickcollections.kiwizin.menus.MenuDeliveries;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.cmd.sw.BalloonsCommand;
import dev.slickcollections.kiwizin.skywars.cmd.sw.BuildCommand;
import dev.slickcollections.kiwizin.skywars.cmd.sw.ChestCommand;
import dev.slickcollections.kiwizin.skywars.cmd.sw.CreateCommand;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.enums.SkyWarsMode;
import dev.slickcollections.kiwizin.skywars.game.object.SkyWarsChest;
import dev.slickcollections.kiwizin.skywars.menus.MenuPlay;
import dev.slickcollections.kiwizin.skywars.menus.MenuPromotionNPC;
import dev.slickcollections.kiwizin.skywars.menus.MenuStatsNPC;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import net.minecraft.server.v1_8_R3.DamageSource;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Chest;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;

import static dev.slickcollections.kiwizin.skywars.cmd.sw.BalloonsCommand.BALLOONS;
import static dev.slickcollections.kiwizin.skywars.cmd.sw.ChestCommand.CHEST;
import static dev.slickcollections.kiwizin.skywars.cmd.sw.CreateCommand.CREATING;

public class PlayerInteractListener implements Listener {
  
  @EventHandler
  public void onNPCLeftClick(NPCLeftClickEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    
    if (profile != null) {
      NPC npc = evt.getNPC();
      if (npc.data().has("play-npc")) {
        new MenuPlay(profile, SkyWarsMode.fromName(npc.data().get("play-npc")));
      }
    }
  }
  
  @EventHandler
  public void onNPCRightClick(NPCRightClickEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    
    if (profile != null) {
      NPC npc = evt.getNPC();
      if (npc.data().has("play-npc")) {
        new MenuPlay(profile, SkyWarsMode.fromName(npc.data().get("play-npc")));
      } else if (npc.data().has("delivery-npc")) {
        new MenuDeliveries(profile);
      } else if (npc.data().has("promotion-npc")) {
        new MenuPromotionNPC(profile);
      } else if (npc.data().has("stats-npc")) {
        new MenuStatsNPC(profile);
      }
    }
  }
  
  @EventHandler(priority = EventPriority.LOWEST)
  public void onPlayerInteract(PlayerInteractEvent evt) {
    Player player = evt.getPlayer();
    Profile profile = Profile.getProfile(player.getName());
    
    if (profile != null) {
      if (CREATING.containsKey(player) && CREATING.get(player)[0].equals(player.getWorld())) {
        ItemStack item = player.getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
          CreateCommand.handleClick(profile, item.getItemMeta().getDisplayName(), evt);
        }
      } else if (CHEST.containsKey(player) && CHEST.get(player)[0].equals(player.getWorld())) {
        ItemStack item = player.getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
          ChestCommand.handleClick(profile, item.getItemMeta().getDisplayName(), evt);
        }
      } else if (BALLOONS.containsKey(player) && BALLOONS.get(player)[0].equals(player.getWorld())) {
        ItemStack item = player.getItemInHand();
        if (item != null && item.hasItemMeta() && item.getItemMeta().hasDisplayName()) {
          BalloonsCommand.handleClick(profile, item.getItemMeta().getDisplayName(), evt);
        }
      }
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game == null && !BuildCommand.hasBuilder(player)) {
        evt.setCancelled(true);
      } else if (game != null && (game.getState() != GameState.EMJOGO || game.isSpectator(player))) {
        if (!(game.getState() == GameState.AGUARDANDO)) {
          player.updateInventory();
          evt.setCancelled(true);
        } else if (game.getState() == GameState.AGUARDANDO && evt.getClickedBlock() != null && evt.getClickedBlock().getType() == Material.GOLD_PLATE) {
          evt.setCancelled(false);
          InventoryHolder ih = ((InventoryHolder) evt.getClickedBlock().getLocation().clone().subtract(0, 0.5, 0).getBlock().getState());
          // Limpar e adicionar os foguetes novamente.
          ih.getInventory().clear();
          for (int i = 0; i < 10; i++) {
            ih.getInventory().addItem(BukkitUtils.deserializeItemStack("FIREWORK : 64"));
          }
        } else if (game.getState() == GameState.AGUARDANDO && evt.getClickedBlock() != null && evt.getClickedBlock().getType() != Material.GOLD_PLATE) {
          evt.setCancelled(true);
        }
      } else if (game != null && game.getState() == GameState.EMJOGO && !game.isSpectator(player)) {
        if (evt.getClickedBlock() != null) {
          Block block = evt.getClickedBlock();
          if (evt.getAction().name().contains("RIGHT")) {
            if (block.getState() instanceof Chest) {
              SkyWarsChest chest = game.getConfig().getChest(block);
              if (chest != null && game.getNextEvent().getKey() != 0) {
                chest.createHologram();
              }
            }
          }
        }
      }
    }
  }
  
  @EventHandler
  public void onPlayerMove(PlayerMoveEvent evt) {
    if (evt.getTo().getBlockY() != evt.getFrom().getBlockY() && evt.getTo().getBlockY() < 0) {
      Player player = evt.getPlayer();
      Profile profile = Profile.getProfile(player.getName());
      
      if (profile != null) {
        AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
        if (game == null) {
          player.teleport(Core.getLobby());
        } else {
          if (game.getState() != GameState.EMJOGO || game.isSpectator(player)) {
            player.teleport(game.getCubeId().getCenterLocation());
          } else {
            ((CraftPlayer) player).getHandle().damageEntity(DamageSource.OUT_OF_WORLD, (float) player.getMaxHealth());
          }
        }
      }
    }
  }
}
