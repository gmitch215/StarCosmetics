package me.gamercoder215.starcosmetics.api;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

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
    static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("StarCosmetics");
    }

    /**
     * Fetches the StarConfig instance.
     * @return StarConfig Instance
     */
    static StarConfig getConfig() {
        return (StarConfig) getPlugin();
    }

    /**
     * Prints a Throwable in the StarCosmetics Logger.
     * @param t Throwable to print.
     */
    static void print(Throwable t) {
        getLogger().severe(t.getClass().getSimpleName());
        getLogger().severe("--------------------------");
        getLogger().severe(t.getMessage());
        for (StackTraceElement s : t.getStackTrace()) getLogger().severe(s.toString());
    }

    /**
     * Fetches the StarCosmetics Configuration.
     * @return StarCosmetics Configuration
     */
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
    static File getDataFolder() {
        return getPlugin().getDataFolder();
    }

    /**
     * Fetches the StarCosmetics Logger.
     * @return StarCosmetics Logger
     */
    static Logger getLogger() {
        return getPlugin().getLogger();
    }

    /**
     * Fetches the StarCosmetics Configuration File.
     * @return StarCosmetics Configuration File
     */
    static File getConfigurationFile() {
        return new File(getDataFolder(), "config.yml");
    }

    /**
     * Loads the StarCosmetics Configuration.
     * @return StarCosmetics Configuration
     */
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
     * Fetches the StarCosmetics Player Data Directory.
     * @return StarCosmetics Player Data Directory
     */
    static File getPlayerDirectory() {
        File f = new File(getDataFolder(), "players");
        if (!f.exists()) f.mkdir();
        return f;
    }

    // Implementation

    /**
     * Fetches the current Language.
     * @return Current Language
     */
    String getLanguage();

    /**
     * Fetches a String from the Language File.
     * @param key Key to fetch.
     * @return String from the Language File according to the current Language
     */
    String get(String key);

    /**
     * Fetches a String from the Language File with the plugin prefix in front.
     * @param key Key to fetch.
     * @return Message from the Language File according to the current Language
     */
    String getMessage(String key);
}
