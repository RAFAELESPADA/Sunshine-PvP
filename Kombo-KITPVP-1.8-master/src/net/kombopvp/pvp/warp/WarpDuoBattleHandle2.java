package net.kombopvp.pvp.warp;



import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.BlockFace;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.inventory.Modo;
import net.kombopvp.warp.provider.Duels;
import net.kombopvp.warp.provider.Gladiator;
import net.kombopvp.warp.provider.OneVsOne;
import net.kombopvp.warp.provider.Sumo;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.item.ItemBuilder;
import net.wavemc.core.bukkit.util.AdminUtil;
import net.wavemc.core.util.WaveCooldown;

public abstract class WarpDuoBattleHandle2 extends WarpHandle {


    private final String warpPos1, warpPos2 , warpPos1_2 , warpPos2_2 , warpPos1_3 , warpPos2_3;
	Path path1 = Paths.get(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/WaveCore/", "warps.yml");
	File file = new File(path1.toAbsolutePath().toString());
	YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
    public WarpDuoBattleHandle2(String warpPos1, String warpPos2, String warpPos1_2, String warpPos2_2 , String warpPos1_3, String warpPos2_3) {
        this.warpPos1 = warpPos1;
        this.warpPos2 = warpPos2;
        this.warpPos1_2 = warpPos1_2;
        this.warpPos2_2 = warpPos2_2;
        this.warpPos1_3 = warpPos1_3;
        this.warpPos2_3 = warpPos2_3;
    }

    public static List<Player> fastChallenge = new ArrayList<>();
    protected final static HashMap<Player, Player> battlingPlayers = new HashMap<>();
	
    public void setItems(Player player) {
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
    }

    public final Optional<Player> findOpponent(Player player) {
        return battlingPlayers.containsKey(player) ? Optional.of(battlingPlayers.get(player)) :
                battlingPlayers.entrySet().stream().filter(entry -> entry.getValue().equals(player)).map(Map.Entry::getKey).findFirst();
    }
    public static final boolean isOnBattle(Player player) {
        return battlingPlayers.containsKey(player);
    }
    public static final boolean estabatalhando(Player player) {
        return battlingPlayers.containsKey(player);
    }
    public final void finalizeBattle(Player player) {
        show(player);


        findOpponent(player).ifPresent(target -> {
            show(target);

        });

        fastChallenge.remove(player);
        battlingPlayers.entrySet().removeIf(entry -> entry.getKey().equals(player) || entry.getValue().equals(player));
    }

    public void sendBattleItems(Player player) {
        player.setGameMode(GameMode.ADVENTURE);
        player.getInventory().clear();
        player.getInventory().setArmorContents(null);
        player.setAllowFlight(false);
        player.setFlying(false);
        player.getInventory().setHeldItemSlot(0);
        player.getActivePotionEffects().forEach(potion -> player.removePotionEffect(potion.getType()));
    }
	@EventHandler
	public void onInvClicki2(InventoryClickEvent event) {
		Player player = (Player) event.getWhoClicked();
		if (event.getInventory().getType() == InventoryType.PLAYER) {
            return;
        }
		if (!event.getView().getTitle().equals(Modo.getInventoryName())) {
			return;
		}
		if (event.getCurrentItem() == null) {
			return;
		}
		if (!(event.getCurrentItem().getType() == Material.LEASH)) {
			return;
		}
		if (Sumo.fastChallenge.contains(player) || OneVsOne.fastChallenge.contains(player) || Gladiator.fastChallenge.contains(player)) {
			player.sendMessage(ChatColor.RED + "Você já está em uma fila");
			player.sendMessage(ChatColor.RED + "Para sair da fila use /spawn");
			player.closeInventory();
			event.setCancelled(true);
			return;
		}
		if (!ItemBuilder.has(event.getCurrentItem(), "sumoi")) {
			return;
		}
		event.setCancelled(true);
		Player p2 = Duels.duels.get(player);
		if (Duels.duels.containsKey(player)) {
			p2.sendMessage(ChatColor.GREEN + "Você foi desafiado por " + player.getName() + " para um Sumo");
			Duels.b.put(p2, player);
			player.sendMessage(ChatColor.GREEN + "Você desafiou " + p2.getName());
			player.closeInventory();
			WaveCooldown.create(player.getName(), "1v1s-challenge-" + p2.getName(), TimeUnit.SECONDS, 15);
			WaveBukkit.getExecutorService().submit(() -> {
				new BukkitRunnable() {
					@Override
					public void run() {
						Duels.b.remove(p2);
						player.closeInventory();
					}}.runTaskLater(KomboPvP2.getInstance(), 100 * 1L);
			});
	    }
	}
    public void startBattle(Player p1, Player p2) {
        fastChallenge.remove(p1); fastChallenge.remove(p2);
        if (p1 == p2) {
        	finalizeBattle(p1);
        	p1.sendMessage("BATALHA FOI FINALIZADA PORQUE VOCÊ ESTAVA SOZINHO NO SUMO!");
        	return;
        }
        else if (Sumo.isNpc(p2) || Sumo.isNpc(p1)) {
        	
        	fastChallenge.remove(p1);

        	finalizeBattle(p1);
        	fastChallenge.remove(p2);
        	p1.sendMessage(ChatColor.DARK_RED + "A procura falhou! Tente novamente.");
        	return;
        	}
        Sumo.fastChallenge.remove(p1); Sumo.fastChallenge.remove(p2);
        OneVsOne.fastChallenge.remove(p1); OneVsOne.fastChallenge.remove(p2);
        Gladiator.fastChallenge.remove(p1);  Gladiator.fastChallenge.remove(p2);
        p1.setHealth(p1.getMaxHealth());
        Duels.protector.remove(p1.getName());
        Duels.protector.remove(p2.getName());
        p2.setHealth(p2.getMaxHealth());
        if (yaml.getString("Mundo-" +warpPos1) == null || yaml.getString("Mundo-" +warpPos2) == null) {
            p1.sendMessage("§cOcorreu um erro ao iniciar a batalha. (LOC-404)");
            WaveWarp2.DUELS.send(p2);
            WaveWarp2.DUELS.send(p1);
            p2.sendMessage("§cOcorreu um erro ao iniciar a batalha. (LOC-404)");
            return;
        }
        
       	Location l1 = new Location(Bukkit.getWorld("1v1"), 6448.124, 70.00000000, 6685.861);            
       
   		Location l2 = new Location(Bukkit.getWorld("1v1"), 6438.072, 70.00000, 6685.597);

        l1.setYaw(90.9f);
        l1.setPitch(6.3f);
        l2.setYaw(-91.0f);
        l2.setPitch(3.9f);
        
             p1.teleport(l1);
                   p2.teleport(l2);
               

        sendBattleItems(p1); sendBattleItems(p2);
        hide(p1, p2);

Duels.c.remove(p1);
Duels.c.remove(p2);
Duels.b.remove(p2);
Duels.b.remove(p1);
Duels.glad.remove(p2);
Duels.glad.remove(p1);
        battlingPlayers.put(p1, p2);

    }
    private float toDegree(double angle) {
        return (float) Math.toDegrees(angle);
    }
     
    private Vector getVector(Entity entity) {
        if (entity instanceof Player)
            return ((Player) entity).getEyeLocation().toVector();
        else
            return entity.getLocation().toVector();
    }
    public void hide(Player p1, Player p2) {
        Bukkit.getOnlinePlayers().stream().filter(
                online -> !online.getName().equals(p1.getName())
                        && !online.getName().equals(p2.getName())
        ).forEach(online -> {
            p1.hidePlayer(online);
            p2.hidePlayer(online);
        });
    }

    public void show(Player player) {
        Bukkit.getOnlinePlayers().stream().filter(
                online -> !AdminUtil.has(online.getName())
        ).forEach(player::showPlayer);
    }

    @EventHandler(ignoreCancelled = true)
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player)
                || (!(event.getDamager() instanceof Player))) {
            return;
        }

        Player victim = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (battlingPlayers.containsKey(victim) && !battlingPlayers.get(victim).getName().equals(damager.getName())) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerCommandProcess(PlayerCommandPreprocessEvent event) {
        Player player = event.getPlayer();

        if (fastChallenge.contains(player) && event.getMessage().split(" ")[0].equalsIgnoreCase("spawn")) {
            fastChallenge.remove(player);
        }
    }

    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        battlingPlayers.forEach((p1, p2)-> {
            p1.hidePlayer(event.getPlayer());
            p2.hidePlayer(event.getPlayer());
        });
    }
}

