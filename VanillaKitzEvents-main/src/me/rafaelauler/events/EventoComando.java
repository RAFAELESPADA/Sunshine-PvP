package me.rafaelauler.events;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.lang3.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.wavemc.core.bukkit.item.ItemBuilder;

public class EventoComando implements CommandExecutor {

	Path path1 = Paths.get(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/WaveCore/", "warps.yml");
	File file = new File(path1.toAbsolutePath().toString());
	YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
	private ItemStack machado = new ItemBuilder(Material.STONE_AXE).toStack(),
			picareta = new ItemBuilder(Material.STONE_PICKAXE).toStack(),
			pote = new ItemBuilder(Material.BOWL).amount(64).toStack(),
			espada = new ItemBuilder(Material.DIAMOND_SWORD).addEnchant(Enchantment.SHARPNESS, 1).toStack(),
			muro = new ItemBuilder(Material.COBBLESTONE_WALL).amount(64).toStack(),

			pedra = new ItemBuilder(Material.COBBLESTONE).amount(64).toStack(),
			sopa = new ItemBuilder(Material.MUSHROOM_STEW).toStack(),
			cocoa = new ItemBuilder(Material.BROWN_MUSHROOM).amount(64).toStack(),

					cocoa2 = new ItemBuilder(Material.RED_MUSHROOM).amount(64).toStack(),
			madeira = new ItemBuilder(Material.OAK_PLANKS).amount(64).toStack(),
			agua = new ItemBuilder(Material.WATER_BUCKET).toStack(),
			lava = new ItemBuilder(Material.LAVA_BUCKET).toStack(),
			capacete = new ItemBuilder(Material.IRON_HELMET).toStack(),
			peitoral = new ItemBuilder(Material.IRON_CHESTPLATE).toStack(),
			cal�a = new ItemBuilder(Material.IRON_LEGGINGS).toStack(),
			bota = new ItemBuilder(Material.IRON_BOOTS).toStack();
	public static List<Location> blocksV = new ArrayList<>();
			private Integer[] soupSlots = { 4, 5, 6, 7, 29, 30, 31, 32, 33, 34, 35};
			private Integer[] cocoaSlots = { 14, 15, 16};
			private Integer[] cocoaSlots2 = { 23, 24, 25 };
	public static HashMap<String, ItemStack[]> saveinv = new HashMap();
    private static void sendHelp(Player player) {
        if (player.hasPermission("kombo.cmd.evento")) {
            player.sendMessage("�a�lKOMBO �7- �eEvents");
            player.sendMessage(" ");
            player.sendMessage("�e/event �7- �fDisplay this help page.");
            player.sendMessage("�e/event join �7- �fJoin the event.");
            player.sendMessage("�e/event leave �7- �fLeave the event.");
            player.sendMessage("�e/event spec �7- �fSpectate the event.");
            player.sendMessage(" ");
            player.sendMessage("�e/event tpto �7- �fTeleport players to event.");
            
            player.sendMessage("�e/event build �7- �fLet players build in the event.");
            player.sendMessage("�e/event cleararena �7- �fClear event arena.");
            player.sendMessage("�e/event damage �7- �fAlter the damage (except pvp).");
            player.sendMessage("�e/event effect <effect/clear> <amplifier> <seconds> <player/all>�7- �fGive potion effects to the event players.");
            player.sendMessage("�e/event kick <player> �7- �fKick a player from the event.");
            player.sendMessage("�e/event players �7- �fList all players in the event.");
            player.sendMessage("�e/event pvp �7- �fAlter the pvp.");
            player.sendMessage("�e/event setspecloc �7- �fSet the spectators spawn location.");
            player.sendMessage("�e/event setspawnloc �7- �fSet the event spawn location.");
            player.sendMessage("�e/event setquit �7- �fSet the event quit location.");
            player.sendMessage("�e/event skit <player/all> �7- �fAlter the event kit.");
            player.sendMessage("�e/event specs �7- �fEnable/Disable spectators.");
            player.sendMessage("�e/event start �7- �fStart the event.");
            player.sendMessage("�e/event stop �7- �fStops the event.");
            player.sendMessage("�e/event toggle �7- �fEnable/Disable the join of players in the event.");
            player.sendMessage("�e/event tpall �7- �fTeleport everyone in the event to you.");
            player.sendMessage("�e/event whitelist <add/remove/list> <player> �7- �fLet a individual player join the event in whitelist.");
            player.sendMessage(" ");
        } else {
            player.sendMessage("�a�lKOMBO �7- �eSistema de eventos");
            player.sendMessage(" ");
            player.sendMessage("�eCRIADO POR RAFAEL AULER EM 27/07/2024");
            player.sendMessage(" ");
        }
    }
    
    public static void darItemEnchant(final Player p, final Material mat, final int quantidade, final String nome, final int lugar, final Enchantment enchant, final int level, final boolean trueorfalse) {
        final ItemStack item = new ItemStack(mat, quantidade);
        final ItemMeta itemmeta = item.getItemMeta();
        itemmeta.addEnchant(enchant, level, trueorfalse);
        itemmeta.setDisplayName(nome);
        item.setItemMeta(itemmeta);
        p.getInventory().setItem(lugar, item);
    }
    
    public static void ClearPlayer(final Player jogador) {
        jogador.setExp(0.0f);
        jogador.setExhaustion(20.0f);
        jogador.setFireTicks(0);
        jogador.setFoodLevel(20000);
        jogador.setHealth(20.0);
        jogador.getInventory().clear();
        jogador.getInventory().setArmorContents((ItemStack[])null);
        for (final PotionEffect potion : jogador.getActivePotionEffects()) {
            jogador.removePotionEffect(potion.getType());
        }
    }
    

    
    public static void cogu(final Player p) {
        final ItemStack verleho = new ItemStack(Material.RED_MUSHROOM, 64);
        final ItemMeta mverleho = verleho.getItemMeta();
        mverleho.setDisplayName("�cCogumelo");
        verleho.setItemMeta(mverleho);
        final ItemStack BROWN = new ItemStack(Material.BROWN_MUSHROOM, 64);
        final ItemMeta mBROWN = BROWN.getItemMeta();
        mBROWN.setDisplayName("�6Cogumelo");
        BROWN.setItemMeta(mBROWN);
        final ItemStack pot = new ItemStack(Material.BOWL, 64);
        final ItemMeta mpot = pot.getItemMeta();
        mpot.setDisplayName("�7Pote");
        pot.setItemMeta(mpot);
        p.getInventory().setItem(13, verleho);
        p.getInventory().setItem(14, BROWN);
        p.getInventory().setItem(15, pot);
    }
    

    

    
    public static void sopa(final Player p) {
        final ItemStack espada = new ItemStack(Material.STONE_SWORD);
        final ItemMeta mespada = espada.getItemMeta();
        mespada.setDisplayName("�6Espada");
        espada.setItemMeta(mespada);
        p.getInventory().setItem(0, espada);
    }
    
    public static void SetarItens(final Player p, final Material mat, final int quantidade, final String nome, final int lugar) {
        final ItemStack item = new ItemStack(mat, quantidade);
        final ItemMeta itemmeta = item.getItemMeta();
        itemmeta.setDisplayName(nome);
        item.setItemMeta(itemmeta);
        p.getInventory().setItem(lugar, item);
    }


    public void Gladiator(Player player) {
		player.setGameMode(GameMode.SURVIVAL);
		
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);

		player.getInventory().setItem(0, espada);
		player.getInventory().setItem(1, agua);
		player.getInventory().setItem(2, lava);
		player.getInventory().setItem(3, madeira);
		player.getInventory().setItem(8, muro);
		player.getInventory().setItem(9, pedra);
		player.getInventory().setItem(10, pedra);
		player.getInventory().setItem(11, pedra);
		player.getInventory().setItem(12, pedra);
		player.getInventory().setItem(17, machado);
		player.getInventory().setItem(18, capacete);
		player.getInventory().setItem(19, peitoral);
		player.getInventory().setItem(20, cal�a);
		player.getInventory().setItem(21, bota);
		player.getInventory().setItem(26, picareta);
		player.getInventory().setItem(27, lava);
		player.getInventory().setItem(28, lava);

		player.getInventory().setHelmet(capacete);
		player.getInventory().setChestplate(peitoral);
		player.getInventory().setLeggings(cal�a);
		player.getInventory().setBoots(bota);
		
		player.getInventory().setItem(13, pote);
		player.getInventory().setItem(22, pote);
		
		for (Integer slot : soupSlots) {
			 player.getInventory().setItem(slot, sopa);
		}
		
		for (Integer slot : cocoaSlots) {
		 	 player.getInventory().setItem(slot, cocoa);
		}
		for (Integer slot : cocoaSlots2) {
		 	 player.getInventory().setItem(slot, cocoa2);
		}
		player.updateInventory();
	}


    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only Players");
            return true;
        }
        Player player = (Player) sender;
        if (command.getName().equalsIgnoreCase("event")) {
            if (args.length == 0) {
                sendHelp(player);
                return false;
            }

                switch (args[0].toLowerCase()) {    
    case "start": 
                    if (EventoUtils.evento) {
                        player.sendMessage("�cThe event room is already open.");
                        return true;
                    }
                    
                    if (!player.hasPermission("cmd.event")) {
                    	player.sendMessage("NO ACESS");
                    	return true;
                    }
                    EventoUtils.evento = true;
                    player.sendMessage("�aVoc� abriu a sala de eventos.");
                    EventoUtils.whitelist.add(player.getUniqueId());
                   
                    for (Player p : Bukkit.getOnlinePlayers()) {
                    	p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 10f, 1f);
                    }
                    player.setGameMode(GameMode.CREATIVE);
                break;
                
    case "explicar":
        if (args.length < 2) {
            player.sendMessage("�cEscolha um evento para explicar");
            return true;
        }
        EventType evento = EventType.getEventoByName(args[1]);
        if (evento == null) {
            player.sendMessage("�cOp��o de evento inv�lida.");
            return true;
        }
        player.sendMessage("�aIniciando explica��o do evento �e" + evento.getName().toUpperCase() + "�a...");
        EventType.explicarEvento(evento);
        break;
                case "stop": 
                    if (!EventoUtils.evento) {
                        player.sendMessage("�cA sala de eventos est� fechada.");
                        return true;
                    }
                    if (!player.hasPermission("cmd.event")) {
                    	player.sendMessage("NO ACESS");
                    	return true;
                    }
                    EventoUtils.evento = false;
                    player.sendMessage("�aVoc� fechou a sala de eventos.");
                    EventoUtils.getEventoPlayers().forEach(p -> {
                        p.sendMessage("�cO evento foi finalizado.");
                       
                       WaveWarp.SPAWN.send(player);
                       player.sendMessage("�aPlayers que sobraram do evento: �e" + EventoUtils.getEventoPlayers());
                       WaveWarp.SPAWN.send(p);
                       p.getLocation().getWorld().strikeLightning(p.getLocation());

                       p.getLocation().getWorld().strikeLightning(p.getLocation());
                       p.getLocation().getWorld().strikeLightning(p.getLocation());

                       p.getLocation().getWorld().strikeLightning(p.getLocation());
                       p.getLocation().getWorld().strikeLightning(p.getLocation());
                       player.getInventory().clear();
                       player.getInventory().setContents(saveinv.get(player.getName()));
                       player.teleport(EventoUtils.quitLoc);
                       EventoUtils.setEvento(false, player);
                       player.sendMessage("�cO evento foi finalizado.");
                        p.getActivePotionEffects().forEach(ef -> p.removePotionEffect(ef.getType()));
                    });
                    EventoUtils.resetEventoClass();
                break;
             
                        case "spec":
                            if (EventoUtils.game.contains(player.getName())) {
                                player.sendMessage("�cYou are in the event.");
                                return true;
                            }
                            if (!EventoUtils.specs) {
                                player.sendMessage("�cSpectators are disabled.");
                                return true;
                            }
                            player.teleport(EventoUtils.specLoc);
                            player.sendMessage("�aYou are now spectating the event.");
                            break;
                        case "build":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (EventoUtils.build) {
                                player.sendMessage("�cYou disabled the build.");
                                EventoUtils.build = false;
                            } else {
                                player.sendMessage("�aYou enabled the build.");
                                EventoUtils.build = true;
                            }
                            break;
                        case "cleararena":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            EventoUtils.clearBlocks();
                            player.sendMessage("�aYou cleared the arena");
                            break;
                        case "tpto":
                            if (args.length < 2) {
                                player.sendMessage("�cEscolha algum evento para teleportar.");
                                return true;
                            }
                            EventType ev = EventType.getEventoByName(args[1]);
                            if (ev == null) {
                                player.sendMessage("�cOp��o de evento inv�lida.");
                                return true;
                            }
                            EventoUtils.started = true;
                            WaveWarp.valueOf(ev.getName()).send(player);
                            break;
                        case "damage":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (EventoUtils.damage) {
                                player.sendMessage("�cYou disabled the damage. �7(Remember to disable the �4pvp�7)");
                                EventoUtils.damage = false;
                            } else {
                                player.sendMessage("�aYou enabled the damage. �7(Remember to enable the �4pvp�7)");
                                EventoUtils.damage = true;
                            }
                            break;
                        case "effect":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (args.length == 2) {
                                if (args[1].equalsIgnoreCase("clear")) {
                                    EventoUtils.getEventoPlayers().forEach(p -> p.getActivePotionEffects().forEach(ef -> p.removePotionEffect(ef.getType())));
                                    player.sendMessage("�aYou cleared the effect of all players.");
                                    return false;
                                }
                                else player.sendMessage("�cUse /event effect <effect/clear> <amplifier> <seconds> <player/all>");
                                return false;
                            }
                            if (args.length < 5) {
                                sendHelp(player);
                                return true;
                            }
                            PotionEffectType potionEffectType = EventoUtils.getPotionEffectTypeByName(args[1]);
                            if (potionEffectType == null) {
                                player.sendMessage("�cInvalid effect.");
                                return true;
                            }
                            int amplif;
                            int secs;
                            try {
                                amplif = Integer.parseInt(args[2]);
                                secs = Integer.parseInt(args[3]);
                            } catch (NumberFormatException exception) {
                                sendHelp(player);
                                return true;
                            }
                            if (args[4].equalsIgnoreCase("all")) {
                                EventoUtils.getEventoPlayers().forEach(p -> p.addPotionEffect(new PotionEffect(potionEffectType, secs * 20, amplif - 1)));
                                player.sendMessage("�aEffect �e"+potionEffectType.getName() + " " + amplif + " �aapplied for �e" + secs + " seconds�a.");
                                return false;
                            } else {
                                Player target = Bukkit.getPlayer(args[4]);
                                if (target == null) {
                                    player.sendMessage("�cCannot find player �e" + args[4] + "�c.");
                                    return true;
                                }
                                if (target == player) {
                                    player.sendMessage("�cYou cannot give the effects to yourself.");
                                }
                                if (!EventoUtils.game.contains(target.getName())) {
                                    player.sendMessage("�cThis player is not on event.");
                                    return true;
                                }
                                target.addPotionEffect(new PotionEffect(potionEffectType, secs * 20, amplif));
                                player.sendMessage("�aEffect �e"+potionEffectType.getName() + " " + amplif + " �aapplied to �e" + target.getName() + " �afor �e" + secs + " seconds�a.");
                            }
                            break;
                        case "kick":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (args.length < 2) {
                                sendHelp(player);
                                return true;
                            }
                            Player target = Bukkit.getPlayer(args[1]);
                            if (target == null) {
                                player.sendMessage("�cCannot find the player �e" + args[1] + "�c.");
                                return true;
                            }
                            if (target == player) {
                                player.sendMessage("�cDont kick yourself.");
                                return true;
                            }
                            if (!EventoUtils.game.contains(target.getName())) {
                                player.sendMessage("�cThis player is not on the event.");
                                return true;
                            }
                           EventoUtils.setEvento(false, player);
                            target.sendMessage("�cYou get kicked out of the event.");
                            player.sendMessage("�aYou kicked �e" + target.getName() + " �aof the event.");
                            break;
                        case "players":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            int size = EventoUtils.getEventoPlayersNames().size();
                            player.sendMessage("�aThe event has �e" + size + " players�a, they are: �7" + StringUtils.join(EventoUtils.getEventoPlayersNames(), "�a, �7"));
                            break;
                        case "pvp":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (EventoUtils.pvp) {
                                player.sendMessage("�cYou disabled the pvp. �7(Remember to disable the �4damage�7)");
                                EventoUtils.pvp = false;
                            } else {
                                player.sendMessage("�aYou enabled the pvp. �7(Remember to enable the �4damage�7)");
                                EventoUtils.pvp = true;
                            }
                            break;
                        case "sumofight":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                        	ArrayList<String> allPlayers = new ArrayList<String>();
                        	
                            for (Player p1 : Bukkit.getOnlinePlayers()) {
                            	if (WaveWarp.Sumo.hasPlayer(p1.getName())) {
                            		allPlayers.add(p1.getName());
                            		int random = new Random().nextInt(allPlayers.size());
                            	    String picked = allPlayers.get(random);
                            	    String picked2 = allPlayers.get(random);
                            		if (picked != picked2) {

                            			Location l = new Location(Bukkit.getWorld(yaml.getString("Mundo-sumoloc1")), yaml.getInt("X-sumoloc1") , yaml.getInt("Y-sumoloc1"), yaml.getInt("Z-sumoloc1"));
                            			l.setPitch(yaml.getInt("PITCH-sumoloc1"));
                            			l.setYaw(yaml.getInt("YAW-sumoloc1"));	

                            			Location l2 = new Location(Bukkit.getWorld(yaml.getString("Mundo-sumoloc2")), yaml.getInt("X-sumoloc2") , yaml.getInt("Y-sumoloc2"), yaml.getInt("Z-sumoloc2"));
                            			l2.setPitch(yaml.getInt("PITCH-sumoloc2"));
                            			l2.setYaw(yaml.getInt("YAW-sumoloc2"));	
                            			Player objeto1 = Bukkit.getPlayerExact(picked);

                            			Player objeto2 = Bukkit.getPlayerExact(picked2);
                            			objeto2.teleport(l2);
                            			objeto1.teleport(l);
                            			player.sendMessage("PUXANDO DOIS PLAYERS PARA BATALHA SUMO!");
                            		}
                            	}
                            }
                            
                            break;     
                        case "1v1fight":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                        	ArrayList<String> allPlayers2 = new ArrayList<String>();
                        	
                            for (Player p1 : Bukkit.getOnlinePlayers()) {
                            	if (WaveWarp.ONE_VS_ONE.hasPlayer(p1.getName())) {
                            		allPlayers2.add(p1.getName());
                            		int random = new Random().nextInt(allPlayers2.size());
                            	    String picked = allPlayers2.get(random);
                            	    String picked2 = allPlayers2.get(random);
                            		if (picked != picked2) {

                            			Location l = new Location(Bukkit.getWorld(yaml.getString("Mundo-1v1loc1")), yaml.getInt("X-1v1loc1") , yaml.getInt("Y-1v1loc1"), yaml.getInt("Z-1v1loc1"));
                            			l.setPitch(yaml.getInt("PITCH-1v1loc1"));
                            			l.setYaw(yaml.getInt("YAW-1v1loc1"));	

                            			Location l2 = new Location(Bukkit.getWorld(yaml.getString("Mundo-1v1loc2")), yaml.getInt("X-1v1loc2") , yaml.getInt("Y-1v1loc2"), yaml.getInt("Z-1v1loc2"));
                            			l2.setPitch(yaml.getInt("PITCH-1v1loc2"));
                            			l2.setYaw(yaml.getInt("YAW-1v1loc2"));	
                            			Player objeto1 = Bukkit.getPlayerExact(picked);

                            			Player objeto2 = Bukkit.getPlayerExact(picked2);
                            			objeto2.teleport(l2);
                            			objeto1.teleport(l);
                            			player.sendMessage("PUXANDO DOIS PLAYERS PARA BATALHA 1v1!");
                            		}
                            	}
                            }
                            break;
                        case "setspecloc":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            EventoUtils.specLoc = player.getLocation();
                            player.sendMessage("�aSpectator location set.");
                            break;
                        case "setspawn":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            EventoUtils.spawnLoc = player.getLocation();
                            player.sendMessage("�aSpawn location set.");
                            break;
                        case "setquit":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            EventoUtils.quitLoc = player.getLocation();
                            player.sendMessage("�aQuit location set.");
                            break;
                        case "skit":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (args.length < 2) {
                                sendHelp(player);
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("all")) {
                                EventoUtils.getEventoPlayers().forEach(p -> {
                                    if (p == player) return;
                                    p.closeInventory();
                                    p.getInventory().setArmorContents(player.getInventory().getArmorContents());
                                    p.getInventory().setContents(player.getInventory().getContents());
                                    p.sendMessage("�aYou receive the event kits.");
                                });
                                player.sendMessage("�aAll players received the kit.");
                                return false;
                            }
                            Player t = Bukkit.getPlayer(args[1]);
                            if (t == null) {
                                player.sendMessage("�cWe cant find �e" + args[1] + "�c.");
                                return true;
                            }
                            t.closeInventory();
                            t.getInventory().setArmorContents(player.getInventory().getArmorContents());
                            t.getInventory().setContents(player.getInventory().getContents());
                            t.sendMessage("�aYou received the event kit.");
                            player.sendMessage("�aThe player �e" + t.getName() + " �areceived the kit.");
                            break;
                        case "specs":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (!EventoUtils.specs) {
                                if (EventoUtils.specLoc == null) {
                                    player.sendMessage("�cSet spectator location spawn first.");
                                    return true;
                                }
                                EventoUtils.specs = true;
                                player.sendMessage("�aYou enabled the spectators.");
                            } else {
                                EventoUtils.specs = false;
                                player.sendMessage("�cYou disabled the spectators.");
                            }
                            break;
                        case "toggle":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (!EventoUtils.tp) {
                                player.sendMessage("�aYou enabled new players event join.");
                                EventoUtils.tp = true;
                            } else {
                                player.sendMessage("�cYou disabled new players event join.");
                                EventoUtils.tp = false;
                            }
                            break;
                        case "tpall":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            EventoUtils.getEventoPlayers().forEach(p -> p.teleport(player.getLocation()));
                            player.sendMessage("�aYou teleported everyone to you.");
                            break;
                        case "whitelist":
                        	if (!player.hasPermission("cmd.event")) {
                            	player.sendMessage("NO ACESS");
                            	return true;
                            }
                            if (args.length < 3) {
                                if (args[1].equalsIgnoreCase("list")) {
                                    player.sendMessage("�aPlayers in event whitelist: �7" + StringUtils.join(EventoUtils.getWhitelistPlayersNames(), "�a, �7"));
                                    return false;
                                }
                                sendHelp(player);
                                return true;
                            }
                            Player tt = Bukkit.getPlayer(args[2]);
                            if (tt == null) {
                                player.sendMessage("�cWe cant find �e" + args[2] + "�c.");
                                return true;
                            }
                            if (args[1].equalsIgnoreCase("add")) {
                                if (EventoUtils.whitelist.contains(tt.getUniqueId())) {
                                    player.sendMessage("�cPlayer �e" + tt.getName() + " �cis already on whitelist.");
                                    return true;
                                }
                                EventoUtils.whitelist.add(tt.getUniqueId());
                                player.sendMessage("�aPlayer �e" + tt.getName() + " �agets added on event whitelist.");
                                return true;
                            } else if (args[1].equalsIgnoreCase("remove")) {
                                if (!EventoUtils.whitelist.contains(tt.getUniqueId())) {
                                    player.sendMessage("�cPlayer �e" + tt.getName() + " �cis not on whitelist.");
                                    return true;
                                }
                                EventoUtils.whitelist.remove(tt.getUniqueId());
                                player.sendMessage("�aO player �e" + tt.getName() + " �afoi �cremovido �ada whitelist.");
                                return false;
                            } else {
                                player.sendMessage("�cN�o encontramos essa op��o.");
                                return false;
                               
                            }
                            
                    }
            
                }
   
            
        
        return false;
    }
}