package me.elias.enametag.api;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ItemStackAPI {
	
	public static ItemStack criarItem(String nome, Material material, Enchantment encantamento, int level, int qnt, int data) {
		ItemStack item = new ItemStack(material, qnt, (short)data);
		ItemMeta meta = item.getItemMeta();
		item.addUnsafeEnchantment(encantamento, level);
		meta.setDisplayName(nome);
		item.setItemMeta(meta);
		return item;
	}
}
