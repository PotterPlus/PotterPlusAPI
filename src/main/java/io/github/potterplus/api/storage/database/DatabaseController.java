package io.github.potterplus.api.storage.database;

import io.github.potterplus.api.misc.PluginLogger;
import lombok.Getter;
import lombok.NonNull;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseController {

    @Getter
    private Connection connection;

    private final String host;

    private final String db;

    private final String user;

    private final String pass;

    public DatabaseController(String host, String db, String user, String pass) {
        this.host = host;
        this.db = db;
        this.user = user;
        this.pass = pass;
    }

    private void openConnection() throws SQLException, ClassNotFoundException {
        if (this.connection != null && !this.connection.isClosed()) {
            return;
        }

        Class.forName("com.mysql.jdbc.Driver");

        this.connection = DriverManager.getConnection("jdbc:mysql://" + this.host + "/" + this.db, this.user, this.pass);
    }

    public void connect() {
        PluginLogger.atInfo("Attempting to establish connection with MySQL database...");

        try {
            openConnection();

            if (this.connection != null) {
                PluginLogger.atInfo("Successfully connected with MySQL database.");
            }
        } catch (Exception e) {
            PluginLogger.atSevere("Failed to establish connection with MySQL database.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (!this.connection.isClosed()) {
                this.connection.close();
                PluginLogger.atInfo("Successfully disconnected from MySQL database.");
            }
        } catch (Exception e) {
            PluginLogger.atWarn("Failed to disconnect from MySQL database.");
            e.printStackTrace();
        }
    }
}
