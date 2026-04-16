package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pvp.sunshine.bukkit.BukkitMain;

public class BroadCastCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            if (args.length == 0) {
                sender.sendMessage("§c§lERRO §fComando inválido, utilize /bc (mensagem)");
            } else {
                broadcastMessage(args);
            }
        } else if (!sender.hasPermission("tag.gerente") || !sender.hasPermission("tag.admin")) {
            sender.sendMessage("§c§lERRO§f Você não possui permissão para executar esse comando.");
        } else if (args.length == 0) {
            sender.sendMessage("§c§lERRO§f Comando inválido, utilize /bc (mensagem)");
        } else {
            broadcastMessage(args);
            Bukkit.broadcast("§7* STAFF " + sender.getName() + " enviou um broadcast.", "pvp.broadcast");
        }
        return true;
    }

    private void broadcastMessage(String[] args) {
        String message = ChatColor.translateAlternateColorCodes('&', String.join(" ", args));
        Bukkit.broadcastMessage(" \n §6§l" + BukkitMain.getInstance().getConfig().getString("NomeServer") +  " §7» " + ChatColor.RESET + message + " \n ");
    }
}
