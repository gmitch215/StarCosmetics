package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;

import static me.gamercoder215.starcosmetics.util.Constants.w;

public interface StructureReader extends Closeable {

    Structure read();

    static boolean isCompatible(String minVersion) {
        if (minVersion.equalsIgnoreCase("ALL")) return true;

        String currentV = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        int current = Integer.parseInt(currentV.split("_")[1]);
        int required = Integer.parseInt(minVersion.split("\\.")[1]);
        return current >= required;
    }

    static String trimLine(String point) {
        String trimmed = point.trim();
        if (point.contains(";")) trimmed = trimmed.substring(0, trimmed.indexOf(";"));

        return trimmed;
    }

    static List<StructurePoint> readPoints(String input) {
        List<StructurePoint> points = new ArrayList<>();

        String[] inputs = input.split("\\*");

        for (String rawS : inputs) {
            String s = trimLine(rawS);

            if (s.startsWith("(") && s.endsWith("]")) {
                String[] coords = s.split("\\^")[1].replaceAll("[\\[\\]\\s]", "").split(",");
                String[] split = s.split("\\^")[0].replaceAll("[()\\s]", "").split(",");

                int cx = Integer.parseInt(coords[0]);
                int cy = Integer.parseInt(coords[1]);
                int cz = Integer.parseInt(coords[2]);

                int x1 = Math.min(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                int x2 = Math.max(Integer.parseInt(split[0]), Integer.parseInt(split[1]));
                int y1 = Math.min(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                int y2 = Math.max(Integer.parseInt(split[2]), Integer.parseInt(split[3]));
                int z1 = Math.min(Integer.parseInt(split[4]), Integer.parseInt(split[5]));
                int z2 = Math.max(Integer.parseInt(split[4]), Integer.parseInt(split[5]));

                for (int x = Math.abs(x2) - Math.abs(x1); x <= Math.abs(x1) + Math.abs(x2); x++)
                    for (int y = Math.abs(y2) - Math.abs(y1); y <= Math.abs(y1) + Math.abs(y2); y++)
                        for (int z = Math.abs(z2) - Math.abs(z1); z <= Math.abs(z1) + Math.abs(z2); z++)
                            points.add(new StructurePoint(x + cx, y + cy, z + cz));

            } else points.add(readRawPoint(s));
        }

        return points;
    }

    static StructurePoint readRawPoint(String rawInput) {
        String input = trimLine(rawInput);

        if (!input.startsWith("[") || !input.endsWith("]")) throw new MalformedStructureException("Malformed Raw Point: " + input);

        String[] split = input.replaceAll("[\\[\\]\\s]", "").split(",");
        int x = Integer.parseInt(split[0]);
        int y = Integer.parseInt(split[1]);
        int z = Integer.parseInt(split[2]);
        return new StructurePoint(x, y, z);
    }

    static StructureReader getStructureReader(File file) {
        try {
            return getStructureReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            StarConfig.print(e);
            return null;
        }
    }

    static StructureReader getStructureReader(InputStream stream) {
        return getStructureReader(new InputStreamReader(stream));
    }

    static StructureReader getStructureReader(Reader r) {
        try {
            if (w.isLegacy())
                return Class.forName("me.gamercoder215.starcosmetics.api.cosmetics.structure.LegacyStructureReader")
                        .asSubclass(StructureReader.class)
                        .getConstructor(Reader.class)
                        .newInstance(r);
            else
                return Class.forName("me.gamercoder215.starcosmetics.api.cosmetics.structure.ModernStructureReader")
                        .asSubclass(StructureReader.class)
                        .getConstructor(Reader.class)
                        .newInstance(r);
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

}
