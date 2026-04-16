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
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLShop;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.ActionUtil;

import java.util.ArrayList;
import java.util.List;

public class PurchaseType implements Listener {

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
                    onlinePlayer.playSound(player.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
                }
            }
        }
    }


    public static List<Player> accept = new ArrayList<>();

    public void Purchase(String kit, Player p, int pricedefault) {
        if (SQLPvP.getCoins(p) < pricedefault) {
            p.sendMessage("§c§lSHOP §fVocê não possui coins o suficiente para realizar essa compra.");
            return;
        }
        SQLPvP.removeCoins((Player) p, pricedefault);
        SQLShop.addkit((Player) p, kit);
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp user " + p.getName() + " permission set kit." + kit + " server=pvp");
        p.sendMessage("§a§lSHOP §fParabéns! você acabou de efetuar a compra do kit §a" + kit + " §fpor §e" + pricedefault + " coins§f!\n§a§lSHOP §fSua compra foi realizada com exito.");
        accept.remove(p);
        ShopAbilityType.shopkit.remove(p.getName());
        p.closeInventory();
        spawnEndermanTeleportParticles(p, 100);
        PvP.update(p);
        for (Player all : Bukkit.getOnlinePlayers()) {
            ActionUtil.sendActionbar(all, "§a§lSHOP §fO jogador §a" + p.getName() + "§f comprou o kit §a" + kit + "§f!");
        }
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                p.sendMessage("§6§lSHOP §fAgora você tem §61§f kit disponível em sua conta. Abra o menu de kits para visualizar!");
                p.playSound(p.getLocation(), Sound.NOTE_PLING, 1.0F, 1.0F);
            }
        }, 40L);
    }

    public static void ConfirmShop(Player p) {
        Inventory inv = Bukkit.createInventory(p, InventoryType.HOPPER, "§8Check-out");

        ItemStack confirm = new ItemStack(Material.WOOL);
        confirm.setDurability((short) 5);
        ItemMeta confirmr = confirm.getItemMeta();
        confirmr.setDisplayName("§aConfirmar");
        confirm.setItemMeta(confirmr);
        inv.addItem(new ItemStack[] { confirm });

        ItemStack desconfirm = new ItemStack(Material.WOOL);
        ItemMeta desconfirmr = desconfirm.getItemMeta();
        desconfirm.setDurability((short) 14);
        desconfirmr.setDisplayName("§cCancelar");
        desconfirm.setItemMeta(desconfirmr);
        inv.addItem(new ItemStack[] { desconfirm });

        ItemStack vidro = new ItemStack(Material.STAINED_GLASS_PANE);
        ItemMeta vidroo = vidro.getItemMeta();
        vidroo.setDisplayName("§f*");
        vidro.setItemMeta(vidroo);

        inv.setItem(0, vidro);
        inv.setItem(2, vidro);
        inv.setItem(1, confirm);
        inv.setItem(3, desconfirm);
        inv.setItem(4, vidro);
        accept.add(p);
        p.openInventory(inv);
        p.sendMessage("§e§lSHOP §fVocê tem apenas 10 segundos para efetuar sua compra. Caso o contrário o seu pedido será cancelado!");
        new BukkitRunnable() {
            public void run() {
                if(accept.contains(p)) {
                    p.sendMessage("§c§lSHOP §fSeu pedido foi cancelado pois não houve reação.");
                    p.closeInventory();
                    accept.remove(p);
                    ShopAbilityType.shopkit.remove(p.getName());
                }
            }
        }.runTaskLater(BukkitMain.getInstance(), 20 * 10);
    }

    @SuppressWarnings({ "deprecation" })
    @EventHandler
    public void onPlayerCLickInventry(InventoryClickEvent e) {
        if (e.getInventory().getTitle().equalsIgnoreCase("§8Check-out") && e.getCurrentItem() != null
                && e.getCurrentItem().getTypeId() != 0) {
            e.setCancelled(true);
            if (e.getCurrentItem().getType() == Material.STAINED_GLASS_PANE) {
                e.getWhoClicked().closeInventory();
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCancelar")) {
                accept.remove(e.getWhoClicked());
                ShopAbilityType.shopkit.remove(e.getWhoClicked().getName());
                e.getWhoClicked().sendMessage("§c§lSHOP §fA sua compra foi cancelada.");
                e.getWhoClicked().closeInventory();
                return;
            }
            if (e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aConfirmar")) {
                if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Magma")) {
                    Purchase("Magma", (Player) e.getWhoClicked(), 5000);
                } else {
                    if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Anchor")) {
                        Purchase("Anchor", (Player) e.getWhoClicked(), 4000);
                    } else {
                        if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("AntiStomper")) {
                            Purchase("AntiStomper", (Player) e.getWhoClicked(), 8000);
                        } else {
                            if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Barbarian")) {
                                Purchase("Barbarian", (Player) e.getWhoClicked(), 12000);
                            } else {
                                if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Boxer")) {
                                    Purchase("Boxer", (Player) e.getWhoClicked(), 3500);
                                } else {
                                    if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Confuser")) {
                                        Purchase("Confuser", (Player) e.getWhoClicked(), 3000);
                                    } else {
                                        if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Fireman")) {
                                            Purchase("Fireman", (Player) e.getWhoClicked(), 3000);
                                        } else {
                                            if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Fisherman")) {
                                                Purchase("Fisherman", (Player) e.getWhoClicked(), 7200);
                                            } else {
                                                if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Gladiator")) {
                                                    Purchase("Gladiator", (Player) e.getWhoClicked(), 15000);
                                                } else {
                                                    if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("HotPotato")) {
                                                        Purchase("HotPotato", (Player) e.getWhoClicked(), 20000);
                                                        } else {
                                                            if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Monk")) {
                                                                Purchase("Monk", (Player) e.getWhoClicked(), 9000);
                                                            } else {
                                                                if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Ninja")) {
                                                                    Purchase("Ninja", (Player) e.getWhoClicked(), 10000);
                                                                } else {
                                                                    if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Phantom")) {
                                                                        Purchase("Phantom", (Player) e.getWhoClicked(), 7000);
                                                                    } else {
                                                                        if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Snail")) {
                                                                            Purchase("Snail", (Player) e.getWhoClicked(), 6500);
                                                                        } else {
                                                                            if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Stomper")) {
                                                                                Purchase("Stomper", (Player) e.getWhoClicked(), 9000);
                                                                            } else {
                                                                                if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Switcher")) {
                                                                                    Purchase("Switcher", (Player) e.getWhoClicked(), 2000);
                                                                                } else {
                                                                                    if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Thor")) {
                                                                                        Purchase("Thor", (Player) e.getWhoClicked(), 1100);
                                                                                    } else {
                                                                                        if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("TimeLord")) {
                                                                                            Purchase("TimeLord", (Player) e.getWhoClicked(), 10000);
                                                                                        } else {
                                                                                            if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Urgal")) {
                                                                                                Purchase("Urgal", (Player) e.getWhoClicked(), 13000);
                                                                                            } else {
                                                                                                if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Viking")) {
                                                                                                    Purchase("Viking", (Player) e.getWhoClicked(), 9000);
                                                                                                } else {
                                                                                                    if (ShopAbilityType.shopkit.get(e.getWhoClicked().getName()).equalsIgnoreCase("Viper")) {
                                                                                                        Purchase("Viper", (Player) e.getWhoClicked(), 1150);
                                                                                                }
                                                                                            }
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }

}
