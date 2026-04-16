package pvp.sunshine.bukkit.manager.mysql.connections;

import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.manager.mysql.Storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class SQLShop extends Storage {

    public static Map<UUID, String> cacheshop = new HashMap<>();

    public static String getKits(Player p) {
        return cacheshop.get(p.getUniqueId());
    }

    public static void createTable() {
        try {
            PreparedStatement ps = getStatement("CREATE TABLE IF NOT EXISTS ShopKit (UUID VARCHAR(100), Kits VARCHAR(300))");
            ps.executeUpdate();
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    public static String getKits(UUID name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ShopKit WHERE UUID= '" + name + "'");
            ResultSet rs = ps.executeQuery();
            rs.next();
            String kits = rs.getString("Kits");
            rs.close();
            ps.close();
            return kits;
        } catch (Exception localException) {
        }
        return "Nenhum";
    }

    public static void registerShop(UUID nick) {
        try {
           
            PreparedStatement ps = getStatement("SELECT * FROM ShopKit WHERE UUID= '" + nick + "'");
            ResultSet rs = ps.executeQuery();
            if (!rs.next()) {
            	 connection.createStatement()
                 .executeUpdate("INSERT INTO `ShopKit`(`UUID`, `Kits`) VALUES ('" + nick + "','Nenhum')");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean checkShop(String name) {
        try {
            PreparedStatement ps = getStatement("SELECT * FROM ShopKit WHERE UUID='" + name + "'");
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

    public static void updateShop(Player p) {
        try {
            if (cacheshop.containsKey(p.getUniqueId())) {
                connection.createStatement().executeUpdate("UPDATE `ShopKit` SET `UUID`='" + p.getUniqueId()
                        + "',`Kits`='" + getKits(p) + "' WHERE `UUID`='" + p.getUniqueId() + "';");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            cacheshop.remove(p.getUniqueId());
        }
    }

    public static void cacheLoad(UUID name) {
        String kits = getKits(name);
        if (kits.equalsIgnoreCase("null")) {
            cacheshop.put(name, "nenhum");
        } else {
            cacheshop.put(name, getKits(name).toLowerCase());
        }
    }

    public static void addkit(Player p, String kit) {
        cacheshop.replace(p.getUniqueId(), cacheshop.get(p.getUniqueId()) + " " + kit);
    }
}
