package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.IOException;

/**
 *
 */
public class StarPlayer {

    // Setting Constants

    /**
     * Represents the Notification Setting.
     */
    public static final String SETTING_NOTIFICATIONS = "notifications";

    // Completion Constants

    /**
     * Represents the Completion of stepping on the Nether Roof.
     */
    public static final String COMPLETION_NETHER_ROOF = "nether_roof";


    private final OfflinePlayer player;
    private final File file;
    private final FileConfiguration config;

    public StarPlayer(OfflinePlayer player) {
        this.player = player;

        this.file = new File(StarConfig.getPlayerDirectory(), player.getUniqueId().toString() + ".yml");
        if (!file.exists()) try { file.createNewFile(); } catch (Exception e) { StarConfig.print(e); }

        this.config = YamlConfiguration.loadConfiguration(file);

        if (!config.isString("name")) {
            config.set("name", player.getName());
            try { config.save(file); } catch (IOException e) { StarConfig.print(e); }
        }
    }

    public OfflinePlayer getPlayer() {
        return player;
    }

    public File getFile() {
        return file;
    }

    public FileConfiguration getConfig() {
        return config;
    }

    // Completion Configuration

    /**
     * Whether or not the player has completed the specified completion.
     * @param s The completion to check.
     * @return true if completed, false otherwise
     */
    public boolean hasCompleted(@NotNull String s) {
        if (s == null) return false;
        return config.getBoolean("completions." + s, false);
    }

    /**
     * Sets the completion of the specified completion to the specified value.
     * @param s The completion to set.
     * @param b The value to set the completion to.
     */
    public void setCompleted(@NotNull String s, boolean b) {
        if (s == null) return;
        if (!config.isConfigurationSection("completions")) config.createSection("completions");

        config.set("completions." + s, b);
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }
    }

    // Settings

    /**
     * Whether or not the player has the specified setting enabled.
     * @param s The setting to check.
     * @return true if enabled, false otherwise
     */
    public boolean getSetting(@NotNull String s) {
        if (s == null) return false;
        return getSetting(s, false);
    }

    /**
     * Whether or not the player has the specified setting enabled.
     * @param s The setting to check.
     * @param def The default value to return if the setting is not set.
     * @return true if enabled, false otherwise
     */
    public boolean getSetting(String s, boolean def) {
        if (!config.isConfigurationSection("settings")) config.createSection("settings");

        return config.getBoolean("settings." + s, def);
    }

    /**
     * Sets the specified setting to the specified value.
     * @param s The setting to set.
     * @param b The value to set the setting to.
     * @return true if enabled, false otherwise
     */
    public boolean setSetting(String s, boolean b) {
        if (!config.isConfigurationSection("settings")) config.createSection("settings");

        config.set("settings." + s, b);
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }

        return b;
    }



}
