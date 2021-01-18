package io.github.potterplus.api.location;

import io.github.potterplus.api.misc.ServerUtilities;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class LocationSerializer {

    public Location deserialize(ConfigurationSection cs) {
        if (!cs.isSet("world") || !cs.isSet("x") || !cs.isSet("y") || !cs.isSet("z")) {
            throw new NullPointerException("Cannot parse partial Location.");
        }

        String worldName = cs.getString("world");
        double x = cs.getDouble("x");
        double y = cs.getDouble("y");
        double z = cs.getDouble("z");

        Optional<World> world = ServerUtilities.getWorld(worldName);

        if (world.isPresent()) {
            return new Location(world.get(), x, y, z);
        } else {
            throw new NullPointerException("World '" + worldName + "' does not exist!");
        }
    }

    public Map<String, Object> serialize(Location location) {
        Map<String, Object> map = new HashMap<>();

        World world = location.getWorld();

        if (world == null) {
            throw new NullPointerException("Cannot map Location with no world!");
        }

        map.put("world", location.getWorld().getName());
        map.put("x", location.getX());
        map.put("y", location.getY());
        map.put("z", location.getZ());

        return map;
    }
}
