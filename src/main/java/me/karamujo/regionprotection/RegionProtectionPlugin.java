package me.karamujo.regionprotection;

import me.karamujo.regionprotection.data.RegionData;
import me.karamujo.regionprotection.listeners.FlagListeners;
import me.karamujo.regionprotection.loaders.CommandLoader;
import me.karamujo.regionprotection.storage.Database;
import me.karamujo.regionprotection.storage.FlagManager;
import me.karamujo.regionprotection.storage.RegionManager;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

/**
 *
 * @author Enzo
 */
public class RegionProtectionPlugin extends JavaPlugin {

    private static Database database;
    private static RegionProtectionPlugin instance;
    private static RegionData regionsData;

    private static boolean working;

    @Override
    public void onEnable() {
        instance = this;
        working = true;
        loadDatabase();
        RegionManager.loadTables();
        FlagManager.loadTables();
        regionsData = new RegionData();
        Bukkit.getPluginManager().registerEvents(new FlagListeners(), this);

        CommandLoader.load(this, "me.karamujo.regionprotection.commands");
    }

    public static RegionProtectionPlugin getInstance() {
        return instance;
    }

    public static RegionData getRegionsData() {
        return regionsData;
    }

    private void loadDatabase() {
        database = new Database(getConfig().getString("SQL.Host"), getConfig().getString("SQL.Database"), getConfig().getString("SQL.User"), getConfig().getString("SQL.Password"));
    }

    public static Database getMySQL() {
        return database;
    }

    public static boolean isWorking() {
        return working;
    }

    public static void setWorking(boolean working) {
        RegionProtectionPlugin.working = working;
    }

}
