package pvp.sunshine.bukkit.commands.team;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;

import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.event.WarpFpsProtection;
import pvp.sunshine.bukkit.manager.scoreboard.Evento;
import pvp.sunshine.bukkit.utils.PvPUtil;
import pvp.sunshine.bukkit.utils.TitleUtil;

public class EventoCMD
        implements CommandExecutor, Listener {
    public static boolean evento = false;
    public static boolean stats = true;
    public static boolean pvp = false;
    public static List<String> participantes = new ArrayList<>();


    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lERRO §fComando apenas para jogadores.");
            return true;
        }
        Player p = (Player) sender;
        if (args.length > 2) {
            sender.sendMessage("§c§lEVENTO §fUtilize: §c/evento | /evento iniciar | /evento abrir | /evento pvp | /evento fechar | /evento finalizar");
        }
        if (args.length == 0) {
            if (!evento) {
                sender.sendMessage("§c§lEVENTO §fNão há nenhum evento em andamento no momento.");
                return true;
            }
            if (sender.hasPermission("pvp.admin")) {
                sender.sendMessage("§c§lEVENTO §fVocê não pode participar desse evento pois é um membro da equipe. Apenas jogadores podem participar de eventos.");
                return true;
            }
            if (!stats) {
                sender.sendMessage("§c§lEVENTO §fO evento já começou, você não pode mais entrar.");
                return true;
            }
            if (participantes.contains(sender.getName())) {
                sender.sendMessage("§c§lEVENTO §fVocê já está em um evento.");
                return true;
            }
            p.setHealth(20.0D);
            RegisterAbility.setAbility(p,"Evento");
            p.setGameMode(GameMode.ADVENTURE);
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            p.getActivePotionEffects().clear();
            p.getInventory().clear();

            p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
            p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
            p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
            p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

            ItemStack espada = new ItemStack(org.bukkit.Material.DIAMOND_SWORD);
            PvPUtil.getPvPUtils(p);
            org.bukkit.inventory.meta.ItemMeta espadaM = espada.getItemMeta();
            espadaM.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1, true);
            espada.setItemMeta(espadaM);
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            p.getInventory().setItem(0, espada);
            p.updateInventory();
            Evento.update(p);
            p.setMaximumNoDamageTicks(20);
            WarpFpsProtection.reiniciarContadorFps(p);
            sender.sendMessage("§a§lEVENTO §fVocê entrou no evento.");
            FlyCMD.removeFly(p);
            p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
            p.teleport(new Location(Bukkit.getWorld("lobbypvp2"), 532.428, 15.00000, 1106.458 , 9.0F, -6.1F));
            Flag.setProtection(p, false);
            TitleUtil.sendTitle(p, 20, 20, 20, "§b§lEVENTO", "§fTenha um bom jogo!");
            p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
            p.setFireTicks(0);
            participantes.add(sender.getName());
            for (String participante : participantes) {
                Player player = Bukkit.getPlayer(participante);
                if (player != null) {
                    player.sendMessage("§e§lEVENTO §fO jogador §e" + p.getName() + " §fentrou no evento. §e" + participantes.size() + " participante(s)§f.");
                    Evento.update(p);
                }
            }
            return true;
        }
        if (args[0].equalsIgnoreCase("iniciar")) {
            if (sender.hasPermission("pvp.evento")) {
                if (evento) {
                    sender.sendMessage("§c§lEVENTO §fJá possui um evento em andamento. Você não pode abrir outro.");
                    return true;
                }
                Bukkit.broadcast("§7* STAFF " + sender.getName() + " iniciou um evento.", "pvp.broadcast");
                Flag.setProtection(p, false);
                p.getInventory().clear();
                p.getInventory().setArmorContents(null);
                p.setHealth(20.0D);
                p.setAllowFlight(false);
                WarpFpsProtection.reiniciarContadorFps(p);
                p.setFlying(false);
                p.setMaximumNoDamageTicks(20);
                p.setGameMode(GameMode.CREATIVE);
                p.playSound(p.getLocation(), Sound.LEVEL_UP, 3.0f, 3.0f);
                p.teleport(new Location(Bukkit.getWorld("lobbypvp2"), 532.428, 15.00000, 1106.458 , 9.0F, -6.1F));
                p.setFireTicks(0);
                evento = true;
                stats = true;
                pvp = false;
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("  §3§lEVENTO §fUm evento foi iniciado!");
                Bukkit.broadcastMessage("  §3§lEVENTO §fEvento: §eColiseu");
                Bukkit.broadcastMessage("  §3§lEVENTO §fParticipantes: §a" + participantes.size());
                Bukkit.broadcastMessage("  §3§lEVENTO §fUtilize §7/evento §fpara entrar!");
                Bukkit.broadcastMessage(" ");
                sender.sendMessage("§a§lEVENTO §fVocê abriu um evento. Caso queira fechar utilize /evento fechar e caso queira finalizar para todos use /evento finalizar");
                for (Player pEvent : Bukkit.getOnlinePlayers()) {
                    p.playSound(pEvent.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                }
                return true;
            }
            p.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
        } else if (args[0].equalsIgnoreCase("abrir")) {
            if (sender.hasPermission("pvp.evento")) {
                if (!evento) {
                    sender.sendMessage("§c§lEVENTO §fNão há nenhum evento em andamento no momento.");
                    return true;
                }
                stats = true;
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("  §a§lEVENTO §fO evento foi aberto!");
                Bukkit.broadcastMessage("  §a§lEVENTO §fEvento: §aColiseu");
                Bukkit.broadcastMessage("  §a§lEVENTO §fParticipantes: §a" + participantes.size());
                Bukkit.broadcastMessage("  §a§lEVENTO §fUtilize §7/evento §fpara entrar!");
                Bukkit.broadcastMessage(" ");
                sender.sendMessage("§a§lEVENTO §fVocê abriu o evento para todos, caso queira fechar utilize /evento fechar");
                for (Player pEvent : Bukkit.getOnlinePlayers()) {
                    p.playSound(pEvent.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                }
                return true;
            }
            p.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
        } else if (args[0].equalsIgnoreCase("pvp")) {
            if (sender.hasPermission("pvp.evento")) {
                if (!evento) {
                    sender.sendMessage("§c§lEVENTO §fNão há nenhum evento em andamento no momento.");
                    return true;
                }
                if (pvp) {
                    pvp = false;
                    sender.sendMessage("§a§lEVENTO §fVocê §cdesabilitou§f o dano do evento. Caso queira habilitar use '/evento pvp' novamente.");
                    for (String participante : participantes) {
                        Player player = Bukkit.getPlayer(participante);
                        if (player != null) {
                            player.sendMessage("§e§lEVENTO §fO dano do evento foi §cdesabilitado§f.");
                        }
                    }
                } else {
                    pvp = true;
                    sender.sendMessage("§a§lEVENTO §fVocê §ahabilitou§f o dano do evento. Caso queira desabilitar use '/evento pvp' novamente.");
                    for (String participante : participantes) {
                        Player player = Bukkit.getPlayer(participante);
                        if (player != null) {
                            player.sendMessage("§e§lEVENTO §fO dano do evento foi §ahabilitado§f.");
                        }
                    }
                }
                return true;
            }
            p.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
        } else if (args[0].equalsIgnoreCase("fechar")) {
            if (sender.hasPermission("pvp.evento")) {
                if (!evento) {
                    sender.sendMessage("§c§lEVENTO §fNão há nenhum evento em andamento no momento.");
                    return true;
                }
                stats = false;
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("  §c§lEVENTO §fO evento foi fechado!");
                Bukkit.broadcastMessage("  §c§lEVENTO §fEvento: §eColiseu");
                Bukkit.broadcastMessage("  §c§lEVENTO §fParticipantes: §a" + participantes.size());
                Bukkit.broadcastMessage(" ");
                sender.sendMessage("§a§lEVENTO §fVocê fechou o evento, agora ninguém poderá mais entrar. Caso queira abrir novamente utilize /evento abrir");
                for (Player pEvent : Bukkit.getOnlinePlayers()) {
                    p.playSound(pEvent.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                }
                return true;
            }
            p.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
        } else if (args[0].equalsIgnoreCase("finalizar")) {
            if (sender.hasPermission("pvp.evento")) {
                if (!evento) {
                    sender.sendMessage("§c§lEVENTO §fNão há nenhum evento em andamento no momento.");
                    return true;
                }
                stats = false;
                evento = false;
                pvp = false;
                Bukkit.broadcast("§7* STAFF " + sender.getName() + " finalizou um evento.", "pvp.broadcast");
                Bukkit.broadcastMessage(" ");
                Bukkit.broadcastMessage("  §9§lEVENTO §fO evento foi finalizado!");
                Bukkit.broadcastMessage("  §9§lEVENTO §fEvento: §eColiseu");
                Bukkit.broadcastMessage(" ");
                sender.sendMessage("§a§lEVENTO §fVocê finalizou o evento com sucesso!");
                Location spawnLocation = new Location(p.getWorld(), 5177.504, 54.00000, 2387.602, 89.6F, -1.2F);
                p.teleport(spawnLocation);
                for (Player pEvent : Bukkit.getOnlinePlayers()) {
                    p.playSound(pEvent.getLocation(), Sound.EXPLODE, 1.0f, 1.0f);
                }
                for (Player all : Bukkit.getOnlinePlayers()) {
                    if (participantes.contains(all.getName())) {
                        participantes.remove(all.getName());
                        all.chat("/spawn");
                        all.sendMessage("\n§3§lEVENTO §fO evento foi finalizado por um membro da equipe.\n§3§lEVENTO §fTodos os jogadores foram teleportados para o spawn.\n");
                        all.playSound(all.getLocation(), Sound.LEVEL_UP, 1.0f, 1.0f);
                    }
                }
            } else {
                p.sendMessage("§c§lERRO §fVocê não tem permissão para executar este comando.");
            }
        }
        return true;
    }

    @EventHandler
    public void onDamage(EntityDamageByEntityEvent e) {
        if (e.getDamager() instanceof Player &&
                !pvp && participantes.contains(e.getEntity().getName())) {
            e.setDamage(0.0D);
            e.setCancelled(true);
            return;
        }
    }

    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent e) {

        if (!e.getPlayer().hasPermission("pvp.admin") &&
                e.getMessage().toLowerCase().startsWith("/") && participantes.contains(e.getPlayer().getName()) &&
                !e.getMessage().toLowerCase().startsWith("/spawn") &&
                !e.getMessage().toLowerCase().startsWith("/report")) {
            e.setCancelled(true);
            e.getPlayer().sendMessage("§c§lEVENTO §fVocê só pode utilizar comandos de spawn e report em evento.");
            return;
        }
    }

    @EventHandler
    public void onQuit(PlayerQuitEvent e) {
        if (participantes.contains(e.getPlayer().getName())) {
            participantes.remove(e.getPlayer().getName());
        }
        for (String participante : participantes) {
            Player player = Bukkit.getPlayer(participante);
            if (player != null) {
                player.sendMessage("§c§lEVENTO §fO jogador §c" + e.getPlayer().getName() + " §fsaiu do evento. §c" + participantes.size() + " participante(s)§f.");
                Evento.update(e.getPlayer());
            }
        }
    }

    @EventHandler
    public void onKick(PlayerKickEvent e) {
        if (participantes.contains(e.getPlayer().getName())) {
            participantes.remove(e.getPlayer().getName());
        }
        for (String participante : participantes) {
            Player player = Bukkit.getPlayer(participante);
            if (player != null) {
                player.sendMessage("§c§lEVENTO §fO jogador §c" + e.getPlayer().getName() + " §fsaiu do evento. §c" + participantes.size() + " participante(s)§f.");
                Evento.update(e.getPlayer());
            }
        }

    }

}