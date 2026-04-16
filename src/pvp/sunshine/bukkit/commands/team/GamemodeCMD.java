package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;

public class GamemodeCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("pvp.gamemode")) {
            player.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            return true;
        }

        if (cmd.getName().equalsIgnoreCase("gamemode")) {
            if (AdminCMD.admin.contains(player.getName())) {
                player.sendMessage("§c§lADMIN §fVocê não pode utilizar este comando em modo admin.");
                return true;
            }
            if (args.length == 0) {
                player.sendMessage(CommandStack.command("gm <1/0> ou /gamemode <1/0>"));
                return true;
            }
            if (args[0].equalsIgnoreCase("1")) {
                player.sendMessage("§a§lGM §fGamemode alterado para §a1");
                Bukkit.broadcast("§7* STAFF " + player.getName() + " alterou o gamemode para 1.", "pvp.broadcast");
                player.setGameMode(GameMode.CREATIVE);
            } else if (args[0].equalsIgnoreCase("0")) {
                player.sendMessage("§a§lGM §fGamemode alterado para §a0");
                Bukkit.broadcast("§7* STAFF " + player.getName() + " alterou o gamemode para 0.", "pvp.broadcast");
                player.setGameMode(GameMode.ADVENTURE);
            } else {
                player.sendMessage("§c§lGM §fModo de jogo não encontrado.");
            }
        } else {
			player.sendMessage("§c§lERRO §fGamemode não encontrado.");
		}
        return true;
    }
}