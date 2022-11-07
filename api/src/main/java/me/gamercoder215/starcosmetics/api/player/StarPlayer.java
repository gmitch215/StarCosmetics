package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.Completion;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.registry.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import me.gamercoder215.starcosmetics.api.player.cosmetics.SoundEventSelection;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Represents a player used by StarCosmetics to manage their configuration.
*/
public final class StarPlayer {

    private final OfflinePlayer player;
    private final File folder;

    // Configuration Files
    private final File configF;
    private final FileConfiguration config;

    private final File soundF;
    private final FileConfiguration sounds;

    /**
     * Constructs a new StarPlayer.
     * @param player OfflinePlayer to use
     */
    public StarPlayer(@NotNull OfflinePlayer player) {
        this.player = player;

        this.folder = new File(StarConfig.getPlayerDirectory(), player.getUniqueId().toString());
        if (!folder.exists()) folder.mkdir();

        this.configF = new File(folder, "config.yml");
        if (!configF.exists()) try { configF.createNewFile(); } catch (IOException e) { StarConfig.print(e); }
        this.config = YamlConfiguration.loadConfiguration(configF);

        this.soundF = new File(folder, "sounds.yml");
        if (!soundF.exists()) try { soundF.createNewFile(); } catch (IOException e) { StarConfig.print(e); }
        this.sounds = YamlConfiguration.loadConfiguration(soundF);
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
     * Fetches the player's name.
     * @return Player Name
     */
    @NotNull
    public String getName() { return player.getName(); }

    /**
     * Fetches the player's UUID.
     * @return Player UUID
     */
    @NotNull
    public UUID getUniqueId() { return player.getUniqueId(); }

    /**
     * Fetches the Folder this player's configuration files are stored in.
     * @return Player's Data Folder
     */
    @NotNull
    public File getFolder() {
        return folder;
    }

    /**
     * Fetches the Main FileConfiguration of this StarPlayer.
     * @return Main FileConfiguration Instance
     */
    @NotNull
    public FileConfiguration getConfig() {
        return config;
    }

    /**
     * Fetches the File that{@link #getConfig()} is stored in.
     * @return Main Configuration File
     */
    @NotNull
    public File getConfigFile() {
        return configF;
    }

    // Completion Configuration

    /**
     * Whether or not the player has completed the specified completion.
     * @param c The completion to check.
     * @return true if completed, false otherwise
     */
    public boolean hasCompleted(@NotNull Completion c) {
        if (c == null) return false;
        return config.getBoolean("completions." + c.getKey(), false);
    }

    /**
     * Sets the completion of the specified completion to the specified value.
     * @param c The completion to set.
     * @param b The value to set the completion to.
     */
    public void setCompleted(@NotNull Completion c, boolean b) {
        if (c == null) return;

        config.set("completions." + c.getKey(), b);
        try { config.save(folder); } catch (IOException e) { StarConfig.print(e); }
    }

    /**
     * Sends a Notification to this StarPlayer.
     * @param message Message to Send
     */
    public void sendNotification(@Nullable String message) {
        if (message == null) return;
        if (!getSetting(PlayerSetting.NOTIFICATIONS)) return;
        if (!player.isOnline()) return;
        if (!player.isOnline()) return;

        player.getPlayer().sendMessage(message);
    }

    /**
     * Sets the completion of the specified completion to true.
     * @param c The completion to set.
     */
    public void setCompleted(@NotNull Completion c) {
        setCompleted(c, true);
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

        return config.getBoolean("settings." + setting.name().toLowerCase(), def);
    }

    /**
     * Sets the specified setting to the specified value.
     * @param setting The setting to set.
     * @param b The value to set the setting to.
     * @return true if enabled, false otherwise
     */
    public boolean setSetting(@NotNull PlayerSetting setting, boolean b) {
        config.set("settings." + setting.name().toLowerCase(), b);
        save();

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

        String path = "cosmetics." + clazz.getSimpleName().toLowerCase();

        return CosmeticLocation.getByFullKey(config.getString(path, null));
    }

    /**
     * Fetches the selected trail cosmetic.
     * @param type {@link TrailType} to use
     * @return Selected Cosmetic for this Trail Type
     */
    public @Nullable CosmeticLocation<?> getSelectedTrail(@NotNull TrailType type) {
        if (type == null) return null;

        String path = "cosmetics.trails." + type.name().toLowerCase();
        return CosmeticLocation.getByFullKey(config.getString(path, null));
    }

    /**
     * Sets the selected Trail Cosmetic for the specified Trail Type.
     * @param type Trail Type to use
     * @param cosmetic The cosmetic to set.
     */
    public void setSelectedTrail(@NotNull TrailType type, @Nullable CosmeticLocation<?> cosmetic) {
        if (type == null) return;

        String path = "cosmetics.trails." + type.name().toLowerCase();

        if (cosmetic == null) config.set(path, null);
        else config.set(path, cosmetic.getFullKey());

        save();
    }

    /**
     * Sets the selected cosmetic.
     * @param clazz The class of the cosmetic to set.
     * @param loc The location to set the cosmetic to.
     */
    public void setSelectedCosmetic(@NotNull Class<? extends Cosmetic> clazz, @NotNull CosmeticLocation<?> loc) {
        if (clazz == null || loc == null) return;
        if (Cosmetic.class.equals(clazz)) return;

        String path = "cosmetics." + clazz.getSimpleName().toLowerCase();

        config.set(path, loc.getFullKey());
        save();
    }

    /**
     * Fetches a list of entries that this player has for sound events.
     * @return List of Sound Event Entries
     */
    @NotNull
    public List<SoundEventSelection> getSoundSelections() {
        List<SoundEventSelection> selections = new ArrayList<>();
        if (!sounds.isList("sounds")) {
            sounds.set("sounds", new ArrayList<>());
            return selections;
        }

        for (Object o : sounds.getList("sounds")) {
            if (!(o instanceof SoundEventSelection)) continue;
            selections.add((SoundEventSelection) o);
        }

        return selections;
    }

    /**
     * <p>Saves all of this StarPlayer's configuration.</p>
     * <p>Methods that edit the configuration automatically save the configuration, so an additional call is not necessary.</p>
     */
    public void save() {
        try {
            config.save(configF);
        } catch (IOException e) {
            StarConfig.print(e);
        }
    }

}
