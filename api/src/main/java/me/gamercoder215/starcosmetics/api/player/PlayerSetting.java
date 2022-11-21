package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Field;

/**
 * Represents a setting available to players.
 * @param <T> Setting Type
 */
public final class PlayerSetting<T> {

    /**
     * Whether the player should receive chat notifications.
     */
    @SettingDescription("settings.notifications.desc")
    public static final PlayerSetting<Boolean> NOTIFICATIONS =
            new PlayerSetting<>("notifications", "settings.notifications", boolean.class, true);

    /**
     * Whether the player should receive sound notifications.
     */
    @SettingDescription("settings.notifications.sound.desc")
    public static final PlayerSetting<Boolean> SOUND_NOTIFICATIONS =
            new PlayerSetting<>("sound_notifications", "settings.notifications.sound", boolean.class, true);

    private final T def;
    private final Class<T> type;
    private final String displayKey;

    private final String id;

    private PlayerSetting(String id, String displayKey, Class<T> type, T def) {
        this.displayKey = displayKey;
        this.def = def;
        this.type = type;
        this.id = id;
    }

    /**
     * Fetches the default value of this setting.
     * @return Default Value
     */
    @NotNull
    public T getDefaultValue() {
        return def;
    }

    /**
     * Fetches the Setting's ID.
     * @return Setting ID
     */
    @NotNull
    public String getId() {
        return id;
    }

    /**
     * Fetches the Setting Type.
     * @return Setting Type
     */
    @NotNull
    public Class<T> getType() {
        return type;
    }

    /**
     * Fetches the setting value off of a Player.
     * @param p Player to fetch from
     * @param setting Setting to fetch
     * @param <T> Setting Type
     * @return Setting Value
     * @throws IllegalArgumentException if player or setting is null
     */
    public static <T> T getSettingValue(@NotNull Player p, @NotNull PlayerSetting<T> setting) throws IllegalArgumentException {
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
            Field f = PlayerSetting.class.getDeclaredField(id.toUpperCase());
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
