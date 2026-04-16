package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerFishEvent;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Fisherman implements Listener {
	@EventHandler
	public void onPlayerFishEventFisherman(PlayerFishEvent e) {
		if (e.getCaught() != null && e.getCaught() != e.getHook().getLocation().getBlock()
				&& RegisterAbility.getAbility(e.getPlayer()) == "Fisherman")
			e.getCaught().teleport(e.getPlayer().getPlayer().getLocation());
	}
}
