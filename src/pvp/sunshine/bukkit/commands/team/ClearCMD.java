package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;

public class ClearCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] strings) {
        if (!sender.hasPermission("pvp.clear")) {
            sender.sendMessage(CommandStack.PERMISSION_DENIED);
            return true;
        }
        Player p = (Player)sender;
        p.getInventory().clear();
        p.sendMessage("§a§lCLEAR §fSeu inventário foi limpo.");
        Bukkit.broadcast("§7* STAFF " + p.getName() + " limpou o inventário.", "pvp.broadcast");
        return false;
    }
}
