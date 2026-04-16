package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.Location;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.CommandStack;

import java.util.ArrayList;

public class FlyCMD implements CommandExecutor, Listener {
    public static ArrayList<String> fly = new ArrayList<>();

    public static void removeFly(Player p) {
        if (fly.contains(p.getName())) {
            fly.remove(p.getName());
            p.sendMessage("§c§lFLY §fSeu fly foi desabilitado.");
        }
    }

    private static final int MAX_DISTANCE = 110;
    private static final Location SPAWN_LOCATION = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
    

    public boolean onCommand(final CommandSender sender, final Command arg1, final String arg2, final String[] arg3) {
        if (!sender.hasPermission("pvp.fly")) {
            sender.sendMessage("§c§lERRO §fVocê não tem permissão para utilizar o fly. Adquira seu §a§lGALATIC §f para ter acesso liberado!");
            return true;
        }

        if (sender instanceof Player) {
            Player p = (Player) sender;

            if (AdminCMD.admin.contains(p.getName())) {
                p.sendMessage("§c§lADMIN §fVocê não pode utilizar este comando em modo admin.");
                return true;
            }

            if (RegisterAbility.getAbility(p) != "Nenhum") {
                p.sendMessage("§c§lFLY §fVocê só pode utilizar o fly no spawn!");
                return true;
            }

            if (!FlyCMD.fly.contains(p.getName())) {
                FlyCMD.fly.add(p.getName());
                p.setAllowFlight(true);
                p.setFlying(true);
                p.sendMessage("§a§lFLY §fSeu fly foi habilitado.");
            } else {
                FlyCMD.fly.remove(p.getName());
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage("§c§lFLY §fSeu fly foi desabilitado.");

            }

        }

        return true;
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (FlyCMD.fly.contains(player.getName()) && event.getTo() != null) {
            Location to = event.getTo();
            if (to.distance(SPAWN_LOCATION) > MAX_DISTANCE) {
                player.teleport(SPAWN_LOCATION);
                player.sendMessage("§c§lFLY §fVocê passou do limite de blocos! Você foi teleportado de volta para o spawn.");
            }
        }
    }
}
