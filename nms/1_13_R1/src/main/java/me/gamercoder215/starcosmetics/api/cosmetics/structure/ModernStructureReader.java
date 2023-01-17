package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
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

    private static final SecureRandom r = Constants.r;

    public ModernStructureReader(Reader r) {
        this.reader = new BufferedReader(r);
    }
    
    @Override
    public Structure read() {
        try {
            AtomicInteger index = new AtomicInteger(0);

            String minVersion = null;
            String displayKey = null;
            String key = null;
            Rarity rarity = Rarity.COMMON;

            Map<StructurePoint, Material> points = new HashMap<>();
            Map<StructurePoint, String> blockData = new HashMap<>();
            for (String line; (line = reader.readLine()) != null;) {
                if (line.trim().isEmpty()) {
                    index.incrementAndGet();
                    continue;
                }

                int i = index.get();

                switch (i) {
                    case 0: minVersion = line; index.incrementAndGet(); continue;
                    case 1: key = line.substring(line.indexOf(":") + 1, line.lastIndexOf(":")); index.incrementAndGet(); continue;
                    case 2: displayKey = line; index.incrementAndGet(); continue;
                    case 3: rarity = Rarity.valueOf(line.toUpperCase()); index.incrementAndGet(); continue;
                }

                if (!StructureReader.isCompatible(minVersion)) {
                    close();
                    return null;
                }

                if (i == 4 && !line.equalsIgnoreCase("---")) throw new MalformedStructureException("Malformed Strucutre File: Expected '---' but got '" + line + "'");

                if (i > 4) {
                    String material = line.split(":")[0].toUpperCase();
                    if (material.startsWith("{") && material.endsWith("}")) {
                        Map<Material, Integer> chances = new HashMap<>();
                        Map<Material, String> blockDataChances = new HashMap<>();

                        int amount = 0;
                        String[] entries = material.split(",");

                        for (String entry : entries) {
                            String[] split = entry.split("=");

                            int chance = Integer.parseInt(split[0].replaceAll("[%{}]", ""));
                            amount += chance;

                            if (split[1].contains("[") && split[1].endsWith("]")) {
                                String mat = split[1].split("\\[")[0];
                                String data = "[" + split[1].split("\\[")[1];

                                Material m;

                                try {
                                    m = StarMaterial.valueOf(mat.toUpperCase()).find();
                                } catch (IllegalArgumentException e) {
                                    try {
                                        m = Material.valueOf(mat.toUpperCase());
                                    } catch (IllegalArgumentException e2) {
                                        throw new MalformedStructureException("Unknown Material '" + mat + "'");
                                    }
                                }

                                chances.put(m, chance);
                                blockDataChances.put(m, data);
                            } else {
                                String mat = split[1].replace("}", "").toUpperCase();

                                Material m;

                                try {
                                    m = StarMaterial.valueOf(mat).find();
                                } catch (IllegalArgumentException e) {
                                    try {
                                        m = Material.valueOf(mat);
                                    } catch (IllegalArgumentException e2) {
                                        throw new MalformedStructureException("Unknown Material '" + mat + "'");
                                    }
                                }

                                chances.put(m, chance);
                            }
                        }

                        if (amount != 100) throw new MalformedStructureException("Malformed Strucutre File: Chance total is not 100% (Found " + amount + "%)");

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
                            mat = material.split("\\[")[0].toUpperCase();
                            data = "[" + material.split("\\[")[1];
                        }

                        Material m;

                        try {
                            m = StarMaterial.valueOf(mat).find();
                        } catch (IllegalArgumentException e) {
                            try {
                                m = Material.valueOf(mat);
                            } catch (IllegalArgumentException e2) {
                                throw new MalformedStructureException("Unknown Material '" + material + "'");
                            }
                        }

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
            return new ModernStructure(key, minVersion, displayKey, points, blockData, rarity);
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
