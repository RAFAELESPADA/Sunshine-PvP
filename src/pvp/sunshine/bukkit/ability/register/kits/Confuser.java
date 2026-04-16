package pvp.sunshine.bukkit.ability.register.kits;

import java.util.Random;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Confuser implements Listener {
	@EventHandler
	public void onEntityDamage(EntityDamageByEntityEvent e) {
		if (e.getDamager() instanceof Player && e.getEntity() instanceof LivingEntity) {
			if (RegisterAbility.getAbility((Player) e.getDamager()).equalsIgnoreCase("Confuser")) {
				Random rand = new Random();
				int percent = rand.nextInt(100);
				if (percent <= 33) {
					((LivingEntity) e.getEntity())
							.addPotionEffect(new PotionEffect(PotionEffectType.CONFUSION, 300, 0));
					return;
				}
				return;
			}
			return;
		}
	}
}
