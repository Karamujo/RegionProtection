package me.karamujo.regionprotection.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import me.karamujo.regionprotection.RegionProtectionPlugin;
import me.karamujo.regionprotection.model.Region;
import me.karamujo.regionprotection.util.LocationSerializer;
import org.bukkit.Location;

/**
 *
 * @author Enzo
 */
public class RegionManager {

    private static final String TABLE = "regions";
    private static final String TABLE_FLAG = "region_flag";

    public static void register(String name, String owner, Location max, Location min) {
        String query = String.format("INSERT INTO %s (%s,%s,%s,%s) VALUES(?,?,?,?);", TABLE, Columns.NAME, Columns.OWNER, Columns.MAX, Columns.MIN);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, owner);
            statement.setString(3, LocationSerializer.serialize(max));
            statement.setString(4, LocationSerializer.serialize(min));
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void unregister(String name) {
        String query = String.format("DELETE FROM %s WHERE %s = ?", TABLE, Columns.NAME);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static HashMap<String, Region> load() {
        HashMap<String, Region> regions = new HashMap<>();
        String query = String.format("SELECT * FROM %s", TABLE);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                Region region = new Region(result.getString("name"), result.getString("owner"), LocationSerializer.deserialize(result.getString("max")), LocationSerializer.deserialize(result.getString("min")));
                regions.put(region.getName(), region);
            }
            result.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return regions;
    }

    public static void loadTables() {
        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s("
                + "%s VARCHAR(16) NOT NULL,"
                + "%s VARCHAR(16) NOT NULL,"
                + "%s VARCHAR(80) NOT NULL,"
                + "%s VARCHAR(80) NOT NULL); ", TABLE, Columns.NAME, Columns.OWNER, Columns.MAX, Columns.MIN);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
