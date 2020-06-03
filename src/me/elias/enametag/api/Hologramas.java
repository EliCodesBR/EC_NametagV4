package me.elias.enametag.api;

import org.bukkit.entity.Player;

import com.gmail.filoghost.holographicdisplays.api.Hologram;

public class Hologramas {

	private Player p;
	private Hologram holo;
	
	public Hologramas(Player p) {
		this.p = p;
	}	
	public Player getP() {
		return p;
	}

	public Hologram getHolo() {
		return holo;
	}

	public void setHolo(Hologram holo) {
		this.holo = holo;
	}
}
