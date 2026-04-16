package pvp.sunshine.bukkit.manager.scoreboard;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import net.md_5.bungee.api.ChatColor;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.api.KillStreakAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.utils.GroupUtil;

public class PvP {

	private static SunshineAnimation waveAnimation;
	private static String text = "";
	public PvP(BukkitMain plugin) {
		new ScoreBoardTask().runTaskTimer(plugin, 0, 2L);
	}
	public static void init() {
		waveAnimation = new SunshineAnimation(" KITPVP ", "§f§l", "§e§l", "§6§l", 3);
		text = waveAnimation.next();

		Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), () -> {
			text = waveAnimation.next();

			Bukkit.getOnlinePlayers().stream()
					.filter(Player::isOnline)
					.filter(player -> !player.isDead())
					.forEach(onlines -> {
						Scoreboard score = onlines.getScoreboard();
						if (score == null) {
							return;
						}
						Objective objective = score.getObjective(DisplaySlot.SIDEBAR);
						if (objective == null) {
							return;
						}
						if (onlines.getPlayer().getScoreboard().getObjective("PvP") != null) {
							objective.setDisplayName(text);
						}
					});
		}, 40, 2L);
	}

	public static void create(Player p) {
		ScoreboardManager manager = Bukkit.getScoreboardManager();
		Scoreboard board = manager.getNewScoreboard();
		Objective obj = board.registerNewObjective("PvP", "dummy");
		obj.setDisplaySlot(DisplaySlot.SIDEBAR);
		obj.setDisplayName("§6§lKITPVP");
		if (SQLPvP.getKills(p) == null || SQLPvP.getDeaths(p) == null || SQLPvP.getCoins(p) == null) {
        	p.kickPlayer(ChatColor.RED + "ENCONTRAMOS UM ERRO COM SUA CONTA! RELOGUE PARA ARRUMAR");
        }
		addLine(board, "§3", 9);
		addLine(board, "§fKills: §7", 8);
		addLine(board, "§fDeaths: §7", 7);
		addLine(board, "§fKillstreak: §7", 6);
		addLine(board, "§0", 5);
		addLine(board, "§fCoins: §e", 4);
		addLine(board, "§fLiga: ", 3);
		addLine(board, "§1", 2);
		addLine(board, "§7" + BukkitMain.getInstance().getConfig().getString("IP"), 1);

		p.setScoreboard(board);
	}

	public static void update(Player p) {
		Scoreboard scoreboard = p.getScoreboard();
		if (scoreboard.getObjective("PvP") == null && scoreboard.getObjective("Lava") == null & scoreboard.getObjective("Evento") == null && scoreboard.getObjective("1v1") == null) {
			create(p);
			return;
		}
		if (scoreboard.getObjective("PvP") != null) {
			Team team = scoreboard.getTeam("line8");
			team.setSuffix(SunshineFormat.format(SQLPvP.getKills(p)));
			Team team2 = scoreboard.getTeam("line7");
			team2.setSuffix(SunshineFormat.format(SQLPvP.getDeaths(p)));
			Team team3 = scoreboard.getTeam("line6");
			team3.setSuffix(SunshineFormat.format(KillStreakAPI.getStreak(p)));
			Team team4 = scoreboard.getTeam("line4");
			team4.setSuffix(SunshineFormat.format(SQLPvP.getCoins(p)));
			Team team5 = scoreboard.getTeam("line3");
			team5.setSuffix(SQLRank.getRank(p));
		} else {
				create(p);
			p.playEffect(p.getLocation(), Effect.INSTANT_SPELL, 10);
		}
		if (scoreboard.getObjective("Lava") != null) {
			scoreboard.clearSlot(DisplaySlot.SIDEBAR);
			scoreboard.getObjective("Lava").unregister();
		}
		if (scoreboard.getObjective("Evento") != null) {
			scoreboard.clearSlot(DisplaySlot.SIDEBAR);
			scoreboard.getObjective("Evento").unregister();
		}
		if (scoreboard.getObjective("PlayerInBattle") != null) {
			scoreboard.clearSlot(DisplaySlot.SIDEBAR);
			scoreboard.getObjective("PlayerInBattle").unregister();
		}
		if (scoreboard.getObjective("1v1") != null) {
			scoreboard.clearSlot(DisplaySlot.SIDEBAR);
			scoreboard.getObjective("1v1").unregister();
		}
	}

	private static void addLine(Scoreboard scoreboard, String text, int score) {
		Team team = scoreboard.registerNewTeam("line" + score);
		team.addEntry(text);
		scoreboard.getObjective("PvP").getScore(text).setScore(score);
	}
}