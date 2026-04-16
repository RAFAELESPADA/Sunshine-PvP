package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Thor implements Listener {
	private static final double STRIKE_RADIUS = 10.0;

	@EventHandler
	public void onPlayerInteractEventThor(PlayerInteractEvent e) {
		if (RegisterAbility.getAbility(e.getPlayer()).equalsIgnoreCase("Thor")
				&& e.getAction() == Action.RIGHT_CLICK_BLOCK
				&& e.getPlayer().getItemInHand().getType() == Material.GOLD_AXE) {

			if (Cooldown.add(e.getPlayer())) {
				e.getPlayer().sendMessage("§c§lCOOLDOWN §fAguarde §c" + Cooldown.cooldown(e.getPlayer()) + "s §fpara usar novamente.");
				return;
			}

			Location clickedLocation = e.getClickedBlock().getLocation();

			for (Entity entity : e.getPlayer().getWorld().getNearbyEntities(clickedLocation, STRIKE_RADIUS, STRIKE_RADIUS, STRIKE_RADIUS)) {
				if (entity instanceof Player) {
					Player player = (Player) entity;
					if (!player.equals(e.getPlayer())) {
						player.damage(7.0);
					}
				}
			}

			Cooldown.add(e.getPlayer(), 10);
			e.getPlayer().getWorld().strikeLightningEffect(clickedLocation);
			e.getPlayer().getWorld().strikeLightningEffect(clickedLocation);
			e.setCancelled(true);

			Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), () -> {
				e.getPlayer().sendMessage("§a§lCOOLDOWN §fAgora você pode usar seu kit novamente.");
			}, 200L);
		}
	}
}
