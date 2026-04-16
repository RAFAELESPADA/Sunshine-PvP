package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Fireman implements Listener {

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            Player player = (Player) e.getEntity();
            if (RegisterAbility.getAbility(player).equalsIgnoreCase("Fireman")) {
                if ((e.getCause() == EntityDamageEvent.DamageCause.LAVA ||
                        e.getCause() == EntityDamageEvent.DamageCause.FIRE ||
                        e.getCause() == EntityDamageEvent.DamageCause.FIRE_TICK)) {
                    e.setCancelled(true);
                }
            }
        }
    }
}
