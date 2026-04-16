package pvp.sunshine.bukkit.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.team.AdminCMD;
import pvp.sunshine.bukkit.commands.team.EventoCMD;
import pvp.sunshine.bukkit.manager.scoreboard.Evento;

public class SpawnCMD implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String arg2, String[] arg3) {
        Player p = (Player)sender;
        if (RegisterAbility.getAbility(p).equalsIgnoreCase("1v1")) {
            p.chat("/1v1");
            return true;
        }
        if (RegisterAbility.getAbility(p).equalsIgnoreCase("Sumo")) {
            p.chat("/Sumo");
            return true;
        }
        if (AdminCMD.admin.contains(p.getName())) {
            AdminCMD.admin.remove(p.getName());
            p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você está com proteção de spawn.\n§e§lADMIN §fAgora você está visível para todos.");
            for (Player all : Bukkit.getOnlinePlayers()) {
                all.showPlayer(p);
            }
        }
        if (EventoCMD.participantes.contains(p.getName())) {
            p.sendMessage("§c§lEVENTO §fVocê foi teleportado para o spawn e desclassificado do evento.");
            EventoCMD.participantes.remove(p.getName());
        }
        for (String participante : EventoCMD.participantes) {
            Player player = Bukkit.getPlayer(participante);
            if (player != null) {
                player.sendMessage("§c§lEVENTO §fO jogador §c" + p.getName() + " §fsaiu do evento. §c" + EventoCMD.participantes.size() + " participante(s)§f.");
                Evento.update(player);
            }
        }
        p.chat("/warp spawn");
        return true;
    }

}
