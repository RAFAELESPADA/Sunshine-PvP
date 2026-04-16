package pvp.sunshine.bukkit.manager.inventory;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.util.Vector;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.inventory.action.OpenInventory;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLShop;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.ActionUtil;

import java.util.HashMap;
import java.util.Random;

public class BoxSystemType implements Listener {

    private static final HashMap<String, String> kitSorteado = new HashMap<String, String>();

    public static void adicionarCaixa(final Player p, final int i) {
        int Value = BukkitMain.getInstance().getConfig().getInt(String.valueOf(p.getName()) + ".Caixa");
        BukkitMain.getInstance().getConfig().set(String.valueOf(p.getName()) + ".Caixa", (Object)(Value + i));
        BukkitMain.getInstance().saveConfig();
    }

    public static int getCaixa(final Player p) {
        return BukkitMain.getInstance().getConfig().getInt(String.valueOf(p.getName()) + ".Caixa");
    }

    public static void removerCaixa(Player p, int i) {
        String playerName = p.getName();
        FileConfiguration config = BukkitMain.getInstance().getConfig();

        if (config.contains(playerName + ".Caixa")) {
            int currentValue = config.getInt(playerName + ".Caixa");
            int newValue = currentValue - i;

            if (newValue <= 0) {
                config.set(playerName, null);
            } else {
                config.set(playerName + ".Caixa", newValue);
            }

            BukkitMain.getInstance().saveConfig();
        }
    }


    public static void GuiCaixas(Player p) {
        Inventory inv = Bukkit.createInventory(p, 9, "§8Sorteando...");
        ItemStack vidrob = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 0);
        ItemStack vidror = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short) 5);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(0, vidrob);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 10L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(1, vidror);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 15L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(2, vidrob);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 20L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(3, vidror);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 25L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(4, vidrob);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 30L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(5, vidror);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 35L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(6, vidrob);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 40L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(7, vidror);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 45L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                inv.setItem(8, vidrob);
                p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
            }
        }, 50L);
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                final int r = new Random().nextInt(21);
                if (r == 0) {
                    sortearKit(inv, p, "Anchor", Material.ANVIL);
                }
                if (r == 1) {
                    sortearKit(inv, p, "AntiStomper", Material.GOLD_BOOTS);
                }
                if (r == 2) {
                    sortearKit(inv, p, "Barbarian", Material.WOOD_SWORD);
                }
                if (r == 3) {
                    sortearKit(inv, p, "Boxer", Material.QUARTZ);
                }
                if (r == 4) {
                    sortearKit(inv, p, "Confuser", Material.APPLE);
                }
                if (r == 5) {
                    sortearKit(inv, p, "Fireman", Material.WATER_BUCKET);
                }
                if (r == 6) {
                    sortearKit(inv, p, "Gladiator", Material.IRON_FENCE);
                }
                if (r == 7) {
                    sortearKit(inv, p, "HotPotato", Material.BAKED_POTATO);
                }
                if (r == 8) {
                    sortearKit(inv, p, "Monk", Material.BLAZE_ROD);
                }
                if (r == 9) {
                    sortearKit(inv, p, "Phantom", Material.FEATHER);
                }
                if (r == 10) {
                    sortearKit(inv, p, "Snail", Material.SOUL_SAND);
                }
                if (r == 11) {
                    sortearKit(inv, p, "Stomper", Material.IRON_BOOTS);
                }
                if (r == 12) {
                    sortearKit(inv, p, "Switcher", Material.SNOW_BALL);
                }
                if (r == 13) {
                    sortearKit(inv, p, "Thor", Material.GOLD_AXE);
                }
                if (r == 14) {
                    sortearKit(inv, p, "TimeLord", Material.WATCH);
                }
                if (r == 15) {
                    sortearKit(inv, p, "Urgal", Material.REDSTONE);
                }
                if (r == 16) {
                    sortearKit(inv, p, "Viking", Material.WOOD_AXE);
                }
                if (r == 17) {
                    sortearKit(inv, p, "Viper", Material.SPIDER_EYE);
                }
            }
        }, 55L);
        p.openInventory(inv);
    }

    private static int randomCoins() {
        Random random = new Random();
        return random.nextInt(190) + 80;
    }

    private static void sortearKit(Inventory inv, Player p, String kit, Material mat) {
        kitSorteado.put(p.getName(), kit);
        ItemStack item = new ItemStack(mat);
        ItemMeta itemm = item.getItemMeta();
        itemm.setDisplayName("§aKit: §7" + kitSorteado.get(p.getName()));
        item.setItemMeta(itemm);
        if (!p.hasPermission("kit." + kit) && !p.hasPermission("kit.*")) {
            SQLShop.addkit((Player) p, kit);
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set kit." + kit + " server=pvp");
            p.sendMessage("§a§lCAIXAS §fParabéns! você ganhou o kit §a" + kitSorteado.get(p.getName()) + "§f!");
            playFirework(p.getLocation(), Color.AQUA, p);
            inv.setItem(4, item);
            for (Player all : Bukkit.getOnlinePlayers()) {
                ActionUtil.sendActionbar(all, "§a§lCAIXAS §fO jogador §a" + p.getName() + "§f recebeu o kit §e" + kitSorteado.get(p.getName()));
            }
            PvP.update(p);
            kitSorteado.remove(p.getName());
            Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.closeInventory();
                    OpenInventory.caixa.remove(p.getName());
                }
            }, 60L);
        } else {
            int coinsSorteado = randomCoins();
            p.sendMessage("§3§lCAIXAS §fOps! parece que você já possui em sua conta o kit sorteado. Como recompensa, você recebeu §e" + coinsSorteado +" coins§f em sua conta.");
            SQLPvP.addCoins(p, coinsSorteado);
            p.playSound(p.getLocation(), Sound.CAT_MEOW, 1.0f, 1.0f);
            PvP.update(p);
            meaw(p, 500);
            ItemStack coins = new ItemStack(Material.GOLD_INGOT);
            ItemMeta coinmeta = coins.getItemMeta();
            coinmeta.setDisplayName("§e" + coinsSorteado + " Coins!");
            coins.setItemMeta(coinmeta);
            inv.setItem(4, coins);
            Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
                @Override
                public void run() {
                    p.closeInventory();
                    OpenInventory.caixa.remove(p.getName());
                }
            }, 60L);
        }
    }

    public static void playFirework(final Location location, final Color color, final Player p) {
        final Firework firework = (Firework)location.getWorld().spawn(location, (Class)Firework.class);
        final FireworkMeta fMeta = firework.getFireworkMeta();
        fMeta.addEffect(FireworkEffect.builder().withColor(color).build());
        firework.setFireworkMeta(fMeta);
        firework.setVelocity(new Vector(0.0, -1.0, 0.0));
    }

    public static void spawnEndermanTeleportParticles(Player player, int particleCount) {
        EnumParticle particleType = EnumParticle.LAVA;
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
            player.playSound(player.getLocation(), Sound.NOTE_STICKS, 1.0F, 1.0F);
        }
    }

    public static void meaw(Player player, int particleCount) {
        EnumParticle particleType = EnumParticle.FLAME;
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

    @EventHandler
    public void aoMecher(final InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Sorteando...") && e.getCurrentItem() != null && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
        }
    }
}
