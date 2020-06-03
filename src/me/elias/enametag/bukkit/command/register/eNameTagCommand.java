package me.elias.enametag.bukkit.command.register;

import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.elias.enametag.api.InventoryAPI;
import me.elias.enametag.central.Main;

public class eNameTagCommand implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player)sender;
		if(label.equalsIgnoreCase("enametag")) {
			if(!p.hasPermission("enametag.admin")) {
				p.sendMessage(Main.getInstance().getConfig().getString("PermissionMsg").replace("&", "§"));
			}
			if(args.length <= 0) {
				p.sendMessage("§cUtilize: /enametag [recarregar, setarnpc, removernpc]");
				p.sendMessage("§cUse: /enametag [reload, setnpc, removenpc]");
				return true;
			}
			if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("recarregar")) {
				InventoryAPI.open_reload_confirm(p);
			}
			if(args[0].equalsIgnoreCase("setnpc") || args[0].equalsIgnoreCase("setarnpc")) {
				InventoryAPI.open_npc_confirm(p);
			}
			if(args[0].equalsIgnoreCase("removenpc") || args[0].equalsIgnoreCase("removernpc")) {
				ItemStack it = new ItemStack(Material.BLAZE_ROD);
				ItemMeta mt = it.getItemMeta();
				mt.setDisplayName("§aRemovedor de NPCs");
				it.setItemMeta(mt);
				p.getInventory().addItem(it);
			}
		}
		return false;
	}

}
