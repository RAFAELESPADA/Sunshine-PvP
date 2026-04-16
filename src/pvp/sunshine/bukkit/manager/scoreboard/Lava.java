package pvp.sunshine.bukkit.manager.scoreboard;

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
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.api.WinStreakAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

public class Lava {

    private static SunshineAnimation waveAnimation;
    private static String text = "";

    public static void init() {
        waveAnimation = new SunshineAnimation(" LAVA ", "§f§l", "§e§l", "§6§l", 3);
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
                        if (onlines.getPlayer().getScoreboard().getObjective("Lava") != null) {
                            objective.setDisplayName(text);
                        }
                    });
        }, 40, 2L);
    }

    public static void create(Player p) {
        ScoreboardManager manager = Bukkit.getScoreboardManager();
        Scoreboard board = manager.getNewScoreboard();
        Objective obj = board.registerNewObjective("Lava", "dummy");
        obj.setDisplaySlot(DisplaySlot.SIDEBAR);
        obj.setDisplayName("§6§lLAVA");
        addLine(board, "§b", 5);
        addLine(board, "§4", 4);
        addLine(board, "§3", 3);
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
            Team team = scoreboard.getTeam("line4");
			team.setPrefix("§fCoins: §e" + SunshineFormat.format(SQLPvP.getCoins(p)));
			Team team2 = scoreboard.getTeam("line3");
			team2.setPrefix("§fLiga: " + SQLRank.getRank(p));
			
        }
        if (scoreboard.getObjective("PlayerInBattle") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("PlayerInBattle").unregister();
            create(p);
        }
        if (scoreboard.getObjective("Evento") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("Evento").unregister();
            create(p);
        }
        if (scoreboard.getObjective("1v1") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("1v1").unregister();
            create(p);
        
        }
    }

    private static void addLine(Scoreboard scoreboard, String text, int score) {
        Team team = scoreboard.registerNewTeam("line" + score);
        team.addEntry(text);
        scoreboard.getObjective("Lava").getScore(text).setScore(score);
    }
}