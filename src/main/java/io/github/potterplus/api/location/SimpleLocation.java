package io.github.potterplus.api.location;

import lombok.Getter;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class SimpleLocation {

    @Getter
    private final String worldName;
    @Getter
    private double x, y, z;

    public SimpleLocation(String world, double x, double y, double z) {
        this.worldName = world;

        if (Bukkit.getWorld(worldName) == null) {
            throw new NullPointerException(String.format("World '%s' does not exist", worldName));
        }
    }

    public SimpleLocation(World world, double x, double y, double z) {
        this(world.getName(), x, y, z);
    }

    public World getWorld() {
        return Bukkit.getWorld(worldName);
    }
}
