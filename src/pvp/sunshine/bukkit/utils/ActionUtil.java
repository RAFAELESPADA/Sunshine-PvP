package pvp.sunshine.bukkit.utils;

import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class ActionUtil {
	
	public static void sendActionbar(Player player, String message) {
		CraftPlayer craftPlayer = (CraftPlayer) player;
		IChatBaseComponent actionbarText = IChatBaseComponent.ChatSerializer.a("{\"text\":\"" + message + "\"}");
		PacketPlayOutChat packet = new PacketPlayOutChat(actionbarText, (byte) 2);
		(craftPlayer.getHandle()).playerConnection.sendPacket(packet);
	}
}
