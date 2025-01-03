package net.kombopvp.pvp.command;


import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.material.Dye;

import net.kombopvp.pvp.KomboPvP2;
import net.kombopvp.pvp.listener.EventoType;
import net.kombopvp.pvp.listener.EventoUtils;
import net.kombopvp.pvp.warp.WaveWarp2;
import net.wavemc.core.bukkit.item.ItemBuilder;

public class Arena
implements CommandExecutor, Listener
{
public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args)
{
  Player p = (Player)sender;
  if (label.equalsIgnoreCase("arenainiciar"))
  {
	  if (!p.hasPermission("kombo.cmd.evento")) {
		  p.sendMessage("§cVocê não tem permissão!");
		  return true;
	  }
	  else if (EventoUtils.evento) {
		  p.sendMessage("A event is already occouring!");
		  return true;
	  }
	  p.sendMessage("§aStarting arena event"); 
	  EventoUtils.evento = true;
	  EventoUtils.tp = true;
	  Bukkit.broadcastMessage("§cO evento arena vai começar em breve.");
      Bukkit.broadcastMessage("§cEscreva /evento join para entrar");
  	Bukkit.broadcastMessage("§cO evento arena ira iniciar em 5 minutos");
      for (Player p1 : Bukkit.getOnlinePlayers()) {
      	p1.playSound(p1.getLocation(), Sound.LEVEL_UP, 1f, 1f);
      }
      Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				  if (!EventoUtils.evento) {
					  return;
				  }
					Bukkit.broadcastMessage("§cO evento arena ira iniciar em 4 minutos");
				Bukkit.broadcastMessage("§cPlayers no evento: " + EventoUtils.getEventoPlayers().size());
				 for (Player p1 : Bukkit.getOnlinePlayers()) {
				      	p1.playSound(p1.getLocation(), Sound.LEVEL_UP, 1f, 1f);
				      }
			}
		}, 1200L);
      Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				if (!EventoUtils.evento) {
					  return;
				  }
				Bukkit.broadcastMessage("§cO evento arena ira iniciar em 3 minutos");
				Bukkit.broadcastMessage("§cPlayers no evento: " + EventoUtils.getEventoPlayers().size());
				 for (Player p1 : Bukkit.getOnlinePlayers()) {
				      	p1.playSound(p1.getLocation(), Sound.LEVEL_UP, 1f, 1f);
				      }
			}
		}, 2400L);
      Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				if (!EventoUtils.evento) {
					  return;
				  }
				Bukkit.broadcastMessage("§cO evento arena ira iniciar em 2 minutos");
				Bukkit.broadcastMessage("§cPlayers no evento: " + EventoUtils.getEventoPlayers().size());
			}
		}, 3600L);
      Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				if (!EventoUtils.evento) {
					  return;
				  }
				Bukkit.broadcastMessage("§cO evento arena ira iniciar em 1 minuto");
				Bukkit.broadcastMessage("§cPlayers no evento: " + EventoUtils.getEventoPlayers().size());
			}
		}, 4800L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				if (!EventoUtils.evento) {
					  return;
				  }
				 EventoType ev = EventoType.getEventoByName("ArenaPvP");
			 	 Location evt = ev.getLocation();
			 	 EventoUtils.getEventoPlayers().forEach(p2 -> {
                 	p2.teleport(evt);
			      for (Player p1 : Bukkit.getOnlinePlayers()) {
			        	p1.playSound(p1.getLocation(), Sound.LEVEL_UP, 1f, 1f);
			        }
			});
			 	Bukkit.broadcastMessage("§cTeleportando todos para o evento!");
			 	EventoType evento = EventoType.getEventoByName("ArenaPvP");
			    Bukkit.broadcastMessage("§aComeçando a explicação do evento §e" + evento.getName().toUpperCase() + "§a...");
                EventoType.explicarEvento(evento);
                EventoUtils.started = true;
			}}, 6000L);
		Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
			public void run() {
				if (!EventoUtils.evento) {
					  return;
				  }
				Bukkit.broadcastMessage("§c§lO evento começou!");
				Bukkit.broadcastMessage("§c§lBoa sorte!");
				EventoUtils.getEventoPlayers().forEach(p2 -> {
		EventoUtils.pvp = true;
		p2.playSound(p2.getLocation(), Sound.PISTON_EXTEND, 1f, 1f);
     	EventoUtils.damage = true;
     	EventoUtils.build = true;
     	EventoUtils.tp = false;
     	ItemStack helmet = new ItemStack(Material.IRON_HELMET);
		ItemStack chest = new ItemStack(Material.IRON_CHESTPLATE);
		ItemStack leg = new ItemStack(Material.IRON_LEGGINGS);
		ItemStack boost = new ItemStack(Material.IRON_BOOTS);
    		
    		
    		p2.getInventory().setHeldItemSlot(0);
    		/* 348 */       
    		p2.getInventory().setItem(0, new ItemBuilder("§7Sword", Material.DIAMOND_SWORD).addEnchant(Enchantment.DAMAGE_ALL, 1)
    				.nbt("cancel-drop")
    				.toStack()
    		);
    		p2.getInventory().setHelmet(helmet);
    		p2.getInventory().setChestplate(chest);
    		p2.getInventory().setLeggings(leg);
    		p2.getInventory().setBoots(boost);
    		p2.getInventory().setItem(9, helmet);
    		p2.getInventory().setItem(10, chest);
    		p2.getInventory().setItem(11, leg);
    		p2.getInventory().setItem(12, boost);
    		p2.getInventory().setItem(18, helmet);
    		p2.getInventory().setItem(19, chest);
    		p2.getInventory().setItem(20, leg);
    		p2.getInventory().setItem(21, boost);
    		Dye d = new Dye();
    	    d.setColor(DyeColor.BROWN);
    	    ItemStack di = d.toItemStack();
    	    di.setAmount(64);
    		p2.getInventory().setItem(13, new ItemStack(Material.BOWL, 64));
    		p2.getInventory().setItem(22, new ItemStack(Material.BOWL, 64));
    		p2.getInventory().setItem(14, di);
    		p2.getInventory().setItem(23, di);
    		p2.getInventory().setItem(15, new ItemStack(Material.WATER_BUCKET, 1));
    		p2.getInventory().setItem(24, new ItemStack(Material.LAVA_BUCKET, 1));
    		p2.getInventory().setItem(3, new ItemStack(Material.WATER_BUCKET, 1));
    		p2.getInventory().setItem(4, new ItemStack(Material.LAVA_BUCKET, 1));
    		p2.getInventory().setItem(16, new ItemStack(Material.COBBLE_WALL, 64));
    		p2.getInventory().setItem(2, new ItemStack(Material.WOOD, 64));
    		p2.getInventory().setItem(8, new ItemStack(Material.WEB, 8));
    		p2.getInventory().setItem(17, new ItemStack(Material.STONE_PICKAXE, 1));
    		p2.getInventory().setItem(26, new ItemStack(Material.STONE_AXE, 1));
    		p2.getInventory().setItem(10, chest);
    		p2.getInventory().setItem(11, leg);
    		p2.getInventory().setItem(12, boost);
    		p2.getInventory().setItem(18, helmet);
    		p2.getInventory().setItem(19, chest);
    		p2.getInventory().setItem(20, leg);
    		p2.getInventory().setItem(21, boost);
    		p2.setGameMode(GameMode.SURVIVAL);
    		for (int i = 0; i < 17; i++) {
    			p2.getInventory().addItem(new ItemStack(Material.MUSHROOM_SOUP));    			
    		}
    		p2.updateInventory();
			}); {
			}}}, 7200L);
	}
  Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
		public void run() {
			if (!EventoUtils.evento || !EventoUtils.started) {
				  return;
			  }
			EventoUtils.getEventoPlayers().forEach(p ->  {
				if (p != null && EventoUtils.getEventoPlayers().size() != 0) {
            	EventoUtils.setEvento(false, p);
                WaveWarp2.SPAWN.send(p);
                p.chat("/spawn");
                p.getActivePotionEffects().forEach(ef -> p.removePotionEffect(ef.getType()));
                
                if (EventoUtils.getEventoPlayers().size() == 1) {
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	Bukkit.broadcastMessage("§aEvent winner: " + EventoUtils.getEventoPlayersNames());
                	p.setHealth(20);
                	p.getWorld().strikeLightning(p.getLocation());
            		p.getWorld().strikeLightning(p.getLocation());
            		p.getWorld().strikeLightning(p.getLocation());
            		p.getWorld().strikeLightning(p.getLocation());
                }
                p.sendMessage("§cThe event ended because the timer runs out.");
                p.sendMessage("§cEvent duration: §a40 minutes");
			}
            });;
            EventoUtils.resetEventoClass();
		}}, 48000L);
  
return false;
      };
      {

}
      @EventHandler(priority=EventPriority.HIGHEST, ignoreCancelled=false)
      public void aAntiSpam(AsyncPlayerChatEvent e) {
      if (EventoUtils.started && EventoUtils.game.contains(e.getPlayer().getName()) && !e.getPlayer().hasPermission("kombo.cmd.report")) {
    	  e.getPlayer().sendMessage("§cVocê está em um evento e não pode falar participando dele.");
    	  e.setCancelled(true);
      }
      }
      
      }
