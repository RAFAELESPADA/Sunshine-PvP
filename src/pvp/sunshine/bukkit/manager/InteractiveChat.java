package pvp.sunshine.bukkit.manager;

import net.minecraft.server.v1_8_R3.ChatClickable;
import net.minecraft.server.v1_8_R3.ChatHoverable;
import net.minecraft.server.v1_8_R3.ChatMessage;
import net.minecraft.server.v1_8_R3.ChatModifier;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.MinecraftServer;

public class InteractiveChat {
	private static final InteractiveChat api = new InteractiveChat();

	public static InteractiveChat getAPI() {
		return api;
	}

	private IChatBaseComponent createHoverableComponent(String hoverMessage, String hoverableText,
			ChatHoverable.EnumHoverAction hoverAction) {
		ChatMessage chatMessage = new ChatMessage(hoverMessage, new Object[0]);
		chatMessage.setChatModifier(new ChatModifier());
		chatMessage.getChatModifier().setInsertion(hoverMessage);
		chatMessage.getChatModifier().setChatHoverable(
				new ChatHoverable(hoverAction, (IChatBaseComponent) new ChatMessage(hoverableText, new Object[0])));
		return (IChatBaseComponent) chatMessage;
	}

	private void sendInteractiveMessage(String playername, String startMessage, String finalMessage,
			IChatBaseComponent hoverComponent) {
		ChatMessage chatMessage = new ChatMessage(startMessage, new Object[0]);
		chatMessage.addSibling(hoverComponent);
		chatMessage.addSibling((IChatBaseComponent) new ChatMessage(finalMessage, new Object[0]));
		EntityPlayer player = MinecraftServer.getServer().getPlayerList().getPlayer(playername);
		if (player != null) {
			player.sendMessage((IChatBaseComponent) chatMessage);
		}
	}

	public void commandHover(String playername, String startMessage, String finalMessage, String hoverMessage,
			String hoverableText, String command) {
		IChatBaseComponent hoverComponent = createHoverableComponent(hoverMessage, hoverableText,
				ChatHoverable.EnumHoverAction.SHOW_TEXT);
		hoverComponent.getChatModifier()
				.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.RUN_COMMAND, command));
		sendInteractiveMessage(playername, startMessage, finalMessage, hoverComponent);
	}

	public void messageHover(String playername, String startMessage, String finalMessage, String hoverMessage,
			String hoverableText) {
		IChatBaseComponent hoverComponent = createHoverableComponent(hoverMessage, hoverableText,
				ChatHoverable.EnumHoverAction.SHOW_TEXT);
		sendInteractiveMessage(playername, startMessage, finalMessage, hoverComponent);
	}

	public void linkHover(String playername, String startMessage, String finalMessage, String hoverMessage,
			String hoverableText, String link) {
		IChatBaseComponent hoverComponent = createHoverableComponent(hoverMessage, hoverableText,
				ChatHoverable.EnumHoverAction.SHOW_TEXT);
		hoverComponent.getChatModifier()
				.setChatClickable(new ChatClickable(ChatClickable.EnumClickAction.OPEN_URL, link));
		sendInteractiveMessage(playername, startMessage, finalMessage, hoverComponent);
	}
}
