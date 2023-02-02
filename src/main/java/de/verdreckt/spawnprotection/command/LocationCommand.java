package de.verdreckt.spawnprotection.command;

import de.verdreckt.spawnprotection.SpawnProtection;
import de.verdreckt.spawnprotection.manager.LocationManager;
import lombok.SneakyThrows;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;

public class LocationCommand implements CommandExecutor, TabCompleter {

    private final LocationManager locationManager = new LocationManager();

    @SneakyThrows
    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage(SpawnProtection.prefix + "§7Dieser Befehl kann nur von einem Spieler ausgeführt werden.");
            return false;
        }
        final Player player = (Player) sender;

        if(player.hasPermission("Teamvace.Admin")) {
            if(args.length == 2) {
                String locationName = args[1];
                if(args[0].equalsIgnoreCase("set")) {
                    locationManager.createLocation(locationName, player.getLocation());
                    player.sendMessage(SpawnProtection.prefix + "§7Du hast die Location §a" + locationName + " §7gesetzt.");
                    player.sendMessage(SpawnProtection.prefix + "§7Um die Änderung wirksam zumachen benutzte §a/rl");
                } else if(args[0].equalsIgnoreCase("remove")) {
                    locationManager.removeLocation(locationName);
                    player.sendMessage(SpawnProtection.prefix + "§7Du hast die Location §a" + locationName + " §7gelöscht.");
                }
            } else {
                player.sendMessage(SpawnProtection.prefix + "§cBitte benutze §7/loc help");
            }
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String label, String[] args) {
        ArrayList<String> list = new ArrayList<>();

        if (args.length == 1) {
            list.add("set");
            list.add("remove");
        }
        if (args.length == 2) {
            list.add("Position1");
            list.add("Position2");
        }

        return list;
    }
}