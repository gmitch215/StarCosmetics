package me.gamercoder215.starcosmetics.api;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.util.logging.Logger;

public interface StarConfig {

    static Plugin getPlugin() {
        return Bukkit.getPluginManager().getPlugin("StarCosmetics");
    }

    static StarConfig getConfig() {
        return (StarConfig) getPlugin();
    }

    static void print(Throwable t) {
        getLogger().severe(t.getClass().getSimpleName());
        getLogger().severe("--------------------------");
        getLogger().severe(t.getMessage());
        for (StackTraceElement s : t.getStackTrace()) getLogger().severe(s.toString());
    }

    static FileConfiguration getConfiguration() {
        if (!getConfigurationFile().exists()) try {
            getConfigurationFile().createNewFile();
        } catch (IOException e) {
            print(e);
        }

        return getPlugin().getConfig();
    }

    static File getDataFolder() {
        return getPlugin().getDataFolder();
    }

    static Logger getLogger() {
        return getPlugin().getLogger();
    }

    static File getConfigurationFile() {
        return new File(getDataFolder(), "config.yml");
    }

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

    static File getPlayerDirectory() {
        File f = new File(getDataFolder(), "players");
        if (!f.exists()) f.mkdir();
        return f;
    }

    // Implementation

    String getLanguage();

    String get(String key);

    String getMessage(String key);
}
