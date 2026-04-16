package pvp.sunshine.bukkit.manager.inventory.cosmetic;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.particle.ParticleEffect;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class ParticleInventory implements Listener {

    public static List<String> playerCache = new ArrayList<>();

    public static void guiParticle(Player p) {
        Inventory inv = Bukkit.createInventory(p,27, "§8Partículas");

        ItemStack heart = new ItemStack(Material.INK_SACK, 1, (short) 1);
        ItemMeta heartmeta = heart.getItemMeta();
        heartmeta.setDisplayName("§cCoração");
        heart.setItemMeta(heartmeta);
        inv.setItem(10, heart);

        ItemStack fogo = new ItemStack(Material.MAGMA_CREAM);
        ItemMeta fogoMeta = fogo.getItemMeta();
        fogoMeta.setDisplayName("§6Fogo");
        fogo.setItemMeta(fogoMeta);
        inv.setItem(11, fogo);

        ItemStack slime = new ItemStack(Material.WATER_BUCKET);
        ItemMeta slimemeta = slime.getItemMeta();
        slimemeta.setDisplayName("§bÁgua");
        slime.setItemMeta(slimemeta);
        inv.setItem(12, slime);

        ItemStack notas = new ItemStack(Material.JUKEBOX);
        ItemMeta notameta = notas.getItemMeta();
        notameta.setDisplayName("§bNotas Musicais");
        notas.setItemMeta(notameta);
        inv.setItem(13, notas);

        ItemStack remove = new ItemStack(Material.REDSTONE);
        ItemMeta removemeta = remove.getItemMeta();
        removemeta.setDisplayName("§cRemover Particulas");
        remove.setItemMeta(removemeta);
        inv.setItem(16, remove);

        ItemStack smoke = new ItemStack(Material.INK_SACK, 1, (short)9);
        ItemMeta smokemeta = smoke.getItemMeta();
        smokemeta.setDisplayName("§dArco-íris");
        smoke.setItemMeta(smokemeta);
        inv.setItem(14, smoke);

        p.openInventory(inv);

    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void playerInteractInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Partículas") && e.getCurrentItem() != null) {
            Material material = e.getCurrentItem().getType();
            short data = e.getCurrentItem().getDurability();
            e.setCancelled(true);

            if (material.equals(Material.REDSTONE)) {
                if (!BukkitMain.getParticle().hasEffect(p)) {
                    p.sendMessage("§c§lCOSMETICO §fVocê não tem nenhuma partícula para ser removida.");
                } else {
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                    p.closeInventory();
                    BukkitMain.getParticle().stopAll(p);
                    p.sendMessage("§a§lCOSMETICO §fTodas as suas partículas foram removidas.");
                }
                p.closeInventory();
            } else if (material.equals(Material.WATER_BUCKET)) {
                BukkitMain.getParticle().stopAll(p);
                BukkitMain.getParticle().spiraleEffect(p, ParticleEffect.DRIP_WATER);
                p.sendMessage("§a§lCOSMETICO §fVocê inseriu a partícula de §bÁgua");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                p.closeInventory();
            } else if (material.equals(Material.INK_SACK) && data == 9) {
                BukkitMain.getParticle().stopAll(p);
                BukkitMain.getParticle().spiraleEffect(p, ParticleEffect.REDSTONE);
                p.sendMessage("§a§lCOSMETICO §fVocê inseriu a partícula de §dArco-íris");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                p.closeInventory();
            } else if (material.equals(Material.INK_SACK)) {
                BukkitMain.getParticle().stopAll(p);
                BukkitMain.getParticle().spiraleEffect(p, ParticleEffect.HEART);
                p.sendMessage("§a§lCOSMETICO §fVocê inseriu a partícula de §cCoração");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                p.closeInventory();
            } else if (material.equals(Material.MAGMA_CREAM)) {
                BukkitMain.getParticle().stopAll(p);
                BukkitMain.getParticle().spiraleEffect(p, ParticleEffect.DRIP_LAVA);
                p.sendMessage("§a§lCOSMETICO §fVocê inseriu a partícula de §6Fogo");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                p.closeInventory();
            } else if (material.equals(Material.JUKEBOX)) {
                BukkitMain.getParticle().stopAll(p);
                BukkitMain.getParticle().spiraleEffect(p, ParticleEffect.NOTE);
                p.sendMessage("§a§lCOSMETICO §fVocê inseriu a partícula de §bNotas Musicais");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                p.closeInventory();
            }
        }
    }

    @EventHandler
    public void PlayerDisconnectEvent(PlayerQuitEvent e) {
        BukkitMain.getParticle().stopAll(e.getPlayer());
    }

    @EventHandler
    public void PlayerKick(PlayerKickEvent e) {
        BukkitMain.getParticle().stopAll(e.getPlayer());
    }
}
