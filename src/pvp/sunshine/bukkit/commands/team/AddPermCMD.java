package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;

public class AddPermCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!sender.getName().equalsIgnoreCase("Rafael_Auler")) {
            sender.sendMessage(CommandStack.PERMISSION_DENIED);
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CommandStack.command("addperm (jogador) (permissão)"));
            return true;
        }
        if (args.length == 1) {
            sender.sendMessage("§c§lPERMISSION §fVocê precisa definir uma permissão para esse jogador.");
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            sender.sendMessage(CommandStack.PLAYER_IS_OFFLINE);
            return true;
        }
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + t.getName() + " permission set pvp." + args[1]);
        sender.sendMessage("§a§lPERMISSION §fVocê definiu a permissão §apvp." + args[1] + "§f para §a" + t.getName() + "§f com sucesso.");
        return true;
    }
}
