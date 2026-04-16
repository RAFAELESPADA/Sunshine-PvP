package pvp.sunshine.bukkit.manager.event;

import org.bukkit.entity.*;

import java.util.*;

import org.bukkit.event.*;
import org.bukkit.event.entity.*;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Flag implements Listener {
    private static Map<Player, Stats> flag;

    static {
        Flag.flag = new HashMap<Player, Stats>();
    }

    public static boolean getProtection(final Player p) {
        return Flag.flag.get(p) == Stats.ON;
    }

    @EventHandler
    public void onBlockedAtack(final EntityDamageEvent e) {
        if (e.getEntity() instanceof Player && e.getCause() != EntityDamageEvent.DamageCause.LAVA
                && RegisterAbility.getAbility((Player) e.getEntity()) != "Lava"
                && getProtection((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onDamageFallLava(EntityDamageEvent e) {
    	if (!(e.getEntity() instanceof Player)) {
    		return;
    	}
        if (RegisterAbility.getAbility((Player) e.getEntity()).equals("Lava") && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        } else if (RegisterAbility.getAbility((Player) e.getEntity()).equals("Sumo") && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        } else if (RegisterAbility.getAbility((Player) e.getEntity()).equals("Nenhum") && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        } else if (RegisterAbility.getAbility((Player) e.getEntity()).equals("1v1") && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void playerAttack(final EntityDamageByEntityEvent e) {
        final Player p = (Player) e.getEntity();
        if (RegisterAbility.getAbility(p) == "Lava") {
            e.setCancelled(true);
        }
        if (RegisterAbility.getAbility(p) == "Lava" && e.getCause() == EntityDamageEvent.DamageCause.FALL
                && getProtection((Player) e.getEntity())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void entityDamage(final EntityDamageByEntityEvent e) {
        if (!(e.getEntity() instanceof Player)) {
            return;
        }
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && getProtection((Player) e.getEntity())) {
            e.setCancelled(true);
        }
        if (e.getDamager() instanceof Player && getProtection((Player) e.getDamager())) {
            e.setCancelled(true);
        }
    }

    public static void setProtection(final Player p, final boolean active) {
        if (active) {
            Flag.flag.put(p, Stats.ON);
        } else {
            Flag.flag.put(p, Stats.OFF);
        }
    }

    public enum Stats {
        OFF("OFF", 0), ON("ON", 1);

        private Stats(final String s, final int n) {
        }
    }
}
