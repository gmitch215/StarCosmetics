package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import org.bukkit.Bukkit;

import java.io.Closeable;
import java.util.ArrayList;
import java.util.List;

/**
 * Represents an abstract Structure Reader.
 */
public interface StructureReader extends Closeable {

    /**
     * Reads the structure.
     * @return Structure.
     */
    Structure read();

    /**
     * Checks whether the current server version is compatible with the specified minimum version, formatted in a Strcuture File.
     * @param minVersion Minimum Structure Version
     * @return true if compatible, false otherwise
     */
    static boolean isCompatible(String minVersion) {
        if (minVersion.equalsIgnoreCase("ALL")) return true;

        String currentV = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        int current = Integer.parseInt(currentV.split("_")[1]);
        int required = Integer.parseInt(minVersion.split("\\.")[1]);
        return current >= required;
    }

    /**
     * Trims a line from a Structure File.
     * @param point Line to trim
     * @return Trimmed line
     */
    static String trimLine(String point) {
        String trimmed = point.trim();
        if (point.contains(";")) trimmed = trimmed.substring(0, trimmed.indexOf(";"));

        return trimmed;
    }

    /**
     * Reads a list of Structure Points from a Structure Line.
     * @param input Structure File Line
     * @return List of Structure Points
     */
    static List<StructurePoint> readPoints(String input) {
        List<StructurePoint> points = new ArrayList<>();

        String[] inputs = input.split("\\*");

        for (String rawS : inputs) {
            String s = trimLine(rawS);

            if (s.startsWith("(") && s.endsWith("]")) {
                String[] coords = s.split("\\^")[1].replaceAll("[\\[\\]\\s]", "").split(",");
                String[] split = s.split("\\^")[0].replaceAll("[()\\s]", "").split(",");

                if (coords.length != 3) throw new MalformedStructureException("Malformed Point: " + s);
                if (split.length != 6) throw new MalformedStructureException("Malformed Point: " + s);

                int cx = Integer.parseInt(coords[0]);
                int cy = Integer.parseInt(coords[1]);
                int cz = Integer.parseInt(coords[2]);

                int x1 = Math.min(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                int x2 = Math.max(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                int y1 = Math.min(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                int y2 = Math.max(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                int z1 = Math.min(Integer.parseInt(split[4]), Integer.parseInt(split[5]));
                int z2 = Math.max(Integer.parseInt(split[4]), Integer.parseInt(split[5]));

                for (int x = x1; x <= x2; x++)
                    for (int y = y1; y <= y2; y++)
                        for (int z = z1; z <= z2; z++)
                            points.add(new StructurePoint(x + cx, y + cy, z + cz));

            } else points.add(readRawPoint(s));
        }

        return points;
    }

    /**
     * Reads a singular point from its string form.
     * @param rawInput Raw Point
     * @return Structure Point
     */
    static StructurePoint readRawPoint(String rawInput) {
        String input = trimLine(rawInput);

        if (!input.startsWith("[") || !input.endsWith("]")) throw new MalformedStructureException("Malformed Raw Point: " + input);

        String[] split = input.replaceAll("[\\[\\]\\s]", "").split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        int z = Integer.parseInt(split[2]);
        return new StructurePoint(x, y, z);
    }

}
