package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.Structure;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;

public final class ModernStructureReader implements StructureReader {

    private final BufferedReader reader;

    public ModernStructureReader(Reader r) {
        this.reader = new BufferedReader(r);
    }

    @Override
    public Structure read() {
        return null; // TODO Setup Structure Syntax
    }

    @Override
    public void close() {
        try { reader.close(); } catch (IOException e) { StarConfig.print(e); }
    }
}
