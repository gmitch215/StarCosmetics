package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.Constants;
import org.bukkit.Material;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import java.security.SecureRandom;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

public final class LegacyStructureReader implements StructureReader {
    
    private final BufferedReader reader;

    private static final SecureRandom r = Constants.r;

    public LegacyStructureReader(Reader r) {
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
            for (String line; (line = reader.readLine()) != null;) {
                int i = index.get();

                switch (i) {
                    case 0: minVersion = line; index.incrementAndGet(); continue;
                    case 1: key = line.substring(line.indexOf(":") + 1, line.lastIndexOf(":")); index.incrementAndGet(); continue;
                    case 2: displayKey = line; index.incrementAndGet(); continue;
                    case 3: rarity = Rarity.valueOf(line.toUpperCase()); index.incrementAndGet(); continue;
                }

                if (i == 4 && !line.equalsIgnoreCase("---")) throw new MalformedStructureException("Malformed Strucutre File: Expected '---' but got '" + line + "'");
                
                if (i > 4) {

                    String material = line.split(":")[0];
                    if (material.startsWith("{") && material.endsWith("}")) {
                        Map<Material, Integer> chances = new HashMap<>();

                        int amount = 0;
                        String[] entries = material.split(",");

                        for (String entry : entries) {
                            String[] split = entry.split("=");

                            int chance = Integer.parseInt(split[0].replaceAll("[%{}]", ""));
                            amount += chance;

                            if (Material.matchMaterial(split[1]) == null)
                                throw new MalformedStructureException("Unknown Material '" + split[1] + "'");

                            chances.put(Material.matchMaterial(split[1]), chance);
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
                        for (StructurePoint p : StructureReader.readPoints(coords))
                            points.put(p, chanceMap.get(r.nextInt(100)));
                    } else {
                        if (Material.matchMaterial(material) == null)
                            throw new MalformedStructureException("Unknown Material '" + material + "'");
                        Material m = Material.matchMaterial(material);

                        String coords = line.split(":")[1];
                        for (StructurePoint p : StructureReader.readPoints(coords)) points.put(p, m);
                    }
                }

                index.incrementAndGet();
            }

            close();
            return new LegacyStructure(key, minVersion, displayKey, points, rarity);
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
