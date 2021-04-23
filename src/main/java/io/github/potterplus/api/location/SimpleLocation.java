package io.github.potterplus.api.location;

import io.github.potterplus.api.misc.ServerUtilities;
import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.util.Optional;

public class SimpleLocation {

    @Getter
    private final String worldName;
    @Getter
    private final double x, y, z;

    public SimpleLocation(String worldName, double x, double y, double z) {
        this.worldName = worldName;

        Optional<World> w = ServerUtilities.getWorld(worldName);

        if (!w.isPresent()) {
            throw new NullPointerException(String.format("World '%s' does not exist", worldName));
        }

        this.x = x;
        this.y = y;
        this.z = z;
    }

    public SimpleLocation(World world, double x, double y, double z) {
        this(world.getName(), x, y, z);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }
}
