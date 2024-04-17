package com.walrusone.skywarsreloaded.utilities;

import com.walrusone.skywarsreloaded.SkyWarsReloaded;
import net.milkbowl.vault.chat.Chat;
import net.milkbowl.vault.economy.Economy;
import net.milkbowl.vault.economy.EconomyResponse;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

public class VaultUtils {
    private static VaultUtils instance;
    private Economy econ = null;
    private Chat chat = null;

    private VaultUtils() {
        if ((!setupEconomy()) && (!setupChat())) {
            SkyWarsReloaded.get().getLogger().info("ERROR: Vault Dependency was not found. Install Vault or turn off Economy in the Config!");
        }
    }

    public static VaultUtils get() {
        if (instance == null) {
            instance = new VaultUtils();
        }
        return instance;
    }

    private boolean setupEconomy() {
        if (Bukkit.getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    private boolean setupChat() {
        RegisteredServiceProvider<Chat> rsp = Bukkit.getServer().getServicesManager().getRegistration(Chat.class);
        chat = rsp.getProvider();
        return chat != null;
    }


    public boolean canBuy(Player player, double cost) {
        return (econ != null) && (econ.getBalance(player) >= cost);
    }

    public boolean payCost(Player player, double cost) {
        if (econ == null) return false;
        try {
            EconomyResponse rp = econ.withdrawPlayer(player, cost);
            return rp.transactionSuccess();
        } catch (Exception e) {
            this.handleException(e);
        }
        return false;
    }

    public double getBalance(Player player) {
        if (econ == null) return 0.0D;
        try {
            return econ.getBalance(player);
        } catch (Exception e) {
            this.handleException(e);
        }
        return 0.0D;
    }

    public void give(Player win, int i) {
        if (econ == null) return;
        try {
            econ.depositPlayer(win, i);
        } catch (Exception e) {
            this.handleException(e);
        }
    }

    public Chat getChat() {
        return chat;
    }

    // PRIVATE UTILS

    private void handleException(Exception e) {
        if (SkyWarsReloaded.getCfg().debugEnabled()) e.printStackTrace();
        else SkyWarsReloaded.get().getLogger().severe(
                "An exception was thrown while attempting to deposit eco: " + e.getMessage() +
                        ". Please enable debugMode in the config file before reporting this issue!");
    }

}
