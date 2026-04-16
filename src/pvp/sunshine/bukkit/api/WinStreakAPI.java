package pvp.sunshine.bukkit.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

public class WinStreakAPI {

    private static final Map<Player, Integer> winStreaks = new HashMap<>();

    public static void addStreak(Player player) {
        int winstreak = winStreaks.getOrDefault(player, 0) + 1;
        winStreaks.put(player, winstreak);
	
        int[] milestones = {3, 5, 10, 20, 30, 40, 50};
        int[] rewards = {30, 50, 80, 100, 150, 200};

        for (int i = 0; i < milestones.length; i++) {
            if (winstreak == milestones[i]) {
                int coinsReward = rewards[i];
        		new BukkitRunnable() {	
        			public void run() {	
        			
                SQLPvP.addCoins(player, coinsReward);
        			}}.runTaskAsynchronously(BukkitMain.getInstance());
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                player.sendMessage("§6§lBONUS! §fParabéns! Você recebeu §e+" + coinsReward + " coins§f por atingir um winstreak de §6" + winstreak + "§f!");

                if (i == milestones.length - 2) {
                    int xpReward = 80;
                	new BukkitRunnable() {	
            			public void run() {	
            		
                    SQLPvP.addCoins(player, xpReward);
            			}}.runTaskAsynchronously(BukkitMain.getInstance());
                    player.sendMessage("§d§lULTRA COMBO! §fVocê recebeu um combo de §e" + xpReward + " coins§f em sua conta por atingir um winstreak de §d" + winstreak + "§f!");
                }

                Bukkit.broadcastMessage("§2§lWINSTREAK! §fO jogador §a" + player.getName() + "§f alcançou um winstreak de §a" + winstreak + "§f!");
                break;
            }
		}
		
    }

    public static int getStreak(Player player) {
        return winStreaks.getOrDefault(player, 0);
    }

    public static void removeStreak(Player player) {
        winStreaks.remove(player);
    }
}
