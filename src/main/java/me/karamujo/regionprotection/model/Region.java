package me.karamujo.regionprotection.model;

import java.util.HashMap;
import java.util.List;
import org.bukkit.Location;
import org.bukkit.World;

/**
 *
 * @author Enzo
 */
public class Region {

    private String name;
    private String owner;
    private World world;
    private int xMax;
    private int xMin;
    private int yMax;
    private int yMin;
    private int zMax;
    private int zMin;
    private HashMap<FlagType, Flag> flags;

    public Region(String name, String owner, Location location1, Location location2) {
        this.name = name;
        this.owner = owner;
        this.world = location1.getWorld();

        this.xMax = Math.max(location1.getBlockX(), location2.getBlockX());
        this.xMin = Math.min(location1.getBlockX(), location2.getBlockX());
        this.yMax = Math.max(location1.getBlockY(), location2.getBlockY());
        this.yMin = Math.min(location1.getBlockY(), location2.getBlockY());
        this.zMax = Math.max(location1.getBlockZ(), location2.getBlockZ());
        this.zMin = Math.min(location1.getBlockZ(), location2.getBlockZ());

        flags = new HashMap<>();
    }

    public String getName() {
        return this.name;
    }

    public String getOwner() {
        return this.owner;
    }

    public World getWorld() {
        return world;
    }

    public int getxMax() {
        return xMax;
    }

    public int getxMin() {
        return xMin;
    }

    public int getyMax() {
        return yMax;
    }

    public int getyMin() {
        return yMin;
    }

    public int getzMax() {
        return zMax;
    }

    public int getzMin() {
        return zMin;
    }

    public Location getMax() {
        return new Location(world, xMax, yMax, zMax);
    }

    public Location getMin() {
        return new Location(world, xMin, yMin, zMin);
    }

    public boolean inRegion(int x, int y, int z) {
        return x >= xMin && x <= xMax && y >= yMin && y <= yMax && z >= zMin && z <= zMax;
    }

    public boolean inRegion(Location location) {
        return inRegion(location.getBlockX(), location.getBlockY(), location.getBlockZ());
    }

    public void addFlag(Flag flag) {
        this.flags.put(flag.getType(), flag);
    }

    public void removeFlag(FlagType type) {
        this.flags.remove(type);
    }

    public Flag getFlag(FlagType type) {
        return flags.get(type);
    }

    public void setFlags(HashMap<FlagType, Flag> flags) {
        this.flags = flags;
    }

}
