package net.kombopvp.pvp.kit.provider;

import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.kit.KitHandler;
import net.kombopvp.pvp.kit.KitManager;
import net.kombopvp.pvp.kit.KitManager2;
import net.kombopvp.pvp.kit.WaveKit;
import net.kombopvp.pvp.kit.WaveKit2;

import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;


public class Hulk extends KitHandler {

	
	
	
	  @EventHandler
	    public void onInteract(PlayerDeathEvent event) {
	        Player p = (Player) event.getEntity();
	        Player k = (Player) event.getEntity().getKiller();
	        Entity passenger = p.getPassenger();
	        if (passenger != null) {
	        passenger.leaveVehicle();
	  
	}
	  }

    @EventHandler
    public void onInteract(PlayerInteractEntityEvent event) {
        if (!(event.getRightClicked() instanceof Player)) return;

        Player rightClicked = (Player) event.getRightClicked();
        if (!KitManager.getPlayer(rightClicked.getName()).hasKit()) return;
        if (!KitManager.getPlayer(event.getPlayer().getName()).hasKit(WaveKit.HULK)) return;
        if (inCooldown(event.getPlayer()) && KitManager.getPlayer(event.getPlayer().getName()).hasKit(this)) {
			sendMessageCooldown(event.getPlayer());
			return;
		}
        else if (event.getPlayer().getLocation().getY() > KomboPvP2.getInstance().getConfig().getInt("SpawnAltura") && KitManager.getPlayer(event.getPlayer().getName()).hasKit(this)) {
        	event.getPlayer().sendMessage("§cNão use o hulk no spawn!");
        	event.setCancelled(true);
			return;
		}
        else if (KitManager.getPlayer(rightClicked.getName()).hasKit(WaveKit.NEO) || KitManager2.getPlayer(rightClicked.getName()).haskit2(WaveKit2.NEO)) {
        	event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.NOTE_BASS, 15.0f, 15.0f);
			event.getPlayer().sendMessage(ChatColor.RED + "Você não pode usar o hulk em " + ChatColor.DARK_RED + rightClicked.getName() + ChatColor.RED + " porque ele esta com o kit" + ChatColor.DARK_RED + " NEO");
			return;
		}
        addCooldown(event.getPlayer(), 5);
        event.getPlayer().setPassenger(rightClicked);
    }

    @EventHandler (ignoreCancelled = true)
    public void onDamage(EntityDamageByEntityEvent event) {
        if (!(event.getEntity() instanceof Player) ||
                !(event.getDamager() instanceof Player)) return;

        Player entity = (Player) event.getEntity();
        Player damager = (Player) event.getDamager();

        if (!KitManager.getPlayer(damager.getName()).hasKit(WaveKit.HULK)) return;
        Entity passenger = damager.getPassenger();

        if (passenger != null && passenger == entity) {
            passenger.leaveVehicle();
            passenger.setVelocity(passenger.getLocation().getDirection().setY(2.6f).multiply(1.6f));
        }
    }
}