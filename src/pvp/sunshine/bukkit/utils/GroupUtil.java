package pvp.sunshine.bukkit.utils;

import java.util.HashMap;
import java.util.Map;
import org.bukkit.entity.Player;

public class GroupUtil {

	private static final Map<String, String> groupPermissions = new HashMap<>();

	static {
		groupPermissions.put("tag.dono", "§4§lDONO");
		groupPermissions.put("tag.diretor", "§4§lDIRETOR");
		groupPermissions.put("tag.admin", "§c§lADMIN");
		groupPermissions.put("tag.mod+", "§2§lMOD+");
		groupPermissions.put("tag.mod", "§2§lMOD");
		groupPermissions.put("tag.trial", "§d§lTRIAL");
		groupPermissions.put("tag.ajudante", "§e§lAJUDANTE");
		groupPermissions.put("tag.builder", "§6§lBUILDER");
		groupPermissions.put("tag.KOMBOPVP", "§b§lKOMBOPVP");
		groupPermissions.put("tag.beta", "§1§lBETA");
		groupPermissions.put("tag.investidor", "§a§lINVST");
		groupPermissions.put("tag.astrion", "§e§lASTRION");
		groupPermissions.put("tag.aurora", "§d§lAURORA");
		groupPermissions.put("tag.seraph", "§3§lSERAPH");
		groupPermissions.put("tag.galatic", "§a§lGALATIC");
		groupPermissions.put("tag.streamer", "§5§lSTREAMER");
		groupPermissions.put("tag.youtuber", "§c§lYOUTUBER");
		groupPermissions.put("tag.discloser", "§3§lDISCLOSER");
	}

	public static String getGroup(Player player) {
		String playerName = player.getName().toLowerCase();

		if (groupPermissions.containsKey(playerName)) {
			return groupPermissions.get(playerName);
		}

		for (Map.Entry<String, String> entry : groupPermissions.entrySet()) {
			if (player.hasPermission(entry.getKey())) {
				return entry.getValue();
			}
		}

		return "§7§lMEMBRO";
	}

}