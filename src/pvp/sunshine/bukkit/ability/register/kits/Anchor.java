package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Anchor implements Listener {
	
	@EventHandler
	public void onEntityDamageEventAnchor(final EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (!(e.getDamager() instanceof Player)) {
			return;
		}
		if (RegisterAbility.getAbility((Player) e.getEntity()).equalsIgnoreCase("Anchor")) {
			e.getDamager().setVelocity(new Vector());
			e.getEntity().setVelocity(new Vector());
			(new BukkitRunnable() {
				public void run() {
					e.getDamager().setVelocity(new Vector());
					e.getEntity().setVelocity(new Vector());
				}
			}).runTaskLater((Plugin) BukkitMain.getInstance(), 0L);
		} else if (RegisterAbility.getAbility((Player) e.getDamager()).equalsIgnoreCase("Anchor")) {
			e.getDamager().setVelocity(new Vector());
			e.getEntity().setVelocity(new Vector());
			(new BukkitRunnable() {
				public void run() {
					e.getDamager().setVelocity(new Vector());
					e.getEntity().setVelocity(new Vector());
				}
			}).runTaskLater((Plugin) BukkitMain.getInstance(), 0L);
		}
	}
}
