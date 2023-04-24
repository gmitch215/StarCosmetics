package me.gamercoder215.starcosmetics.api.cosmetics;

import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Represents the registry for all StarCosmetics Cosmetics.
 */
public interface CosmeticRegistry {

    /**
     * Fetches a list of Locations for a specific Cosmetic.
     * @param parentClass Parent Cosmetic Class
     * @return List of Cosmetic Locations
     */
    @NotNull
    List<CosmeticLocation<?>> getAllFor(@Nullable Class<? extends Cosmetic> parentClass);

    /**
     * Fetches a list of Locations for a specific Cosmetic.
     * @param parent Parent Cosmetic
     * @return List of Cosmetic Locations
     */
    @NotNull
    default List<CosmeticLocation<?>> getAllFor(@Nullable Cosmetic parent) {
        return getAllFor(parent.getClass());
    }

    /**
     * Fetches a list of all registered CosmeticLocations.
     * @return List of Cosmetic Locations
     */
    @NotNull
    default List<CosmeticLocation<?>> getAllCosmetics() {
        return getAllFor(Cosmetic.class);
    }

    /**
     * Fetches a CosmeticLocation by its full key.
     * @param key Full Key
     * @return Cosmetic Location found, or null if not found
     */
    @Nullable
    default CosmeticLocation<?> getByFullKey(@Nullable String key) {
        return getAllCosmetics().stream()
                .filter(l -> l.getFullKey().equals(key))
                .findFirst()
                .orElse(null);
    }

    /**
     * Fetches a list of all registered Cosmetic Parents.
     * @return List of Registered Cosmetic Parents
     */
    @NotNull
    List<Cosmetic> getAllParents();

    /**
     * Fetches a Cosmetic by its namespace.
     * @param key Cosmetic Namespace
     * @return Cosmetic Parent
     */
    @Nullable
    Cosmetic getByNamespace(@NotNull String key);

    /**
     * Fetches a set of all structures available for use.
     * @return Set of Structures
     */
    @NotNull
    Set<StructureInfo> getAvailableStructures();

    /**
     * Adds a Structure to the registry.
     * @param file Structure File
     * @throws IllegalArgumentException If the file is not a valid Structure File
     */
    default void addStructure(@NotNull File file) throws IllegalArgumentException {
        if (!file.exists()) throw new IllegalArgumentException("File does not exist!");
        if (!file.getName().endsWith(".scs")) throw new IllegalArgumentException("File must be a .scs file!");

        try {
            addStructure(new BufferedInputStream(new FileInputStream(file)));
        } catch (FileNotFoundException e) {
            throw new IllegalArgumentException("File does not exist!");
        }
    }

    /**
     * Adds a Structure to the registry.
     * @param stream Structure InputStream
     */
    void addStructure(@NotNull InputStream stream);

    /**
     * Fetches a Map of all Pet Types to their information.
     * @return Pet Information Map
     */
    @NotNull
    Map<PetType, PetInfo> getAllPets();

    /**
     * Fetches a PetInfo by its PetType.
     * @param type Pet Type
     * @return Pet Information
     */
    @Nullable
    default PetInfo getPetInfo(@Nullable PetType type) {
        return getAllPets().get(type);
    }

}
