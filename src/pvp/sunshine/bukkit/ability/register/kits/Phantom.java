package pvp.sunshine.bukkit.ability.register.kits;

import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Phantom implements Listener {
	public static ArrayList<String> emphantom = new ArrayList<>();

	@EventHandler
	public void onPlayerInteractEventPhantom(final PlayerInteractEvent e) {
		if (RegisterAbility.getAbility(e.getPlayer()).equalsIgnoreCase("Phantom")
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& e.getPlayer().getItemInHand().getType() == Material.FEATHER) {
			if (Cooldown.add(e.getPlayer())) {
				e.getPlayer().sendMessage(
						"§c§lCOOLDOWN §fAguarde §c" + Cooldown.cooldown(e.getPlayer()) + "s §fpara usar novamente.");
				return;
			}
			e.getPlayer().getInventory().setHelmet(new ItemStack(Material.AIR));
			e.getPlayer().getInventory().setChestplate(new ItemStack(Material.AIR));
			e.getPlayer().getInventory().setLeggings(new ItemStack(Material.AIR));
			e.getPlayer().getInventory().setBoots(new ItemStack(Material.AIR));
			e.getPlayer().getInventory().setHelmet(new ItemStack(Material.LEATHER_HELMET));
			e.getPlayer().getInventory().setChestplate(new ItemStack(Material.LEATHER_CHESTPLATE));
			e.getPlayer().getInventory().setLeggings(new ItemStack(Material.LEATHER_LEGGINGS));
			e.getPlayer().getInventory().setBoots(new ItemStack(Material.LEATHER_BOOTS));
			e.getPlayer().updateInventory();
			emphantom.add(e.getPlayer().getName());
			e.getPlayer().setAllowFlight(true);
			e.getPlayer().setFlying(true);
			e.getPlayer().sendMessage("§a§lKIT §fSeu voo foi habilitado, você tem apenas 5 segundos!");
			Cooldown.add(e.getPlayer(), 25);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§e§lKIT §fDesabilitando em 5...");
				}
			}, 0L);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§e§lKIT §fDesabilitando em 4...");
				}
			}, 20L);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§e§lKIT §fDesabilitando em 3...");
				}
			}, 40L);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§e§lKIT §fDesabilitando em 2...");
				}
			}, 60L);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§e§lKIT §fDesabilitando em 1...");
				}
			}, 80L);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§e§lKIT §fO tempo acabou.");
					e.getPlayer().getInventory().setArmorContents(null);
					Phantom.emphantom.remove(e.getPlayer().getName());
					e.getPlayer().updateInventory();
					e.getPlayer().setAllowFlight(false);
					e.getPlayer().setFlying(false);
				}
			}, 100L);
			Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), new Runnable() {
				public void run() {
					e.getPlayer().sendMessage("§a§lCOOLDOWN §fAgora você pode usar seu kit novamente.");
				}
			}, 500L);
		}
	}

	@EventHandler
	public void onInventoryClickEvent(InventoryClickEvent e) {
		try {
			if (e.getWhoClicked() instanceof org.bukkit.entity.Player && emphantom.contains(e.getWhoClicked().getName())
					&& e.getSlotType().equals(InventoryType.SlotType.ARMOR)) {
				e.setCancelled(true);
			}
		} catch (Exception exception) {
		}
	}
}