package net.kombopvp.pvp.kit.provider;

import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.kombopvp.pvp.kit.KitHandler;
import net.kombopvp.pvp.kit.KitManager;

public class Camel extends KitHandler {

    @EventHandler
    public void EntityDamage(EntityDamageEvent event) {
        if (event.isCancelled()) return;
        if (!(event.getEntity() instanceof Player)) return;
        Player player = (Player) event.getEntity();
        if (!KitManager.getPlayer(player.getName()).hasKit(this)) {
        	return;
        }
        if (event.getCause() == EntityDamageEvent.DamageCause.CONTACT) event.setCancelled(true);
    }

    @EventHandler
    public void PlayerMove(PlayerMoveEvent event) {
        if (event.isCancelled()) return;
        Player player = event.getPlayer();
        if (!KitManager.getPlayer(player.getName()).hasKit(this)) {
        	return;
        }
        Block block = event.getTo().getBlock().getRelative(BlockFace.DOWN);
        if (block.getBiome().name().contains("DESERT") || block.getType() == Material.SAND || block.getType() == Material.SANDSTONE) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 4 * 20, 0), true);
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 4 * 20, 0), true);
        }
    }

}
