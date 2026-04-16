package pvp.sunshine.bukkit.manager.duels;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import pvp.sunshine.bukkit.ability.RegisterAbility;
import pvp.sunshine.bukkit.utils.PvPUtil;

public class Complementer {

	public static ItemStack UmVUm;
	private static ItemMeta UmVUmM;
	public static ItemStack Sair;
	public static ItemStack Randomico;
	private static ItemMeta RandomicoM;

	public static void items1v1(org.bukkit.entity.Player p) {
		UmVUm = new ItemStack(org.bukkit.Material.BLAZE_ROD);
		UmVUmM = UmVUm.getItemMeta();
		UmVUmM.setDisplayName("§eConvidar Jogadores");
		UmVUm.setItemMeta(UmVUmM);

		Randomico = new ItemStack(351, 1, (short) 8);
		RandomicoM = Randomico.getItemMeta();
		RandomicoM.setDisplayName("§e1v1 Rápido §8(§cDesativado§8)");
		Randomico.setItemMeta(RandomicoM);

		p.getInventory().clear();
		RegisterAbility.setAbility(p, "1v1");
		p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
		p.getActivePotionEffects().clear();
		p.getInventory().setArmorContents(null);
		p.getInventory().setItem(3, UmVUm);
		p.getInventory().setItem(5, Randomico);
		p.updateInventory();
	}

	public static void items1v1Partida(org.bukkit.entity.Player p) {
		p.getInventory().clear();
		p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
		p.getActivePotionEffects().clear();
		p.getInventory().setArmorContents(null);

		p.getInventory().setHelmet(new ItemStack(Material.IRON_HELMET));
		p.getInventory().setChestplate(new ItemStack(Material.IRON_CHESTPLATE));
		p.getInventory().setLeggings(new ItemStack(Material.IRON_LEGGINGS));
		p.getInventory().setBoots(new ItemStack(Material.IRON_BOOTS));

		ItemStack espada = new ItemStack(org.bukkit.Material.DIAMOND_SWORD);
		PvPUtil.giveSoup(p, 9);
		org.bukkit.inventory.meta.ItemMeta espadaM = espada.getItemMeta();
		espadaM.addEnchant(org.bukkit.enchantments.Enchantment.DAMAGE_ALL, 1, true);
		espada.setItemMeta(espadaM);
		p.getActivePotionEffects().forEach(potionEffect -> p.removePotionEffect(potionEffect.getType()));
		p.getInventory().setItem(0, espada);
		p.updateInventory();
	}

}
