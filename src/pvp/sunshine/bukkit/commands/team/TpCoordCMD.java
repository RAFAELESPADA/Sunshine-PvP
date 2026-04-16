package pvp.sunshine.bukkit.commands.team;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;

public class TpCoordCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (!sender.hasPermission("pvp.tp")) {
            sender.sendMessage(CommandStack.PERMISSION_DENIED);
            return true;
        }
        if (args.length < 3) {
            sender.sendMessage(CommandStack.command("tpcoord (x, y, z)"));
            return true;
        }

        try {
            double x = Double.parseDouble(args[0]);
            double y = Double.parseDouble(args[1]);
            double z = Double.parseDouble(args[2]);

            Player player = (Player) sender;
            player.teleport(player.getWorld().getBlockAt((int) x, (int) y, (int) z).getLocation());
            sender.sendMessage("§a§lTPCOORD §fVocê foi teleportado para as coordenadas §a" + x + "§f, §a" + y + "§f, §a" + z + "§f!");
        } catch (NumberFormatException e) {
            sender.sendMessage("§c§lTPCOORD §fForneça as coordenadas válidas!");
        }

        return true;
    }
}
