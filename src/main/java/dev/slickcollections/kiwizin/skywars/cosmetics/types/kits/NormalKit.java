package dev.slickcollections.kiwizin.skywars.cosmetics.types.kits;

import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.enums.EnumRarity;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.ItemStack;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;

public class NormalKit extends Kit {
  
  private static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger()).getModule("NORMAL_KITS");
  
  public NormalKit(long id, EnumRarity rarity, String permission, String name, List<Integer> slots, String icon, List<KitLevel> levels) {
    super(id, rarity, permission, name, slots, icon, levels);
  }
  
  public static void setupNormalKits() {
    File folder = new File("plugins/kSkyWars/cosmetics/kits");
    if (!folder.exists()) {
      folder.mkdirs();
      printAllFolderFiles(folder);
    }
    
    for (File kit : folder.listFiles()) {
      try {
        FileConfiguration configuration = YamlConfiguration.loadConfiguration(new InputStreamReader(new FileInputStream(kit), StandardCharsets.UTF_8));
        long id = configuration.getLong("id");
        String name = configuration.getString("name");
        String permission = configuration.getString("permission");
        List<Integer> slots = configuration.getIntegerList("slots");
        String icon = configuration.getString("icon");
        if (!configuration.contains("rarity")) {
          Object def = getAbsentProperty(kit.getName().replace(".yml", ""), "rarity");
          configuration.set("rarity", def == null ? "COMUM" : def);
          try {
            configuration.save(kit);
          } catch (IOException ignore) {
          }
        }
        
        List<KitLevel> kitLevels = new ArrayList<>();
        ConfigurationSection levels = configuration.getConfigurationSection("levels");
        for (String level : levels.getKeys(false)) {
          String levelName = levels.getString(level + ".name");
          double coins = levels.getDouble(level + ".coins");
          if (!levels.contains(level + ".cash")) {
            Object def = getAbsentProperty(kit.getName().replace(".yml", ""), "levels." + level + ".cash");
            levels.set(level + ".cash", def);
            try {
              configuration.save(kit);
            } catch (IOException ignore) {
            }
          }
          long cash = levels.getInt(level + ".cash");
          List<ItemStack> items = new ArrayList<>();
          for (String serialized : levels.getStringList(level + ".items")) {
            items.add(BukkitUtils.deserializeItemStack(serialized));
          }
          String desc = levels.getString(level + ".description");
          kitLevels.add(new KitLevel(levelName, coins, cash, items, desc));
        }
        
        if (kitLevels.isEmpty()) {
          Bukkit.getScheduler()
              .scheduleSyncDelayedTask(Main.getInstance(), () -> LOGGER.log(Level.INFO, "O kit \"" + kit.getName() + "\" nao possui niveis, nao sera adicionado."));
          continue;
        }
        
        new NormalKit(id, EnumRarity.fromName(configuration.getString("rarity")), permission, name, slots, icon, kitLevels);
      } catch (FileNotFoundException ex) {
        ex.printStackTrace();
      }
    }
  }
  
  private static void printAllFolderFiles(File folder) {
    for (String file : new String[] {"archer", "enchanter", "paladin", "mage"}) {
      Main.getInstance().getFileUtils().copyFile(Main.getInstance().getResource(file + ".yml"), new File(folder, file + ".yml"));
    }
  }
}
