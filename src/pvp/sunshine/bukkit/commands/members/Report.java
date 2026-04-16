package pvp.sunshine.bukkit.commands.members;



import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.api.HelixCooldown;



public class Report implements CommandExecutor {
    
	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] args) {
		
        if (!(sender instanceof Player)) {
            return true;
	}
        if (args.length < 2) {
            sender.sendMessage("§b/report <Player> <Motivo>");
            return true;
        }
        String targetName = args[0];
       Player targetPlayer;
        if ((targetPlayer = Bukkit.getPlayer(targetName)) == null) {
            sender.sendMessage("§cEsse jogador está offline.");
            return true;
        }
        if (impossibleToBan(targetName)) {
        	   sender.sendMessage("§cUtilize o /report apenas para denunciar jogadores.");
               return true;          
        }
        if (targetPlayer.hasPermission("staffchat.use")) {
     	   sender.sendMessage("§cUtilize o /report apenas para denunciar jogadores.");
            return true;
     }
        if (targetName.equalsIgnoreCase(sender.getName())) {
            sender.sendMessage("§cVocê não pode se reportar");
            return true;
        }
        if (HelixCooldown.inCooldown(sender.getName(), "report")) {
            sender.sendMessage("§cEspere " + HelixCooldown.getTime(sender.getName(), "report") + " segundos para reportar novamente.");
            return true;
        }
        StringBuilder reasonBuilder = new StringBuilder();
        for (int i = 1; i < args.length; i++)
            reasonBuilder.append(args[i]).append(" ");
        String reason = reasonBuilder.toString().trim();
        HelixCooldown.create(sender.getName(), "report", TimeUnit.SECONDS, 60L);
       Bukkit.getServer().getOnlinePlayers().stream().filter(online -> online.hasPermission("staffchat.use"))

                .forEach(online -> {
                    online.sendMessage("");
                    online.sendMessage("§3Novo Report");
                    online.sendMessage("§bVitíma: §6" + sender.getName());
                    online.sendMessage("§bAcusado: §c" + targetName);
                    online.sendMessage("§bKit do Acusado: §c" + RegisterAbility.getAbility(targetPlayer));
                    online.sendMessage("§bMotivo: §8" + reason);
                    online.playSound(online.getLocation(), Sound.ANVIL_LAND, 10, 10);
                });
	return false;

   
    }


private static boolean impossibleToBan(String nickName) {
    return Stream.of("EvilNaraku", "Sweake_PvP", "Rafael_Auler").anyMatch(s -> s.equalsIgnoreCase(nickName));
}


}