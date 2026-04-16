package pvp.sunshine.bukkit.manager.feast;

import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.commands.CommandStack;

public class SetFeast implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (!sender.getName().equalsIgnoreCase("Rafael_Auler")) {
            sender.sendMessage(CommandStack.PERMISSION_DENIED);
            return true;
        }
        Player player = (Player)sender;
        if (cmd.getName().equalsIgnoreCase("setfeast")) {
            // Obtém a seção "MiniFeast" da configuração
            ConfigurationSection config = BukkitMain.getInstance().getConfig().getConfigurationSection("MiniFeast");

            // Verifica se a seção existe
            if (config == null) {
                config = BukkitMain.getInstance().getConfig().createSection("MiniFeast");
            }

            Location loc = player.getLocation();

            // Define os valores dentro da seção
            config.set("x", loc.getBlockX());
            config.set("y", loc.getBlockY());
            config.set("z", loc.getBlockZ());

            // Salva a configuração
            BukkitMain.getInstance().saveConfig();

            player.sendMessage("§a§lFEAST §fVocê setou o feast com sucesso.");
            return true;
        }
        return false;
    }
}
