package net.kombopvp.pvp.inventory;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;


import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.util.Vector;

import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.kit.KitManager;
import net.kombopvp.pvp.kit.KitManager2;
import net.kombopvp.pvp.kit.WaveKit;
import net.kombopvp.pvp.kit.WaveKit2;
import net.kombopvp.pvp.kit.provider.EnderMageReal;
import net.kombopvp.pvp.warp.WaveWarp2;

public class KitDiario implements Listener {

	@SuppressWarnings("unused")
	private static HashMap<String, Long> cooldown = new HashMap<String, Long>();

	 public static ArrayList<String> setandokit = new ArrayList<>();
	 private static final Random PRNG = new Random();
	public static void playFirework(Location location, Color color, Player p) {
		Firework firework = (Firework) location.getWorld().spawn(location, Firework.class);
		FireworkMeta fMeta = firework.getFireworkMeta();
		fMeta.addEffect(FireworkEffect.builder().withColor(color).build());
		firework.setFireworkMeta(fMeta);
		firework.setVelocity(new Vector(0.0D, -1.0D, 0.0D));
	}

	public void playFirework(Location location, FireworkEffect effecta, Player p) {
		Firework firework = (Firework) location.getWorld().spawn(location, Firework.class);
		FireworkMeta fMeta = firework.getFireworkMeta();
		fMeta.addEffect(effecta);
		firework.setFireworkMeta(fMeta);
	}



	@SuppressWarnings("deprecation")
	public static void Inventario(final Player jogador) {
		Random r = new Random();
		int ggr = r.nextInt(WaveKit2.values().length);
				
		if (WaveKit2.findKit(ggr).get().getName().toString() == null) {
			Inventario(jogador);
			return;
		}
String kit = WaveKit2.findKit(ggr).get().getName().toString();
if (kit == null && !jogador.hasPermission("wave.kit2.stomper")) {
	jogador.sendMessage(ChatColor.GREEN + "Você ganhou o kit Stomper");
	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + jogador.getName() + " permission settemp wave.kit2.stomper" + " true 1d server=pvp");
	return;
}
else if (kit == null && jogador.hasPermission("wave.kit2.stomper")) {
	jogador.sendMessage(ChatColor.GREEN + "Você ganhou o kit Thor");
	Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + jogador.getName() + " permission settemp wave.kit2.thor" + " true 1d server=pvp");
	return;
}
				if (ggr != 0 && ggr < WaveKit2.values().length) {
					if (jogador.hasPermission("wave.kit2." + kit)) {
						
						Inventario(jogador);
						return;
					}
					if (kit == "Nenhum" || kit == "Kangaroo" || kit == "Archer") {
						
						Inventario(jogador);
						return;
					}
					setandokit.add(jogador.getName());
					playFirework(jogador.getLocation(), Color.MAROON, jogador);
					jogador.getWorld().playSound(jogador.getLocation(), Sound.LEVEL_UP, 10F, 10F);
					jogador.sendMessage(ChatColor.GREEN + "Você ganhou o kit " + kit);
					Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + jogador.getName() + " permission settemp wave.kit2." + kit + " true 1d server=pvp");
				}

		Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				jogador.closeInventory();
			}
		}, 20L);

		
	}
	public static WaveKit2 randomKit()  {
	
        WaveKit2[] directions = WaveKit2.values();
        return directions[PRNG.nextInt(directions.length)];
    }
	
	@EventHandler
	public void aoInteragir(final PlayerInteractEvent evento) {
		if (evento.getItem() == null) {
			return;
		}
		if (evento.getItem().getType().equals(Material.MUSHROOM_SOUP)) {
			return;
		}
		final Player jogador = evento.getPlayer();
		if (jogador.getItemInHand().getType() == Material.STORAGE_MINECART) {
			if (WaveWarp2.SPAWN.hasPlayer(jogador.getName()) && EnderMageReal.isSpawn(jogador.getLocation())) {
			if ((evento.getAction() == Action.RIGHT_CLICK_AIR) || (evento.getAction() == Action.RIGHT_CLICK_BLOCK)) {
				if (setandokit.contains(jogador.getName())) {
					jogador.sendMessage("§aVoce já recebeu seu Kit!");
					
					evento.setCancelled(true);
					return;
				}
				if (jogador.hasPermission("wave.kit2.*")) {
					jogador.sendMessage("§aVoce já possui todos os kits!");
					evento.setCancelled(true);
					return;
				}

				evento.setCancelled(true);

				Inventario(jogador);

			}
			
		}
	}
}
}

