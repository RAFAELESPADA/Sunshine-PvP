package pvp.sunshine.bukkit.utils;

import org.bukkit.entity.Player;
import pvp.sunshine.bukkit.BukkitMain;

public class ClearCosmeticUtil {

    public static void removeCache(Player p) {
        if (p.hasPermission("practice.cosmetic")) {
            if (BukkitMain.getParticle().hasEffect(p)) {
                BukkitMain.getParticle().stopAll(p.getPlayer());
                p.sendMessage("§c§lCOSMETICO §fSuas partículas foram removidas.");
            }
        }
    }
}
