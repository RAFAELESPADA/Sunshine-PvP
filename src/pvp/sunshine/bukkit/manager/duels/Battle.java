package pvp.sunshine.bukkit.manager.duels;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.World;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.team.AdminCMD;
import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.scoreboard.duels.PlayerInBattle;

public class Battle implements Listener {

	public static Map<UUID, ItemStack[]> contents = new HashMap<UUID, ItemStack[]>();
	public static List<UUID> in1v1 = new ArrayList<UUID>();
	public static Map<UUID, UUID> convite = new HashMap<UUID, UUID>();
	public static Map<UUID, UUID> partida = new HashMap<UUID, UUID>();
	public static UUID random = null;

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		UUID id = p.getUniqueId();
		if (partida.containsKey(id)) {
			deathPlayer(p);
		} else if (partida.containsValue(id)) {
			deathPlayer(p);
		}
		BukkitMain.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(),
				new Runnable() {
					public void run() {
						if (contents.containsKey(id)) {
							contents.remove(id);
						}
						if (in1v1.contains(id)) {
							in1v1.remove(id);
						}
					}
				}, 20L);

	}

	@EventHandler
	public void onSair(PlayerInteractEvent e) {
		if (in1v1.contains(e.getPlayer().getUniqueId())
				&& (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK)
				&& e.getPlayer().getItemInHand().getType() == Material.SLIME_BALL) {
			e.getPlayer().chat("/1v1");
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onQuit(PlayerKickEvent e) {
		Player p = e.getPlayer();
		UUID id = p.getUniqueId();
		if (partida.containsKey(id)) {
			deathPlayer(p);
		} else if (partida.containsValue(id)) {
			deathPlayer(p);
		}
		BukkitMain.getInstance().getServer().getScheduler().scheduleSyncDelayedTask(BukkitMain.getInstance(),
				new Runnable() {
					public void run() {
						if (contents.containsKey(id)) {
							contents.remove(id);
						}
						if (in1v1.contains(id)) {
							in1v1.remove(id);
						}
					}
				}, 20L);
	}

	@EventHandler
	public void onPlayerInteractEntity(PlayerInteractEntityEvent e) {
		Player p = e.getPlayer();
		UUID idP = p.getUniqueId();
		if ((in1v1.contains(idP)) && (p.getItemInHand().equals(Complementer.UmVUm))
				&& ((e.getRightClicked() instanceof Player))) {
			Player r = (Player) e.getRightClicked();
			UUID idR = r.getUniqueId();
			if ((in1v1.contains(idR)) && (!partida.containsKey(idR)) && (!partida.containsValue(idR))) {
				if (convite.containsKey(idR)) {
					if (convite.get(idR) == idP) {
						iniciarPartida(p, r);
						p.sendMessage("§a§l1v1 §fVocê aceitou o convite de §a" + r.getName() + "§f. O duelo foi iniciado. Boa sorte!");
						r.sendMessage("§a§l1v1 §fVocê aceitou o convite de §a" + p.getName() + "§f. O duelo foi iniciado. Boa sorte!");
						if (!p.hasPermission("pvp.admin") || !r.hasPermission("pvp.admin")) {
							for (Player todos : Bukkit.getOnlinePlayers()) {
								if (AdminCMD.admin.contains(todos.getName())) {
									if (!p.hasPermission("pvp.admin")) {
										p.hidePlayer(todos);
									}
									if (!r.hasPermission("pvp.admin")) {
										r.hidePlayer(todos);
									}
								}
							}

						}
					} else if (convite.get(idP) != idR) {
						if (convite.containsKey(idP)) {
							convite.remove(idP);
						}
						convite.put(idP, idR);
						p.sendMessage("§e§l1V1 §fVocê desafiou §e" + r.getName() + "§f para um duelo!");
						r.sendMessage("§e§l1V1 §fO jogador §e" + p.getName() + " §fdesafiou você para um duelo!");
					} else {
						p.sendMessage("§c§l1V1 §fVocê já convidou esse jogador(a), aguarde para convida-lo novamente!");
					}
				} else if (convite.get(idP) != idR) {
					if (convite.containsKey(idP)) {
						convite.remove(idP);
					}
					convite.put(idP, idR);
					p.sendMessage("§e§l1V1 §fVocê desafiou §e" + r.getName() + "§f para um duelo!");
					r.sendMessage("§e§l1V1 §fO jogador §e" + p.getName() + " §fdesafiou você para um duelo!");
				} else {
					p.sendMessage("§c§l1V1 §fVocê já convidou esse jogador(a), aguarde para convida-lo novamente!");
				}
			} else {
				p.sendMessage("§c§lERRO §fEsse jogador não está na warp 1v1.");
			}
		}
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		Player p = e.getEntity();
		UUID id = p.getUniqueId();
		if ((in1v1.contains(id)) && ((p.getKiller() instanceof Player))) {
			Player k = p.getKiller();
			UUID idK = k.getUniqueId();
			if (in1v1.contains(idK)) {
				if ((partida.containsKey(id)) && (partida.get(id) == idK)) {
					if (partida.containsKey(idK)) {
						partida.remove(idK);
					}
					if (partida.containsKey(id)) {
						partida.remove(id);
					}
					partida.remove(id);
					if (in1v1.contains(id)) {
						in1v1.remove(id);
					}
					if (convite.containsKey(id)) {
						convite.remove(id);
					}
					if (convite.containsKey(idK)) {
						convite.remove(idK);
					}
					new BukkitRunnable() {
						public void run() {
							p.teleport(new Location(Bukkit.getWorld("1v1"), -14.720, 41.000000, 20.233, -179.1F, -1.7F));
							
							if (!Battle.in1v1.contains(p.getUniqueId())) {
								Battle.in1v1.add(p.getUniqueId());
							}

							Flag.setProtection(p, true);
							p.setAllowFlight(false);
							p.setFlying(false);
							Battle.contents.put(p.getUniqueId(), p.getInventory().getContents());
							Complementer.items1v1(p);
							for (UUID idS : Battle.partida.keySet()) {
								Player s = Bukkit.getPlayer(idS);
								if ((s != null) && (s.isOnline()) && (s.canSee(p))) {
									s.hidePlayer(p);
								}
							}
							for (Player s : Bukkit.getOnlinePlayers()) {
								if (AdminCMD.admin.contains(s.getName())) {
									p.hidePlayer(s);
								}
							}
							RegisterAbility.setAbility(p, "1v1");
						}
					}.runTaskLater(BukkitMain.getInstance(), 3);

					Complementer.items1v1(k);

					k.teleport(new Location(Bukkit.getWorld("1v1"), -14.720, 41.000000, 20.233, -179.1F, -1.7F));
					k.setMaxHealth(20.0D);
					k.setHealth(20.0D);
					for (Player s : Bukkit.getOnlinePlayers()) {
						p.showPlayer(s);
						k.showPlayer(s);
						if (!p.hasPermission("pvp.admin") || !k.hasPermission("pvp.admin")) {
							if (AdminCMD.admin.contains(s.getName())) {
								if (!p.hasPermission("pvp.admin")) {
									p.hidePlayer(s);
								}
								if (!k.hasPermission("pvp.admin")) {
									k.hidePlayer(s);
								}
							}
						}
					}
				}
			} else if ((partida.containsKey(idK)) && (partida.get(idK) == id)) {
				if (partida.containsKey(idK)) {
					partida.remove(idK);
				}
				if (partida.containsKey(id)) {
					partida.remove(id);
				}
				partida.remove(idK);
				if (in1v1.contains(id)) {
					in1v1.remove(id);
				}
				if (convite.containsKey(id)) {
					convite.remove(id);
				}
				if (convite.containsKey(idK)) {
					convite.remove(idK);
				}
				Flag.setProtection(p, false);
				Flag.setProtection(k, false);
				Complementer.items1v1(k);
				k.teleport(new Location(Bukkit.getWorld("1v1"), -14.720, 41.000000, 20.233, -179.1F, -1.7F));
				k.setMaxHealth(20.0D);
				k.setHealth(20.0D);
				for (Player s : Bukkit.getOnlinePlayers()) {
					p.showPlayer(s);
					k.showPlayer(s);
					if (!p.hasPermission("pvp.admin") || !k.hasPermission("pvp.admin")) {
						if (AdminCMD.admin.contains(s.getName())) {
							if (!p.hasPermission("pvp.admin")) {
								p.hidePlayer(s);
							}
							if (!k.hasPermission("pvp.admin")) {
								k.hidePlayer(s);
							}
						}
					}
				}
			}
		}
	}

	public static void deathPlayer(Player p) {
		if (in1v1.contains(p.getUniqueId())) {
			// Jogador está em um duelo 1v1
			if (partida.containsKey(p.getUniqueId())) {
				Player s = Bukkit.getPlayer(partida.get(p.getUniqueId()));
				if (s != null && !s.isDead()) {
					p.setHealth(1.0D);
					p.damage(999999.0D, s);
					return;
				}
			}
		} else {
			if (!p.isDead()) {
				p.damage(999999.0D);
			}
		}
	}


	@EventHandler
	public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
		if ((e.getEntity() instanceof Player)) {
			Player ep = (Player) e.getEntity();
			if ((e.getDamager() instanceof Player)) {
				Player dp = (Player) e.getDamager();
				if (((in1v1.contains(ep.getUniqueId())) || (in1v1.contains(dp.getUniqueId())))
						&& (((!partida.containsKey(ep.getUniqueId())) && (!partida.containsValue(ep.getUniqueId())))
								|| ((!partida.containsKey(dp.getUniqueId()))
										&& (!partida.containsValue(dp.getUniqueId()))))) {
					e.setCancelled(true);
				}
			}
		}
	}

	@EventHandler
	public void onPlayerCommandProcess(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		UUID id = p.getUniqueId();
		if (in1v1.contains(id)) {
			if ((partida.containsKey(id)) || (partida.containsValue(id))) {
				e.setCancelled(true);
				p.sendMessage("§c§l1V1 §fVocê já está em um duelo.");
			} else if ((in1v1.contains(id)) && (!e.getMessage().toString().equals("/1v1"))) {
				if (e.getMessage().toString().equals("/spawn")) {
					return;
				} else {
					e.setCancelled(false);
				}
			}
		}
	}

	@SuppressWarnings("deprecation")
	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		UUID id = p.getUniqueId();
		if ((e.getItem() != null) && (e.getItem().getItemMeta() != null) && (in1v1.contains(id))
				&& ((e.getAction() == Action.RIGHT_CLICK_AIR) || (e.getAction() == Action.RIGHT_CLICK_BLOCK))) {
			if ((e.getItem().equals(Complementer.Sair)) && (e.getItem().getType() == Material.SLIME_BALL)) {
				p.chat("/1v1");
				p.updateInventory();
			}
			if (e.getItem().getTypeId() == 351) {
				if ((random != null) && (random != id)) {
					Player p2 = Bukkit.getPlayer(random);
					if (p2 != null) {
						random = null;
						iniciarPartida(p, p2);
						p.sendMessage("§a§l1v1 §fO modo randômico encontrou um oponente!\n§a§l1V1 §fVocê está duelando contra o jogador §a" + p2.getName() + "§f!");
						p2.sendMessage("§a§l1v1 §fO modo randômico encontrou um oponente!\n§a§l1V1 §fVocê está duelando contra o jogador §a" + p.getName() + "§f!");

					} else {
						random = id;
						p.setItemInHand(new ItemStack(351, 1, (short) 10));

						ItemMeta meta = p.getItemInHand().getItemMeta();
						meta.setDisplayName("§e1v1 Rápido §8(§aAtivado§8)");

						p.getItemInHand().setItemMeta(meta);
						p.sendMessage("§e§lSEARCH §fVocê entrou na fila. Buscando jogadores...");
						p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
					}
				} else if (e.getItem().getItemMeta().getDisplayName().contains("§cDesativado")) {
					random = id;
					p.setItemInHand(new ItemStack(351, 1, (short) 10));

					ItemMeta meta = p.getItemInHand().getItemMeta();
					meta.setDisplayName("§e1v1 Rápido §8(§aAtivado§8)");

					p.getItemInHand().setItemMeta(meta);
					p.sendMessage("§e§lSEARCH §fVocê entrou na fila. Buscando jogadores...");
					p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
				} else {
					random = null;
					p.setItemInHand(new ItemStack(351, 1, (short) 8));

					ItemMeta meta = p.getItemInHand().getItemMeta();
					meta.setDisplayName("§e1v1 Rápido §8(§cDesativado§8)");

					p.getItemInHand().setItemMeta(meta);
					p.sendMessage("§c§lSEARCH §fVocê saiu da fila.");
					p.playSound(p.getLocation(), Sound.CLICK, 1.0f, 1.0f);
				}
			}
		}
	}

	public void iniciarPartida(Player p, Player p2) {
		p.teleport(
				new Location(Bukkit.getWorld("1v1"), 66538.515, 71.000000, 66759.435, 1.1F, -1.6F));
		p2.teleport(
				new Location(Bukkit.getWorld("1v1"), 66538.515, 71.000000, 66809.664, -178.9F, -3.5F));
		
		partida.put(p.getUniqueId(), p2.getUniqueId());
		partida.put(p2.getUniqueId(), p.getUniqueId());
		Flag.setProtection(p2, false);
		Flag.setProtection(p, false);
		Complementer.items1v1Partida(p);
		Complementer.items1v1Partida(p2);

		p.setMaxHealth(20.0D);
		p2.setMaxHealth(20.0D);
		p.setHealth(20.0D);
		p2.setHealth(20.0D);
		PlayerInBattle.update(p2);
		PlayerInBattle.update(p);
		p2.playSound(p2.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
		p.playSound(p2.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
		for (Player s : Bukkit.getOnlinePlayers()) {
			p.hidePlayer(s);
			p2.hidePlayer(s);
		}
		p.showPlayer(p2);
		p2.showPlayer(p);
		if (!p.hasPermission("pvp.admin") || !p2.hasPermission("pvp.admin")) {
			for (Player todos : Bukkit.getOnlinePlayers()) {
				if (AdminCMD.admin.contains(todos.getName())) {
					if (!p.hasPermission("pvp.admin")) {
						p.hidePlayer(todos);
					}
					if (!p2.hasPermission("pvp.admin")) {
						p2.hidePlayer(todos);
					}
				}
			}

		}
		if (random == p.getUniqueId() || (random == p2.getUniqueId())) {
			random = null;
		}
	}
}
