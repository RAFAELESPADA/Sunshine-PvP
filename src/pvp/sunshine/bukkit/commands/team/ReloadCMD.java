package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.commands.CommandStack;

public class ReloadCMD implements CommandExecutor {

    public int numbercount = 0;

    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!sender.getName().equalsIgnoreCase("Rafael_Auler")) {
            sender.sendMessage(CommandStack.PERMISSION_DENIED);
            return true;
        }
        numbercount = 11;
        new BukkitRunnable() {
            public void run() {
                numbercount = numbercount - 1;
                if (numbercount == 10) {
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando em 10 segundos! - §cKitPvP");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        all.playSound(all.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                    }
                }
                if (numbercount == 5) {
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando em 5...");
                }
                if (numbercount == 4) {
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando em 4...");
                }
                if (numbercount == 3) {
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando em 3....");
                }
                if (numbercount == 2) {
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando em 2...");
                    for (Player todos : Bukkit.getOnlinePlayers()) {
                        BukkitMain.sendServer(todos, "Lobby");
                    }
                }
                if (numbercount == 1) {
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando em 1...");
                    Bukkit.broadcastMessage("§c§lRELOAD §fReiniciando...");
                    Bukkit.shutdown();
                }

            }
        }.runTaskTimer(BukkitMain.getInstance(), 20 * 1, 20 * 1);
        return false;
    }

}