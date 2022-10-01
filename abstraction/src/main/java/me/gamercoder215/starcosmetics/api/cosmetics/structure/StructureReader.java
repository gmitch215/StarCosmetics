package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.StarConfig;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

public interface StructureReader {

    Structure read();

    void close();

    static List<StructurePoint> readPoints(String input) {
        List<StructurePoint> points = new ArrayList<>();

        String[] inputs = input.split("\\*");

        for (String s : inputs)
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
        

        return points;
    }

    static StructurePoint readRawPoint(String input) {
        if (!input.startsWith("[") || !input.endsWith("]")) throw new RuntimeException("Malformed Raw Point: " + input);

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

    static StructureReader getStructureReader(Reader r) {
        try {
            if (StarConfig.isLegacy())
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.LegacyStructureReader")
                        .asSubclass(StructureReader.class)
                        .getConstructor(Reader.class)
                        .newInstance(r);
            else
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.ModernStructureReader")
                        .asSubclass(StructureReader.class)
                        .getConstructor(Reader.class)
                        .newInstance(r);
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

}
