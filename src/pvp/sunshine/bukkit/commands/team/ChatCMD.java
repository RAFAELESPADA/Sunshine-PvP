package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.commands.CommandStack;

public class ChatCMD implements CommandExecutor {
	public static boolean chat = true;

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("pvp.chat")) {
			sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(CommandStack.command("chat <off/on/clear>"));
			return true;
		}

		Player player = (Player) sender;

		if (args[0].equalsIgnoreCase("clear")) {
			if (Cooldown.add(player)) {
				player.sendMessage(
						"§c§lCHAT §fAguarde §c" + Cooldown.cooldown(player) + "s §fpara limpar o chat novamente.");
				return true;
			}

			for (int i = 0; i < 100; i++) {
				Bukkit.broadcastMessage(" ");
			}

			Bukkit.broadcastMessage("§e§lCHAT §fO chat foi limpo por §e" + player.getName());
			Cooldown.add(player, 20);

			Bukkit.getScheduler().runTaskLater((Plugin) BukkitMain.getInstance(),
					() -> player.sendMessage("§a§lCHAT §fAgora você já pode limpar o chat novamente."),

					400L);
		} else if (args[0].equalsIgnoreCase("off")) {
			if (!chat) {
				player.sendMessage("§c§lCHAT §fO chat já está desabilitado.");
				return true;
			}

			chat = false;
			Bukkit.broadcastMessage("§c§lCHAT §fO chat foi desabilitado por §c" + player.getName());
		} else if (args[0].equalsIgnoreCase("on")) {
			if (chat) {
				player.sendMessage("§c§lCHAT §fO chat já está habilitado.");
				return true;
			}

			chat = true;
			Bukkit.broadcastMessage("§a§lCHAT §fO chat foi habilitado por §a" + player.getName());
		} else {
			player.sendMessage("§c§lERRO §fArgumento inválido.");
		}

		return true;
	}
}