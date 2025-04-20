package dev.slickcollections.kiwizin.skywars;

import dev.slickcollections.kiwizin.plugin.config.KConfig;
import dev.slickcollections.kiwizin.plugin.config.KWriter;
import dev.slickcollections.kiwizin.plugin.logger.KLogger;
import dev.slickcollections.kiwizin.utils.StringUtils;
import org.bukkit.Bukkit;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;

@SuppressWarnings("rawtypes")
public class Language {
  
  public static final KLogger LOGGER = ((KLogger) Main.getInstance().getLogger())
      .getModule("LANGUAGE");
  private static final KConfig CONFIG = Main.getInstance().getConfig("language");~

  public static String a$b = "~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~/~";
  public static long scoreboards$scroller$every_tick = 1;
  public static List<String> scoreboards$scroller$titles = Arrays
      .asList("§a§lSKY WARS", "§6§lS§a§lKY WARS", "§f§lS§6§lK§a§lY WARS", "§f§lSK§6§lY §a§lWARS",
          "§f§lSKY §6§lW§a§lARS", "§f§lSKY W§6§lA§a§lRS", "§f§lSKY WA§6§lR§a§lS",
          "§f§lSKY WAR§6§lS", "§f§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§f§lSKY WARS", "§f§lSKY WARS",
          "§f§lSKY WARS", "§f§lSKY WARS", "§f§lSKY WARS", "§f§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§f§lSKY WARS", "§f§lSKY WARS", "§f§lSKY WARS", "§f§lSKY WARS", "§f§lSKY WARS",
          "§f§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS",
          "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS", "§a§lSKY WARS");
  public static String scoreboards$time$waiting = "Aguardando...";
  public static String scoreboards$time$starting = "Iniciando em §a{time}s";
  public static String scoreboards$ranking$empty = "§7Ninguém §f[0]";
  public static String scoreboards$ranking$format = "{name} §f[{kills}]";
  public static List<String> scoreboards$lobby = Arrays
      .asList("", " §8>§f Eliminações: §a%kCore_SkyWars_kills%",
          " §8>§f Vitórias: §a%kCore_SkyWars_wins%", " §8>§f Partidas: §a%kCore_SkyWars_games%", "",
          " §8>§f Cash: §b%kCore_cash%", " §8>§f Coins: §6%kCore_SkyWars_coins%", "", " &8- §7www.redeslick.com", "");
  public static List<String> scoreboards$waiting =
      Arrays
          .asList("", " §8>§f Mapa: §a{map}", " §8>§f Jogadores: §a{players}/{max_players}",
              "", "  {time}", "", " &8- §7www.redeslick.com", "");
  public static List<String> scoreboards$ingame$solo = Arrays
      .asList("", " §8>§f Próximo Evento:", " §8>§f §a{next_event}", "", " §8>§f Restantes: §a{players}", "",
          " §8>§f Abates: §a{kills}", "", " §8>§f Mapa: §a{map}", " §8>§f Modo: §a{mode}", "", " &8- §7www.redeslick.com", "");
  public static List<String> scoreboards$ingame$dupla = Arrays
      .asList("", " §8>§f Próximo Evento:", " §8>§f §a{next_event}", "", " §8>§f Restantes: §a{players}", " §8>§f Times Restantes: §a{teams}", "",
          " §8>§f Abates: §a{kills}", "", " §8>§f Mapa: §a{map}", " §8>§f Modo: §a{mode}", "", " &8- §7www.redeslick.com", "");
  public static String cosmetics$color$locked = "§a";
  public static String cosmetics$color$canbuy = "§e";
  public static String cosmetics$color$unlocked = "§a";
  public static String cosmetics$color$selected = "§6";
  public static String cosmetics$icon$perm_desc$common = "§cVocê não possui permissão.";
  public static String cosmetics$icon$perm_desc$role = "§7Exclusivo para {role} §7ou superior.";
  public static String cosmetics$icon$buy_desc$enough = "§cVocê não possui saldo suficiente.";
  public static String cosmetics$icon$buy_desc$click_to_buy = "§eClique para comprar!";
  public static String cosmetics$icon$has_desc$select = "§eClique para selecionar!";
  public static String cosmetics$icon$has_desc$selected = "§eClique para deselecionar!";
  public static String cosmetics$kit$icon$perm_desc$start = "\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$kit$icon$buy_desc$start = "\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$kit$icon$has_desc$start = "\n \n§fRaridade: {rarity}\n \n§eAdquirido!\n§eClique para customizar ou evoluir!";
  public static String cosmetics$perk$icon$perm_desc$start = "\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$perk$icon$buy_desc$start = "\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$perk$icon$has_desc$start = "\n \n§fRaridade: {rarity}\n \n§eAdquirido!\n§eClique para evoluir!";
  public static String cosmetics$kill_effect$icon$perm_desc$start = "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$kill_effect$icon$buy_desc$start =
      "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$kill_effect$icon$has_desc$start = "\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$projectile_effect$icon$perm_desc$start =
      "§7Quando você jogar um projétil\n§7sairá partículas de {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$projectile_effect$icon$buy_desc$start =
      "§7Quando você jogar um projétil\n§7sairá partículas de {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$projectile_effect$icon$has_desc$start =
      "§7Quando você jogar um projétil\n§7sairá partículas de {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$fall_effect$icon$perm_desc$start =
      "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$fall_effect$icon$buy_desc$start =
      "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$fall_effect$icon$has_desc$start =
      "§7Quando você levar dano de queda\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$teleport_effect$icon$perm_desc$start =
      "§7Quando você se teleportar\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$teleport_effect$icon$buy_desc$start =
      "§7Quando você se teleportar\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$teleport_effect$icon$has_desc$start =
      "§7Quando você se teleportar\n§7sairá partículas de {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$deathcry$icon$perm_desc$start =
      "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§6Clique direito para escutar!\n  \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$deathcry$icon$buy_desc$start =
      "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n§6Clique direito para escutar!\n \n{buy_desc_status}";
  public static String cosmetics$deathcry$icon$has_desc$start =
      "§7Quando você morrer tocará\n§7o grito de morte {name}.\n \n§6Clique direito para escutar!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$deathmessage$icon$perm_desc$start =
      "\n§6Clique direito para ver!\n \n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$deathmessage$icon$buy_desc$start =
      "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$deathmessage$icon$has_desc$start =
      "\n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$deathhologram$icon$perm_desc$start =
      "§7Quando você morrer spawnará\n§7um holograma de morte {name}.\n \n§6Clique direito para ver!\n \n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$deathhologram$icon$buy_desc$start =
      "§7Quando você morrer spawnará\n§7um holograma de morte {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$deathhologram$icon$has_desc$start =
      "§7Quando você morrer spawnará\n§7um holograma de morte {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$cage$icon$perm_desc$start =
      "§7Utilize a jaula {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$cage$icon$buy_desc$start =
      "§7Utilize a jaula {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins\n \n{buy_desc_status}";
  public static String cosmetics$cage$icon$has_desc$start =
      "§7Utilize a jaula {name}.\n \n§6Clique direito para ver!\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$balloon$icon$perm_desc$start = "§7Altera o balão decorativo da ilha\n§7para {name}.\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$balloon$icon$buy_desc$start =
      "§7Altera o balão decorativo da ilha\n§7para {name}.\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$balloon$icon$has_desc$start = "§7Altera o balão decorativo da ilha\n§7para {name}.\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String cosmetics$win_animation$icon$perm_desc$start = "\n \n§fRaridade: {rarity}\n \n{perm_desc_status}";
  public static String cosmetics$win_animation$icon$buy_desc$start = "\n \n§fRaridade: {rarity}\n§fCusto: §6{coins} Coins §7ou §b{cash} Cash\n \n{buy_desc_status}";
  public static String cosmetics$win_animation$icon$has_desc$start = "\n \n§fRaridade: {rarity}\n \n{has_desc_status}";
  public static String chat$delay = "§cAguarde mais {time}s para falar novamente.";
  public static String chat$color$default = "§7";
  public static String chat$color$custom = "§f";
  public static String chat$format$lobby = "{league} {player}{color}: {message}";
  public static String chat$format$spectator = "§8[Espectador] {player}{color}: {message}";
  public static int options$coins$wins = 50;
  public static int options$coins$kills = 5;
  public static int options$coins$clan$wins = 50;
  public static int options$coins$clan$kills = 15;
  public static int options$coins$clan$play = 5;
  public static int options$points$wins = 60;
  public static int options$points$kills = 20;
  public static int options$start$waiting = 45;
  public static int options$start$full = 10;
  @KWriter.YamlEntryInfo(annotation = "Existem dois tipos de Regeneração de Arena: WorldReload e BlockRegen.\nÉ recomendável que teste os dois e veja qual se sai melhor.")
  public static boolean options$regen$world_reload = true;
  @KWriter.YamlEntryInfo(annotation = "Quantos blocos serão regenerados por tick no BlockRegen")
  public static int options$regen$block_regen$per_tick = 20000;
  public static String options$events$refill = "Reabastecimento";
  public static String options$events$end = "Fim de Jogo";
  @KWriter.YamlEntryInfo(annotation = "Se você não definir o evento FIM a partida não terá fim por tempo.\nEventos disponíveis:\nFIM:tempo_em_segundos\nANUNCIO(anuncio de minutos restante):tempo_em_segundos\nREFILL:tempo_em_segundos")
  public static List<String> options$events$solo$timings = Arrays
      .asList("REFILL:300", "REFILL:480", "ANUNCIO:540", "FIM:840");
  public static List<String> options$events$ranked$timings = Arrays
      .asList("REFILL:300", "REFILL:480", "ANUNCIO:540", "FIM:840");
  public static List<String> options$events$dupla$timings = Arrays
      .asList("REFILL:300", "REFILL:480", "ANUNCIO:540", "FIM:840");
  public static String lobby$achievement = " \n§aVocê completou o desafio §f{name}\n ";
  public static String lobby$broadcast = "{player} §6entrou no lobby!";
  public static boolean lobby$tab$enabled = true;
  public static String lobby$tab$header = " \n§b§lREDE SLICK\n  §fredeslick.com\n ";
  public static String lobby$tab$footer =
      " \n \n§aForúm: §fredeslick.com/forum\n§aTwitter: §f@RedeSlick\n§aDiscord: §fredeslick.com/discord\n \n                                          §bAdquira VIP acessando: §floja.redeslick.com                                          \n ";
  public static long lobby$leaderboard$minutes = 30;
  public static String lobby$leaderboard$empty = "§7Ninguém";
  public static List<String> lobby$leaderboard$wins$hologram = Arrays
      .asList("{monthly_color}Mensal {total_color}Total", "§6§lClique para alternar!", "§a10. {name_10} §7- §a{stats_10}", "§a9. {name_9} §7- §a{stats_9}",
          "§a8. {name_8} §7- §a{stats_8}", "§a7. {name_7} §7- §a{stats_7}",
          "§a6. {name_6} §7- §a{stats_6}",
          "§a5. {name_5} §7- §a{stats_5}", "§a4. {name_4} §7- §a{stats_4}",
          "§a3. {name_3} §7- §a{stats_3}", "§a2. {name_2} §7- §a{stats_2}",
          "§a1. {name_1} §7- §a{stats_1}", "",
          "§7Ranking de Vitórias", "§f§lTodos os Modos");
  public static List<String> lobby$leaderboard$kills$hologram = Arrays
      .asList("{monthly_color}Mensal {total_color}Total", "§6§lClique para alternar!", "§a10. {name_10} §7- §a{stats_10}", "§a9. {name_9} §7- §a{stats_9}",
          "§a8. {name_8} §7- §a{stats_8}", "§a7. {name_7} §7- §a{stats_7}",
          "§a6. {name_6} §7- §a{stats_6}",
          "§a5. {name_5} §7- §a{stats_5}", "§a4. {name_4} §7- §a{stats_4}",
          "§a3. {name_3} §7- §a{stats_3}", "§a2. {name_2} §7- §a{stats_2}",
          "§a1. {name_1} §7- §a{stats_1}", "",
          "§7Ranking de Abates", "§f§lTodos os Modos");
  public static List<String> lobby$leaderboard$points$hologram = Arrays
      .asList("§a10. {name_10} §7- §a{stats_10}", "§a9. {name_9} §7- §a{stats_9}",
          "§a8. {name_8} §7- §a{stats_8}", "§a7. {name_7} §7- §a{stats_7}",
          "§a6. {name_6} §7- §a{stats_6}",
          "§a5. {name_5} §7- §a{stats_5}", "§a4. {name_4} §7- §a{stats_4}",
          "§a3. {name_3} §7- §a{stats_3}", "§a2. {name_2} §7- §a{stats_2}",
          "§a1. {name_1} §7- §a{stats_1}", "",
          "§7Ranking de Pontos", "§f§lRanqueado");
  public static String lobby$npc$play$connect = "§aConectando...";
  public static String lobby$npc$play$menu$info$item = "PAPER : 1 : nome>§aInformações : desc>{desc}";
  public static String lobby$npc$play$menu$info$desc_limit =
      "§fLimite Diário: §7{limit}\n \n§7Jogadores que possuem o grupo §aVIP §7ou\n§7superior, podem escolher o mapa sem\n§7limite algum.\n \n&7www.redeslick.com/loja";
  public static String lobby$npc$play$menu$info$desc_not_limit = "§7Você não possui limite diário de seleções.";
  public static String lobby$npc$deliveries$deliveries = "§c{deliveries} Entrega{s}";
  public static List<String> lobby$npc$deliveries$hologram = Arrays
      .asList("{deliveries}", "§bEntregador", "§e§lCLIQUE DIREITO");
  public static List<String> lobby$npc$promotion$hologram = Arrays
      .asList("§c{promotions} Itens em Promoção", "§bPromoções", "§e§lCLIQUE DIREITO");
  public static List<String> lobby$npc$stats$hologram = Arrays
      .asList("§6Estatísticas", "Total de Eliminações: §7%kCore_SkyWars_kills%", "Total de Vitórias: §7%kCore_SkyWars_wins%", "§e§lCLIQUE DIREITO");
  public static List<String> lobby$npc$play$solo$hologram = Arrays
      .asList("§bSolo", "§a{players} Jogadores");
  public static List<String> lobby$npc$play$dupla$hologram = Arrays
      .asList("§bDuplas", "§a{players} Jogadores");
  public static List<String> lobby$npc$play$ranked$hologram = Arrays
      .asList("§bRanked", "§a{players} Jogadores");
  public static String lobby$npc$promotion$skin$value =
      "ewogICJ0aW1lc3RhbXAiIDogMTYyMzI1Mzg3NzQwNSwKICAicHJvZmlsZUlkIiA6ICJhMDVkZWVjMDdhMGU0MDc2ODdjYmRjNWRjYWNhODU4NiIsCiAgInByb2ZpbGVOYW1lIiA6ICJWaWxsYWdlciIsCiAgInNpZ25hdHVyZVJlcXVpcmVkIiA6IHRydWUsCiAgInRleHR1cmVzIiA6IHsKICAgICJTS0lOIiA6IHsKICAgICAgInVybCIgOiAiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS83YWY3YzA3ZDFkZWQ2MWIxZDMzMTI2ODViMzJlNDU2OGZmZGRhNzYyZWM4ZDgwODg5NWNjMzI5YTkzZDYwNmUwIgogICAgfQogIH0KfQ==";
  public static String lobby$npc$promotion$skin$signature =
      "yeeouiiK7vV/9o4BpBUz/UMoRWqkLE9GeLU/fbcXWFBycWw/z2CxX8UXdXrmg8IDKOA9ELTeeQQcuejo7dC0tsOGhgyeWPJR5jn8H3w24ZmeG154cQMvtTQW/fKuCcwFxj807VSBtGoEYqYjgaxYmou32dBCnPrB+L7RUf/kNO7wPhTj1zykwuhdXBNHS8G05ghFXmr9SN1CoctUhR5aK++CESn5qLVMhZAA81eCd29v0Tx65Kq6gSQpBU/dElFaVhCaQph5j/0XgDfYy5TOwK4tvhA18vpbHPGRXFg0Da873d0U5g9JqFcMMzD3DWq+J2XdhASyDjX4qlrn+0ShKtS2FIeN+soksLH+62pSuF+cNmvltPLktSlyp5tf6gy6Eox/MH6GANJ5O+NCDxxvqNli8z14zfM62nHJMIeDXGYyeZLyf35n9bHbIZnVmPjgp+/g0xyDq02jGOJvWmzlZe+WdhnDc6HeIIJFSkoY8GBUkPqhMTmCge6FhcahWQFxO0AXZysQevl+DlvwRkmcBTH3PgW6ryebcDWxZ7LdH6tg9RCO/aKwoi1fw06NczAOtLrz4zN1jvJD8tzaNtID78nYVPSCJWysMY5NnEVQdE6DglHs13Xe1kJtMK1Xs75j9WbTfrulLPVlbGAE4//odVKB7jhi2xtA7VUr45Y7I3Y=";
  public static String lobby$npc$deliveries$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1ODM0NTc4OTkzMTksInByb2ZpbGVJZCI6IjIxMWNhN2E4ZWFkYzQ5ZTVhYjBhZjMzMTBlODY0M2NjIiwicHJvZmlsZU5hbWUiOiJNYXh0ZWVyIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS85MWU0NTc3OTgzZjEzZGI2YTRiMWMwNzQ1MGUyNzQ2MTVkMDMyOGUyNmI0MGQ3ZDMyMjA3MjYwOWJmZGQ0YTA4IiwibWV0YWRhdGEiOnsibW9kZWwiOiJzbGltIn19fX0=";
  public static String lobby$npc$deliveries$skin$signature =
      "SXnMF3f9x90fa+FdP2rLk/V6/zvMNuZ0sC4RQpPHF9JxdVWYRZm/+DhxkfjCHWKXV/4FSTN8LPPsxXd0XlYSElpi5OaT9/LGhITSK6BbeBfaYhLZnoD0cf9jG9nl9av38KipnkNXI+cO3wttB27J7KHznAmfrJd5bxdO/M0aGQYtwpckchYUBG6pDzaxN7tr4bFxDdxGit8Tx+aow/YtYSQn4VilBIy2y/c2a4PzWEpWyZQ94ypF5ZojvhaSPVl88Fbh+StdgfJUWNN3hNWt31P68KT4Jhx+SkT2LTuDj0jcYsiuxHP6AzZXtOtPPARqM0/xd53CUHCK+TEF5mkbJsG/PZYz/JRR1B1STk4D2cgbhunF87V4NLmCBtF5WDQYid11eO0OnROSUbFduCLj0uJ6QhNRRdhSh54oES7vTi0ja3DftTjdFhPovDAXQxCn+ROhTeSxjW5ZvP6MpmJERCSSihv/11VGIrVRfj2lo9MaxRogQE3tnyMNKWm71IRZQf806hwSgHp+5m2mhfnjYeGRZr44j21zqnSKudDHErPyEavLF83ojuMhNqTTO43ri3MVbMGix4TbIOgB2WDwqlcYLezENBIIkRsYO/Y1r5BWCA7DJ5IlpxIr9TCu39ppVmOGReDWA/Znyox5GP6JIM53kQoTOFBM3QWIQcmXll4=";
  public static String lobby$npc$play$solo$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1MjM1Njk3MjI0OTgsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQwMTdhYmQ5ZjExZTlkZTM4ODBkNGM0OTAxODUzNTdiOGY4ZmY1NGM3MzA2Mzg2ZTgyYWQ1NjdhNTMwMzMifX19";
  public static String lobby$npc$play$solo$skin$signature =
      "i7k5tYkZ0CJ1hnGrGELLVXjIi0hfVVtg+c4a/iXP4wOwvAPj6tQtExFWgGaZYnYhN6ldcjJKUw13a/TRwHi4er4OceOlxBgqSvc0zzT7U4iZsEUuCwv7r9t6a+3MELqSQe3/bbX6WP6pDA9TRSVWaCTGpBtZfAYyrszk+VTowMjKrDB7r/kzrhE+h2rSozVcv4fUMGOd4m8xbTPlcvBatZ9OcHfZEpuoTpECUq3tWH3GIJi+Uxz3rTVl5rKJdKLOeUVXLpiLSgQ0jybMy705WlB0NWFbWFkY0mEQU7yca6keopEsGaQ+36yEtcE4hKYhibqW2sFhne/wIZh5arwyXVv/04twL/dpdiBwg4nqGEO60i+tQoF9RVWeCmIwJizEn3+WO6H2QogfCy+W1vNO65/HoHlhVbC6Y6nkUUQ8r0jtqz/sBQVAEBhFDjOQcdFucyjnO4LXrZPajdzJtBhkottBZDQZQlbFoZxC47WpQ+sktc51SWT2f3BzMowRKg08R8xpZxMTf+bB5OldilMuDPggXF/wVQU4+N9OFo1qYNxRPtM/7DCP8dtS7pwfhJkRhnQOfBVu7/mkNX1EM3mlMRzhEiUmqXfhL3SSyzTzqdTB76JgrRF92zuW+ouUlnXHe4hWiaWvRQ1XHB4fc+HOQ6/1RMYb4NItJFte1tjcQQs=";
  public static String lobby$npc$play$dupla$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1MjM1Njk3MjI0OTgsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQwMTdhYmQ5ZjExZTlkZTM4ODBkNGM0OTAxODUzNTdiOGY4ZmY1NGM3MzA2Mzg2ZTgyYWQ1NjdhNTMwMzMifX19";
  public static String lobby$npc$play$dupla$skin$signature =
      "i7k5tYkZ0CJ1hnGrGELLVXjIi0hfVVtg+c4a/iXP4wOwvAPj6tQtExFWgGaZYnYhN6ldcjJKUw13a/TRwHi4er4OceOlxBgqSvc0zzT7U4iZsEUuCwv7r9t6a+3MELqSQe3/bbX6WP6pDA9TRSVWaCTGpBtZfAYyrszk+VTowMjKrDB7r/kzrhE+h2rSozVcv4fUMGOd4m8xbTPlcvBatZ9OcHfZEpuoTpECUq3tWH3GIJi+Uxz3rTVl5rKJdKLOeUVXLpiLSgQ0jybMy705WlB0NWFbWFkY0mEQU7yca6keopEsGaQ+36yEtcE4hKYhibqW2sFhne/wIZh5arwyXVv/04twL/dpdiBwg4nqGEO60i+tQoF9RVWeCmIwJizEn3+WO6H2QogfCy+W1vNO65/HoHlhVbC6Y6nkUUQ8r0jtqz/sBQVAEBhFDjOQcdFucyjnO4LXrZPajdzJtBhkottBZDQZQlbFoZxC47WpQ+sktc51SWT2f3BzMowRKg08R8xpZxMTf+bB5OldilMuDPggXF/wVQU4+N9OFo1qYNxRPtM/7DCP8dtS7pwfhJkRhnQOfBVu7/mkNX1EM3mlMRzhEiUmqXfhL3SSyzTzqdTB76JgrRF92zuW+ouUlnXHe4hWiaWvRQ1XHB4fc+HOQ6/1RMYb4NItJFte1tjcQQs=";
  public static String lobby$npc$play$ranked$skin$value =
      "eyJ0aW1lc3RhbXAiOjE1MjM1Njk3MjI0OTgsInByb2ZpbGVJZCI6IjdiM2QxNGQ2YzExZDRjODA5NTc1ZjI5ODczNGE0ZDFiIiwicHJvZmlsZU5hbWUiOiJUYWxvbkRldiIsInNpZ25hdHVyZVJlcXVpcmVkIjp0cnVlLCJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUvMmQwMTdhYmQ5ZjExZTlkZTM4ODBkNGM0OTAxODUzNTdiOGY4ZmY1NGM3MzA2Mzg2ZTgyYWQ1NjdhNTMwMzMifX19";
  public static String lobby$npc$play$ranked$skin$signature =
      "i7k5tYkZ0CJ1hnGrGELLVXjIi0hfVVtg+c4a/iXP4wOwvAPj6tQtExFWgGaZYnYhN6ldcjJKUw13a/TRwHi4er4OceOlxBgqSvc0zzT7U4iZsEUuCwv7r9t6a+3MELqSQe3/bbX6WP6pDA9TRSVWaCTGpBtZfAYyrszk+VTowMjKrDB7r/kzrhE+h2rSozVcv4fUMGOd4m8xbTPlcvBatZ9OcHfZEpuoTpECUq3tWH3GIJi+Uxz3rTVl5rKJdKLOeUVXLpiLSgQ0jybMy705WlB0NWFbWFkY0mEQU7yca6keopEsGaQ+36yEtcE4hKYhibqW2sFhne/wIZh5arwyXVv/04twL/dpdiBwg4nqGEO60i+tQoF9RVWeCmIwJizEn3+WO6H2QogfCy+W1vNO65/HoHlhVbC6Y6nkUUQ8r0jtqz/sBQVAEBhFDjOQcdFucyjnO4LXrZPajdzJtBhkottBZDQZQlbFoZxC47WpQ+sktc51SWT2f3BzMowRKg08R8xpZxMTf+bB5OldilMuDPggXF/wVQU4+N9OFo1qYNxRPtM/7DCP8dtS7pwfhJkRhnQOfBVu7/mkNX1EM3mlMRzhEiUmqXfhL3SSyzTzqdTB76JgrRF92zuW+ouUlnXHe4hWiaWvRQ1XHB4fc+HOQ6/1RMYb4NItJFte1tjcQQs=";
  public static String ingame$broadcast$join = "{player} §eentrou na partida! §a[{players}/{max_players}]";
  public static String ingame$broadcast$leave = "{player} §csaiu da partida! §a[{players}/{max_players}]";
  public static String ingame$broadcast$starting = "§aO jogo começa em §f{time} §asegundo{s}.";
  public static String ingame$broadcast$suicide = "{name} §emorreu sozinho.";
  public static String ingame$broadcast$default_killed_message = "{name} §efoi abatido por {killer}";
  public static String ingame$broadcast$double_kill = "§e. §e§lDOUBLE KILL";
  public static String ingame$broadcast$triple_kill = "§e. §b§lTRIPLE KILL";
  public static String ingame$broadcast$quadra_kill = "§e. §6§lQUADRA KILL";
  public static String ingame$broadcast$monster_kill = "§e. §c§lMOOONSTER KILL";
  public static String ingame$broadcast$end = " \n§aO tempo acabou, não houve ganhadores.\n ";
  public static String ingame$broadcast$win$solo = " \n{name} §avenceu a partida!\n ";
  public static String ingame$broadcast$win$dupla = " \n{name} §avenceram a partida!\n ";
  public static String ingame$actionbar$killed = "§aRestam §c{alive} §ajogadores!";
  public static String ingame$actionbar$kitselected = "§eKit selecionado: §a{kit}";
  public static String ingame$titles$end$header = "";
  public static String ingame$titles$end$footer = "§aRestam {time} minuto{s}";
  public static String ingame$titles$refill$header = "";
  public static String ingame$titles$refill$footer = "§aOs baús foram reabastecidos";
  public static String ingame$titles$border$header = "§c§lAVISO";
  public static String ingame$titles$border$footer = "§aVocê está saindo da borda!";
  public static String ingame$titles$death$header = "§c§lVOCE MORREU";
  public static String ingame$titles$death$footer = "§7Você agora é um espectador";
  public static String ingame$titles$win$header = "§a§lVITÓRIA";
  public static String ingame$titles$win$footer = "§7Você é o último de pé";
  public static String ingame$titles$lose$header = "§c§lFIM DE JOGO";
  public static String ingame$titles$lose$footer = "§7Você não foi vitorioso dessa vez";
  public static String ingame$messages$bow$hit = "{name} §aestá com §c{hp} §ade HP.";
  public static String ingame$messages$coins$base = " \n  §a{coins} coins ganhos nesta partida:\n {coins_win}{coins_kills}\n ";
  public static String ingame$messages$coins$win = "\n       §a+{coins} §fpor vencer o jogo";
  public static String ingame$messages$coins$kills = "\n       §a+{coins} §fpor realizar §c{kills} §fabate{s}";
  public static String ingame$messages$points$base = "\n  \n §a{points} pontos ganhos nesta partida:\n {points_win}{points_kills}\n ";
  public static String ingame$messages$points$win = "\n       §a+{points} §fpor vencer o jogo";
  public static String ingame$messages$points$kills = "\n       §a+{points} §fpor realizar §c{kills} §fabate{s}";
  
  public static void setupLanguage() {
    boolean save = false;
    KWriter writer = Main.getInstance().getWriter(CONFIG.getFile(),
        "kSkyWars - Criado por Kiwizin\nVersão da configuração: " + Main.getInstance()
            .getDescription().getVersion());
    for (Field field : Language.class.getDeclaredFields()) {
      if (field.getName().contains("$") && !Modifier.isFinal(field.getModifiers())) {
        String nativeName = field.getName().replace("$", ".").replace("_", "-");
        
        try {
          Object value;
          KWriter.YamlEntryInfo entryInfo = field.getAnnotation(KWriter.YamlEntryInfo.class);
          
          if (CONFIG.contains(nativeName)) {
            value = CONFIG.get(nativeName);
            if (value instanceof String) {
              value = StringUtils.formatColors((String) value).replace("\\n", "\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.formatColors((String) v).replace("\\n", "\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            field.set(null, value);
            writer.set(nativeName, new KWriter.YamlEntry(
                new Object[]{entryInfo == null ? "" : entryInfo.annotation(),
                    CONFIG.get(nativeName)}));
          } else {
            value = field.get(null);
            if (value instanceof String) {
              value = StringUtils.deformatColors((String) value).replace("\n", "\\n");
            } else if (value instanceof List) {
              List l = (List) value;
              List<Object> list = new ArrayList<>(l.size());
              for (Object v : l) {
                if (v instanceof String) {
                  list.add(StringUtils.deformatColors((String) v).replace("\n", "\\n"));
                } else {
                  list.add(v);
                }
              }
              
              value = list;
            }
            
            save = true;
            writer.set(nativeName, new KWriter.YamlEntry(
                new Object[]{entryInfo == null ? "" : entryInfo.annotation(), value}));
          }
        } catch (ReflectiveOperationException e) {
          LOGGER.log(Level.WARNING, "Unexpected error on settings file: ", e);
        }
      }
    }
    if (save) {
      writer.write();
      CONFIG.reload();
      Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getInstance(),
          () -> LOGGER.info("A config §6language.yml §afoi modificada ou criada."));
    }
  }
}