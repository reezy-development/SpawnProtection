package de.verdreckt.spawnprotection.manager;

import de.verdreckt.spawnprotection.SpawnProtection;
import de.verdreckt.spawnprotection.config.Config;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class LocationManager {
    private final File locationFile = new File("plugins/SpawnProtection/locations.yml");
    private final FileConfiguration locationConfiguration = YamlConfiguration.loadConfiguration(locationFile);

    public void save() throws IOException {
        locationConfiguration.save(locationFile);
    }

    public void createLocation(String locationName, Location location) throws IOException {
        locationConfiguration.set(locationName + ".world", Objects.requireNonNull(location.getWorld()).getName());
        locationConfiguration.set(locationName + ".x", location.getX());
        locationConfiguration.set(locationName + ".y", location.getY());
        locationConfiguration.set(locationName + ".z", location.getZ());
        save();
    }

    public void removeLocation(String locationName) throws IOException {
        locationConfiguration.set(locationName + ".world", null);
        locationConfiguration.set(locationName + ".x", null);
        locationConfiguration.set(locationName + ".y", null);
        locationConfiguration.set(locationName + ".z", null);
        save();
    }

    public Location get(String locationName) {
        if(locationConfiguration.contains(locationName + ".world")) {
            World world = Bukkit.getWorld(Objects.requireNonNull(locationConfiguration.getString(locationName + ".world")));
            double x = locationConfiguration.getDouble(locationName + ".x");
            double y = locationConfiguration.getDouble(locationName + ".y");
            double z = locationConfiguration.getDouble(locationName + ".z");

            return new Location(world, x, y, z);
        } else {
            return null;
        }
    }
    public void reloadLocations() {
        SpawnProtection.getInstance().getconfig().reload();
        FileConfiguration config = YamlConfiguration.loadConfiguration(locationFile);

        for (String locationName : config.getKeys(false)) {
            SpawnProtection.getInstance().getconfig().get(locationName);


        }
    }
}