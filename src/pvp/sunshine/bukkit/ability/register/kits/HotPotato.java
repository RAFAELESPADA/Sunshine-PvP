package pvp.sunshine.bukkit.ability.register.kits;

import java.util.*;
import org.bukkit.event.player.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.*;
import org.bukkit.scheduler.*;

import org.bukkit.inventory.meta.*;
import org.bukkit.event.*;
import org.bukkit.event.inventory.*;
import org.bukkit.*;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class HotPotato implements Listener {

    public static ArrayList<String> emhotpotato = new ArrayList<String>();

    @SuppressWarnings({ "deprecation" })
    @EventHandler
    public void onPlayerInteractEntityEventHotPotato(PlayerInteractEntityEvent e) {
        if (e.getRightClicked() instanceof Player) {
            if (e.getPlayer().getItemInHand().getType().equals((Object) Material.BAKED_POTATO)
                    && RegisterAbility.getAbility(e.getPlayer()).equalsIgnoreCase("Hotpotato")) {
                if (Cooldown.add(e.getPlayer())) {
                    e.getPlayer().sendMessage("§c§lCOOLDOWN §fAguarde §c" + Cooldown.cooldown(e.getPlayer())
                            + "s §fpara usar novamente.");
                    return;
                }
                Cooldown.add(e.getPlayer(), 25);
                HotPotato.emhotpotato.add(e.getRightClicked().getName());
                e.getPlayer().sendMessage("§a§lKIT §fHotPotato Implantada!");
                e.getRightClicked()
                        .sendMessage("§e§lKIT §fVocê está com uma hotpotato em sua cabeça, se não retira-la ela explodirá em 5 segundos!");
                e.getRightClicked().sendMessage("§e§lKIT §fClique com o botão direito para remove-la");
                ItemStack tnt = new ItemStack(Material.TNT);
                ItemMeta tntmeta = tnt.getItemMeta();
                tntmeta.setDisplayName("§cTNT");
                tnt.setItemMeta(tntmeta);
                ((Player) e.getRightClicked()).getInventory().setHelmet(tnt);
                new BukkitRunnable() {
                    public void run() {
                        if (HotPotato.emhotpotato.contains(e.getRightClicked().getName())) {
                            e.getRightClicked().sendMessage("§e§lKIT §fExplodindo em 4...");
                        }
                    }
                }.runTaskLater(BukkitMain.getInstance(), 0L);
                new BukkitRunnable() {
                    public void run() {
                        if (HotPotato.emhotpotato.contains(e.getRightClicked().getName())) {
                            e.getRightClicked().sendMessage("§e§lKIT §fExplodindo em 3...");
                        }
                    }
                }.runTaskLater(BukkitMain.getInstance(), 20L);
                new BukkitRunnable() {
                    public void run() {
                        if (HotPotato.emhotpotato.contains(e.getRightClicked().getName())) {
                            e.getRightClicked().sendMessage("§e§lKIT §fExplodindo em 2...");
                        }
                    }
                }.runTaskLater(BukkitMain.getInstance(), 40L);
                new BukkitRunnable() {
                    public void run() {
                        if (HotPotato.emhotpotato.contains(e.getRightClicked().getName())) {
                            e.getRightClicked().sendMessage("§e§lKIT §fExplodindo em 1...");
                        }
                    }
                }.runTaskLater(BukkitMain.getInstance(), 60L);
                new BukkitRunnable() {
                    public void run() {
                        if (HotPotato.emhotpotato.contains(e.getRightClicked().getName())) {
                            ((Player) e.getRightClicked()).setHealth(1.0);
                            e.getRightClicked().getWorld().createExplosion(e.getRightClicked().getLocation(), 3.0f,
                                    true);
                            HotPotato.emhotpotato.remove(e.getRightClicked().getName());
                        }
                    }
                }.runTaskLater(BukkitMain.getInstance(), 80L);
                new BukkitRunnable() {
                    public void run() {
                        e.getPlayer().sendMessage("§a§lCOOLDOWN §fAgora você pode usar seu kit novamente.");
                    }
                }.runTaskLater(BukkitMain.getInstance(), 500L);
            }
        }

    }

    @EventHandler
    public void onRemoverTNT(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (RegisterAbility.getAbility(p) != "Nenhum" && e.getSlot() == 39
                && e.getCurrentItem().getType().equals(Material.TNT)
                && HotPotato.emhotpotato.contains(p.getName())) {
            HotPotato.emhotpotato.remove(p.getName());
            e.setCancelled(true);
            p.getInventory().setHelmet((ItemStack) null);
            p.playSound(p.getLocation(), Sound.CREEPER_HISS, 2.0f, 2.0f);
            p.sendMessage("§a§lKIT §fHotPotato Removida!");
            p.closeInventory();
        }
    }
}
