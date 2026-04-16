package pvp.sunshine.bukkit.manager.scoreboard.duels;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import org.bukkit.scoreboard.Objective;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.ScoreboardManager;
import org.bukkit.scoreboard.Team;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.api.WinStreakAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.scoreboard.SunshineAnimation;

public class PlayerNotBattle {

    private static SunshineAnimation waveAnimation;
    private static String text = "";

    public static void init() {
        waveAnimation = new SunshineAnimation(" DUELS ", "§f§l", "§e§l", "§6§l", 3);
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
                        if (onlines.getPlayer().getScoreboard().getObjective("1v1") != null) {
                            objective.setDisplayName(text);
                        }
                    });
        }, 40, 2L);
    }

    public static void create(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("1v1", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6§l1V1");
        addLine(board, "§b", 9);
        addLine(board, "§8", 8);
        addLine(board, "§9", 7);
        addLine(board, "§7", 6);
        addLine(board, "§3", 5);
        addLine(board, "§fModo: §e1v1",4);
        addLine(board, "§4", 3);
        addLine(board, "§1", 2);
        addLine(board, "§7" + BukkitMain.getInstance().getConfig().getString("IP"), 1);

        p.setScoreboard(board);
    }

    public static void update(Player p) {
        Scoreboard scoreboard = p.getScoreboard();
        if (scoreboard.getObjective("PvP") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("PvP").unregister();
            create(p);
        }
        if (scoreboard.getObjective("Lava") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("Lava").unregister();
            create(p);
        }
        if (scoreboard.getObjective("Evento") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("Evento").unregister();
            create(p);
        }
        if (scoreboard.getObjective("PlayerInBattle") != null) {
        	 scoreboard.clearSlot(DisplaySlot.SIDEBAR);
             scoreboard.getObjective("PlayerInBattle").unregister();
        	 create(p);
        }
        if (scoreboard.getObjective("1v1") != null) {
            Team team = scoreboard.getTeam("line8");
			team.setPrefix("§fVitórias: §7" + SunshineFormat.format(SQL1v1.getWins(p)));
			Team team2 = scoreboard.getTeam("line7");
			team2.setPrefix("§fDerrotas: §7" + SunshineFormat.format(SQL1v1.getLoses(p)));
			Team team3 = scoreboard.getTeam("line6");
			team3.setPrefix("§fWinstreak: §7" + SunshineFormat.format(WinStreakAPI.getStreak(p)));
			Team team4 = scoreboard.getTeam("line3");
			team4.setPrefix("§fLiga: " + SQLRank.getRank(p));
			
            
        }
    }

    private static void addLine(Scoreboard scoreboard, String text, int score) {
        Team team = scoreboard.registerNewTeam("line" + score);
        team.addEntry(text);
        scoreboard.getObjective("1v1").getScore(text).setScore(score);
    }
}