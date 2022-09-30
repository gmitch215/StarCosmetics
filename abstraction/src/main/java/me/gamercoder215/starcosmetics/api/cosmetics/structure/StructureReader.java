package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;
import java.util.ArrayList;
import java.util.List;

import me.gamercoder215.starcosmetics.api.StarConfig;

public interface StructureReader {

    Structure read();

    void close();

    static List<StructurePoint> readPoints(String input) {
        List<StructurePoint> points = new ArrayList<>();
        
        if (input.startsWith("(") && input.startsWith(")")) {
            String[] coords = input.split("\\[")[1].split("*");
            String[] split = input.replaceAll("[\\(\\)]", "").split(",");
            // TODO Finish
        } else points.addAll(readRawPoints(input));
        

        return points;
    }

    static List<StructurePoint> readRawPoints(String input) {
        List<StructurePoint> points = new ArrayList<>();
        
        String[] split = input.split("\\*");
        for (String s : split) {
            String[] split2 = s.split(",");
            if (split2.length != 3) throw new RuntimeException("Malformed Structure Point: '" + s + "'");
            
            points.add(readRawPoint(s));
        }

        return points;
    }

    static StructurePoint readRawPoint(String input) {
        if (!input.startsWith("[") || !input.startsWith("]")) throw new RuntimeException("Malformed Raw Point: " + input);

        String[] split = input.replaceAll("[\\[\\]]", "").split(",");
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
            if (StarConfig.isLegacy()) {
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.LegacyStructureReader")
                .asSubclass(StructureReader.class)
                .getConstructor(Reader.class)
                .newInstance(r);
            } else {
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.ModernStructureReader")
                .asSubclass(StructureReader.class)
                .getConstructor(Reader.class)
                .newInstance(r);
            }
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

}
