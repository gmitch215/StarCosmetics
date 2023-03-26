package me.gamercoder215.starcosmetics.api;

import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;

final class TestStarConfig implements StarConfig {

    @Override
    public @NotNull String getLanguage() {
        return "en";
    }

    @Override
    public @NotNull String get(String key) {
        return "";
    }

    @Override
    public void updatePluginCache() {
    }

    @Override
    public long getEntityDisappearTime() {
        return 4;
    }

    @Override
    public void setEntityDisappearTime(long time) throws IllegalArgumentException {
    }

    @Override
    public long getItemDisappearTime() {
        return 10;
    }

    @Override
    public void setItemDisappearTime(long time) throws IllegalArgumentException {
    }

    @Override
    public long getBlockDisappearTime() {
        return 15;
    }

    @Override
    public void setBlockDisappearTime(long time) throws IllegalArgumentException {}

    @Override
    public StructureReader getStructureReader(@NotNull File file) {
        return null;
    }

    @Override
    public StructureReader getStructureReader(@NotNull InputStream stream) {
        return null;
    }

    @Override
    public StructureReader getStructureReader(@NotNull Reader reader) {
        return null;
    }

}
