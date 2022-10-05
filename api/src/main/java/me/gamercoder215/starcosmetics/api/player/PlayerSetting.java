package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.jetbrains.annotations.NotNull;

/**
 * Represents a setting available to players.
 * @since 1.0.0
 */
public enum PlayerSetting {

    ;

    private final boolean def;
    private final String displayKey;

    PlayerSetting(String displayKey, boolean def) {
        this.displayKey = displayKey;
        this.def = def;
    }

    /**
     * Fetches the default value of this setting.
     * @return Default Value
     */
    @NotNull
    public boolean getDefaultValue() {
        return def;
    }

    /**
     * Fetches the setting's display name.
     * @return Setting Display Name
     * @since 1.0.0
     */
    @NotNull
    public String getDisplayName() {
        return StarConfig.getConfig().get(displayKey);
    }
}
