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
import pvp.sunshine.bukkit.SunshineFormat;
import pvp.sunshine.bukkit.api.KillStreakAPI;
import pvp.sunshine.bukkit.api.WinStreakAPI;
import pvp.sunshine.bukkit.manager.mysql.connections.SQL1v1;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopDeaths;

import java.util.ArrayList;

public class StatisticsType implements Listener {

    public static void getStatistics(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§8Minhas Estatísticas");

        ItemStack pvp = new ItemStack(Material.MUSHROOM_SOUP);
        ItemMeta pvpmeta = pvp.getItemMeta();
        pvpmeta.setDisplayName("§aPvP");
        ArrayList<String> pvpdesc = new ArrayList<>();
        pvpdesc.add(" §7Seu grupo: §e" + TopDeaths.giveMeADamnUser(p.getUniqueId()).getPrimaryGroup().toUpperCase());
        pvpdesc.add(" §7Kills: §e" + SunshineFormat.format(SQLPvP.getKills(p)));
        pvpdesc.add(" §7Deaths: §e" + SunshineFormat.format(SQLPvP.getDeaths(p)));
        pvpdesc.add(" §7Killstreak atual: §e" + SunshineFormat.format(KillStreakAPI.getStreak(p)));
        pvpdesc.add(" ");
        pvpdesc.add(" §7Rank atual: " + SQLRank.getRankComplete(SQLRank.getXp(p)));
        pvpdesc.add(" ");
        pvpmeta.setLore(pvpdesc);
        pvp.setItemMeta(pvpmeta);
        inv.setItem(11, pvp);

        ItemStack x1 = new ItemStack(Material.BLAZE_ROD);
        ItemMeta x1meta = x1.getItemMeta();
        x1meta.setDisplayName("§a1v1");
        ArrayList<String> x1desc = new ArrayList<>();
        x1desc.add("§7 Vitórias: §e" + SunshineFormat.format(SQL1v1.getWins(p)));
        x1desc.add("§7 Derrotas: §e" + SunshineFormat.format(SQL1v1.getLoses(p)));
        x1desc.add("§7 Winstreak atual: §e" + SunshineFormat.format(WinStreakAPI.getStreak(p)));
        x1desc.add("");
        x1desc.add(" §7Rank atual: " + SQLRank.getRankComplete(SQLRank.getXp(p)));
        x1desc.add("");
        x1meta.setLore(x1desc);
        x1.setItemMeta(x1meta);
        inv.setItem(12, x1);

        ItemStack cash = new ItemStack(Material.EXP_BOTTLE);
        ItemMeta cashmeta = cash.getItemMeta();
        cashmeta.setDisplayName("§aDinheiro");
        ArrayList<String> descash = new ArrayList<String>();
        descash.add("§7 Coins: §e" + SunshineFormat.format(SQLPvP.getCoins(p)));
        descash.add("§7 XP: §e" + SunshineFormat.format(SQLRank.getXp(p)));
        descash.add("");
        descash.add("§7 Rank atual: " + SQLRank.getRankComplete(SQLRank.getXp(p)));
        descash.add("§7 XP necessário para o próximo rank: §e" + SunshineFormat.format(SQLRank.getXpRequiredForNextRank(SQLRank.getXp(p))));
        descash.add("");
        cashmeta.setLore(descash);
        cash.setItemMeta(cashmeta);
        inv.setItem(16, cash);

        ItemStack practice = new ItemStack(Material.POTION);
        ItemMeta pracmet = practice.getItemMeta();
        pracmet.setDisplayName("§aPractice");
        ArrayList<String> descprac = new ArrayList<String>();
        descprac.add("§cEm breve disponível para todos.");
        pracmet.setLore(descprac);
        practice.setItemMeta(pracmet);
        inv.setItem(13, practice);
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

        if (!clickedInventory.getTitle().equalsIgnoreCase("§8Minhas Estatísticas")) {
            return;
        }

        e.setCancelled(true);
    }
}
