package me.gamercoder215.starcosmetics.api.cosmetics.emote;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.jetbrains.annotations.NotNull;

import java.io.InputStream;

/**
 * Represents an Emote that a player can use.
 */
public enum Emote {

    /**
     * The Wave Emote.
     */
    WAVE,
    /**
     * The Dab Emote.
     */
    DAB,
    ;

    Emote() {}

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

}
