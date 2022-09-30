package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.Structure;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructurePoint;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;

public final class LegacyStructureReader implements StructureReader {
    
    private final BufferedReader reader;

    public LegacyStructureReader(Reader r) {
        this.reader = new BufferedReader(r);
    }

    @Override
    public Structure read() {
        try {
            AtomicInteger index = new AtomicInteger(0);

            String minVersion = null;
            String displayKey = null;

            Map<StructurePoint, Material> points = new HashMap<>();
            Map<StructurePoint, EntityType> entities = new HashMap<>();
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
                    if (!line.equalsIgnoreCase("---")) {
                        String material = line.split(":")[0];
                        if (material.startsWith("{") && material.endsWith("}")) {

                        } else {

                        if (Material.matchMaterial(material) == null) throw new RuntimeException("Unknown Material '" + material + "'");
                            Material m = Material.matchMaterial(material);

                            String[] coords = line.split(":")[1].split("*");

                            for (String coord : coords) {
                                String[] split = coord.replaceAll("[\\[\\]]", "").split(",");
                                int x = Integer.parseInt(split[0]);
                                int y = Integer.parseInt(split[1]);
                                int z = Integer.parseInt(split[2]);

                                points.put(new StructurePoint(x, y, z), m);
                            }
                        }
                    } else {
                        // read entities
                    }
                }

                index.incrementAndGet();
            }

            return new Structure(minVersion, displayKey, points, entities);
        } catch (IOException e) {
            StarConfig.print(e);
        }

        return null;
    }

    @Override
    public void close() {
        try { reader.close(); } catch (IOException e) { StarConfig.print(e); }
    }

    public static void main(String[] args) throws Exception {
        LegacyStructureReader r = new LegacyStructureReader(new FileReader(new File("/workspaces/StarCosmetics/plugin/src/main/resources/structures/1.9/tree.scs")));
        for (String line; (line = r.reader.readLine()) != null;) {
            System.out.println(line);
        }
    }

    

}
