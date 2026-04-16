package pvp.sunshine.bukkit.manager.mysql.connections;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.Storage;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.UUID;

public class SQLAuth extends Storage {

    public static final String TABLE_NAME = "Login";
    public static final String COLUMN_UUID = "UUID";
    public static final String COLUMN_NAME = "NAME";
    public static final String COLUMN_PASSWORD = "PASSWORD";

    public static ArrayList<String> cacheLogin = new ArrayList<>();

    public static String getPlayerIP(UUID playerId) {
        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT IP FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, playerId.toString());
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString("IP");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void printAccountsOnSameIP(CommandSender sender, OfflinePlayer player) {
        UUID playerId = player.getUniqueId();
        String playerIP = getPlayerIP(playerId);

        if (playerIP != null) {
            try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE IP=?")) {
                stm.setString(1, playerIP);

                try (ResultSet rs = stm.executeQuery()) {
                    while (rs.next()) {
                        String playerName = rs.getString(COLUMN_NAME);
                        sender.sendMessage(" §e- " + playerName);
                    }
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            sender.sendMessage("§c§lERRO §fO jogador não está cadastrado no banco de dados.");
        }
    }

    public static boolean checkAccount(OfflinePlayer player) {
        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, player.getUniqueId().toString());
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkAuth(UUID id, Player p) {
        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, id.toString());
            try (ResultSet rs = stm.executeQuery()) {
                return rs.next();
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPassword(UUID id, Player p, String password) {
        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, id.toString());
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String hashedPassword = rs.getString(COLUMN_PASSWORD);
                    return checkSHA1(password, hashedPassword);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean registerAccount(UUID id, Player p, String password, String name, String ip) {
        try {
            String hashedPassword = hashSHA1(password);
            try (PreparedStatement stm = Storage.connection.prepareStatement("INSERT INTO " + TABLE_NAME + " (" + COLUMN_UUID + ", " + COLUMN_NAME + ", " + COLUMN_PASSWORD + ", IP) VALUES (?, ?, ?, ?)")) {
                stm.setString(1, id.toString());
                stm.setString(2, name);
                stm.setString(3, hashedPassword);
                stm.setString(4, ip);
                stm.executeUpdate();
                cacheLogin.add(p.getName());
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    public static boolean isAuthenticated(UUID id, Player p) {
        return cacheLogin.contains(p.getName());
    }

    public static void updateIP(UUID id, Player p) {
        String playerIP = p.getAddress().getAddress().getHostAddress();
        try (PreparedStatement stm = Storage.connection.prepareStatement("UPDATE " + TABLE_NAME + " SET IP=? WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, playerIP);
            stm.setString(2, id.toString());
            stm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static void autoAuthenticate(Player player) {
        UUID playerId = player.getUniqueId();
        String playerIP = player.getAddress().getAddress().getHostAddress();

        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=? AND IP=?")) {
            stm.setString(1, playerId.toString());
            stm.setString(2, playerIP);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    cacheLogin.add(player.getName());
                    player.sendMessage("§a§lLOGIN §fVocê foi automaticamente autenticado.");
                    new BukkitRunnable() {
                        @Override
                        public void run() {
                            player.sendMessage("§a§lCONNECT §fConectando ao Lobby...");
                            BukkitMain.sendServer(player, "Lobby");
                        }
                    }.runTaskLater(BukkitMain.getInstance(), 20L);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void checkIPAndRedirect(Player player) {
        UUID playerId = player.getUniqueId();
        String playerIP = player.getAddress().getAddress().getHostAddress();

        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, playerId.toString());

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    String storedIP = rs.getString("IP");
                    if (!playerIP.equals(storedIP)) {
                        player.sendMessage("§c§lLOGIN §fAlerta de Segurança: Detectamos uma atividade suspeita na sua conta. Por favor, confirme sua identidade usando /login (sua senha) para garantir a segurança da sua conta.");

                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public static void completeLogin(UUID id, Player p, String password) {
        String hashedPassword = getPassword(id, p);

        if (hashedPassword != null && checkSHA1(password, hashedPassword)) {
            cacheLogin.add(p.getName());
            updateIP(id, p);
        }
    }

    public static boolean checkDoubleIP(Player player) {
        String playerIP = player.getAddress().getAddress().getHostAddress();

        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT COUNT(*) AS count FROM " + TABLE_NAME + " WHERE IP=?")) {
            stm.setString(1, playerIP);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    if (count >= 2) {
                        player.kickPlayer("§c§lDOUBLE-IP \n \n§fDetectamos um possível duplo IP em sua conta.\n§fPor favor, tente reiniciar seu roteador\n§fE assim que possível, retorne para o servidor de login. \n");
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static boolean checkTripleIP(Player player) {
        String playerIP = player.getAddress().getAddress().getHostAddress();

        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT COUNT(*) AS count FROM " + TABLE_NAME + " WHERE IP=?")) {
            stm.setString(1, playerIP);

            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    int count = rs.getInt("count");
                    if (count >= 3) {
                        player.kickPlayer("§c§lTRIPLE-IP \n \n§fDetectamos um possível duplo IP em sua conta.\n§fPor favor, tente reiniciar seu roteador\n§fE assim que possível, retorne para o servidor de login. \n");
                        return true;
                    }
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }


    public static boolean changePassword(UUID id, Player p, String oldPassword, String newPassword) {
        if (checkPassword(id, p, oldPassword)) {
            try {
                String hashedPassword = hashSHA1(newPassword);
                try (PreparedStatement stm = Storage.connection.prepareStatement("UPDATE " + TABLE_NAME + " SET " + COLUMN_PASSWORD + "=? WHERE " + COLUMN_UUID + "=?")) {
                    stm.setString(1, hashedPassword);
                    stm.setString(2, id.toString());
                    stm.executeUpdate();
                    return true;
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        return false;
    }



    public static String getPassword(UUID id, Player p) {
        try (PreparedStatement stm = Storage.connection.prepareStatement("SELECT * FROM " + TABLE_NAME + " WHERE " + COLUMN_UUID + "=?")) {
            stm.setString(1, id.toString());
            try (ResultSet rs = stm.executeQuery()) {
                if (rs.next()) {
                    return rs.getString(COLUMN_PASSWORD);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    private static String hashSHA1(String input) {
        try {
            MessageDigest sha1 = MessageDigest.getInstance("SHA-1");
            byte[] hashedBytes = sha1.digest(input.getBytes());
            return convertByteArrayToHexString(hashedBytes);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return null;
        }
    }

    private static boolean checkSHA1(String input, String hashed) {
        return hashSHA1(input).equals(hashed);
    }

    private static String convertByteArrayToHexString(byte[] array) {
        StringBuilder stringBuilder = new StringBuilder();
        for (byte b : array) {
            stringBuilder.append(String.format("%02x", b));
        }
        return stringBuilder.toString();
    }
}
