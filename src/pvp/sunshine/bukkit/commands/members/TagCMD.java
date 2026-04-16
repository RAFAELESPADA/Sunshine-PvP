package pvp.sunshine.bukkit.commands.members;



import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.neznamy.tab.api.TabAPI;
import me.neznamy.tab.api.tablist.SortingManager;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.luckperms.api.model.group.Group;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.NodeType;
import net.luckperms.api.node.types.PrefixNode;
import net.luckperms.api.query.QueryOptions;

public class TagCMD implements CommandExecutor {

	Map<String, Long> cooldowns = new HashMap<String, Long>();

	  public static HashMap<Player , String> tagatual = new HashMap();

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (Bukkit.getPluginManager().getPlugin("LB_Anunciar") != null) {
        	
             if (args.length == 0) {
                 sender.sendMessage("§cPara escolher uma tag padrão use /tag <Tag>, para escolher uma especial use o menu");
                 Bukkit.dispatchCommand(sender, "ytags:tag");
        }
        }
        if (Bukkit.getPluginManager().isPluginEnabled("TAB")) {
       SortingManager S =  TabAPI.getInstance().getSortingManager();
        }
        LuckPerms api = LuckPermsProvider.get();
       
        Player player = (Player) sender;
        Set<Group> groups = api.getGroupManager().getLoadedGroups();
        if (args.length == 0) {
        	List<String> groupsList = new ArrayList<>();

            StringBuilder tags = new StringBuilder();
            PlayerGroup[] values;
            for (int length = (values = PlayerGroup.values()).length, i = 0; i < length; ++i) {
                final PlayerGroup tag2 = values[i];
                if (player.hasPermission(tag2.getPermission())) {
                    tags.append((tags.length() == 0) ? "" : "§f, ").append(tag2.getColor()).append(tag2.getName().replace("plus", "+"));
                }
            }
            player.sendMessage("§aSuas tags disponíveis são: " + tags);
        	
        	
    
        	return true;
        }


        	
        
       
Player p = (Player)sender;


            
        

if (!Bukkit.getPluginManager().isPluginEnabled("TAB")) {
    sender.sendMessage("§cEsse comando está desativado nesta parte do servidor.");
    return true;
     }
        if (!player.hasPermission("tag." + args[0])) {
            player.sendMessage("§cVocê não tem essa tag ou ela não existe.");
            return true;
        }
        if (cooldowns.containsKey(player.getName() + "EPT")) {
            if (cooldowns.get(player.getName() + "EPT") > System.currentTimeMillis()) {
                long timeleft = (cooldowns.get(player.getName() + "EPT") - System.currentTimeMillis()) / 1000;
                player.sendMessage(ChatColor.RED + "Espere " + timeleft +" segundos para mudar sua tag novamente!");
                return true;
            }
        }

try {
	
        String prefix = api.getGroupManager().getGroup(args[0]).getCachedData().getMetaData().getPrefix();

        // Get an OfflinePlayer object for the player

       


        // Load, modify & save the user in LuckPerms.
        api.getUserManager().modifyUser(player.getUniqueId(), (User user) -> {

            // Remove all other prefixes the user had before.
            user.data().clear(NodeType.PREFIX::matches);

            // Find the highest priority of their other prefixes
            // We need to do this because they might inherit a prefix from a parent group,
            // and we want the prefix we set to override that!
            Map<Integer, String> inheritedPrefixes = user.getCachedData().getMetaData(QueryOptions.nonContextual()).getPrefixes();
            int priority = inheritedPrefixes.keySet().stream().mapToInt(i -> i + 10).max().orElse(10);

            // Create a node to add to the player.
            Node node = PrefixNode.builder(prefix, priority).build();

            // Add the node to the user.
            user.data().add(node);
          player.setDisplayName(String.valueOf(prefix + player.getName()));
            // Tell the sender.
            cooldowns.put(player.getName() + "EPT", System.currentTimeMillis() + 7 * 1000);
            sender.sendMessage("§a§lTAG: §fSua tag foi alterada para " + (args[0].equalsIgnoreCase("Membro") || (args[0].equalsIgnoreCase("default")) ? "§7[Membro]" : ChatColor.RESET + prefix.replace("&", "§")));
            if (Bukkit.getPluginManager().isPluginEnabled("TAB")) {
            	   TabAPI apitab = TabAPI.getInstance();
            apitab.getPlayer(player.getName()).setTemporaryGroup(args[0]);
            tagatual.put(player, args[0]);
            }
            Bukkit.getConsoleSender().sendMessage(player.getName() + " alterou a tag para " + args[0]);
        });
} catch (NullPointerException e) {
	sender.sendMessage(ChatColor.RED + "§cVocê não tem essa tag ou ela não existe.");
}

     

        
		return false;
    
    }
}