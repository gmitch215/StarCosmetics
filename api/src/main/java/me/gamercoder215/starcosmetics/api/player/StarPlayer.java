package me.gamercoder215.starcosmetics.api.player;

import me.gamercoder215.starcosmetics.api.Completion;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.particle.ParticleShape;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.Trail;
import me.gamercoder215.starcosmetics.api.cosmetics.trail.TrailType;
import org.bukkit.OfflinePlayer;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.File;
import java.io.IOException;
import java.util.*;

/**
 * Represents a player used by StarCosmetics to manage their configuration.
*/
@SuppressWarnings("unchecked")
public final class StarPlayer {

    static final Map<UUID, Pet> SPAWNED_PETS = new HashMap<>();

    private final OfflinePlayer player;
    private final File folder;

    // Configuration Files
    private final File configF;
    private final FileConfiguration config;

    private final File soundF;
    private final FileConfiguration sounds;

    private final File completionsF;
    private final FileConfiguration completions;

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

        this.completionsF = new File(folder, "completions.yml");
        if (!completionsF.exists()) try { completionsF.createNewFile(); } catch (IOException e) { StarConfig.print(e); }
        this.completions = YamlConfiguration.loadConfiguration(completionsF);
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
     * Utility method to check whether a player is online.
     * @return true if the player is online, false otherwise
     * @see OfflinePlayer#isOnline()
     */
    public boolean isOnline() { return player.isOnline(); }

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
        return completions.getBoolean(c.getNamespace() + "." + c.getKey(), false);
    }

    /**
     * Sets the completion of the specified completion to the specified value.
     * @param c The completion to set.
     * @param b The value to set the completion to.
     */
    public void setCompleted(@NotNull Completion c, boolean b) {
        if (c == null) return;

        completions.set(c.getNamespace() + "." + c.getKey(), b);
        save();
    }

    /**
     * Sends a Notification to this StarPlayer.
     * @param message Message to Send
     */
    public void sendNotification(@Nullable String message) {
        if (message == null) return;
        if (!getSetting(PlayerSetting.NOTIFICATIONS)) return;
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
     * Fetches the setting's value.
     * @param s The setting to check.
     * @param <T> The type of the setting.
     * @return Setting Value, or default value if not found
     */
    @Nullable
    public <T> T getSetting(@NotNull PlayerSetting<T> s) {
        if (s == null) return null;
        return getSetting(s, s.getDefaultValue());
    }

    /**
     * Fetches the setting's value.
     * @param setting The setting to check.
     * @param def The default value to return if the setting is not set.
     * @param <T> The type of the setting.
     * @return true if enabled, false otherwise
     */
    @Nullable
    public <T> T getSetting(@NotNull PlayerSetting<T> setting, T def) {
        if (setting == null) return null;

        return readSetting(setting, def);
    }

    /**
     * Sets the specified setting to the specified value.
     * @param setting The setting to set.
     * @param value The value to set the setting to.
     * @param <T> Setting Type
     * @return value set
     */
    @Nullable
    public <T> T setSetting(@NotNull PlayerSetting<T> setting, @Nullable T value) {
        writeSetting(setting, value);
        save();

        return value;
    }

    private <T> void writeSetting(PlayerSetting<T> setting, T value) {
        String path = "settings." + setting.getId().toLowerCase();

        if (value instanceof Enum<?>) {
            config.set(path + ".value", ((Enum<?>) value).name());
            config.set(path + ".clazz", value.getClass().getName());
            return;
        }

        config.set(path, value);
    }

    private <T> T readSetting(PlayerSetting<T> setting, T def) {
        String path = "settings." + setting.getId().toLowerCase();

        if (config.contains(path + ".clazz")) try {
            Object value = config.get(path + ".value", def);
            Class<?> clazz = Class.forName(config.getString(path + ".clazz"));

            if (clazz.isEnum())
                try {
                    return (T) Enum.valueOf(clazz.asSubclass(Enum.class), value.toString()); // Will not compile without Cast on Java 8
                } catch (IllegalArgumentException e) {
                    return def;
                }

        } catch (ClassNotFoundException e) {
            StarConfig.print(e);
        }

        return (T) config.get(path, def);
    }

    /**
     * Fetches the selected cosmetic.
     * @param clazz The class of the cosmetic to fetch.
     * @return Selected Cosmetic for this Cosmetic Class
     */
    @Nullable
    public CosmeticLocation<?> getSelectedCosmetic(@Nullable Class<? extends Cosmetic> clazz) {
        if (clazz == null) return null;
        if (Cosmetic.class.equals(clazz) || Trail.class.equals(clazz)) return null;

        String path = "cosmetics." + toString(clazz);

        return CosmeticLocation.getByFullKey(config.getString(path, null));
    }

    private static String toString(Class<? extends Cosmetic> clazz) {
        if (Trail.class.isAssignableFrom(clazz)) return "trails";
        if (ParticleShape.class.isAssignableFrom(clazz)) return "particle_shape";

        return clazz.getSimpleName().toLowerCase();
    }

    /**
     * Ticks this StarPlayer, running any necessary tasks (i.e. particle shapes) every tick. Ran asynchronously and is thread safe.
     */
    public void tick() {
        if (!player.isOnline()) return;
        Player p = player.getPlayer();

        CosmeticLocation<Particle> shape = (CosmeticLocation<Particle>) getSelectedCosmetic(ParticleShape.class);
        if (shape != null) shape.getParent().run(p.getLocation().add(0, .25, 0), shape);
    }

    /**
     * Whether this StarPlayer has a selected cosmetic for this class.
     * @param clazz The class of the cosmetic to set.
     * @return true if selected, false otherwise
     */
    public boolean hasSelectedCosmetic(@Nullable Class<? extends Cosmetic> clazz) {
        return getSelectedCosmetic(clazz) != null;
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
    public void setSelectedCosmetic(@NotNull Class<? extends Cosmetic> clazz, @Nullable CosmeticLocation<?> loc) {
        if (clazz == null) return;
        if (Cosmetic.class.equals(clazz)) return;

        String path = "cosmetics." + toString(clazz);

        if (loc == null) config.set(path, null);
        else config.set(path, loc.getFullKey());
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
     * Adds an event selection to this player.
     * @param s Selection to Add
     * @throws IllegalArgumentException if added selections exceed limit or does not match owner
     */
    public void addSelection(@NotNull SoundEventSelection s) throws IllegalArgumentException {
        if (s == null) return;
        if (!s.getPlayer().getUniqueId().equals(player.getUniqueId()))
            throw new IllegalArgumentException("Selection Owner " + s.getPlayer().getName() + " does not match this StarPlayer of " + player.getName());

        List<SoundEventSelection> selections = getSoundSelections();
        selections.add(s);

        if (selections.size() > getSelectionLimit()) throw new IllegalArgumentException("Selection limit exceeded!");

        sounds.set("sounds", selections);
        save();
    }

    /**
     * Adds an iterable of SoundEventSelections to this player.
     * @param it Iterable of SoundEventSelections to add
     * @throws IllegalArgumentException if added selections exceed limit or does not match owner
     */
    public void addSelections(@NotNull Iterable<SoundEventSelection> it) throws IllegalArgumentException {
        if (it == null) return;

        List<SoundEventSelection> selections = getSoundSelections();
        for (SoundEventSelection s : it) {
            if (s == null) continue;
            if (!s.getPlayer().getUniqueId().equals(player.getUniqueId()))
                throw new IllegalArgumentException("Selection Owner " + s.getPlayer().getName() + " does not match this StarPlayer of " + player.getName());
            selections.add(s);
        }

        if (selections.size() > getSelectionLimit()) throw new IllegalArgumentException("Selection limit exceeded!");

        sounds.set("sounds", selections);
        save();
    }

    /**
     * Adds an array of SoundEventSelections to this player.
     * @param selections Array of SoundEventSelections to add
     * @throws IllegalArgumentException if added selections exceed limit or does not match owner
     */
    public void addSelections(@NotNull SoundEventSelection... selections) throws IllegalArgumentException {
        if (selections == null) return;
        addSelections(Arrays.asList(selections));
    }

    /**
     * Removes an event selection from this player.
     * @param selection Selection to Remove
     */
    public void removeSelection(@NotNull SoundEventSelection selection) {
        if (selection == null) return;

        List<SoundEventSelection> selections = getSoundSelections();
        selections.remove(selection);

        sounds.set("sounds", selections);
        save();
    }

    /**
     * Removes an event selection from this player.
     * @param clazz Class of the selection's event to remove
     */
    public void removeSelection(@NotNull Class<? extends Event> clazz) {
        if (clazz == null) return;

        List<SoundEventSelection> selections = getSoundSelections();
        selections.removeIf(s -> s.getEvent().equals(clazz));

        sounds.set("sounds", selections);
        save();
    }

    /**
     * Removes an event selection from this player.
     * @param s Sound to remove
     */
    public void removeSelection(@NotNull Sound s) {
        if (s == null) return;

        List<SoundEventSelection> selections = getSoundSelections();
        selections.removeIf(sel -> sel.getSound().equals(s));

        sounds.set("sounds", selections);
        save();
    }

    /**
     * Removes all event selections from this player.
     */
    public void clearEventSelections() {
        sounds.set("sounds", new ArrayList<>());
        save();
    }

    /**
     * Fetches the limit of sound event selections this player can have.
     * @return {@link SoundEventSelection} Limit
     */
    public int getSelectionLimit() {
        if (!player.isOnline()) return sounds.getInt("limit", 1);
        Player p = player.getPlayer();

        int limit = 1;
        CompletionCriteria criteria = CompletionCriteria.fromSelectionLimit(limit);
        while (criteria.getCriteria().test(p) && limit < 36) {
            limit++;
            criteria = CompletionCriteria.fromSelectionLimit(limit);
        }

        sounds.set("limit", limit);
        save();

        return limit;
    }

    /**
     * Sets the limit of sound event selections this player can have.
     * @param limit Limit to set
     * @throws IllegalArgumentException if limit is less than 0 or bigger than 35
     */
    public void setSelectionLimit(int limit) throws IllegalArgumentException {
        if (limit < 0 || limit > 35) throw new IllegalArgumentException("Limit must be between 0 and 35");

        sounds.set("limit", limit);
        save();
    }

    /**
     * Fetches the Pet this player has active.
     * @return Pet Spawned, or null if not spawned
     */
    @Nullable
    public Pet getSpawnedPet() {
        if (!player.isOnline()) return null;

        return SPAWNED_PETS.get(player.getUniqueId());
    }

    /**
     * <p>Saves all of this StarPlayer's configuration.</p>
     * <p>Methods that edit the configuration automatically save the configuration, so an additional call is not necessary.</p>
     */
    public void save() {
        try {
            config.save(configF);
            sounds.save(soundF);
            completions.save(completionsF);
        } catch (IOException e) {
            StarConfig.print(e);
        }
    }

}
