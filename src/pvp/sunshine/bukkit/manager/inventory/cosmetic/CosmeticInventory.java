package pvp.sunshine.bukkit.manager.inventory.cosmetic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.Collections;

public class CosmeticInventory implements Listener {

    public static void guiCosmetic(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§8Cosmético's");

        ItemStack particle = new ItemStack(Material.BLAZE_POWDER);
        ItemMeta particlemeta = particle.getItemMeta();
        particlemeta.setDisplayName("§a§lPARTICULAS");
        particlemeta.setLore(Collections.singletonList("§eClique para ver!"));
        particle.setItemMeta(particlemeta);
        inv.setItem(10, particle);

        ItemStack medalhas = new ItemStack(Material.SKULL_ITEM);
        ItemMeta medalhameta = medalhas.getItemMeta();
        medalhameta.setDisplayName("§a§lCABEÇAS");
        medalhameta.setLore(Collections.singletonList("§eClique para ver!"));
        medalhas.setItemMeta(medalhameta);
        inv.setItem(14, medalhas);

        ItemStack pets = new ItemStack(Material.GOLD_INGOT);
        ItemMeta petsmeta = pets.getItemMeta();
        petsmeta.setDisplayName("§a§lTAG ESPECIAL");
        petsmeta.setLore(Collections.singletonList("§eClique para ver!"));
        pets.setItemMeta(petsmeta);
        inv.setItem(12, pets);

        ItemStack medalhas2 = new ItemStack(Material.BONE);
        ItemMeta medalha3 = medalhas2.getItemMeta();
        medalha3.setDisplayName("§a§lPETS");
        medalha3.setLore(Collections.singletonList("§cEm breve."));
        medalhas2.setItemMeta(medalha3);
        inv.setItem(16, medalhas2);
        p.openInventory(inv);

    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCLickInventry(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Cosmético's") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                e.getWhoClicked().closeInventory();
                return;
            }
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.BLAZE_POWDER) {
            ParticleInventory.guiParticle((Player) e.getWhoClicked());
            e.setCancelled(true);
            return;
        }
        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.GOLD_INGOT) {
            TagMenuType.getTag((Player)e.getWhoClicked());
            e.setCancelled(true);
            return;
        }

        if (e.getCurrentItem() != null && e.getCurrentItem().getType() == Material.SKULL_ITEM) {
            HeadInventory.guiHead((Player)e.getWhoClicked());
            e.setCancelled(true);
            return;
        }
    }
}
