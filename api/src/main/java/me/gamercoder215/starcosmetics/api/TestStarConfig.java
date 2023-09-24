package me.gamercoder215.starcosmetics.api;

import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.Structure;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.InputStream;
import java.io.Reader;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @Override
    public @NotNull Set<CosmeticLocation<?>> getDisabledCosmetics() { return null; }

    @Override
    public boolean isAmbientPetSoundEnabled() { return true; }

    @Override
    public void setAmbientPetSoundEnabled(boolean enabled) {}

    @Override
    public double getRequirementMultiplier() { return 0; }

    @Override
    public double getRequirementMultiplier(@Nullable CosmeticLocation<?> loc) { return 0;}

    @Override
    public void setRequirementMultiplier(double multiplier) {}

    @Override
    public void setRequirementMultiplier(@Nullable CosmeticLocation<?> loc, double multiplier) {}

    @Override
    public @NotNull List<OfflinePlayer> getBlacklistedPlayers() { return new ArrayList<>(); }

    @Override
    public void setBlacklistedPlayers(@NotNull Iterable<? extends OfflinePlayer> players) {}

    @Override
    public @NotNull Set<Sound> getBlacklistedSounds() { return new HashSet<>(); }

    @Override
    public void setBlacklistedSounds(@NotNull Iterable<Sound> sounds) {}

    @Override
    public @NotNull Set<CosmeticLocation<?>> getCustomCosmetics() { return new HashSet<>(); }

    @Override
    public int getInternalMaxHologramLimit() { return 48; }

    @Override
    public int getMaxHologramLimit() { return 0; }

    @Override
    public void setMaxHologramLimit(int limit) {}

    @Override
    public @NotNull Set<Structure> getCustomStructures() { return new HashSet<>(); }

}
