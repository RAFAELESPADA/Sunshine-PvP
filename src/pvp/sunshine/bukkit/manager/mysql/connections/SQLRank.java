package pvp.sunshine.bukkit.manager.mysql.connections;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class SQLRank extends Storage {
    public static final Map<UUID, Integer> Experience = new HashMap<>();

    public static String getRankComplete(int rank) {
        if (rank >= 50000) {
            return "§5Deus";
        } else if (rank >= 20000) {
            return "§1Safira";
        } else if (rank >= 15000) {
            return "§bDiamante";
        } else if (rank >= 10000) {
            return "§2Esmeralda";
        } else if (rank >= 5000) {
            return "§4Ruby";
        } else if (rank >= 3500) {
            return "§eOuro";
        } else if (rank >= 2000) {
            return "§fFerro";
        } else if (rank >= 1500) {
            return "§6Bronze";
        } else if (rank >= 1000) {
            return "§4Avançado";
        } else if (rank >= 700) {
            return "§2Iniciante";
        } else if (rank >= 500) {
            return "§8Pedra";
        }
        return "§7Unranked";
    }

    public static String getRank(Player p) {
        if (Experience.containsKey(p.getUniqueId())) {
            int rank = Experience.get(p.getUniqueId());
            if (rank >= 50000) {
                return "§5✺";
            } else if (rank >= 20000) {
                return "§1✯";
            } else if (rank >= 15000) {
                return "§b❂";
            } else if (rank >= 10000) {
                return "§2☯";
            } else if (rank >= 5000) {
                return "§4❃";
            } else if (rank >= 3500) {
                return "§e❆";
            } else if (rank >= 2000) {
                return "§f❅";
            } else if (rank >= 1500) {
                return "§6⚝";
            } else if (rank >= 1000) {
                return "§4❂";
            } else if (rank >= 700) {
                return "§2✥";
            } else if (rank >= 500) {
                return "§8✽";
            } else if (rank >= 0) {
                return "§a⚍";
            }
        }
        return "§a⚍";
    }

    public static int getXpRequiredForNextRank(int currentXP) {
        if (currentXP >= 50000) return -1;
        if (currentXP >= 20000) return 50000 - currentXP;
        if (currentXP >= 15000) return 20000 - currentXP;
        if (currentXP >= 10000) return 15000 - currentXP;
        if (currentXP >= 5000) return 10000 - currentXP;
        if (currentXP >= 3500) return 5000 - currentXP;
        if (currentXP >= 2000) return 3500 - currentXP;
        if (currentXP >= 1500) return 2000 - currentXP;
        if (currentXP >= 1000) return 1500 - currentXP;
        if (currentXP >= 700) return 1000 - currentXP;
        if (currentXP >= 500) return 700 - currentXP;
        return 500 - currentXP;
    }

    public static String getRankSymbol(int rank) {
        if (rank >= 50000) {
            return "§6✺";
        } else if (rank >= 20000) {
            return "§1✯";
        } else if (rank >= 15000) {
            return "§3❂";
        } else if (rank >= 10000) {
            return "§b☯";
        } else if (rank >= 5000) {
            return "§4❃";
        } else if (rank >= 3500) {
            return "§6❆";
        } else if (rank >= 2000) {
            return "§b❅";
        } else if (rank >= 1500) {
            return "§9⚝";
        } else if (rank >= 1000) {
            return "§c❂";
        } else if (rank >= 700) {
            return "§5✥";
        } else if (rank >= 500) {
            return "§2✽";
        }
        return "§a⚍";
    }

    public static boolean checkXP(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Xp WHERE UUID= ?");
            ps.setString(1, name);
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

    public static void registerXP(final UUID Nome) {
        (new BukkitRunnable() {
            public void run() {
                try {
                	
                    PreparedStatement ps = SQLRank.getStatement("INSERT INTO Xp (UUID, EXP) VALUES (?, ?)");
                    ResultSet resultSet = ps.executeQuery("select * from Xp where UUID = '" + Nome + "'");
                    if (!resultSet.next()) {
                    ps.setString(1, Nome.toString());
                    ps.setInt(2, 0);
                    ps.executeUpdate();
                    ps.close();
                    }} catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        }).runTaskAsynchronously((Plugin) BukkitMain.getInstance());
    }

    public static void addXp(Player p, int value) {
        Experience.compute(p.getUniqueId(),
                (name, current) -> Integer.valueOf(((current == null) ? 0 : current.intValue()) + value));
    }

    public static void removeXP(Player p, int value) {
        Experience.compute(p.getUniqueId(), (name, current) -> {
            int currentValue = (current == null) ? 0 : current.intValue();
            int newValue = currentValue - value;
            return (newValue < 0) ? 0 : newValue;
        });
    }
    public static void zerarXP(Player p) {
        Experience.put(p.getUniqueId(), 0);
       
        updateData(p);
    
    }

    public static int getXp(Player p) {
        return Experience.get(p.getUniqueId());
    }

    public static int getXpConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Xp WHERE UUID= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int xp = rs.getInt("EXP");
            rs.close();
            ps.close();
            return xp;
        } catch (SQLException ex) {
            return 0;
        }
    }

    public static void updateData(final Player p) {
        int xp = getXp(p);
        try {
            PreparedStatement ps = Storage.getConnection()
                    .prepareStatement("UPDATE `Xp` SET `UUID`=?,`EXP`=? WHERE `UUID`=?");
            ps.setString(1, p.getUniqueId().toString());
            ps.setInt(2, xp);
            ps.setString(3, p.getUniqueId().toString());
            ps.executeUpdate();
            ps.close();
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            SQLRank.Experience.remove(p.getUniqueId());
        }
    }
}