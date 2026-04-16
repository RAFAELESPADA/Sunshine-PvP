package pvp.sunshine.bukkit.manager.inventory;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;

public class ExtraType implements Listener {

    public static void getShops(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, "§8Extra's");

        ItemStack vidrob = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidrobb = vidrob.getItemMeta();
        vidrob.setDurability((short) 4);
        vidrobb.setDisplayName("§e*");
        vidrob.setItemMeta(vidrobb);

        ItemStack fence = new ItemStack(Material.IRON_FENCE);
        ItemMeta fencee = fence.getItemMeta();
        fencee.setDisplayName("§8*");
        fence.setItemMeta(fencee);


        ItemStack lojakit = new ItemStack(Material.DIAMOND);
        ItemMeta lojameta = lojakit.getItemMeta();
        lojameta.setDisplayName("§dLoja de Kit's");
        ArrayList<String> desckit = new ArrayList<>();
        desckit.add("§eClique para visualizar!");
        lojameta.setLore(desckit);
        lojakit.setItemMeta(lojameta);
        inv.setItem(2, lojakit);
        inv.setItem(0, fence);
        inv.setItem(1, vidrob);
        inv.setItem(8, fence);
        inv.setItem(7, vidrob);

        ItemStack tagEspecial = new ItemStack(Material.NAME_TAG);
        ItemMeta tagMeta = tagEspecial.getItemMeta();
        tagMeta.setDisplayName("§dClan's");
        ArrayList<String> tagDesc = new ArrayList<>();
        tagDesc.add("§eClique para visualizar!");
        tagMeta.setLore(tagDesc);
        tagEspecial.setItemMeta(tagMeta);
        inv.setItem(3, tagEspecial);

        ItemStack lojavip = new ItemStack(Material.ENDER_CHEST);
        ItemMeta loja = lojavip.getItemMeta();
        loja.setDisplayName("§dCaixas");
        ArrayList<String> lojadesc = new ArrayList<>();
        lojadesc.add("§eClique para visualizar!");
        loja.setLore(lojadesc);
        lojavip.setItemMeta(loja);
        inv.setItem(4, lojavip);

        ItemStack midia = new ItemStack(Material.ITEM_FRAME);
        ItemMeta midiameta = midia.getItemMeta();
        midiameta.setDisplayName("§dMídia");
        ArrayList<String> midiaDesc = new ArrayList<>();
        midiaDesc.add("§eClique para visualizar!");
        midiameta.setLore(midiaDesc);
        midia.setItemMeta(midiameta);
        inv.setItem(5, midia);

        ItemStack clickTest = new ItemStack(Material.IRON_SWORD);
        ItemMeta clickmeta = clickTest.getItemMeta();
        clickmeta.setDisplayName("§dRanking's Globais");
        ArrayList<String> descClick = new ArrayList<>();
        descClick.add("§eClique para visualizar!");
        clickmeta.setLore(descClick);
        clickTest.setItemMeta(clickmeta);
        inv.setItem(6, clickTest);



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

        if (!clickedInventory.getTitle().equalsIgnoreCase("§8Extra's")) {
            return;
        }
        switch (clickedItem.getType()) {
            case DIAMOND:
                if (e.getWhoClicked().hasPermission("kit.*")) {
                    player.closeInventory();
                    e.getWhoClicked().sendMessage("§c§lSHOP §fVocê já possui todos os kits da loja!");
                    return;
                }
                ShopAbilityType.getShopKit(player);
                break;
            case NAME_TAG:
                player.performCommand("clan");
                player.closeInventory();
                break;
            case ITEM_FRAME:
                MidiaType.getMidia(player);
                break;
            case IRON_SWORD:
                player.performCommand("ranks");
                player.closeInventory();
                break;
            case ENDER_CHEST:
                BoxShopType.getBoxShop(player);
                break;
        }
        e.setCancelled(true);
    }
}
