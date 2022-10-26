package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;

import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;

/**
 * Represents a player used by StarCosmetics to manage their configuration.
*/
public final class StarPlayer {

    private final OfflinePlayer player;
    private final File file;
    private final FileConfiguration config;

    /**
     * Constructs a new StarPlayer.
     * @param player OfflinePlayer to use
     */
    public StarPlayer(@NotNull OfflinePlayer player) {
        this.player = player;

        this.file = new File(StarConfig.getPlayerDirectory(), player.getUniqueId().toString() + ".yml");
        if (!file.exists()) try { file.createNewFile(); } catch (Exception e) { StarConfig.print(e); }

        this.config = YamlConfiguration.loadConfiguration(file);

        if (!config.isString("name")) {
            config.set("name", player.getName());
            try { config.save(file); } catch (IOException e) { StarConfig.print(e); }
        }
    }

    /**
     * Fetches the OfflinePlayer used to construct this StarPlayer.
     * @return OfflinePlayer used
     */
    @NotNull
    public OfflinePlayer getPlayer() {
        return player;
    }

    /**
     * Fetches the File this configuration is stored in.
     * @return File
     */
    @NotNull
    public File getFile() {
        return file;
    }

    /**
     * Fetches the FileConfiguration of this StarPlayer.
     * @return FileConfiguration
     */
    @NotNull
    public FileConfiguration getConfig() {
        return config;
    }

    // Completion Configuration

    /**
     * Whether or not the player has completed the specified completion.
     * @param c The completion to check.
     * @return true if completed, false otherwise
     */
    public boolean hasCompleted(@NotNull PlayerCompletion c) {
        if (c == null) return false;
        return config.getBoolean("completions." + c.name().toLowerCase(), false);
    }

    /**
     * Sets the completion of the specified completion to the specified value.
     * @param c The completion to set.
     * @param b The value to set the completion to.
     */
    public void setCompleted(@NotNull PlayerCompletion c, boolean b) {
        if (c == null) return;
        if (!config.isConfigurationSection("completions")) config.createSection("completions");

        config.set("completions." + c.name().toLowerCase(), b);
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }
    }

    // Settings

    /**
     * Whether or not the player has the specified setting enabled.
     * @param s The setting to check.
     * @return true if enabled, false otherwise
     */
    public boolean getSetting(@NotNull PlayerSetting s) {
        if (s == null) return false;
        return getSetting(s, false);
    }

    /**
     * Whether or not the player has the specified setting enabled.
     * @param setting The setting to check.
     * @param def The default value to return if the setting is not set.
     * @return true if enabled, false otherwise
     */
    public boolean getSetting(@NotNull PlayerSetting setting, boolean def) {
        if (setting == null) return false;
        if (!config.isConfigurationSection("settings")) config.createSection("settings");

        return config.getBoolean("settings." + setting.name().toLowerCase(), def);
    }

    /**
     * Sets the specified setting to the specified value.
     * @param setting The setting to set.
     * @param b The value to set the setting to.
     * @return true if enabled, false otherwise
     */
    public boolean setSetting(@NotNull PlayerSetting setting, boolean b) {
        if (!config.isConfigurationSection("settings")) config.createSection("settings");

        config.set("settings." + setting.name().toLowerCase(), b);
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }

        return b;
    }

    /**
     * Fetches the selected cosmetic.
     * @param clazz The class of the cosmetic to fetch.
     * @return Selected Cosmetic for this Cosmetic Class
     */
    @Nullable
    public CosmeticLocation<?> getSelectedCosmetic(@Nullable Class<Cosmetic> clazz) {
        if (clazz == null) return null;
        if (Cosmetic.class.equals(clazz) || Trail.class.equals(clazz)) return null;

        if (!config.isConfigurationSection("cosmetics")) {
            config.createSection("cosmetics");
            return null;
        }

        String path = "cosmetics." + clazz.getSimpleName().toLowerCase();

        return CosmeticLocation.getByFullKey(config.getString(path, null));
    }

    public <T> CosmeticLocation<T> getSelectedTrail(@NotNull Class<T> clazz) {
        if (clazz == null) return null;
        if (!config.isConfigurationSection("cosmetics")) {
            config.createSection("cosmetics");
            return null;
        }

        String path = "cosmetics.trails." + clazz.getSimpleName().toLowerCase();

        return CosmeticLocation.getByFullKey(config.getString(path, null));
    }

    /**
     * Sets the selected cosmetic.
     * @param clazz The class of the cosmetic to set.
     * @param loc The location to set the cosmetic to.
     */
    public void setSelectedCosmetic(@NotNull Class<Cosmetic> clazz, @NotNull CosmeticLocation<?> loc) {
        if (clazz == null || loc == null) return;
        if (Cosmetic.class.equals(clazz)) return;

        if (!config.isConfigurationSection("cosmetics")) config.createSection("cosmetics");
        String path = "cosmetics." + clazz.getSimpleName().toLowerCase();

        config.set(path, loc.getFullKey());
        save();
    }

    /**
     * <p>Saves this StarPlayer's configuration.</p>
     * <p>Methods that edit the configuration automatically save the configuration, so an additional call is not necessary.</p>
     */
    public void save() {
        try { config.save(file); } catch (IOException e) { StarConfig.print(e); }
    }

}
