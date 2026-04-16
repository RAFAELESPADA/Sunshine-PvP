package pvp.sunshine.bukkit.manager.event;

import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CombatLog implements Listener {
	public static Map<Player, Player> emcombate = new HashMap<>();

	public static void removeCombat(Player p) {
		emcombate.remove(p);
	}

	@EventHandler
	public void onEventPlayerDeathEvent(PlayerDeathEvent e) {
		CombatLog.emcombate.remove(e.getEntity());
		CombatLog.emcombate.remove(CombatLog.emcombate.get(e.getEntity()));
	
	}

	@EventHandler
	public void onEventPlayerCommandPreprocessEvent(PlayerCommandPreprocessEvent e) {
		if (e.getMessage().toLowerCase().startsWith("/") && CombatLog.emcombate.containsKey(e.getPlayer())) {
			e.getPlayer().sendMessage("§c§lCOMBATE §fVocê está em combate, aguarde para executar comandos.");
			e.setCancelled(true);
		}
	}

	@EventHandler
	public void aosair(PlayerQuitEvent e) {
		if (emcombate.containsKey(e.getPlayer())) {
			e.getPlayer().setHealth(0.0D);
			emcombate.remove(e.getPlayer());
			emcombate.remove(emcombate.get(e.getPlayer()));
		}
	}

	@EventHandler
	public void aosair(PlayerKickEvent e) {
		if (emcombate.containsKey(e.getPlayer())) {
			e.getPlayer().setHealth(0.0D);
			emcombate.remove(e.getPlayer());
			emcombate.remove(emcombate.get(e.getPlayer()));
		}
	}




}