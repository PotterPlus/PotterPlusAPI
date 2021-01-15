package io.github.potterplus.api.storage;

import io.github.potterplus.api.PotterPlusAPI;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.github.potterplus.api.misc.PluginLogger.atInfo;
import static io.github.potterplus.api.misc.PluginLogger.atWarn;

@RequiredArgsConstructor
public class DatabaseController {

    @NonNull
    private PotterPlusAPI api;

    private Connection connection;

    private void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) return;

        String host = api.getConfig().getString("host");
        String database = api.getConfig().getString("db");
        String username = api.getConfig().getString("user");
        String password = api.getConfig().getString("pass");

        Class.forName("com.mysql.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, username, password);
    }

    public void connect() {
        atInfo().with("Attempting to open MySQL connection...")
                .print();

        try {
            this.openConnection();

            if (connection != null) {
                atInfo().with("Connected to MySQL DB.")
                        .print();
            }
        } catch (Exception e) {
            atWarn().with("Failed to open MySQL connection!");

            e.printStackTrace();
        }
    }

    public void disconnect() {
        try {
            if (!connection.isClosed()) {
                connection.close();

                atInfo().with("MySQL connection has been closed.")
                        .print();
            }
        } catch (Exception e) {
            atWarn().with("Failed to close MySQL connection!")
                    .print();
            e.printStackTrace();
        }
    }
}
