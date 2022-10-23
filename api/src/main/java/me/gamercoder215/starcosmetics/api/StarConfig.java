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
     */
    @NotNull
    static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("StarCosmetics");
    }

    /**
     * Fetches the StarConfig instance.
     * @return StarConfig Instance
     */
    @NotNull
    static StarConfig getConfig() {
        return (StarConfig) getPlugin();
    }

    /**
     * Prints a Throwable in the StarCosmetics Logger.
     * @param t Throwable to print.
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
        if (Bukkit.getServer() == null) return Logger.getLogger("StarCosmetics");

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
     * Fetches a String from the Language File.
     * @param key Key to fetch.
     * @return String from the Language File according to the current Language
     */
    @NotNull
    String get(String key);

    /**
     * Fetches a String from the Language File with the plugin prefix in front.
     * @param key Key to fetch.
     * @return Message from the Language File according to the current Language
     */
    @NotNull
    String getMessage(String key);
}
