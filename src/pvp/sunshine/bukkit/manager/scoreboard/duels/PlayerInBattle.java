package pvp.sunshine.bukkit.manager.scoreboard.duels;

import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
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
import pvp.sunshine.bukkit.manager.duels.Battle;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.scoreboard.SunshineAnimation;

import java.util.UUID;

public class PlayerInBattle {

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
                        if (onlines.getPlayer().getScoreboard().getObjective("PlayerInBattle") != null) {
                            objective.setDisplayName(text);
                        }
                    });
        }, 40, 2L);
    }

    public static void create(Player p) {
        UUID playerId = p.getUniqueId();
        if (Battle.partida.containsKey(playerId)) {
            Player p2 = Bukkit.getPlayer(Battle.partida.get(playerId));
            if (p2 != null && Battle.partida.containsKey(p2.getUniqueId())) {
                int Ping = ((CraftPlayer) p.getPlayer()).getHandle().ping;
                int Ping2 = ((CraftPlayer) p2.getPlayer()).getHandle().ping;
                ScoreboardManager manager = Bukkit.getScoreboardManager();
                Scoreboard board = manager.getNewScoreboard();
                Objective obj = board.registerNewObjective("PlayerInBattle", "dummy");
                obj.setDisplaySlot(DisplaySlot.SIDEBAR);
                obj.setDisplayName("§6§l1V1");
                addLine(board, "§0", 5);
                addLine(board, "§f Você está duelando contra:", 4);
                addLine(board, "§b  " + p2.getName(), 3);
                addLine(board, "§1", 2);
                addLine(board, "§7" + BukkitMain.getInstance().getConfig().getString("IP"), 1);

                p.setScoreboard(board);
            }
        }
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
        if (scoreboard.getObjective("1v1") != null) {
            scoreboard.clearSlot(DisplaySlot.SIDEBAR);
            scoreboard.getObjective("1v1").unregister();
            create(p);
        }
        if (scoreboard.getObjective("PlayerInBattle") != null) {
           
        }
    }

    private static void addLine(Scoreboard scoreboard, String text, int score) {
        Team team = scoreboard.registerNewTeam("line" + score);
        team.addEntry(text);
        scoreboard.getObjective("PlayerInBattle").getScore(text).setScore(score);
    }
}