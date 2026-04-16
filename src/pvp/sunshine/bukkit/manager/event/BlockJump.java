package pvp.sunshine.bukkit.manager.event;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Effect;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class BlockJump implements Listener {

	ArrayList<String> jump = new ArrayList<>();
	List<String> nofalldamage = new ArrayList<>();

	@EventHandler
	public void onPlayerMoveEventJumps(PlayerMoveEvent e) {
		if (e.getTo().getBlock().getRelative(BlockFace.DOWN).getType() == Material.SPONGE) {
			e.getPlayer().playSound(e.getTo().getBlock().getLocation(), Sound.SLIME_WALK2, 6.0F, 1.0F);
			e.getPlayer().getEyeLocation().getWorld().playEffect(e.getPlayer().getLocation(), Effect.SMOKE, 10);
			e.getPlayer().getLocation().getWorld().playEffect(e.getPlayer().getLocation(), Effect.ENDER_SIGNAL, 8);
			e.getPlayer().setVelocity(e.getPlayer().getLocation().getDirection().multiply(0).setY(5));
			if (!nofalldamage.contains(e.getPlayer().getName())) {
				nofalldamage.add(e.getPlayer().getName());
			}
			return;
		}
	}

	@EventHandler
	public void onEntityDamageEventJumps(EntityDamageEvent event) {
		if ((event.getEntity() instanceof Player)) {
			Player player = (Player) event.getEntity();
			if ((nofalldamage.contains(player.getName()))
					&& (event.getCause().equals(EntityDamageEvent.DamageCause.FALL))) {
				event.setCancelled(true);
				nofalldamage.remove(player.getName());
			}
		}
	}

}
