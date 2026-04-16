package pvp.sunshine.bukkit.commands.team;



import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;


public class TempoGrupoBukkit implements CommandExecutor {
	
	  
  LuckPerms api = LuckPermsProvider.get();
  @Override
  public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] args)
  {
    if (!(sender instanceof Player)) {
      return true;
    }
    if (cmd.getName().equalsIgnoreCase("tempogrupo"))
    { 
    if (args.length < 3) {
      sender.sendMessage(ChatColor.DARK_AQUA + "Uso correto: /tempogrupo <Jogador> <Grupo> <Dias>.");
      return true;
    } 
    net.luckperms.api.model.group.Group group = api.getGroupManager().getGroup(args[1]);
    if (group == null) {
      sender.sendMessage(ChatColor.RED + "Grupo não encontrado.");
      sender.sendMessage(ChatColor.RED + "Use default para colocar como Membro.");
      return true;
    } 
    if (!sender.hasPermission("cmd.group." + args[1])) {
      sender.sendMessage(ChatColor.RED + "Você não pode setar alguém com esse grupo");
      return true;
    } 
    String target = args[0];
    Player targetPlayer = Bukkit.getPlayer(target);
    if ((targetPlayer != null)) {
if (targetPlayer.hasPermission("groupset.bypass")) {
	sender.sendMessage(ChatColor.RED + "Você não pode gerenciar o grupo desse Jogador!");
	sender.sendMessage(ChatColor.RED + "Ele possui um cargo acima do seu!");
    return true;
}
if (targetPlayer == sender) {
	sender.sendMessage(ChatColor.RED + "Você não pode alterar seu proprio grupo!");
    return true;
	
}
    }
    
    try {
    	Integer dias = Integer.valueOf(args[2]);
        
    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + target + " parent clear");
    Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(), "lp user " + target + " parent addtemp " + args[1] + " " + dias + "d server=global");

    String textString = "§c* " + sender.getName() + " §csetou o grupo " + group.getName()
           + "§c para " + args[0] + "\n§cDuração: (" + dias + " Dias)";
Bukkit.broadcast(textString, "wpunish.veralerta");

    if ((targetPlayer != null)) {
      targetPlayer.sendMessage(ChatColor.RED + "Seu grupo foi atualizado para " + args[1] + " Duração: " + dias + " Dias." + " Atualizado pelo staff: " + sender.getName() + " !"); 
    sender.sendMessage(ChatColor.RED + "Você atualizou o grupo de " + target + " para " + args[1] + " !");
    return true;
  }
    }
  catch (NumberFormatException e) {
	  sender.sendMessage("Use apenas números no tempo de dias!");
  }
  }
	return false;
}


}

