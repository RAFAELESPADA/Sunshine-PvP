package pvp.sunshine.bukkit.ability.register.kits;

import java.util.Random;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Viper implements Listener {
	@EventHandler
	public void onEntityDamageByEntityEventViper(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof Player
				&& RegisterAbility.getAbility((Player) e.getDamager()).equalsIgnoreCase("Viper")
				&& (new Random()).nextInt(100) >= 67)
			((Player) e.getEntity()).addPotionEffect(new PotionEffect(PotionEffectType.POISON, 80, 1));
	}
}
