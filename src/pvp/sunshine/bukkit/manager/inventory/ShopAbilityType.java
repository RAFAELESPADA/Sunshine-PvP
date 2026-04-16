package pvp.sunshine.bukkit.manager.inventory;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLShop;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class ShopAbilityType implements Listener {

    public static Map<String, String> shopkit = new HashMap<String, String>();

    public static void getShopKit(Player p) {
        Inventory inv = Bukkit.createInventory(p, 54, "§8Loja de Kit's");

        ItemStack vidroa = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidroaa = vidroa.getItemMeta();
        vidroa.setDurability((short) 2);
        vidroaa.setDisplayName("§d*");
        vidroa.setItemMeta(vidroaa);

        ItemStack vidrob = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidrobb = vidrob.getItemMeta();
        vidrob.setDurability((short) 2);
        vidrobb.setDisplayName("§d*");
        vidrob.setItemMeta(vidrobb);

        ItemStack vidroz = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidrozz = vidroz.getItemMeta();
        vidroz.setDurability((short) 4);
        vidrozz.setDisplayName("§e*");
        vidroz.setItemMeta(vidrozz);

        ItemStack vinha = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vinhaa = vinha.getItemMeta();
        vinhaa.setDisplayName("§f*");
        vinha.setItemMeta(vinhaa);


        ItemStack fence = new ItemStack(Material.IRON_FENCE);
        ItemMeta fencee = fence.getItemMeta();
        fencee.setDisplayName("§8*");
        fence.setItemMeta(fencee);

        ItemStack emerald = new ItemStack(Material.VINE);
        ItemMeta emeraldd = emerald.getItemMeta();
        emeraldd.setDisplayName("§2*");
        emerald.setItemMeta(emeraldd);

        inv.setItem(0, vidroz);
        inv.setItem(1, fence);
        inv.setItem(2, vidroa);
        inv.setItem(3, vinha);
        inv.setItem(4, vidroz);
        inv.setItem(5, vinha);
        inv.setItem(6, vidroa);
        inv.setItem(7, fence);
        inv.setItem(8, vidroz);
        inv.setItem(9, fence);
        inv.setItem(10, vidroa);
        inv.setItem(16, vidroa);
        inv.setItem(17, fence);
        inv.setItem(18, vidrob);
        inv.setItem(26, vidrob);
        inv.setItem(27, vidrob);
        inv.setItem(35, vidrob);
        inv.setItem(36, fence);
        inv.setItem(37, vidrob);
        inv.setItem(43, vidrob);
        inv.setItem(44, fence);
        inv.setItem(45, vidroz);
        inv.setItem(46, fence);
        inv.setItem(47, vidroa);
        inv.setItem(47, vidroa);
        inv.setItem(48, vinha);
        inv.setItem(50, vinha);
        inv.setItem(49, vidroa);
        inv.setItem(49, vidroa);
        inv.setItem(50, vinha);
        inv.setItem(49, vidroz);
        inv.setItem(51, vidroa);
        inv.setItem(52, fence);
        inv.setItem(53, vidroz);

        if (!p.hasPermission("kit.ninja") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("ninja")) {
            ItemStack pyro = new ItemStack(Material.COMPASS);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dNinja");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Ao apertar shift você se teletransporta");
            descpyro.add("§7Até seu oponente!");
            descpyro.add("");
            descpyro.add("§fPreço: §e10.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }

        if (!p.hasPermission("kit.urgal") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("urgal")) {
            ItemStack pyro = new ItemStack(Material.REDSTONE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dUrgal");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Receba efeito de força por alguns segundos!");
            descpyro.add("");
            descpyro.add("§fPreço: §e13.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.hotpotato") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("hotpotato")) {
            ItemStack pyro = new ItemStack(Material.BAKED_POTATO);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dHotPotato");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Exploda a cabeça dos oponentes!");
            descpyro.add("");
            descpyro.add("§fPreço: §e20.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.magma") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("magma")) {
            ItemStack pyro = new ItemStack(Material.MAGMA_CREAM);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dMagma");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Coloque fogo em seus oponentes!");
            descpyro.add("");
            descpyro.add("§fPreço: §e5.000");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.anchor") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("anchor")) {
            ItemStack pyro = new ItemStack(Material.ANVIL);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dAnchor");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Não receba repulsão!");
            descpyro.add("§7Mas seu oponente também não receberá!");
            descpyro.add("");
            descpyro.add("§fPreço: §e4.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.antistomper") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("antistomper")) {
            ItemStack pyro = new ItemStack(Material.GOLD_BOOTS);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dAntiStomper");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Seja immune a jogadores com kit stomper");
            descpyro.add("§7E não leve dano de queda em lugares altos");
            descpyro.add("");
            descpyro.add("§fPreço: §e8.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.fisherman") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("fisherman")) {
            ItemStack pyro = new ItemStack(Material.FISHING_ROD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dFisherman");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Puxe o oponente até você");
            descpyro.add("§7Com sua vara!");
            descpyro.add("");
            descpyro.add("§fPreço: §e7.200 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.boxer") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("boxer")) {
            ItemStack pyro = new ItemStack(Material.QUARTZ);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dBoxer");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Dê o dobro a mais do dano de uma");
            descpyro.add("§7Espada de pedra!");
            descpyro.add("");
            descpyro.add("§fPreço: §e3.500 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.confuser") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("confuser")) {
            ItemStack pyro = new ItemStack(Material.APPLE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dConfuser");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Cause efeito de confusão em seu oponente!");
            descpyro.add("");
            descpyro.add("§fPreço: §e3.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.gladiator") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("gladiator")) {
            ItemStack pyro = new ItemStack(Material.IRON_FENCE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dGladiator");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Lute com seu oponente em uma arena");
            descpyro.add("§7De gladiadores!");
            descpyro.add("");
            descpyro.add("§fPreço: §e15.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.monk") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("monk")) {
            ItemStack pyro = new ItemStack(Material.BLAZE_ROD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dMonk");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Desarme o seu oponente!");
            descpyro.add("");
            descpyro.add("§fPreço: §e9.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.phantom") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("phantom")) {
            ItemStack pyro = new ItemStack(Material.FEATHER);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dPhantom");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Tenha a habilidade de voar por 5 segundos!");
            descpyro.add("");
            descpyro.add("§fPreço: §e7.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.snail") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("snail")) {
            ItemStack pyro = new ItemStack(Material.SOUL_SAND);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dSnail");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Cause lentidão em seus oponentes!");
            descpyro.add("");
            descpyro.add("§fPreço: §e6.500 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.stomper") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("stomper")) {
            ItemStack pyro = new ItemStack(Material.IRON_BOOTS);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dStomper");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Esmague seus oponentes e não receba");
            descpyro.add("§7Dano de lugares altos!");
            descpyro.add("");
            descpyro.add("§fPreço: §e9.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.switcher") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("switcher")) {
            ItemStack pyro = new ItemStack(Material.SNOW_BALL);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dSwitcher");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Receba 16 bolas de neves com o poder");
            descpyro.add("§7De trocar o lugar do seu oponente com o seu!");
            descpyro.add("");
            descpyro.add("§fPreço: §e2.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.thor") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("thor")) {
            ItemStack pyro = new ItemStack(Material.GOLD_AXE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dThor");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Conjure raios dos ceús para");
            descpyro.add("§7Atingir seus oponentes!");
            descpyro.add("");
            descpyro.add("§fPreço: §e1.100 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.timelord") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("timelord")) {
            ItemStack pyro = new ItemStack(Material.WATCH);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dTimeLord");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Congele todos a sua volta!");
            descpyro.add("");
            descpyro.add("§fPreço: §e10.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.viking") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("viking")) {
            ItemStack pyro = new ItemStack(Material.WOOD_AXE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dViking");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Cause muito dano com seu machado!");
            descpyro.add("");
            descpyro.add("§fPreço: §e9.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.viper") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("viper")) {
            ItemStack pyro = new ItemStack(Material.SPIDER_EYE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dViper");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Envenene seus oponentes!");
            descpyro.add("");
            descpyro.add("§fPreço: §e1.150 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.barbarian") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("barbarian")) {
            ItemStack pyro = new ItemStack(Material.WOOD_SWORD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dBarbarian");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Ao matar um jogador, sua espada");
            descpyro.add("§7Ficará mais forte!");
            descpyro.add("");
            descpyro.add("§fPreço: §e12.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (!p.hasPermission("kit.fireman") && !p.hasPermission("kit.*") && !SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("fireman")) {
            ItemStack pyro = new ItemStack(Material.WATER_BUCKET);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dFireman");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Seja immune ao fogo!");
            descpyro.add("");
            descpyro.add("§fPreço: §e3.000 coins");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }

        p.openInventory(inv);
    }


    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onPlayerClickInventry(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Loja de Kit's") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                e.getWhoClicked().closeInventory();
                return;
            }
            if (!PurchaseType.accept.contains(e.getWhoClicked())) {
                if (e.getCurrentItem().getType() == Material.WOOD_SWORD) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Barbarian");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.ANVIL) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Anchor");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.GOLD_BOOTS) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "AntiStomper");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.QUARTZ) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Boxer");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.APPLE) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Confuser");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.FISHING_ROD) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Fisherman");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.IRON_FENCE) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Gladiator");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.BAKED_POTATO) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "HotPotato");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.MAGMA_CREAM) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Magma");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.BLAZE_ROD) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Monk");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.COMPASS) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Ninja");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.FEATHER) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Phantom");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.SOUL_SAND) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Snail");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.IRON_BOOTS) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Stomper");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.SNOW_BALL) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Switcher");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.GOLD_AXE) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Thor");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.WATCH) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "TimeLord");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.REDSTONE) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Urgal");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.WOOD_AXE) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Viking");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.SPIDER_EYE) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Viper");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
                if (e.getCurrentItem().getType() == Material.WATER_BUCKET) {
                    e.setCancelled(true);
                    shopkit.put(e.getWhoClicked().getName(), "Fireman");
                    PurchaseType.ConfirmShop(((Player) e.getWhoClicked()));
                    return;
                }
            } else {
                e.getWhoClicked().closeInventory();
                e.getWhoClicked().sendMessage(
                        "§c§lSHOP §fVocê já possui uma compra pendente, aguarde 10 segundos para concluir outra.");
            }
        }
    }


}
