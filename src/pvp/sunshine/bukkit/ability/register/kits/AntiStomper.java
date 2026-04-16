package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class AntiStomper implements Listener {
	@EventHandler
	public void aogalinha(EntityDamageEvent e) {
		if (e.getEntity() instanceof Player && e.getCause() == EntityDamageEvent.DamageCause.FALL
				&& RegisterAbility.getAbility((Player) e.getEntity()) == "AntiStomper")
			e.setDamage(6.0D);
	}
}