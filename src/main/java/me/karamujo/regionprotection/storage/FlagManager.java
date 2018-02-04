package me.karamujo.regionprotection.storage;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import me.karamujo.regionprotection.RegionProtectionPlugin;
import me.karamujo.regionprotection.model.Flag;
import me.karamujo.regionprotection.model.FlagType;

/**
 *
 * @author Enzo
 */
public class FlagManager {

    private static final String TABLE = "region_flag";

    public static void register(String name, Flag flag) {
        String query = String.format("INSERT INTO %s (%s,%s,%s) VALUES(?,?,?);", TABLE, Columns.NAME, Columns.FLAG, Columns.ALLOW);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            statement.setString(1, name);
            statement.setString(2, flag.getType().getName());
            statement.setBoolean(3, flag.isAllow());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static void update(String name, FlagType type, boolean allow) {
        String query = String.format("UPDATE %s SET %s = ? WHERE %s = ? AND %s = ?", TABLE, Columns.ALLOW, Columns.NAME, Columns.FLAG);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            statement.setBoolean(1, allow);
            statement.setString(2, name);
            statement.setString(3, type.getName());
            statement.executeUpdate();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    public static HashMap<FlagType, Flag> loadFlags(String name) {
        HashMap<FlagType, Flag> flags = new HashMap<>();
        String query = String.format("SELECT * FROM %s", TABLE);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            ResultSet result = statement.executeQuery();
            while (result.next()) {
                FlagType type = FlagType.valueOf(result.getString("flag").toUpperCase());
                flags.put(type, new Flag(type, result.getBoolean("allow")));
            }
            result.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
        }
        return flags;
    }

    public static void loadTables() {
        String query = String.format(
                "CREATE TABLE IF NOT EXISTS %s("
                + "%s VARCHAR(16) NOT NULL,"
                + "%s VARCHAR(10) NOT NULL,"
                + "%s TINYINT(1) NOT NULL);", TABLE, Columns.NAME, Columns.FLAG, Columns.ALLOW);
        try (PreparedStatement statement = RegionProtectionPlugin.getMySQL().getConnection().prepareStatement(query)) {
            statement.executeUpdate();
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
    }

}
