package net.wavemc.pvp.command;

import net.wavemc.pvp.WavePvP;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Arrays;

public class AvisoT implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command command, final String label, final String[] args) {
        if (sender instanceof Player) {
            final Player player = (Player)sender;
            if (player.hasPermission("wave.sendtitle")) {
                if (args.length > 0) {
                    final String message = ChatColor.translateAlternateColorCodes('&', args[0]).replace("{discord}", WavePvP.getInstance().getConfig().getString("DiscordLink").replace("&", "§"));
                    for (Player p : Bukkit.getOnlinePlayers()) {
                    	((Player) p.spigot()).sendTitle("§c§lAVISO", message);
                    }
                   String textComponent = "§7§o(STAFF) Aviso enviado por §f" + player.getName() + "§7§o.";
                   Bukkit.broadcast(textComponent, "kombo.cmd.report");
              
                    
                }
                else {
                    player.sendMessage("§cUtilize: /title <Mensagem>");
                }
            }
            else {
                player.sendMessage("§cVocê não tem autorização");
            }
        }
        else {
            sender.sendMessage("Sem console");
        }
        return true;
    }

}