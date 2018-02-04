package me.karamujo.regionprotection.storage;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 *
 * @author Enzo
 */
public class Database {

    private java.sql.Connection connection = null;
    private final String user;
    private final String password;
    private final String host;
    private final String database;

    public Database(String host, String database, String user, String password) {
        this.host = host;
        this.database = database;
        this.user = user;
        this.password = password;
        this.connection = getConnection();
    }

    private Connection createConnection() {
        try {
            String connStr = "jdbc:mysql://" + this.host + ":3306/" + this.database + "?autoReconnect=true";
            Class.forName("com.mysql.jdbc.Driver").newInstance();
            connection = DriverManager.getConnection(connStr, this.user, this.password);
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return connection;
    }

    public synchronized Connection getConnection() {
        try {
            if (connection == null) {
                connection = createConnection();
                connection.setAutoCommit(true);
            }
        } catch (SQLException exception) {
            exception.printStackTrace();
        }
        return connection;
    }

}
