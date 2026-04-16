package pvp.sunshine.bukkit.ability;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;

public class Cooldown {
	private static Map<Player, Long> run = new HashMap<>();

	public static void add(final Player p, double seconds) {
		run.remove(p);
		run.put(p, System.currentTimeMillis() + (long) (seconds * 1000));
		(new BukkitRunnable() {
			public void run() {
				Cooldown.run.remove(p);
			}
		}).runTaskLater((Plugin) BukkitMain.getInstance(), (long) (seconds * 20));
	}

	public static double cooldown(Player p) {
		if (run.containsKey(p)) {
			long remainingMillis = ((Long) run.get(p)).longValue() - System.currentTimeMillis();
			double remainingSeconds = remainingMillis / 1000.0;
			return Math.max(0, Math.round(remainingSeconds * 10.0) / 10.0);
		}
		return 0.0;
	}

	public static boolean add(Player p) {
		return run.containsKey(p);
	}

	public static void remove(Player p) {
		run.remove(p);
	}
}
