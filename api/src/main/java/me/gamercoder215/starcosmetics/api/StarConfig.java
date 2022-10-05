package me.gamercoder215.starcosmetics.api;

import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticRegistry;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Represents the main StarCosmetics Configuration.
 */
public interface StarConfig {

    /**
     * Fetches the plugin.
     * @return The plugin.
     * @since 1.0.0
     */
    @NotNull
    static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("StarCosmetics");
    }

    /**
     * Fetches the StarConfig instance.
     * @return StarConfig Instance
     * @since 1.0.0
     */
    @NotNull
    static StarConfig getConfig() {
        return (StarConfig) getPlugin();
    }

    /**
     * Prints a Throwable in the StarCosmetics Logger.
     * @param t Throwable to print.
     * @since 1.0.0
     */
    static void print(@NotNull Throwable t) {
        getLogger().severe(t.getClass().getSimpleName());
        getLogger().severe("--------------------------");
        getLogger().severe(t.getMessage());
        for (StackTraceElement s : t.getStackTrace()) getLogger().severe(s.toString());
    }

    /**
     * Fetches the StarCosmetics Configuration.
     * @return StarCosmetics Configuration
     * @since 1.0.0
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
     * @since 1.0.0
     */
    @NotNull
    static File getDataFolder() {
        return getPlugin().getDataFolder();
    }

    /**
     * Fetches the StarCosmetics Logger.
     * @return StarCosmetics Logger
     * @since 1.0.0
     */
    @NotNull
    static Logger getLogger() {
        return getPlugin().getLogger();
    }

    /**
     * Fetches the StarCosmetics Configuration File.
     * @return StarCosmetics Configuration File
     * @since 1.0.0
     */
    @NotNull
    static File getConfigurationFile() {
        return new File(getDataFolder(), "config.yml");
    }

    /**
     * Loads the StarCosmetics Configuration.
     * @return StarCosmetics Configuration
     * @since 1.0.0
     */
    @NotNull
    static FileConfiguration loadConfig() {
        FileConfiguration config = getConfiguration();

        if (!config.isString("language")) config.set("language", "en");

        try {
            config.save(getConfigurationFile());
        } catch (IOException e) {
            print(e);
        }

        return config;
    }

    /**
     * Fetches the StarCosmetics Cosmetic Registry.
     * @return StarCosmetics Cosmetic Registry
     * @since 1.0.0
     */
    @NotNull
    static CosmeticRegistry getRegistry() {
        return (CosmeticRegistry) getPlugin();
    }

    /**
     * Fetches the StarCosmetics Player Data Directory.
     * @return StarCosmetics Player Data Directory
     * @since 1.0.0
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
     * @since 1.0.0
     */
    @NotNull
    String getLanguage();

    /**
     * Fetches a String from the Language File.
     * @param key Key to fetch.
     * @return String from the Language File according to the current Language
     * @since 1.0.0
     */
    @NotNull
    String get(String key);

    /**
     * Fetches a String from the Language File with the plugin prefix in front.
     * @param key Key to fetch.
     * @return Message from the Language File according to the current Language
     * @since 1.0.0
     */
    @NotNull
    String getMessage(String key);
}
