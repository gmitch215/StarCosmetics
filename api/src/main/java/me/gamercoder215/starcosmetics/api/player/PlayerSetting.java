package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * Represents a setting available to players.
 */
public enum PlayerSetting {

    /**
     * Whether the player should receive chat notifications.
     */
    @SettingDescription("settings.notifications.desc")
    NOTIFICATIONS("settings.notifications", true),

    /**
     * Whether the player should receive sound notifications.
     */
    @SettingDescription("settings.notifications.sound.desc")
    SOUND_NOTIFICATIONS("settings.notifications.sound", true),
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
     * Fetches the setting value off of a Player.
     * @param p Player to fetch from
     * @param setting Setting to fetch
     * @return Setting Value
     * @throws IllegalArgumentException if player or setting is null
     */
    public static boolean getSettingValue(@NotNull Player p, @NotNull PlayerSetting setting) throws IllegalArgumentException {
        if (p == null) throw new IllegalArgumentException("Player cannot be null!");
        if (setting == null) throw new IllegalArgumentException("Setting cannot be null!");

        StarPlayer sp = new StarPlayer(p);
        return sp.getSetting(setting);
    }

    /**
     * Fetches the setting's display name.
     * @return Setting Display Name
     */
    @NotNull
    public String getDisplayName() {
        return StarConfig.getConfig().get(displayKey);
    }

    /**
     * Fetches the setting's description.
     * @return Setting Description
     */
    @NotNull
    public String getDescription() {
        try {
            Field f = PlayerSetting.class.getDeclaredField(name());
            f.setAccessible(true);

            SettingDescription desc = f.getDeclaredAnnotation(SettingDescription.class);
            if (desc == null) return "";

            return desc.value();
        } catch (NoSuchFieldException e) {
            StarConfig.print(e);
        }

        return "";
    }
}
