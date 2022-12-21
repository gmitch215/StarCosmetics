package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.jetbrains.annotations.NotNull;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import java.util.function.Function;
import java.util.stream.Collectors;

import static org.bukkit.Material.STONE;

/**
 * Represents an Information Holder for a Structure
 */
public final class StructureInfo {

    private final Map<StructurePoint, Material> points;
    private final String localizedName;
    private final String minVersion;
    private final Structure structure;

    StructureInfo(@NotNull Structure s) {
        this.structure = s.clone();

        this.points = s.getMaterials();
        this.localizedName = s.getLocalizedName();
        this.minVersion = s.minVersion;
    }

    /**
     * Fetches a Map of StructurePoints to Materials that the Structure will place.
     * @return StructurePoint to Material Blueprint Map
     */
    @NotNull
    public Map<StructurePoint, Material> getPoints() {
        return ImmutableMap.copyOf(points);
    }

    /**
     * Fetches the Structure this information uses.
     * @return Structure
     */
    @NotNull
    public Structure getStructure() {
        return structure.clone();
    }

    /**
     * Fetches the unique identifier of the Structure.
     * @return Structure Key
     */
    @NotNull
    public UUID getUniqueId() {
        return UUID.nameUUIDFromBytes(structure.key.getBytes(StandardCharsets.UTF_8));
    }

    /**
     * Fetches the Structure's Rarity.
     * @return Structure Rarity
     */
    @NotNull
    public Rarity getRarity() {
        return structure.getRarity();
    }

    /**
     * Fetches the Localized Name of the Structure.
     * @return Localized Name
     */
    @NotNull
    public String getLocalizedName() {
        return localizedName;
    }

    /**
     * Fetches the minimum version of Minecraft that this Structure is compatible with.
     * @return Minimum Minecraft Version
     */
    @NotNull
    public String getMinVersion() {
        return minVersion;
    }

    /**
     * Whether this Structure is compatible with this version of Minecraft.
     * @deprecated Structures will not be read properly and will throw errors if they are read on an incompatible version, making this method obselete.
     * @return true if this Structure is compatible with this version of Minecraft, false otherwise.
     */
    @Deprecated
    public boolean isCompatible() {
        if (minVersion.equalsIgnoreCase("ALL")) return true;

        String currentV = Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);

        int current = Integer.parseInt(currentV.split("_")[1]);
        int required = Integer.parseInt(minVersion.split("\\.")[1]);
        return current >= required;
    }

    /**
     * Fetches the Completion Criteria for this Structure.
     * @return Completion Criteria
     */
    @NotNull
    public CompletionCriteria getCriteria() {
        return StructureCompletion.byRarity(getRarity()).getCriteria();
    }

    /**
     * Fetches the Primary Material used in this Structure.
     * @return Primary Material
     */
    @NotNull
    public Material getPrimaryMaterial() {
        return points.values()
                .stream()
                .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                .entrySet()
                .stream()
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(STONE);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        StructureInfo that = (StructureInfo) o;
        return getUniqueId().equals(that.getUniqueId());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getUniqueId());
    }

    @Override
    public String toString() {
        return "StructureInfo{" +
                "structure=" + structure +
                '}';
    }
}
