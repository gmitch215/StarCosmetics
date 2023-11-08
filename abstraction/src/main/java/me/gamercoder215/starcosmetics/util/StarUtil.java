package me.gamercoder215.starcosmetics.util;

import org.bukkit.Location;
import org.bukkit.util.Vector;

import java.util.Arrays;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public final class StarUtil {
    
    public static int levenshteinDistance(String s1, String s2) {
        if (s1 == null || s2 == null) return -1;
        if (s1.equals(s2)) return 0;

        int[][] dp = new int[s1.length() + 1][s2.length() + 1];
 
        for (int i = 0; i <= s1.length(); i++)
            for (int j = 0; j <= s2.length(); j++)
                if (i == 0) dp[i][j] = j;
                else if (j == 0) dp[i][j] = i;
                else {
                    char c1 = s1.charAt(i - 1);
                    char c2 = s2.charAt(j - 1);
                    dp[i][j] = Arrays.stream(new int[] {
                            dp[i - 1][j - 1] + (c1 == c2 ? 0 : 1),
                            dp[i - 1][j] + 1,
                            dp[i][j - 1] + 1
                    }).min().orElse(Integer.MAX_VALUE);
                }
 
        return dp[s1.length()][s2.length()];
    }

    public static Location local(Location reference, Vector local) {
        Vector base = new Vector(0, 0, 1);
        Vector left = rotateAroundY(base.clone(), Math.toRadians(-reference.getYaw() + 90.0));
        Vector up = rotateAroundNonUnitAxis(reference.getDirection().clone(), left, Math.toRadians(-90.0));

        Vector sway = left.clone().normalize().multiply(local.getX());
        Vector heave = up.clone().normalize().multiply(local.getY());
        Vector surge = reference.getDirection().clone().multiply(local.getZ());

        Location loc = (new Vector(reference.getX(), reference.getY(), reference.getZ()).add(sway).add(heave).add(surge)).toLocation(reference.getWorld());
        loc.setYaw(reference.getYaw());

        return loc;
    }

    public static Vector rotateAroundY(Vector vector, double angle) {
        double x = vector.getX(); double z = vector.getZ();
        double cos = cos(angle); double sin = sin(angle);

        double nx = cos * x + sin * z;
        double nz = -sin * x + cos * z;
        return vector.setX(nx).setZ(nz);
    }

    public static Vector rotateAroundNonUnitAxis(Vector vector, Vector axis, double angle) {
        double x = vector.getX(); double y = vector.getY(); double z = vector.getZ();
        double x2 = axis.getX(); double y2 = axis.getY(); double z2 = axis.getZ();

        double cos = cos(angle); double sin = sin(angle);
        double dot = vector.dot(axis);

        double xPrime = x2 * dot * (1.0 - cos) + x * cos + (-z2 * y + y2 * z) * sin;
        double yPrime = y2 * dot * (1.0 - cos) + y * cos + (z2 * x - x2 * z) * sin;
        double zPrime = z2 * dot * (1.0 - cos) + z * cos + (-y2 * x + x2 * y) * sin;

        return vector.setX(xPrime).setY(yPrime).setZ(zPrime);
    }

}
