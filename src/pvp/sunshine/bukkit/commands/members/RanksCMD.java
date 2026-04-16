package pvp.sunshine.bukkit.commands.members;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;

public class RanksCMD implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("ranks")) {
            int currentXP = SQLRank.getXp((Player)sender);

            String currentRank = SQLRank.getRankComplete(currentXP);
            int xpRequired = SQLRank.getXpRequiredForNextRank(currentXP);
            sender.sendMessage("");
            sender.sendMessage("         §d§lRANKS - KITPVP");
            sender.sendMessage("");
            sender.sendMessage(" §7(§5✺§7) §5Deus §f- §a50.000 XP");
            sender.sendMessage(" §7(§1✯§7) §1Safira §f- §a20.000 XP");
            sender.sendMessage(" §7(§b❂§7) §bDiamante §f- §a15.000 XP");
            sender.sendMessage(" §7(§2☯§7) §2Esmeralda §f- §a10.000 XP");
            sender.sendMessage(" §7(§4❃§7) §4Ruby §f- §a5.000 XP");
            sender.sendMessage(" §7(§e❆§7) §eOuro §f- §a3.500 XP");
            sender.sendMessage(" §7(§f❅§7) §fFerro §f- §a2.000 XP");
            sender.sendMessage(" §7(§6⚝§7) §6Bronze §f- §a1.500 XP");
            sender.sendMessage(" §7(§4❂§7) §4Avançado §f- §a1.000 XP");
            sender.sendMessage(" §7(§2✥§7) §2Iniciante §f- §a700 XP");
            sender.sendMessage(" §7(§8✽§7) §8Pedra §f- §a500 XP");
            sender.sendMessage(" §7(§a⚍§7) §7Unranked §f- §a0 XP");
            sender.sendMessage("");
            sender.sendMessage(" §fXP necessário para o próximo rank: §a" + SunshineFormat.format(xpRequired));
            sender.sendMessage(" §fXP atual: §a" + SunshineFormat.format(currentXP));
            sender.sendMessage(" §fSeu rank atual: " + SQLRank.getRankComplete(SQLRank.getXp((Player)sender)));
            sender.sendMessage("");
        }
        return false;
    }
}
