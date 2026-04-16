package pvp.sunshine.bukkit.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;

import java.io.IOException;

public class ChatClanCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (cmd.getName().equalsIgnoreCase("c")) {
            if (args.length == 0) {
                sender.sendMessage("§c§lERRO §fComando inválido, utilize: /c (mensagem)");
                return true;
            }
            String msg = "";
            for (String msg2 : args) {
                msg = ((msg)) + msg2 + " ";
            }
            Player p1 = Bukkit.getPlayer(sender.getName());
            if (!SQLClan.clan.get(p1.getUniqueId()).equalsIgnoreCase("Nenhum")) {
                for (Player jogadores : Bukkit.getOnlinePlayers()) {
                    if (SQLClan.clan.get(jogadores.getUniqueId()).equalsIgnoreCase(SQLClan.clan.get(p1.getUniqueId()))) {
                        jogadores.sendMessage("§7§l[§e§lCLAN§7§l] §7" + sender.getName() + " §7» §f" + msg);
                    }
                }
            } else {
                sender.sendMessage("§c§lCLAN §fVocê não pertence a nenhum clan.");
            }
        }
        return false;
    }

}
