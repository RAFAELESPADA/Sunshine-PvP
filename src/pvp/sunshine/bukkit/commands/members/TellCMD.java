package pvp.sunshine.bukkit.commands.members;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;

public class TellCMD implements CommandExecutor {
	public static final List<Player> telloff = new ArrayList<>();

	public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("§cApenas jogadores podem executar esse comando.");
			return true;
		}

		Player playerSender = (Player) sender;

		if (args.length == 0) {
			sender.sendMessage(CommandStack.command("tell <jogador> <mensagem> ou /tell <on/off>"));
			return true;
		}

		if (args[0].equalsIgnoreCase("on")) {
			toggleTellOff(playerSender, false);
		} else if (args[0].equalsIgnoreCase("off")) {
			toggleTellOff(playerSender, true);
		} else {
			Player target = Bukkit.getPlayer(args[0]);

			if (target == null || !target.isOnline()) {
				sender.sendMessage("§c§lERRO §fEste jogador está offline.");
				return true;
			}

			if (isTellOff(target)) {
				sender.sendMessage("§c§lTELL §fEste jogador está com o tell desabilitado.");
				return true;
			}

			if (target.equals(playerSender)) {
				sender.sendMessage("§c§lTELL §fVocê não pode enviar mensagens para si mesmo.");
				return true;
			}

			String message = String.join(" ", (CharSequence[]) args);
			message = message.substring(args[0].length() + 1);

			sendTellMessage(playerSender, target, message);
		}

		return true;
	}

	private void toggleTellOff(Player player, boolean off) {
		if (off) {
			if (!isTellOff(player)) {
				telloff.add(player);
				player.sendMessage("§c§lTELL §fSeu tell foi desabilitado.");
			} else {
				player.sendMessage("§c§lTELL §fSeu tell já está desabilitado.");
			}

		} else if (isTellOff(player)) {
			telloff.remove(player);
			player.sendMessage("§a§lTELL §fSeu tell foi habilitado.");
		} else {
			player.sendMessage("§c§lTELL §fSeu tell já está habilitado.");
		}
	}

	private boolean isTellOff(Player player) {
		return telloff.contains(player);
	}

	private void sendTellMessage(Player sender, Player target, String message) {
		sender.sendMessage("§f(§7Você §f» " + target.getDisplayName() + "§f)§7:§e " + message);
		target.sendMessage("§f(§7De §f» " + sender.getDisplayName() + "§f)§7:§e " + message);
	}
}