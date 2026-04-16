package pvp.sunshine.bukkit.ability.register;

import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.CommandStack;
import pvp.sunshine.bukkit.commands.team.FlyCMD;
import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLShop;
import pvp.sunshine.bukkit.utils.ClearCosmeticUtil;
import pvp.sunshine.bukkit.utils.PvPUtil;
import pvp.sunshine.bukkit.utils.TitleUtil;

public class KitSelector implements CommandExecutor {

    public static void TeleportArenaRandom(final Player p) {
        switch (new Random().nextInt(5)) {
            case 0: {
                p.getPlayer().teleport(
                        new Location(Bukkit.getWorld("world"), 64407.977, 65.000, 64496.344, -97.4F, -0.6F));
                break;
            }
            case 1: {
                p.getPlayer().teleport(
                        new Location(Bukkit.getWorld("world"), 64466.928, 64.000000, 64451.891, 30.7F, -0.5F));
                break;
            }
            case 2: {
                p.getPlayer().teleport(
                        new Location(Bukkit.getWorld("world"), 64379.859, 65.00000000, 64413.534, -44.1F, 5.4F));
                break;
            }
            case 3: {
                p.getPlayer().teleport(
                        new Location(Bukkit.getWorld("world"), 64338.503, 66.000000, 64476.181, -105.5F, 4.6F));
                break;
            }
            case 4: {
                p.getPlayer().teleport(
                        new Location(Bukkit.getWorld("world"), 64350.371, 67.000000, 64533.764, -143.6F, 2.4F));
                break;
            }
        }
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (args.length == 0) {
            sender.sendMessage(CommandStack.command("kit (kit)"));
            return true;
        }
        Player p = (Player) sender;
        if (!RegisterAbility.getAbility(p).equals("Nenhum")) {
            p.sendMessage("§c§lERRO §fVocê já está utilizando um kit!");
            return true;
        }
        if (args[0].equalsIgnoreCase("pvp")) {
            if (sender.hasPermission("kit.pvp") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("pvp")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents(null);
                p.getInventory().clear();
                PvPUtil.getItemChant(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0, Enchantment.DAMAGE_ALL, 1,
                        true);
                final ItemStack Peito = new ItemStack(Material.LEATHER_CHESTPLATE);
                final LeatherArmorMeta kPeito = (LeatherArmorMeta) Peito.getItemMeta();
                kPeito.setDisplayName("§6Peitoral");
                kPeito.setColor(Color.ORANGE);
                Peito.setItemMeta((ItemMeta) kPeito);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                p.getInventory().setChestplate(Peito);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "PvP");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.updateInventory();
                p.setAllowFlight(false);
                p.setFlying(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.getInventory().setHelmet((ItemStack) null);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("fisherman")) {
            if (sender.hasPermission("kit.fisherman") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("fisherman")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.FISHING_ROD, 1, "§eVarinha", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Fisherman");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("hotpotato")) {
            if (sender.hasPermission("kit.hotpotato") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("hotpotato")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.BAKED_POTATO, 1, "§eBatatinha quente", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "HotPotato");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setMaximumNoDamageTicks(20);
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("grandpa")) {
            if (sender.hasPermission("kit.grandpa") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("grandpa")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItemChant(p, Material.STICK, 1, "§5Grandpa", 1, Enchantment.KNOCKBACK, 2, true);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Grandpa");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("urgal")) {
            if (sender.hasPermission("kit.urgal") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("urgal")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.REDSTONE, 1, "§cUrgal", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Urgal");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("anchor")) {
            if (sender.hasPermission("kit.anchor") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("anchor")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                RegisterAbility.setAbility(p, "Anchor");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("ninja")) {
            if (sender.hasPermission("kit.ninja") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("ninja")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                FlyCMD.removeFly(p);
                RegisterAbility.setAbility(p, "Ninja");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("antistomper")) {
            if (sender.hasPermission("kit.antistomper") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("antistomper")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                RegisterAbility.setAbility(p, "AntiStomper");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("boxer")) {
            if (sender.hasPermission("kit.boxer") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("boxer")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                p.setMaximumNoDamageTicks(20);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.QUARTZ, 1, "§7Boxer!", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Boxer");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("confuser")) {
            if (sender.hasPermission("kit.confuser") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("confuser")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                RegisterAbility.setAbility(p, "Confuser");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("gladiator")) {
            if (sender.hasPermission("kit.gladiator") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("gladiator")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.IRON_FENCE, 1, "§aGladiator", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Gladiator");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("kangaroo")) {
            if (sender.hasPermission("kit.kangaroo") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("kangaroo")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.FIREWORK, 1, "§cKangaroo", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Kangaroo");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("magma")) {
            if (sender.hasPermission("kit.magma") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("magma")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Magma");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("monk")) {
            if (sender.hasPermission("kit.monk") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("monk")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                FlyCMD.removeFly(p);
                p.setMaximumNoDamageTicks(20);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.BLAZE_ROD, 1, "§6Monk", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Monk");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                PvPUtil.getRecraft(p);
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("phantom")) {
            if (sender.hasPermission("kit.phantom") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("phantom")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.FEATHER, 1, "§3Phantom", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                PvPUtil.getRecraft(p);
                RegisterAbility.setAbility(p, "Phantom");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                PvPUtil.getRecraft(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                PvPUtil.getRecraft(p);
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("snail")) {
            if (sender.hasPermission("kit.snail") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("snail")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Snail");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                PvPUtil.getRecraft(p);
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("stomper")) {
            if (sender.hasPermission("kit.stomper") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("stomper")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Stomper");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("fireman")) {
            if (sender.hasPermission("kit.fireman") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("fireman")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Fireman");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("barbarian")) {
            if (sender.hasPermission("kit.barbarian") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("barbarian")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getItem(p, Material.WOOD_SWORD, 1, "§6Espada de Madeira", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Barbarian");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("switcher")) {
            if (sender.hasPermission("kit.switcher") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("switcher")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItemChant(p, Material.SNOW_BALL, 16, "§bSwitcher", 1, Enchantment.ARROW_INFINITE, 1, true);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "Switcher");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                PvPUtil.getRecraft(p);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }

        } else if (args[0].equalsIgnoreCase("thor")) {
            if (sender.hasPermission("kit.thor") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("thor")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                p.setMaximumNoDamageTicks(20);
                ClearCosmeticUtil.removeCache(p);
                FlyCMD.removeFly(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.GOLD_AXE, 1, "§eThor", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                PvPUtil.getRecraft(p);
                RegisterAbility.setAbility(p, "Thor");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("timelord")) {
            if (sender.hasPermission("kit.timelord") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("timelord")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                FlyCMD.removeFly(p);
                PvPUtil.getRecraft(p);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.WATCH, 1, "§6TimeLord", 1);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 33);
                RegisterAbility.setAbility(p, "TimeLord");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("viking")) {
            if (sender.hasPermission("kit.viking") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("viking")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                PvPUtil.getRecraft(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getItem(p, Material.WOOD_AXE, 1, "§7Viking", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Viking");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.setAllowFlight(false);
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else if (args[0].equalsIgnoreCase("viper")) {
            if (sender.hasPermission("kit.viper") || sender.hasPermission("kit.*")
                    || SQLShop.cacheshop.get(p.getName()).contains("viper")) {
                Flag.setProtection(p, false);
                p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
                p.getInventory().setArmorContents((ItemStack[]) null);
                p.getInventory().clear();
                PvPUtil.getRecraft(p);
                FlyCMD.removeFly(p);
                ClearCosmeticUtil.removeCache(p);
                p.setMaximumNoDamageTicks(20);
                PvPUtil.getItem(p, Material.STONE_SWORD, 1, "§7Espada de Pedra", 0);
                PvPUtil.getItem(p, Material.COMPASS, 1, "§eBússola", 8);
                PvPUtil.giveSoup(p, 34);
                RegisterAbility.setAbility(p, "Viper");
                TitleUtil.sendTitle(p, 30, 30, 30, "§a§l" + RegisterAbility.getAbility(p).toUpperCase(), "§fSelecionado!");
                TeleportArenaRandom(p);
                p.getInventory().setHelmet((ItemStack) null);
                p.updateInventory();
                p.sendMessage("§a§lKIT §fVocê selecionou o kit §a" + RegisterAbility.getAbility(p) + "§f!");
                p.setAllowFlight(false);
                p.setFlying(false);
            } else {
                sender.sendMessage("§c§lERRO §fVocê não possui esse kit.");
            }
        } else {
            sender.sendMessage("§c§lERRO §fEste kit não existe.");
        }
        return true;
    }

}
