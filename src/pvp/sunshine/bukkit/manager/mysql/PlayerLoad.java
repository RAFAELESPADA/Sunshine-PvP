package pvp.sunshine.bukkit.manager.mysql;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scoreboard.DisplaySlot;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.team.BuildCMD;
import pvp.sunshine.bukkit.manager.mysql.connections.*;

public class PlayerLoad implements Listener {

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        Cooldown.remove(e.getPlayer());
        RegisterAbility.removeAbility(e.getPlayer());
        updateDataAsync(e.getPlayer());
        clearPlayerScoreboard(e.getPlayer());
        BuildCMD.buildModePlayers.remove(e.getPlayer());
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        updateDataAsync(e.getPlayer());
        clearPlayerScoreboard(e.getPlayer());
        Cooldown.remove(e.getPlayer());
        RegisterAbility.removeAbility(e.getPlayer());
        BuildCMD.buildModePlayers.remove(e.getPlayer());
    }

    @EventHandler
    public void onPlayerCache(AsyncPlayerPreLoginEvent e) {
        try {
            if (!SQLRank.Experience.containsKey(e.getUniqueId())) {
                SQLRank.Experience.put(e.getUniqueId(), SQLRank.getXpConnection(e.getUniqueId().toString()));
            }
            SQLPvP.registerPvP(e.getUniqueId(), e.getName());
            SQLRank.registerXP(e.getUniqueId());

            SQL1v1.register1v1(e.getUniqueId(), e.getName());
            SQLShop.registerShop(e.getUniqueId());
            SQLClan.registerClan(e.getUniqueId());
            SQLClan.registerClan("Nenhum", "Nenhuma", 0);
            SQLPvP.loadCache(e.getUniqueId());
            SQL1v1.loadCache(e.getUniqueId());
            SQLShop.cacheLoad(e.getUniqueId());
            SQLClan.loadCache(e.getUniqueId());
        } catch (Exception ex) {
            BukkitMain.getInstance().getLogger()
                    .severe("Erro ao carregar dados do jogador " + e.getName() + ": " + ex.getMessage());
        }
    }


    private void updateDataAsync(final Player player) {
        (new BukkitRunnable() {
            public void run() {
                try {
                    SQLClan.updateData(player);
                    SQLPvP.updateData(player);
                    SQLRank.updateData(player);
                    SQL1v1.updateData(player);
                    SQLShop.updateShop(player);
                } catch (Exception ex) {
                	ex.printStackTrace();
                    BukkitMain.getInstance().getLogger()
                            .severe("Erro ao atualizar dados do jogador " + player.getName() + ": " + ex.getMessage());
                }
            }
        }).runTaskAsynchronously((Plugin) BukkitMain.getInstance());
    }

    private void clearPlayerScoreboard(Player player) {
        player.getScoreboard().clearSlot(DisplaySlot.SIDEBAR);
    }
}
