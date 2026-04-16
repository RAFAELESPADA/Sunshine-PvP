package pvp.sunshine.bukkit.api;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

public class KillStreakAPI {

    private static final Map<Player, Integer> killStreaks = new HashMap<>();

    public static void addStreak(Player player) {
        int streak = killStreaks.getOrDefault(player, 0) + 1;
        killStreaks.put(player, streak);

        int[] milestones = {3, 5, 10, 20, 30, 40, 50};
        int[] rewards = {30, 50, 80, 100, 150, 200};

        for (int i = 0; i < milestones.length; i++) {
            if (streak == milestones[i]) {
                int coinsReward = rewards[i];
                SQLPvP.addCoins(player, coinsReward);
                player.sendMessage("§6§lBONUS! §fParabéns! Você recebeu §e+" + coinsReward + " coins§f por atingir um killstreak de §6" + streak + "§f!");
                player.playSound(player.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);

                if (i == milestones.length - 2) {
                    int xpReward = 80;
                    SQLPvP.addCoins(player, xpReward);
                    player.sendMessage("§d§lULTRA COMBO! §fVocê recebeu um combo de §e" + xpReward + " coins§f em sua conta por atingir um killstreak de §d" + streak + "§f!");
                }

                Bukkit.broadcastMessage("§4§lKILLSTREAK! §fO jogador §c" + player.getName() + "§f alcançou um killstreak de §c" + streak + "§f!");
                break;
            }
        }
    }

    public static int getStreak(Player player) {
        return killStreaks.getOrDefault(player, 0);
    }

    public static void removeStreak(Player player) {
        killStreaks.remove(player);
    }
}
