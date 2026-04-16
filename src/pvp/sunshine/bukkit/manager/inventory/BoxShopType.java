package pvp.sunshine.bukkit.manager.inventory;

import net.minecraft.server.v1_8_R3.EnumParticle;
import net.minecraft.server.v1_8_R3.PacketPlayOutWorldParticles;
import org.bukkit.*;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.ActionUtil;

import java.util.ArrayList;

public class BoxShopType implements Listener {

    public static void getBoxShop(Player p) {
        Inventory inv = Bukkit.createInventory(p, 27, "§8Caixa's");

        ItemStack vidroBranco = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta branco = vidroBranco.getItemMeta();
        branco.setDisplayName("§f*");
        vidroBranco.setItemMeta(branco);

        ItemStack vidroRoxo = new ItemStack(Material.STAINED_GLASS_PANE, 1, (short)10);
        ItemMeta roxo = vidroRoxo.getItemMeta();
        roxo.setDisplayName("§5*");
        vidroRoxo.setItemMeta(roxo);

        inv.setItem(0, vidroBranco);
        inv.setItem(1, vidroBranco);
        inv.setItem(2, vidroRoxo);
        inv.setItem(3, vidroRoxo);
        inv.setItem(3, vidroRoxo);
        inv.setItem(4, vidroRoxo);
        inv.setItem(5, vidroRoxo);
        inv.setItem(6, vidroRoxo);
        inv.setItem(7, vidroBranco);
        inv.setItem(8, vidroBranco);
        inv.setItem(9, vidroBranco);
        inv.setItem(10, vidroRoxo);
        inv.setItem(11, vidroRoxo);
        inv.setItem(15, vidroRoxo);
        inv.setItem(16, vidroRoxo);
        inv.setItem(17, vidroBranco);
        inv.setItem(18, vidroBranco);
        inv.setItem(19, vidroBranco);
        inv.setItem(20, vidroRoxo);
        inv.setItem(21, vidroRoxo);
        inv.setItem(22, vidroRoxo);
        inv.setItem(23, vidroRoxo);
        inv.setItem(24, vidroRoxo);
        inv.setItem(25, vidroBranco);
        inv.setItem(26, vidroBranco);

        ItemStack standard = new ItemStack(Material.ENDER_CHEST);
        ItemMeta standmeta = standard.getItemMeta();
        standmeta.setDisplayName("§3§lOMEGA");
        ArrayList<String> standdesc = new ArrayList<>();
        standdesc.add("§8Informações:");
        standdesc.add(" §7- Preço: §e2.000 coins");
        standdesc.add(" §7- Quantidade de caixas: §a1");
        standdesc.add("§7");
        standdesc.add("§d5% de chance de kit's");
        standmeta.setLore(standdesc);
        standard.setItemMeta(standmeta);
        inv.setItem(12, standard);

        ItemStack intermed = new ItemStack(Material.ENDER_CHEST);
        ItemMeta inter = intermed.getItemMeta();
        inter.setDisplayName("§e§lMEGA");
        ArrayList<String> interdesc = new ArrayList<String>();
        interdesc.add("§8Informações:");
        interdesc.add(" §7- Preço: §e3.000 coins");
        interdesc.add(" §7- Quantidade de caixas: §a3");
        interdesc.add("");
        interdesc.add("§d15% de chance de kit's");
        inter.setLore(interdesc);
        intermed.setItemMeta(inter);
        inv.setItem(13, intermed);

        ItemStack premium = new ItemStack(Material.ENDER_CHEST);
        ItemMeta premi = premium.getItemMeta();
        premi.setDisplayName("§6§lULTRA");
        ArrayList<String> premidesc = new ArrayList<>();
        premidesc.add("§8Informações:");
        premidesc.add(" §7- Preço: §e5.000 coins");
        premidesc.add(" §7- Quantidade de caixas: §a5");
        premidesc.add("");
        premidesc.add("§d30% de chance de kit's");
        premi.setLore(premidesc);
        premium.setItemMeta(premi);
        inv.setItem(14, premium);
        p.openInventory(inv);

    }

    @EventHandler(priority = EventPriority.HIGH)
    public void onInventoryClickEventWarps(InventoryClickEvent e) {
        Player p = (Player) e.getWhoClicked();
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Caixa's") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.IRON_FENCE) {
                e.setCancelled(true);
                p.closeInventory();
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§3§lOMEGA")) {
                e.setCancelled(true);
                if (SQLPvP.getCoins(p) >= 2000) {
                    SQLPvP.removeCoins(p, 2000);
                    p.sendMessage("§a§lCAIXAS §fVocê comprou a caixa §3Omega§f por §e2.000 coins!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        ActionUtil.sendActionbar(all, "§a§lCAIXAS §fO jogador §a" + p.getName() + "§f comprou a caixa §3Omega");
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            p.sendMessage("§6§lCAIXAS §fAgora você tem §61§f caixa disponível na sua conta.");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                        }
                    }, 40L);
                    BoxSystemType.adicionarCaixa(p, 1);
                    p.closeInventory();
                    PvP.update(p);
                    spawnEndermanTeleportParticles(p, 100);
                } else {
                    p.sendMessage("§c§lCAIXAS §fVocê não possui 2.000 coins para comprar a caixa §3Omega");
                }
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§e§lMEGA")) {
                e.setCancelled(true);
                if (SQLPvP.getCoins(p) >= 3000) {
                    SQLPvP.removeCoins(p, 3000);
                    p.sendMessage("§a§lCAIXAS §fVocê comprou a caixa §5Mega§f por §e3.000 coins!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        ActionUtil.sendActionbar(all, "§a§lCAIXAS §fO jogador §a" + p.getName() + "§f comprou a caixa §eMega");
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            p.sendMessage("§6§lCAIXAS §fAgora você tem §63§f caixas disponíveis na sua conta.");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                        }
                    }, 40L);

                    BoxSystemType.adicionarCaixa(p, 3);
                    p.closeInventory();
                    PvP.update(p);
                    spawnEndermanTeleportParticles(p, 100);
                } else {
                    p.sendMessage("§c§lCAIXAS §fVocê não possui 3.000 coins para comprar a caixa §eMega");
                }
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§6§lULTRA")) {
                e.setCancelled(true);
                if (SQLPvP.getCoins(p) >= 5000) {
                    SQLPvP.removeCoins(p, 5000);
                    p.sendMessage("§a§lCAIXAS §fVocê comprou a caixa §6Ultra§f por §e5.000 coins!");
                    for (Player all : Bukkit.getOnlinePlayers()) {
                        ActionUtil.sendActionbar(all, "§a§lCAIXAS §fO jogador §a" + p.getName() + "§f comprou a caixa §6Ultra");
                    }
                    Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
                        @Override
                        public void run() {
                            p.sendMessage("§6§lCAIXA §fAgora você tem §65§f caixas disponíveis na sua conta");
                            p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                        }
                    }, 40L);
                    BoxSystemType.adicionarCaixa(p, 5);
                    p.closeInventory();
                    PvP.update(p);
                    spawnEndermanTeleportParticles(p, 100);
                } else {
                    p.sendMessage("§c§lCAIXAS §fVocê não possui 5.000 coins para comprar a caixa §6Ultra");
                }
                return;
            }
        }
    }

    public static void spawnEndermanTeleportParticles(Player player, int particleCount) {
        EnumParticle particleType = EnumParticle.LAVA;
        Location location = player.getLocation();
        int count = particleCount;

        for (Player onlinePlayer : Bukkit.getOnlinePlayers()) {
            if (onlinePlayer.getWorld() == location.getWorld() && onlinePlayer.getLocation().distanceSquared(location) < 100 * 100) {
                for (int i = 0; i < count; i++) {
                    double angle = Math.random() * Math.PI * 2.0D;
                    double radius = Math.random() * 1.0D;

                    float xOffset = (float) (Math.cos(angle) * radius);
                    float yOffset = 0.0F;
                    float zOffset = (float) (Math.sin(angle) * radius);
                    PacketPlayOutWorldParticles packet = new PacketPlayOutWorldParticles(particleType, true,
                            (float) location.getX() + xOffset, (float) location.getY() + yOffset,
                            (float) location.getZ() + zOffset, 0.0F, 0.0F, 0.0F, 0.1F, 1, new int[0]);

                    (((CraftPlayer) onlinePlayer).getHandle()).playerConnection.sendPacket(packet);
                    onlinePlayer.playSound(player.getLocation(), Sound.NOTE_BASS_DRUM, 1.0F, 1.0F);
                }
            }
        }
    }
}
