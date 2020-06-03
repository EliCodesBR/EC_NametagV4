package me.elias.enametag.core.event;

import org.bukkit.entity.Player;
import org.bukkit.event.HandlerList;
import org.bukkit.event.player.PlayerEvent;

public class ScoreboardUpdateEvent extends PlayerEvent {

	/**
	 * Evento responsável por atualizar as tags que desaparecem para alguns jogadores.
	 */
	
	private static final HandlerList Handlers = new HandlerList();

	public ScoreboardUpdateEvent(Player who) {
		super(who);
	}

	@Override
	public HandlerList getHandlers() {
		return Handlers;
	}
	
	public static HandlerList getHandlerList() {
		return Handlers;
	}
}
