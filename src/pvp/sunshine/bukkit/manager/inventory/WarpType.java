package pvp.sunshine.bukkit.manager.inventory;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
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

import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.event.SumoListener;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;

public class WarpType implements Listener {

    public static UUID SumoRandom = null;
    public static Map<String, String> Sumo = new HashMap<String, String>();

    public static int Fps = 0;

    public static int getFpsInfo() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(p) == "Fps") {
                Fps = Fps + 1;
            }
        }
        return Fps;
    }
    public static boolean inWarp(Player p) {

        if (RegisterAbility.getAbility(p) == "ComboFly") {
        	return true;
        }
        if (RegisterAbility.getAbility(p) == "Fps") {
        	return true;
        }
        if (RegisterAbility.getAbility(p) == "Knockback") {
        	return true;
        }
        if (RegisterAbility.getAbility(p) == "Nenhum") {
        	return true;
        }
        if (RegisterAbility.getAbility(p) == "Lava") {
        	return true;
        }
        if (RegisterAbility.getAbility(p) == "1v1") {
        	return true;
        }
        if (RegisterAbility.getAbility(p) == "Gapple") {
        	return true;
        }
        return false;
    }

    public static int Knockback = 0;

    public static int getKnockInfo() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(p) == "Knockback") {
                Knockback = Knockback + 1;
            }
        }
        return Knockback;
    }

    public static int Challenge = 0;

    public static int getChallengeInfo() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(p) == "Lava") {
                Challenge = Challenge + 1;
            }
        }
        return Challenge;
    }

    public static int x1 = 0;

    public static int get1v1Info() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(p) == "1v1") {
                x1 = x1 + 1;
            }
        }
        return x1;
    }
    public static int ComboFly = 0;

    public static int getComboInfo() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(p) == "ComboFly") {
                ComboFly = ComboFly + 1;
            }
        }
        return ComboFly;
    }

    public static int Gapple = 0;

    public static int getGappleInfo() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (RegisterAbility.getAbility(p) == "Gapple") {
                Gapple = Gapple + 1;
            }
        }
        return Gapple;
    }

    public static void getWarp(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§8Warp's");

        ItemStack verde = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);  // Verde
        ItemStack amarelo = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 4);  // Amarelo
        ItemStack azul = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 11);    // Azul
        ItemStack branco = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);   // Branco

        ItemMeta metaVerde = verde.getItemMeta();
        metaVerde.setDisplayName("§a*");  // Verde
        verde.setItemMeta(metaVerde);

        ItemMeta metaAmarelo = amarelo.getItemMeta();
        metaAmarelo.setDisplayName("§e*");  // Amarelo
        amarelo.setItemMeta(metaAmarelo);

        ItemMeta metaAzul = azul.getItemMeta();
        metaAzul.setDisplayName("§1*");  // Azul
        azul.setItemMeta(metaAzul);

        ItemMeta metaBranco = branco.getItemMeta();
        metaBranco.setDisplayName("§f*");  // Branco
        branco.setItemMeta(metaBranco);

        inv.setItem(0, verde);
        inv.setItem(1, amarelo);
        inv.setItem(2, amarelo);
        inv.setItem(3, azul);
        inv.setItem(4, branco);
        inv.setItem(5, azul);
        inv.setItem(6, amarelo);
        inv.setItem(7, amarelo);
        inv.setItem(8, verde);
        inv.setItem(9, branco);
        inv.setItem(17, branco);
        inv.setItem(18, verde);
        inv.setItem(19, amarelo);
        inv.setItem(20, amarelo);
        inv.setItem(21, azul);
        inv.setItem(22, branco);
        inv.setItem(23, azul);
        inv.setItem(24, amarelo);
        inv.setItem(25, amarelo);
        inv.setItem(26, verde);

        ItemStack fps22 = new ItemStack(Material.GOLDEN_APPLE);
        ItemMeta fpsmeta22 = fps22.getItemMeta();
        fpsmeta22.setDisplayName("§aGapple");
        fpsmeta22.setLore(Arrays.asList("§8Participantes", " §7- " + Gapple + " jogadores conectados", "§eClique para entrar!"));
        fps22.setItemMeta(fpsmeta22);
        inv.setItem(10, fps22);

        ItemStack fps2 = new ItemStack(Material.RAW_FISH, 1, (short)3);
        ItemMeta fpsmeta2 = fps2.getItemMeta();
        fpsmeta2.setDisplayName("§aComboFly");
        fpsmeta2.setLore(Arrays.asList("§8Participantes", " §7- " + ComboFly + " jogadores conectados", "§eClique para entrar!"));
        fps2.setItemMeta(fpsmeta2);
        inv.setItem(11, fps2);

        ItemStack fps = new ItemStack(Material.GLASS);
        ItemMeta fpsmeta = fps.getItemMeta();
        fpsmeta.setDisplayName("§aFps");
        fpsmeta.setLore(Arrays.asList("§8Participantes", " §7- " + Fps + " jogadores conectados", "§eClique para entrar!"));
        fps.setItemMeta(fpsmeta);
        inv.setItem(12, fps);

        ItemStack lava = new ItemStack(Material.LAVA_BUCKET);
        ItemMeta lavameta = lava.getItemMeta();
        lavameta.setDisplayName("§aLava Challenge");
        lavameta.setLore(Arrays.asList("§8Participantes:", " §7- " + Challenge + " jogadores conectados", "§eClique para entrar!"));
        lava.setItemMeta(lavameta);
        inv.setItem(13, lava);

        ItemStack duels = new ItemStack(Material.BLAZE_ROD);
        ItemMeta duelsmeta = duels.getItemMeta();
        duelsmeta.setDisplayName("§a1v1");
        duelsmeta.setLore(Arrays.asList("§8Participantes:", " §7- " + x1 + " jogadores conectados", "§eClique para entrar!"));
        duels.setItemMeta(duelsmeta);
        inv.setItem(14, duels);

        ItemStack sumo = new ItemStack(Material.LEASH);
        ItemMeta sumometa = sumo.getItemMeta();
        sumometa.setDisplayName("§aSumô");
        if (SumoRandom == null) {
            sumometa.setLore(Arrays.asList("§8Participantes:", " §7- 0 jogadores na fila.", "§eClique para entrar!"));
        } else {
            sumometa.setLore(Arrays.asList("§8Participantes:", " §7- 1 jogadores na fila.", "§eClique para entrar!"));
        }
        sumo.setItemMeta(sumometa);
        inv.setItem(15, sumo);

        ItemStack knockback = new ItemStack(Material.STICK);
        ItemMeta knockmeta = knockback.getItemMeta();
        knockmeta.setDisplayName("§aKnockback");
        knockmeta.setLore(Arrays.asList("§8Participantes:", " §7- " + Knockback + " jogadores conectados", "§eClique para entrar!"));
        knockback.setItemMeta(knockmeta);
        inv.setItem(16, knockback);

        p.openInventory(inv);
    }

    @SuppressWarnings("deprecation")
    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClickEventWarps(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Warp's") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.IRON_FENCE) {
                e.setCancelled(true);
                p.closeInventory();
                return;
            }
            if (e.getCurrentItem().getType() == Material.GOLDEN_APPLE) {
                p.chat("/warp gapple");
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getType() == Material.RAW_FISH && e.getCurrentItem().getDurability() == 3) {
                p.chat("/warp combofly");
                e.setCancelled(true);
                return;
            }
            if (e.getCurrentItem().getType() == Material.GLASS) {
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.dispatchCommand(p, "warp fps");
                return;
            }
            if (e.getCurrentItem().getType() == Material.STICK) {
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.dispatchCommand(p, "warp knockback");
                return;
            }
            if (e.getCurrentItem().getType() == Material.LAVA_BUCKET) {
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.dispatchCommand(p, "warp lava");
                return;
            }
            if (e.getCurrentItem().getType() == Material.BLAZE_ROD) {
                e.setCancelled(true);
                p.closeInventory();
                Bukkit.dispatchCommand(p, "1v1");
                return;
            }
            if (e.getCurrentItem().getType() == Material.LEASH) {
                UUID id = p.getUniqueId();
                e.setCancelled(true);
                p.closeInventory();
                p.sendMessage("§e§lSUMO §fVocê entrou na fila do Sumô, buscando oponente...");
                p.setAllowFlight(false);
                p.setFlying(false);
                if ((SumoRandom != null) && (SumoRandom != id)) {
                    Player p2 = Bukkit.getPlayer(SumoRandom);
                    if (p2 != null) {
                        p.closeInventory();
                        p.sendMessage("§a§lSUMO §fOponente encontrado! você está batalhando contra §a" + p2.getName());
                        p2.sendMessage("§a§lSUMO §fOponente encontrado! você está batalhando contra §a" + p.getName());
                        p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                        p2.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                        Sumo.put(p.getName(), p2.getName());
                        Sumo.put(p2.getName(), p.getName());
                        SumoListener.BLOCK_COMMAND.remove(p.getName());
                        SumoListener.BLOCK_COMMAND.remove(p2.getName());
                        p.teleport(new Location(Bukkit.getServer().getWorld("1v1"), 6429.881, 76.000000, 64483.881, -0.5F, 0.5F));
                        p2.teleport(new Location(Bukkit.getServer().getWorld("1v1"), 6429.110, 76.000000, 64496.683, 178.9F, 2.6F));
                        SumoRandom = null;
                        for (Player s : Bukkit.getOnlinePlayers()) {
                            p.hidePlayer(s);
                            p2.hidePlayer(s);
                        }
                        p2.setMaximumNoDamageTicks(20);
                        p.setMaximumNoDamageTicks(20);
                        p.updateInventory();
                        p2.updateInventory();
                        p.showPlayer(p2);
                        p2.showPlayer(p);
                        SumoListener.teleportPlayer(p);
                        SumoListener.teleportPlayer(p2);
                        PvP.update(p);
                        PvP.update(p2);
                    }
                } else {
                    SumoListener.APISearch(p);
                    SumoRandom = id;
                }
            }
        }
    }
}
