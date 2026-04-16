package pvp.sunshine.bukkit.ability.register.kits;

import java.util.ArrayList;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockFace;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.util.Vector;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.event.CombatLog;

public class Kangaroo implements Listener {
	public static ArrayList<Player> kanga = new ArrayList<>();

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		if (RegisterAbility.getAbility(event.getPlayer()).equalsIgnoreCase("Kangaroo")) {
			if (event.getPlayer().getItemInHand().getType() == Material.FIREWORK
					&& (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
							|| event.getAction() == Action.RIGHT_CLICK_BLOCK
							|| event.getAction() == Action.RIGHT_CLICK_AIR)
					&& CombatLog.emcombate.containsKey(event.getPlayer())) {
				event.setCancelled(true);
				event.getPlayer().sendMessage("§c§lCOMBATE §fNão é possível utilizar seu kit em combate!");
				return;
			}
			if (event.getPlayer().getItemInHand().getType() == Material.FIREWORK
					&& (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
							|| event.getAction() == Action.RIGHT_CLICK_BLOCK
							|| event.getAction() == Action.RIGHT_CLICK_AIR)) {
				event.setCancelled(true);
				if (!kanga.contains(event.getPlayer())) {
					if (!event.getPlayer().isSneaking()) {
						event.getPlayer().setFallDistance(-1.0F);
						Vector vector = event.getPlayer().getEyeLocation().getDirection();
						vector.multiply(0.5F);
						vector.setY(1.0D);
						event.getPlayer().setVelocity(vector);
					} else {
						event.getPlayer().setFallDistance(-1.0F);
						Vector vector = event.getPlayer().getEyeLocation().getDirection();
						vector.multiply(2.3F);
						vector.setY(0.5D);
						event.getPlayer().setVelocity(vector);
					}
					kanga.add(event.getPlayer());

				}
			}
		}
	}

	@EventHandler
	public void onMove(PlayerMoveEvent event) {
		if (kanga.contains(event.getPlayer())) {
			if (event.getPlayer().getLocation().getBlock().getType() != Material.AIR
					|| event.getPlayer().getLocation().getBlock().getRelative(BlockFace.DOWN).getType() != Material.AIR)
				kanga.remove(event.getPlayer());
		}
	}

	@EventHandler
	public void onDamage(EntityDamageEvent event) {
		if (event.getEntity() instanceof Player) {
			Player player = (Player) event.getEntity();
			if (RegisterAbility.getAbility(player).equalsIgnoreCase("Kangaroo") && event.getEntity() instanceof Player
					&& event.getCause() == EntityDamageEvent.DamageCause.FALL
					&& player.getInventory().contains(Material.FIREWORK))
				event.setDamage(3.0D);
		}
	}
}