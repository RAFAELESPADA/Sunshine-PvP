package net.kombopvp.kit2;

import java.util.Random;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kombopvp.pvp.kit.KitHandler2;
import net.kombopvp.pvp.kit.KitManager2;

public class Viper extends KitHandler2 {
	
	@EventHandler(ignoreCancelled = true)
	public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
		if (!(event.getEntity() instanceof Player) 
				|| (!(event.getDamager() instanceof Player))) {
			return;
		}
		
		Player damager = (Player) event.getDamager();
		if (!KitManager2.getPlayer(damager.getName()).haskit2(this)) {
			return;
		}
		if (event.isCancelled()) {
			return;
		}
		Player victim = (Player) event.getEntity();
		int percentage = new Random().nextInt(100);
		
		if (percentage < 15) {
			victim.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 4 * 20, 1));
		}
	}
}
