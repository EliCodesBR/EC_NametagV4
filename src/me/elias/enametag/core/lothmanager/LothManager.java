package me.elias.enametag.core.lothmanager;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;

import me.elias.enametag.central.Main;
import me.elias.enametag.core.event.ScoreboardUpdateEvent;

public class LothManager {

	private static final LothManager instance = new LothManager();
	private static final Plugin plugin = Main.getPlugin(Main.class);
	private static final PluginManager pluginmanager = Bukkit.getPluginManager();
	private static final Map<UUID, Tag> nametag = new ConcurrentHashMap<UUID, Tag>();

	public void update() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(plugin, new Runnable() {
			@Override
			public void run() {
				for (Player players : Bukkit.getOnlinePlayers())
					Bukkit.getPluginManager().callEvent(new ScoreboardUpdateEvent(players));
			}
		}, 0, 5L);
	}
	
	public PluginManager getPluginManager() {
		return pluginmanager;
	}

	public Plugin getPlugin() {
		return plugin;
	}

	public void info(String paramLocalInformation) {
		plugin.getLogger().info(paramLocalInformation);
	}

	public void clear(UUID paramLocalUUID) {
		nametag.remove(paramLocalUUID);
	}

	public boolean has(UUID paramLocalUUID) {
		return nametag.containsKey(paramLocalUUID);
	}

	public Tag get(UUID paramLocalUUID) {
		return nametag.get(paramLocalUUID);
	}

	public void set(UUID paramLocalUUID, Tag paramLocalTag) {
		nametag.put(paramLocalUUID, paramLocalTag);
	}

	public void set(UUID paramLocalUUID, String[] arrayOfString) {
		String prefix = arrayOfString[0].replace("&", "§");
		String suffix = arrayOfString[1].replace("&", "§");
		String name = arrayOfString[2].replace("&", "§");
		String order = arrayOfString[3].replace("&", "§");
		String permission = arrayOfString[4].replace("&", "§");
		Tag paramLocalTag = Tag.NAMETAG;
		paramLocalTag.setPrefix(prefix);
		paramLocalTag.setSuffix(suffix);
		paramLocalTag.setName(name);
		paramLocalTag.setOrder(order);
		paramLocalTag.setPermission(permission);
		nametag.put(paramLocalUUID, paramLocalTag);
	}

	public Tag getTag(String[] arrayOfString) {
		String prefix = arrayOfString[0].replace("&", "§");
		String suffix = arrayOfString[1].replace("&", "§");
		String name = arrayOfString[2].replace("&", "§");
		String order = arrayOfString[3].replace("&", "§");
		String permission = arrayOfString[4].replace("&", "§");
		Tag paramLocalTag = Tag.NAMETAG;
		paramLocalTag.setPrefix(prefix);
		paramLocalTag.setSuffix(suffix);
		paramLocalTag.setName(name);
		paramLocalTag.setOrder(order);
		paramLocalTag.setPermission(permission);
		return paramLocalTag;
	}

	public List<String> getAllTags() {
		return ((ArrayList<String>) plugin.getConfig().getStringList("NameTags.TagsList"));
	}

	public String[] findTagFromArgs(String args) {
		String[] paramLocalTagString = null;
		ArrayList<String> list = (ArrayList<String>) plugin.getConfig().getStringList("NameTags.TagsList");
		for (String strings : list) {
			String[] arrayOfString = strings.split(",");
			for (int i = 5; i < arrayOfString.length; i++) {
				if (arrayOfString[i].equalsIgnoreCase(args)) {
					paramLocalTagString = arrayOfString;
					break;
				}
			}
		}
		return paramLocalTagString;
	}

	public enum Tag {

		NAMETAG("", "", "", "", "", "", "");//

		private String preffix;
		private String suffix;
		private String name;
		private String order;
		private String permission;
		private List<String> aliases;

		Tag(String preffix, String suffix, String name, String order, String permission, String... aliases) {
			this.preffix = preffix;
			this.suffix = suffix;
			this.name = name;
			this.order = order;
			this.permission = permission;
			this.aliases = Arrays.asList(aliases);
		}

		public void setPrefix(String prefix) {
			this.preffix = prefix;
		}

		public void setSuffix(String suffix) {
			this.suffix = suffix;
		}

		public void setName(String name) {
			this.name = name;
		}

		public void setOrder(String order) {
			this.order = order;
		}

		public void setPermission(String permission) {
			this.permission = permission;
		}

		public void setAliases(String[] aliases) {
			this.aliases = Arrays.asList(aliases);
		}

		public String getPrefix() {
			return preffix;
		}

		public String getSuffix() {
			return suffix;
		}

		public String getName() {
			return name;
		}

		public String getOrder() {
			return order;
		}

		public String getPermission() {
			return permission;
		}

		public List<String> getAliases() {
			return aliases;
		}
	}

	public static LothManager getInstance() {
		return instance;
	}
}
