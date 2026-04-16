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
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.model.user.UserManager;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;

public class TopLoses extends Storage {

    private static Hologram topLosesHologram;
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

    public static void incrementLoses(Player player) {
    	new BukkitRunnable() {	
			public void run() {	
		
        try (PreparedStatement ps = connection.prepareStatement("UPDATE 1v1 SET Loses = Loses + 1 WHERE UUID = ?")) {
            ps.setString(1, player.getUniqueId().toString());
            ps.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }}}.runTaskAsynchronously(BukkitMain.getInstance());
    }

    private static List<String> getTopLoses() {
        List<String> topLoses = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM 1v1 ORDER BY Loses DESC LIMIT 10");
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
                topLoses.add("§6" + index + "º §7- " + tagColor + Name + " §f- Derrotas: §e" + SunshineFormat.format(rs.getInt("Loses")));
                index++;
            }
            if (topLoses.isEmpty()) {
                topLoses.add("§61º §7- Carregando...");
                topLoses.add("§62º §7- Carregando...");
                topLoses.add("§63º §7- Carregando...");
                topLoses.add("§64º §7- Carregando...");
                topLoses.add("§65º §7- Carregando...");
                topLoses.add("§66º §7- Carregando...");
                topLoses.add("§67º §7- Carregando...");
                topLoses.add("§68º §7- Carregando...");
                topLoses.add("§69º §7- Carregando...");
                topLoses.add("§610º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topLoses.add("§6" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topLoses;
    }

    public static void updateHologram() {
        List<String> hologramTopLoses = getTopLoses();
        World world = Bukkit.getWorld("1v1");
        double x = -21.708;
        double y =  47.98041;
        double z =  7.343;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (topLosesHologram == null) {
                topLosesHologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                topLosesHologram.teleport(hologramLocation);
                topLosesHologram.clearLines();
            }
            topLosesHologram.appendTextLine("§c§l§nTOP 10 LOSES");
            topLosesHologram.appendTextLine("");
            for (String line : hologramTopLoses) {
                topLosesHologram.appendTextLine(line);
            }
        });
    }
}