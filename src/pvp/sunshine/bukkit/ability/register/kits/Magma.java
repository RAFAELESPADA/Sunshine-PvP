package pvp.sunshine.bukkit.ability.register.kits;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Magma implements Listener {
	@EventHandler
	public void onPlayerMoveEventMagma(PlayerMoveEvent e) {
		if (RegisterAbility.getAbility(e.getPlayer()).equalsIgnoreCase("Magma")
				&& (e.getPlayer().getLocation().getBlock().getType() == Material.STATIONARY_WATER
						|| e.getPlayer().getLocation().getBlock().getType() == Material.WATER)) {
			e.getPlayer().damage(1.0D);
		}
	}

	@EventHandler
	public void onEntityDamageEventMagma(EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		if (RegisterAbility.getAbility((Player) e.getEntity()) == "Magma"
				&& (e.getCause() == EntityDamageEvent.DamageCause.LAVA
						|| e.getCause() == EntityDamageEvent.DamageCause.FIRE
						|| e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void onEntityDamageByEntityEventMagma(EntityDamageByEntityEvent e) {
		if (!(e.getEntity() instanceof Player) && e.getDamager() instanceof Player) {
			return;
		}
		if (RegisterAbility.getAbility(e.getDamager().getName()).equalsIgnoreCase("Magma")
				&& ((Player) e.getDamager()).getInventory().getItemInHand() != null
				&& (new Random()).nextInt(100) <= 46)
			e.getEntity().setFireTicks(50);
	}
}
