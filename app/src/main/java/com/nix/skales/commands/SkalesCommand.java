package com.nix.skales.commands;

import org.bukkit.command.CommandExecutor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;


public class SkalesCommand implements CommandExecutor{
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String [] args) {
        if (args.length > 0){
            if (args[0].equals("help")){
                String message = "&aSkales has one command, but this command has many possible arguments.\n\n&7/skales help\n&7/skales info\n&7/skales reload\n";
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            else if (args[0].equals("info")){
                String message = "&aPlaceholder message1";
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            else if (args[0].equals("reload")){
                String message = "&aPlaceholder message2";
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
            else {
                String message = "&aImproper syntax!\n\n&7/skales help\n&7/skales info\n&7/skales reload\n";
                sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
            }
        }
        else {
            String message = "&aYou must enter arguments with the command!\n\n&7/skales help\n&7/skales info\n&7/skales reload\n";
            sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
        }
        return true;
    }
}
