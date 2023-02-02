package de.verdreckt.spawnprotection;

import de.verdreckt.spawnprotection.command.LocationCommand;
import de.verdreckt.spawnprotection.config.Config;
import de.verdreckt.spawnprotection.listener.PlayerBreakListener;
import de.verdreckt.spawnprotection.listener.PlayerPlaceListener;
import de.verdreckt.spawnprotection.manager.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Objects;

public class SpawnProtection extends JavaPlugin {

    public static String prefix = "§8[§6Protection§8] §7";
    private static Config config;
    private static LocationManager locationManager;
    private static SpawnProtection instance;

    @Override
    public void onLoad() {
        instance = this;
        if(!this.getDataFolder().exists()) {
            this.getDataFolder().mkdir();
        }
        config = new Config("locations.yml", this.getDataFolder());
        locationManager = new LocationManager();
        Bukkit.broadcastMessage(prefix + "§7Das Plugin §6Protection §7wurde geladen.");
    }

    @Override
    public void onEnable() {
        registerEvent(new PlayerBreakListener(locationManager));
        registerEvent(new PlayerPlaceListener(locationManager));
        registerCommand("location", new LocationCommand());
    }

    private void registerEvent(Listener listener) {
        this.getServer().getPluginManager().registerEvents(listener, this);
    }

    private void registerCommand(String command, CommandExecutor executor) {
        Objects.requireNonNull(this.getCommand(command)).setExecutor(executor);
    }


    public Config getconfig() {
        return config;
    }

    public static SpawnProtection getInstance() {
        return instance;
    }



}
