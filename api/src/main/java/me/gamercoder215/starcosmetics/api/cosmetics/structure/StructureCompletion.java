package me.gamercoder215.starcosmetics.api.cosmetics.structure;

import me.gamercoder215.starcosmetics.api.Completion;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

/**
 * Represents a Completion for unlocking a {@link Structure}.
 */
public enum StructureCompletion implements Completion {

    /**
     * Represents the completion for unlocking {@link Rarity#COMMON} structures.
     */
    COMMON(CompletionCriteria.fromBlocksMined(1000)),

    /**
     * Represents the completion for unlocking {@link Rarity#OCCASIONAL} structures.
     */
    OCCASIONAL(CompletionCriteria.fromBlocksMined(5000)),

    /**
     * Represents the completion for unlocking {@link Rarity#UNCOMMON} structures.
     */
    UNCOMMON(CompletionCriteria.fromBlocksMined(10000)),

    /**
     * Represents the completion for unlocking {@link Rarity#RARE} structures.
     */
    RARE(CompletionCriteria.fromBlocksMined(50000)),

    /**
     * Represents the completion for unlocking {@link Rarity#EPIC} structures.
     */
    EPIC(CompletionCriteria.fromBlocksMined(75500)),

    /**
     * Represents the completion for unlocking {@link Rarity#LEGENDARY} structures.
     */
    LEGENDARY,

    /**
     * Represents the completion for unlocking {@link Rarity#MYTHICAL} structures.
     */
    MYTHICAL,

    /**
     * Represents the completion for unlocking {@link Rarity#ULTRA} structures.
     */
    ULTRA,

    /**
     * Represents the completion for unlocking {@link Rarity#SPECIAL} structures.
     */
    SPECIAL,

    /**
     * Represents the completion for unlocking {@link Rarity#SECRET} structures.
     */
    SECRET,
    ;

    private final CompletionCriteria criteria;

    StructureCompletion() {
        this.criteria = CompletionCriteria.fromCompletion(this);
    }

    StructureCompletion(CompletionCriteria criteria) {
        this.criteria = criteria;
    }

    @Override
    public @NotNull String getKey() {
        return name().toLowerCase();
    }

    @Override
    public @NotNull String getNamespace() {
        return "structure";
    }

    /**
     * Fetches the StructureCompletion for the specified Rarity.
     * @param rarity Rarity to fetch StructureCompletion for
     * @return StructureCompletion for specified Rarity, or null if not found
     */
    @Nullable
    public static StructureCompletion byRarity(@NotNull Rarity rarity) {
        if (rarity == null) return null;
        return valueOf(rarity.name());
    }

    @Override
    public @NotNull Rarity getRarity() {
        return Rarity.valueOf(name());
    }

    @Override
    public CompletionCriteria getCriteria() {
        return criteria;
    }
}
