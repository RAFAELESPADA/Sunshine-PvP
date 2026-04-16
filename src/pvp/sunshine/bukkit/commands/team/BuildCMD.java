package pvp.sunshine.bukkit.commands.team;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.commands.CommandStack;

public class BuildCMD implements CommandExecutor {
	public static final Set<Player> buildModePlayers = new HashSet<>();

	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!sender.hasPermission("pvp.build")) {
			sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
			return true;
		}
		if (AdminCMD.admin.contains(sender.getName())) {
			sender.sendMessage("§c§lADMIN §fVocê não pode utilizar este comando em modo admin.");
			return true;
		}

		if (args.length == 0) {
			sender.sendMessage(CommandStack.command("build <on/off>"));
			return true;
		}

		if (!(sender instanceof Player)) {
			sender.sendMessage("§cEste comando só pode ser usado por jogadores.");
			return true;
		}

		Player player = (Player) sender;

		if (args[0].equalsIgnoreCase("on")) {
			if (buildModePlayers.contains(player)) {
				player.sendMessage("§e§lBUILD §fSeu build já está §ahabilitado§f.");
			} else {
				buildModePlayers.add(player);
				player.sendMessage("§a§lBUILD §fSeu build foi habilitado.");
				Bukkit.broadcast("§7* STAFF " + player.getName() + " ativou seu build.", "pvp.broadcast");
			}
		} else if (args[0].equalsIgnoreCase("off")) {
			if (buildModePlayers.remove(player)) {
				player.sendMessage("§c§lBUILD §fSeu build foi desabilitado.");
				Bukkit.broadcast("§7* STAFF " + player.getName() + " desativou seu build.", "pvp.broadcast");
			} else {
				player.sendMessage("§e§lBUILD §fSeu build já está §cdesabilitado§f.");
			}
		} else {
			sender.sendMessage("§c§lERRO §fArgumento inválido! Use /build <on/off>");
		}

		return true;
	}
}