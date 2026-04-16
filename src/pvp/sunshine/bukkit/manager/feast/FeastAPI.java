package pvp.sunshine.bukkit.manager.feast;


import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Chest;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import pvp.sunshine.bukkit.BukkitMain;

public class FeastAPI {

    public static void miniFeast() {
        for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            ConfigurationSection list = BukkitMain.getInstance().getConfig().getConfigurationSection("MiniFeast");
            int x = (Integer) list.get("x");
            int y = (Integer) list.get("y");
            int z = (Integer) list.get("z");
            Random r = new Random();
            spawnLightningStrikes();
            p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x + 2, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).setType(Material.ENCHANTMENT_TABLE);
            p1.getLocation().getWorld().getBlockAt(x - 2, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 2).setType(Material.CHEST);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 2).setType(Material.CHEST);


            Chest e2 = (Chest) p1.getLocation().getWorld().getBlockAt(x - 2, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).getState();
            addItemToChest(e2, Material.GOLDEN_APPLE, 2, (short) 0, 100);
            addItemToChest(e2, Material.GOLD_HELMET, 1, (short) 0, 100);
            addItemToChest(e2, Material.SNOW_BALL, 16, (short) 0, 100);
            addItemToChest(e2, Material.LEATHER_HELMET, 1, (short) 0, 100);
            addItemToChest(e2, Material.CHAINMAIL_CHESTPLATE, 1, (short) 0, 100);
            addItemToChest(e2, Material.POTION, 1, (short) 16410, 100);
            addItemToChest(e2, Material.POTION, 1, (short) 16426, 100);

            Chest f2 = (Chest) p1.getLocation().getWorld().getBlockAt(x + 2, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).getState();
            addItemToChest(f2, Material.SNOW_BALL, 16, (short) 0, 100);
            addItemToChest(f2, Material.IRON_BOOTS, 1, (short) 0, 100);
            addItemToChest(f2, Material.BOW, 1, (short) 0, 100);
            addItemToChest(f2, Material.ARROW, 16, (short) 0, 100);
            addItemToChest(f2, Material.WOOD_SWORD, 1, (short) 0, 100);
            addItemToChest(f2, Material.GOLD_AXE, 1, (short) 0, 100);
            addItemToChest(f2, Material.POTION, 1, (short) 16418, 100);

            Chest d2 = (Chest) p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).getState();
            addItemToChest(d2, Material.GOLDEN_APPLE, 2, (short) 0, 100);
            addItemToChest(d2, Material.STONE_AXE, 1, (short) 0, 100);
            addItemToChest(d2, Material.IRON_CHESTPLATE, 1, (short) 293, 100);
            addItemToChest(d2, Material.CHAINMAIL_BOOTS, 1, (short) 0, 100);
            addItemToChest(d2, Material.BOW, 1, (short) 0, 100);
            addItemToChest(d2, Material.ARROW, 16, (short) 0, 100);
            addItemToChest(d2, Material.WOOD_SWORD, 1, (short) 0, 100);
            addItemToChest(d2, Material.LEATHER_LEGGINGS, 1, (short) 0, 100);
            addItemToChest(d2, Material.GOLD_HELMET, 1, (short) 0, 100);
            addItemToChest(d2, Material.POTION, 1, (short) 16418, 100);

            Chest d3 = (Chest) p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).getState();
            addItemToChest(d3, Material.IRON_HELMET, 1, (short) 0, 100);
            addItemToChest(d3, Material.GOLD_HELMET, 1, (short) 0, 100);
            addItemToChest(d3, Material.POTION, 1, (short) 16418, 100);

            Chest e3 = (Chest) p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 2).getState();

            addItemToChest(e3, Material.CHAINMAIL_CHESTPLATE, 1, (short) 0, 100);
            addItemToChest(e3, Material.GOLD_HELMET, 1, (short) 0, 100);
            addItemToChest(e3, Material.IRON_BOOTS, 1, (short) 0, 100);
            addItemToChest(e3, Material.POTION, 1, (short) 16429, 100);

            Chest g3 = (Chest) p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).getState();

            addItemToChest(g3, Material.IRON_AXE, 1, (short) 0, 100);
            addItemToChest(g3, Material.LEATHER_CHESTPLATE, 1, (short) 0, 100);
            addItemToChest(g3, Material.IRON_HELMET, 1, (short) 0, 100);
            addItemToChest(g3, Material.GOLD_LEGGINGS, 1, (short) 0, 100);
            addItemToChest(g3, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(g3, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(g3, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(g3, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(g3, Material.POTION, 1, (short) 16428, 100);
            addItemToChest(g3, Material.POTION, 1, (short) 16421, 100);

            Chest i2 = (Chest) p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).getState();
            Chest j2 = (Chest) p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).getState();

            Chest h2 = (Chest) p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).getState();
            addItemToChest(h2, Material.IRON_PICKAXE, 1, (short) 0, 100);
            addItemToChest(h2, Material.SNOW_BALL, 16, (short) 0, 100);
            addItemToChest(h2, Material.IRON_SWORD, 1, (short) 0, 100);
            addItemToChest(h2, Material.POTION, 1, (short) 16418, 100);

            addItemToChest(i2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(i2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(i2, Material.SNOW_BALL, 16, (short) 0, 100);
            addItemToChest(i2, Material.LEATHER_BOOTS, 1, (short) 0, 100);
            addItemToChest(i2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(i2, Material.BOW, 1, (short) 0, 100);
            addItemToChest(i2, Material.ARROW, 16, (short) 0, 100);
            addItemToChest(i2, Material.WOOD_SWORD, 1, (short) 0, 100);
            addItemToChest(i2, Material.LEATHER_LEGGINGS, 1, (short) 0, 100);
            addItemToChest(i2, Material.GOLD_HELMET, 1, (short) 0, 100);
            addItemToChest(i2, Material.POTION, 1, (short) 16418, 100);

            addItemToChest(j2, Material.IRON_AXE, 1, (short) 0, 100);
            addItemToChest(j2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(j2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(j2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(j2, Material.MUSHROOM_SOUP, 1, (short) 0, 100);
            addItemToChest(j2, Material.SNOW_BALL, 16, (short) 0, 100);
            addItemToChest(j2, Material.GOLD_HELMET, 1, (short) 0, 100);
            addItemToChest(j2, Material.GOLD_BOOTS, 1, (short) 0, 100);
            addItemToChest(j2, Material.POTION, 1, (short) 16421, 100);

            Chest k2 = (Chest) p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 2).getState();
            addItemToChest(k2, Material.STONE_SWORD, 1, (short) 0, 100);
            addItemToChest(k2, Material.CHAINMAIL_HELMET, 1, (short) 0, 100);
            addItemToChest(k2, Material.GOLD_LEGGINGS, 1, (short) 0, 100);
            addItemToChest(k2, Material.DIAMOND_BOOTS, 1, (short) 0, 100);
            addItemToChest(k2, Material.DIAMOND_AXE, 1, (short) 0, 100);
            addItemToChest(k2, Material.POTION, 1, (short) 16430, 100);
            addItemToChest(k2, Material.POTION, 1, (short) 16422, 100);

        }
    }


    private static void addItemToChest(Chest chest, Material material, int amount, int probability) {
        Random random = new Random();
        if (random.nextInt(100) <= probability) {
            chest.getBlockInventory().setItem(random.nextInt(chest.getBlockInventory().getSize()), new ItemStack(material, amount));
        }
    }

    private static void addItemToChest(Chest chest, Material material, int amount, short data, int probability) {
        Random random = new Random();
        if (random.nextInt(100) <= probability) {
            chest.getBlockInventory().setItem(random.nextInt(chest.getBlockInventory().getSize()), new ItemStack(material, amount, data));
        }
    }

    public static void removeFeast() {
        for (Player p1 : Bukkit.getServer().getOnlinePlayers()) {
            ConfigurationSection list = BukkitMain.getInstance().getConfig().getConfigurationSection("MiniFeast");
            int x = (Integer) list.get("x");
            int y = (Integer) list.get("y");
            int z = (Integer) list.get("z");
            Random r = new Random();

            p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 1).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 1).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x + 2, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).setType(Material.AIR);


            p1.getLocation().getWorld().getBlockAt(x - 2, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z).setType(Material.AIR);


            p1.getLocation().getWorld().getBlockAt(x + 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z) + 1, z).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x - 1, p1.getLocation().getWorld().getHighestBlockYAt(x, z) + 1, z).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z) + 1, z + 1).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z) + 1, z - 1).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z + 2).setType(Material.AIR);
            p1.getLocation().getWorld().getBlockAt(x, p1.getLocation().getWorld().getHighestBlockYAt(x, z), z - 2).setType(Material.AIR);
        }
    }

    private static Hologram hologram;

    private static void spawnHologram(String message) {
        World world = Bukkit.getWorld("world");
        double x = 64414.329;
        double y = 68.86823;
        double z = 64447.834;
        Location hologramLocation = new Location(world, x, y, z);

        Bukkit.getScheduler().runTask(BukkitMain.getInstance(), () -> {
            if (hologram == null) {
                hologram = HologramsAPI.createHologram(BukkitMain.getInstance(), hologramLocation);
            } else {
                hologram.teleport(hologramLocation);
                hologram.clearLines();
            }
            hologram.appendTextLine(message);
        });
    }


    public static void start() {
            long delay = 0L;
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §61 minuto", delay += 1200L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §630 segundos", delay += 600L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §610 segundos", delay += 200L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §65 segundos", delay += 100L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §64 segundos", delay += 80L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §63 segundos", delay += 60L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §62 segundos", delay += 40L);
            broadcastDelayedMessage("§6§lFEAST §fO feast será spawnado em §61 segundo", delay += 20L);
            broadcastDelayedMessage("§a§lFEAST §fO feast spawnou!", delay += 20L);
            broadcastDelayedMessage("§3§lFEAST §fO feast foi resetado.", delay += 260L);
    }

    private static void broadcastDelayedMessage(String message, long delay) {
        new BukkitRunnable() {
            @Override
            public void run() {
                for (Player player : Bukkit.getOnlinePlayers()) {
                    if (player.getWorld().getName().equals("world")) {
                            player.sendMessage(message);
                            spawnHologram(message);
                        }
                    
                }
                if (message.equals("§a§lFEAST §fO feast spawnou!")) {
                    miniFeast();
                } else if (message.equals("§3§lFEAST §fO feast foi resetado.")) {
                    removeFeast();
                    start();
                }
            }
        }.runTaskLater(BukkitMain.getInstance(), delay);
    }


    private static void spawnLightningStrikes() {
        World world = Bukkit.getWorld("world");
        Location location1 = new Location(world, 64414.600, 65.00000000, 64448.353);
        Location location2 = new Location(world, getRandomCoordinate(), 100, getRandomCoordinate());
        Location location3 = new Location(world, getRandomCoordinate(), 100, getRandomCoordinate());
        Location location4 = new Location(world, getRandomCoordinate(), 100, getRandomCoordinate());
        Location location5 = new Location(world, getRandomCoordinate(), 100, getRandomCoordinate());

        world.strikeLightning(location1);
        world.strikeLightning(location2);
        world.strikeLightning(location3);
        world.strikeLightning(location4);
        world.strikeLightning(location5);
    }

    private static double getRandomCoordinate() {
        return Math.random() * 64400 - 50;
    }


}

