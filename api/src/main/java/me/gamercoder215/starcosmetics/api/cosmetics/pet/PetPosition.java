package me.gamercoder215.starcosmetics.api.cosmetics.pet;

import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

import java.security.SecureRandom;
import java.util.function.Function;

/**
 * Represents the position that a pet can be in, according to the player.
 */
public enum PetPosition implements Function<Player, Location>  {

    /**
     * Position for behind the Player
     */
    BEHIND(p -> {
        SecureRandom r = new SecureRandom();
        Location loc = p.getEyeLocation();

        Location loc0 = loc.clone();
        loc0.setPitch(0);
        Vector dir = loc0.getDirection();

        return loc
                .subtract(dir.multiply(2))
                .subtract(0, 0.6, 0)
                .add(0.1, r.nextInt(4) * 0.02, 0.1);
    }),

    /**
     * Position for on the head of the Player
     */
    HEAD(p -> p.getEyeLocation().subtract(0, 0.5, 0)),

    /**
     * Position for the behind the player, on the ground
     */
    BEHIND_GROUND(p -> {
        SecureRandom r = new SecureRandom();
        Location loc = p.getLocation().add(0, 0.25, 0);

        Location loc0 = loc.clone();
        loc0.setPitch(0);
        Vector dir = loc0.getDirection();

        return loc
                .subtract(dir.multiply(2))
                .subtract(0, 0.6, 0)
                .add(0.1, r.nextInt(4) * 0.02, 0.1);
    })

    ;

    private final Function<Player, Location> function;

    PetPosition(Function<Player, Location> func) {
        this.function = func;
    }

    @Override
    public Location apply(Player player) {
        return function.apply(player);
    }
}
