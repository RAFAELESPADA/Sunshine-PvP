package pvp.sunshine.bukkit.manager.inventory;

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
import org.bukkit.inventory.meta.SkullMeta;
import pvp.sunshine.bukkit.commands.team.EventoCMD;
import pvp.sunshine.bukkit.manager.inventory.action.OpenInventory;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLClan;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.utils.GroupUtil;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ProfileType implements Listener {

    public static String getClan(Player player) {
        String clan = "";
        if (!SQLClan.clan.get(player.getUniqueId()).equalsIgnoreCase("Nenhum")) {
            clan = "§b" + SQLClan.clan.get(player.getUniqueId());
        }
        else {
            clan = "§cNenhum";
        }
        return clan;
    }

    public static void getProfile(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, "§8Meu perfil");

        ItemStack perfil = new ItemStack(Material.SKULL_ITEM);
        perfil.setDurability((short)3);
        SkullMeta skmeta = (SkullMeta)perfil.getItemMeta();
        skmeta.setDisplayName("§aSuas informações");
        ArrayList<String> desconta = new ArrayList<String>();
        desconta.add("§7Grupo: " + GroupUtil.getGroup(p));
        desconta.add("§7Ranking: " + SQLRank.getRank(p) + " " + SQLRank.getRankComplete(SQLRank.getXp(p)));
        skmeta.setOwner(p.getName());
        skmeta.setLore((List<String>) desconta);
        perfil.setItemMeta((ItemMeta) skmeta);
        inv.setItem(13, perfil);

        ItemStack estistica = new ItemStack(Material.PAPER);
        ItemMeta estameta = estistica.getItemMeta();
        estameta.setDisplayName("§aEstatistícas");
        estistica.setItemMeta(estameta);
        inv.setItem(30, estistica);

        ItemStack skins = new ItemStack(Material.ENDER_CHEST);
        ItemMeta skinmeta = skins.getItemMeta();
        skinmeta.setDisplayName("§aSuas Caixas");
        skins.setItemMeta(skinmeta);
        inv.setItem(31, skins);

        ItemStack preference = new ItemStack(Material.NAME_TAG);
        ItemMeta prefermeta = preference.getItemMeta();
        prefermeta.setDisplayName("§aSeu Clan");
        ArrayList<String> descClan = new ArrayList<>();
        descClan.add("§7Clan: " + getClan(p));
        prefermeta.setLore(descClan);
        preference.setItemMeta(prefermeta);
        inv.setItem(32, preference);

        ItemStack passe = new ItemStack(Material.DIAMOND_SWORD);
        ItemMeta passemeta = passe.getItemMeta();
        passemeta.setDisplayName("§aPasse de Batalha");
        passemeta.setLore(Collections.singletonList("§cEm breve disponível para todos."));
        passe.setItemMeta(passemeta);
        inv.setItem(29, passe);

        ItemStack config = new ItemStack(Material.REDSTONE_COMPARATOR);
        ItemMeta configmeta = config.getItemMeta();
        configmeta.setDisplayName("§aConfigurações");
        config.setItemMeta(configmeta);
        inv.setItem(33, config);

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

        if (!clickedInventory.getTitle().equalsIgnoreCase("§8Meu perfil")) {
            return;
        }
        switch (clickedItem.getType()) {
            case PAPER:
            StatisticsType.getStatistics(player);
            break;
            case REDSTONE_COMPARATOR:
                ConfigType.OpenConfig(player);
                break;
            case ENDER_CHEST:
                if (BoxSystemType.getCaixa(player) >= 1) {
                    if (OpenInventory.caixa.contains(player.getName())) {
                        player.sendMessage("§e§lCAIXAS §fAguarde alguns segundos para abrir uma caixa novamente.");
                        player.closeInventory();
                        return;
                    }
                    BoxSystemType.GuiCaixas(player);
                    BoxSystemType.removerCaixa(player, 1);
                    OpenInventory.caixa.add(player.getName());
                } else {
                    player.sendMessage("§c§lCAIXAS §fVocê não possui nenhuma caixa para abrir.");
                }
            break;
        }
        e.setCancelled(true);
    }
}
