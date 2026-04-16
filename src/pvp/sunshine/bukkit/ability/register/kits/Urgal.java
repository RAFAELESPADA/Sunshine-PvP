package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.event.player.*;
import org.bukkit.event.block.*;
import org.bukkit.potion.*;
import org.bukkit.*;
import org.bukkit.scheduler.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Urgal implements Listener {
    @EventHandler
    public void onPlayerInteractEventUrgal(final PlayerInteractEvent e) {
        if ((e.getAction() == Action.RIGHT_CLICK_AIR || (e.getAction() == Action.RIGHT_CLICK_BLOCK && e.getPlayer().getItemInHand().getType() == Material.REDSTONE)) && e.getPlayer().getItemInHand().getType().equals((Object) Material.REDSTONE)) {
            if (RegisterAbility.getAbility(e.getPlayer()) != "Urgal") {
                return;
            }
            if (Cooldown.add(e.getPlayer())) {
                e.getPlayer().sendMessage("§c§lKIT §fAguarde §c" + Cooldown.cooldown(e.getPlayer()) + "s §fpara usar novamente.");
                return;
            }
            e.getPlayer().sendMessage("§a§lKIT §fSuper força ativada!");
            e.setCancelled(true);
            e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 250, 0));
            e.getPlayer().playSound(e.getPlayer().getLocation(), Sound.BLAZE_DEATH, 1.0f, 1.0f);
            Cooldown.add(e.getPlayer(), 20);
            new BukkitRunnable() {
                public void run() {
                    e.getPlayer().sendMessage("§a§lCOOLDOWN §fAgora você pode usar seu kit novamente.");
                }
            }.runTaskLater((Plugin) BukkitMain.getInstance(), 400L);
        }
    }
}
