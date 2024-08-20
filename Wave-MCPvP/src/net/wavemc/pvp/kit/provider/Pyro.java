package net.wavemc.pvp.kit.provider;

import java.util.HashSet;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Fireball;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.wavemc.core.bukkit.item.ItemBuilder;
import net.wavemc.pvp.WavePvP;
import net.wavemc.pvp.kit.WaveKit;
import net.wavemc.pvp.kit.KitHandler;
import net.wavemc.pvp.kit.KitManager;

public class Pyro extends KitHandler {

	   @Override
	    public void execute(Player player) {
	        super.execute(player);

	   }
	   
@EventHandler(priority = EventPriority.HIGH)
public void onInteract(EntityDamageEvent event) {
	if (!(event.getEntity() instanceof Player)) {
		return;
	}
	Player p = (Player)event.getEntity();
	if (!KitManager.getPlayer(p.getName()).hasKit(this)) {
		return;
	}	
	if (event.getCause() == DamageCause.ENTITY_EXPLOSION) {
		event.setCancelled(true);
	}
}
@EventHandler(priority = EventPriority.LOWEST)
public void onInteract(PlayerInteractEvent event) {
	Player p = event.getPlayer();
	if (event.getItem() == null) {
		return;
	}
	if (event.getItem().getType().equals(Material.MUSHROOM_STEW)) {
		return;
	}
	if (!KitManager.getPlayer(p.getName()).hasKit(this)) {
		return;
	}

	if (!event.hasItem() || !ItemBuilder.has(event.getItem(), "kit-handler", "pyro")) return; 
	if (!event.getAction().toString().contains("RIGHT")) 
	return; {
		event.setCancelled(true);
		
		if (!inCooldown(event.getPlayer())) {

			new BukkitRunnable() {
				@Override
				public void run() {
			p.launchProjectile(Fireball.class);
				}
			}.runTaskLater(WavePvP.getInstance(), 10);

			new BukkitRunnable() {
				@Override
				public void run() {
			p.launchProjectile(Fireball.class);
				}
			}.runTaskLater(WavePvP.getInstance(), 20);

			new BukkitRunnable() {
				@Override
				public void run() {
			p.launchProjectile(Fireball.class);
				}
			}.runTaskLater(WavePvP.getInstance(), 30);

			new BukkitRunnable() {
				@Override
				public void run() {
			p.launchProjectile(Fireball.class);
				}
			}.runTaskLater(WavePvP.getInstance(), 40);

			new BukkitRunnable() {
				@Override
				public void run() {
			p.launchProjectile(Fireball.class);
				}
			}.runTaskLater(WavePvP.getInstance(), 50);
			addCooldown(p, 15);
}
}
}
}