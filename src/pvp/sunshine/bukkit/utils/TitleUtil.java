package pvp.sunshine.bukkit.utils;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PlayerConnection;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;

public class TitleUtil {
	public static void sendTitle(Player player, int fadeIn, int stay, int fadeOut, String title, String subtitle) {
		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;

		PacketPlayOutTitle titlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.TITLE,
				new ChatComponentText(title), fadeIn, stay, fadeOut);
		connection.sendPacket(titlePacket);

		if (subtitle != null && !subtitle.isEmpty()) {
			PacketPlayOutTitle subtitlePacket = new PacketPlayOutTitle(PacketPlayOutTitle.EnumTitleAction.SUBTITLE,
					new ChatComponentText(subtitle), fadeIn, stay, fadeOut);
			connection.sendPacket(subtitlePacket);
		}
	}
}
