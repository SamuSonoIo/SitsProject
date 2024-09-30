package me.samu.sitproject;

import me.samu.sitproject.commands.SitCommand;
import me.samu.sitproject.listener.DismountEvent;
import me.samu.sitproject.manager.SitManager;
import org.bukkit.plugin.java.JavaPlugin;

public final class SitProject extends JavaPlugin {

    private static SitManager sitManager;
    @Override
    public void onEnable() {
        // MANAGER
        sitManager = new SitManager(this);
        // COMMANDS
        getCommand("sit").setExecutor(new SitCommand());
        // LISTENER
        getServer().getPluginManager().registerEvents(new DismountEvent(), this);
    }

    public static SitManager getSitManager() { return sitManager; }
}
