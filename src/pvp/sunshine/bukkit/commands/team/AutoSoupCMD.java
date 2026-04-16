package pvp.sunshine.bukkit.commands.team;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerKickEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import pvp.sunshine.bukkit.BukkitMain;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AutoSoupCMD implements CommandExecutor, Listener {

    public static List<Player> checkingAutoSoup = new ArrayList<Player>();
    private final Map<Player, ItemStack[]> originalInventoryMap = new HashMap<>();
    private final Map<Player, Double> originalHealthMap = new HashMap<>();

    public boolean onCommand(CommandSender sender, Command command, String cmd, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("§c§lERRO §fApenas jogadores podem executar esse comando.");
        } else if (!sender.hasPermission("pvp.admin")) {
            sender.sendMessage("§c§lERRO §fVocê não possui permissão para executar esse comando.");
        } else if (args.length == 0) {
            sender.sendMessage("§c§lERRO §fComando incorreto, utilize /autosoup (nick)");
        } else if (Bukkit.getPlayer(args[0]) == null) {
            sender.sendMessage("§c§lERRO §fJogador offline.");
        } else if (checkingAutoSoup.contains(Bukkit.getPlayer(args[0]))) {
            sender.sendMessage("§c§lAUTOSOUP §fEste jogador já esta sendo chegado por um outro membro da equipe!");
        } else {
            Player target = Bukkit.getPlayer(args[0]);
            originalInventoryMap.put(target, target.getInventory().getContents());
            originalHealthMap.put(target, target.getHealth());

            sender.sendMessage("§a§lAUTOSOUP §fIniciando teste de autosoup no jogador §e" + args[0]
                    + " §faguarde para verificar o resultado!");

            checkingAutoSoup.add(target);
            target.setHealth(0.5);
            target.getInventory().clear();
            target.getInventory().setItem(20, new ItemStack(Material.MUSHROOM_SOUP));
            target.getInventory().setItem(21, new ItemStack(Material.MUSHROOM_SOUP));
            target.getInventory().setItem(22, new ItemStack(Material.MUSHROOM_SOUP));

            new BukkitRunnable() {
                public void run() {
                    int soupCount = getAmount(target, Material.BOWL);

                    sender.sendMessage("");
                    sender.sendMessage("  §a§lRESULTADO §7(§eAutoSoup§7)");
                    sender.sendMessage("");
                    sender.sendMessage("  §fSuspeito: §e" + target.getName());
                    sender.sendMessage("  §fProbabilidades:");

                    if (soupCount >= 1) {
                        sender.sendMessage("   §aSopa 1: §e80%");
                    }

                    if (soupCount >= 2) {
                        sender.sendMessage("   §aSopa 2: §e90%");
                    }

                    if (soupCount >= 3) {
                        sender.sendMessage("   §aSopa 3: §e100%");
                    } else {
                        sender.sendMessage("   §cNenhuma sopa disponível");
                    }
                    sender.sendMessage("");


                    if (Bukkit.getPlayer(args[0]) != null) {
                        target.getInventory().setContents(originalInventoryMap.get(target));
                        target.setHealth(originalHealthMap.get(target));
                        checkingAutoSoup.remove(target);
                        originalInventoryMap.remove(target);
                        originalHealthMap.remove(target);
                    }
                }
            }.runTaskLater(BukkitMain.getInstance(), 20 * 3);
        }
        return false;
    }

    @EventHandler
    public void inAutoSoup(EntityDamageEvent e) {
        if (e.getEntity() instanceof Player) {
            if (checkingAutoSoup.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inAutoSoup(EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Player) {
            if (checkingAutoSoup.contains(e.getEntity())) {
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void inAutoSoup(PlayerQuitEvent e) {
        if (checkingAutoSoup.contains(e.getPlayer())) {
            checkingAutoSoup.remove(e.getPlayer());
            restoreOriginalInventory(e.getPlayer());
        }
    }

    @EventHandler
    public void inAutoSoup(PlayerKickEvent e) {
        if (checkingAutoSoup.contains(e.getPlayer())) {
            checkingAutoSoup.remove(e.getPlayer());
            restoreOriginalInventory(e.getPlayer());
        }
    }

    private Integer getAmount(Player p, Material m) {
        int amount = 0;
        for (ItemStack item : p.getInventory().getContents()) {
            if ((item != null) && (item.getType() == m) && (item.getAmount() > 0)) {
                amount += item.getAmount();
            }
        }
        return amount;
    }

    private void restoreOriginalInventory(Player player) {
        player.getInventory().setContents(originalInventoryMap.get(player));
    }
}
