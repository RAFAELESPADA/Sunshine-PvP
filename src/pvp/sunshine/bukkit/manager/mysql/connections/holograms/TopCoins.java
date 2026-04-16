package pvp.sunshine.bukkit.manager.mysql.connections.holograms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class TopCoins extends Storage {
    public static Hologram topKillsHologram;

    public static String getTagColor(String playerName) {
        if (playerName == null) {
            return "§7";
        }

        OfflinePlayer offlinePlayer = Bukkit.getOfflinePlayer(UUID.fromString(playerName));

        if (offlinePlayer != null) {
            String groupName = TopDeaths.giveMeADamnUser(offlinePlayer.getUniqueId()).getPrimaryGroup(); // Obtém o grupo do jogador do banco de dados

            if ("dono".equalsIgnoreCase(groupName)) {
                return "§4";
            } else if ("subdono".equalsIgnoreCase(groupName)) {
                return "§c";
            }   else if ("admin".equalsIgnoreCase(groupName)) {
                    return "§c";
            } else if ("coord".equalsIgnoreCase(groupName)) {
                return "§c";
            } else if ("mod+".equalsIgnoreCase(groupName)) {
                return "§5";
            } else if ("mod".equalsIgnoreCase(groupName)) {
                return "§5";

            } else if ("trial".equalsIgnoreCase(groupName)) {
                return "§5";
            } else if ("helper".equalsIgnoreCase(groupName)) {
                return "§9";
            } else if ("builder".equalsIgnoreCase(groupName)) {
                return "§9";
            } else if ("investidor".equalsIgnoreCase(groupName)) {
                return "§2";

            } else if ("youtuber+".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("youtuber".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("tiktoker".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("streamer".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("miniyt".equalsIgnoreCase(groupName)) {
                return "§b";
            } else if ("nitro".equalsIgnoreCase(groupName)) {
                return "§d";
            } else if ("beta".equalsIgnoreCase(groupName)) {
                return "§1";

            } else if ("slower".equalsIgnoreCase(groupName)) {
                return "§a";

            } else if ("imperador".equalsIgnoreCase(groupName)) {
                return "§a";

            } else if ("paladino".equalsIgnoreCase(groupName)) {
                return "§a";

            } else if ("lord".equalsIgnoreCase(groupName)) {
                return "§a";

            } else if ("cavaleiro".equalsIgnoreCase(groupName)) {
                return "§a";
            } else if ("default".equalsIgnoreCase(groupName)) {
                return "§7";
            }
        }

        return "§7";
    }


    private static List<String> getTopCoins() {
        List<String> topCoins = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PvP ORDER BY Coins DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                String playerName = rs.getString("UUID");

                String Name = Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getName();
                String tagColor = "";
                if (playerName == null) {
                   tagColor = "§7" ;
                   Name = "Ninguém";
                } else {
                   
                
                    tagColor = getTagColor(playerName);
                    }
                
                topCoins.add("§e" + index + "º §7- " + tagColor + Name + " §f- Coins: §6" + SunshineFormat.format(rs.getInt("Coins")));
                index++;
            }
            if (topCoins.isEmpty()) {
                topCoins.add("§e1º §7- Carregando...");
                topCoins.add("§e2º §7- Carregando...");
                topCoins.add("§e3º §7- Carregando...");
                topCoins.add("§e4º §7- Carregando...");
                topCoins.add("§e5º §7- Carregando...");
                topCoins.add("§e6º §7- Carregando...");
                topCoins.add("§e7º §7- Carregando...");
                topCoins.add("§e8º §7- Carregando...");
                topCoins.add("§e9º §7- Carregando...");
                topCoins.add("§e10º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topCoins.add("§e" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topCoins;
    }

    public static void updateHologram() {
        List<String> hologramTopCoins = getTopCoins();

        World world = Bukkit.getWorld("lobbypvp2");
        double x = 524.307;
        double y =  17.84010;
        double z =  620.415;
        Location hologramLocation = new Location(world, x, y, z);
        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topKillsHologram == null) {
                topKillsHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topKillsHologram.teleport(hologramLocation);
                topKillsHologram.clearLines();

            }
            topKillsHologram.appendTextLine("§e§l§nTOP 10 COINS");
            topKillsHologram.appendTextLine("");
            for (String line : hologramTopCoins) {
                topKillsHologram.appendTextLine(line);
            }
        });
    }
}