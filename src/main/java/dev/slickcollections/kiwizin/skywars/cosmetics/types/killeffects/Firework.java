package dev.slickcollections.kiwizin.skywars.cosmetics.types.killeffects;

import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.KillEffect;
import dev.slickcollections.kiwizin.skywars.nms.NMS;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumRarity;
import org.bukkit.Bukkit;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

import java.util.concurrent.ThreadLocalRandom;

public class Firework extends KillEffect {
  
  public Firework(ConfigurationSection section) {
    super(section.getLong("id"), EnumRarity.fromName(section.getString("rarity")), section.getDouble("coins"), section.getInt("cash"), section.getString("permission"), section.getString("name"), section.getString("icon"));
  }
  
  @Override
  public void execute(Player viewer, Location location) {
    org.bukkit.entity.Firework firework = NMS.createAttachedFirework(viewer, location);
    FireworkMeta meta = firework.getFireworkMeta();
    meta.setPower(1);
    meta.addEffect(FireworkEffect.builder()
        .with(FireworkEffect.Type.BALL)
        .withColor(BukkitUtils.COLORS.get(ThreadLocalRandom.current().nextInt(BukkitUtils.COLORS.size())).get(null), BukkitUtils.COLORS.get(ThreadLocalRandom.current().nextInt(BukkitUtils.COLORS.size())).get(null))
        .withFade(BukkitUtils.COLORS.get(ThreadLocalRandom.current().nextInt(BukkitUtils.COLORS.size())).get(null))
        .build());
    firework.setFireworkMeta(meta);
    Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(), firework::detonate, 8L);
  }
}
