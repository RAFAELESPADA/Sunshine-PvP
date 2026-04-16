package pvp.sunshine.bukkit.manager.mysql.connections;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class SQLClan extends Storage {

    public static String getTagPlayer(Player p) {
        if(!clan.get(p.getUniqueId()).equalsIgnoreCase("Nenhum")) {
            return " " + SQLRank.getRank(p) + "§e " + tag.get(p.getUniqueId());
        } else {
            return " " + SQLRank.getRank(p);
        }
    }

    public static boolean checkClan(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE ClanName= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            ps.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static boolean checkTag(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            ps.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static String getClanConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE UUID= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String clan = rs.getString("ClanName");
            rs.close();
            ps.close();
            return clan;
        } catch (Exception localException) {
        }
        return "Nenhum";
    }



    public static void registerClan(String nome, String tag, int xp) {
        try {
            PreparedStatement ps = getStatement("INSERT INTO ClanRegistry (Nome, Tag, Xp) VALUES (?, ?, ?)");
            ResultSet resultSet = ps.executeQuery("select * from ClanRegistry where Nome = '" + nome + "'");
            if (!resultSet.next()) {
            ps.setString(1, nome);
            ps.setString(2, tag);
            ps.setInt(3, xp);
            ps.executeUpdate();
            ps.close();
        }} catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static void deleteClan(String nome) {
        try {
            PreparedStatement ps = getStatement("DELETE FROM `ClanRegistry` WHERE `Nome`='" + nome + "'");
            ps.executeUpdate();
            ps.close();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getCargoConnection(UUID uuid) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE UUID= ?");
            ps.setString(1, uuid.toString());
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Cargo = rs.getString("CargoName");
            rs.close();
            ps.close();
            return Cargo;
        } catch (Exception localException) {
        }
        return "Membro";
    }

    public static String getClanNameConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Tag = rs.getString("Tag");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return "Nenhuma";
    }

    public static String getTagConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Tag = rs.getString("Tag");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return "Nenhuma";
    }

    public static String getClanTagConnection(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Tag= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            String Tag = rs.getString("Nome");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return "Nenhuma";
    }

    public static Integer getXp(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ClanRegistry WHERE Nome= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            rs.next();
            int Tag = rs.getInt("Xp");
            rs.close();
            ps.close();
            return Tag;
        } catch (Exception localException) {
        }
        return 0;
    }

    public static void updateData(Player p) {
        if (clan.containsKey(p.getUniqueId()) && tag.containsKey(p.getUniqueId())) {
            String tagc = tag.get(p.getUniqueId());
            int totalx = 0;
            try {
                connection.createStatement().executeUpdate("UPDATE `Clan` SET `UUID`='" + p.getUniqueId() + "',`ClanName`='"
                        + getClan(p) + "',`CargoName`='" + getTag(p) + "' WHERE `UUID`='" + p.getUniqueId() + "';");

                PreparedStatement ps = SQLClan.connection
                        .prepareStatement("SELECT `UUID`, `ClanName` FROM `Clan` WHERE `ClanName`='"
                                + SQLClan.clan.get(p.getUniqueId()) + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tempcachename = rs.getString("UUID");
                    int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                    totalx = totalx + xp;
                }
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();

            }
            try {
                connection.createStatement()
                        .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan.get(p.getUniqueId()) + "',`Tag`='"
                                + tagc + "',`Xp`='" + totalx + "' WHERE `Nome`='" + clan.get(p.getUniqueId()) + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cargo.remove(p.getUniqueId());
                clan.remove(p.getUniqueId());
                if (tag.containsKey(p.getUniqueId())) {
                    tag.remove(p.getUniqueId());
                }
            }
        }

    }


    public static void removeXp(int xp, String clan) throws SQLException {
        int XpClan = getXp(clan);
        XpClan = XpClan - xp;
        connection.createStatement()
                .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan + "',`Tag`='"
                        + SQLClan.getTagConnection(clan) + "',`Xp`='" + XpClan
                        + "' WHERE `Nome`='" + clan + "';");
    }

    public static void updatePlayer(Player p) {
        if (clan.containsKey(p.getUniqueId()) && tag.containsKey(p.getUniqueId())) {
            String tagc = tag.get(p.getUniqueId());
            int totalx = 0;
            try {
                connection.createStatement().executeUpdate("UPDATE `Clan` SET `UUID`='" + p.getUniqueId() + "',`ClanName`='"
                        + getClan(p) + "',`CargoName`='" + getTag(p) + "' WHERE `UUID`='" + p.getUniqueId() + "';");
                PreparedStatement ps = SQLClan.connection
                        .prepareStatement("SELECT `UUID`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='"
                                + SQLClan.clan.get(p.getUniqueId()) + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tempcachename = rs.getString("UUID");
                    int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                    totalx = totalx + xp;
                }
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();

            }
            try {
                connection.createStatement()
                        .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan.get(p.getUniqueId()) + "',`Tag`='"
                                + tagc + "',`Xp`='" + totalx + "' WHERE `Nome`='" + clan.get(p.getUniqueId()) + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                cargo.remove(p.getUniqueId());
                clan.remove(p.getUniqueId());
                if (tag.containsKey(p.getUniqueId())) {
                    tag.remove(p.getUniqueId());
                }
                loadCache(p.getUniqueId());
            }
        }

    }

    public static void updateConvite(Player p) {
        if (clan.containsKey(p.getUniqueId()) && tag.containsKey(p.getUniqueId())) {
            String tagc = tag.get(p.getUniqueId());
            int totalx = 0;
            try {
                connection.createStatement().executeUpdate("UPDATE `Clan` SET `UUID`='" + p.getUniqueId() + "',`ClanName`='"
                        + getClan(p) + "',`CargoName`='" + getTag(p) + "' WHERE `UUID`='" + p.getUniqueId() + "';");
                PreparedStatement ps = SQLClan.connection
                        .prepareStatement("SELECT `UUID`, `ClanName`, `CargoName` FROM `Clan` WHERE `ClanName`='"
                                + SQLClan.clan.get(p.getUniqueId()) + "'");
                ResultSet rs = ps.executeQuery();
                while (rs.next()) {
                    String tempcachename = rs.getString("UUID");
                    int xp = Integer.valueOf(SQLRank.getXpConnection(tempcachename));
                    totalx = totalx + xp;
                }
            } catch (SQLException localSQLException) {
                localSQLException.printStackTrace();

            }
            try {

                connection.createStatement()
                        .executeUpdate("UPDATE `ClanRegistry` SET `Nome`='" + clan.get(p.getUniqueId()) + "',`Tag`='"
                                + tagc + "',`Xp`='" + totalx + "' WHERE `Nome`='"
                                + clan.get(p.getUniqueId()) + "';");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

    }

    public static void removeAllClans(String name) {
        try {
            connection.createStatement().executeUpdate("UPDATE `Clan` SET `UUID`='" + name
                    + "',`ClanName`='Nenhum',`CargoName`='Membro' WHERE `UUID`='" + name + "'");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static Map<UUID, String> clan = new HashMap<UUID, String>();
    public static Map<UUID, String> cargo = new HashMap<UUID, String>();
    public static Map<UUID, String> tag = new HashMap<UUID, String>();

    public static void loadCache(UUID p) {
        String sql = SQLClan.getClanConnection(p.toString());
        if (!sql.equalsIgnoreCase("null")) {
            cargo.put(p, SQLClan.getCargoConnection(p));
            clan.put(p, sql);
            String tagS = getTagConnection(sql);
            if (!tagS.equalsIgnoreCase("Nenhuma")) {
                tag.put(p, tagS);
            }
        } else {
            cargo.put(p, "Membro");
            clan.put(p, "Nenhum");
        }
    }

    public static String getTag(Player p) {
        return cargo.get(p.getUniqueId());
    }

    public static String getClan(Player p) {
        return clan.get(p.getUniqueId());
    }

    public static boolean checkName(String name) {
        return Pattern.compile("[a-zA-Z0-9_]{1,12}").matcher(name).matches();
    }

    public static boolean checkClanPlayer(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM Clan WHERE UUID= ?");
            ps.setString(1, name);
            ResultSet rs = ps.executeQuery();
            boolean user = rs.next();
            rs.close();
            ps.close();
            return user;
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return false;
    }

    public static void registerClan(UUID nick) {
        try {
            String query = "INSERT INTO `Clan`(`UUID`, `ClanName`, `CargoName`) VALUES (?, 'Nenhum', 'Membro')";
            
            PreparedStatement statement = connection.prepareStatement(query);
            ResultSet resultSet = statement.executeQuery("select * from Clan where UUID = '" + nick + "'");
            if (!resultSet.next()) {
            statement.setString(1, nick.toString());
            statement.executeUpdate();
            statement.close();
            }} catch (SQLException e) {
            e.printStackTrace();
        }
    }


}
