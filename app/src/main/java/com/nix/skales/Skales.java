package com.nix.skales;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.Bukkit;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import com.nix.skales.utils.PackageLoader;
import com.nix.skales.commands.SkalesCommand;
import com.olyno.skriptmigrate.SkriptMigrate;
import org.bukkit.command.PluginCommand;
import ch.njol.skript.Skript;
import ch.njol.skript.SkriptAddon;


public class Skales extends JavaPlugin {

    public static Skales instance;
    public static FileConfiguration config;
	SkriptAddon addon;

    //startup logic
	public void onEnable() {
	    long start = System.currentTimeMillis();
	    getLogger().info("Unpackaging mental issues...");
	    getLogger().info("Registering syntax...");
	    getLogger().info("Enabling commands...");
	    
        getCommand("skales").setExecutor(new SkalesCommand());
        Util.log("Test %", 4);
        instance = this;
        addon = Skript.registerAddon(this);
        try {
            addon.loadClasses("com.nix.skales.skript");
        } catch (IOException e) {
            e.printStackTrace();
        }

        // Register Metrics
        Metrics metrics = new Metrics(this);
        metrics.addCustomChart(new Metrics.SimplePie("used_language", () ->
                getConfig().getString("language", "en")));
        metrics.addCustomChart(new Metrics.SimplePie("skript_version", () ->
                Bukkit.getServer().getPluginManager().getPlugin("Skript").getDescription().getVersion()));
        metrics.addCustomChart(new Metrics.SimplePie("Skord_version", () ->
                this.getDescription().getVersion()));
        metrics.addCustomChart(new Metrics.DrilldownPie("java_version", () -> {
            Map<String, Map<String, Integer>> map = new HashMap<>();
            String javaVersion = System.getProperty("java.version");
            Map<String, Integer> entry = new HashMap<>();
            entry.put(javaVersion, 1);
            if (javaVersion.startsWith("1.7")) {
                map.put("Java 1.7", entry);
            } else if (javaVersion.startsWith("1.8")) {
                map.put("Java 1.8", entry);
            } else if (javaVersion.startsWith("1.9")) {
                map.put("Java 1.9", entry);
            } else {
                map.put("Other", entry);
            }
            return map;
        }));

        // Register events
        new PackageLoader<Listener>("com.nix.skales.skript.events.bukkit", "register bukkit events").getList()
        .thenAccept(events -> {
            for (Listener evt : events) {
                getServer().getPluginManager().registerEvents(evt, this);
            }
        });

        // Setup migrations
		if (classExist("com.olyno.skriptmigrate.SkriptMigrate")) {
			SkriptMigrate.load(this);
		}

        if (!getDataFolder().exists()) {
			saveDefaultConfig();
		}

		config = getConfig();
	float finaltime = (System.currentTimeMillis() - start) / 1000;
	getLogger().info("Enabled in " + finaltime + " seconds.");

    }
    //shutdown logic
	public void onDisable() {
	    long start = System.currentTimeMillis();
	    getLogger().info("Bottling mental issues...");
	    getLogger().info("Removing Skales syntax...");
	    getLogger().info("Disabling commands...");
	    float finaltime = (System.currentTimeMillis() - start) / 1000;
	    getLogger().info("Disabled in " + finaltime + " seconds.");
	}

    private boolean classExist(String clazz) {
		try {
			Class.forName(clazz);
			return true;
		} catch (ClassNotFoundException e) {
			return false;
		}
	}

    public Config getPluginConfig() {
        return this.config;
    }

    public static Skales getPlugin() {
        return instance;
    }

    


}
