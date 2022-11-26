package me.gamercoder215.starcosmetics.api.player;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

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
            ofBoolean("notifications", "settings.notifications", true);

    /**
     * Whether the player should receive sound notifications.
     */
    @SettingDescription("settings.notifications.sound.desc")
    public static final PlayerSetting<Boolean> SOUND_NOTIFICATIONS =
            ofBoolean("sound_notifications", "settings.notifications.sound", true);

    /**
     * Whether the player should be launched when spawning a structure, to "avoid" suffocation.
     */
    @SettingDescription("settings.structure_velocity.desc")
    public static final PlayerSetting<Boolean> STRUCTURE_VELOCITY =
            ofBoolean("structure_velocity", "settings.structure_velocity", true);

    private final T def;
    private final Class<T> type;
    private final String displayKey;

    private final String id;
    private final List<T> values = new ArrayList<>();

    private PlayerSetting(String id, String displayKey, Class<T> type, T def) {
        this.displayKey = displayKey;
        this.def = def;
        this.type = type;
        this.id = id;
    }

    @SafeVarargs
    private PlayerSetting(String id, String displayKey, Class<T> type, T def, T... values) {
        this.displayKey = displayKey;
        this.def = def;
        this.type = type;
        this.id = id;
        this.values.addAll(Arrays.asList(values));
    }

    // Default Value Impl

    private static PlayerSetting<Boolean> ofBoolean(String id, String displayKey, boolean def) {
        return new PlayerSetting<>(id, displayKey, Boolean.class, def, true, false);
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
     * Fetches all of the possible values for this setting.
     * @return List of Possible Values
     */
    @NotNull
    public List<T> getPossibleValues() {
        return ImmutableList.copyOf(values);
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

    /**
     * Creates an array of all of the Settings.
     * @return Setting Values Array
     */
    @NotNull
    public static PlayerSetting<?>[] values() {
        List<PlayerSetting<?>> values = new ArrayList<>();

        try {
            for (Field f : PlayerSetting.class.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) continue;

                if (PlayerSetting.class.isAssignableFrom(f.getType())) {
                    f.setAccessible(true);
                    values.add((PlayerSetting<?>) f.get(null));
                }
            }
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        }

        values.sort(Comparator.comparing(PlayerSetting::getId));

        return values.toArray(new PlayerSetting[0]);
    }

    /**
     * Fetches a Setting by its ID.
     * @param id Setting ID
     * @return Setting
     */
    public static PlayerSetting<?> byId(@Nullable String id) {
        if (id == null) return null;

        return Arrays.stream(values())
                .filter(s -> s.getId().equalsIgnoreCase(id))
                .findFirst()
                .orElse(null);
    }
}
