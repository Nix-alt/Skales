package com.nix.skales.utils;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CompletableFuture;

import ch.njol.skript.Skript;

public class PackageLoader<T> {

    private String packagePath;
    private String what;
    private CompletableFuture<LinkedList<T>> instancesList;

    public PackageLoader(String packagePath, String what) {
        this.packagePath = packagePath;
        this.what = what;
        instancesList = CompletableFuture.supplyAsync(() -> {
            LinkedList<T> classList = new LinkedList<>();
            try {
                List<Class<T>> classes = new PackageFilter<T>(this.packagePath).getClasses();
                for (Class<T> event : classes) {
                    T classInstance = event.getDeclaredConstructor().newInstance();
                    classList.add(classInstance);
                }
            } catch (IOException | InstantiationException | IllegalAccessException | IllegalArgumentException
                    | InvocationTargetException | NoSuchMethodException | SecurityException ex) {
                Skript.error("Something was wrong when trying to " + this.what + ". Please create an issue with this error:");
                ex.printStackTrace();
            }
            return classList;
        });
    }

    public CompletableFuture<LinkedList<T>> getList() {
        return instancesList;
    }
}

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
