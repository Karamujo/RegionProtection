package me.karamujo.regionprotection.util;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

public class LocationSerializer {

    public static String serialize(Location loc) {
        String world = loc.getWorld().getName();
        int x = loc.getBlockX();
        int y = loc.getBlockY();
        int z = loc.getBlockZ();
        String linha;
        linha = world + " " + x + " " + y + " " + z;
        return linha;
    }

    public static Location deserialize(String locSerialized) {
        String[] argumentos = locSerialized.split(" ");
        World world = Bukkit.getWorld(argumentos[0]);
        int x = Integer.valueOf(argumentos[1]);
        int y = Integer.valueOf(argumentos[2]);
        int z = Integer.valueOf(argumentos[3]);
        Location loc;
        loc = new Location(world, x, y, z);
        return loc;
    }
}
