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

import java.util.HashMap;
import java.util.UUID;

public class SitManager {

    private final SitProject sitProject;
    private final HashMap<UUID, BukkitTask> sitters;

    public SitManager(SitProject sitProject) {
        this.sitProject = sitProject;
        this.sitters = new HashMap<>();
    }

    // VARIOUS CHECKS AND SETTERS
    public void unsitPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        if (!sitters.containsKey(uuid)) return;

        BukkitTask task = sitters.get(uuid);

        player.teleport(player.getLocation().add(0, 2, 0));
        sitters.remove(uuid);

        if (task != null && !task.isCancelled()) {
            task.cancel();
        }
    }

    public void sitPlayer(Player player) {
        UUID uuid = player.getUniqueId();
        if (sitters.containsKey(uuid)) return;

        addChair(player.getLocation()).addPassenger(player);
        BukkitTask task = new BukkitRunnable() {
            @Override
            public void run() {
                player.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent("§eᴘʀᴇѕѕ [ѕʜɪꜰᴛ] ᴛᴏ ᴅɪѕᴍᴏᴜɴᴛ!"));
            }
        }.runTaskTimer(sitProject, 1, 2);

        sitters.put(uuid, task);
    }

    private boolean isSitting(Player player) {
        return sitters.containsKey(player.getUniqueId());
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
