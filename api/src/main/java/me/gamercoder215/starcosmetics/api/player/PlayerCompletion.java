package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a custom completion a player has to achieve to unlock cosmetics.
*/
public enum PlayerCompletion {

    /**
     * Completion for reaching the roof of the Nether.
     */
    NETHER_ROOF("completion.nether_roof")
    ;

    private final String displayKey;

    PlayerCompletion(String displayKey) {
        this.displayKey = displayKey;
    }

    /**
     * Fetches the completion's display name.
     * @return Completion Display Name
     */
    @NotNull
    public String getDisplayName() {
        return StarConfig.getConfig().get(displayKey);
    }

}
