package pvp.sunshine.bukkit.manager.inventory;

import java.util.ArrayList;
import java.util.Arrays;

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
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pvp.sunshine.bukkit.ability.KitVisibility;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLShop;

public class KitType implements Listener {
    public static void getHud(Player p) {
        Inventory inv = Bukkit.createInventory((InventoryHolder) p, 54, "§8Kit's");

        ItemStack vidroa = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidroaa = vidroa.getItemMeta();
        vidroa.setDurability((short) 5);
        vidroaa.setDisplayName("§a*");
        vidroa.setItemMeta(vidroaa);

        ItemStack vidrob = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidrobb = vidrob.getItemMeta();
        vidrob.setDurability((short) 4);
        vidrobb.setDisplayName("§e*");
        vidrob.setItemMeta(vidrobb);

        ItemStack vidroz = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidrozz = vidroz.getItemMeta();
        vidroz.setDurability((short) 11);
        vidrozz.setDisplayName("§1*");
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

        if (p.hasPermission("kit.pvp") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("pvp")) {
            ItemStack pyro = new ItemStack(Material.STONE_SWORD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dPvP");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Batalhe com o kit clássico do kitpvp");
            descpyro.add("§7Receba uma espada com força 1 e um peitoral!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("PvP") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }

        if (p.hasPermission("kit.kangaroo") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("kangaroo")) {
            ItemStack pyro = new ItemStack(Material.FIREWORK);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dKangaroo");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Consiga dar Double Jump e ser");
            descpyro.add("§7Immune a tomar dano de lugares altos!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Kangaroo") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }

        if (p.hasPermission("kit.ninja") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("ninja")) {
            ItemStack pyro = new ItemStack(Material.COMPASS);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dNinja");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Ao apertar shift você se teletransporta");
            descpyro.add("§7Até seu oponente!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Ninja") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }

        if (p.hasPermission("kit.urgal") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("urgal")) {
            ItemStack pyro = new ItemStack(Material.REDSTONE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dUrgal");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Receba efeito de força por alguns segundos!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Urgal") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.hotpotato") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("hotpotato")) {
            ItemStack pyro = new ItemStack(Material.BAKED_POTATO);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dHotPotato");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Exploda a cabeça dos oponentes!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("HotPotato") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.magma") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("magma")) {
            ItemStack pyro = new ItemStack(Material.MAGMA_CREAM);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dMagma");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Coloque fogo em seus oponentes!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Magma") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.anchor") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("anchor")) {
            ItemStack pyro = new ItemStack(Material.ANVIL);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dAnchor");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Não receba repulsão!");
            descpyro.add("§7Mas seu oponente também não receberá!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Anchor") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.antistomper") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("antistomper")) {
            ItemStack pyro = new ItemStack(Material.GOLD_BOOTS);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dAntiStomper");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Seja immune a jogadores com kit stomper");
            descpyro.add("§7E não leve dano de queda em lugares altos");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("AntiStomper") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.fisherman") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("fisherman")) {
            ItemStack pyro = new ItemStack(Material.FISHING_ROD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dFisherman");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Puxe o oponente até você");
            descpyro.add("§7Com sua vara!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Fisherman") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.boxer") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("boxer")) {
            ItemStack pyro = new ItemStack(Material.QUARTZ);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dBoxer");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Dê o dobro a mais do dano de uma");
            descpyro.add("§7Espada de pedra!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Boxer") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.confuser") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("confuser")) {
            ItemStack pyro = new ItemStack(Material.APPLE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dConfuser");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Cause efeito de confusão em seu oponente!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Confuser") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.gladiator") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("gladiator")) {
            ItemStack pyro = new ItemStack(Material.IRON_FENCE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dGladiator");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Lute com seu oponente em uma arena");
            descpyro.add("§7De gladiadores!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Gladiator") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.monk") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("monk")) {
            ItemStack pyro = new ItemStack(Material.BLAZE_ROD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dMonk");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Desarme o seu oponente!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Monk") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.phantom") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("phantom")) {
            ItemStack pyro = new ItemStack(Material.FEATHER);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dPhantom");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Tenha a habilidade de voar por 5 segundos!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Phantom") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.snail") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("snail")) {
            ItemStack pyro = new ItemStack(Material.SOUL_SAND);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dSnail");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Cause lentidão em seus oponentes!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Snail") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.stomper") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("stomper")) {
            ItemStack pyro = new ItemStack(Material.IRON_BOOTS);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dStomper");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Esmague seus oponentes e não receba");
            descpyro.add("§7Dano de lugares altos!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Stomper") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.switcher") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("switcher")) {
            ItemStack pyro = new ItemStack(Material.SNOW_BALL);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dSwitcher");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Receba 16 bolas de neves com o poder");
            descpyro.add("§7De trocar o lugar do seu oponente com o seu!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Switcher") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.thor") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("thor")) {
            ItemStack pyro = new ItemStack(Material.GOLD_AXE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dThor");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Conjure raios dos ceús para");
            descpyro.add("§7Atingir seus oponentes!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Thor") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.timelord") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("timelord")) {
            ItemStack pyro = new ItemStack(Material.WATCH);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dTimeLord");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Congele todos a sua volta!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("TimeLord") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.viking") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("viking")) {
            ItemStack pyro = new ItemStack(Material.WOOD_AXE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dViking");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Cause muito dano com seu machado!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Viking") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.viper") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("viper")) {
            ItemStack pyro = new ItemStack(Material.SPIDER_EYE);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dViper");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Envenene seus oponentes!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Viper") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.grandpa") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("grandpa")) {
            ItemStack pyro = new ItemStack(Material.STICK);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dGrandpa");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Receba uma bastão com repulsão II!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Grandpa") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.barbarian") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("barbarian")) {
            ItemStack pyro = new ItemStack(Material.WOOD_SWORD);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dBarbarian");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Ao matar um jogador, sua espada");
            descpyro.add("§7Ficará mais forte!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Barbarian") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }
        if (p.hasPermission("kit.fireman") || p.hasPermission("kit.*") || SQLShop.cacheshop.get(p.getUniqueId()).toLowerCase().contains("fireman")) {
            ItemStack pyro = new ItemStack(Material.WATER_BUCKET);
            ItemMeta metapyro = pyro.getItemMeta();
            metapyro.setDisplayName("§dFireman");
            ArrayList<String> descpyro = new ArrayList<>();
            descpyro.add("§7Seja immune ao fogo!");
            descpyro.add("");
            descpyro.add("§e" + KitVisibility.abilityCounts.get("Fireman") + "§7 jogadores usando.");
            descpyro.add("");
            descpyro.add("§eClique para selecionar.");
            metapyro.setLore(descpyro);
            pyro.setItemMeta(metapyro);
            inv.addItem(new ItemStack[]{pyro});
        }

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

        if (!clickedInventory.getTitle().equalsIgnoreCase("§8Kit's")) {
            return;
        }

        e.setCancelled(true);

        switch (clickedItem.getType()) {
            case STAINED_GLASS_PANE:
                player.closeInventory();
                break;
            case STONE_SWORD:
                executeKitCommand(player, "kit pvp");
                spawnEndermanTeleportParticles(player, 100);
                break;

            case STICK:
                executeKitCommand(player, "kit grandpa");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case WOOD_SWORD:
                executeKitCommand(player, "kit barbarian");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case WATER_BUCKET:
                executeKitCommand(player, "kit fireman");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case BAKED_POTATO:
                executeKitCommand(player, "kit hotpotato");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case COMPASS:
                executeKitCommand(player, "kit ninja");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case REDSTONE:
                executeKitCommand(player, "kit urgal");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case IRON_FENCE:
                executeKitCommand(player, "kit gladiator");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case GOLD_BOOTS:
                executeKitCommand(player, "kit antistomper");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case WOOD_AXE:
                executeKitCommand(player, "kit viking");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case SNOW_BALL:
                executeKitCommand(player, "kit switcher");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case FIREWORK:
                executeKitCommand(player, "kit kangaroo");
                spawnEndermanTeleportParticles(player, 100);
                spawnEndermanTeleportParticles(player, 100);
                break;
            case FISHING_ROD:
                executeKitCommand(player, "kit fisherman");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case BLAZE_ROD:
                executeKitCommand(player, "kit monk");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case ANVIL:
                executeKitCommand(player, "kit anchor");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case QUARTZ:
                executeKitCommand(player, "kit boxer");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case APPLE:
                executeKitCommand(player, "kit confuser");
                spawnEndermanTeleportParticles(player, 100);
                break;

            case SPIDER_EYE:
                executeKitCommand(player, "kit viper");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case SOUL_SAND:
                executeKitCommand(player, "kit snail");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case IRON_BOOTS:
                executeKitCommand(player, "kit stomper");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case GOLD_AXE:
                executeKitCommand(player, "kit thor");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case MAGMA_CREAM:
                executeKitCommand(player, "kit magma");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case FEATHER:
                executeKitCommand(player, "kit phantom");
                spawnEndermanTeleportParticles(player, 100);
                break;
            case WATCH:
                executeKitCommand(player, "kit timelord");
                spawnEndermanTeleportParticles(player, 100);
                break;
            default:
                break;
        }
    }

    private void executeKitCommand(Player player, String command) {
        player.closeInventory();
        player.performCommand(command);
    }

    public static void spawnEndermanTeleportParticles(Player player, int particleCount) {
        EnumParticle particleType = EnumParticle.PORTAL;
        Location location = player.getLocation();
        int count = particleCount;

        for (int i = 0; i < count; i++) {
            double angle = Math.random() * Math.PI * 2.0D;
            double radius = Math.random() * 1.0D;

            float xOffset = (float) (Math.cos(angle) * radius);
            float yOffset = 0.0F;
            float zOffset = (float) (Math.sin(angle) * radius);
            PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particleType, true,
                    (float) location.getX() + xOffset, (float) location.getY() + yOffset,
                    (float) location.getZ() + zOffset, 0.0F, 0.0F, 0.0F, 0.1F, 1, new int[0]);

            (((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packet);
        }
    }
}
