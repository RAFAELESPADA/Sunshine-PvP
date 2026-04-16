package pvp.sunshine.bukkit.ability.register.kits;

import java.util.Iterator;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;

import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Stomper implements Listener {
	@EventHandler
	public void KitEvent(final EntityDamageEvent e) {
		if (!(e.getEntity() instanceof Player)) {
			return;
		}
		final Player p = (Player)e.getEntity();
		if (e.getCause() == EntityDamageEvent.DamageCause.FALL && RegisterAbility.getAbility(p).equalsIgnoreCase("Stomper")) {
			final Iterator<?> localIterator = p.getNearbyEntities(4.0, 1.5, 4.0).iterator();
			while (localIterator.hasNext()) {
				final Entity entidade;
				if ((entidade = (Entity)localIterator.next()) instanceof Player) {
					final Player stompado = (Player)entidade;
					if (e.getDamage() <= 4.0) {
						e.setCancelled(true);
						return;
					}
					if (stompado.isSneaking() || RegisterAbility.getAbility(stompado) == "AntiStomper") {
						stompado.damage(6.0, (Entity)p);
					}
					else {
						stompado.damage(e.getDamage(), (Entity)p);
					}
				}
			}
			e.setDamage(4.0);
		}
	}
}