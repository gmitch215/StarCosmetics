package me.gamercoder215.starcosmetics.api;

import com.google.common.collect.Iterables;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRegistry;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.Reader;
import java.util.*;
import java.util.logging.Logger;
import java.util.stream.Collectors;

/**
 * Represents the main StarCosmetics Configuration.
 */
public interface StarConfig {

    /**
     * Fetches the plugin.
     * @return The plugin.
     */
    @NotNull
    static Plugin getPlugin() {
        try {
            return Bukkit.getPluginManager().getPlugin("StarCosmetics");
        } catch (NullPointerException ignored) {}
        return null;
    }

    /**
     * Updates the Plugin's Cache, removing any old data.
     * @see #updatePluginCache()
     */
    static void updateCache() {
        getConfig().updatePluginCache();
    }

    /**
     * Fetches the StarConfig instance.
     * @return StarConfig Instance
     */
    @NotNull
    static StarConfig getConfig() {
        if (Bukkit.getServer() == null) return new TestStarConfig(); // Using Test Implementation

        return (StarConfig) getPlugin();
    }

    /**
     * Prints a Throwable in the StarCosmetics Logger.
     * @param t Throwable to print.
     */
    static void print(@NotNull Throwable t) {
        if (t.getCause() == null) {
            getLogger().severe(t.getClass().getSimpleName());
            getLogger().severe("--------------------------");
            getLogger().severe(t.getMessage());
            for (StackTraceElement s : t.getStackTrace()) getLogger().severe(s.toString());
        } else {
            Throwable cause = t.getCause();
            getLogger().severe(t.getClass().getSimpleName() + " : " + cause.getClass().getSimpleName());
            getLogger().severe("--------------------------");
            getLogger().severe(cause.getMessage());
            for (StackTraceElement s : cause.getStackTrace()) getLogger().severe(s.toString());
        }
    }

    /**
     * Fetches the StarCosmetics Configuration.
     * @return StarCosmetics Configuration
     */
    @NotNull
    static FileConfiguration getConfiguration() {
        if (!getConfigurationFile().exists()) try {
            getConfigurationFile().createNewFile();
        } catch (IOException e) {
            print(e);
        }

        return getPlugin().getConfig();
    }

    /**
     * Fetches the plugin's data folder.
     * @return Plugin's data folder.
     */
    @NotNull
    static File getDataFolder() {
        return getPlugin().getDataFolder();
    }

    /**
     * Fetches the StarCosmetics Logger.
     * @return StarCosmetics Logger
     */
    @NotNull
    static Logger getLogger() {
        if (Bukkit.getServer() == null || getPlugin() == null) return Logger.getLogger("StarCosmetics");

        return getPlugin().getLogger();
    }

    /**
     * Fetches the StarCosmetics Configuration File.
     * @return StarCosmetics Configuration File
     */
    @NotNull
    static File getConfigurationFile() {
        return new File(getDataFolder(), "config.yml");
    }

    /**
     * Loads the StarCosmetics Configuration.
     * @return StarCosmetics Configuration
     */
    @NotNull
    static FileConfiguration loadConfig() {
        FileConfiguration config = getConfiguration();

        if (!config.isString("language")) config.set("language", "en");

        if (!config.isString("functionality.command-version") && !config.isInt("functionality.command-version"))
            config.set("functionality.command-version", "auto");

        if (!config.isInt("cosmetics.item-disappear-time")) config.set("cosmetics.item-disappear-time", 10);
        if (!config.isInt("cosmetics.block-disappear-time")) config.set("cosmetics.block-disappear-time", 4);
        if (!config.isInt("cosmetics.entity-disappear-time")) config.set("cosmetics.entity-disappear-time", 15);

        if (!config.isConfigurationSection("cosmetics.pets")) config.createSection("cosmetics.pets");
        if (!config.isBoolean("cosmetics.pets.ambient-sound")) config.set("cosmetics.pets.ambient-sound", true);

        if (!config.isConfigurationSection("cosmetics.requirement-multiplier")) config.createSection("cosmetics.requirement-multiplier");
        if (!config.isDouble("cosmetics.requirement-multiplier.global") && !config.isInt("cosmetics.requirement-multipler.global")) config.set("cosmetics.requirement-multiplier.level", 1.0D);
        if (!config.isConfigurationSection("cosmetics.requirement-multiplier.cosmetics")) config.createSection("cosmetics.requirement-multiplier.cosmetics");

        if (!config.isList("cosmetics.blacklisted-players")) config.set("cosmetics.blacklisted-players", new ArrayList<>());

        int hologramLimit = config.getInt("cosmetics.max-hologram-size", -1);
        if (hologramLimit < 5 || hologramLimit > getConfig().getInternalMaxHologramLimit())
            config.set("cosmetics.max-hologram-size", getConfig().getInternalMaxHologramLimit());

        try {
            config.save(getConfigurationFile());
        } catch (IOException e) {
            print(e);
        }

        return config;
    }

    /**
     * Fetches the StarCosmetics Custom Cosmetics File.
     * @return StarCosmetics Custom Cosmetics File
     */
    @NotNull
    static File getCosmeticsFile() {
        return new File(getDataFolder(), "cosmetics.yml");
    }

    /**
     * Loads the StarCosmetics Custom Cosmetics File into a FileConfiguration.
     * @return StarCosmetics Custom Cosmetics File
     */
    @NotNull
    static FileConfiguration loadCosmeticsFile() {
        FileConfiguration cosmetics = YamlConfiguration.loadConfiguration(getCosmeticsFile());

        if (!cosmetics.isList("particle-shapes")) cosmetics.set("particle-shapes", new ArrayList<>());

        try {
            cosmetics.save(getCosmeticsFile());
        } catch (IOException e) {
            print(e);
        }

        return cosmetics;
    }

    /**
     * Fetches the StarCosmetics Cosmetic Registry.
     * @return StarCosmetics Cosmetic Registry
     */
    @NotNull
    static CosmeticRegistry getRegistry() {
        return (CosmeticRegistry) getPlugin();
    }

    /**
     * Fetches the StarCosmetics Player Data Directory.
     * @return StarCosmetics Player Data Directory
     */
    static @NotNull File getPlayerDirectory() {
        File f = new File(getDataFolder(), "players");
        if (!f.exists()) f.mkdir();
        return f;
    }

    // Implementation

    /**
     * Fetches the current Language.
     * @return Current Language
     */
    @NotNull
    String getLanguage();

    /**
     * Fetches the locale based on {@link #getLanguage()}.
     * @return Language Locale
     */
    @NotNull
    default Locale getLocale() {
        String l = getLanguage().toLowerCase(Locale.ROOT);
        switch (l) {
            case "en": return Locale.ENGLISH;
            case "it": return Locale.ITALIAN;
            case "zh": return Locale.CHINESE;
            case "fr": return Locale.FRENCH;
            case "de": return Locale.GERMAN;
            case "ja": return Locale.JAPANESE;
            default: return new Locale(l);
        }
    }

    /**
     * Fetches a String from the Language File.
     * @param key Key to fetch.
     * @return String from the Language File according to the current Language
     */
    @NotNull
    String get(String key);

    /**
     * Fetches a String from the Language File. 
     * @param key Key to fetch.
     * @param def Default String to return if the key is not found.
     * @return String from the Language File according to the current Language
     */
    default String get(String key, String def) {
        if (get(key).equalsIgnoreCase("Unknown Value")) return def;
        else return get(key);
    }

    /**
     * Fetches a String from the Language File.
     * @param key Key to fetch.
     * @param args Arguments to replace in the String.
     * @return String from the Language File according to the current Language
     */
    default String getWithArgs(String key, Object... args) {
        return String.format(get(key), args);
    }

    /**
     * Fetches a String from the Language File.
     * @param key Key to fetch.
     * @param def Default String to return if the key is not found.
     * @param args Arguments to replace in the String.
     * @return String from the Language File according to the current Language
     */
    default String getWithArgs(String key, String def, Object... args) {
        return String.format(get(key, def), args);
    }

    /**
     * Updates the plugin's cached data, removing any old states.
     */
    void updatePluginCache();

    /**
     * How long, in ticks, an entity cosmetic should last for.
     * @return Entity Cosmetic Duration
     */
    long getEntityDisappearTime();

    /**
     * Sets how long, in ticks, an entity cosmetic should last for.
     * @param time Entity Cosmetic Duration
     * @throws IllegalArgumentException if time is not positive
     */
    void setEntityDisappearTime(long time) throws IllegalArgumentException;

    /**
     * How long, in ticks, an item cosmetic should last for.
     * @return Item Cosmetic Duration
     */
    long getItemDisappearTime();

    /**
     * Sets how long, in ticks, an item cosmetic should last for.
     * @param time Item Cosmetic Duration
     * @throws IllegalArgumentException if time is not positive
     */
    void setItemDisappearTime(long time) throws IllegalArgumentException;

    /**
     * How long, in ticks, a block cosmetic should last for.
     * @return Item Cosmetic Duration
     */
    long getBlockDisappearTime();

    /**
     * Sets how long, in ticks, a block cosmetic should last for.
     * @param time Item Cosmetic Duration
     * @throws IllegalArgumentException if time is not positive
     */
    void setBlockDisappearTime(long time) throws IllegalArgumentException;

    /**
     * Fetches the current implementation of the StructureReader.
     * @param file File to Read
     * @return StructureReader
     */
    StructureReader getStructureReader(@NotNull File file);

    /**
     * Fetches the current implementation of the StructureReader.
     * @param stream InputStream to Read
     * @return StructureReader
     */
    StructureReader getStructureReader(@NotNull InputStream stream);

    /**
     * Fetches the current implementation of the StructureReader.
     * @param reader Reader to Read
     * @return StructureReader
     */
    StructureReader getStructureReader(@NotNull Reader reader);

    /**
     * Fetches a list of all disabled cosmetics.
     * @return Disabled Cosmetics
     */
    @NotNull
    Set<CosmeticLocation<?>> getDisabledCosmetics();

    /**
     * Whether pets can play their ambient sound.
     * @return true if enabled, else false
     */
    boolean isAmbientPetSoundEnabled();

    /**
     * Sets whether pets can play their ambient sound.
     * @param enabled true if enabled, else false
     */
    void setAmbientPetSoundEnabled(boolean enabled);

    /**
     * Fetches the requirement multiplier for all cosmetics.
     * @return Requirement Multiplier
     */
    double getRequirementMultiplier();

    /**
     * Fetches the requirement multiplier for a specific cosmetic.
     * @param loc Cosmetic Location
     * @return Requirement Multiplier
     */
    double getRequirementMultiplier(@Nullable CosmeticLocation<?> loc);

    /**
     * Sets the requirement multiplier for all cosmetics.
     * @param multiplier Requirement Multiplier
     */
    void setRequirementMultiplier(double multiplier);

    /**
     * Sets the requirement multiplier for a specific cosmetic.
     * @param loc Cosmetic Location
     * @param multiplier Requirement Multiplier
     */
    void setRequirementMultiplier(@Nullable CosmeticLocation<?> loc, double multiplier);

    /**
     * Fetches an immutable list of all of the players who cannot use cosmetics.
     * @return Blacklisted Players
     */
    @NotNull
    List<OfflinePlayer> getBlacklistedPlayers();

    /**
     * Adds a player to the blacklist.
     * @param player Player to add
     */
    default void addBlacklistedPlayer(@NotNull OfflinePlayer player) {
        if (isBlacklisted(player)) return;
        setBlacklistedPlayers(Iterables.concat(getBlacklistedPlayers(), Collections.singleton(player)));
    }

    /**
     * Removes a player from the blacklist.
     * @param player Player to remove
     */
    default void removeBlacklistedPlayer(@Nullable OfflinePlayer player) {
        setBlacklistedPlayers(getBlacklistedPlayers()
                .stream()
                .filter(p -> !p.equals(player))
                .collect(Collectors.toList())
        );
    }

    /**
     * Whether a player is blacklisted.
     * @param player Player to check
     * @return true if blacklisted, else false
     */
    default boolean isBlacklisted(@Nullable OfflinePlayer player) {
        return getBlacklistedPlayers().contains(player);
    }

    /**
     * Sets the list of blacklisted players.
     * @param players Blacklisted Players
     */
    void setBlacklistedPlayers(@NotNull Iterable<? extends OfflinePlayer> players);

    /**
     * Fetches an immutable list of all of the sounds unavailable for use in {@linkplain SoundEventSelection Custom Sound Events}.
     * @return Blacklisted Sounds
     */
    @NotNull
    Set<Sound> getBlacklistedSounds();

    /**
     * Adds a sound to the blacklist.
     * @param sound Sound to add
     */
    default void addBlacklistedSound(@NotNull Sound sound) {
        if (isBlacklisted(sound)) return;
        setBlacklistedSounds(Iterables.concat(getBlacklistedSounds(), Collections.singleton(sound)));
    }

    /**
     * Removes a sound from the blacklist.
     * @param sound Sound to remove
     */
    default void removeBlacklistedSound(@Nullable Sound sound) {
        setBlacklistedSounds(getBlacklistedSounds()
                .stream()
                .filter(s -> s != sound)
                .collect(Collectors.toSet())
        );
    }

    /**
     * Whether a sound is blacklisted.
     * @param sound Sound to check
     * @return true if blacklisted, else false
     */
    default boolean isBlacklisted(@Nullable Sound sound) {
        return getBlacklistedSounds().contains(sound);
    }

    /**
     * Sets the list of blacklisted sounds.
     * @param sounds Blacklisted Sounds
     */
    void setBlacklistedSounds(@NotNull Iterable<Sound> sounds);

    /**
     * Fetches all of the custom cosmetics found in cosmetics.yml.
     * @return Set of Custom Cosmetics
     */
    @NotNull
    Set<CosmeticLocation<?>> getCustomCosmetics();

    /**
     * Fetches the hard-coded internal maximum hologram text size limit.
     * <p>In 1.9 until 1.13, this is 16. From 1.13 ownard, this is 48.</p>
     * @return Internal Maximum Hologram Text Size Limit
     */
    int getInternalMaxHologramLimit();

    /**
     * Fetches the maximum hologram text size limit.
     * @return Maximum Hologram Text Size Limit
     */
    int getMaxHologramLimit();

    /**
     * Sets the maximum hologram text size limit.
     * @param limit Maximum Hologram Text Size Limit
     */
    void setMaxHologramLimit(int limit);

}
