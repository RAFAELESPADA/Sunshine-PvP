package net.kombopvp.pvp.listener;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scoreboard.DisplaySlot;

import net.kombopvp.pvp.KomboPvP2;

public class EventoUtils {
	

	public EventoUtils(KomboPvP2 plugin) {
		this.plugin = plugin;
	}
	private final KomboPvP2 plugin;
    public static boolean evento = false;
    public static boolean eventoplayer = false;
    public static boolean specs = false;
    public static boolean build = false;
    public static boolean damage = false;
    public static boolean pvp = false;
   
    public static boolean tp = false;
    public static boolean started = false;
    public static Location mainArena = new Location(Bukkit.getWorld(KomboPvP2.getInstance().getConfig().getString("EVENTOSPAWNMUNDO")), KomboPvP2.getInstance().getConfig().getInt("EVENTOSPAWNX"), KomboPvP2.getInstance().getConfig().getInt("EVENTOSPAWNY"), KomboPvP2.getInstance().getConfig().getInt("EVENTOSPAWNZ"));
    public static Location specLoc = null;
    public static ArrayList<String> game = new ArrayList();
    public static List<UUID> whitelist = new ArrayList<>();
    public static List<Location> blocks = new ArrayList<>();
    public static List<Location> blocksV = new ArrayList<>();
    public static List<String> getEventoPlayersNames() {
        List<String> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {

            if (game.contains(p.getName())) players.add(p.getName());
        });
        return players;
    }

    public static List<Player> getEventoPlayers() {
        List<Player> players = new ArrayList<>();
        Bukkit.getOnlinePlayers().forEach(p -> {

            if (game.contains(p.getName())) players.add(p);
        });
        return players;
    }

    public static PotionEffectType getPotionEffectTypeByName(String name) {
        name = name.replace("slowness", "slow").replace("haste", "fast")
        .replace("fatigue", "slow_dig").replace("strength", "increase");
        for (PotionEffectType effect : PotionEffectType.values()) {
            if (effect == null) continue;
            if (effect.getName().toLowerCase().contains(name.toLowerCase())) {
                return effect;
            }
        }
        return null;
    }

    public static List<String> getWhitelistPlayersNames() {
        List<String> players = new ArrayList<>();
        for (UUID uuid : whitelist) {
            Player p = Bukkit.getPlayer(uuid);
            if (p != null) players.add(p.getName());
        }
        return players;
    }

    public static void clearBlocks() {
        blocks.forEach(blockLoc -> {
            if (blockLoc.getBlock().getType() != Material.AIR) {
                blockLoc.getBlock().setType(Material.AIR);
            }
        });
        blocks.clear();
    }

    public static void resetEventoClass() {
        if (blocks.size() > 0) clearBlocks();
        evento = false;
        specs = false;
        build = false;
        damage = false;
        pvp = false;
        RDMAutomatic.iniciou = false;
        RDMAutomatic.star = false;
        started = false;
        tp = false;
        specLoc = null;
        whitelist.clear();
        blocks.clear();
    }

	public static void setEvento(boolean b, Player player) {
		if (b) {
			EventoUtils.game.add(player.getName());
			if (player != null) {
				
				KomboPvP2.getInstance().getScoreboardBuilder().build(player);
				player.sendMessage(ChatColor.GREEN + "Te adicionando no evento");
			}
		}
		else if (game.contains(player.getName())) {
			EventoUtils.game.remove(player.getName());
			if (player != null) {
				KomboPvP2.getInstance().getScoreboardBuilder().build(player);
			player.sendMessage(ChatColor.GREEN + "Te removendo do evento");
		}
		}
		
	}

}
