package net.kombopvp.warp.provider;


import net.kombopvp.pvp.command.NoBreakEvent;
import net.kombopvp.pvp.kit.KitManager;
import net.kombopvp.pvp.kit.KitManager2;
import net.kombopvp.pvp.warp.WarpHandle;
import net.wavemc.core.bukkit.item.ItemBuilder;

import java.util.HashMap;

import org.bukkit.Material;

import org.bukkit.entity.Player;


public class Duels extends WarpHandle {
	public static HashMap<String, Boolean> protector = new HashMap();	
	public static HashMap<Player, Player> duels = new HashMap();	
	public static HashMap<Player, Player> glad = new HashMap();	
	public static HashMap<Player, Player> b = new HashMap();
	public static HashMap<Player, Player> c = new HashMap();
	@Override
	public void execute(Player player) {
		super.execute(player);
	      player.getInventory().setItem(0, new ItemBuilder("§bDesafiar §7(Clique)", Material.BLAZE_ROD)
					.nbt("cancel-drop")
					.nbt("cancel-click")
					.nbt("fila", "challenge")
					.toStack()
			);
	 	 player.getInventory().setItem(8, new ItemBuilder("§bRetornar §7(Clique)", Material.BED)
					.nbt("cancel-drop")
					.nbt("cancel-click")
					.nbt("voltar", "spawn")
					.toStack()
			);
	 	NoBreakEvent.embuild.remove(player);
protector.put(player.getName(), true);
		KitManager.getPlayer(player.getName()).removeKit();
		KitManager2.getPlayer(player.getName()).removekit2();
		player.getInventory().setHeldItemSlot(0);
	}
}