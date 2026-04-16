package pvp.sunshine.bukkit.manager.inventory.cosmetic;

import java.util.Collections;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class TagMenuType implements Listener {

    public static void getTag(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§8Tag's Especiais");

        ItemStack sunshine = new ItemStack(Material.DOUBLE_PLANT);
        ItemMeta sunmeta = sunshine.getItemMeta();
        sunmeta.setDisplayName("§8Tag: §6Sunshine");
        sunmeta.setLore(Collections.singletonList("§eClique para selecionar!"));
        sunshine.setItemMeta(sunmeta);
        inv.setItem(10, sunshine);

        ItemStack aquatic = new ItemStack(Material.INK_SACK,1, (short)6);
        ItemMeta aquameta = aquatic.getItemMeta();
        aquameta.setDisplayName("§8Tag: §3Aquatic");
        aquameta.setLore(Collections.singletonList("§eClique para selecionar!"));
        aquatic.setItemMeta(aquameta);
        inv.setItem(11, aquatic);

        ItemStack barbie = new ItemStack(Material.INK_SACK, 1, (short)9);
        ItemMeta barbiemeta = barbie.getItemMeta();
        barbiemeta.setDisplayName("§8Tag: §dBarbie");
        barbiemeta.setLore(Collections.singletonList("§eClique para selecionar!"));
        barbie.setItemMeta(barbiemeta);
        inv.setItem(12, barbie);

        ItemStack starlight = new ItemStack(Material.GLOWSTONE_DUST);
        ItemMeta star = starlight.getItemMeta();
        star.setDisplayName("§8Tag: §eStarLight");
        star.setLore(Collections.singletonList("§eClique para selecionar!"));
        starlight.setItemMeta(star);
        inv.setItem(13, starlight);

        ItemStack gamer = new ItemStack(Material.INK_SACK, 1, (short)10);
        ItemMeta gamermeta = gamer.getItemMeta();
        gamermeta.setDisplayName("§8Tag: §aGamer");
        gamermeta.setLore(Collections.singletonList("§eClique para selecionar!"));
        gamer.setItemMeta(gamermeta);
        inv.setItem(14, gamer);

        ItemStack killer = new ItemStack(Material.INK_SACK, 1, (short)1);
        ItemMeta killermeta = killer.getItemMeta();
        killermeta.setDisplayName("§8Tag: §cKiller");
        killermeta.setLore(Collections.singletonList("§eClique para selecionar!"));
        killer.setItemMeta(killermeta);
        inv.setItem(15, killer);

        ItemStack nightwing = new ItemStack(Material.INK_SACK, 1, (short)14);
        ItemMeta night = nightwing.getItemMeta();
        night.setDisplayName("§8Tag: §6Thunder");
        night.setLore(Collections.singletonList("§eClique para selecionar!"));
        nightwing.setItemMeta(night);
        inv.setItem(16, nightwing);

        p.openInventory(inv);


    }

    @SuppressWarnings("deprecation")
    @EventHandler
    public void playerInteractInventory(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Tag's Especiais") && e.getCurrentItem() != null) {
            Material material = e.getCurrentItem().getType();
            short data = e.getCurrentItem().getDurability();
            e.setCancelled(true);

            if (material.equals(Material.DOUBLE_PLANT)) {
                if (isUsingTag(p, "§6§lSUNSHINE §6")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §6Sunshine§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§6§lSUNSHINE §6" + p.getName());
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
            if (material.equals(Material.INK_SACK) && data == 6) {
                if (isUsingTag(p, "§3§lAQUATIC §3")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §3Aquatic§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§3§lAQUATIC §3" + p.getName());
                   p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
            if (material.equals(Material.INK_SACK) && data == 9) {
                if (isUsingTag(p, "§d§lBARBIE §d")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §dBarbie§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§d§lBARBIE §d" + p.getName());
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
            if (material.equals(Material.GLOWSTONE_DUST)) {
                if (isUsingTag(p, "§e§lSTARLIGHT §e")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §eStarLight§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§e§lSTARLIGHT §e" + p.getName());
                   p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
            if (material.equals(Material.INK_SACK) && data == 10) {
                if (isUsingTag(p, "§a§lGAMER §a")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §aGamer§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§a§lGAMER §a" + p.getName());
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
            if (material.equals(Material.INK_SACK) && data == 1) {
                if (isUsingTag(p, "§c§lKILLER §c")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §cKiller§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§c§lKILLER §c" + p.getName());
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
            if (material.equals(Material.INK_SACK) && data == 14) {
                if (isUsingTag(p, "§6§lTHUNDER §6")) {
                    p.sendMessage("§c§lCOSMETICO §fVocê já está usando essa tag!");
                    p.closeInventory();
                } else {
                    p.sendMessage("§a§lCOSMETICO §fVocê selecionou a tag especial §6Thunder§f com sucesso! Caso queira removê-la, basta executar o comando §a/tag§f e escrever o nome da sua tag.");
                    p.closeInventory();
                    p.setDisplayName("§6§lTHUNDER §6" + p.getName());
                    p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                }
            }
        }
    }

    private boolean isUsingTag(Player player, String tag) {
        String playerDisplayName = player.getDisplayName();
        return playerDisplayName.contains(tag);
    }
}
