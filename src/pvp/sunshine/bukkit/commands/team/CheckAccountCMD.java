package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLAuth;

public class CheckAccountCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length != 1) {
            sender.sendMessage("§c§lERRO §fComando incorreto, utilize: /checkaccount <jogador>");
            return true;
        }
        if (!sender.hasPermission("*")) {
            sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            return true;

        }
        Player p = (Player) sender;
        String playerName = args[0];

        OfflinePlayer targetPlayer = Bukkit.getOfflinePlayer(playerName);
        if (p.hasPermission("*")) { {
            if (SQLAuth.checkAccount(targetPlayer)) {
                sender.sendMessage("§a§lCHECKACCOUNT §fAs seguintes contas estão logadas no mesmo IP que §a" + playerName + ":");
                SQLAuth.printAccountsOnSameIP(sender, targetPlayer);
            } else {
                sender.sendMessage("§c§lERRO §fEsse jogador não está cadastrado no banco de dados.");
            }

            return true;
        }
    }
		return false;
    }}
