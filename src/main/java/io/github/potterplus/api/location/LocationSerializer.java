package io.github.potterplus.api.location;

import io.github.potterplus.api.misc.ServerUtilities;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Pattern;

public class LocationSerializer {

    public Location deserializeFromString(String s) {
        String[] split = s.split(Pattern.quote(":"));

        if (split.length < 4) {
            throw new IllegalArgumentException(String.format("Cannot parse Location from incomplete Location String! (%d)", split.length));
        }

        String worldName = split[0];
        double x, y, z;

        x = 0;
        y = 0;
        z = 0;

        try {
            x = Double.parseDouble(split[1]);
            y = Double.parseDouble(split[2]);
            z = Double.parseDouble(split[3]);
        } catch (NumberFormatException ignored) { }

        Optional<World> world = ServerUtilities.getWorld(worldName);

        if (world.isPresent()) {
            return new Location(world.get(), x, y, z);
        } else {
            throw new IllegalArgumentException(String.format("World '%s' doesn't exist!", worldName));
        }
    }

    public String serializeToString(Location l) {
        StringBuilder sb = new StringBuilder();

        World w = l.getWorld();

        if (w == null) {
            throw new IllegalArgumentException("Cannot write null world to Location String.");
        }

        sb.append(w.getName())
                .append(":")
                .append(l.getX())
                .append(":")
                .append(l.getY())
                .append(":")
                .append(l.getZ());

        return sb.toString();
    }

    public Location deserializeConfigSection(ConfigurationSection cs) {
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
            throw new IllegalArgumentException(String.format("World '%s' doesn't exist!", worldName));
        }
    }

    public Map<String, Object> serializeConfigSection(Location l) {
        Map<String, Object> map = new HashMap<>();

        World w = l.getWorld();

        if (w == null) {
            throw new NullPointerException("Cannot map Location with no world!");
        }

        map.put("world", l.getWorld().getName());
        map.put("x", l.getX());
        map.put("y", l.getY());
        map.put("z", l.getZ());

        return map;
    }
}
