package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Switcher implements Listener {
	@EventHandler
	public void snowball(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Snowball && e.getEntity() instanceof Player) {
			Snowball s = (Snowball) e.getDamager();
			if (s.getShooter() instanceof Player) {
				Player shooter = (Player) s.getShooter();
				if (RegisterAbility.getAbility(shooter).equalsIgnoreCase("Switcher")) {
					Location shooterLoc = shooter.getLocation();
					shooter.teleport(e.getEntity().getLocation());
					e.getEntity().teleport(shooterLoc);
					shooter.getPlayer().getWorld().playEffect(shooterLoc, Effect.ENDER_SIGNAL, 10);
					shooter.getPlayer().getWorld().playEffect(shooterLoc, Effect.EXTINGUISH, 10);
					shooter.getWorld().playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 1.0F, 1.2F);
				}
			}
		}
	}
}
