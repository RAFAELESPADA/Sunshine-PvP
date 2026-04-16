package pvp.sunshine.bukkit.manager.inventory.action;

import java.util.ArrayList;

import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import pvp.sunshine.bukkit.manager.event.Flag;
import pvp.sunshine.bukkit.manager.event.SumoListener;
import pvp.sunshine.bukkit.manager.inventory.BoxSystemType;
import pvp.sunshine.bukkit.manager.inventory.ExtraType;
import pvp.sunshine.bukkit.manager.inventory.KitType;
import pvp.sunshine.bukkit.manager.inventory.ProfileType;
import pvp.sunshine.bukkit.manager.inventory.ShopAbilityType;
import pvp.sunshine.bukkit.manager.inventory.StatisticsType;
import pvp.sunshine.bukkit.manager.inventory.WarpType;
import pvp.sunshine.bukkit.manager.inventory.cosmetic.CosmeticInventory;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class OpenInventory implements Listener {

    public static ArrayList<String> caixa = new ArrayList<String>();

    @EventHandler
    public void onPlayerInteractEvent(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR && event.getAction() != Action.RIGHT_CLICK_BLOCK) {
            return;
        }

        ItemStack handItem = event.getItem();

        if (handItem != null && handItem.getType() == Material.CHEST) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aKit's")) {
                KitType.getHud(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
        if (handItem != null && handItem.getType() == Material.COMPASS) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aWarp's")) {
                WarpType.getWarp(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
        if (handItem != null && handItem.getType() == Material.ANVIL) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aExtras")) {
                ExtraType.getShops(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
        if (handItem != null && handItem.getType() == Material.REDSTONE_COMPARATOR) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aStatus")) {
                StatisticsType.getStatistics(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
        if (handItem != null && handItem.getType() == Material.SKULL_ITEM) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aSeu Perfil")) {
                ProfileType.getProfile(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
        if (event.getPlayer().getItemInHand().getType().equals(Material.REDSTONE)
                && event.getPlayer().getItemInHand().getItemMeta().hasDisplayName()
                && event.getPlayer().getItemInHand().getItemMeta().getDisplayName()
                .equalsIgnoreCase("§aSair da Fila")) {
            event.setCancelled(true);
            SumoListener.removePlayer(event.getPlayer());
            event.getPlayer().getInventory().setArmorContents(null);
            event.getPlayer().getInventory().clear();
            PvPUtil.spawnItem(event.getPlayer());
            Flag.setProtection(event.getPlayer(), true);
            event.getPlayer().sendMessage("§c§lSUMO §fVocê foi removido da fila.");
            if (WarpType.SumoRandom == event.getPlayer().getUniqueId()) {
               WarpType.SumoRandom = null;
            }
            SumoListener.BLOCK_COMMAND.remove(event.getPlayer().getName());
            event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.CLICK, 1.0f, 1.0f);
            return;
        }
        if (handItem != null && handItem.getType() == Material.DIAMOND) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aLoja de Kit's")) {
                ShopAbilityType.getShopKit(event.getPlayer());
                event.setCancelled(true);
                event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
            }
        }
        if (handItem != null && handItem.getType() == Material.STORAGE_MINECART) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aSuas Caixa's")) {
                if (BoxSystemType.getCaixa(event.getPlayer()) >= 1) {
                    if (OpenInventory.caixa.contains(event.getPlayer().getName())) {
                        event.getPlayer().sendMessage("§e§lCAIXAS §fAguarde alguns segundos para abrir uma caixa novamente.");
                        event.getPlayer().closeInventory();
                        return;
                    }
                    BoxSystemType.GuiCaixas(event.getPlayer());
                    BoxSystemType.removerCaixa(event.getPlayer(), 1);
                    OpenInventory.caixa.add(event.getPlayer().getName());
                } else {
                    event.getPlayer().sendMessage("§c§lCAIXAS §fVocê não possui nenhuma caixa para abrir.");
                }
                event.setCancelled(true);
            }
        }
        if (handItem != null && handItem.getType() == Material.BLAZE_POWDER) {
            ItemMeta itemMeta = handItem.getItemMeta();
            if (itemMeta != null && itemMeta.hasDisplayName()
                    && itemMeta.getDisplayName().equalsIgnoreCase("§aCosmético's")) {
                if (!event.getPlayer().hasPermission("pvp.cosmetic")) {
                    event.getPlayer().sendMessage("§c§lCOSMETICO §fAdquira seu §e§lCAVALEIRO§f para ter os cosméticos liberados em toda a network.");
                } else {
                    CosmeticInventory.guiCosmetic(event.getPlayer());
                    event.getPlayer().playSound(event.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
                }
                event.setCancelled(true);
            }
        }
    }
}