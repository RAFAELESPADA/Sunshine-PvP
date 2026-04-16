package pvp.sunshine.bukkit.manager.mysql.connections;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class SQL1v1 extends Storage {

    private static final Map<UUID, Integer> Wins = new HashMap<>();
    private static final Map<UUID, Integer> Loses = new HashMap<>();

    public static boolean check1v1(UUID uuid) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM 1v1 WHERE UUID= ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            ps.close();
            return user;
        } catch (SQLException ex) {
            ex.printStackTrace();

            return false;
        }
    }

    public static void register1v1(final UUID uuid, final String nick) {
        (new BukkitRunnable() {
            public void run() {
                try {
                    PreparedStatement ps = SQL1v1.getStatement(
                            "INSERT INTO `1v1` (`uuid`, `nick`, `wins`, `loses`) VALUES (?, ?, '0', '0')");
                    ResultSet resultSet = ps.executeQuery("select * from 1v1 where uuid = '" + uuid + "'");
                    if (!resultSet.next()) {
                    ps.setString(1, uuid.toString());
                    ps.setString(2, nick);
                    ps.executeUpdate();
                    ps.close();
                    } } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).runTaskAsynchronously((Plugin) BukkitMain.getInstance());
    }

    public static int getIntConnection(UUID name, String column) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM 1v1 WHERE UUID= ?");
            ps.setString(1, name.toString());
            ResultSet rs = ps.executeQuery();
            if (rs.next()) {
                int value = rs.getInt(column);
                rs.close();
                ps.close();
                return value;
            }
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return 0;
    }

    public static void loadCache(UUID name) {
        Wins.put(name, getIntConnection(name, "Wins"));
        Loses.put(name, getIntConnection(name, "Loses"));
    }

    public static Integer getWins(Player p) {
        return Wins.get(p.getUniqueId());
    }

    public static Integer getLoses(Player p) {
        return Loses.get(p.getUniqueId());
    }

    public static void addWins(Player p) {
        Wins.compute(p.getUniqueId(),
                (name, current) -> Integer.valueOf(((current == null) ? 0 : current.intValue()) + 1));
    }

    public static void addLoses(Player p) {
        Loses.compute(p.getUniqueId(),
                (name, current) -> Integer.valueOf(((current == null) ? 0 : current.intValue()) + 1));
    }

    public static void updateData(final Player p) {
        int wins = getWins(p).intValue();
        int loses = getLoses(p).intValue();
        try {
        	
            PreparedStatement ps = Storage.getConnection().prepareStatement(
                    "UPDATE `1v1` SET `Wins`=?, `NICK`=?, `Loses`=? WHERE `UUID`=?;");
            ps.setInt(1, wins);
            ps.setString(2, p.getUniqueId().toString());
            ps.setInt(3, loses);
            ps.setString(4, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQL1v1.Wins.remove(p.getUniqueId());
            SQL1v1.Loses.remove(p.getUniqueId());
        }
    }
}
