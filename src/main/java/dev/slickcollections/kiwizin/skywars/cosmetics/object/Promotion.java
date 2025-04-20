package dev.slickcollections.kiwizin.skywars.cosmetics.object;

import dev.slickcollections.kiwizin.Core;
import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cosmetics.Cosmetic;
import dev.slickcollections.kiwizin.skywars.cosmetics.object.kit.KitLevel;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Kit;
import dev.slickcollections.kiwizin.skywars.cosmetics.types.Perk;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class Promotion {
  
  private static final KConfig CONFIG = Main.getInstance().getConfig("promotions");
  
  public static Map<Kit, Integer> kitsPromotions;
  public static Map<Perk, Integer> perkPromotions;
  public static Map<Kit, Integer> kitsPromotionsCash;
  public static Map<Perk, Integer> perkPromotionsCash;
  
  public static long size() {
    List<Perk> perk = new ArrayList<>();
    List<Kit> cloned = new ArrayList<>();
    cloned.addAll(kitsPromotions.keySet().stream().filter(a -> !kitsPromotionsCash.containsKey(a)).collect(Collectors.toList()));
    cloned.addAll(kitsPromotionsCash.keySet());
    perk.addAll(perkPromotions.keySet().stream().filter(a -> !perkPromotionsCash.containsKey(a)).collect(Collectors.toList()));
    perk.addAll(perkPromotionsCash.keySet());
    
    return cloned.size() + perk.size();
  }
  
  public static void setupPromotions() {
    kitsPromotions = new HashMap<>();
    perkPromotions = new HashMap<>();
    kitsPromotionsCash = new HashMap<>();
    perkPromotionsCash = new HashMap<>();
  
    for (String key : CONFIG.getSection("kits").getKeys(false)) {
      int id = 1;
      try {
        id = Integer.parseInt(key);
      } catch (NumberFormatException ex) {
        return;
      }
      int finalId = id;
      Kit kit = Cosmetic.listByType(Kit.class).stream().filter(a -> a.getId() == finalId).findFirst().orElse(null);
      if (kit == null) {
        continue;
      }
      kitsPromotions.put(kit, CONFIG.getInt("kits." + key + ".promotion"));
      kitsPromotionsCash.put(kit, CONFIG.getInt("kits." + key + ".cash_promotion"));
    }
    for (String key : CONFIG.getSection("perks").getKeys(false)) {
      int id = 1;
      try {
        id = Integer.parseInt(key);
      } catch (NumberFormatException ex) {
        ex.printStackTrace();
        return;
      }
      int finalId = id;
      Perk kit = Cosmetic.listByType(Perk.class).stream().filter(a -> a.getId() == finalId).findFirst().orElse(null);
      if (kit == null) {
        continue;
      }
      perkPromotions.put(kit, CONFIG.getInt("perks." + key + ".promotion"));
      perkPromotionsCash.put(kit, CONFIG.getInt("perks." + key + ".cash_promotion"));
    }
  }
  
  public static double applyCashPromotion(double price, Cosmetic toApply) {
    if (hasPromotionCash(toApply)) {
      double a = getPromotionCash(toApply) / 100;
      return price - (a * price);
    }
    return price;
  }
  
  public static double getPromotionCash(Cosmetic c) {
    if (!hasPromotion(c)) {
      return 0D;
    }
    double promo = 0;
    if (c instanceof Kit) {
      promo = kitsPromotionsCash.get(c);
    }
    if (c instanceof Perk) {
      promo = perkPromotionsCash.get(c);
    }
    return promo;
  }
  
  public static boolean hasPromotionCash(Cosmetic c) {
    if ((c instanceof Kit || c instanceof Perk) && (kitsPromotionsCash.containsKey(c) || perkPromotionsCash.containsKey(c)) && (kitsPromotionsCash.get(c) != null && kitsPromotionsCash.get(c) < 1 || perkPromotionsCash.get(c) != null && perkPromotionsCash.get(c) < 1)) {
      return false;
    }
    if (c instanceof Kit) {
      return kitsPromotionsCash.containsKey(c);
    }
    if (!(c instanceof Perk)) {
      return false;
    }
    return perkPromotionsCash.containsKey(c);
  }
  
  public static double applyPromotion(double price, Cosmetic toApply) {
    if (hasPromotion(toApply)) {
      double a = getPromotion(toApply) / 100;
      return price - (a * price);
    }
    return price;
  }
  
  public static double getPromotion(Cosmetic c) {
    if (!hasPromotion(c)) {
      return 0D;
    }
    double promo = 0;
    if (c instanceof Kit) {
      promo = kitsPromotions.get(c);
    }
    if (c instanceof Perk) {
      promo = perkPromotions.get(c);
    }
    return promo;
  }
  
  public static boolean hasPromotion(Cosmetic c) {
    if ((c instanceof Kit || c instanceof Perk) && (kitsPromotions.containsKey(c) || perkPromotions.containsKey(c)) && (kitsPromotions.get(c) != null && kitsPromotions.get(c) < 1 || perkPromotions.get(c) != null && perkPromotions.get(c) < 1)) {
      return false;
    }
    if (c instanceof Kit) {
      return kitsPromotions.containsKey(c);
    }
    if (!(c instanceof Perk)) {
      return false;
    }
    return perkPromotions.containsKey(c);
  }
  
  /*
  
    public static double removePromotion(double price, Cosmetic toApply) {
    if (hasPromotion(toApply)) {
      double a = getPromotion(toApply) / 100;
      return price + (a * price);
    }
    return price;
  }
  
  public static double removePromotionCash(double price, Cosmetic toApply) {
    if (hasPromotionCash(toApply)) {
      double a = getPromotionCash(toApply) / 100;
      return price + (a * price);
    }
    return price;
  }
   */
}
