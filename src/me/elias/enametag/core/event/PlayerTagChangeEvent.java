package me.elias.enametag.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

import me.elias.enametag.core.lothmanager.LothManager.Tag;

public class PlayerTagChangeEvent extends PlayerEvent {
	
	private static final HandlerList Handlers = new HandlerList();
	private Tag paramLocalTag;

	public PlayerTagChangeEvent(Player who, Tag paramLocalTag) {
		super(who);
		this.paramLocalTag = paramLocalTag;
	}

	@Override
	public HandlerList getHandlers() {
		return Handlers;
	}

	public Tag getTag() {
		return paramLocalTag;
	}
	
	public static HandlerList getHandlerList() {
		return Handlers;
	}
}
