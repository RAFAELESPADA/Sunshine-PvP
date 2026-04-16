package pvp.sunshine.bukkit.manager.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class MidiaType implements Listener {

    public static void getMidia(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§8Tag's & Requisitos");



        ItemStack youtuber = new ItemStack(Material.REDSTONE);
        ItemMeta ytmeta = youtuber.getItemMeta();
        ytmeta.setDisplayName("§cCreator");
        ArrayList<String> ytDesc = new ArrayList<>();
        ytDesc.add("§8Requisitos:");
        ytDesc.add(" §7Gravar §e1§7 vídeo no servidor.");
        ytDesc.add(" §7Ter §e100 inscritos§7 em seu canal.");
        ytDesc.add(" §7Possuir um feedback de §e60 a 120§7 views ou mais.");
        ytDesc.add("");
        ytmeta.setLore(ytDesc);
        youtuber.setItemMeta(ytmeta);
        inv.setItem(13, youtuber);



        p.openInventory(inv);
    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClickInventory(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();
        Inventory clickedInventory = e.getInventory();
        ItemStack clickedItem = e.getCurrentItem();

        if (clickedInventory == null || clickedItem == null) {
            return;
        }

        if (!clickedInventory.getTitle().equalsIgnoreCase("§8Tag's & Requisitos")) {
            return;
        }

        e.setCancelled(true);
    }
}
