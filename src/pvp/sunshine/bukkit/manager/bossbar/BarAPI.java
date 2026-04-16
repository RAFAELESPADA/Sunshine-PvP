package pvp.sunshine.bukkit.manager.bossbar;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;


public class BarAPI implements Listener {

	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}

	@EventHandler
	public void onKick(PlayerKickEvent e) {
		BossBarAPI.removeBar(e.getPlayer());
	}



}
