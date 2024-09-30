package me.samu.sitproject.commands;

import me.samu.sitproject.SitProject;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class SitCommand implements CommandExecutor {
    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String s, String[] args) {

        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage("Command only for players!");
            return true;
        }

        Player player = (Player) commandSender;

        SitProject.getSitManager().toggleSit(player);
        return true;
    }
}