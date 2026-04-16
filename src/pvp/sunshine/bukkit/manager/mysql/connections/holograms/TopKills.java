package pvp.sunshine.bukkit.manager.mysql.connections.holograms;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class TopKills extends Storage {
    public static Hologram topKillsHologram;

    public static void incrementKills(Player player) {
        try (PreparedStatement ps = connection.prepareStatement("UPDATE PvP SET Kills = Kills + 1 WHERE UUID = ?")) {
            ps.setString(1, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
    public static User giveMeADamnUser(UUID uniqueId) {
      	 RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);
      		if (provider == null) return null; 
      		    LuckPerms api = provider.getProvider();
          UserManager userManager = api.getUserManager();
          CompletableFuture<User> userFuture = userManager.loadUser(uniqueId);

          return userFuture.join(); 
      		}
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


    private static List<String> getTopKills() {
        List<String> topKills = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PvP ORDER BY Kills DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                String playerName = rs.getString("UUID");
                String tagColor = "";

                String Name = Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getName();
                if (playerName == null) {
                   tagColor = "§7" ;

                   Name = "Ninguém";
                } else {
                   
                
                    tagColor = getTagColor(playerName);
                    }
                topKills.add("§a" + index + "º §7- " + tagColor + Name + " §f- Kills: §e" + SunshineFormat.format(rs.getInt("Kills")));
                index++;
            }
            if (topKills.isEmpty()) {
                topKills.add("§a1º §7- Carregando...");
                topKills.add("§a2º §7- Carregando...");
                topKills.add("§a3º §7- Carregando...");
                topKills.add("§a4º §7- Carregando...");
                topKills.add("§a5º §7- Carregando...");
                topKills.add("§a6º §7- Carregando...");
                topKills.add("§a7º §7- Carregando...");
                topKills.add("§a8º §7- Carregando...");
                topKills.add("§a9º §7- Carregando...");
                topKills.add("§a10º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topKills.add("§a" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topKills;
    }

    public static void updateHologram() {
        List<String> hologramTopKills = getTopKills();

        World world = Bukkit.getWorld("lobbypvp2");
        double x = 520.352;
        double y =  18.0000000;
        double z =   614.724;
        Location hologramLocation = new Location(world, x, y, z);
        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topKillsHologram == null) {
                topKillsHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topKillsHologram.teleport(hologramLocation);
                topKillsHologram.clearLines();

            }
            topKillsHologram.appendTextLine("§a§l§nTOP 10 KILLS");
            topKillsHologram.appendTextLine("");
            for (String line : hologramTopKills) {
                topKillsHologram.appendTextLine(line);
            }
        });
    }
}