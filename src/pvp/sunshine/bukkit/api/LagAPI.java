package pvp.sunshine.bukkit.api;

import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;

public class LagAPI {

    public static double Tps = 0.00;

    public static void IntelicLag() {
        new BukkitRunnable() {
            public void run() {
                Tps = BukkitMain.getTps();
            }
        }.runTaskTimer(BukkitMain.getInstance(), 0, 20 * 300);
    }

}
