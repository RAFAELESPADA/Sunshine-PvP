package me.ipreader;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import org.bukkit.Bukkit;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	protected String getIpLocalHost() {
        try {
            return (new BufferedReader(new InputStreamReader((new URL("http://checkip.amazonaws.com")).openStream())))
                    .readLine();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
	}
    
public void onEnable() {
	PluginManager pm = Bukkit.getPluginManager();
	pm.registerEvents(new Join(), this);
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
	Bukkit.getConsoleSender().sendMessage("IP REAL DA MAQUINA: " + getIpLocalHost());
}
	}