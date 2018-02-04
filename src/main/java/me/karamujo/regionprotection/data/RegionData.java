package me.karamujo.regionprotection.data;

import java.util.HashMap;
import java.util.Optional;
import me.karamujo.regionprotection.model.Flag;
import me.karamujo.regionprotection.model.FlagType;
import me.karamujo.regionprotection.model.Region;
import me.karamujo.regionprotection.storage.FlagManager;
import me.karamujo.regionprotection.storage.RegionManager;
import org.bukkit.Location;

/**
 *
 * @author Enzo
 */
public class RegionData {

    private HashMap<String, Region> regions = new HashMap<String, Region>();

    public RegionData() {
        load();
    }

    public void register(Region region) {
        RegionManager.register(region.getName(), region.getOwner(), region.getMax(), region.getMin());
        FlagManager.register(region.getName(), new Flag(FlagType.PVP, false));
        FlagManager.register(region.getName(), new Flag(FlagType.MOBS, false));
        FlagManager.register(region.getName(), new Flag(FlagType.BUILD, false));
        regions.put(region.getName(), region);
    }

    public void updateFlag(String name, FlagType type, boolean allow) {
        FlagManager.update(name, type, allow);
        getRegion(name).getFlag(type).setAllow(allow);
    }

    public void unregister(String name) {
        RegionManager.unregister(name);
        regions.remove(name);
    }

    public boolean existsRegion(String name) {
        return regions.containsKey(name);
    }

    public Region getRegion(String name) {
        return regions.get(name);
    }

    public Region getRegionByLocation(Location location) {
        Optional<Region> regionOptional = regions.values().stream().filter(r -> r.inRegion(location)).findFirst();
        if (regionOptional.isPresent()) {
            return regionOptional.get();
        }
        return null;
    }

    private void load() {
        regions.putAll(RegionManager.load());
        regions.values().forEach(r -> {
            r.setFlags(FlagManager.loadFlags(r.getName()));
        });
    }

}
