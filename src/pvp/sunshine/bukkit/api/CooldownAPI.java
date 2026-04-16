package pvp.sunshine.bukkit.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;

import java.util.HashMap;
import java.util.Map;

public class CooldownAPI {
    private static final Map<Player, Long> cooldowns = new HashMap<>();
    private static final int COOLDOWN_SECONDS = 30 * 60;

    public static void add(final Player player) {
        long currentTimeMillis = System.currentTimeMillis();
        long cooldownExpiration = currentTimeMillis + (COOLDOWN_SECONDS * 1000);

        cooldowns.put(player, cooldownExpiration);

        new BukkitRunnable() {
            @Override
            public void run() {
                cooldowns.remove(player);
            }
        }.runTaskLater(BukkitMain.getInstance(), COOLDOWN_SECONDS * 20); // 20 ticks por segundo
    }

    public static double getRemainingTime(Player player) {
        if (cooldowns.containsKey(player)) {
            long currentTimeMillis = System.currentTimeMillis();
            long cooldownExpiration = cooldowns.get(player);

            long remainingMillis = cooldownExpiration - currentTimeMillis;
            double remainingSeconds = remainingMillis / 1000.0;

            return Math.max(0, Math.round(remainingSeconds * 10.0) / 10.0);
        }

        return 0.0;
    }

    public static boolean hasCooldown(Player player) {
        return cooldowns.containsKey(player);
    }

    public static void remove(Player player) {
        cooldowns.remove(player);
    }
}
