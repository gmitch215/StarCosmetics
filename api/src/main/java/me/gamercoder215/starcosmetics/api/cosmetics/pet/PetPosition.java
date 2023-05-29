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
    }),

    /**
     * Position for behind the player like a Backpack
     */
    BACKPACK(p -> p.getLocation()
            .add(0, 0.25, 0)
            .subtract(p.getLocation().getDirection().setY(0).multiply(0.5))
    ),

    /**
     * Position for on the shoulder of the Player
     */
    SHOULDER(p -> local(p.getEyeLocation().subtract(0, 1, 0), new Vector(-0.5, 0, 0)).toLocation(p.getWorld()))

    ;

    private final Function<Player, Location> function;

    PetPosition(Function<Player, Location> func) {
        this.function = func;
    }

    /**
     * Fetches a Location based on the Player
     * @param player Player to fetch the Location for
     * @return Pet Position Location for the Player
     */
    @Override
    public Location apply(Player player) {
        return function.apply(player);
    }

    private static Vector local(Location reference, Vector local) {
        Vector base = new Vector(0, 0, 1);

        Vector left = rotateAroundY(base.clone(), Math.toRadians(-reference.getYaw() + 90.0f));
        Vector up = rotateAroundNonUnitAxis(reference.getDirection().clone(), left, Math.toRadians(-90f));

        Vector sway = left.clone().normalize().multiply(local.getX());
        Vector heave = up.clone().normalize().multiply(local.getY());
        Vector surge = reference.getDirection().clone().multiply(local.getZ());

        return new Vector(reference.getX(), reference.getY(), reference.getZ()).add(sway).add(heave).add(surge);
    }

    private static Vector rotateAroundY(Vector v, double angle) {
        double cos = Math.cos(angle);
        double sin = Math.sin(angle);

        double x = cos * v.getX() + sin * v.getZ();
        double z = -sin * v.getX() + cos * v.getZ();
        return v.setX(x).setZ(z);
    }

    private static Vector rotateAroundNonUnitAxis(Vector v, Vector axis, double angle) throws IllegalArgumentException {
        double x = v.getX(), y = v.getY(), z = v.getZ();
        double x2 = axis.getX(), y2 = axis.getY(), z2 = axis.getZ();

        double cosT = Math.cos(angle);
        double sinT = Math.sin(angle);
        double dot = v.dot(axis);

        double xPrime = x2 * dot * (1d - cosT)
                + x * cosT
                + (-z2 * y + y2 * z) * sinT;
        double yPrime = y2 * dot * (1d - cosT)
                + y * cosT
                + (z2 * x - x2 * z) * sinT;
        double zPrime = z2 * dot * (1d - cosT)
                + z * cosT
                + (-y2 * x + x2 * y) * sinT;

        return v.setX(xPrime).setY(yPrime).setZ(zPrime);
    }
}
