package me.samu.sitproject.manager;

import me.samu.sitproject.SitProject;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

public class SitManager {

    private Set<UUID> sitted;
    private SitProject sitProject;
    private BukkitTask task;

    public SitManager(SitProject sitProject) {
        this.sitted = new HashSet<>();
        this.sitProject = sitProject;
    }

    // VARIOUS CHECKS AND SETTERS
    public void unsitPlayer(Player player) {
        if (!sitted.contains(player.getUniqueId())) return;

        player.teleport(player.getLocation().add(0, 2, 0));
        sitted.remove(player.getUniqueId());

        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

    public void sitPlayer(Player player) {
        if (sitted.contains(player.getUniqueId())) return;

        sitted.add(player.getUniqueId());

        addChair(player.getLocation()).addPassenger(player);

        task = new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eᴘʀᴇѕѕ [ѕʜɪꜰᴛ] ᴛᴏ ᴅɪѕᴍᴏᴜɴᴛ!"));
            }
        }.runTaskTimer(sitProject, 1, 1);
    }

    private boolean isSitting(Player player) {
        return sitted.contains(player.getUniqueId());
    }

    // ADD AN ARMORSTAND TO USE AS A VEHICLE
    private ArmorStand addChair(Location location) {
        World world = location.getWorld();
        ArmorStand chair = (ArmorStand) world.spawnEntity(location.subtract(0, 1, 0), EntityType.ARMOR_STAND);
        chair.setInvisible(true);
        chair.setInvulnerable(true);
        chair.setGravity(false);
        chair.setSmall(true);

        return chair;
    }

    // TOGGLE SIT ( THE ONE USED IN THE COMMAND )
    public void toggleSit(Player player) {
        if (isSitting(player)) {
            unsitPlayer(player);
        } else {
            sitPlayer(player);
        }
    }
}
