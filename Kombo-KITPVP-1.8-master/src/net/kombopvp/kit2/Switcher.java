package net.kombopvp.kit2;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Snowball;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.inventory.ItemStack;

import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.kit.KitHandler2;
import net.kombopvp.pvp.kit.KitManager;
import net.kombopvp.pvp.kit.KitManager2;
import net.kombopvp.pvp.kit.WaveKit;
import net.kombopvp.pvp.kit.WaveKit2;
import net.kombopvp.pvp.kit.provider.EnderMageReal;
import net.kombopvp.pvp.kit.provider.GladiatorListener2;
import net.wavemc.core.bukkit.item.ItemBuilder;

public class Switcher extends KitHandler2 {


    @Override
    public void execute(Player player) {
        super.execute(player);

        player.getInventory().setItem(2, new ItemBuilder(Material.SNOW_BALL)
                .amount(2)
                .nbt("cancel-drop").nbt("kit-handler", "switcher")
                .toStack()
        );
    }
@EventHandler
public void snowball(final ProjectileLaunchEvent e) {
    if (e.getEntity() instanceof Snowball) {
    	if (e.getEntity().getShooter() instanceof Player) {
        final Player p = (Player) e.getEntity().getShooter();
            if (!KitManager2.getPlayer(p.getName()).haskit2(this) || !p.getItemInHand().equals(new ItemStack(Material.SNOW_BALL))) {
            	return;
            }
            if (GladiatorListener2.combateGlad.containsKey(p) || net.kombopvp.kit2.GladiatorListener.combateGlad.containsKey(p)) {
                p.sendMessage("§cNão utilize isso no Gladiator!");
                e.setCancelled(true);
                return;
            }
            if (inCooldown(p)) {
            	e.setCancelled(true);
                sendMessageCooldown(p);
                p.getInventory().addItem(new ItemBuilder(Material.SNOW_BALL)
                        .amount(1)
                        .nbt("cancel-drop").nbt("kit-handler", "switcher")
                        .toStack());
                p.sendMessage("§cAguarde para usar novamente!");
                return;
            }
            if (inCooldown(p) && KitManager2.getPlayer(p.getName()).haskit2(this)) {
             e.setCancelled(true);
             sendMessageCooldown(p);
             p.getInventory().addItem(new ItemBuilder(Material.SNOW_BALL)
                     .amount(1)
                     .nbt("cancel-drop").nbt("kit-handler", "switcher")
                     .toStack());
             return;
            }
            p.getInventory().addItem( new ItemBuilder(Material.SNOW_BALL)
                    .amount(1)
                    .nbt("cancel-drop").nbt("kit-handler", "switcher")
                    .toStack());
            addCooldown(p , KomboPvP2.getInstance().getConfig().getInt("SwitcherCooldown"));
        
    	}
    }
    
}
    	  @EventHandler
    	  public void snowball(EntityDamageByEntityEvent e) {
    	    if (e.getDamager() instanceof Snowball && e.getEntity() instanceof Player) {
    	    	
    	      Snowball s = (Snowball)e.getDamager();
    	      if (s.getShooter() instanceof Player) {
    	        Player shooter = (Player)s.getShooter();
    	        if (!KitManager2.getPlayer(shooter.getName()).haskit2(this)) {
                	return;
                }
    	        else if (shooter.getLocation().getY() > KomboPvP2.getInstance().getConfig().getInt("SpawnAltura") && EnderMageReal.isSpawn(shooter.getLocation())) {
    	        	shooter.sendMessage("§cNão use seu poder no spawn!");
    	    		return;
    	       }
    	        Player p = (Player)e.getEntity();
    	        if (KitManager.getPlayer(p.getName()).hasKit(WaveKit.NEO) || KitManager2.getPlayer(p.getName()).haskit2(WaveKit2.NEO)) {
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 15.0f, 15.0f);
					shooter.sendMessage("§cSeu kit falhou porque seu oponente está de NEO!");
					return;
				}
    	        if (!KitManager.getPlayer(p.getName()).hasKit()) {
    	          return; 
    	        }
    	        Location shooterLoc = shooter.getLocation();
    	        shooter.teleport(e.getEntity().getLocation());
    	        p.teleport(shooterLoc);
    	        shooter.playSound(shooter.getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 10.0F);
    	        p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 10.0F, 10.0F);
    	      } 
    }
}
}

