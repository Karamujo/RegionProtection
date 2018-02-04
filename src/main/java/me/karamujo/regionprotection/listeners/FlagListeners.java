package me.karamujo.regionprotection.listeners;

import me.karamujo.regionprotection.RegionProtectionPlugin;
import me.karamujo.regionprotection.data.RegionData;
import me.karamujo.regionprotection.model.FlagType;
import me.karamujo.regionprotection.model.Region;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

/**
 *
 * @author Enzo
 */
public class FlagListeners implements Listener {

    private RegionData data = RegionProtectionPlugin.getRegionsData();

    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        Player player = event.getPlayer();
        Region region = data.getRegionByLocation(event.getBlock().getLocation());
        if (region != null) {
            if (!region.getFlag(FlagType.BUILD).isAllow()) {
                String owner = region.getOwner();
                if (!owner.equalsIgnoreCase(player.getName())) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        Player player = event.getPlayer();
        Region region = data.getRegionByLocation(event.getBlock().getLocation());
        if (region != null) {
            if (!region.getFlag(FlagType.BUILD).isAllow()) {
                String owner = region.getOwner();
                if (!owner.equalsIgnoreCase(player.getName())) {
                    event.setBuild(false);
                }
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (event.getDamager() instanceof Player && event.getEntity() instanceof Player) {
            Region region = data.getRegionByLocation(event.getEntity().getLocation());
            if (region != null) {
                if (!region.getFlag(FlagType.PVP).isAllow()) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventHandler
    public void onSpawnMobs(CreatureSpawnEvent event) {
        Region region = data.getRegionByLocation(event.getEntity().getLocation());
        if (region != null) {
            if (!region.getFlag(FlagType.MOBS).isAllow()) {
                event.setCancelled(true);
            }
        }
    }

}
