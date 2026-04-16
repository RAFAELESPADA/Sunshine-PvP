package pvp.sunshine.bukkit.commands.members;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.Cooldown;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.CommandStack;
import pvp.sunshine.bukkit.commands.team.AdminCMD;
import pvp.sunshine.bukkit.commands.team.FlyCMD;
import pvp.sunshine.bukkit.manager.event.CombatLog;
import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.event.WarpFpsProtection;
import pvp.sunshine.bukkit.manager.scoreboard.Lava;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.ClearCosmeticUtil;
import pvp.sunshine.bukkit.utils.PvPUtil;
import pvp.sunshine.bukkit.utils.TitleUtil;

public class WarpCMD implements CommandExecutor, Listener {

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player deadPlayer = event.getEntity();
        Player killer = deadPlayer.getKiller();

        if (killer != null && RegisterAbility.getAbility(killer).equals("Gapple")) {
            giveItemsToPlayer(killer);
            killer.sendMessage("§a§lGAPPLE §fVocê matou um jogador na warp gapple. Seus itens foram restaurados!");
            killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
            return;
        }
        if (killer != null && RegisterAbility.getAbility(killer).equals("ComboFly")) {
            giveItemsToPlayer(killer);
            killer.sendMessage("§a§lCOMBOFLY §fVocê matou um jogador na warp ComboFly. Seus itens foram restaurados!");
            killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
        }
        if (killer != null && RegisterAbility.getAbility(killer).equals("Fps")) {
            killer.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            killer.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            killer.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            killer.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));
            killer.updateInventory();
            killer.sendMessage("§a§lARMOR §fSua armadura foi restaurada pois você matou um jogador na Fps.");
            killer.playSound(killer.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
        }
    }



    private void giveItemsToPlayer(Player player) {
        player.getInventory().clear();

        PvPUtil.getItem(player, Material.DIAMOND_HELMET, 1, "§bCapacete", 1);
        PvPUtil.getItem(player, Material.DIAMOND_CHESTPLATE, 1, "§bPeitoral", 2);
        PvPUtil.getItem(player, Material.DIAMOND_LEGGINGS, 1, "§bCalça", 3);
        PvPUtil.getItem(player, Material.DIAMOND_BOOTS, 1, "§bBotas", 4);

        player.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
        player.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
        player.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
        player.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));

        PvPUtil.getItemChant(player, Material.DIAMOND_SWORD, 1, "§bEspada", 0, Enchantment.DAMAGE_ALL, 3, true);
        PvPUtil.getEnchantedGoldenApple(player, 32, "§6Maçã Dourada", 8);
        player.updateInventory();
    }

    private static final String IN_WARP = "§c§lERRO §fVocê já está nessa warp.";

    public static void getPotion(Player p, int quantas) {
        ItemStack potion = new ItemStack(Material.POTION, 1, (short) 16421);
        ItemMeta mpotion = potion.getItemMeta();
        mpotion.setDisplayName("§aPoção de cura");
        potion.setItemMeta(mpotion);
        potion.setAmount(1);
        for (int i = 0; i < quantas; i++) {
            p.getInventory().setItem(p.getInventory().firstEmpty(), potion);
        }
    }

    public void iniciarContagemRegressiva(Player player) {
        int[] tempos = {5, 4, 3, 2, 1};

        for (int i = 0; i < tempos.length; i++) {
            int atraso = i * 20;
            agendarTarefa(player, tempos[i], atraso);
        }
    }

    private void agendarTarefa(Player player, int tempo, int atraso) {
        Bukkit.getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(), new Runnable() {
            @Override
            public void run() {
                if (tempo > 1) {
                    player.sendMessage("§a§lSPAWN §fSua proteção de spawn acaba em " + tempo + "...");
                } else {
                    player.sendMessage("§a§lSPAWN §fSua proteção acabou.");
                    Flag.setProtection(player, false);
                }
            }
        }, atraso);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String lb, String[] args) {
        if (!(sender instanceof Player)) {
            return true;
        }
        if (cmd.getName().equalsIgnoreCase("warp")) {
            if (args.length == 0) {
                sender.sendMessage(CommandStack.command("warp <warp>"));
                return true;
            }
            Player p = (Player) sender;
            if (args[0].equalsIgnoreCase("fps")) {
                if (RegisterAbility.getAbility(p).equals("Fps")) {
                    sender.sendMessage(IN_WARP);
                } else {
                    Location fpsWarp = new Location(Bukkit.getWorld("fps"), 0.00000, 68.50000000, 0.000000000, -3.2F, -7.8F);
                    p.teleport(fpsWarp);
                    FlyCMD.removeFly(p);
                    RegisterAbility.setAbility(p, "Fps");
                    PvP.update(p);
                    p.getInventory().clear();
                    p.getInventory().setArmorContents(null);
                    ClearCosmeticUtil.removeCache(p);
                    p.updateInventory();
                    p.setMaximumNoDamageTicks(20);

                    Flag.setProtection(p, true);
                    p.sendMessage("§a§lSPAWN §fVocê recebeu proteção de spawn.");
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                    TitleUtil.sendTitle(p, 20, 20, 20, "", "§b§lFPS");
                    Cooldown.remove(p);
                    p.sendMessage("§e§lWARP §fVocê foi teleportado para a warp §eFps§f");
                    CombatLog.removeCombat(p);
                    if (AdminCMD.admin.contains(p.getName())) {
                        AdminCMD.admin.remove(p.getName());
                        p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você entrou em uma warp.\n§e§lADMIN§f Agora você está visível para todos.");
                        Bukkit.getOnlinePlayers().forEach(all -> all.showPlayer(p));
                    }
                }
            } else if (args[0].equalsIgnoreCase("knockback")) {
                if (RegisterAbility.getAbility(p).equals("Knockback")) {
                    sender.sendMessage(IN_WARP);
                } else {
                    Location fpsWarp = new Location(Bukkit.getWorld("arenabuild"), 64465.500, 85.00000000000, 646222.500, -3.3F, 7.5F);
                    p.teleport(fpsWarp);
                    FlyCMD.removeFly(p);
                    PvPUtil.InventoryAdapter(p);
                    RegisterAbility.setAbility(p, "Knockback");
                    p.setMaximumNoDamageTicks(20);
                    ClearCosmeticUtil.removeCache(p);
                    PvPUtil.giveSoup(p, 35);
                    WarpFpsProtection.reiniciarContadorFps(p);
                    iniciarContagemRegressiva(p);
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                    TitleUtil.sendTitle(p, 20, 20, 20, "", "§b§lKNOCKBACK");
                    PvPUtil.getItemChant(p, Material.STICK, 1, "§a( ͡° ͜ʖ ͡°)", 0, Enchantment.KNOCKBACK, 3, true);
                    Cooldown.remove(p);
                    PvP.update(p);
                    FlyCMD.removeFly(p);
                    p.sendMessage("§e§lWARP §fVocê foi teleportado para a warp §eKnockback§f");
                    CombatLog.removeCombat(p);
                    if (AdminCMD.admin.contains(p.getName())) {
                        AdminCMD.admin.remove(p.getName());
                        p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você entrou em uma warp.\n§e§lADMIN§f Agora você está visível para todos.");
                        Bukkit.getOnlinePlayers().forEach(all -> all.showPlayer(p));
                    }
                }
            } else if (args[0].equalsIgnoreCase("gapple")) {
                if (RegisterAbility.getAbility(p).equals("Gapple")) {
                    sender.sendMessage(IN_WARP);
                } else {
                    Location fpsWarp = new Location(Bukkit.getWorld("arenabuild"), 43.423, 88.0000000, -59.00, -0.8F, -0.5F);
                    p.teleport(fpsWarp);
                    FlyCMD.removeFly(p);
                    PvPUtil.InventoryAdapter(p);
                    RegisterAbility.setAbility(p, "Gapple");
                    WarpFpsProtection.reiniciarContadorFps(p);
                    ClearCosmeticUtil.removeCache(p);
                    p.setMaximumNoDamageTicks(20);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                    TitleUtil.sendTitle(p, 20, 20, 20, "", "§b§lGAPPLE");
                    PvPUtil.getItem(p, Material.DIAMOND_HELMET, 1, "§bCapacete", 1);
                    PvPUtil.getItem(p, Material.DIAMOND_CHESTPLATE, 1, "§bPeitoral", 2);
                    PvPUtil.getItem(p, Material.DIAMOND_LEGGINGS, 1, "§bCalça", 3);
                    PvPUtil.getItem(p, Material.DIAMOND_BOOTS, 1, "§bBotas", 4);
                    p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    PvPUtil.getItemChant(p, Material.DIAMOND_SWORD, 1, "§bEspada", 0, Enchantment.DAMAGE_ALL, 5, true);
                    PvPUtil.getEnchantedGoldenApple(p, 32, "§6Maçã Dourada", 8);
                    Cooldown.remove(p);
                    PvP.update(p);
                    FlyCMD.removeFly(p);
                    p.sendMessage("§e§lWARP §fVocê foi teleportado para a warp §eGapple§f");
                    CombatLog.removeCombat(p);
                    Flag.setProtection(p, false);
                    if (AdminCMD.admin.contains(p.getName())) {
                        AdminCMD.admin.remove(p.getName());
                        p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você entrou em uma warp.\n§e§lADMIN§f Agora você está visível para todos.");
                        Bukkit.getOnlinePlayers().forEach(all -> all.showPlayer(p));
                    }
                }
            } else if (args[0].equalsIgnoreCase("combofly")) {
                if (RegisterAbility.getAbility(p).equals("ComboFly")) {
                    sender.sendMessage(IN_WARP);
                } else {
                    Location fpsWarp = new Location(Bukkit.getWorld("Gladiator"), -0.217, 135.0000000, 29.109, -176.3F, 2.5F);
                    p.teleport(fpsWarp);
                    FlyCMD.removeFly(p);
                    ClearCosmeticUtil.removeCache(p);
                    PvPUtil.InventoryAdapter(p);
                    RegisterAbility.setAbility(p, "ComboFly");
                    
                    WarpFpsProtection.reiniciarContadorFps(p);
                    p.setMaximumNoDamageTicks(5);
                    p.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, Integer.MAX_VALUE, 1, false, false));
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                    TitleUtil.sendTitle(p, 20, 20, 20, "", "§b§lCOMBOFLY");
                    PvPUtil.getItem(p, Material.DIAMOND_HELMET, 1, "§bCapacete", 1);
                    PvPUtil.getItem(p, Material.DIAMOND_CHESTPLATE, 1, "§bPeitoral", 2);
                    PvPUtil.getItem(p, Material.DIAMOND_LEGGINGS, 1, "§bCalça", 3);
                    PvPUtil.getItem(p, Material.DIAMOND_BOOTS, 1, "§bBotas", 4);
                    p.getInventory().setHelmet(new ItemStack(Material.DIAMOND_HELMET));
                    p.getInventory().setChestplate(new ItemStack(Material.DIAMOND_CHESTPLATE));
                    p.getInventory().setLeggings(new ItemStack(Material.DIAMOND_LEGGINGS));
                    p.getInventory().setBoots(new ItemStack(Material.DIAMOND_BOOTS));
                    PvPUtil.getItemChant(p, Material.DIAMOND_SWORD, 1, "§bEspada", 0, Enchantment.DAMAGE_ALL, 5, true);
                    PvPUtil.getEnchantedGoldenApple(p, 32, "§6Maçã Dourada", 8);
                    Cooldown.remove(p);
                    PvP.update(p);
                    FlyCMD.removeFly(p);
                    p.sendMessage("§e§lWARP §fVocê foi teleportado para a warp §eComboFly§f");
                    CombatLog.removeCombat(p);
                    Flag.setProtection(p, false);
                    if (AdminCMD.admin.contains(p.getName())) {
                        AdminCMD.admin.remove(p.getName());
                        p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você entrou em uma warp.\n§e§lADMIN§f Agora você está visível para todos.");
                        Bukkit.getOnlinePlayers().forEach(all -> all.showPlayer(p));
                    }
                }
            } else if (args[0].equalsIgnoreCase("Spawn")) {
                if (RegisterAbility.getAbility(p).equals("Nenhum")) {
                    sender.sendMessage("§c§lSPAWN §fVocê já está no spawn.");
                } else {

                    Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);
                    p.teleport(spawnLocation);
                    
                    PvPUtil.InventoryAdapter(p);
                    PvPUtil.spawnItem(p);
                    RegisterAbility.removeAbility(p);
                    
                    ClearCosmeticUtil.removeCache(p);
                    WarpFpsProtection.reiniciarContadorFps(p);
                    p.setMaximumNoDamageTicks(20);
                    p.sendMessage("§e§lSPAWN §fVocê foi teleportado para o spawn.");
                    FlyCMD.removeFly(p);
                    PvP.update(p);
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                    TitleUtil.sendTitle(p, 20, 20, 20, "", "§b§lSPAWN");
                    Cooldown.remove(p);
                    CombatLog.removeCombat(p);
                    Flag.setProtection(p, true);
                }
            } else if (args[0].equalsIgnoreCase("Lava")) {
                if (RegisterAbility.getAbility(p).equals("Lava")) {
                    sender.sendMessage(IN_WARP);
                } else {
                    PvPUtil.InventoryAdapter(p);
                    p.playSound(p.getLocation(), Sound.ENDERMAN_TELEPORT, 3.0f, 3.0f);
                    TitleUtil.sendTitle(p, 20, 20, 20, "", "§b§lLAVA CHALLENGE");
                    RegisterAbility.setAbility(p, "Lava");
                    PvPUtil.getPvPUtils(p);
                    Cooldown.remove(p);
                    p.setMaximumNoDamageTicks(20);
                    p.sendMessage("§e§lWARP §fVocê foi teleportado para a warp §eLava Challenge§f");
                    CombatLog.removeCombat(p);
                    WarpFpsProtection.reiniciarContadorFps(p);
                    FlyCMD.removeFly(p);
                    ClearCosmeticUtil.removeCache(p);
                    Lava.update(p);
                    Flag.setProtection(p, true);
                    p.getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                    if (AdminCMD.admin.contains(p.getName())) {
                        AdminCMD.admin.remove(p.getName());
                        p.sendMessage("§e§lADMIN §fModo admin desabilitado pois você entrou em uma warp.\n§e§lADMIN§f Agora você está visível para todos.");
                        for (Player all : Bukkit.getOnlinePlayers()) {
                            all.showPlayer(p);
                        }
                    }
                }

            } else {
                sender.sendMessage("§c§lERRO §fWarp não encontrada.");
            }
            return true;
        }
        return false;
    }
}
