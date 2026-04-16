package pvp.sunshine.bukkit.manager.event;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Sign;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockBurnEvent;
import org.bukkit.event.block.BlockExplodeEvent;
import org.bukkit.event.block.BlockIgniteEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.event.entity.FoodLevelChangeEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerAchievementAwardedEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerPickupItemEvent;
import org.bukkit.event.weather.WeatherChangeEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import net.md_5.bungee.api.ChatColor;
import pvp.sunshine.bukkit.BukkitMain;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.commands.team.BuildCMD;
import pvp.sunshine.bukkit.commands.team.ChatCMD;
import pvp.sunshine.bukkit.manager.fake.FakeNameManager;
import pvp.sunshine.bukkit.manager.inventory.BoxSystemType;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLRank;
import pvp.sunshine.bukkit.manager.scoreboard.Lava;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class Essentials implements Listener {

    private final Map<Player, Long> chatCooldown = new HashMap<>();
	  
LuckPerms api = LuckPermsProvider.get();



    @EventHandler
    public void onPlayerDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player jogadorAtacante = (Player) event.getDamager();
            if (jogadorAtacante.getInventory().getItemInHand().getType() == Material.WOOD_SWORD) {
                event.setDamage(3.2);
            }
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player attacker = (Player) event.getDamager();
            Player victim = (Player) event.getEntity();

            if (RegisterAbility.getAbility(attacker).equalsIgnoreCase("Nenhum")) {
                return;
            }

            if (isCriticalAttack(attacker)) {
                playCriticalEffect(victim);
            }
        }
    }

    private boolean isCriticalAttack(Player player) {
        return player.isSprinting() && player.getFallDistance() > 0;
    }

    private void playCriticalEffect(Player player) {
        player.getWorld().playEffect(player.getLocation(), Effect.STEP_SOUND, Material.REDSTONE_BLOCK, 10);
    }



    @EventHandler
    public void onPlayerDamage2(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Player jogadorAtacante = (Player) event.getDamager();
            if (jogadorAtacante.getInventory().getItemInHand().getType() == Material.WOOD_AXE) {
                event.setDamage(3.0);
            }
            if (jogadorAtacante.getInventory().getItemInHand().getType() == Material.IRON_SWORD) {
                event.setDamage(3.0);
            }
            if (jogadorAtacante.getInventory().getItemInHand().getType() == Material.DIAMOND_AXE) {
                event.setDamage(3.0);
            }
            if (jogadorAtacante.getInventory().getItemInHand().getType() == Material.WOOD_AXE) {
                event.setDamage(3.0);
            }
        }
    }

    private final Map<Player, String> playerFakeTags;

    public Essentials(Map<Player, String> playerFakeTags) {
        this.playerFakeTags = playerFakeTags;
    }


    @EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        Player player = event.getPlayer();

        if (!player.hasPermission("pvp.chatoff") && !ChatCMD.chat) {
            event.setCancelled(true);
            player.sendMessage("§c§lCHAT §fO chat está desabilitado.");

            return;
        }

        String displayName = player.getName();
        if (playerFakeTags.containsKey(player)) {
            displayName = playerFakeTags.get(player) + FakeNameManager.getFakeName(player.getUniqueId());
        }

        if (player.hasPermission("pvp.chatcolor")) {
            event.setFormat("§7(" + SQLRank.getRank(event.getPlayer()) + "§7) " +  api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix().replace("&", "§") + player.getName() +
                     " §7» §f" + event.getMessage().replace("%", "").replace("&", "§"));
        } else {
        	event.setFormat("§7(" + SQLRank.getRank(event.getPlayer()) + "§7) " +  api.getUserManager().getUser(player.getUniqueId()).getCachedData().getMetaData().getPrefix().replace("&", "§") + player.getName() +
                    " §7» §f" + event.getMessage().replace("%", ""));
        }
        long currentTimeMillis = System.currentTimeMillis();
        long lastMessageTime = ((Long) this.chatCooldown.getOrDefault(player, Long.valueOf(0L))).longValue();

        if (!player.hasPermission("pvp.spam")) {
            long cooldownTime = 4000L;

            if (lastMessageTime + cooldownTime > currentTimeMillis) {
                long remainingTime = (lastMessageTime + cooldownTime - currentTimeMillis) / 1000L;
                player.sendMessage("§c§lSPAM §fAguarde §c" + remainingTime + " §fsegundos para enviar outra mensagem.");
                event.setCancelled(true);
            } else {
                this.chatCooldown.put(player, Long.valueOf(currentTimeMillis));
            }
        }
    }



    @EventHandler
    public void construir(BlockPlaceEvent e) {
        if (!BuildCMD.buildModePlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void quebrar(BlockBreakEvent e) {
        if (!BuildCMD.buildModePlayers.contains(e.getPlayer())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onExplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void blockExplode(BlockExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBurn(BlockBurnEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onBlockCommands(PlayerCommandPreprocessEvent e) {
        String[] mensagem = e.getMessage().split(" ");
        ArrayList<String> bukkitcommands = new ArrayList<>();
        bukkitcommands.add("ver");
        bukkitcommands.add("version");
        bukkitcommands.add("about");
        bukkitcommands.add("help");
        bukkitcommands.add("?");
        bukkitcommands.add("bukkit:?");
        bukkitcommands.add("bukkit:ver");
        bukkitcommands.add("bukkit:version");
        bukkitcommands.add("bukkit:help");
        bukkitcommands.add("bukkit:about");
        bukkitcommands.add("minecraft:me");
        bukkitcommands.add("bukkit:stop");
        bukkitcommands.add("bukkit:restart");
        bukkitcommands.add("bukkit:op");
        bukkitcommands.add("bukkit:deop");
        bukkitcommands.add("bukkit:reload");
        bukkitcommands.add("ace");

        for (String loop : bukkitcommands) {
            if (mensagem[0].equalsIgnoreCase("/" + loop)) {
                e.getPlayer().getPlayerListName();
                e.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onMinecraftPlugins(PlayerCommandPreprocessEvent e) {
        String lowerCaseMessage = e.getMessage().toLowerCase();

        if (lowerCaseMessage.startsWith("/pl") ||
                lowerCaseMessage.startsWith("/plugins") ||
                lowerCaseMessage.startsWith("/me") ||
                lowerCaseMessage.startsWith("/hd") ||
                lowerCaseMessage.startsWith("/restart") ||
                lowerCaseMessage.startsWith("/op") ||
                lowerCaseMessage.startsWith("/deop") ||
                lowerCaseMessage.startsWith("/reload") ||
                lowerCaseMessage.startsWith("/bungee") ||
                lowerCaseMessage.startsWith("/minecraft:") ||
                lowerCaseMessage.startsWith("/ace") ||
                lowerCaseMessage.startsWith("/jh_ace") ||
                lowerCaseMessage.startsWith("/crashfix") ||
                lowerCaseMessage.startsWith("/bukkit:") ||
                lowerCaseMessage.startsWith("/icanhasbukkit:") ||
                lowerCaseMessage.startsWith("/icanhas:") ||
                lowerCaseMessage.startsWith("/spigot:")) {
            e.getPlayer().sendMessage("§c§lERRO §fComando inexistente.");
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onSoupManager(PlayerInteractEvent event) {
        Player player = event.getPlayer();

        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        if (player.getItemInHand().getType() == Material.MUSHROOM_SOUP && player.getHealth() != player.getMaxHealth()) {
            double maxHealth = player.getMaxHealth();
            double currentHealth = player.getHealth();

            if (currentHealth > maxHealth - 7.0D) {
                player.setHealth(maxHealth);
            } else {
                player.setHealth(currentHealth + 7.0D);
                player.getWorld().playEffect(player.getLocation().add(0.0D, 1.5D, 0.0D), Effect.HEART, 7);
            }

            player.getItemInHand().setType(Material.BOWL);
            player.getItemInHand().setAmount(1);
            player.getInventory().getItemInHand().setItemMeta(player.getItemInHand().getItemMeta());
            player.updateInventory();
        }
    }

    @EventHandler
    public void onCompass(PlayerInteractEvent event) {
        if (RegisterAbility.getAbility(event.getPlayer()) != "Nenhum"
                && event.getPlayer().getItemInHand().getType() == Material.COMPASS
                && (event.getAction() == Action.LEFT_CLICK_AIR || event.getAction() == Action.LEFT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_BLOCK
                || event.getAction() == Action.RIGHT_CLICK_AIR)) {
            Boolean pesquisado = Boolean.valueOf(false);
            for (int i = 0; i < 100; i++) {
                List<Entity> pertos = event.getPlayer().getNearbyEntities(i, 128.0D, i);
                for (Entity e : pertos) {
                    if (((Entity) e).getType().equals(EntityType.PLAYER)
                            && event.getPlayer().getLocation().distance(((Entity) e).getLocation()) > 0.0D) {
                        event.getPlayer().setCompassTarget(((Entity) e).getLocation());
                        if (((Player) e).hasPermission("pvp.admin")) {
                            event.getPlayer().sendMessage(
                                    "§e§lBUSSOLA §fSua bússola está apontando para §e" + ((Player) e).getName());
                            return;
                        }
                        event.getPlayer().sendMessage(
                                "§e§lBUSSOLA §fSua bússola está apontando para §e" + ((Player) e).getName());
                        pesquisado = Boolean.valueOf(true);
                        break;
                    }
                }
                if (pesquisado.booleanValue()) {
                    break;
                }
            }
            if (!pesquisado.booleanValue()) {
                event.getPlayer()
                        .sendMessage("§c§lBUSSOLA §fNenhum jogador encontrado, bússsola apontando para o spawn.");
                
                event.getPlayer().setCompassTarget(event.getPlayer().getWorld().getSpawnLocation());
            }
        }
    }

    @EventHandler
    public void onFood(FoodLevelChangeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onEplode(EntityExplodeEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onWeather(WeatherChangeEvent e) {
        e.setCancelled(e.toWeatherState());
    }

    @EventHandler
    public void onPickUp(PlayerPickupItemEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void conquist(PlayerAchievementAwardedEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onFire(BlockIgniteEvent e) {
        if (e.getCause() == BlockIgniteEvent.IgniteCause.FLINT_AND_STEEL) {
            return;
        }
        e.setCancelled(true);
    }

    @EventHandler
    public void burn(BlockBurnEvent e) {
        e.setCancelled(true);
    }
@EventHandler
public void kikaraobugar(PlayerMoveEvent e) {
	Player p = e.getPlayer();
	if (SQLPvP.getKills(p) == null || SQLPvP.getDeaths(p) == null || SQLPvP.getCoins(p) == null) {
    	p.kickPlayer(ChatColor.RED + "ENCONTRAMOS UM ERRO COM SUA CONTA! RELOGUE PARA ARRUMAR");
    }
}
    @EventHandler
    public void onItemSpawn(final ItemSpawnEvent event) {
        BukkitRunnable removeItemTask = new BukkitRunnable() {
            public void run() {
                event.getEntity().remove();
            }
        };

        removeItemTask.runTaskTimerAsynchronously(BukkitMain.getInstance(), 0L, 20L);
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent event) {
        if (event.getSpawnReason() != CreatureSpawnEvent.SpawnReason.CUSTOM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent event) {
        Material itemType = event.getItemDrop().getItemStack().getType();
        if (itemType == Material.ENDER_CHEST || itemType == Material.COMPASS || itemType == Material.FIREWORK
                || itemType == Material.PAPER || itemType == Material.BLAZE_POWDER || itemType == Material.SKULL_ITEM) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDamageByPlayer(EntityDamageByEntityEvent event) {
        if (!(event.getDamager() instanceof Player)) {
            return;
        }

        Player attacker = (Player) event.getDamager();
        Material weaponType = attacker.getInventory().getItemInHand().getType();

        if (weaponType == Material.STONE_SWORD) {
            event.setDamage(3.3D);
        } else if (weaponType == Material.DIAMOND_SWORD) {
            if (attacker.getInventory().getItemInHand().getEnchantments().containsKey(Enchantment.DAMAGE_ALL)) {
                event.setDamage(6.9D);
            } else {
                event.setDamage(4.5D);
            }
        }
        if (weaponType == Material.STONE_SWORD || weaponType == Material.FISHING_ROD || weaponType == Material.WOOD_AXE
                || weaponType == Material.STONE_AXE || weaponType == Material.GOLD_AXE || weaponType == Material.WOOD_SWORD || weaponType == Material.DIAMOND_SWORD) {
            attacker.getInventory().getItemInHand().setDurability((short) 0);
            attacker.updateInventory();
        }
    }


    @EventHandler
    public void onDropItem(PlayerDropItemEvent event) {
        Material itemType = event.getItemDrop().getItemStack().getType();
        if (event.getItemDrop() != null) {
if (event.getItemDrop().getName().contains("POTION") || event.getItemDrop().getName().contains("GOLD") || event.getItemDrop().getItemStack().equals(new ItemStack(Material.ARROW))) {
	return;
}
        }
        if (itemType != Material.MUSHROOM_SOUP && itemType != Material.BOWL && itemType != Material.RED_MUSHROOM
                && itemType != Material.BROWN_MUSHROOM  && itemType != Material.POTION  && itemType != Material.GOLD_SWORD  && itemType != Material.BOW  && itemType != Material.STONE_AXE  && itemType != Material.GOLD_AXE  && itemType != Material.LEATHER_LEGGINGS  && itemType != Material.WOOD_SWORD  && itemType != Material.IRON_AXE  && itemType != Material.SNOW_BALL  && itemType != Material.IRON_BOOTS  && itemType != Material.IRON_HELMET  && itemType != Material.CHAINMAIL_CHESTPLATE) {
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onSignEvent(SignChangeEvent e) {
        Player player = e.getPlayer();
        if (player.hasPermission("pvp.placas")) {
            String line1 = e.getLine(0);
            if (line1.equalsIgnoreCase("recraft")) {
                e.setLine(0, "");
                e.setLine(1, "§b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"));
                e.setLine(2, "§eRecraft");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca de Recraft criada!");
            } else if (line1.equalsIgnoreCase("sopas")) {
                e.setLine(0, "");

                e.setLine(1, "§b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"));
                e.setLine(2, "§eSopas");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca de Sopas criada!");
            } else if (line1.equalsIgnoreCase("potion")) {
                e.setLine(0, "");
                e.setLine(1, "§b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"));
                e.setLine(2, "§ePotion");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca de Poção de Cura criada!");
            } else if (line1.equalsIgnoreCase("facil")) {
                e.setLine(0, "");
                e.setLine(1, "§a§lFACIL");
                e.setLine(2, "§eClique aqui!");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca do nível Fácil da Lava Challenge criada!");
            } else if (line1.equalsIgnoreCase("medio")) {
                e.setLine(0, "");
                e.setLine(1, "§6§lMEDIO");
                e.setLine(2, "§eClique aqui!");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca do nível Médio da Lava Challenge criada!");
            } else if (line1.equalsIgnoreCase("dificil")) {
                e.setLine(0, "");
                e.setLine(1, "§c§lDIFICIL");
                e.setLine(2, "§eClique aqui!");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca do nível Díficil da Lava Challenge criada!");
            } else if (line1.equalsIgnoreCase("impossivel")) {
                e.setLine(0, "");
                e.setLine(1, "§4§lIMPOSSIVEL");
                e.setLine(2, "§eClique aqui!");
                e.setLine(3, "");
                player.sendMessage("§a§lPLACA §fPlaca do nível Impossível da Lava Challenge criada!");
            }

        }
    }



    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) {
        if (e.getAction() == Action.RIGHT_CLICK_BLOCK) {
            Block clickedBlock = e.getClickedBlock();
            if (clickedBlock != null
                    && (clickedBlock.getType() == Material.WALL_SIGN || clickedBlock.getType() == Material.SIGN_POST)) {
                Sign sign = (Sign) clickedBlock.getState();
                String[] lines = sign.getLines();
                if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"))
                        && lines[2].equals("§eRecraft") && lines[3].isEmpty()) {
                    openRecraftInventory(e.getPlayer());
                } else if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"))
                        && lines[2].equals("§eSopas") && lines[3].isEmpty()) {
                    openSopasInventory(e.getPlayer());
                } else if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§b§l" + BukkitMain.getInstance().getConfig().getString("NomeServer"))
                        && lines[2].equals("§ePotion") && lines[3].isEmpty()) {
                    openPotionInventory(e.getPlayer());
                } else if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§a§lFACIL")
                        && lines[2].equals("§eClique aqui!") && lines[3].isEmpty()) {
                    Bukkit.broadcastMessage("§3§lLAVA §fO jogador §3" + e.getPlayer().getName() + "§f completou o nível §aFácil§f do Lava Challenge.");
                    e.getPlayer().sendMessage("§a§lLAVA §fParabéns! §fVocê completou o nível §aFácil§f do Lava Challenge! E como recompensa, adicionamos §e+30 coins§f em sua conta.");
                    Lava.update(e.getPlayer());
                    BoxSystemType.spawnEndermanTeleportParticles(e.getPlayer(), 50);
                    BoxSystemType.playFirework(e.getPlayer().getLocation(), Color.AQUA, e.getPlayer());
                    e.getPlayer().getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                    PvPUtil.InventoryAdapter(e.getPlayer());
                    PvPUtil.giveSoup(e.getPlayer(), 36);
                    PvPUtil.getRecraft(e.getPlayer());
                    SQLPvP.addCoins(e.getPlayer(), 30);
                    e.getPlayer().setHealth(20);
                    e.getPlayer().setFireTicks(0);
                } else if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§6§lMEDIO")
                        && lines[2].equals("§eClique aqui!") && lines[3].isEmpty()) {
                    Bukkit.broadcastMessage("§3§lLAVA §fO jogador §3" + e.getPlayer().getName() + "§f completou o nível §eMédio§f do Lava Challenge.");
                    e.getPlayer().sendMessage("§a§lLAVA §fParabéns! §fVocê completou o nível §eMédio§f do Lava Challenge! E como recompensa, adicionamos §e+50 coins§f em sua conta.");
                    Lava.update(e.getPlayer());
                    BoxSystemType.spawnEndermanTeleportParticles(e.getPlayer(), 50);
                    BoxSystemType.playFirework(e.getPlayer().getLocation(), Color.AQUA, e.getPlayer());
                    e.getPlayer().getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                    PvPUtil.InventoryAdapter(e.getPlayer());
                    PvPUtil.giveSoup(e.getPlayer(), 36);
                    PvPUtil.getRecraft(e.getPlayer());
                    SQLPvP.addCoins(e.getPlayer(), 50);
                    e.getPlayer().setHealth(20);
                    e.getPlayer().setFireTicks(0);
                } else if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§c§lDIFICIL")
                        && lines[2].equals("§eClique aqui!") && lines[3].isEmpty()) {
                    Bukkit.broadcastMessage("§3§lLAVA §fO jogador §3" + e.getPlayer().getName() + "§f completou o nível §cDíficil§f do Lava Challenge.");
                    e.getPlayer().sendMessage("§a§lLAVA §fParabéns! §fVocê completou o nível §cDíficil§f do Lava Challenge! E como recompensa, adicionamos §e+100 coins§f em sua conta.");
                    Lava.update(e.getPlayer());
                    BoxSystemType.spawnEndermanTeleportParticles(e.getPlayer(), 50);
                    BoxSystemType.playFirework(e.getPlayer().getLocation(), Color.AQUA, e.getPlayer());
                    e.getPlayer().getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                    
                    PvPUtil.InventoryAdapter(e.getPlayer());
                    PvPUtil.giveSoup(e.getPlayer(), 36);
                    PvPUtil.getRecraft(e.getPlayer());
                    SQLPvP.addCoins(e.getPlayer(), 100);
                    e.getPlayer().setHealth(20);
                    e.getPlayer().setFireTicks(0);
                } else if (lines.length >= 4 && lines[0].isEmpty() && lines[1].equals("§4§lIMPOSSIVEL")
                        && lines[2].equals("§eClique aqui!") && lines[3].isEmpty()) {
                    Bukkit.broadcastMessage("§3§lLAVA §fO jogador §3" + e.getPlayer().getName() + "§f completou o nível §4Impossível§f do Lava Challenge.");
                    e.getPlayer().sendMessage("§a§lLAVA §fParabéns! §fVocê completou o nível §4Impossível§f do Lava Challenge! E como recompensa, adicionamos §e+300 coins§f em sua conta.");
                    Lava.update(e.getPlayer());
                    BoxSystemType.spawnEndermanTeleportParticles(e.getPlayer(), 50);
                    BoxSystemType.playFirework(e.getPlayer().getLocation(), Color.AQUA, e.getPlayer());
                    e.getPlayer().getPlayer().teleport(new Location(Bukkit.getWorld("lava"), 48.00000000, 6.0000000, 51.000, (float) -179.0, (float)  4.0));
                    
                    PvPUtil.InventoryAdapter(e.getPlayer());
                    PvPUtil.giveSoup(e.getPlayer(), 36);
                    PvPUtil.getRecraft(e.getPlayer());
                    SQLPvP.addCoins(e.getPlayer(), 300);
                    e.getPlayer().setHealth(20);
                    e.getPlayer().setFireTicks(0);
                }
            }
        }
    }

    private void openRecraftInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, "§eRecraft");
        ItemStack cogu1 = new ItemStack(Material.RED_MUSHROOM, 64);
        ItemStack cogu2 = new ItemStack(Material.BROWN_MUSHROOM, 64);
        ItemStack potes = new ItemStack(Material.BOWL, 64);
        fillInventory(inv, cogu1, 0, 9);
        fillInventory(inv, cogu2, 9, 18);
        fillInventory(inv, potes, 18, 27);
        player.openInventory(inv);
    }

    private void openSopasInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, "§eSopas");
        ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
        fillInventory(inv, sopa, 0, 27);
        player.openInventory(inv);
    }

    private void openPotionInventory(Player player) {
        Inventory inv = Bukkit.createInventory(player, 27, "§ePotion");
        ItemStack sopa = new ItemStack(Material.POTION);
        sopa.setDurability((short)16421);
        fillInventory(inv, sopa, 0, 27);
        player.openInventory(inv);
    }

    private void fillInventory(Inventory inv, ItemStack item, int start, int end) {
        for (int i = start; i < end; i++) {
            inv.setItem(i, item);
        }
    }

    @EventHandler
    public void onDropsFall(ItemSpawnEvent event) {
        Material itemType = event.getEntity().getItemStack().getType();

        if (itemType == Material.MUSHROOM_SOUP || itemType == Material.BOWL || itemType == Material.RED_MUSHROOM
                || itemType == Material.BROWN_MUSHROOM) {
            event.getEntity().remove();
        }
    }

    @EventHandler
    public void entityDamageByVoid(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID
                && RegisterAbility.getAbility((Player) e.getEntity()) == "Nenhum") {
//510.137 12.000000 620.218
            Location spawnLocation = new Location(Bukkit.getWorld("lobbypvp2") , 510.137, 12.000000, 620.218 , (float)-89.811 , (float)3.0000000);((Player)e.getEntity()).teleport(spawnLocation);
            e.setCancelled(true);
        }
    }
        @EventHandler
        public void entityDamageByVoitd(EntityDamageEvent e) {
            if (e.getCause() == EntityDamageEvent.DamageCause.VOID
                    && RegisterAbility.getAbility((Player) e.getEntity()) == "1v1") {
            	((Player)e.getEntity()).teleport(new Location(Bukkit.getWorld("1v1"), -14.720, 41.000000, 20.233, -179.1F, -1.7F));
				
                e.setCancelled(true);
            }
    }

    @EventHandler
    public void entityDamageVoid(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            if (!RegisterAbility.getAbility((Player) e.getEntity()).equals("Nenhum") && !RegisterAbility.getAbility((Player) e.getEntity()).equals("1v1")) {
                Player p = (Player) e.getEntity();
                p.setHealth(0.0D);
                e.setCancelled(true);
            }
        }
    }
}
