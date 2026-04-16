package pvp.sunshine.bukkit.ability.register.kits;

import java.util.*;

import org.bukkit.entity.*;
import org.bukkit.*;
import org.bukkit.plugin.*;
import org.bukkit.event.*;

import java.text.*;

import org.bukkit.event.entity.*;
import org.bukkit.event.player.*;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Ninja implements Listener {
    public HashMap<Player, Player> ajinhash;
    public HashMap<Player, Long> ajincooldown;

    public Ninja() {
        this.ajinhash = new HashMap<Player, Player>();
        this.ajincooldown = new HashMap<Player, Long>();
    }

    @EventHandler
    public void a(final EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player && e.getEntity() instanceof Player) {
            final Player p = (Player) e.getDamager();
            final Player t = (Player) e.getEntity();
            if (RegisterAbility.getAbility(p).equalsIgnoreCase("Ninja")) {
                this.ajinhash.put(p, t);
                Bukkit.getServer().getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), (Runnable) new Runnable() {
                    @Override
                    public void run() {
                    }
                }, 160L);
            }
        }
    }

    @EventHandler
    public void aPlayerToggle(final PlayerToggleSneakEvent e) {
        final Player p = e.getPlayer();
        if (RegisterAbility.getAbility(p).equalsIgnoreCase("Ninja") && this.ajinhash.containsKey(p)) {
            if (Cooldown.add(p)) {
                p.sendMessage("§c§lCOOLDOWN §fAguarde §c" + Cooldown.cooldown(e.getPlayer()) + "s §fpara usar novamente.");
                return;
            }
        }
        if (e.isSneaking() && RegisterAbility.getAbility(p).equalsIgnoreCase("Ninja") && this.ajinhash.containsKey(p)) {
            final Player t = this.ajinhash.get(p);
            if (t != null && !t.isDead()) {
                String s = null;
                if (this.ajincooldown.get(p) != null) {
                    final long l = this.ajincooldown.get(p) - System.currentTimeMillis();
                    final DecimalFormat localDecimalFormat = new DecimalFormat("##");
                    final int i = (int) l / 1000;
                    s = localDecimalFormat.format(i);
                }
                if (p.getLocation().distance(t.getLocation()) < 50.0) {
                    p.teleport(t.getLocation());
                    p.sendMessage("§a§lKIT §fVocê foi teleportado.");
                    Cooldown.add(p, 8);
                    Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(), (Runnable) new Runnable() {
                        @Override
                        public void run() {
                            p.sendMessage("§a§lCOOLDOWN §fAgora você pode usar seu kit novamente.");
                        }
                    }, 160L);
                } else {
                    p.sendMessage("§c§lKIT §fO ultimo jogador hitado esta muito longe!");
                }
            }
        }
    }

    @EventHandler
    public void aomorrer(final PlayerDeathEvent e) {
        final Player p = e.getEntity();
        if (this.ajinhash.containsKey(p)) {
            final Player t = this.ajinhash.get(p);
            this.ajinhash.remove(t);
            this.ajinhash.remove(p);
        }
    }

    @EventHandler
    public void aosair(final PlayerQuitEvent e) {
        final Player p = e.getPlayer();
        if (this.ajinhash.containsKey(p)) {
            final Player t = this.ajinhash.get(p);
            this.ajinhash.remove(t);
            this.ajinhash.remove(p);
        }
        ajincooldown.remove(e.getPlayer());
    }
}
