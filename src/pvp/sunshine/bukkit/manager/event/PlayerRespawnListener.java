package pvp.sunshine.bukkit.manager.event;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.duels.Battle;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class PlayerRespawnListener implements Listener {

    @EventHandler
    public void onRespawn(PlayerRespawnEvent e) {
        if (!Battle.in1v1.contains(e.getPlayer().getUniqueId())) {
            if (RegisterAbility.getAbility(e.getPlayer()).equalsIgnoreCase("Lava")) {
            	Player p = e.getPlayer();
            	p.getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                
                PvPUtil.InventoryAdapter(e.getPlayer());
                PvPUtil.giveSoup(e.getPlayer(), 36);
                new BukkitRunnable() {
                    @Override
                    public void run() {
                    	p.getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                        
                    }
                }.runTaskLater(BukkitMain.getInstance(), 3);

                return;
            }
            e.getPlayer().getActivePotionEffects()
                    .forEach(potionEffect -> e.getPlayer().removePotionEffect(potionEffect.getType()));
            PvPUtil.spawnItem(e.getPlayer());
            Flag.setProtection(e.getPlayer(), true);

            Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000); e.getPlayer().teleport(spawnLocation);
            WarpFpsProtection.reiniciarContadorFps(e.getPlayer());
        }
    }
}
