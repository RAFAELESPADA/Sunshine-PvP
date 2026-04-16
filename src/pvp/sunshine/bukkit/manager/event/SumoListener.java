package pvp.sunshine.bukkit.manager.event;

import java.util.ArrayList;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;

import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.api.KillStreakAPI;
import pvp.sunshine.bukkit.commands.members.ClanCMD;
import pvp.sunshine.bukkit.commands.team.FlyCMD;
import pvp.sunshine.bukkit.manager.inventory.WarpType;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopKills;
import pvp.sunshine.bukkit.manager.mysql.connections.holograms.TopLoses;
import pvp.sunshine.bukkit.manager.scoreboard.PvP;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class SumoListener implements Listener {

    @EventHandler
    public void onBlockCommandsOnParty(PlayerCommandPreprocessEvent e) {
        if (WarpType.Sumo.containsKey(e.getPlayer().getName())) {
            e.getPlayer().sendMessage("§c§lERRO §fVocê não pode usar comandos em uma batalha.");
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onPlayerKick(PlayerKickEvent e) {
        if (WarpType.SumoRandom == e.getPlayer().getUniqueId()) {
            WarpType.SumoRandom = null;
        }
        quitPlayer(e.getPlayer());
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent event) {
        Location to = event.getTo();
        if (to.getBlock().getType() == Material.WATER || to.getBlock().getType() == Material.STATIONARY_WATER) {
            if (WarpType.Sumo.containsKey(event.getPlayer().getName())) {
                deathSumo(event.getPlayer());
            } else {
                if (WarpType.Sumo.containsKey(event.getPlayer().getName())) {
Player p = event.getPlayer();

Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);  p.teleport(spawnLocation);
                }
            }
        }
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        e.setQuitMessage(null);
        if (WarpType.SumoRandom == e.getPlayer().getUniqueId()) {
            WarpType.SumoRandom = null;
        }
        quitPlayer(e.getPlayer());
    }

    @EventHandler
    public void danosSumo(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player) {
            if (WarpType.Sumo.containsKey(e.getEntity().getName())) {
                e.setDamage(0);
            }
           
            if (e.getEntity() instanceof Player) {
            	if (!WarpType.inWarp((Player)e.getDamager())) {
                    
            if (!CombatLog.emcombate.containsKey((Player)e.getEntity())) {
            	CombatLog.emcombate.put((Player)e.getEntity(), (Player)e.getDamager());
            	HelixActionBar.send((Player)e.getDamager(), ChatColor.RED + "Você entrou em combate com o jogador " + e.getEntity().getName());

            	HelixActionBar.send((Player)e.getEntity(), ChatColor.RED + "Você entrou em combate com o jogador " + e.getDamager().getName());
            	new BukkitRunnable() {	
            		public void run() {	
            			HelixActionBar.send((Player)e.getEntity(), ChatColor.GREEN + "Você está fora de combate");
                    	
            			CombatLog.removeCombat((Player)e.getEntity());
            		}}.runTaskLater(BukkitMain.getInstance(), 20 * 15l);
            		
            }
            	}
            }
            Player d = (Player)e.getDamager();
            if (!WarpType.inWarp(d)) {
            	if (e.getEntity() instanceof Player) {
            	Player e2 = (Player)e.getEntity();
            	HelixActionBar.send(d, ChatColor.GREEN + "Jogador: " + ChatColor.YELLOW  + e2.getName() +   ChatColor.GREEN +" Kit: " + ChatColor.YELLOW + RegisterAbility.getAbility(e2) +  ChatColor.GREEN +"  Vida: " + ChatColor.RED + e2.getHealth());
            }
            }
        }
    }

    @EventHandler
    public void onBlockedFoodLevel(FoodLevelChangeEvent e) {
        if (WarpType.Sumo.containsKey(e.getEntity().getName())) {
            e.setCancelled(true);
        }
    }

    public static void removePlayer(Player p) {
        WarpType.Sumo.remove(p.getName());
    }

    public static void quitPlayer(Player p) {
        if (ClanCMD.convite.containsKey(p)) {
            ClanCMD.convite.remove(p);
        }
        Player target;
        if (WarpType.Sumo.containsKey(p.getName())) {
            target = Bukkit.getPlayer(WarpType.Sumo.get(p.getName()));
            target.sendMessage("§a§lSUMO §fVocê venceu a batalha contra §a" + p.getName());
            target.sendMessage("§6(+5 XP)");
            target.playSound(target.getLocation(), Sound.ANVIL_LAND, 5.0f, 1.0f);
            new BukkitRunnable() {	
        		public void run() {	
        		
            SQLPvP.addKills(target);
            SQLPvP.addDeaths(p);
            KillStreakAPI.addStreak(target);
            KillStreakAPI.removeStreak(p);
            SQLRank.addXp(target, 5);
            SQLRank.removeXP(p, 3);
            TopKills.incrementKills(target);
            TopLoses.incrementLoses(p);
        		}}.runTaskAsynchronously(BukkitMain.getInstance());
            BLOCK_COMMAND.remove(p.getName());
            BLOCK_COMMAND.remove(target.getName());
            WarpType.Sumo.remove(target.getName());
            WarpType.Sumo.remove(p.getName());


            Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);target.teleport(spawnLocation);
            target.getInventory().setArmorContents(null);
            target.getInventory().clear();
            target.setFoodLevel(20);
            target.setHealth(20);
            target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));
            PvPUtil.spawnItem(target);
            PvP.update(target);
            for (Player todos : Bukkit.getOnlinePlayers()) {
                todos.showPlayer(target);
                target.showPlayer(todos);
            }
            return;
        }
    }

    public static void deathSumo(Player p) {
        Player target = Bukkit.getPlayer(WarpType.Sumo.get(p.getName()));
        Flag.setProtection(p, true);
        Flag.setProtection(target, true);
        target.sendMessage("§a§lSUMO §fVocê venceu a batalha contra §a" + p.getName());
        target.sendMessage("§6(+8 XP)");
        target.playSound(target.getLocation(), Sound.ANVIL_LAND, 5.0f, 1.0f);

        p.sendMessage("§c§lSUMO §fVocê foi derrotado por §c" + WarpType.Sumo.get(p.getName()));

        p.playSound(p.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
        for (Player todos : Bukkit.getOnlinePlayers()) {
            p.showPlayer(todos);
            target.showPlayer(todos);
        }
        TopKills.incrementKills(target);
        TopLoses.incrementLoses(p);
        p.showPlayer(target);
        BLOCK_COMMAND.remove(p.getName());
        BLOCK_COMMAND.remove(target.getName());
        target.showPlayer(p);
        KillStreakAPI.addStreak(target);
        KillStreakAPI.removeStreak(p);
        SQLPvP.addKills(target);
        SQLPvP.addKills(p);
        SQLRank.addXp(target, 8);
        SQLRank.removeXP(p, 5);
        PvP.update(p);
        PvP.update(target);
        p.getInventory().setArmorContents(null);
        p.getInventory().clear();
        p.setFoodLevel(20);
        p.setHealth(20);


        Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000); p.teleport(spawnLocation);
       
        target.getInventory().setArmorContents(null);
        target.getInventory().clear();
        target.setFoodLevel(20);
        target.setHealth(20);
        target.teleport(spawnLocation);
        target.getActivePotionEffects().forEach(potionEffect -> target.removePotionEffect(potionEffect.getType()));

        PvPUtil.spawnItem(target);
        PvPUtil.spawnItem(p);
        WarpType.Sumo.remove(target.getName());
        WarpType.Sumo.remove(p.getName());

    }

    public static ArrayList<String> BLOCK_COMMAND = new ArrayList<>();

    @EventHandler
    public void blockCommandFila(PlayerCommandPreprocessEvent e) {
        if (BLOCK_COMMAND.contains(e.getPlayer().getName())) {
            e.getPlayer().sendMessage("§c§lERRO §fVocê não pode usar comandos enquanto está em fila.");
            e.setCancelled(true);
            return;
        }
    }

    public static void APISearch(Player p) {
        BLOCK_COMMAND.add(p.getName());
        p.getInventory().setArmorContents(null);
        p.getInventory().clear();
        ItemStack redstone = new ItemStack(Material.REDSTONE);
        ItemMeta redstonee = redstone.getItemMeta();
        redstonee.setDisplayName("§aSair da Fila");
        redstone.setItemMeta(redstonee);
        redstone.setAmount(1);
        p.getInventory().setItem(4, redstone);
        p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
    }

    public static void teleportPlayer(Player p) {
        p.setMaxHealth(20.0D);
        p.setHealth(20.0D);
        p.setMaximumNoDamageTicks(20);
        p.getInventory().clear();
        p.getInventory().setArmorContents(null);
        p.getActivePotionEffects().clear();
        p.updateInventory();
        Flag.setProtection(p, false);
        FlyCMD.removeFly(p);
    }

}
