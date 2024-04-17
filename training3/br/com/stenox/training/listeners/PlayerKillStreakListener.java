package br.com.stenox.training.listeners;


import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import net.helix.core.bukkit.HelixBukkit;
import net.helix.core.bukkit.account.HelixPlayer;



public class PlayerKillStreakListener implements Listener {
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPlayerDeath(PlayerDeathEvent event) {
		if (!(event.getEntity().getKiller() instanceof Player)) {
			return;
		}
		
		Player killer = event.getEntity().getKiller();
		HelixPlayer killerAccount = HelixBukkit.getInstance().getPlayerManager().getPlayer(killer.getName());
		
		int killstreak = killerAccount.getPvp().getKillstreak();
		if (String.valueOf(killstreak).contains("5") || (String.valueOf(killstreak).contains("0")) && killstreak != 0) {
			Bukkit.broadcastMessage("§5§lKS §d" + killer.getName() + " tem um killstreak de §5" + killstreak + "§d!");
		}
		
		Player victim = event.getEntity();
		HelixPlayer victimA = HelixBukkit.getInstance().getPlayerManager().getPlayer(victim.getName());
		int killstreak2 = victimA.getPvp().getKillstreak();
		if (killstreak2 >= 3) {
			Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§6" + victim.getName() + " §eperdeu seu killstreak de §6" + victimA.getPvp().getKillstreak() + " §epara §6" +
	                killer.getName() + "§e!"));
		}
		victimA.getPvp().setKillstreak(0);
	}
	public static void checkKillStreakLose(int winstreak, Player killer, String victim) {
		HelixPlayer victimA = HelixBukkit.getInstance().getPlayerManager().getPlayer(victim);
	
		if (winstreak >= 3) {
			Bukkit.getOnlinePlayers().forEach(p -> p.sendMessage("§6" + victimA.getName() + " §eperdeu seu killstreak de §6" + victimA.getPvp().getKillstreak() + " §epara §6" +
	                killer.getName() + "§e!"));
		}
}
}