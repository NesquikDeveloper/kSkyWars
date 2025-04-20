package dev.slickcollections.kiwizin.skywars.cosmetics;


public enum CosmeticType {
  KIT("Kit"),
  PERK("Habilidade"),
  KILL_EFFECT("Efeitos de Abate"),
  PROJECTILE_EFFECT("Efeitos de Projétil"),
  FALL_EFFECT("Efeitos de Queda"),
  TELEPORT_EFFECT("Efeitos de Teleporte"),
  CAGE("Jaulas"),
  DEATH_CRY("Gritos de Morte"),
  BALLOON("Balões"),
  DEATH_MESSAGE("Mensagens de Morte"),
  DEATH_HOLOGRAM("Hologramas de Morte"),
  WIN_ANIMATION("Comemorações de Vitória");
  
  private final String[] names;
  
  CosmeticType(String... names) {
    this.names = names;
  }
  
  public String getName(long index) {
    return this.names[(int) (index - 1)];
  }
}
