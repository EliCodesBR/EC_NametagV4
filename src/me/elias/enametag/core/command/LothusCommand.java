package me.elias.enametag.core.command;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import me.elias.enametag.central.Main;

public abstract class LothusCommand extends Command implements CommandManager {

	private CommandSender sender;
	
	public LothusCommand(String name) {
		super(name);
	}

	@Override
	public boolean isPlayer() {
		return (sender instanceof Player);
	}

	@Override
	public Player getPlayer() {
		return ((Player) sender);
	}

	@Override
	public ConsoleCommandSender getConsoleSender() {
		return ((ConsoleCommandSender) sender);
	}

	@Override
	public void sendMessage(String message) {
		sender.sendMessage(message);
	}

	@Override
	public void sendMessage(String[] message) {
		sender.sendMessage(message);	
	}

	@Override
	public void setSender(CommandSender sender) {
		this.sender = sender;		
	}

	@Override
	public void sendNoPermissionMessage() {
		sender.sendMessage(Main.getInstance().getConfig().getString("PermissionMsg").replace("&", "§"));		
	}

	@Override
	public void sendOnlyInGameMessage() {
		sender.sendMessage(Main.getInstance().getConfig().getString("CmdInConsole").replace("&", "§"));		
	}

	@Override
	public abstract boolean execute(CommandSender sender, String label, String[] args);

	@Override
	public boolean hasPermission(String permission) {
		return sender.hasPermission(permission);
	}
}
