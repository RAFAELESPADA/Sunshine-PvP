package pvp.sunshine.bukkit.manager.inventory;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pvp.sunshine.bukkit.commands.members.TellCMD;

public class ConfigType implements Listener {

    public static List<String> Scoreboard = new ArrayList<String>();

    @EventHandler
    public static void ClearCache(PlayerQuitEvent e) {
        if (MessageAutomatic.contains(e.getPlayer().getName())) {
            MessageAutomatic.remove(e.getPlayer().getName());
        }
    }

    @EventHandler
    public static void ClearCache(PlayerKickEvent e) {
        if (MessageAutomatic.contains(e.getPlayer().getName())) {
            MessageAutomatic.remove(e.getPlayer().getName());
        }
    }

    public static List<String> MessageAutomatic = new ArrayList<String>();

    @SuppressWarnings("deprecation")
    public static void OpenConfig(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§8Configurações");

        ItemStack vidroa = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta kvidroa = vidroa.getItemMeta();
        kvidroa.setDisplayName("§f*");
        vidroa.setItemMeta(kvidroa);

        if (!TellCMD.telloff.contains(p)) {
            ItemStack Tell = new ItemStack(new ItemStack(351, 1, (short) 10));
            ItemMeta Telll = Tell.getItemMeta();
            Telll.setDisplayName("§7Tell: §aAtivado");
            Tell.setItemMeta(Telll);
            inv.setItem(1, Tell);
        } else {
            ItemStack Tell = new ItemStack(new ItemStack(351, 1, (short) 8));
            ItemMeta Telll = Tell.getItemMeta();
            Telll.setDisplayName("§7Tell: §cDesativado");
            Tell.setItemMeta(Telll);
            inv.setItem(1, Tell);
        }

        if (!MessageAutomatic.contains(p.getName())) {
            ItemStack Auto = new ItemStack(new ItemStack(351, 1, (short) 10));
            ItemMeta Autoo = Auto.getItemMeta();
            Autoo.setDisplayName("§7Mensagens automáticas & Feast: §aAtivado");
            Auto.setItemMeta(Autoo);
            inv.setItem(3, Auto);
        } else {
            ItemStack Auto = new ItemStack(new ItemStack(351, 1, (short) 8));
            ItemMeta Autoo = Auto.getItemMeta();
            Autoo.setDisplayName("§7Mensagens automáticas & Feast: §cDesativado");
            Auto.setItemMeta(Autoo);
            inv.setItem(3, Auto);
        }
        inv.setItem(0, vidroa);
        inv.setItem(4, vidroa);
        inv.setItem(2, vidroa);
        p.openInventory(inv);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerCLickInventry(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Configurações") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                e.getWhoClicked().closeInventory();
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Scoreboard: §aAtivado")) {
                Inventory inv = e.getClickedInventory();

                ItemStack Score = new ItemStack(new ItemStack(351, 1, (short) 8));
                ItemMeta Scoree = Score.getItemMeta();
                Scoree.setDisplayName("§7Scoreboard: §cDesativado");
                Score.setItemMeta(Scoree);
                ((Player) e.getWhoClicked()).chat("/score");

                inv.setItem(2, Score);
                return;
            } else {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Scoreboard: §cDesativado")) {
                    Inventory inv = e.getClickedInventory();
                    ItemStack Score = new ItemStack(new ItemStack(351, 1, (short) 10));
                    ItemMeta Scoree = Score.getItemMeta();
                    Scoree.setDisplayName("§7Scoreboard: §aAtivado");
                    Score.setItemMeta(Scoree);
                    ((Player) e.getWhoClicked()).chat("/score");
                    inv.setItem(2, Score);
                    return;
                }
            }

            if (e.getCurrentItem().getItemMeta().getDisplayName()
                    .equalsIgnoreCase("§7Mensagens automáticas & Feast: §aAtivado")) {
                MessageAutomatic.remove(e.getWhoClicked().getName());
                Inventory inv = e.getClickedInventory();
                ItemStack Auto = new ItemStack(new ItemStack(351, 1, (short) 8));
                ItemMeta Autoo = Auto.getItemMeta();
                Autoo.setDisplayName("§7Mensagens automáticas & Feast: §cDesativada");
                Auto.setItemMeta(Autoo);

                inv.setItem(3, Auto);
                return;
            } else {
                Inventory inv = e.getClickedInventory();
                ItemStack Auto = new ItemStack(new ItemStack(351, 1, (short) 10));
                ItemMeta Autoo = Auto.getItemMeta();
                Autoo.setDisplayName("§7Mensagens automáticas & Feast: §aAtivado");
                Auto.setItemMeta(Autoo);
                MessageAutomatic.add(e.getWhoClicked().getName());
                inv.setItem(3, Auto);
            }


            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Tell: §aAtivado")) {
                Inventory inv = e.getClickedInventory();

                ItemStack Tell = new ItemStack(new ItemStack(351, 1, (short) 8));
                ItemMeta Scoree = Tell.getItemMeta();
                Scoree.setDisplayName("§7Tell: §cDesativado");
                Tell.setItemMeta(Scoree);
                ((Player) e.getWhoClicked()).chat("/tell off");

                inv.setItem(1, Tell);
                return;
            } else {
                if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§7Tell: §cDesativado")) {
                    Inventory inv = e.getClickedInventory();

                    ItemStack Tell = new ItemStack(new ItemStack(351, 1, (short) 10));
                    ItemMeta Telll = Tell.getItemMeta();
                    Telll.setDisplayName("§7Tell: §aAtivado");
                    Tell.setItemMeta(Telll);
                    ((Player) e.getWhoClicked()).chat("/tell on");

                    inv.setItem(1, Tell);
                    return;
                }
            }
        }
    }

}
