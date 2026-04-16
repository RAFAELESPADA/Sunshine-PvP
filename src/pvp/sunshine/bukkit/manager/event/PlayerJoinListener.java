package pvp.sunshine.bukkit.manager.event;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.scheduler.BukkitRunnable;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.team.AdminCMD;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.PvPUtil;
import pvp.sunshine.bukkit.utils.TitleUtil;

public class PlayerJoinListener implements Listener {
	
	@EventHandler
	public void onJoin(PlayerJoinEvent event) {
		Player player = event.getPlayer();
		event.setJoinMessage(null);
		PvPUtil.setTab(event.getPlayer(), "\n §b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer") + "\n",
				"\n §eTwitter §f" + BukkitMain.getInstance().getConfig().getString("Twitter") + "\n §eDiscord: §f" + BukkitMain.getInstance().getConfig().getString("Discord") + "\n \n §a       Obtenha seus planos §d§lVIPS§a acessando: §f" + BukkitMain.getInstance().getConfig().getString("Discord") + "\n ");
		Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
			@Override
			public void run() {
				teleportToSpawnLocation(player);
			}
		}, 1L);
		preparePlayer(event.getPlayer());

	}

	private void preparePlayer(Player player) {
		sendWelcomeTitle(player);
		configurePlayer(player);
		clearPlayerAbilities(player);
		Cooldown.remove(player);
		Flag.setProtection(player, true);
		clearPotionEffects(player);
		hideAdminsFromPlayer(player);
		int i = 0;
		while (i < 100) {
			player.sendMessage(" ");
			i++;
		}
		LuckPerms api = LuckPermsProvider.get();
		player.performCommand("tag " + api.getUserManager().getUser(player.getUniqueId()).getPrimaryGroup().toLowerCase());
		player.sendMessage("§a§lCONNECT §fConectado com sucesso.");
	}

	private void teleportToSpawnLocation(Player player) {

        Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);player.teleport(spawnLocation);
        
	}

	private void sendWelcomeTitle(Player player) {
		new BukkitRunnable() {
			@Override
			public void run() {
				TitleUtil.sendTitle(player, 0, 0, 0, "§e§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"), "§fDivirta-se!");
			}
		}.runTaskLater(BukkitMain.getInstance(), 20L);

    }

	private void configurePlayer(Player player) {
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setGameMode(GameMode.ADVENTURE);
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.setHealth(20.0D);
		PvPUtil.spawnItem(player);
		PvP.update(player);
		player.updateInventory();
		player.playSound(player.getLocation(), Sound.LEVEL_UP, 3.0F, 3.0F);
		player.sendMessage("§a§lCONNECT §fConectado com sucesso.");
	}

	private void clearPlayerAbilities(Player player) {
		RegisterAbility.removeAbility(player);
		player.setFireTicks(0);
	}

	private void clearPotionEffects(Player player) {
		player.getActivePotionEffects().forEach(potionEffect -> player.removePotionEffect(potionEffect.getType()));
	}

	private void hideAdminsFromPlayer(Player player) {
		if (!player.hasPermission("pvp.admin"))
			for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
				if (AdminCMD.admin.contains(onlinePlayer.getName()))
					player.hidePlayer(onlinePlayer);
			}
	}
}