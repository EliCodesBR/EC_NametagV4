package me.elias.enametag.bukkit.listener;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

import me.elias.enametag.core.event.PlayerTagChangeEvent;
import me.elias.enametag.core.event.ScoreboardUpdateEvent;
import me.elias.enametag.core.lothmanager.LothManager;
import me.elias.enametag.core.lothmanager.LothManager.Tag;

public class PlayerListener implements Listener {

	private static List<String> novidades;

	public PlayerListener() {
		novidades = new ArrayList<String>();
		novidades.add("Refeito do 0!");
		novidades.add("Agora TOTALMENTE configuráveis!");
		novidades.add("Bug da Tag desaparecer corrigido!");
		novidades.add("Versão 2.0.9 COMPLETA!");
	}

	public static List<String> getNews() {
		return novidades;
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onJoin(PlayerJoinEvent paramPlayerJoinEvent) {
		paramPlayerJoinEvent.setJoinMessage(null);
		Player localPlayer = paramPlayerJoinEvent.getPlayer();
		List<String> paramLocalList = LothManager.getInstance().getAllTags();
		for (String strings : paramLocalList) {
			String[] arrayOfString = strings.split(",");
			Tag paramLocalTag = LothManager.getInstance().getTag(arrayOfString);
			if (localPlayer.hasPermission(paramLocalTag.getPermission())) {
				LothManager.getInstance().set(localPlayer.getUniqueId(), paramLocalTag);
				break;
			}
		}
		if (!LothManager.getInstance().has(localPlayer.getUniqueId())) {
			for (String strings : paramLocalList) {
				String[] arrayOfString = strings.split(",");
				Tag paramLocalTag = LothManager.getInstance().getTag(arrayOfString);
				if (paramLocalTag.getPermission().equalsIgnoreCase("none")) {
					LothManager.getInstance().set(localPlayer.getUniqueId(), paramLocalTag);
					break;
				}
			}
		}
		PlayerTagChangeEvent paramPlayerTagChangeEvent = new PlayerTagChangeEvent(localPlayer,
				LothManager.getInstance().get(localPlayer.getUniqueId()));
		Bukkit.getPluginManager().callEvent(paramPlayerTagChangeEvent);
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onQuit(PlayerQuitEvent paramPlayerQuitEvent) {
		paramPlayerQuitEvent.setQuitMessage(null);
		Player localPlayer = paramPlayerQuitEvent.getPlayer();
		if (LothManager.getInstance().has(localPlayer.getUniqueId()))
			LothManager.getInstance().clear(localPlayer.getUniqueId());
	}

	@EventHandler(priority = EventPriority.MONITOR)
	public void onTagChange(PlayerTagChangeEvent paramPlayerTagChangeEvent) {
		Player localPlayer = paramPlayerTagChangeEvent.getPlayer();
		Tag localTag = paramPlayerTagChangeEvent.getTag();
		Scoreboard localScoreboard = localPlayer.getScoreboard();
		if (localScoreboard == null) {
			localScoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
			localPlayer.setScoreboard(localScoreboard);
		}
		Team localTeam = (localScoreboard.getTeam(localTag.getOrder()) == null
				? localScoreboard.registerNewTeam(localTag.getOrder())
				: localScoreboard.getTeam(localTag.getOrder()));
		if (!localTeam.hasEntry(localPlayer.getName()))
			localTeam.addEntry(localPlayer.getName());
		localTeam.setPrefix(localTag.getPrefix());
		localTeam.setSuffix(localTag.getSuffix());
		localPlayer.setDisplayName(localTag.getPrefix() + localPlayer.getName() + localTag.getSuffix());
		//ScoreboardUpdateEvent//
	}
	
	@EventHandler(priority = EventPriority.MONITOR)
	public void onScoreboardUpdate(ScoreboardUpdateEvent paramScoreboardUpdateEvent) {
		Player localPlayer = paramScoreboardUpdateEvent.getPlayer();
		Scoreboard localScoreboard = localPlayer.getScoreboard();
		if (localScoreboard == null)
			return;
		for (Player players : Bukkit.getOnlinePlayers()) {
			if (players == localPlayer)
				continue;
			if (localScoreboard == players.getScoreboard())
				continue;
			Tag localTag = LothManager.getInstance().get(players.getUniqueId());
			Team localTeam = (localScoreboard.getTeam(localTag.getOrder()) == null
					? localScoreboard.registerNewTeam(localTag.getOrder())
					: localScoreboard.getTeam(localTag.getOrder()));
			if (!localTeam.hasEntry(players.getName()))
				localTeam.addEntry(players.getName());
			localTeam.setPrefix(localTag.getPrefix());
			localTeam.setSuffix(localTag.getSuffix());
		}
	}
}
