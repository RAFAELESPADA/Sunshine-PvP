package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class Viking implements Listener {
	@EventHandler
	public void onEntityDamagebyEventAnchor(EntityDamageByEntityEvent e) {
		if (e.getEntity() instanceof Player && e.getDamager() instanceof Player
				&& ((Player) e.getDamager()).getItemInHand().getType() == Material.WOOD_AXE) {

		}
	}
}
