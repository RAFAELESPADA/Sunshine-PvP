package pvp.sunshine.bukkit.commands.members;

import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.*;
import org.bukkit.*;

public class PingCMD implements CommandExecutor {

    public boolean onCommand(final CommandSender sender, final Command cmd, final String arg2, final String[] args) {
        if (args.length == 0) {
            Player p = (Player) sender;
            int ping = ((CraftPlayer) p).getHandle().ping;
            String pingStatus = getPingStatus(ping);
            p.sendMessage("§a§lPING §fSua conexão é de §a" + ping + " ms §f- " + pingStatus);
            return true;
        }
        Player t = Bukkit.getPlayer(args[0]);
        if (t == null) {
            sender.sendMessage("§c§lERRO §fJogador não encontrado.");
        } else {
            int tping = ((CraftPlayer) t).getHandle().ping;
            String pingStatus = getPingStatus(tping);
            sender.sendMessage("§a§lPING §fA conexão de §a" + t.getName() + " §fé de §a" + tping + " ms §f- " + pingStatus);
        }
        return true;
    }

    private String getPingStatus(int ping) {
        if (ping <= 120) {
            return "§aEstável";
        } else {
            return "§cInstável";
        }
    }
}
