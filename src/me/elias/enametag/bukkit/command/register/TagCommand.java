package me.elias.enametag.bukkit.command.register;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.elias.enametag.api.TitlesAPI;
import me.elias.enametag.core.command.LothusCommand;
import me.elias.enametag.core.event.PlayerTagChangeEvent;
import me.elias.enametag.core.lothmanager.LothManager;
import me.elias.enametag.core.lothmanager.LothManager.Tag;

public class TagCommand extends LothusCommand {

	public TagCommand(String name) {
		super(name);
	}

	@Override
	public void setCommandAliases() {
		setAliases(Arrays.asList("nametag", "tag", "tags", "taglist"));
	}

	@Override
	public boolean execute(CommandSender sender, String label, String[] args) {
		setSender(sender);
		if (isPlayer()) {
			Player localPlayer = getPlayer();
			if (args.length == 0) {
				List<String> paramLocalList = new LinkedList<String>();
				List<String> paramLocalTagList = LothManager.getInstance().getAllTags();
				for (String strings : paramLocalTagList) {
					String[] arrayOfString = strings.split(",");
					Tag paramLocalTag = LothManager.getInstance().getTag(arrayOfString);
					if (!paramLocalList.contains(paramLocalTag.getName())) {
						if (paramLocalTag.getPermission().equalsIgnoreCase("none"))
							paramLocalList.add(paramLocalTag.getName());
						if (hasPermission(paramLocalTag.getPermission())
								&& !paramLocalList.contains(paramLocalTag.getName()))
							paramLocalList.add(paramLocalTag.getName());
					}
				}
				String localPlayerTagList = paramLocalList.toString().replace("[", "").replace("]", "").replace(",",
						"§f,");
				for (String strings : LothManager.getInstance().getPlugin().getConfig()
						.getStringList("TagCommandSintaxe.Messages")) {
					strings = strings.replace("%tags%", localPlayerTagList).replace("&", "§");
					localPlayer.sendMessage(strings);
					TitlesAPI sintaxe = new TitlesAPI("&aPor Favor", "§fUtilize: /tag <tag>", 0, 1, 1);
					
					sintaxe.send(localPlayer);
				}
				return true;
			} else if (args.length == 1) {
				String paramLocalArgs = args[0];
				String[] arrayOfString = LothManager.getInstance().findTagFromArgs(paramLocalArgs);
				if (arrayOfString == null) {
					for (String strings : LothManager.getInstance().getPlugin().getConfig()
							.getStringList("TagCommandNotFound.Messages")) {
						strings = strings.replace("&", "§");
						localPlayer.sendMessage(strings);
						TitlesAPI found = new TitlesAPI("&4§lERRO", strings, 0, 1, 1);
						
						found.send(localPlayer);
					}
					return true;
				} else {
					Tag paramLocalTag = LothManager.getInstance().getTag(arrayOfString);
					if (paramLocalTag.getPermission().equalsIgnoreCase("none")) {
						PlayerTagChangeEvent paramPlayerTagChangeEvent = new PlayerTagChangeEvent(localPlayer,
								paramLocalTag);
						Bukkit.getPluginManager().callEvent(paramPlayerTagChangeEvent);
						for (String strings : LothManager.getInstance().getPlugin().getConfig()
								.getStringList("TagCommandSucessChanged.Messages")) {
							strings = strings.replace("%tag%", paramLocalTag.getName()).replace("&", "§");
							localPlayer.sendMessage(strings);
							TitlesAPI changed = new TitlesAPI("&a&lSUCESSO", strings, 0, 1, 1);
							
							changed.send(localPlayer);
						}
						return true;
					} else if (!hasPermission(paramLocalTag.getPermission())) {
						for (String strings : LothManager.getInstance().getPlugin().getConfig()
								.getStringList("TagCommandNoPermission.Messages")) {
							strings = strings.replace("%tag%", paramLocalTag.getName()).replace("&", "§");
							localPlayer.sendMessage(strings);
							TitlesAPI changed = new TitlesAPI("&4§lERRO", strings, 0, 1, 1);
							
							changed.send(localPlayer);
						}
						return true;
					} else {
						PlayerTagChangeEvent paramPlayerTagChangeEvent = new PlayerTagChangeEvent(localPlayer,
								paramLocalTag);
						Bukkit.getPluginManager().callEvent(paramPlayerTagChangeEvent);
						for (String strings : LothManager.getInstance().getPlugin().getConfig()
								.getStringList("TagCommandSucessChanged.Messages")) {
							strings = strings.replace("%tag%", paramLocalTag.getName()).replace("&", "§");
							localPlayer.sendMessage(strings);
							TitlesAPI changed1 = new TitlesAPI("&a&lSUCESSO", strings, 0, 1, 1);
							
							changed1.send(localPlayer);
						}
						return true;
					}
				}
			} else {
				for (String strings : LothManager.getInstance().getPlugin().getConfig()
						.getStringList("TagCommandErrorSintaxe.Messages")) {
					strings = strings.replace("&", "§");
					localPlayer.sendMessage(strings);
					TitlesAPI changed = new TitlesAPI("&aPor Favor", "§fUtilize: /tag <tag>", 0, 1, 1);
					
					changed.send(localPlayer);
				}
				return true;
			}
		} else {
			sendOnlyInGameMessage();
			return true;
		}
	}
}
