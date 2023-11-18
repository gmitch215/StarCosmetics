package me.gamercoder215.starcosmetics.api.cosmetics.emote;

import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.Statistic;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromStatistic;

/**
 * Represents an Emote that a player can use.
 */
public enum Emote {

    /**
     * The Wave Emote.
     */
    WAVE(Rarity.COMMON),
    /**
     * The Dab Emote.
     */
    DAB(Rarity.OCCASIONAL, fromStatistic(Statistic.PLAYER_KILLS, 100)),
    ;

    /**
     * The identifier tag for an Emote.
     */
    public static final String EMOTE_TAG = "emote";

    private final Rarity rarity;
    private final CompletionCriteria criteria;

    Emote(Rarity rarity) {
        this(rarity, CompletionCriteria.NONE);
    }

    Emote(Rarity rarity, CompletionCriteria criteria) {
        this.criteria = criteria;
        this.rarity = rarity;
    }

    /**
     * Gets the input stream for the Emote File.
     * @return Emote File
     */
    @NotNull
    public InputStream getFile() {
        return StarConfig.class.getResourceAsStream("/animations/" + getName() + ".animc");
    }

    /**
     * Gets the name of the Emote.
     * @return Emote Identificaiton Name
     */
    @NotNull
    public String getName() {
        return name().toLowerCase();
    }

    /**
     * Gets the rarity of the Emote.
     * @return Emote Rarity
     */
    public Rarity getRarity() {
        return rarity;
    }

    /**
     * Gets the completion criteria of the Emote.
     * @return Emote Completion Criteria
     */
    public CompletionCriteria getCriteria() {
        return criteria;
    }
}
