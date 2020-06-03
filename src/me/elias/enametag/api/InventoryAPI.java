package me.elias.enametag.api;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.craftbukkit.v1_8_R1.inventory.CraftItemStack;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.entity.Villager.Profession;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.metadata.FixedMetadataValue;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import me.elias.enametag.central.Main;
import me.elias.enametag.core.lothmanager.LothManager;
import net.minecraft.server.v1_8_R1.EntityLiving;

public class InventoryAPI implements Listener{
	
	public static ArrayList<String> lore = new ArrayList<>();
	public static net.minecraft.server.v1_8_R1.ItemStack st;
	
	public static void open_reload_confirm(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§7Confirmar/Cancelar - Reload");
		
		inv.setItem(12, ItemStackAPI.criarItem("§aConfirmar", Material.WOOL, Enchantment.DURABILITY, 1, 1, 5));
		inv.setItem(14, ItemStackAPI.criarItem("§cCancelar", Material.WOOL, Enchantment.DURABILITY, 1, 1, 14));
		
		p.openInventory(inv);
	}
	public static void open_npc_confirm(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§7Confirmar/Cancelar - NPC");
		
		inv.setItem(12, ItemStackAPI.criarItem("§aConfirmar", Material.WOOL, Enchantment.DURABILITY, 1, 1, 5));
		inv.setItem(14, ItemStackAPI.criarItem("§cCancelar", Material.WOOL, Enchantment.DURABILITY, 1, 1, 14));
		
		p.openInventory(inv);
	}
	public static void open_npc_inventory(Player p) {
		Inventory inv = Bukkit.createInventory(null, 3*9, "§7NPC - TAGs");
		
		inv.setItem(12, CraftItemStack.asBukkitCopy(st));
		inv.setItem(14, ItemStackAPI.criarItem("§eAlterar Tag", Material.EYE_OF_ENDER, null, 0, 1, 0));
		
		p.openInventory(inv);
	}
	public static void preloadSkull(){
        ItemStack skull = new ItemStack(Material.SKULL_ITEM, 1, (short)3);
        SkullMeta skMeta = (SkullMeta) skull.getItemMeta();
        skMeta.setOwner("Papers");
        skMeta.setDisplayName("§eLista de TAGs");
        lore.add("");
        lore.add("§6Lista: ");
        List<String> paramLocalTagList = LothManager.getInstance().getAllTags();
        lore.add("§8- §7" + paramLocalTagList);
        lore.add("");
        skMeta.setLore(lore);
        skull.setItemMeta(skMeta);
        st = CraftItemStack.asNMSCopy(skull);
    }
	@EventHandler
	public void onClick3(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player)e.getWhoClicked();
			if(e.getInventory().getTitle().equalsIgnoreCase("§7NPC - TAGs")) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eAlterar Tag")) {
					p.closeInventory(); 
					TitlesAPI title = new TitlesAPI("§aPor favor", "§fUtilize: /tag (sua tag).", 0, 2, 2);
					title.send(p);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§eLista de TAGs")) {
					e.setCancelled(true);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
				}
			}
		}
	}
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player)e.getWhoClicked();
			if(e.getInventory().getTitle().equalsIgnoreCase("§7Confirmar/Cancelar - Reload")) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aConfirmar")) {
					p.closeInventory(); 
					p.sendMessage("§8§lRELOAD §7Configurações reiniciadas com sucesso.");
					TitlesAPI title = new TitlesAPI("§a§lCONFIRMADO!", "§7Operação confirmada.", 0, 2, 2);
					title.send(p);
					Main.getInstance().reloadConfig();
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCancelar")) {
					p.closeInventory();
					TitlesAPI title = new TitlesAPI("§c§lCANCELADO!", "§7Operação cancelada.", 0, 2, 2);
					title.send(p);
					p.playSound(p.getLocation(), Sound.NOTE_PLING, 1, 1);
				}
			}
		}
	}
	@EventHandler
	public void onClick2(InventoryClickEvent e) {
		if(e.getWhoClicked() instanceof Player) {
			Player p = (Player)e.getWhoClicked();
			if(e.getInventory().getTitle().equalsIgnoreCase("§7Confirmar/Cancelar - NPC")) {
				e.setCancelled(true);
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§aConfirmar")) {
					p.closeInventory();
					            final Villager villager = (Villager)p.getWorld().spawn(p.getLocation(), (Class)Villager.class);
					            villager.setAdult();
					            villager.setProfession(Profession.BLACKSMITH);
					            villager.setCustomNameVisible(false);
					            villager.setMetadata("TagsNPC", new FixedMetadataValue(Main.getInstance(), new Object()));
					            
					            Hologramas holo = new Hologramas(p);
					            
					    		Hologram h = HologramsAPI.createHologram(Main.getInstance(), villager.getLocation().clone().add(0, 2.85, 0));
					    			
					    		h.appendTextLine("§e§lTAGS");
					    		h.appendTextLine("§7Clique direito para: §fVizualizar tags.");
					    		h.appendTextLine("§7Clique direito para: §fAltere sua tag.");
					    		h.appendTextLine("§7");
					    		
					    		holo.setHolo(h);
								
								EntityLiving handle = ((CraftLivingEntity) villager).getHandle();
							      handle.getDataWatcher().watch(15, (byte) 1);
							      handle.b(true);
							      
							    TitlesAPI.mandarTitulo(p, "§6NPC & Holograma", "§eforam criados com sucesso!", 2);
								p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1, 1);
								
						    }
				}
				if(e.getCurrentItem().getItemMeta().getDisplayName().equalsIgnoreCase("§cCancelar")) {
					p.closeInventory();
					TitlesAPI.mandarTitulo(p, "§6SUCESSO!", "§eA ação foi cancelada.", 1);
				    p.playSound(p.getLocation(), Sound.LEVEL_UP, 1, 1);
			}
		}
	}
}
