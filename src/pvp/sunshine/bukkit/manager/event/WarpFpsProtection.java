package pvp.sunshine.bukkit.manager.event;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.utils.PvPUtil;

import java.util.HashMap;
import java.util.Map;

public class WarpFpsProtection implements Listener {

    private static final Map<String, Integer> fallCountMap = new HashMap<>();

    @EventHandler
    public void onPlayerDamage(EntityDamageEvent event) {
        if (event.getEntity() instanceof Player) {
            Player p = (Player) event.getEntity();

            if (RegisterAbility.getAbility(p).equalsIgnoreCase("Fps") && event.getCause() == EntityDamageEvent.DamageCause.FALL) {
                int fallCount = fallCountMap.getOrDefault(p.getName(), 0) + 1;
                fallCountMap.put(p.getName(), fallCount);

                if (fallCount == 1) {
                    Flag.setProtection(p, false);
                    PvPUtil.InventoryAdapter(p);
                    PvPUtil.getPvPUtils(p);
                    PvPUtil.getItemChant(p, Material.DIAMOND_SWORD, 1, "§aEspada", 0, Enchantment.DAMAGE_ALL, 1, true);
                    p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
                    p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
                    p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
                    p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
                    p.sendMessage("§c§lSPAWN §fSua proteção de spawn foi removida.");
                }
            }
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        fallCountMap.remove(e.getPlayer().getName());
    }

    public static void reiniciarContadorFps(Player p) {
        fallCountMap.remove(p.getName());
    }
}
