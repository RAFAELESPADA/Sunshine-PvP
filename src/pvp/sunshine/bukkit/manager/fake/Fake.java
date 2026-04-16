package pvp.sunshine.bukkit.manager.fake;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import pvp.sunshine.bukkit.manager.mysql.Storage;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;

public class Fake extends Storage implements CommandExecutor, Listener  {

    private static final Map<String, String> availableTags = new HashMap<>();
    private final Map<Player, String> playerFakeTags;
    private final Map<Player, String> playerOriginalDisplayNames = new HashMap<>();

    public Fake(Map<Player, String> playerFakeTags) {
        this.playerFakeTags = playerFakeTags;
    }

    static {
        availableTags.put("Vip+", "§b§lVIP+ §b");
        availableTags.put("Vip", "§9§lVIP §9");
        availableTags.put("Membro", "§7");
        availableTags.put("Apoiador", "§5§lAPOIADOR §5");
    }
    @SuppressWarnings("unchecked")
    public void changeName(String name, Player player) {
        try {
            Method getHandle = player.getClass().getMethod("getHandle");
            Object entityPlayer = getHandle.invoke(player);
            /*
             * These methods are no longer needed, as we can just access the
             * profile using handle.getProfile. Also, because we can just use
             * the method, which will not change, we don't have to do any
             * field-name look-ups.
             */
            boolean gameProfileExists = false;
            // Some 1.7 versions had the GameProfile class in a different package
            try {
                Class.forName("net.minecraft.util.com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            try {
                Class.forName("com.mojang.authlib.GameProfile");
                gameProfileExists = true;
            } catch (ClassNotFoundException ignored) {

            }
            if (!gameProfileExists) {
                /*
                 * Only 1.6 and lower servers will run this code.
                 *
                 * In these versions, the name wasn't stored in a GameProfile object,
                 * but as a String in the (final) name field of the EntityHuman class.
                 * Final (non-static) fields can actually be modified by using
                 * {@link java.lang.reflect.Field#setAccessible(boolean)}
                 */
                Field nameField = entityPlayer.getClass().getSuperclass().getDeclaredField("name");
                nameField.setAccessible(true);
                nameField.set(entityPlayer, name);
            } else {
                // Only 1.7+ servers will run this code
                Object profile = entityPlayer.getClass().getMethod("getProfile").invoke(entityPlayer);
                Field ff = profile.getClass().getDeclaredField("name");
                ff.setAccessible(true);
                ff.set(profile, name);
            }
            // In older versions, Bukkit.getOnlinePlayers() returned an Array instead of a Collection.
            if (Bukkit.class.getMethod("getOnlinePlayers", new Class<?>[0]).getReturnType() == Collection.class) {
                Collection<? extends Player> players = (Collection<? extends Player>) Bukkit.class.getMethod("getOnlinePlayers").invoke(null);
                for (Player p : players) {
                    p.hidePlayer(player);
                    p.showPlayer(player);
                }
            } else {
                Player[] players = ((Player[]) Bukkit.class.getMethod("getOnlinePlayers").invoke(null));
                for (Player p : players) {
                    p.hidePlayer(player);
                    p.showPlayer(player);
                }
            }
        } catch (NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException | NoSuchFieldException e) {
            /*
             * Merged all the exceptions. Less lines
             */
            e.printStackTrace();
        }
    }
    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Este comando só pode ser usado por jogadores.");
            return true;
        }

        Player player = (Player) sender;
        if (!player.hasPermission("pvp.fake")) {
            player.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            return true;
        }
        else if (player.hasPermission("pvp.fake")) {
            player.sendMessage("§cEsse comando está em manutenção por tempo inderterminado.");
            return true;
        }

        if (args.length == 0 || args.length > 2) {
            player.sendMessage("§c§lERRO §fUso correto: /fake <nome> <tag>, /fake list, /fake reset ou /fake random");
            return true;
        }

        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("list")) {
                showFakeList(player);
                return true;
            } else if (args[0].equalsIgnoreCase("reset")) {
                if (FakeNameManager.getFakeName(player.getUniqueId()) == null) {
                    player.sendMessage("§c§lFAKE §fVocê não está com nenhum fake agora.");
                    return true;
                }
                resetFakeName(player);
                return true;
            } else if (args[0].equalsIgnoreCase("random")) {
                setRandomFake(player);
                return true;
            }
        }

        if (FakeNameManager.getFakeName(player.getUniqueId()) != null) {
            player.sendMessage("§c§lFAKE §fVocê já está usando um fake. Para remover, utilize /fake reset");
            return true;
        }

        String fakeName = args[0];
       
if (args.length < 2 && !args[0].equals("reset") && !args[0].equals("random") && !args[0].equals("list")) {
	   player.sendMessage("§c§lERRO §fTag inválida. Tags disponíveis: " + String.join(", ", availableTags.keySet()));
       return true;
   
}
     String tag = args[1];   if (fakeName.length() < 4 || fakeName.length() > 16) {
            player.sendMessage("§c§lFAKE §fO nome falso deve ter entre 4 e 16 caracteres.");
            return true;
        }

        if (!fakeName.matches("^[a-zA-Z0-9_]+$")) {
            player.sendMessage("§c§lFAKE §fO nome falso só pode conter letras, números e sublinhados.");
            return true;
        }

        if (!availableTags.containsKey(tag)) {
            player.sendMessage("§c§lERRO §fTag inválida. Tags disponíveis: " + String.join(", ", availableTags.keySet()));
            return true;
        }
        if (Bukkit.getPlayer(fakeName) != null) {
        	   player.sendMessage("§c§lERRO §fEsse nick está online no servidor como jogador!");
               return true;
           
        }
        try (PreparedStatement ps = connection.prepareStatement("SELECT * FROM PvP WHERE UUID= ?")) {
        		ps.setString(1, UUID.fromString(Bukkit.getOfflinePlayer(fakeName).getUniqueId().toString()).toString());
                       ResultSet rs = ps.executeQuery(); 

   		     
               while (rs.next()) {
                   String playerName = rs.getString("UUID");

                   String Name = Bukkit.getOfflinePlayer(UUID.fromString(playerName)).getName();
               if (Name != null) {
            	   player.sendMessage("§c§lERRO §fEsse nick está cadastrado no banco de dados como um jogador");
                   return true;
               
               }
               }
        String formattedName = availableTags.get(tag) + fakeName;
        String originalDisplayName = player.getDisplayName();
        player.setDisplayName(formattedName);
        player.setPlayerListName(formattedName);
        changeName(fakeName, player);
        FakeNameManager.setFakeName(player.getUniqueId(), fakeName);
        FakeNameManager.setRealName(player.getUniqueId(), player.getName());
        changeName(fakeName, player);
        
        PvP.update(player);
        playerFakeTags.put(player, availableTags.get(tag));
        playerOriginalDisplayNames.put(player, originalDisplayName);
       player.sendMessage("§a§lFAKE §fSeu nick foi atualizado para §e" + formattedName);
        return true;
    } catch (SQLException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
		return false;
    }

    private void showFakeList(Player player) {
        StringBuilder fakeList = new StringBuilder("§a§lFAKE §fLista de jogadores com nomes falsos:\n");

        boolean hasFake = false;
        for (UUID uuid : FakeNameManager.getRealNames().keySet()) {
            Player fakePlayer = Bukkit.getPlayer(uuid);
            if (fakePlayer != null && fakePlayer.isOnline()) {
                fakeList.append(" §7* §e").append("§bFake: " + fakePlayer.getName()).append(" §f- §7Nick Real: §e").append(FakeNameManager.getRealName(uuid)).append("\n");
                hasFake = true;
            }
        }

        if (!hasFake) {
            fakeList.append("§cNão há ninguém de fake agora.");
        }

        player.sendMessage(fakeList.toString());
    }

    private void resetFakeName(Player player) {
        FakeNameManager.removeFakeName(player.getUniqueId());
        FakeNameManager.removeRealNameCache(player.getUniqueId());
        playerFakeTags.remove(player);
        player.setPlayerListName(player.getName());
        player.setDisplayName("§7");
        changeName(player.getName(), player);
        PvP.update(player);
        player.kickPlayer("§a§lFAKE §fSeu nick falso foi removido.");
       
    }

    private void setRandomFake(Player player) {
        if (FakeNameManager.getFakeName(player.getUniqueId()) != null) {
            player.sendMessage("§c§lFAKE §fVocê já está usando um fake. Para remover, utilize /fake reset");
            return;
        }

        String fakeName = FakeNameGenerator.generateFakeName();
        String tag = getRandomTag();
        FakeNameManager.setRealName(player.getUniqueId(), player.getName());
        changeName(fakeName, player);
  
        String formattedName = availableTags.get(tag) + fakeName;
        String originalDisplayName = player.getDisplayName();
        player.setDisplayName(formattedName);
        player.setPlayerListName(formattedName);
        FakeNameManager.setFakeName(player.getUniqueId(), fakeName);
        playerFakeTags.put(player, availableTags.get(tag));
        playerOriginalDisplayNames.put(player, originalDisplayName);
        player.sendMessage("§a§lFAKE §fSeu nick foi atualizado para §e" + formattedName);
        player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
    }

    private String getRandomTag() {
        String[] tags = availableTags.keySet().toArray(new String[0]);
        int index = (int) (Math.random() * tags.length);
        return tags[index];
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        Player player = event.getPlayer();
        removeFake(player);
    }

    @EventHandler
    public void onPlayerQuit2(PlayerKickEvent event) {
        Player player = event.getPlayer();
        removeFake(player);
    }

    private void removeFake(Player player) {
        if (FakeNameManager.getFakeName(player.getUniqueId()) != null) {
            FakeNameManager.removeFakeName(player.getUniqueId());
            playerFakeTags.remove(player);
            String originalDisplayName = playerOriginalDisplayNames.remove(player);
            player.setPlayerListName(player.getName());
            changeName(player.getName(), player);
            player.setDisplayName(originalDisplayName);
        }
    }
}
