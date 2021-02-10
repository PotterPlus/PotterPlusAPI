package io.github.potterplus.api.player;

import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.entity.Player;

/**
 * A bunch of shorthand player methods. Create one of these wherever you need it.
 */
@RequiredArgsConstructor
public class PlayerUtils {

    @NonNull
    private Player target;

    public PlayerUtils target(Player player) {
        this.target = player;

        return this;
    }

    public double getMaxHealth() {
        AttributeInstance maxHealth = target.getAttribute(Attribute.GENERIC_MAX_HEALTH);
        double d;

        if (maxHealth == null) {
            d = 20;
        } else {
            d = maxHealth.getValue();
        }

        return d;
    }
}
