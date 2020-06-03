package me.elias.enametag.bukkit.listener;

import org.bukkit.Effect;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.entity.Villager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.player.PlayerInteractEntityEvent;

import com.gmail.filoghost.holographicdisplays.api.Hologram;
import com.gmail.filoghost.holographicdisplays.api.HologramsAPI;

import me.elias.enametag.api.InventoryAPI;
import me.elias.enametag.api.TitlesAPI;
import me.elias.enametag.central.Main;

public class NpcListener implements Listener {

	@EventHandler
    public void Interact(final PlayerInteractEntityEvent e) {
        final Player p = e.getPlayer();
        final Villager villager = (Villager)e.getRightClicked();
        e.setCancelled(true);
        if (villager.hasMetadata("TagsNPC")) {
        	InventoryAPI.open_npc_inventory(p);
        }
    }
    
    @SuppressWarnings("deprecation")
	@EventHandler
    public void Dano(final EntityDamageByEntityEvent e) {
        if (e.getEntity() instanceof Villager) {
            final Villager villager = (Villager)e.getEntity();
            if (villager.hasMetadata("TagsNPC")) {
                final Player p = (Player)e.getDamager();
                if (e.getDamager() instanceof Player) {
                    if (p.hasPermission("enametag.admin") && p.getItemInHand().getType() == (Material.BLAZE_ROD)) {
                        villager.setHealth(0.0);
                        TitlesAPI.mandarTitulo(p, "§6ASSASINATO!", "§eVocê assasinou o NPC :c", 1);
                        p.playSound(p.getLocation(), Sound.ORB_PICKUP, 1.0f, 1.0f);
                		p.playEffect(p.getLocation(), Effect.CRIT, 10);

                      for (Hologram hologram : HologramsAPI.getHolograms(Main.getInstance())) {
                          Location playerLoc   = p.getLocation();
                          Location hologramLoc = hologram.getLocation();

                          if (playerLoc.distance(hologramLoc) < 10) {
                              hologram.delete();
                          }else {
                        	  e.setCancelled(true);
                          }
                      }
                    }
                    else {
                        e.setCancelled(true);
                    }
                }
                else {
                    e.setCancelled(true);
                }
            }
        }
    }
}
