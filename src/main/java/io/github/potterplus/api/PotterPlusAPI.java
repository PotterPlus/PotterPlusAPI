package io.github.potterplus.api;

import com.elmakers.mine.bukkit.api.magic.MagicAPI;
import io.github.potterplus.api.misc.PluginLogger;
import io.github.potterplus.api.storage.DatabaseController;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.event.HandlerList;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.annotation.command.Command;
import org.bukkit.plugin.java.annotation.command.Commands;
import org.bukkit.plugin.java.annotation.permission.Permission;
import org.bukkit.plugin.java.annotation.permission.Permissions;
import org.bukkit.plugin.java.annotation.plugin.ApiVersion;
import org.bukkit.plugin.java.annotation.plugin.Plugin;
import org.bukkit.plugin.java.annotation.plugin.author.Author;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static io.github.potterplus.api.misc.PluginLogger.atInfo;
import static io.github.potterplus.api.misc.PluginLogger.atWarn;

@Plugin(
        name = "PotterPlusAPI",
        version = "1.0.0"
)
@ApiVersion(
        ApiVersion.Target.v1_13
)
@Author("T0xicTyler")
@Commands(
        @Command(
                name = "potterplusapi",
                aliases = {"ppapi"}
        )
)
@Permissions(
        @Permission(
                name = "potterplus.admin"
        )
)
public class PotterPlusAPI extends JavaPlugin implements CommandExecutor {

    @Getter
    private PotterPlusAPI plugin;

    @Getter
    private PotterPlusAPICommand command;

    @Getter
    private DatabaseController database;

    @Getter
    private MagicAPI magicAPI;

    @Override
    public void onEnable() {
        plugin = this;

        getConfig().options().copyDefaults(true);
        saveDefaultConfig();

        PluginManager pm = Bukkit.getPluginManager();

        if (pm.isPluginEnabled("Magic")) {
            this.magicAPI = (MagicAPI) pm.getPlugin("Magic");
            atInfo().with("Hooked into Magic.");
        } else {
            atWarn().with("Required dependency Magic not present.")
                    .print();
            Bukkit.shutdown();
        }

        this.database = new DatabaseController(this);

        database.connect();

        this.command = new PotterPlusAPICommand(this);
    }

    @Override
    public void onDisable() {


        HandlerList.unregisterAll(this);
    }
}
