package com.nix.skales.util.log;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//Logger. Code from ShaneBee
public class Util {
    private static final String PREFIX = "&7[&aSkales&7] ";
    private static final String PREFIX_ERROR = "&7[&aSkales &cERROR&7] ";
    private static final Pattern HEX_PATTERN = Pattern.compile("<#([A-Fa-f\\d]){6}>");

    @SuppressWarnings("deprecation") // Paper deprecation
    public static String getColString(String string) {
        Matcher matcher = HEX_PATTERN.matcher(string);
        string = HEX_PATTERN.matcher(string).replaceAll("");
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static void log(String format, Object... objects) {
        String log = String.format(format, objects);
        Bukkit.getConsoleSender().sendMessage(getColString(PREFIX + log));
    }
}
