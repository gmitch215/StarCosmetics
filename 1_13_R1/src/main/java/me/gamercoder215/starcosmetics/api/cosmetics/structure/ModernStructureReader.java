package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Material;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class ModernStructureReader implements StructureReader {

    private final BufferedReader reader;

    private static final SecureRandom r = new SecureRandom();

    public ModernStructureReader(Reader r) {
        this.reader = new BufferedReader(r);
    }
    
    @Override
    public Structure read() {
        try {
            AtomicInteger index = new AtomicInteger(0);

            String minVersion = null;
            String displayKey = null;

            Map<StructurePoint, Material> points = new HashMap<>();
            Map<StructurePoint, String> blockData = new HashMap<>();
            for (String line; (line = reader.readLine()) != null;) {
                int i = index.get();

                if (i == 0) {
                    minVersion = line;
                    continue;
                }

                if (i == 1) {
                    displayKey = line;
                    continue;
                }

                if (i == 3 && !line.equalsIgnoreCase("---")) throw new RuntimeException("Malformed Strucutre File: Expected '---' but got '" + line + "'");

                if (i > 3) {
                    String material = line.split(":")[0];
                    if (material.startsWith("{") && material.endsWith("}")) {
                        Map<Material, Integer> chances = new HashMap<>();
                        Map<Material, String> blockDataChances = new HashMap<>();

                        int amount = 0;
                        String[] entries = material.split(",");
                        for (String entry : entries) {
                            String[] split = entry.split("=");

                            int chance = Integer.parseInt(split[0].replace("%", ""));
                            amount += chance;

                            if (split[1].contains("[") && split[1].endsWith("]")) {
                                String mat = split[1].split("\\[")[0];
                                String data = "[" + split[1].split("\\[")[1];

                                if (Material.matchMaterial(mat) == null) throw new RuntimeException("Malformed Strucutre File: Unknown Material '" + mat + "'");
                                chances.put(Material.matchMaterial(mat), chance);
                                blockDataChances.put(Material.matchMaterial(mat), data);
                            } else {
                                if (Material.matchMaterial(split[1]) == null)
                                    throw new RuntimeException("Unknown Material '" + split[1] + "'");

                                chances.put(Material.matchMaterial(split[1]), chance);
                            }
                        }

                        if (amount != 100) throw new RuntimeException("Malformed Strucutre File: Chance total is not 100%");

                        Map<Integer, Material> chanceMap = new HashMap<>();
                        int current = 0;
                        for (Map.Entry<Material, Integer> entry : chances.entrySet())
                            for (int j = 0; j < entry.getValue(); j++) {
                                chanceMap.put(current, entry.getKey());
                                current++;
                            }

                        String coords = line.split(":")[1];
                        for (StructurePoint p : StructureReader.readPoints(coords)) {
                            Material m = chanceMap.get(r.nextInt(100));
                            points.put(p, m);
                            blockData.put(p, blockDataChances.get(m));
                        }
                    } else {
                        String mat = material;
                        String data = null;
                        if (material.contains("[") && material.endsWith("]")) {
                            mat = material.split("\\[")[0];
                            data = "[" + material.split("\\[")[1];

                            if (Material.matchMaterial(mat) == null) throw new RuntimeException("Malformed Strucutre File: Unknown Material '" + mat + "'");
                        }

                        if (Material.matchMaterial(mat) == null)
                            throw new RuntimeException("Malformed Structure File: Unknown Material '" + mat + "'");
                        Material m = Material.matchMaterial(mat);

                        String coords = line.split(":")[1];
                        for (StructurePoint p : StructureReader.readPoints(coords)) {
                            points.put(p, m);
                            if (data != null) blockData.put(p, data);
                        }
                    }
                }

                index.incrementAndGet();
            }

            close();
            return new ModernStructure(minVersion, displayKey, points, blockData);
        } catch (IOException e) {
            StarConfig.print(e);
        }

        return null;
    }

    @Override
    public void close() {
        try { reader.close(); } catch (IOException e) { StarConfig.print(e); }
    }
}
