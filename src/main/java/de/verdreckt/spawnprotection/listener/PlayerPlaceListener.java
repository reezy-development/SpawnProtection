package de.verdreckt.spawnprotection.listener;

import de.verdreckt.spawnprotection.SpawnProtection;
import de.verdreckt.spawnprotection.manager.LocationManager;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class PlayerPlaceListener implements Listener {

    private final LocationManager locationManager;
    private Location loc1;
    private Location loc2;

    public PlayerPlaceListener(LocationManager locationManager) {
        this.locationManager = locationManager;
        this.loc1 = locationManager.get("Position1");
        if (loc1 == null) {
            Bukkit.getLogger().warning("Position1 ist nicht gesetzt");
            return;
        }

        this.loc2 = locationManager.get("Position2");
        if (loc2 == null) {
            Bukkit.getLogger().warning("Position2 ist nicht gesetzt");
            return;
        }
    }

    @EventHandler
    public void onPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();

        if (!isInProtectedArea(event.getBlock().getLocation())) {
            return;
        }

        if (isInCreativeMode(player)) {
            if (hasPermission(player)) {
                return;
            }
        }

        event.setCancelled(true);
        player.sendMessage(SpawnProtection.prefix + "§7Du darfst hier nicht.");
    }

    private boolean isInProtectedArea(Location blockLocation) {
        if (loc1 == null || loc2 == null) {
            Bukkit.getLogger().warning("loc1 or loc2 is not set!");
            return false;
        }

        int blockX = blockLocation.getBlockX();
        int blockY = blockLocation.getBlockY();
        int blockZ = blockLocation.getBlockZ();

        int minX = Math.min(loc1.getBlockX(), loc2.getBlockX());
        int maxX = Math.max(loc1.getBlockX(), loc2.getBlockX());
        int minY = Math.min(loc1.getBlockY(), loc2.getBlockY());
        int maxY = Math.max(loc1.getBlockY(), loc2.getBlockY());
        int minZ = Math.min(loc1.getBlockZ(), loc2.getBlockZ());
        int maxZ = Math.max(loc1.getBlockZ(), loc2.getBlockZ());

        return blockX >= minX && blockX <= maxX &&
                blockY >= minY && blockY <= maxY &&
                blockZ >= minZ && blockZ <= maxZ;
    }

    private boolean hasPermission(Player player) {
        return player.hasPermission("spawnprotection.place");
    }

    private boolean isInCreativeMode(Player player) {
        return player.getGameMode() == GameMode.CREATIVE;
    }
}