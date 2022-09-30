package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.Reader;

import me.gamercoder215.starcosmetics.api.StarConfig;

public interface StructureReader {

    Structure read();

    void close();

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
