package me.elias.enametag.central;

import java.lang.reflect.Field;

import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.command.CommandMap;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftLivingEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Villager;
import org.bukkit.plugin.java.JavaPlugin;

import me.elias.enametag.api.InventoryAPI;
import me.elias.enametag.bukkit.command.register.TagCommand;
import me.elias.enametag.bukkit.command.register.eNameTagCommand;
import me.elias.enametag.bukkit.listener.NpcListener;
import me.elias.enametag.bukkit.listener.PlayerListener;
import me.elias.enametag.core.command.LothusCommand;
import me.elias.enametag.core.lothmanager.LothManager;
import net.minecraft.server.v1_8_R1.EntityLiving;

public class Main extends JavaPlugin {
	
	public eConfig changelogs = new eConfig(this, "changelogs.yml");
	
	public void onEnable() {
		changelogs.saveDefaultConfig();
		saveDefaultConfig();
		LothManager.getInstance().update();
		LothManager.getInstance().getPluginManager().registerEvents(new InventoryAPI(), this);
		LothManager.getInstance().getPluginManager().registerEvents(new NpcListener(), this);
		LothManager.getInstance().getPluginManager().registerEvents(new PlayerListener(), this);
		register(new TagCommand("tag"));
		getCommand("enametag").setExecutor(new eNameTagCommand());
		for (String strings : PlayerListener.getNews()) {
			LothManager.getInstance().info(strings);
		}
		for(World worlds : Bukkit.getWorlds()) {
			for(Entity entidades : worlds.getEntities()) {
				if(entidades instanceof Villager) {
					Villager v = (Villager)entidades;
					if(v.hasMetadata("TagsNPC")) {
						EntityLiving handle = ((CraftLivingEntity) v).getHandle();
					      handle.getDataWatcher().watch(15, (byte) 1);
					      handle.b(true);
					}
				}
			}
		}
	}
	
	public void onDisable() {
		// null
	}
	
	private void register(LothusCommand command) {
		try {
			Field commandMapField = Bukkit.getServer().getClass().getDeclaredField("commandMap");
			commandMapField.setAccessible(true);
			CommandMap commandMap = (CommandMap) commandMapField.get(Bukkit.getServer());
			command.setCommandAliases();
			commandMap.register("nametag", command);
			commandMap.register("ent", command);
		} catch (IllegalAccessException | IllegalArgumentException | NoSuchFieldException | SecurityException e) {
			e.printStackTrace();
		}
	}

	public static Main getInstance() {
		return null;
	}
}
