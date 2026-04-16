package pvp.sunshine.bukkit.ability;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class RegisterAbility {
	public static Map<String, String> powerMap = new HashMap<>();

	public static String getAbility(Player p) {
		if (!powerMap.containsKey(p.getName())) {
			powerMap.put(p.getName(), "Nenhum");
		}
		return powerMap.get(p.getName());
	}

	public static String getAbility(String p) {
		if (!powerMap.containsKey(p)) {
			powerMap.put(p, "Nenhum");
		}
		return powerMap.get(p);
	}

	public static void removeAbility(Player p) {
		powerMap.remove(p.getName());
	}

	public static void setAbility(Player p, String ability) {
		if (powerMap.containsKey(p.getName())) {
			powerMap.remove(p.getName());
		}
		powerMap.put(p.getName(), ability);
	}
}