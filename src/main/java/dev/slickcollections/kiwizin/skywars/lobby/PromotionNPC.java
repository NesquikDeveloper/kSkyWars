package dev.slickcollections.kiwizin.skywars.lobby;

import dev.slickcollections.kiwizin.libraries.holograms.HologramLibrary;
import dev.slickcollections.kiwizin.libraries.holograms.api.Hologram;
import dev.slickcollections.kiwizin.libraries.npclib.NPCLibrary;
import dev.slickcollections.kiwizin.libraries.npclib.api.npc.NPC;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.skywars.Language;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.Promotion;
import dev.slickcollections.kiwizin.skywars.lobby.trait.NPCSkinTrait;
import dev.slickcollections.kiwizin.utils.BukkitUtils;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class PromotionNPC {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("npcs");
  private static final List<PromotionNPC> NPCS = new ArrayList<>();
  private String id;
  private Location location;
  private NPC npc;
  private Hologram hologram;
  
  public PromotionNPC(Location location, String id) {
    this.location = location;
    this.id = id;
    if (!this.location.getChunk().isLoaded()) {
      this.location.getChunk().load(true);
    }
    
    this.spawn();
  }
  
  public static void setupNPCs() {
    if (!CONFIG.contains("promotions")) {
      CONFIG.set("promotions", new ArrayList<>());
    }
    
    for (String serialized : CONFIG.getStringList("promotions")) {
      if (serialized.split("; ").length > 6) {
        String id = serialized.split("; ")[6];
        
        NPCS.add(new PromotionNPC(BukkitUtils.deserializeLocation(serialized), id));
      }
    }
    
    Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> listNPCs().forEach(PromotionNPC::update), 20, 20);
  }
  
  public static void add(String id, Location location) {
    NPCS.add(new PromotionNPC(location, id));
    List<String> list = CONFIG.getStringList("promotions");
    list.add(BukkitUtils.serializeLocation(location) + "; " + id);
    CONFIG.set("promotions", list);
  }
  
  public static void remove(PromotionNPC npc) {
    NPCS.remove(npc);
    List<String> list = CONFIG.getStringList("promotions");
    list.remove(BukkitUtils.serializeLocation(npc.getLocation()) + "; " + npc.getId());
    CONFIG.set("promotions", list);
    
    npc.destroy();
  }
  
  public static PromotionNPC getById(String id) {
    return NPCS.stream().filter(npc -> npc.getId().equals(id)).findFirst().orElse(null);
  }
  
  public static Collection<PromotionNPC> listNPCs() {
    return NPCS;
  }
  
  public void spawn() {
    if (this.npc != null) {
      this.npc.destroy();
      this.npc = null;
    }
    
    if (this.hologram != null) {
      HologramLibrary.removeHologram(this.hologram);
      this.hologram = null;
    }
    
    this.hologram = HologramLibrary.createHologram(this.location.clone().add(0, 0.5, 0));
    for (int index = Language.lobby$npc$promotion$hologram.size(); index > 0; index--) {
      this.hologram.withLine(Language.lobby$npc$promotion$hologram.get(index - 1));
    }
    
    this.npc = NPCLibrary.createNPC(EntityType.PLAYER, "§8[NPC] ");
    this.npc.data().set("promotion-npc", true);
    this.npc.data().set(NPC.HIDE_BY_TEAMS_KEY, true);
    this.npc.addTrait(new NPCSkinTrait(this.npc, Language.lobby$npc$promotion$skin$value, Language.lobby$npc$promotion$skin$signature));
    this.npc.spawn(this.location);
  }
  
  public void update() {
    int size = Language.lobby$npc$promotion$hologram.size();
    for (int index = size; index > 0; index--) {
      if (this.hologram.getLine(size - (index - 1)).getLine().contains("{promotions") && Promotion.size() < 1) {
        this.hologram.getLine(size - (index - 1)).despawn();
        continue;
      } else if (this.hologram.getLine(size - (index - 1)).getLine().contains("{promotions}") && Promotion.size() >= 1) {
        this.hologram.getLine(size - (index - 1)).spawn();
      }
      this.hologram.updateLine(size - (index - 1), Language.lobby$npc$promotion$hologram.get(index - 1).replace("{promotions}", StringUtils.formatNumber(Promotion.size())));
    }
  }
  
  public void destroy() {
    this.id = null;
    this.location = null;
    
    this.npc.destroy();
    this.npc = null;
    HologramLibrary.removeHologram(this.hologram);
    this.hologram = null;
  }
  
  public String getId() {
    return id;
  }
  
  public Location getLocation() {
    return this.location;
  }
}
