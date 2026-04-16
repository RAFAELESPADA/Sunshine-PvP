package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class TpCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command Cmd, String Label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lERRO §fApenas jogadores podem executar esse comando.");
        } else if (!sender.hasPermission("pvp.tp")) {
            sender.sendMessage("§c§lERRO §fVocê não possui permissão para executar esse comando.");
        } else if (args.length == 0) {
            sender.sendMessage("§c§lERRO §fComando inválido, utilize /tp (nick) ou /tp (player) (você)");
        } else if (args.length == 1) {
            Player target = Bukkit.getPlayer(args[0]);
            if (target != null) {
                sender.sendMessage("§a§lTP §fVocê foi teleportado para o jogador(a) §a" + target.getName() + "§f.");
                Bukkit.broadcast("§7* STAFF " + sender.getName() + " teleportou-se até um jogador.", "pvp.broadcast");
                ((Player) sender).teleport(target.getLocation());
            } else {
                sender.sendMessage("§c§lERRO §fJogador offline.");
            }
        } else if (args.length == 2) {
            Player target = Bukkit.getPlayer(args[1]);

            if (target != null) {
                if (target.getName().equalsIgnoreCase(sender.getName())) {
                    sender.sendMessage("§c§lTP §fVocê não pode teleporatar para si mesmo.");
                } else {
                    sender.sendMessage(
                            "§a§lTP §fO jogador §a" + target.getName() + " §ffoi teleportado até você.");
                    target.sendMessage(
                            "§a§lTP §fVocê foi teleportado para o jogador(a) §a" + sender.getName() + "§f.");
                    Bukkit.broadcast("§7* STAFF " + sender.getName() + " puxou um jogador.", "pvp.broadcast");
                    target.teleport(((Player) sender).getLocation());
                }
            } else {
                sender.sendMessage("§c§lERRO §fJogador offline.");
            }
        } else {
            sender.sendMessage("§c§lERRO §fComando inválido, utilize /tp (nick) ou /tp (player) (você)");
        }
        return false;
    }
}