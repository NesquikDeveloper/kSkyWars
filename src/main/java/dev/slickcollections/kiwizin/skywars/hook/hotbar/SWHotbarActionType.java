package dev.slickcollections.kiwizin.skywars.hook.hotbar;

import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.hotbar.HotbarActionType;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.kits.NormalKit;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.skywars.menus.MenuLobbies;
import dev.slickcollections.kiwizin.skywars.menus.MenuPlay;
import dev.slickcollections.kiwizin.skywars.menus.MenuShop;
import dev.slickcollections.kiwizin.skywars.menus.MenuSpectator;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.kits.MenuSelectKit;
import dev.slickcollections.kiwizin.skywars.menus.cosmetics.perks.MenuSelectPerk;

public class SWHotbarActionType extends HotbarActionType {
  
  @Override
  public void execute(Profile profile, String action) {
    if (action.equalsIgnoreCase("loja")) {
      new MenuShop(profile);
    } else if (action.equalsIgnoreCase("lobbies")) {
      new MenuLobbies(profile);
    } else if (action.equalsIgnoreCase("kits")) {
      new MenuSelectKit<>(profile, NormalKit.class);
    } else if (action.equalsIgnoreCase("habilidades")) {
      new MenuSelectPerk<>(profile, Perk.class);
    } else if (action.equalsIgnoreCase("espectar")) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        new MenuSpectator(profile.getPlayer(), game);
      }
    } else if (action.equalsIgnoreCase("jogar")) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        new MenuPlay(profile, game.getMode());
      }
    } else if (action.equalsIgnoreCase("sair")) {
      AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
      if (game != null) {
        game.leave(profile, null);
      }
    }
  }
}
