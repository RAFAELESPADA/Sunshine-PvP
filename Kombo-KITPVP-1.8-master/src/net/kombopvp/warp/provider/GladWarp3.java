package net.kombopvp.warp.provider;




import net.kombopvp.pvp.command.NoBreakEvent;
import net.kombopvp.pvp.kit.KitManager;
import net.kombopvp.pvp.kit.KitManager2;
import net.kombopvp.pvp.warp.WarpHandle;
import net.wavemc.core.bukkit.item.ItemBuilder;

import java.util.HashMap;

import org.bukkit.Material;

import org.bukkit.entity.Player;


public class GladWarp3 extends WarpHandle {
	public static HashMap<String, Boolean> protector = new HashMap();	
	public static HashMap<Player, Player> duels = new HashMap();	
	public static HashMap<Player, Player> glad = new HashMap();	
	public static HashMap<Player, Player> b = new HashMap();
	public static HashMap<Player, Player> c = new HashMap();
	@Override
	public void execute(Player player) {
		super.execute(player);
	      player.getInventory().setItem(3, new ItemBuilder("§3Desafiar §7(Clique)", Material.IRON_FENCE)
					.nbt("cancel-drop")
					.nbt("cancel-click")
					.nbt("1v1g", "challenge")
					.toStack()
			);
	 	 player.getInventory().setItem(5, new ItemBuilder("§bRetornar §7(Clique)", Material.BED)
					.nbt("cancel-drop")
					.nbt("cancel-click")
					.nbt("voltar", "spawn")
					.toStack()
			);
	 	NoBreakEvent.embuild.remove(player);
protector.put(player.getName(), true);
		KitManager.getPlayer(player.getName()).removeKit();
		KitManager2.getPlayer(player.getName()).removekit2();
		player.getInventory().setHeldItemSlot(3);
	}
}