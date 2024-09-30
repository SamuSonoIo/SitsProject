package me.samu.sitproject.listener;

import me.samu.sitproject.SitProject;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDismountEvent;

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

}

