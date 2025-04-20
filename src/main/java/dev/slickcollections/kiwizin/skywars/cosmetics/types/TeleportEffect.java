package dev.slickcollections.kiwizin.skywars.cosmetics.types;

import dev.slickcollections.kiwizin.cash.CashManager;
import dev.slickcollections.kiwizin.game.GameState;
import dev.slickcollections.kiwizin.player.Profile;
import dev.slickcollections.kiwizin.player.role.Role;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.container.SelectedContainer;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.CosmeticType;
import dev.slickcollections.kiwizin.skywars.game.AbstractSkyWars;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumRarity;
import dev.slickcollections.kiwizin.utils.particles.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.ItemStack;

public class TeleportEffect extends Cosmetic implements Listener {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("TELEPORT_EFFECTS");
  private final String name;
  private final String icon;
  private final ParticleEffect particle;
  
  public TeleportEffect(long id, EnumRarity rarity, double coins, long cash, String permission, String name, String icon, ParticleEffect particle) {
    super(id, CosmeticType.TELEPORT_EFFECT, coins, permission);
    this.name = name;
    this.icon = icon;
    this.particle = particle;
    this.rarity = rarity;
    this.cash = cash;
    
    Bukkit.getPluginManager().registerEvents(this, Main.getInstance());
  }
  
  public static void setupTeleportEffects() {
    KConfig config = Main.getInstance().getConfig("cosmetics", "teleporteffects");
    
    for (String key : config.getKeys(false)) {
      long id = config.getInt(key + ".id");
      double coins = config.getDouble(key + ".coins");
      if (!config.contains(key + ".cash")) {
        config.set(key + ".cash", getAbsentProperty("teleporteffects", key + ".cash"));
      }
      long cash = config.getInt(key + ".cash", 0);
      String permission = config.getString(key + ".permission");
      String name = config.getString(key + ".name");
      String icon = config.getString(key + ".icon");
      if (!config.contains(key + ".rarity")) {
        config.set(key + ".rarity", getAbsentProperty("teleporteffects", key + ".rarity"));
      }
      ParticleEffect particle;
      try {
        particle = ParticleEffect.valueOf(config.getString(key + ".particle"));
      } catch (Exception ex) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), () -> LOGGER.warning("A partícula \"" + config.getString(key + ".particle") + "\" nao foi encontrada."));
        continue;
      }
      
      new TeleportEffect(id, EnumRarity.fromName(config.getString(key + ".rarity")), coins, cash, permission, name, icon, particle);
    }
  }
  
  @Override
  public String getName() {
    return this.name;
  }
  
  @EventHandler(priority = EventPriority.MONITOR)
  public void onPlayerTeleport(PlayerTeleportEvent evt) {
    if (evt.getCause() != null && evt.getCause() == PlayerTeleportEvent.TeleportCause.ENDER_PEARL) {
      Player player = evt.getPlayer();
      Profile profile = Profile.getProfile(player.getName());
      if (profile != null) {
        AbstractSkyWars game = profile.getGame(AbstractSkyWars.class);
        if (game != null && game.getState() == GameState.EMJOGO && !game.isSpectator(player) && isSelected(profile) && canBuy(player) && has(profile)) {
          for (int i = 0; i < 5; i++) {
            game.listPlayers().forEach(r -> getParticle().display((float) Math.floor(Math.random() * 2.0F), 0.0F, (float) Math.floor(Math.random() * 2.0F), 1.0F, 3, player.getLocation(), r));
          }
        }
      }
    }
  }
  
  public ParticleEffect getParticle() {
    return this.particle;
  }
  
  @Override
  public EnumRarity getRarity() {
    return this.rarity;
  }
  
  @Override
  public ItemStack getIcon(Profile profile) {
    double coins = profile.getCoins("kCoreSkyWars");
    long cash = profile.getStats("kCoreProfile", "cash");
    boolean has = this.has(profile);
    boolean canBuy = this.canBuy(profile.getPlayer());
    boolean isSelected = this.isSelected(profile);
    if (isSelected && !canBuy) {
      isSelected = false;
      profile.getAbstractContainer("kCoreSkyWars", "selected", SelectedContainer.class).setSelected(getType(), 0);
    }
    
    Role role = Role.getRoleByPermission(this.getPermission());
    String color = has ?
        (isSelected ? Language.cosmetics$color$selected : Language.cosmetics$color$unlocked) :
        (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) && canBuy ? Language.cosmetics$color$canbuy : Language.cosmetics$color$locked;
    String desc = (has && canBuy ?
        Language.cosmetics$teleport_effect$icon$has_desc$start.replace("{has_desc_status}", isSelected ? Language.cosmetics$icon$has_desc$selected : Language.cosmetics$icon$has_desc$select) :
        canBuy ?
            Language.cosmetics$teleport_effect$icon$buy_desc$start
                .replace("{buy_desc_status}", (coins >= this.getCoins() || (CashManager.CASH && cash >= this.getCash())) ? Language.cosmetics$icon$buy_desc$click_to_buy : Language.cosmetics$icon$buy_desc$enough) :
            Language.cosmetics$teleport_effect$icon$perm_desc$start
                .replace("{perm_desc_status}", (role == null ? Language.cosmetics$icon$perm_desc$common : Language.cosmetics$icon$perm_desc$role.replace("{role}", role.getName()))))
        .replace("{name}", this.name).replace("{rarity}", this.getRarity().getName()).replace("{coins}", StringUtils.formatNumber(this.getCoins())).replace("{cash}", StringUtils.formatNumber(this.getCash()));
    ItemStack item = BukkitUtils.deserializeItemStack(this.icon + " : nome>" + color + this.name + " : desc>" + desc);
    if (isSelected) {
      BukkitUtils.putGlowEnchantment(item);
    }
    
    return item;
  }
}
