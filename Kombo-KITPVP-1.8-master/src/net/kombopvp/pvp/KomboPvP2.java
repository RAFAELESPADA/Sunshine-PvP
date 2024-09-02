package net.kombopvp.pvp;


import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.EnumSet;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.inventory.EnchantingInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;


import eu.decentsoftware.holograms.api.DHAPI;
import eu.decentsoftware.holograms.api.holograms.Hologram;
import me.neznamy.tab.api.TabAPI;
import me.rafaelauler.ss.TagCommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.audit.ActionType;
import net.dv8tion.jda.api.audit.AuditLogEntry;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.guild.member.GuildMemberRemoveEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.DefaultMemberPermissions;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.requests.restaction.CommandListUpdateAction;

import net.kombopvp.kit2.GladiatorListener;
import net.kombopvp.pvp.command.ActionBar;
import net.kombopvp.pvp.command.Admin;
import net.kombopvp.pvp.command.Aplicar;
import net.kombopvp.pvp.command.Arena;
import net.kombopvp.pvp.command.AutoSoup;
import net.kombopvp.pvp.command.AvisoT;
import net.kombopvp.pvp.command.ClearCMD;
import net.kombopvp.pvp.command.DarKit;
import net.kombopvp.pvp.command.DuelJoin;
import net.kombopvp.pvp.command.EuforiaB2;
import net.kombopvp.pvp.command.Fly;
import net.kombopvp.pvp.command.GamemodeCMD;
import net.kombopvp.pvp.command.GiveCoins;
import net.kombopvp.pvp.command.GiveDeaths;
import net.kombopvp.pvp.command.GiveKills;
import net.kombopvp.pvp.command.GiveXP;
import net.kombopvp.pvp.command.GladInfo;
import net.kombopvp.pvp.command.Head;
import net.kombopvp.pvp.command.Info;
import net.kombopvp.pvp.command.Invsee;
import net.kombopvp.pvp.command.KITPVP;
import net.kombopvp.pvp.command.LastLogin;
import net.kombopvp.pvp.command.LavaIniciar;
import net.kombopvp.pvp.command.Lobby;
import net.kombopvp.pvp.command.MacroTest;
import net.kombopvp.pvp.command.Money;
import net.kombopvp.pvp.command.NoBreakEvent;
import net.kombopvp.pvp.command.OneVsOneIniciar2;
import net.kombopvp.pvp.command.PvP;
import net.kombopvp.pvp.command.RankCMD;
import net.kombopvp.pvp.command.RemoveKit;
import net.kombopvp.pvp.command.RemoverGlads;
import net.kombopvp.pvp.command.Report;
import net.kombopvp.pvp.command.ResetKDR;
import net.kombopvp.pvp.command.ScoreboardCMD;
import net.kombopvp.pvp.command.SendCredits;
import net.kombopvp.pvp.command.ServerTimerEvent2;
import net.kombopvp.pvp.command.SetArena;
import net.kombopvp.pvp.command.SetFeast;
import net.kombopvp.pvp.command.SetHologramCMD;
import net.kombopvp.pvp.command.SetWarp;
import net.kombopvp.pvp.command.SkitCMD;
import net.kombopvp.pvp.command.SpawnCMD;
import net.kombopvp.pvp.command.TPALL;
import net.kombopvp.pvp.command.TPHere;
import net.kombopvp.pvp.command.Vanish;
import net.kombopvp.pvp.command.VerRank;
import net.kombopvp.pvp.command.WarpJoin;
import net.kombopvp.pvp.cooldown2.UpdateScheduler;
import net.kombopvp.pvp.inventory.KitDiario;
import net.kombopvp.pvp.inventory.KitDiarioRunner;
import net.kombopvp.pvp.inventory.Modo;
import net.kombopvp.pvp.inventory.Servidores;
import net.kombopvp.pvp.inventory.StatusGUI;
import net.kombopvp.pvp.inventory.listener.BuyKitListener;
import net.kombopvp.pvp.inventory.listener.Lapis;
import net.kombopvp.pvp.inventory.listener.SelectKitListener;
import net.kombopvp.pvp.inventory.listener.SoupTypeGUI;
import net.kombopvp.pvp.kit.provider.Boxer;
import net.kombopvp.pvp.kit.provider.Flash;
import net.kombopvp.pvp.kit.provider.Grappler;
import net.kombopvp.pvp.kit.provider.Jumper;
import net.kombopvp.pvp.listener.ArenaBuild;
import net.kombopvp.pvp.listener.Cocoa;
import net.kombopvp.pvp.listener.EntityCalculateDamageListener;
import net.kombopvp.pvp.listener.EventoComando;
import net.kombopvp.pvp.listener.EventoListeners;
import net.kombopvp.pvp.listener.EventoTabComplete;
import net.kombopvp.pvp.listener.EventoUtils;
import net.kombopvp.pvp.listener.Jump;
import net.kombopvp.pvp.listener.LAVA;
import net.kombopvp.pvp.listener.OnevsOneKS;
import net.kombopvp.pvp.listener.OpenSpawnItemsListener;
import net.kombopvp.pvp.listener.PlayerCombatLogListener;
import net.kombopvp.pvp.listener.PlayerCompassListener;
import net.kombopvp.pvp.listener.PlayerDeathListener;
import net.kombopvp.pvp.listener.PlayerDieArenaListener;
import net.kombopvp.pvp.listener.PlayerJoin;
import net.kombopvp.pvp.listener.PlayerKillstreakListener;
import net.kombopvp.pvp.listener.PlayerQuitListener;
import net.kombopvp.pvp.listener.PotePlaca;
import net.kombopvp.pvp.listener.RDMAutomatic;
import net.kombopvp.pvp.listener.RecraftGeral;
import net.kombopvp.pvp.listener.ServerEssentialsListener;
import net.kombopvp.pvp.listener.SignListener;
import net.kombopvp.pvp.listener.SopaBugada;
import net.kombopvp.pvp.scoreboard.ScoreboardBuilder;
import net.kombopvp.warp.provider.Gladiator;
import net.kombopvp.warp.provider.OneVsOne;
import net.kombopvp.warp.provider.Sumo;
import net.luckperms.api.LuckPerms;
import net.wavemc.core.bukkit.WaveBukkit;
import net.wavemc.core.bukkit.account.WavePlayer;
import net.wavemc.core.bukkit.file.WaveFile;
import net.wavemc.core.bukkit.warp.WaveWarp;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import us.ajg0702.leaderboards.LeaderboardPlugin;

public class KomboPvP2 extends JavaPlugin implements Listener, PluginMessageListener {
	
	public static KomboPvP2 getInstance() {
		return getPlugin(KomboPvP2.class);
	}
	public static KomboPvP2 getInstance2() {
		return instance;
	}
	public static boolean euforia;
	public JDABuilder bot;
	  
	public final String TOKEN = getConfig().getString("TOKEN");


	public ArrayList<EnchantingInventory> inventories;
	private ScoreboardBuilder scoreboardBuilder;
	private Hologram topPlayersHd;
	 private EventManager eventmanager;
	Path path1 = Paths.get(Bukkit.getServer().getWorldContainer().getAbsolutePath() + "/plugins/WaveCore/", "warps.yml");
	File file = new File(path1.toAbsolutePath().toString());
	YamlConfiguration yaml = YamlConfiguration.loadConfiguration(file);
	
	private static KomboPvP2 instance;
	 

	 protected String getIpLocalHost() {
	        try {
	            return (new BufferedReader(new InputStreamReader((new URL("http://checkip.amazonaws.com")).openStream())))
	                    .readLine();
	        } catch (IOException e) {
	            e.printStackTrace();
	            return null;
	        }
	    }
	 private void startUpdating() {

		    Bukkit.getServer().getScheduler().runTaskTimer(getInstance(), new UpdateScheduler(), 20, 20);
		  
		}
	 
	 private void setupRecipes() {
		 ShapelessRecipe cocoa = new ShapelessRecipe(
	                new ItemStack(Material.MUSHROOM_SOUP));
	        cocoa.addIngredient(1, Material.INK_SACK, (short)3);
	        cocoa.addIngredient(1, Material.BOWL);
	        Bukkit.getServer().addRecipe(cocoa);
	    }

	 public EventManager getEventManager() {
		    return this.eventmanager;
		  }

     
	public void onEnable() {
		FeastAPI.start();
		JDA jda = JDABuilder.createLight(TOKEN, EnumSet.of(GatewayIntent.GUILD_MESSAGES, GatewayIntent.MESSAGE_CONTENT)).addEventListeners(new SlashBott()).addEventListeners(new SlashBot2())
	      .build();
		 CommandListUpdateAction commands = jda.updateCommands();
       
		  // Add all your commands on this action instance
		  commands.addCommands(

net.dv8tion.jda.api.interactions.commands.build.Commands.slash("serverinfo", "Veja informações do discord").setDefaultPermissions(DefaultMemberPermissions.ENABLED));
		  net.dv8tion.jda.api.interactions.commands.build.Commands.slash("ping", "PONG!").setDefaultPermissions(DefaultMemberPermissions.ENABLED);

		  commands.addCommands(
				  net.dv8tion.jda.api.interactions.commands.build.Commands.slash("status", "Veja status de kitpvp de um jogador")
		                .addOption(OptionType.STRING, "nick", "Nick do jogador", true) // you can add required options like this too
		                 
		      .setDefaultPermissions(DefaultMemberPermissions.ENABLED)) // only admins should be able to use this command.
		  ;
		  commands.addCommands(
				  net.dv8tion.jda.api.interactions.commands.build.Commands.slash("anunciar", "Mande um anúncio pelo bot")
		                .addOption(OptionType.STRING, "mensagem", "Mensagem a ser enviada", true).addOption(OptionType.CHANNEL, "canal", "Canal a ser enviado a mensagem", true) // you can add required options like this too
		                 
		      .setDefaultPermissions(DefaultMemberPermissions.DISABLED)) // only admins should be able to use this command.
		  ;

		  // Then finally send your commands to discord using the API
		  commands.queue();
		  
	
		Bukkit.getConsoleSender().sendMessage("CAMINHO DO ARQUIVO DE WARPS -> " + path1.toAbsolutePath().toString());
	Bukkit.getServer().getConsoleSender().sendMessage("MUNDO DO LOBBY -> " + yaml.getString("Mundo-lobby"));
		instance = this;
		 startUpdating();
		    	 Bukkit.getMessenger().registerIncomingPluginChannel(this, "BungeeCord", this);
		    		Bukkit.getMessenger().registerOutgoingPluginChannel(this, "BungeeCord");
		this.scoreboardBuilder = new ScoreboardBuilder(this);
		  (getInstance()).eventmanager = new EventManager();
		new KitDiarioRunner();
		loadCommands();
		ScoreboardBuilder.init();
		loadListeners();

new BukkitRunnable() {
			
			@Override
			public void run() {
				if (euforia) {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (net.kombopvp.pvp.warp.WaveWarp2.SPAWN.hasPlayer(player.getName()) || net.kombopvp.pvp.warp.WaveWarp2.FPS.hasPlayer(player.getName()) && !EventoUtils.game.contains(player.getName())) {
					player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120*20, 0));
						}
						else {
							 player.getActivePotionEffects().forEach(potion -> player.removePotionEffect(potion.getType()));	
					
						}
			
			} 
				}}}.runTaskTimer(this, 0, 3 * 20L);
		new BukkitRunnable() {
			
			@Override
			public void run() {
				if (!euforia) {
				Bukkit.getWorlds().forEach(world -> world.setTime(1000));
			}
				else {

					Bukkit.getWorlds().forEach(world -> world.setTime(18000));
				}
			}
		}.runTaskTimer(this, 0, 5 * 20L);
WaveBukkit.getExecutorService().submit(() -> {
			
			new BukkitRunnable() {

				
				@Override
				public void run() {
					for (Player player : Bukkit.getOnlinePlayers()) {
						if (Bukkit.getOnlinePlayers().size() < 3) {
							return;
						}
player.sendTitle("§4§lFÚRIA", "§cTodos ficam fortes");

Bukkit.getWorlds().forEach(world -> world.setTime(18000));
						player.addPotionEffect(new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 120*20, 0));
						player.playSound(player.getLocation(), Sound.ANVIL_BREAK, 1F, 10F);
						euforia = true;
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "lp group default permission settemp wave.kit2.* true 2m");
					    Bukkit.getScheduler().scheduleSyncDelayedTask(KomboPvP2.getInstance(), new Runnable() {
								public void run() {
									if (!euforia) {
										  return;
									  }
									
									Bukkit.broadcastMessage("§aO evento fúria acabou!");
									euforia = false;

									Bukkit.getWorlds().forEach(world -> world.setTime(1000));
									 for (Player p1 : Bukkit.getOnlinePlayers()) {
										 p1.playSound(p1.getLocation(), Sound.LEVEL_UP, 1f, 1f);
									        p1.getActivePotionEffects().forEach(potion -> p1.removePotionEffect(potion.getType()));
									      }
								}
							}, 2400L);

						Bukkit.broadcastMessage("§cO evento fúria foi iniciado");
						Bukkit.broadcastMessage("§cPor dois minutos todos serão fortes");
						Bukkit.broadcastMessage("§cE o server estará de noite"); 
					}
					
				}}.runTaskTimer(this, 0, 22 * 60 * 20L);
		});
		
		WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@SuppressWarnings("deprecation")
				@Override
				public void run() {


	
Location l3 = new Location(Bukkit.getWorld(yaml.getString("Mundo-arenabuild")), yaml.getInt("X-arenabuild") , yaml.getInt("Y-arenabuild"), yaml.getInt("Z-arenabuild"));

for (Player p : l3.getWorld().getPlayers()) {
	if (p == null) {
		for (Location l : ArenaBuild.blocks) {
			l.getBlock().setType(Material.AIR);
		}
		return;
	}
	if (ArenaBuild.placed_blocks.get(p) == null) {
			return;
		}
for (Block b : ArenaBuild.placed_blocks.get(p)) {
	if (p == null) {
		return;
	}
	  b.setType(Material.AIR);
}
for (Location l : ArenaBuild.blocks) {
	l.getBlock().setType(Material.AIR);
}
}
net.kombopvp.pvp.listener.RTP.broadcast(ChatColor.RED + "A arena BUILD está sendo resetada automaticamente", Bukkit.getWorld(yaml.getString("Mundo-arenabuild")));

}}.runTaskTimer(KomboPvP2.getInstance(), 1, 300 * 20L);
		});
	

		this.inventories = new ArrayList<>();
		 // check if Citizens is present and enabled.
	      if (getServer().getPluginManager().getPlugin("Citizens") == null
	              || !getServer().getPluginManager().getPlugin("Citizens").isEnabled()) {
	          getLogger().log(Level.SEVERE, "Citizens 2.0 not found or not enabled");
	      } 
		setupRecipes();
		Bukkit.getConsoleSender().sendMessage("RECEITAS CARREGADAS (CACAU)");





	Bukkit.getConsoleSender().sendMessage("EVENTO DO /ADMIN CARREGADO");
		

		
		WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.getWorlds().forEach(world -> {
						world.getEntities().stream().filter(entity -> entity instanceof Item)
						.forEach(en -> en.remove());
					});
					}}.runTaskTimer(this, 0, 30 * 20L);
		});
		WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.getWorlds().forEach(world ->  {
						if (world.equals(Bukkit.getWorld(yaml.getString("Mundo-lavachallenge")))) {
						 world.getEntities().stream().filter(entity -> entity instanceof Item)
						.forEach(en -> en.remove());
					}});
					
					}}.runTaskTimer(this, 0, 1 * 1L);
		});


		WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					for (WavePlayer w : WaveBukkit.getInstance().getPlayerManager().getPlayers()) {
					Player p = Bukkit.getPlayer(w.getName());
					if (p == null) {
						return;
					}
					if (!p.isOnline()) {
						net.kombopvp.pvp.warp.WaveWarp2.removeHandle(p.getName());
					}
					}
					
					}}.runTaskTimer(this, 0, 1 * 1L);
		});
		
		WaveBukkit.getExecutorService().submit(() -> {
			new BukkitRunnable() {
				@Override
				public void run() {
					Bukkit.getWorlds().forEach(world -> {
						world.getEntities().stream().filter(entity -> entity instanceof Item && entity.getLocation().getX() > 654100 && entity.getLocation().getX() < 654150)
						.forEach(en -> en.remove());
					
						
					    
					});
					}}.runTaskTimer(this, 0, 1 * 5L);
					new BukkitRunnable() {
						
						@Override
						public void run() {
							for (Player p : Bukkit.getOnlinePlayers()) {
								if (EventoUtils.game.contains(p.getName()) && RDMAutomatic.iniciou) {
									if (!getInstance().getEventManager().getRdmAutomatic().players.contains(p)) {
							getInstance().getEventManager().getRdmAutomatic().putInEvent2(p);
									}
							else if (!EventoUtils.game.contains(p.getName()) && RDMAutomatic.iniciou) {
								getInstance().getEventManager().getRdmAutomatic().removeFromEvent(p);
							}
						}
								}
						}}
					.runTaskTimer(this, 0, 1 * 20L);
		});
		

		
		
	
		new BukkitRunnable() {

			private long tick;
			private long tick2;

			@Override
			public void run() {
				getServer().getPluginManager().callEvent(new net.kombopvp.pvp.command.ServerTimerEvent(++tick));
				getServer().getPluginManager().callEvent(new ServerTimerEvent2(++tick2));
			}
		}.runTaskTimer(this, 1, 1);
		new net.kombopvp.pvp.papi.PlaceHolderAPIHook(this).register();
		loadTopPlayersHologram();
		Bukkit.getConsoleSender().sendMessage("§a§lWavePVP: §fPlugin habilitado! §a[v" + getDescription().getVersion() + "]");
		Bukkit.getConsoleSender().sendMessage("§a§lWavePVP: §fCriado por §a[v" + getDescription().getAuthors() + "]");
		
	}
	public String color(String s) {
		s = ChatColor.translateAlternateColorCodes('&', s);
		return s;
	}
	public static void handleTopPlayers(Location location) {
		Plugin lb = Bukkit.getPluginManager().getPlugin("ajLeaderboards");
		if (lb == null || !lb.isEnabled() || !(lb instanceof LeaderboardPlugin)) {
			Bukkit.getLogger().severe("AjLeaderBoards not found! TopKills will not work.");
			return;
		}
		Plugin dc = Bukkit.getPluginManager().getPlugin("DecentHolograms");
		if (dc == null) {
			Bukkit.getLogger().severe("DecentHolograms not found! TopKills will not work.");
			return;
		}
		LeaderboardPlugin ajlb = (LeaderboardPlugin) lb;
		if (!ajlb.getCache().createBoard("wavepvp_player_kills")) {
			Bukkit.getLogger().severe("TopKills Creation Failed. Aborting");
			return;
		}
		

			String header = "§e§lTop 15 players §a(KILLS)";	
				List<String> lines = Arrays.asList(header,
					"§61"  + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_1_alltime_name%" +
					" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_1_alltime_value%", "§62" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_2_alltime_name%" +
							" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_2_alltime_value%", "§63" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_3_alltime_name%" +
									" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_3_alltime_value%", "§64" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_4_alltime_name%" +
											" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_4_alltime_value%", "§65" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_5_alltime_name%" +
													" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_5_alltime_value%", "§66" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_6_alltime_name%" +
															" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_6_alltime_value%", "§67" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_7_alltime_name%" +
																	" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_7_alltime_value%", "§68" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_8_alltime_name%" +
																			" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_8_alltime_value%", "§69" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_9_alltime_name%" +
																					" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_9_alltime_value%", "§610" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_10_alltime_name%" +
																							" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_10_alltime_value%", "§611" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_11_alltime_name%" +
																									" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_11_alltime_value%", "§612" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_12_alltime_name%" +
																											" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_12_alltime_value%", "§613" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_13_alltime_name%" +
																													" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_13_alltime_value%", "§614" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_14_alltime_name%" +
																															" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_14_alltime_value%", "§615" + "§ " + "§e" + "%ajlb_lb_helixpvp2_player_kills_15_alltime_name%" +
																																	" §fKills: §6" + "%ajlb_lb_helixpvp2_player_kills_15_alltime_value%");
			
			
				

			Hologram hologram = DHAPI.getHologram("topkills");
			if (hologram == null) {
			 Hologram holo = DHAPI.createHologram("topkills", location, true, lines);
		 holo.updateAll();
				}
			
			 DHAPI.updateHologram("topkills");
			}
			
			
			
				
	
	
	private void loadTopPlayersHologram() {
		new BukkitRunnable() {
			@Override
			public void run() {
				Optional<WaveWarp> warpOptional;
				if (!(warpOptional = WaveBukkit.getInstance().getWarpManager().findWarp("top-players")).isPresent()) {
					return;
				}
				
				Location location = warpOptional.get().getLocation();
				handleTopPlayers(location);
			}
		}.runTaskTimer(KomboPvP2.getInstance(), 10 * 20L, 2 * (60 * 20L));
	}
	
	public void onDisable() {
		for (Player p : Bukkit.getOnlinePlayers()) {
			for (Block b : ArenaBuild.placed_blocks.get(p)) {
				  b.setType(Material.AIR);
			}
			for (Location l : ArenaBuild.blocks) {
				l.getBlock().setType(Material.AIR);
			}
			p.kickPlayer("§cServidor está reiniciando.");
		}
		
		for (Player p : Bukkit.getOnlinePlayers()) {
			if (GladiatorListener.combateGlad.containsKey(p)) {
			for (final Location loc : GladiatorListener.blocks.get(p.getName())) {
				
			Bukkit.getConsoleSender().sendMessage("[KITPVP] Removendo Glad de " + p.getName());
	            loc.getBlock().setType(Material.AIR);
	        }
			if (net.kombopvp.kit2.GladiatorListener.combateGlad.containsKey(p)) {
				for (final Location loc : net.kombopvp.kit2.GladiatorListener.blocks.get(p.getName())) {
					Bukkit.getConsoleSender().sendMessage("[KITPVP] Removendo Glad Secundário de " + p.getName());				
		            loc.getBlock().setType(Material.AIR);
		        }

		
		for (EnchantingInventory ei : this.inventories)
		      ei.setItem(1, null); 
		    this.inventories = null;
			Bukkit.getConsoleSender().sendMessage("§a§lPVP: §fPlugin desabilitado! §a[v" + getDescription().getVersion() + "]");
		  }
		}
	}
		
	}
	
	
	
	public void loadCommands() {

		getCommand("arenainiciar").setExecutor(new Arena());
		getCommand("spawn").setExecutor(new SpawnCMD());
		getCommand("skit").setExecutor(new SkitCMD());
		getCommand("sethologram").setExecutor(new SetHologramCMD());
		getCommand("scoreboard").setExecutor(new ScoreboardCMD(this));
		getCommand("rank").setExecutor(new RankCMD());
		getCommand("givecoins").setExecutor(new GiveCoins());
		getCommand("warpjoin").setExecutor(new WarpJoin());
		getCommand("givekills").setExecutor(new GiveKills());
		getCommand("setfeast").setExecutor(new SetFeast());
		getCommand("furia").setExecutor(new EuforiaB2());
		getCommand("sc").setExecutor(new net.kombopvp.pvp.command.StaffChat(this));
		getCommand("givedeaths").setExecutor(new GiveDeaths());
		getCommand("build").setExecutor(new NoBreakEvent());
		getCommand("resetkdr").setExecutor(new ResetKDR());
		getCommand("lastlogin").setExecutor(new LastLogin());
				getCommand("finalizarbatalhas").setExecutor(new RemoverGlads());
		getCommand("ll").setExecutor(new LastLogin());
		getCommand("lastlogin").setExecutor(new LastLogin());
		getCommand("clear").setExecutor(new ClearCMD());
		getCommand("invsee").setExecutor(new Invsee());
		getCommand("head").setExecutor(new Head());

		getCommand("report").setExecutor(new Report());
		getCommand("removekit").setExecutor(new RemoveKit());
		getCommand("givekit").setExecutor(new DarKit());

		getCommand("tphere").setExecutor(new TPHere());
		getCommand("fly").setExecutor(new Fly());
		getCommand("lobby").setExecutor(new Lobby());
		getCommand("hub").setExecutor(new Lobby());
		getCommand("macrotest").setExecutor(new MacroTest());
		getCommand("pvp").setExecutor(new PvP());
		getCommand("autosoup").setExecutor(new AutoSoup(this));
		getCommand("warpinfo").setExecutor(new GladInfo());
		getCommand("gamemode").setExecutor(new GamemodeCMD());
		getCommand("gm").setExecutor(new GamemodeCMD());
		getCommand("tpall").setExecutor(new TPALL());
		getCommand("verrank").setExecutor(new VerRank());
		getCommand("stats").setExecutor(new VerRank());
		getCommand("status").setExecutor(new VerRank());
		getCommand("pinfo").setExecutor(new Info());
		getCommand("givexp").setExecutor(new GiveXP());
		getCommand("vanish").setExecutor(new Vanish());
		getCommand("aplicar").setExecutor(new Aplicar());
		getCommand("v").setExecutor(new Vanish());
		getCommand("kitpvp").setExecutor(new KITPVP(this));
		getCommand("actionbar").setExecutor(new ActionBar());
		getCommand("setfeast").setExecutor(new SetFeast());
		getCommand("dueljoin").setExecutor(new DuelJoin());
		getCommand("setwarp").setExecutor(new SetWarp(this));
		getCommand("money").setExecutor(new Money());
		getCommand("dinheiro").setExecutor(new Money());
		getCommand("admin").setExecutor(new Admin());
		getCommand("sendtitle").setExecutor(new AvisoT());

		getCommand("evento").setExecutor(new EventoComando());
		getCommand("1v1iniciar").setExecutor(new OneVsOneIniciar2());

		getCommand("lavainiciar").setExecutor(new LavaIniciar());
		getCommand("evento").setTabCompleter(new EventoTabComplete());
		
	}
	public void loadListeners() {
		PluginManager pm = Bukkit.getPluginManager();
		pm.registerEvents(new StatusGUI(), this);
		pm.registerEvents(new Boxer(), this);
		pm.registerEvents(new Flash(), this);
		pm.registerEvents(new RecraftGeral(), this);
		pm.registerEvents(new Jumper(), this);
		pm.registerEvents(new EventoListeners(), this);
		pm.registerEvents(new Modo(), this);
		pm.registerEvents(new Grappler(), this);
		pm.registerEvents(new OnevsOneKS(), this);
		pm.registerEvents(new NoBreakEvent(), this);
		pm.registerEvents(new ArenaBuild(), this);
		pm.registerEvents(new SoupTypeGUI(), this);
		pm.registerEvents(new Arena(), this);
		pm.registerEvents(new SelectKitListener(), this);
		pm.registerEvents(new BuyKitListener(), this);
		pm.registerEvents(new OpenSpawnItemsListener(), this);
		pm.registerEvents(new ServerEssentialsListener(), this);
		pm.registerEvents(new KitDiario(), this);
		pm.registerEvents(new PotePlaca(), this);
		pm.registerEvents(new MacroTest(), this);
		pm.registerEvents(new Cocoa(), this);
		pm.registerEvents(new PlayerJoin(), this);
		pm.registerEvents(new PlayerDeathListener(), this);
		
		pm.registerEvents(new net.kombopvp.kit2.Anchor(), this);
		pm.registerEvents(new net.kombopvp.kit2.Kangaroo(), this);

		pm.registerEvents(new net.kombopvp.kit2.Reaper(), this);
		pm.registerEvents(new net.kombopvp.kit2.Reaper(), this);
		pm.registerEvents(new net.kombopvp.kit2.PvP(), this);
		pm.registerEvents(new net.kombopvp.kit2.Viper(), this);
		pm.registerEvents(new net.kombopvp.kit2.Boxer(), this);
		pm.registerEvents(new Lapis(this), this);
		pm.registerEvents(new EntityCalculateDamageListener(), this);
		pm.registerEvents(new Jump(), this);
		pm.registerEvents(new SpawnCMD(), this);
		pm.registerEvents(new SignListener(), this);
		pm.registerEvents(new PlayerCombatLogListener(), this);
		pm.registerEvents(new PlayerQuitListener(), this);
		pm.registerEvents(new PlayerCompassListener(), this);
		pm.registerEvents(new LAVA(), this);
		pm.registerEvents(new Gladiator(), this);
		pm.registerEvents(new OneVsOne(), this);
		pm.registerEvents(new SopaBugada(), this);
		pm.registerEvents(new Sumo(), this);

		pm.registerEvents(new RDMAutomatic(), this);
		pm.registerEvents(new Servidores(), this);
		pm.registerEvents(new PlayerKillstreakListener(), this);
		pm.registerEvents(new PlayerDieArenaListener(), this);
	}
	
	public ScoreboardBuilder getScoreboardBuilder() {
		return scoreboardBuilder;
	}
	
	public Hologram getTopPlayersHd() {
		return topPlayersHd;
	}
	@Override
	public void onPluginMessageReceived(String arg0, Player arg1, byte[] arg2) {
		// TODO Auto-generated method stub
		
	}
}
