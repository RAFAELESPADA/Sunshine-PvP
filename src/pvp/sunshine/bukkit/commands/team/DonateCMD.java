package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerNotBattle;
import pvp.sunshine.bukkit.manager.scoreboard.Lava;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.commands.CommandStack;

public class DonateCMD implements CommandExecutor {

    @Override
    public boolean onCommand(final CommandSender sender, final Command cmd, final String label, final String[] args) {
        if (!cmd.getName().equalsIgnoreCase("donate")) return false;

        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lERRO §fEste comando só pode ser executado por jogadores.");
            return true;
        }

        Player player = (Player) sender;

        if (!player.hasPermission("pvp.donate")) {
            player.sendMessage(CommandStack.PERMISSION_DENIED);
            return true;
        }

        if (args.length < 3) {
            player.sendMessage("§c§lERRO §fComando inválido, utilize: /donate (coins/xp) (jogador) (quantia)");
            return true;
        }

        String type = args[0].toLowerCase();
        Player target = Bukkit.getPlayer(args[1]);
        int amount;

        try {
            amount = Integer.parseInt(args[2]);
        } catch (NumberFormatException e) {
            player.sendMessage("§c§lERRO §fQuantia inválida.");
            return true;
        }

        if (target == null || !target.isOnline()) {
            player.sendMessage(CommandStack.PLAYER_IS_OFFLINE);
            return true;
        }

        switch (type) {
            case "coins":
                SQLPvP.addCoins(target, amount);
                break;
            case "xp":
                SQLRank.addXp(target, amount);
                break;
            default:
                player.sendMessage("§c§lERRO §fTipo de doação inválido. Use 'coins' ou 'xp'.");
                return true;
        }

        player.sendMessage("§3§lDONATE §fVocê realizou uma transação de §e" + amount + " " + type + "§f para §3" + target.getName() + "§f com sucesso!");
        target.sendMessage("§3§lDONATE §fVocê recebeu uma transação de §e" + amount + " " + type + "§f em sua conta de " + player.getName());
        updateScoreboards(target);
        target.playSound(target.getLocation(), Sound.LEVEL_UP, 2.0f, 2.0f);
        return true;
    }

    public static void updateScoreboards(Player player) {
        String ability = RegisterAbility.getAbility(player);

        switch (ability) {
            case "Nenhum":
                PvP.update(player);
                break;
            case "1v1":
                PlayerNotBattle.update(player);
                break;
            case "Lava":
                Lava.update(player);
                break;
            case "Fps":
                PvP.update(player);
                break;
            default:
                break;
        }
    }
}
