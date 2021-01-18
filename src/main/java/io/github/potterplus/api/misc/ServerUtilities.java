package io.github.potterplus.api.misc;

import plugin.PotterPlusAPI;
import org.bukkit.Bukkit;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.util.Optional;

public class ServerUtilities {

    public static Optional<World> getWorld(String worldName) {
        World w = Bukkit.getWorld(worldName);

        if (w != null) {
            return Optional.of(w);
        } else {
            return Optional.empty();
        }
    }

    public static void sendTab(Player player, String header, String footer) {
        PotterPlusAPI.getInstance().getTabManager().getTablist(player).setHeader(header);
        PotterPlusAPI.getInstance().getTabManager().getTablist(player).setFooter(footer);
    }
}
