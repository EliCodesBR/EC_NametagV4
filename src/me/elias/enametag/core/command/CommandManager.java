package me.elias.enametag.core.command;

import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public interface CommandManager {

	public boolean isPlayer();
	
	public boolean hasPermission(String permission);
	
	public Player getPlayer();
	
	public ConsoleCommandSender getConsoleSender();
	
	public void sendMessage(String message);
	
	public void sendMessage(String[] message);
	
	public void setSender(CommandSender sender);
	
	public void setCommandAliases();
	
	public void sendNoPermissionMessage();
	
	public void sendOnlyInGameMessage();
	
	
}
