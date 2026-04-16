package pvp.sunshine.bukkit.manager.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerNotBattle;

public class ScoreBoardTask extends BukkitRunnable {
	
	@Override
	public void run() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (p.getScoreboard().getObjective("PvP") != null) {
			PvP.update(p);
		}
			else if (p.getScoreboard().getObjective("1v1") != null) {
				PlayerNotBattle.update(p);
			}
			else if (p.getScoreboard().getObjective("Lava") != null) {
				Lava.update(p);
			}
			else if (p.getScoreboard().getObjective("Evento") != null) {
				Evento.update(p);
			}
	}
}
}
