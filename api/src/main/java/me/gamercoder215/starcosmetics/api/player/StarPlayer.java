package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

public class StarPlayer {

    // Setting Constants

    public static final String SETTING_NOTIFICATIONS = "notifications";

    // Completion Constants

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

    public boolean hasCompleted(String s) {
        return config.getBoolean("completions.", false);
    }

    public void setCompleted(String s, boolean b) {
        if (!config.isConfigurationSection("completions")) config.createSection("completions");

        config.set("completions." + s, b);
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }
    }

    // Settings

    public boolean getSetting(String s) {
        return getSetting(s, false);
    }

    public boolean getSetting(String s, boolean def) {
        if (!config.isConfigurationSection("settings")) config.createSection("settings");

        return config.getBoolean("settings." + s, def);
    }

    public boolean setSetting(String s, boolean b) {
        if (!config.isConfigurationSection("settings")) config.createSection("settings");

        config.set("settings." + s, b);
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }

        return b;
    }



}
