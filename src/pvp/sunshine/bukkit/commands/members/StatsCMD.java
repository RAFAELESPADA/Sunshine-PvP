package pvp.sunshine.bukkit.commands.members;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.inventory.StatisticsType;

public class StatsCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] lb) {
        sender.sendMessage("§a§lSTATS §fVocê está visualizando os status da sua conta.");
        StatisticsType.getStatistics((Player) sender);
        return false;
    }
}
