package pvp.sunshine.bukkit.ability.register.kits;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;

@SuppressWarnings("all")
public class Gladiator implements Listener {
	public Plugin plugin;
	private List<Block> gladiatorbloco;
	private HashMap<Block, Player> gladblock;

	public Gladiator() {
		this.gladiatorbloco = new ArrayList<>();
		this.gladblock = new HashMap<>();
		lutando = new HashMap<>();
		this.lugar = new HashMap<>();
		this.plugin = this.plugin;
	}

	static HashMap<Player, Player> lutando;
	private HashMap<Player, Location> lugar;
	private int glad1;
	private int glad2;

	@EventHandler
	void KitGladiator(PlayerInteractEntityEvent e) {
		if (e.getRightClicked() instanceof Player) {
			final Player p = e.getPlayer();
			final Player r = (Player) e.getRightClicked();
			if (p.getItemInHand().getType() == Material.IRON_FENCE && RegisterAbility.getAbility(p) == "Gladiator") {
				Location loc = new Location(p.getWorld(), p.getLocation().getX(), p.getLocation().getY() + 80.0D,
						p.getLocation().getZ());
				Location loc2 = new Location(p.getWorld(), (p.getLocation().getBlockX() + 8),
						(p.getLocation().getBlockY() + 82), (p.getLocation().getBlockZ() + 8));
				Location loc3 = new Location(p.getWorld(), (p.getLocation().getBlockX() - 8),
						(p.getLocation().getBlockY() + 82), (p.getLocation().getBlockZ() - 8));
				if (lutando.containsKey(p) || lutando.containsKey(r)) {
					p.sendMessage("§c§lKIT §fVocê já está em uma batalha.");
					return;
				}
				List<Location> cuboid = new ArrayList<>();
				for (int bX = -10; bX <= 10; bX++) {
					for (int bZ = -10; bZ <= 10; bZ++) {
						for (int bY = -1; bY <= 10; bY++) {
							Block b = loc.clone().add(bX, bY, bZ).getBlock();
							if (!b.isEmpty()) {
								p.sendMessage("§c§lKIT §fVocê não pode criar uma arena aqui.");
								return;
							}
							if (bY == 10) {
								cuboid.add(loc.clone().add(bX, bY, bZ));
							} else if (bY == -1) {
								cuboid.add(loc.clone().add(bX, bY, bZ));
							} else if (bX == -10 || bZ == -10 || bX == 10 || bZ == 10) {
								cuboid.add(loc.clone().add(bX, bY, bZ));
							}
						}
					}
				}
				for (Location loc4 : cuboid) {
					loc4.getBlock().setType(Material.GLASS);
					loc4.getBlock().setData((byte) 4);
					this.gladiatorbloco.add(loc4.getBlock());
					this.gladblock.put(loc4.getBlock(), p);
					this.gladblock.put(loc4.getBlock(), r);
				}
				this.lugar.put(p, p.getLocation());
				this.lugar.put(r, r.getLocation());
				loc2.setYaw(135.0F);
				p.teleport(loc2);
				loc3.setYaw(-45.0F);
				r.teleport(loc3);
				p.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110, 5));
				r.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 110, 5));
				p.sendMessage("§a§lKIT §fVocê desafiou um jogador para uma batalha gladiador. Você ganhou 5 segundos de §e§lINVENCIBILIDADE§f. Caso passe 5 minutos de batalha os dois começaram a tomar dano até a morte.");
				r.sendMessage("§a§lKIT §fVocê foi desafiado por uma batalha gladiador. Você ganhou 5 segundos de §e§lINVENCIBILIDADE§f. Caso passe 5 minutos de batalha os dois começaram a tomar dano até a morte.");
				lutando.put(p, r);
				lutando.put(r, p);
				this.glad1 = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(),
						new Runnable() {

							public void run() {
							}
						}, 4800L);
				this.glad2 = Bukkit.getScheduler().scheduleSyncDelayedTask((Plugin) BukkitMain.getInstance(),
						new Runnable() {
							public void run() {
								if (Gladiator.lutando.containsKey(p) && Gladiator.lutando.containsKey(r)) {
									Gladiator.lutando.remove(p);
									Gladiator.lutando.remove(r);
									p.teleport((Location) Gladiator.this.lugar.get(p));
									r.teleport((Location) Gladiator.this.lugar.get(r));
									for (Block glad : Gladiator.this.gladiatorbloco) {
										if ((Gladiator.this.gladblock.get(glad) == r
												|| Gladiator.this.gladblock.get(glad) == p)
												&& glad.getType() == Material.GLASS) {
											glad.setType(Material.AIR);
										}
									}
									Gladiator.this.lugar.remove(p);
									Gladiator.this.lugar.remove(r);
									Gladiator.this.gladblock.remove(p);
									Gladiator.this.gladblock.remove(r);
								}
							}
						}, 6000L);
			}
		}
	}

	@EventHandler
	void KitGladiatorInteragir(BlockBreakEvent e) {
		Player p = e.getPlayer();
		if (this.gladiatorbloco.contains(p) && p.getGameMode() != GameMode.CREATIVE) {
			e.setCancelled(true);
		}
	}

	@EventHandler
	void KitGladiatorSair(PlayerQuitEvent e) {
		Player p = e.getPlayer();
		if (lutando.containsKey(p)) {
			String nome = ((Player) lutando.get(p)).getName();
			Player q = Bukkit.getPlayer(nome);
			lutando.remove(p);
			lutando.remove(q);
			q.sendMessage("§e§lKIT §fO jogador §e" + p.getName() + " §fdesconectou-se da batalha.");
			q.teleport(this.lugar.get(q));
			Bukkit.getScheduler().cancelTask(this.glad1);
			Bukkit.getScheduler().cancelTask(this.glad2);
			for (Block glad : this.gladiatorbloco) {
				if ((this.gladblock.get(glad) == q || this.gladblock.get(glad) == p)
						&& glad.getType() == Material.GLASS) {
					glad.setType(Material.AIR);
				}
			}
			this.gladblock.remove(p);
			this.gladblock.remove(q);
		}
	}

	@EventHandler
	void KitGladiatorMorrer(PlayerDeathEvent e) {
		Player p = e.getEntity();
		if (lutando.containsKey(p)) {
			String nome = ((Player) lutando.get(p)).getName();
			Player m = Bukkit.getPlayer(nome);
			m.sendMessage("§a§lKIT §fParabéns! você venceu a batalha gladiador.");
			lutando.remove(p);
			lutando.remove(m);
			m.teleport(this.lugar.get(m));
			m.addPotionEffect(new PotionEffect(PotionEffectType.DAMAGE_RESISTANCE, 100, 10));
			Bukkit.getScheduler().cancelTask(this.glad1);
			Bukkit.getScheduler().cancelTask(this.glad2);
			for (Block glad : this.gladiatorbloco) {
				if ((this.gladblock.get(glad) == m || this.gladblock.get(glad) == p)
						&& glad.getType() == Material.GLASS) {
					glad.setType(Material.AIR);
				}
			}
			this.gladblock.remove(p);
			this.gladblock.remove(m);
		}
	}

	@EventHandler
	void KitGladiatorComando(PlayerCommandPreprocessEvent e) {
		Player p = e.getPlayer();
		if (lutando.containsKey(p)) {
			e.setCancelled(true);
			p.sendMessage("§c§lKIT §fVocê não pode executar comandos em batalha gladiador.");
		}
	}
}