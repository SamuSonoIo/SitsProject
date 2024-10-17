package me.samu.sitproject.listener;

import me.samu.sitproject.SitProject;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class DismountEvent implements Listener {

    @EventHandler
    public void onDismount(EntityDismountEvent e) {
        if (!(e.getEntity() instanceof Player player)) {
            return;
        }

        if (!(e.getDismounted() instanceof ArmorStand)) {
            return;
        }

        SitProject.getSitManager().unsitPlayer(player);
        e.getDismounted().remove();
    }

    @EventHandler
    public void removeSeatOnDisconnect(PlayerQuitEvent e) {
        SitProject.getSitManager().unsitPlayer(e.getPlayer()); // Already checks if they're sitting or not.
    }

}

