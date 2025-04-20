package dev.slickcollections.kiwizin.skywars.game.object;

import dev.slickcollections.kiwizin.game.GameState;
import dev.slickcollections.kiwizin.nms.NMS;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.AbstractExecutor;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.WinAnimation;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.game.SkyWarsEvent;
import dev.slickcollections.kiwizin.skywars.game.SkyWarsTeam;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumSound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SkyWarsTask {
  
  private final AbstractSkyWars game;
  private BukkitTask task;
  
  public SkyWarsTask(AbstractSkyWars game) {
    this.game = game;
  }
  
  public void cancel() {
    if (this.task != null) {
      this.task.cancel();
      this.task = null;
    }
  }
  
  public void reset() {
    this.cancel();
    this.task = new BukkitRunnable() {
      @Override
      public void run() {
        if (game.getTimer() == 0) {
          game.start();
          return;
        }
        
        game.listPlayers().forEach(player -> {
          Profile profile = Profile.getProfile(player.getName());
          Kit kit = profile.getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).getSelected(CosmeticType.KIT, Kit.class, game.getMode().getCosmeticIndex());
          
          NMS.sendActionBar(player, Language.ingame$actionbar$kitselected.replace("{kit}", kit == null ? "Nenhum" : kit.getName()));
        });
        
        if (game.getOnline() < game.getConfig().getMinPlayers()) {
          if (game.getTimer() != (Language.options$start$waiting + 1)) {
            game.setTimer(Language.options$start$waiting + 1);
          }
          
          game.listPlayers().forEach(player -> Profile.getProfile(player.getName()).update());
          return;
        }
        
        if (game.getTimer() == (Language.options$start$waiting + 1)) {
          game.setTimer(Language.options$start$waiting);
        }
        
        game.listPlayers().forEach(player -> {
          Profile.getProfile(player.getName()).update();
          if (game.getTimer() == 10 || game.getTimer() <= 5) {
            EnumSound.CLICK.play(player, 0.5F, 2.0F);
          }
        });
        
        if (game.getTimer() == 30 || game.getTimer() == 15 || game.getTimer() == 10 || game.getTimer() <= 5) {
          game.broadcastMessage(Language.ingame$broadcast$starting.replace("{time}", StringUtils.formatNumber(game.getTimer())).replace("{s}", game.getTimer() > 1 ? "s" : ""));
        }
        
        game.setTimer(game.getTimer() - 1);
      }
    }.runTaskTimer(Main.getInstance(), 0, 20);
  }
  
  public void swap(SkyWarsTeam winners) {
    this.cancel();
    if (this.game.getState() == GameState.EMJOGO) {
      this.game.setTimer(0);
      this.game.getWorld().getEntities().stream().filter(entity -> !(entity instanceof Player)).forEach(Entity::remove);
      this.task = new BukkitRunnable() {
        @Override
        public void run() {
          Map.Entry<Integer, SkyWarsEvent> entry = game.getNextEvent();
          if (entry != null) {
            if (entry.getKey() == game.getTimer()) {
              entry.getValue().execute(game);
              game.generateEvent();
            }
          } else {
            game.generateEvent();
          }
          
          game.listPlayers().forEach(player -> {
            if (!game.getCubeId().contains(player.getLocation())) {
              if (game.isSpectator(player)) {
                player.teleport(game.getCubeId().getCenterLocation());
              } else if (player.getLocation().getY() > 1) {
                NMS.sendTitle(player, Language.ingame$titles$border$header, Language.ingame$titles$border$footer, 0, 30, 0);
                player.damage(2.0D);
              }
            }
            
            Profile.getProfile(player.getName()).update();
          });
          game.setTimer(game.getTimer() + 1);
          game.listChests().forEach(SkyWarsChest::update);
        }
      }.runTaskTimer(Main.getInstance(), 0, 20);
    } else if (this.game.getState() == GameState.ENCERRADO) {
      this.game.setTimer(10);
      List<AbstractExecutor> executors = new ArrayList<>();
      if (winners != null) {
        winners.listPlayers().forEach(player -> executors.add(
            Profile.getProfile(player.getName()).getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).getSelected(CosmeticType.WIN_ANIMATION, WinAnimation.class)
                .execute(player)));
      }
      this.task = new BukkitRunnable() {
        @Override
        public void run() {
          if (game.getTimer() == 0) {
            executors.forEach(AbstractExecutor::cancel);
            executors.clear();
            game.listPlayers().forEach(player -> game.leave(Profile.getProfile(player.getName()), null));
            game.reset();
            return;
          }
          
          executors.forEach(executor -> {
            if (winners == null || !winners.listPlayers().contains(executor.getPlayer())) {
              return;
            }
            
            executor.tick();
          });
          
          game.setTimer(game.getTimer() - 1);
        }
      }.runTaskTimer(Main.getInstance(), 0, 20);
    }
  }
}
