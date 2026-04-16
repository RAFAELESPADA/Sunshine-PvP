package pvp.sunshine.bukkit.manager.event;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.api.KillStreakAPI;
import pvp.sunshine.bukkit.api.WinStreakAPI;
import pvp.sunshine.bukkit.commands.team.EventoCMD;
import pvp.sunshine.bukkit.manager.duels.Battle;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopDeaths;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopKills;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopLoses;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopWins2;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerNotBattle;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class PlayerDeathListener implements Listener {
	
	@EventHandler
	public void onDeath(PlayerDeathEvent event) {
		event.setDeathMessage(null);
		Player player = event.getEntity();
		event.getEntity().setAllowFlight(false);
		event.getEntity().setFlying(false);
		Player k = event.getEntity().getKiller();
		if (EventoCMD.participantes.contains(event.getEntity().getName())) {
			event.getDrops().clear();
			EventoCMD.participantes.remove(event.getEntity().getName());
			RegisterAbility.removeAbility(event.getEntity());
			PvP.update(event.getEntity());
			new BukkitRunnable() {
				@Override
				public void run() {

	                Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);player.teleport(spawnLocation);
				}
			}.runTaskLater(BukkitMain.getInstance(), 3);
			event.getEntity().sendMessage("§c§lDEATH §fVocê morreu no evento, seus status não foram alterados!");
			if (k instanceof Player) {
				k.sendMessage("§c§lKILL §fVocê matou um jogador no evento, os status de ambos não foram alterados.");
			}
			return;
		}
		event.setDeathMessage(null);
		Player p = event.getEntity();
		int killerXP = randomXP();
		int deathXP = randomXPNegative();
		if (Battle.partida.containsKey(p.getUniqueId())) {
			Bukkit.getConsoleSender().sendMessage(p.getName() + " MORREU PARA " + k.getName() + " NA 1V1!");
			if (k instanceof Player && k != null) {
				//DEATH

				
				//KILLER
				k.sendMessage("§a§l1V1 §fParabéns! você venceu o duelo contra o jogador(a) §a" + p.getName() + "\n§6(+8 coins)\n§b(+" + killerXP + " xp)");
				event.getEntity().sendMessage("§c§l1V1 §fVocê perdeu o duelo contra o jogador(a) §c" + k.getName());
				k.playSound(k.getLocation(),Sound.ANVIL_LAND, 5.0F, 1.0F);
				PlayerNotBattle.update(p);
				PlayerNotBattle.update(k);
				event.getEntity().playSound(p.getLocation(), Sound.EXPLODE, 1.0F, 1.0F);
				new BukkitRunnable() {	
				public void run() {	
					
				SQLPvP.addCoins(k, 8);
				SQLPvP.removeCoins(p, 5);
				SQL1v1.addWins(k);
				SQL1v1.addLoses(p);
				SQLRank.addXp(k, killerXP);
				SQLRank.removeXP(p, deathXP);
				WinStreakAPI.addStreak(k);
				WinStreakAPI.removeStreak(p);
				TopWins2.incrementWins(k);			
				TopLoses.incrementLoses(p);
				}}.runTaskAsynchronously(BukkitMain.getInstance());
			return;
		}
		}		
		event.setDeathMessage(null);
		Player victim = event.getEntity();
		victim.setAllowFlight(false);
		victim.setFlying(false);
		Player killer = event.getEntity().getKiller();
		PvP.update(victim);
		

		victim.playSound(victim.getLocation(), Sound.CREEPER_DEATH, 10.0F, 10.0F);
		RegisterAbility.powerMap.remove(victim.getName());


	    Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
        (new BukkitRunnable() {
			public void run() {
				 victim.teleport(spawnLocation);
			}
		}).runTaskLater((Plugin) BukkitMain.getInstance(), 3l);
		if (k instanceof Player) {
			PvP.update(k);
			killer.sendMessage("§a§lKILL §fVocê matou §a" + victim.getName() + "\n§6(+8 coins)");
		
			victim.sendMessage("§c§lDEATH §fVocê morreu para §c" + killer.getName());
			killer.playSound(killer.getLocation(), Sound.ANVIL_LAND, 5.0F, 1.0F);
	
		new BukkitRunnable() {	
		public void run() {	
		
			handlePlayerKill(victim, k);
		}}.runTaskAsynchronously(BukkitMain.getInstance());
		} else if (!RegisterAbility.getAbility(victim).equalsIgnoreCase("Lava") && !RegisterAbility.getAbility(victim).equalsIgnoreCase("Sumo")) {
			
			handleNonPlayerKill(victim);

				}
		
	}

	private void handlePlayerKill(Player victim, Player killer) {
		victim.getInventory().clear();
		int killerXP = randomXP();
		int deathXP = randomXPNegative();

		KillStreakAPI.addStreak(killer);
		KillStreakAPI.removeStreak(victim);
		TopKills.incrementKills(killer);
		TopKills.updateHologram();
		SQLPvP.addCoins(killer, 8);
		SQLPvP.removeCoins(victim, 5);
		SQLPvP.addKills(killer);
		SQLPvP.addDeaths(victim);
		SQLRank.addXp(killer, killerXP);
		SQLRank.removeXP(victim, deathXP);
		TopDeaths.incrementDeaths(victim);
		TopDeaths.updateHologram();
		(new BukkitRunnable() {
			public void run() {
				Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
				  PvPUtil.spawnItem(victim);
				 victim.teleport(spawnLocation);
			}
		}).runTaskLater((Plugin) BukkitMain.getInstance(), 15l);
	
	}

	private void handleNonPlayerKill(Player victim) {
		victim.getInventory().clear();
		RegisterAbility.removeAbility(victim);
		RegisterAbility.setAbility(victim, "Nenhum");

		victim.sendMessage("§4§lDEATH §fVocê morreu por causas desconhecidas.");
		PvP.update(victim);

		victim.getActivePotionEffects().forEach(potionEffect -> victim.removePotionEffect(potionEffect.getType()));

		Flag.setProtection(victim, true);


        Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
        (new BukkitRunnable() {
			public void run() {
				 victim.teleport(spawnLocation);
			}
		}).runTaskLater((Plugin) BukkitMain.getInstance(), 5l);
	}
	

	



	@EventHandler
	public void respawn(PlayerDeathEvent event) {
		final Player player = event.getEntity();
		(new BukkitRunnable() {
			public void run() {
				player.spigot().respawn();
			}
		}).runTask((Plugin) BukkitMain.getInstance());
	}

	private static int randomXP() {
		Random random = new Random();
		return random.nextInt(10) + 8;
	}

	private static int randomXPNegative() {
		Random random = new Random();
		return random.nextInt(5) + 1;
	}
}