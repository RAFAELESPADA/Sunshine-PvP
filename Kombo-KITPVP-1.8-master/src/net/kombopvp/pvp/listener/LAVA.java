package net.kombopvp.pvp.listener;





import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.event.WavePlayerDeathEvent;
import net.kombopvp.pvp.event.WavePlayerDeathEvent.Reason;
import net.kombopvp.pvp.warp.WaveWarp2;

import org.bukkit.ChatColor;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class LAVA implements Listener {
	
	@EventHandler
	public void onPlayerDieInArena(WavePlayerDeathEvent event) {
		if (event.getReason() != Reason.LAVA) {
			return;
		}
		Player player = event.getPlayer();
		World w = player.getWorld();
		List<ItemStack> drops = new ArrayList<>(event.getDrops());
		Location deathLocation = event.getDeathLocation();
		if (drops.size() > 0) {
			deathLocation.getWorld().playEffect(deathLocation, Effect.CLOUD, 10);
		}
		ItemStack capacete0 = new ItemStack(Material.MUSHROOM_SOUP);
		ItemStack capacete1 = new ItemStack(Material.BOWL);
		ItemStack capacete2 = new ItemStack(Material.BROWN_MUSHROOM);
		ItemStack capacete3 = new ItemStack(Material.RED_MUSHROOM);
		for (int i = 0; i < 64; i++) {
			w.dropItemNaturally(deathLocation, capacete1);
			w.dropItemNaturally(deathLocation, capacete2);
			w.dropItemNaturally(deathLocation, capacete3);
		}
		for (int i = 0; i < 33; i++) {
		w.dropItemNaturally(deathLocation, capacete0);
		}
		event.getDrops().clear();
		drops.clear();
		
		new BukkitRunnable() {
			@Override
			public void run() {
				WaveWarp2.LAVACHALLENGE.send(player, true);			
				player.setFireTicks(0);
				player.spigot().respawn();
			}
		}.runTaskLater(KomboPvP2.getInstance(), 1);
		new BukkitRunnable() {
			@Override
			public void run() {
				player.spigot().respawn();
			}
		}.runTaskLater(KomboPvP2.getInstance(), 100);
		player.setFireTicks(0);
		player.sendMessage(ChatColor.YELLOW + "Você respawnou na warp lava. para voltar use /lobby");
		}
}