package pvp.sunshine.bukkit.commands.team;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;

import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;


public class ResetKDRCMD
  implements Listener, CommandExecutor
{
  public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
  {
    if (cmd.getName().equalsIgnoreCase("resetkdr"))
    {
      if (!(sender instanceof Player)) {
        return true;
      }
      Player p = (Player)sender;
      if (args.length == 0) {
    	  p.sendMessage(ChatColor.RED + "Utilize /resetkdr <nick>");
        return true;
      }
      else if (!sender.hasPermission("admin.cmd.resetkdr")) {
          sender.sendMessage("§cVocê não tem permissão!");
          return true;
      }
      Player t = Bukkit.getPlayer(args[0]);
      if (args.length == 1) {
    	  if (t == null) {
              sender.sendMessage("§cO Player precisa estar online");
              return true;  
    	  }
    	  SQLPvP.zerarKills(p);
    	  SQLRank.zerarXP(p);
			Bukkit.broadcast("§4§lRESETKDR §a" + p.getName() + " §fresetou o KDR do Jogador " + t.getName(), "kombo.cmd.report");
      }
    }
    return true;
  }
}
