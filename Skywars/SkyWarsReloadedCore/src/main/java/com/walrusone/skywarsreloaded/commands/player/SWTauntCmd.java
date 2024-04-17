package com.walrusone.skywarsreloaded.commands.player;

import com.walrusone.skywarsreloaded.commands.BaseCmd;
import com.walrusone.skywarsreloaded.menus.playeroptions.OptionSelectionMenu;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SWTauntCmd extends BaseCmd {
    public SWTauntCmd(String t) {
        type = t;
        forcePlayer = true;
        cmdName = "taunt";
        alias = new String[]{"t"};
        argLength = 1;
    }

    public boolean run(CommandSender sender, Player player, String[] args) {
        new OptionSelectionMenu(player, com.walrusone.skywarsreloaded.enums.PlayerOptions.TAUNT, true);
        return true;
    }
}
