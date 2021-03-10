package io.github.potterplus.api;

import io.github.potterplus.api.misc.DebugLevel;
import io.github.potterplus.api.string.StringUtilities;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Map;

import static io.github.potterplus.api.string.StringUtilities.color;
import static java.lang.String.format;

public abstract class PotterPlugin extends JavaPlugin {

    public abstract String getLogPrefix();

    public void debug(DebugLevel level, String s) {
        FileConfiguration c = getConfig();

        boolean debug = c.getBoolean("debug", false);

        if (debug) {
            Bukkit.getLogger().info(format("%s DEBUG [%s] - %s", getLogPrefix(), level.name(), s));

            for (Player p : Bukkit.getOnlinePlayers()) {
                if (p.hasPermission("potterplus.admin")) {
                    p.sendMessage(color(format("&8| &d&lDEBUG &b%s &7Receiving notification from &3potterplus.admin", getLogPrefix())));
                    p.sendMessage(color(format("&8| %s%s", level.getPrefixColor(), s)));
                }
            }
        }
    }

    public void debug(DebugLevel level, String format, Object... replace) {
        String s = String.format(format, replace);

        debug(level, s);
    }

    public void debug(String s) {
        debug(DebugLevel.INFO, s);
    }

    public void debug(String format, Object... replace) {
        debug(DebugLevel.INFO, format, replace);
    }

    public void debug(String s, Map<String, String> replace) {
        debug(StringUtilities.replace(s, replace));
    }
}
