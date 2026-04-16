package pvp.sunshine.bukkit.commands.members;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.PlayerLoad;

public class LobbyCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] args) {
        if (sender instanceof Player) {
            Player player = (Player) sender;
            sender.sendMessage("§a§lCONNECT §fConectando ao Lobby...");
           
        } else {
            sender.sendMessage("Este comando só pode ser executado por jogadores.");
        }
        return true;
    }
}
