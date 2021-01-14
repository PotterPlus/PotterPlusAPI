package io.github.grisstyl.ppapi;

import io.github.grisstyl.ppapi.misc.PluginLogger;
import io.github.grisstyl.ppapi.misc.StringUtilities;
import lombok.Getter;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.permissions.Permission;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.github.grisstyl.ppapi.misc.StringUtilities.color;

@Plugin(
        name = "PotterPlusAPI",
        version = "1.0.0"
)
@ApiVersion(
        ApiVersion.Target.v1_13
)
@Author("T0xicTyler")
@Commands(
        @Command(name = "ppapi")
)
public class PotterPlusAPI extends JavaPlugin implements CommandExecutor {

    public static final Permission PERMISSION_ADMIN;

    static {
        PERMISSION_ADMIN = new Permission("potterplus.admin");
    }

    @Getter
    private PotterPlusAPI plugin;

    static Connection connection;

    public String getHost() {
        return getConfig().getString("host");
    }

    public String getDatabase() {
        return getConfig().getString("db");
    }

    public String getUsername() {
        return getConfig().getString("user");
    }

    public String getPassword() {
        return getConfig().getString("pass");
    }

    public void openConnection() throws SQLException, ClassNotFoundException {
        if (connection != null && !connection.isClosed()) return;

        Class.forName("com.mysql.jdbc.Driver");

        connection = DriverManager.getConnection("jdbc:mysql://" + this.getHost() + "/" + this.getDatabase(), this.getUsername(), this.getPassword());
    }

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        PluginLogger.atInfo().with("Attempting to open MySQL connection...")
                .print();

        try {
            this.openConnection();

            if (connection != null) {
                PluginLogger.atInfo().with("Connected to MySQL DB.")
                        .print();
            }
        } catch (Exception e) {
            PluginLogger.atWarn().with("Failed to open MySQL connection!");

            e.printStackTrace();
        }

        {
            PluginCommand plCommand = getCommand("ppapi");

            if (plCommand != null) {
                plCommand.setExecutor(this);
            } else {
                PluginLogger.atWarn().with("Failed to set up /ppapi command!")
                        .print();
            }
        }
    }

    @Override
    public void onDisable() {
        try {
            if (!connection.isClosed()) {
                connection.close();
            }
        } catch (Exception e) {
            PluginLogger.atWarn()
                    .with("Failed to close MySQL connection!")
                    .print();
            e.printStackTrace();
        }

        HandlerList.unregisterAll(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!StringUtilities.equalsAny(label, "ppapi", "potterplusapi")) {
            String message = String.format("Label '%s' got parsed by PotterPlusAPI command?", label);

            PluginLogger.atWarn()
                    .with(message).print();

            return true;
        }

        if (!sender.hasPermission(PERMISSION_ADMIN)) {
            sender.sendMessage(StringUtilities.color(" &4&lX &cYou are not allowed to do that!"));

            return true;
        }

        PluginDescriptionFile pdFile = plugin.getDescription();

        String version = pdFile.getVersion();
        String message = String.format("PotterPlusAPI version %s", version);

        sender.sendMessage(message);

        return true;
    }
}
