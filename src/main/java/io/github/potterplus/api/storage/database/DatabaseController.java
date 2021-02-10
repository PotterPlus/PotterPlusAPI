package io.github.potterplus.api.storage.database;

import lombok.Getter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.github.potterplus.api.misc.PluginLogger.*;

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
        try {
            openConnection();

            if (this.connection != null) {
                atInfo("Connected to database.");
            }
        } catch (Exception e) {
            atSevere("Failed to connect to database.");
            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (!this.connection.isClosed()) {
                this.connection.close();
                atInfo("Disconnected from database.");
            }
        } catch (Exception e) {
            atWarn("Failed to disconnect from database.");
            e.printStackTrace();
        }
    }
}
