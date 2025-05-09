package dev.slickcollections.kiwizin.skywars.cmd;

import dev.slickcollections.kiwizin.skywars.Main;
import dev.slickcollections.kiwizin.skywars.cmd.sw.*;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.*;
import java.util.stream.Collectors;

public class SkyWarsCommand extends Commands {
  
  private final List<SubCommand> commands = new ArrayList<>();
  
  public SkyWarsCommand() {
    super("sw", "skywars");
    
    this.commands.add(new UpdateCommand());
    if (Main.kMysteryBox) {
      this.commands.add(new MysteryBoxCommand());
    }
    this.commands.add(new BuildCommand());
    this.commands.add(new SetSpawnCommand());
    this.commands.add(new CageCommand());
    this.commands.add(new StartCommand());
    this.commands.add(new CreateCommand());
    this.commands.add(new BalloonsCommand());
    this.commands.add(new CloneCommand());
    this.commands.add(new LoadCommand());
    this.commands.add(new UnloadCommand());
    this.commands.add(new TeleportCommand());
    this.commands.add(new SeasonCommand());
    this.commands.add(new GiveCommand());
    this.commands.add(new PreviewCommand());
    this.commands.add(new ChestCommand());
    this.commands.add(new LeaderboardCommand());
    this.commands.add(new NPCPlayCommand());
    this.commands.add(new NPCStatsCommand());
    this.commands.add(new NPCDeliveryCommand());
    this.commands.add(new NPCPromotionCommand());
  }
  
  @Override
  public void perform(CommandSender sender, String label, String[] args) {
    if (!sender.hasPermission("kskywars.cmd.skywars")) {
      sender.sendMessage("§6kSkyWars §bv" + Main.getInstance().getDescription().getVersion() + " §7Criado por §6Kiwizin§7.");
      return;
    }
    
    if (args.length == 0) {
      this.sendHelp(sender, 1);
      return;
    }
    
    try {
      this.sendHelp(sender, Integer.parseInt(args[0]));
    } catch (Exception ex) {
      SubCommand subCommand = this.commands.stream().filter(sc -> sc.getName().equalsIgnoreCase(args[0])).findFirst().orElse(null);
      if (subCommand == null) {
        this.sendHelp(sender, 1);
        return;
      }
      
      List<String> list = new ArrayList<>(Arrays.asList(args));
      list.remove(0);
      if (subCommand.onlyForPlayer()) {
        if (!(sender instanceof Player)) {
          sender.sendMessage("§cEsse comando pode ser utilizado apenas pelos jogadores.");
          return;
        }
        
        subCommand.perform((Player) sender, list.toArray(new String[0]));
      } else {
        subCommand.perform(sender, list.toArray(new String[0]));
      }
    }
  }
  
  private void sendHelp(CommandSender sender, int page) {
    List<SubCommand> commands = this.commands.stream().filter(subcommand -> sender instanceof Player || !subcommand.onlyForPlayer()).collect(Collectors.toList());
    Map<Integer, StringBuilder> pages = new HashMap<>();
    
    int pagesCount = (commands.size() + 6) / 7;
    for (int index = 0; index < commands.size(); index++) {
      int currentPage = (index + 7) / 7;
      if (!pages.containsKey(currentPage)) {
        pages.put(currentPage, new StringBuilder(" \n§eAjuda - " + currentPage + "/" + pagesCount + "\n \n"));
      }
      
      pages.get(currentPage).append("§6/sw ").append(commands.get(index).getUsage()).append(" §f- §7").append(commands.get(index).getDescription()).append("\n");
    }
    
    StringBuilder sb = pages.get(page);
    if (sb == null) {
      sender.sendMessage("§cPágina não encontrada.");
      return;
    }
    
    sb.append(" ");
    sender.sendMessage(sb.toString());
  }
}