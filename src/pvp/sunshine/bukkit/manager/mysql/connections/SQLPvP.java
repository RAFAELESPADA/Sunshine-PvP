package pvp.sunshine.bukkit.manager.mysql.connections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import net.md_5.bungee.api.ChatColor;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class SQLPvP extends Storage {

    private static final Map<String, Integer> Kills = new HashMap<>();
    private static final Map<String, Integer> Deaths = new HashMap<>();
    private static final Map<String, Integer> Coins = new HashMap<>();

    public static boolean checkPvP(UUID uuid) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM PvP WHERE UUID= ?");
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

    public static void registerPvP(final UUID uuid, final String nick) {
        (new BukkitRunnable() {
            public void run() {
                try {
                	loadCache(uuid);
                	 Bukkit.getConsoleSender().sendMessage("SETANDO CACHE DE KILLS PARA: " + nick + "( " + uuid + " )");
                     
                    PreparedStatement ps = SQLPvP.getStatement(
                            "INSERT INTO `PvP` (`uuid`, `nick`, `kills`, `deaths`, `coins`) VALUES (?, ?, '0', '0', '0')");
                    ResultSet resultSet = ps.executeQuery("select * from PvP where uuid = '" + uuid + "'");
                    if (!resultSet.next()) {
                    ps.setString(1, uuid.toString());
                    ps.setString(2, nick);
                    ps.executeUpdate();
                    ps.close();
                    Bukkit.getConsoleSender().sendMessage("CRIANDO USUARIO PARA" + nick + "( " + uuid + " )");
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }).runTaskAsynchronously((Plugin) BukkitMain.getInstance());
    }

    public static int getIntConnection(UUID name, String column) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM PvP WHERE UUID= ?");
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
        Kills.put(name.toString(), getIntConnection(name, "Kills"));
        Deaths.put(name.toString(), getIntConnection(name, "Deaths"));
        Coins.put(name.toString(), getIntConnection(name, "Coins"));
    }

    public static Integer getCoins(Player p) {
        return Coins.get(p.getUniqueId().toString());
    }

    public static Integer getDeaths(Player p) {
        return Deaths.get(p.getUniqueId().toString());
    }

    public static Integer getKills(Player p) {
        return Kills.get(p.getUniqueId().toString());
    }

    public static void addCoins(Player p, int value) {
        Coins.compute(p.getUniqueId().toString(),
                (name, current) -> Integer.valueOf(((current == null) ? 0 : current) + value));
    }

    public static void removeCoins(Player p, int value) {
        Coins.compute(p.getUniqueId().toString(), (name, current) -> {
            int currentValue = (current == null) ? 0 : current;
            int newValue = currentValue - value;
            return (newValue < 0) ? 0 : newValue;
        });
    }

    public static void addKills(Player p) {
        Kills.compute(p.getUniqueId().toString(),
                (name, current) -> ((current == null) ? 0 : current.intValue()) + 1);
    }
    public static void zerarKills(Player p) {
        Kills.put(p.getUniqueId().toString(), 0);
        Deaths.put(p.getUniqueId().toString(), 0);
        Coins.put(p.getUniqueId().toString(), 0);
        updateData(p);
    
    }

    public static void addDeaths(Player p) {
        Deaths.compute(p.getUniqueId().toString(),
                (name, current) -> ((current == null) ? 0 : current.intValue()) + 1);
    }

    public static void updateData(final Player p) {

        
        
        int kills = getKills(p);
        int coins = getCoins(p);
        int deaths = getDeaths(p);
        
        new BukkitRunnable() {
            public void run() {
                try {
        	
            PreparedStatement ps = Storage.getConnection().prepareStatement(
                    "UPDATE `PvP` SET `Kills`=?, `Coins`=?, `NICK`=?, `Deaths`=? WHERE `UUID`=?;");
            ps.setInt(1, kills);
            ps.setInt(2, coins);
            ps.setString(3, p.getName());
            ps.setInt(4, deaths);
            ps.setString(5, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLPvP.Coins.remove(p.getUniqueId().toString());
            SQLPvP.Deaths.remove(p.getUniqueId().toString());
            SQLPvP.Kills.remove(p.getUniqueId().toString());
        }}}.runTaskAsynchronously((Plugin) BukkitMain.getInstance());
    }}
   
   

