package pvp.sunshine.bukkit.manager.mysql.connections.holograms;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.plugin.Plugin;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.Storage;


public class TopClans
        extends Storage {
    private static Hologram topKillsHologram;

    private static List<String> getTopClans() {
        List<String> topClans = new LinkedList<>();
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM ClanRegistry ORDER BY Xp DESC LIMIT 10");
             ResultSet rs = ps.executeQuery()) {
            int index = 1;
            while (rs.next()) {
                String clan = rs.getString("Nome");
                if (!clan.equalsIgnoreCase("Nenhum")) {
                    topClans.add("§6" + index + "º §7- " + rs.getString("Nome") + " §f- XP: §e" + SunshineFormat.format(Integer.parseInt(rs.getString("Xp"))));
                    index++;
                }
            }
            if (topClans.isEmpty()) {
                topClans.add("§61º §7- Carregando...");
                topClans.add("§62º §7- Carregando...");
                topClans.add("§63º §7- Carregando...");
                topClans.add("§64º §7- Carregando...");
                topClans.add("§65º §7- Carregando...");
                topClans.add("§66º §7- Carregando...");
                topClans.add("§67º §7- Carregando...");
                topClans.add("§68º §7- Carregando...");
                topClans.add("§69º §7- Carregando...");
                topClans.add("§610º §7- Carregando...");
            } else {
                for (int i = index; i <= 10; i++) {
                    topClans.add("§6" + i + "º §7- Carregando...");
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return topClans;
    }

    public static void updateHologram() {
        List<String> hologramTopClans = getTopClans();

        World world = Bukkit.getWorld("lobbypvp2");
        double x = 510.556;
        double y =  18.63379;
        double z = 633.094;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask((Plugin) BukkitMain.getInstance(), () -> {
            if (topKillsHologram == null) {
                topKillsHologram = HologramsAPI.createHologram((Plugin) BukkitMain.getInstance(), hologramLocation);
            } else {
                topKillsHologram.teleport(hologramLocation);
                topKillsHologram.clearLines();
            }
            topKillsHologram.appendTextLine("§6§l§nTOP 10 CLANS");
            topKillsHologram.appendTextLine("");
            for (String line : hologramTopClans)
                topKillsHologram.appendTextLine(line);
        });
    }
}
