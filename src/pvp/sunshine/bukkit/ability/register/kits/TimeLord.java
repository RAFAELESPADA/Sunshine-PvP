package pvp.sunshine.bukkit.ability.register.kits;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.Plugin;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class TimeLord implements Listener {
	public static ArrayList<String> frozenPlayers = new ArrayList<>();

	@EventHandler
	public void onTimeLord(PlayerInteractEvent e) {
		if (e.getPlayer().getItemInHand().getType() == Material.WATCH
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& RegisterAbility.getAbility(e.getPlayer()) == "TimeLord") {
			if (Cooldown.add(e.getPlayer())) {
				e.getPlayer().sendMessage(
						"§c§lCOOLDOWN §fAguarde §c" + Cooldown.cooldown(e.getPlayer()) + "s §fpara usar novamente.");
				return;
			}
			for (Entity todos : e.getPlayer().getNearbyEntities(5.0D, 5.0D, 5.0D)) {
				if (todos instanceof Player) {
					frozenPlayers.add(((Player) todos).getName());
					((Player) todos).sendMessage("§e§lKIT §fVocê foi congelado por um TimeLord!");

					Cooldown.add(e.getPlayer(), 35);
					e.getPlayer().sendMessage("§a§lKIT §fVocê congelou todos a sua volta!");

					Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
						public void run() {
							TimeLord.frozenPlayers.remove(todos.getName());
						}
					}, 10*20L);
				}
			}
		}
	}

	@EventHandler
	public void onTimeLordado(PlayerMoveEvent e) {
		if (frozenPlayers.contains(e.getPlayer().getName()))
			e.getPlayer().teleport((Entity) e.getPlayer());
	}

	@EventHandler
	public void onDeath(PlayerDeathEvent e) {
		frozenPlayers.remove(e.getEntity().getName());
	}
}