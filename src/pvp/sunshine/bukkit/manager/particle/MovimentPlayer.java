package pvp.sunshine.bukkit.manager.particle;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

import java.util.*;

public class MovimentPlayer implements Listener {

	private static List<String> Moving = new ArrayList<String>();
	private Map<UUID, Location> lastLocation = new HashMap<UUID, Location>();

	@EventHandler
	public void moveEvent(PlayerMoveEvent e) {
		Player player = e.getPlayer();
		Location Current = player.getLocation();
		Location Last = (Location) this.lastLocation.get(player.getUniqueId());
		if (this.lastLocation.get(player.getUniqueId()) == null) {
			this.lastLocation.put(player.getUniqueId(), Current);
			Last = (Location) this.lastLocation.get(player.getUniqueId());
		}
		this.lastLocation.put(player.getUniqueId(), player.getLocation());
		if ((Last.getX() != Current.getX()) || (Last.getY() != Current.getY()) || (Last.getZ() != Current.getZ())) {
			if (!Moving.contains(player.getName())) {
				Moving.add(player.getName());
			}
		} else if (Moving.contains(player.getName())) {
			Moving.remove(player.getName());
		}
	}

	public static boolean isNotMoving(Player p) {
		if (Moving.contains(p.getName())) {
			return false;
		}
		return true;
	}
}