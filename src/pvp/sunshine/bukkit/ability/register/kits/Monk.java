package pvp.sunshine.bukkit.ability.register.kits;

import java.util.Random;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Monk implements Listener {
	@EventHandler
	public void aoMonk(final PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			Player jogadorClicado = (Player) e.getRightClicked();
			if (RegisterAbility.getAbility(e.getPlayer()).equalsIgnoreCase("Monk")
					&& e.getPlayer().getItemInHand().getType() == Material.BLAZE_ROD) {
				if (Cooldown.add(e.getPlayer())) {
					e.getPlayer().sendMessage("§c§lCOOLDOWN §fAguarde §c" + Cooldown.cooldown(e.getPlayer())
							+ "s §fpara usar novamente.");
					return;
				}
				int randomN = (new Random()).nextInt(36);
				ItemStack atual = (jogadorClicado.getItemInHand() != null) ? jogadorClicado.getItemInHand().clone()
						: null;
				ItemStack random = (jogadorClicado.getInventory().getItem(randomN) != null)
						? jogadorClicado.getInventory().getItem(randomN).clone()
						: null;
				if (random == null) {
					jogadorClicado.getInventory().setItem(randomN, atual);
					jogadorClicado.setItemInHand(null);
				} else {
					jogadorClicado.getInventory().setItem(randomN, atual);
					jogadorClicado.getInventory().setItemInHand(random);
				}
				jogadorClicado.sendMessage("§e§KIT! §fSeu inventário foi bagunçado por um Monk!");
				e.getPlayer().sendMessage("§a§lKIT §fMonkado!");
				Cooldown.add(e.getPlayer(), 20);
				(new BukkitRunnable() {
					public void run() {
						e.getPlayer().sendMessage("§a§lCOOLDOWN §fAgora você pode usar seu kit novamente.");
					}
				}).runTaskLater((Plugin) BukkitMain.getInstance(), 400L);
			}
		}
	}
}