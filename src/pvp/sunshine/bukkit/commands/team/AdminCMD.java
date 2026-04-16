package pvp.sunshine.bukkit.commands.team;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.bossbar.BossBarAPI;
import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.event.HelixActionBar;
import pvp.sunshine.bukkit.utils.ActionUtil;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class AdminCMD implements CommandExecutor, Listener {

	private int actionBarTaskID = -1;
	
	public static final List<String> admin = new ArrayList<>();
	private static final HashMap<String, ItemStack[]> savedInventory = new HashMap<>();
	private static final HashMap<String, ItemStack[]> savedArmor = new HashMap<>();

	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (!(sender instanceof Player)) {
			return true;
		}

		Player player = (Player) sender;

		if (!player.hasPermission("pvp.admin")) {
			sender.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
			return true;
		}

		if (!admin.contains(player.getName())) {
			enableAdminMode(player);
		} else {
			disableAdminMode(player);
		}

		return true;
	}

	@EventHandler
	public void onBlockPlace(BlockPlaceEvent event) {
		if (admin.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onBlockBreak(BlockBreakEvent event) {
		if (admin.contains(event.getPlayer().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInventoryClick(InventoryClickEvent event) {
		if (admin.contains(event.getWhoClicked().getName())) {
			event.setCancelled(true);
		}
	}

	@EventHandler
	public void onInteract(PlayerInteractEvent event) {
		Player player = event.getPlayer();

		if (admin.contains(player.getName()) && event.getAction() == Action.RIGHT_CLICK_AIR
				&& event.getPlayer().getItemInHand().getType() == Material.REDSTONE) {
			player.chat("/admin");
		}
	}

	@EventHandler
	public void onInteractWithMagmaCream(final PlayerInteractEvent e) {
		if (admin.contains(e.getPlayer().getName())
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& e.getPlayer().getItemInHand().getType() == Material.getMaterial(351)) {
			e.setCancelled(true);
			e.getPlayer().chat("/admin");
			(new BukkitRunnable() {
				public void run() {
					e.getPlayer().chat("/admin");
				}
			}).runTaskLater((Plugin) BukkitMain.getInstance(), 10L);
		}
	}

	@EventHandler
	public void onQuit(PlayerQuitEvent event) {
		admin.remove(event.getPlayer().getName());
	}

	private void enableAdminMode(Player player) {
		admin.add(player.getName());
		player.sendMessage("§3§lADMIN §fVocê entrou no modo admin.");
		HelixActionBar.send(player, "§5Você está no modo admin.");
		player.setGameMode(GameMode.CREATIVE);
		savedInventory.put(player.getName(), player.getInventory().getContents());
		savedArmor.put(player.getName(), player.getInventory().getArmorContents());
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		Flag.setProtection(player, true);
		actionBarTaskID = Bukkit.getScheduler().runTaskTimer(BukkitMain.getInstance(), () -> {
			if (admin.contains(player.getName())) {
				ActionUtil.sendActionbar(player, "§aAtualmente temos §f" + admin.size() + " §apessoa(s) no modo admin.");
			}
		}, 0, 20).getTaskId();
		Bukkit.broadcast("§7* STAFF " + player.getName() + " entrou no modo admin.", "pvp.broadcast");

		for (Player all : Bukkit.getOnlinePlayers()) {
			if (!all.hasPermission("pvp.admin")) {
				all.hidePlayer(player);
			}
		}
		PvPUtil.getItem(player, Material.STONE_SWORD, 1, "§eChecar Kill-Aura", 0);
		PvPUtil.getItem(player, Material.PAPER, 1, "§eVer Informações do Jogador", 3);
		PvPUtil.getItem(player, Material.BOWL, 1, "§eTestar AutoSoup", 5);
		PvPUtil.getItemStackAdmin(player, 1, "§eTroca Rápida", 4);
		PvPUtil.getItem(player, Material.REDSTONE, 1, "§eSair do Admin", 8);
	}

	@EventHandler
	public void click(PlayerInteractEntityEvent e) {
		if (!(e.getRightClicked() instanceof Player)) {
			return;
		}
		final Player c = (Player) e.getRightClicked();
		if (admin.contains(e.getPlayer().getName()) && (e.getPlayer().getItemInHand().getType() == Material.FEATHER)) {
			c.setVelocity(new Vector(0, 2, 0).setY(2));
		}
	}

	private void disableAdminMode(Player player) {
		admin.remove(player.getName());
		player.setGameMode(GameMode.ADVENTURE);
		BossBarAPI.removeBar(player);
		player.sendMessage("§3§lADMIN §fVocê saiu do modo admin.");
		Bukkit.broadcast("§7* STAFF " + player.getName() + " saiu do modo admin.", "pvp.broadcast");
		player.getInventory().clear();
		String playerAbility = RegisterAbility.getAbility(player);
		if (actionBarTaskID != -1) {
			Bukkit.getScheduler().cancelTask(actionBarTaskID);
			actionBarTaskID = -1;
		}

		Flag.setProtection(player, false);

		player.getInventory().setContents(savedInventory.get(player.getName()));
		player.getInventory().setArmorContents(savedArmor.get(player.getName()));
		player.updateInventory();

		for (Player all : Bukkit.getOnlinePlayers())
			all.showPlayer(player);
	}

	@EventHandler
	public void Abririnv(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player && e.getPlayer().getItemInHand().getType() == Material.AIR
				&& admin.contains(e.getPlayer().getName())) {
			e.getPlayer().openInventory(((Player) e.getRightClicked()).getInventory());
		}
	}

	@EventHandler
	public void infoPlayer(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player && e.getPlayer().getItemInHand().getType() == Material.PAPER
				&& admin.contains(e.getPlayer().getName())) {
			Player t = (Player) e.getRightClicked();
			e.getPlayer().sendMessage(" ");
			e.getPlayer().sendMessage("      §e§lADMIN");
			e.getPlayer().sendMessage(" ");
			e.getPlayer().sendMessage(" §fInformações de: §e" + t.getName());
			e.getPlayer().sendMessage("");
			if (t.isFlying()) {
				e.getPlayer().sendMessage(" §fFly: §aAtivo");
			} else {
				e.getPlayer().sendMessage(" §fFly: §cDesativado");
			}
			e.getPlayer().sendMessage(" §fKit: §e" + RegisterAbility.getAbility(t));
			e.getPlayer().sendMessage("");
		}
	}

	@EventHandler
	public void cage(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player && e.getPlayer().getItemInHand().getType() == Material.BOWL
				&& admin.contains(e.getPlayer().getName())) {
			Player t = (Player) e.getRightClicked();
			e.getPlayer().chat("/autosoup " + t.getName());
		}
	}

    @EventHandler
    public void cage2(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player && e.getPlayer().getItemInHand().getType() == Material.STONE_SWORD
                && admin.contains(e.getPlayer().getName())) {
            Player t = (Player) e.getRightClicked();
            e.getPlayer().chat("/auracheck " + t.getName());
        }
    }
}