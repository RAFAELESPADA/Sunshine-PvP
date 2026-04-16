package pvp.sunshine.bukkit.ability.register.kits;

import org.bukkit.Sound;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import pvp.sunshine.bukkit.ability.RegisterAbility;

public class Barbarian implements Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player victim = event.getEntity();
        Player killer = victim.getKiller();
        if (killer != null && RegisterAbility.getAbility(killer).equalsIgnoreCase("Barbarian")) {
            ItemStack sword = killer.getItemInHand();

            if (sword != null && sword.getType().toString().contains("SWORD")) {
                int currentEnchantmentLevel = sword.getEnchantmentLevel(Enchantment.DAMAGE_ALL);
                int newEnchantmentLevel = currentEnchantmentLevel + 1;

                if (newEnchantmentLevel > 5) {
                    newEnchantmentLevel = 5;
                    killer.sendMessage("§c§lKIT §fVocê chegou ao seu encantamento máximo (nível 5)!");
                } else {
                    killer.sendMessage("§a§lKIT §fSua espada foi aprimorada para nível §a" + newEnchantmentLevel + "§f!");
                }

                sword.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, newEnchantmentLevel);
                killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
            }
        }
    }
}
