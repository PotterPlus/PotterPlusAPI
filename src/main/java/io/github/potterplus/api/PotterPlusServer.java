package io.github.potterplus.api;

import org.bukkit.Bukkit;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;

import java.util.Collection;
import java.util.List;
import java.util.function.Function;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class PotterPlusServer {

    public static PluginManager getPluginManager() {
        return Bukkit.getPluginManager();
    }

    public static Logger getLogger() {
        return Bukkit.getLogger();
    }

    public static Collection<? extends Player> getOnlinePlayers() {
        return Bukkit.getOnlinePlayers();
    }

    public static List<String> getOnlinePlayerNames() {
        return getOnlinePlayers().stream().map((Function<Player, String>) HumanEntity::getName).collect(Collectors.toList());
    }
}
