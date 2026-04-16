package pvp.sunshine.bukkit.manager.inventory.cosmetic;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class HeadInventory implements Listener {

    public static void guiHead(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.CHEST, "§8Cabeças Personalizadas");

        ItemStack melancia = new ItemStack(Material.MELON_BLOCK);
        ItemMeta melon = melancia.getItemMeta();
        melon.setDisplayName("§2Melancia");
        melancia.setItemMeta(melon);
        inv.setItem(10, melancia);

        ItemStack tnt = new ItemStack(Material.TNT);
        ItemMeta tntm = tnt.getItemMeta();
        tntm.setDisplayName("§cTNT");
        tnt.setItemMeta(tntm);
        inv.setItem(11, tnt);

        ItemStack abobora = new ItemStack(Material.PUMPKIN);
        ItemMeta abobora2 = abobora.getItemMeta();
        abobora2.setDisplayName("§6Abóbora");
        abobora.setItemMeta(abobora2);
        inv.setItem(12,abobora);

        ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK);
        ItemMeta dia = diamond.getItemMeta();
        dia.setDisplayName("§bBloco de Diamante");
        diamond.setItemMeta(dia);
        inv.setItem(13,diamond);

        ItemStack pedra = new ItemStack(Material.STONE);
        ItemMeta ped2 = pedra.getItemMeta();
        ped2.setDisplayName("§7Cabeça de Pedra");
        pedra.setItemMeta(ped2);
        inv.setItem(14,pedra);

        ItemStack redstone = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta remove = redstone.getItemMeta();
        remove.setDisplayName("§cBloco de Redstone");
        redstone.setItemMeta(remove);
        inv.setItem(15,redstone);

        ItemStack redstone2 = new ItemStack(Material.BOOKSHELF);
        ItemMeta remove2 = redstone2.getItemMeta();
        remove2.setDisplayName("§6Estante de Livros");
        redstone2.setItemMeta(remove2);
        inv.setItem(15,redstone2);

        ItemStack redstone3 = new ItemStack(Material.LAPIS_BLOCK);
        ItemMeta remove3 = redstone3.getItemMeta();
        remove3.setDisplayName("§1Lapiz Lazuli");
        redstone3.setItemMeta(remove3);
        inv.setItem(16,redstone3);

        p.openInventory(inv);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClickInventory(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Cabeças Personalizadas") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            Player p = (Player) e.getWhoClicked();
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                e.getWhoClicked().closeInventory();
                return;
            }
            if (e.getCurrentItem().getType() == Material.TNT) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack tnt = new ItemStack(Material.TNT);
                e.getWhoClicked().getInventory().setHelmet(tnt);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §cTNT§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.MELON_BLOCK) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack tnt = new ItemStack(Material.MELON_BLOCK);
                e.getWhoClicked().getInventory().setHelmet(tnt);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §2Melancia§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.PUMPKIN) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack abobora = new ItemStack(Material.PUMPKIN);
                e.getWhoClicked().getInventory().setHelmet(abobora);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §6Abóbora§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.DIAMOND_BLOCK) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack diamond = new ItemStack(Material.DIAMOND_BLOCK);
                e.getWhoClicked().getInventory().setHelmet(diamond);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §bDiamante§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.STONE) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack stone = new ItemStack(Material.STONE);
                e.getWhoClicked().getInventory().setHelmet(stone);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §7Pedra§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.REDSTONE_BLOCK) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack stone2 = new ItemStack(Material.REDSTONE_BLOCK);
                e.getWhoClicked().getInventory().setHelmet(stone2);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §cRedstone§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.BOOKSHELF) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack stone3 = new ItemStack(Material.BOOKSHELF);
                e.getWhoClicked().getInventory().setHelmet(stone3);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §6Estante de Livros§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
            if (e.getCurrentItem().getType() == Material.LAPIS_BLOCK) {
                e.setCancelled(true);
                e.getWhoClicked().closeInventory();
                ItemStack stone4 = new ItemStack(Material.LAPIS_BLOCK);
                e.getWhoClicked().getInventory().setHelmet(stone4);
                e.getWhoClicked().sendMessage("§a§lCOSMETICO §fVocê selecionou a cabeça de §1Lapiz Lazuli§f com sucesso!§f Caso queira retirar a sua cabeça, basta apenas sair e entrar novamente no servidor ou selecionar outra cabeça.");
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                return;
            }
        }
    }

    @EventHandler
    public void onRemoverTNT(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getSlot() == 39 && (
                e.getCurrentItem().getType().equals(Material.MELON_BLOCK) ||
                        e.getCurrentItem().getType().equals(Material.TNT) ||
                        e.getCurrentItem().getType().equals(Material.PUMPKIN) ||
                        e.getCurrentItem().getType().equals(Material.DIAMOND_BLOCK) ||
                        e.getCurrentItem().getType().equals(Material.STONE) ||
                        e.getCurrentItem().getType().equals(Material.BOOKSHELF) ||
                        e.getCurrentItem().getType().equals(Material.LAPIS_BLOCK))) {
            e.setCancelled(true);
            p.closeInventory();
        }

    }
}
