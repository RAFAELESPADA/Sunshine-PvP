package pvp.sunshine.bukkit.commands.members;

import java.util.UUID;

import org.bukkit.*;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.team.AdminCMD;
import pvp.sunshine.bukkit.commands.team.FlyCMD;
import pvp.sunshine.bukkit.manager.duels.Battle;
import pvp.sunshine.bukkit.manager.duels.Complementer;
import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerNotBattle;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.ClearCosmeticUtil;
import pvp.sunshine.bukkit.utils.TitleUtil;

public class DuelCMD implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§cApenas jogadores podem executar esse comando.");
        } else {
            Player p = (Player) sender;
            UUID id = p.getUniqueId();
            if (!Battle.in1v1.contains(p.getUniqueId())) {
            	p.teleport(new Location(Bukkit.getWorld("1v1"), -14.720, 41.000000, 20.233, -179.1F, -1.7F));
				
            	Battle.in1v1.add(id);
                Flag.setProtection(p, true);
                PlayerNotBattle.update(p);
                p.setAllowFlight(false);
                p.setFlying(false);
                p.setMaximumNoDamageTicks(20);
                ClearCosmeticUtil.removeCache(p);
                p.sendMessage("§e§lWARP §fVocê foi teleportado para a warp §e1v1");
                FlyCMD.removeFly(p);
                p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                TitleUtil.sendTitle(p, 30, 30, 30, "", "§b§l1V1");
                Battle.contents.put(p.getUniqueId(), p.getInventory().getContents());
                Complementer.items1v1(p);
                if (AdminCMD.admin.contains(p.getName())) {
                    AdminCMD.admin.remove(p.getName());
                    p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você entrou em uma warp.\n§e§lADMIN§f Agora você está visível para todos.");
                }
                p.setGameMode(GameMode.ADVENTURE);
                for (UUID idS : Battle.partida.keySet()) {
                    Player s = Bukkit.getPlayer(idS);
                    if ((s != null) && (s.isOnline()) && (s.canSee(p))) {
                        s.hidePlayer(p);
                        if (AdminCMD.admin.contains(s.getName())) {
                            p.hidePlayer(s);
                        }
                    }
                }
                RegisterAbility.setAbility(p, "1v1");
            } else if (!Battle.partida.containsKey(id)) {
                Battle.in1v1.remove(id);
                if (Battle.convite.containsKey(id)) {
                    Battle.convite.remove(id);
                }

                Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
                p.teleport(spawnLocation);
                p.getInventory().clear();
                PvP.update(p);
                Flag.setProtection(p, false);
                p.getInventory().setContents((ItemStack[]) Battle.contents.get(id));
                p.chat("/warp spawn");
                if (Battle.random == p.getUniqueId()) {
                    Battle.random = null;
                }
                Battle.contents.remove(id);
                for (Player s : Bukkit.getOnlinePlayers()) {
                    if (AdminCMD.admin.contains(s.getName())) {
                        p.hidePlayer(s);
                    }
                }
            } else {
                sender.sendMessage("§c§l1V1 §fVocê não pode executar comandos em um duelo.");
            }
            return false;
        }
        return false;
    }
}
