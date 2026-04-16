package pvp.sunshine.bukkit.commands.members;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.inventory.MidiaType;

public class MidiaCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String s, String[] strings) {
        MidiaType.getMidia((Player) sender);
        return false;
    }
}
