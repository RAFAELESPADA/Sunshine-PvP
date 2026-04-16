package pvp.sunshine.bukkit.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;
import pvp.sunshine.bukkit.manager.inventory.ProfileType;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.utils.GroupUtil;

public class AccountCMD implements CommandExecutor {


    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("account")) {
            if (args.length == 0) {
                sender.sendMessage(CommandStack.command("account (jogador)"));
                return true;
            }
            Player t = Bukkit.getPlayer(args[0]);
            if (t == null) {
                sender.sendMessage(CommandStack.PLAYER_IS_OFFLINE);
                return true;
            }
            sender.sendMessage("");
            sender.sendMessage("         §a§lACCOUNT");
            sender.sendMessage("");
            sender.sendMessage("  §fInformações de: §a" + t.getName());
            sender.sendMessage("  §fGrupo: " + GroupUtil.getGroup(t));
            sender.sendMessage("  §fUUID: §7" + t.getUniqueId());
            sender.sendMessage("");
            sender.sendMessage("  §fXP: §a" + SQLRank.getXp(t));
            sender.sendMessage("  §fRanking: " + SQLRank.getRank(t) + " " + SQLRank.getRankComplete(SQLRank.getXp(t)));
            sender.sendMessage("  §fClan: " + ProfileType.getClan(t));;
            sender.sendMessage("");
            return true;


        }
        return false;
    }
}
