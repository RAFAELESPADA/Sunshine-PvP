package pvp.sunshine.bukkit.commands.members;


import java.util.UUID;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pvp.sunshine.bukkit.manager.inventory.BoxShopType;


public class CaixasCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar esse comando.");
        } else {
            Player p = (Player) sender;
            BoxShopType.getBoxShop(p);
        }
		return false;
    }
}