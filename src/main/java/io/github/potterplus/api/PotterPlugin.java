package io.github.potterplus.api;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class PotterPlugin extends JavaPlugin {

    public abstract String getLogPrefix();

    public void debug(String s) {
        FileConfiguration c = getConfig();

        boolean debug = c.getBoolean("debug", false);

        if (debug) {
            Bukkit.getLogger().info(
                    String.format("%s DEBUG - %s", getLogPrefix(), s)
            );
        }

        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.hasPermission("potterplus.admin")) {
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', "&8| &d&lDEBUG &7Receiving notification from &3potterplus.admin"));
                p.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format("&8| %s ", s)));
            }
        }
    }
}
