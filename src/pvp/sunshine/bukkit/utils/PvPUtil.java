package pvp.sunshine.bukkit.utils;

import java.lang.reflect.Field;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.potion.PotionEffect;
import pvp.sunshine.bukkit.manager.inventory.BoxSystemType;
import pvp.sunshine.bukkit.manager.mysql.connections.SQLPvP;

public class PvPUtil {
	
	public static void InventoryAdapter(Player player) {
		player.setHealth(20.0D);
		player.setFireTicks(0);
		player.setFoodLevel(20);
		clearActivePotionEffects(player);
		player.setAllowFlight(false);
		player.setFlying(false);
		player.setGameMode(GameMode.ADVENTURE);
		clearInventory(player);
	}

	private static void clearActivePotionEffects(Player player) {
		for (PotionEffect effect : player.getActivePotionEffects()) {
			player.removePotionEffect(effect.getType());
		}
	}

	private static void clearInventory(Player player) {
		player.getInventory().clear();
		player.getInventory().setArmorContents(null);
		player.updateInventory();
	}

	public static void getPvPUtils(Player player) {
		giveItem(player, Material.BOWL, 32, 13);
		giveItem(player, Material.BROWN_MUSHROOM, 32, 14);
		giveItem(player, Material.RED_MUSHROOM, 32, 15);
		giveSoup(player, 33);
	}

	public static void getRecraft(Player player) {
		giveItem(player, Material.BOWL, 32, 13);
		giveItem(player, Material.BROWN_MUSHROOM, 32, 14);
		giveItem(player, Material.RED_MUSHROOM, 32, 15);
	}

	private static void giveItem(Player player, Material material, int amount, int slot) {
		ItemStack item = new ItemStack(material, amount);
		player.getInventory().setItem(slot, item);
	}

	public static void giveSoup(Player p, int quantas) {
		ItemStack sopa = new ItemStack(Material.MUSHROOM_SOUP);
		ItemMeta msopa = sopa.getItemMeta();
		sopa.setItemMeta(msopa);

		for (int i = 0; i < quantas; i++) {
			int emptySlot = p.getInventory().firstEmpty();
			if (emptySlot != -1) {
				p.getInventory().setItem(emptySlot, sopa);
			} else {
				break;
			}
		}
	}


	private static ItemStack createCustomItem(Material material, String displayName) {
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		meta.setDisplayName(displayName);
		item.setItemMeta(meta);
		return item;
	}

	public static void getPotion(Player player, int amount) {
		ItemStack potion = createCustomPotion();
		for (int i = 0; i < amount; i++) {
			player.getInventory().addItem(new ItemStack[] { potion });
		}
	}

	private static ItemStack createCustomPotion() {
		ItemStack potion = new ItemStack(Material.POTION, 1, (short) 16421);
		ItemMeta potionMeta = potion.getItemMeta();
		potionMeta.setDisplayName("§aPotion");
		potion.setItemMeta(potionMeta);
		return potion;
	}

	public static void getItemStackAdmin(Player p, int quantidade, String nome, int lugar) {
		ItemStack item = new ItemStack(Material.INK_SACK, quantidade, (short) 10);
		ItemMeta itemmeta = item.getItemMeta();
		itemmeta.setDisplayName(nome);
		item.setItemMeta(itemmeta);
		p.getInventory().setItem(lugar, item);
	}

	public static void getItemStack(Player player, Material material, int amount, String name, int slot) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		item.setItemMeta(itemMeta);
		player.getInventory().setItem(slot, item);
	}

	public static void getItem(Player player, Material material, int amount, String name, int slot) {
		getItemStack(player, material, amount, name, slot);
	}

	public static void getItemChant(Player player, Material material, int amount, String name, int slot,
			Enchantment enchantment, int level, boolean b) {
		ItemStack item = new ItemStack(material, amount);
		ItemMeta itemMeta = item.getItemMeta();
		itemMeta.setDisplayName(name);
		itemMeta.addEnchant(enchantment, level, true);
		item.setItemMeta(itemMeta);
		player.getInventory().setItem(slot, item);
	}

	public static void getEnchantedGoldenApple(Player player, int amount, String name, int slot) {
		ItemStack enchantedApple = createEnchantedGoldenApple(amount, name);
		player.getInventory().setItem(slot, enchantedApple);
	}

	private static ItemStack createEnchantedGoldenApple(int amount, String name) {
		ItemStack enchantedApple = new ItemStack(Material.GOLDEN_APPLE, amount, (short) 1);
		ItemMeta itemMeta = enchantedApple.getItemMeta();
		itemMeta.setDisplayName(name);

		enchantedApple.setItemMeta(itemMeta);

		return enchantedApple;
	}

	public static void setTab(Player player, String header, String footer) {
		try {
			IChatBaseComponent headerComponent = IChatBaseComponent.ChatSerializer.a("{'text': '" + header + "'}");
			IChatBaseComponent footerComponent = IChatBaseComponent.ChatSerializer.a("{'text': '" + footer + "'}");
			PacketPlayOutPlayerListHeaderFooter packetPlayOut = new PacketPlayOutPlayerListHeaderFooter(
					headerComponent);
			Field field = packetPlayOut.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(packetPlayOut, footerComponent);
			(((CraftPlayer) player).getHandle()).playerConnection.sendPacket(packetPlayOut);
		} catch (Exception exception) {
			exception.printStackTrace();
		}
	}

	public static void spawnItem(Player player) {
		getItem(player, Material.CHEST, 1, "§aKit's", 3);
		getItem(player, Material.COMPASS, 1, "§aWarp's", 5);
		getItem(player, Material.DIAMOND, 1, "§aLoja de Kit's", 4);
		getItem(player, Material.REDSTONE_COMPARATOR, 1, "§aStatus", 8);
		getItem(player, Material.ANVIL, 1, "§aExtras", 1);
		getItem(player, Material.STORAGE_MINECART, 1, "§aSuas Caixa's", 0);
	}

	private static ItemStack createSkullItem(Player player) {
		ItemStack perfil = new ItemStack(Material.SKULL_ITEM);
		perfil.setDurability((short) 3);
		SkullMeta skullMeta = (SkullMeta) perfil.getItemMeta();
		skullMeta.setDisplayName("§aSeu Perfil");
		skullMeta.setOwner(player.getName());
		perfil.setItemMeta((ItemMeta) skullMeta);
		return perfil;
	}
}
