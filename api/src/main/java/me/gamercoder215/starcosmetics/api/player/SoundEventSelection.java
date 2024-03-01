package me.gamercoder215.starcosmetics.api.player;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerDamageEntityEvent;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerDamagePlayerEvent;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerTakeDamageByEntityEvent;
import me.gamercoder215.starcosmetics.api.events.cosmetics.PlayerTakeDamageByPlayerEvent;
import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.entity.Entity;
import org.bukkit.event.Event;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.SignChangeEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.SheepDyeWoolEvent;
import org.bukkit.event.inventory.FurnaceExtractEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents an entry in a player's configuration for a sound to be played on any events in {@link #AVAILABLE_EVENTS}.
 */
public final class SoundEventSelection implements ConfigurationSerializable {

    private final OfflinePlayer player;
    private final Sound sound;
    private final Class<? extends Event> event;
    private final float volume;
    private final float pitch;

    private final Date timestamp;

    private SoundEventSelection(Class<? extends Event> event, Sound sound, float volume, float pitch, OfflinePlayer player, Date timestamp) {
        this.event = event;
        this.player = player;
        this.volume = volume;
        this.pitch = pitch;
        this.sound = sound;
        this.timestamp = timestamp;
    }

    /**
     * Creates a new instance.
     * @param event The event to play the sound on.
     * @param sound The sound to play.
     * @param volume The volume of the sound.
     * @param pitch The pitch of the sound.
     * @param player The Player that owns this SoundEventSelection.
     * @param date The Timestamp that this sound was created at.
     * @return Constructed SoundEventSelection
     */
    public static SoundEventSelection of(@NotNull Class<? extends Event> event, @NotNull Sound sound, float volume, float pitch, @NotNull OfflinePlayer player, @NotNull Date date) {
        if (event == null) throw new IllegalArgumentException("Event cannot be null");
        if (sound == null) throw new IllegalArgumentException("Sound cannot be null");
        if (player == null) throw new IllegalArgumentException("Player cannot be null");
        if (date == null) throw new IllegalArgumentException("Date cannot be null");

        if (volume < 0) throw new IllegalArgumentException("Volume cannot be less than 0");
        if (pitch < 0 || pitch > 2) throw new IllegalArgumentException("Pitch must be between 0 and 2: " + pitch);

        return new SoundEventSelection(event, sound, volume, pitch, player, date);
    }

    /**
     * <p>A List of all Available Events to be used in a {@link SoundEventSelection}.</p>
     * <strong>This list is mutable; Integration for custom events is supported.</strong>
     */
    public static final List<Class<? extends Event>> AVAILABLE_EVENTS;

    /**
     * Checks whether this event is valid to listen to.
     * @param event The event to check.
     * @return true if valid, false otherwise
     */
    public static boolean isValid(@NotNull Class<? extends Event> event) {
        if (event == null) return false;

        for (Class<? extends Event> e : AVAILABLE_EVENTS) if (e.isAssignableFrom(event)) return true;

        return false;
    }

    static {
        AVAILABLE_EVENTS = ImmutableList.copyOf(ImmutableList.<Class<? extends Event>>builder()
                .add(BlockBreakEvent.class)
                .add(BlockPlaceEvent.class)
                .add(FurnaceExtractEvent.class)
                .add(InventoryOpenEvent.class)
                .add(optional("player.PlayerAdvancementDoneEvent"))
                .add(PlayerBedEnterEvent.class)
                .add(PlayerChangedWorldEvent.class)
                .add(PlayerDeathEvent.class)
                .add(PlayerEditBookEvent.class)
                .add(PlayerEggThrowEvent.class)
                .add(PlayerExpChangeEvent.class)
                .add(PlayerFishEvent.class)
                .add(PlayerJoinEvent.class)
                .add(PlayerItemBreakEvent.class)
                .add(PlayerItemConsumeEvent.class)
                .add(PlayerRespawnEvent.class)
                .add(optional("player.PlayerRiptideEvent"))
                .add(optional("player.PlayerItemMendEvent"))
                .add(PlayerGameModeChangeEvent.class)
                .add(SheepDyeWoolEvent.class)
                .add(SignChangeEvent.class)
                .add(PlayerInteractEntityEvent.class)
                .add(PlayerDamageEntityEvent.class)
                .add(PlayerDamagePlayerEvent.class)
                .add(PlayerTakeDamageByEntityEvent.class)
                .add(PlayerTakeDamageByPlayerEvent.class)
                .add(optional("raid.RaidTriggerEvent"))
                .build()
                .stream().filter(Objects::nonNull).collect(Collectors.toList()));
    }

    private static Class<? extends Event> optional(String name) {
        try {
            return Class.forName("org.bukkit.event." + name)
                    .asSubclass(Event.class);
        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /**
     * Fetches the player that this SoundEventSelection belongs to.
     * @return OfflinePlayer
     */
    @NotNull
    public OfflinePlayer getPlayer() {
        return player;
    }

    /**
     * Fetches the Sound that this SoundEventSelection will play.
     * @return Sound
     */
    @NotNull
    public Sound getSound() {
        return sound;
    }

    /**
     * Fetches the Date this SoundEventSelection was created.
     * @return Timestamp of Creation
     */
    @NotNull
    public Date getTimestamp() {
        return timestamp;
    }

    /**
     * Fetches the volume of the sound.
     * @return Sound Volume
     */
    public float getVolume() {
        return volume;
    }

    /**
     * Plays this sound with its volume and pitch.
     * @param loc The location to play the sound at.
     */
    public void play(@NotNull Location loc) {
        if (loc == null) return;
        loc.getWorld().playSound(loc, sound, volume, pitch);
    }

    /**
     * Plays this sound with its volume and pitch.
     * @param en The entity to play the sound at.
     */
    public void play(@NotNull Entity en) {
        play(en.getLocation());
    }

    /**
     * Fetches the pitch of the sound.
     * @return Sound Pitch
     */
    public float getPitch() {
        return pitch;
    }

    /**
     * Fetches the Event that this SoundEventSelection is listening for.
     * @return Class of the Event
     */
    @NotNull
    public Class<? extends Event> getEvent() {
        return event;
    }

    /**
     * Clones this SoundEventSelection with a new owner.
     * @param player The new owner of this SoundEventSelection.
     * @return Cloned SoundEventSelection
     */
    @NotNull
    public SoundEventSelection cloneTo(@NotNull OfflinePlayer player) {
        return of(event, sound, volume, pitch, player, timestamp);
    }

    /**
     * Clones this SoundEventSelection with a new Sound, pitch, and volume.
     * @param s The new Sound to play.
     * @param volume The new volume of the sound.
     * @param pitch The new pitch of the sound.
     * @return Cloned SoundEventSelection
     */
    @NotNull
    public SoundEventSelection cloneTo(@NotNull Sound s, float volume, float pitch) {
        return of(event, s, volume, pitch, player, timestamp);
    }

    /**
     * Clones this SoundEventSelection with a new Sound.
     * @param s The new Sound to play.
     * @return Cloned SoundEventSelection
     */
    @NotNull
    public SoundEventSelection cloneTo(@NotNull Sound s) {
        return cloneTo(s, volume, pitch);
    }

    /**
     * Clones this SoundEventSelection with a new Event.
     * @param e The new Event to listen for.
     * @return Cloned SoundEventSelection
     */
    @NotNull
    public SoundEventSelection cloneTo(@NotNull Class<? extends Event> e) {
        return of(e, sound, volume, pitch, player, timestamp);
    }

    @Override
    public String toString() {
        return "SoundEventSelection{" +
                "player=" + player.getName() +
                ", sound=" + sound +
                ", event=" + event.getName() +
                ", volume=" + volume +
                ", pitch=" + pitch +
                ", timestamp=" + timestamp +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SoundEventSelection that = (SoundEventSelection) o;
        return player.getUniqueId().equals(that.player.getUniqueId()) && sound == that.sound && event.equals(that.event)
                && volume == that.volume && pitch == that.pitch;
    }

    @Override
    public int hashCode() {
        return Objects.hash(player.getUniqueId(), sound, volume, pitch, event);
    }

    /**
     * Whether this SoundEventSelection equals another SoundEventSelection, ignoring the player.
     * @param other The other SoundEventSelection
     * @return Whether the two SoundEventSelections are equal without the player
     */
    public boolean equalsIgnorePlayer(@NotNull SoundEventSelection other) {
        return sound == other.sound && event.equals(other.event) && volume == other.volume && pitch == other.pitch;
    }

    /**
     * Deserializes a Map into a SoundEventSelection.
     * @param serial Serialization from {@link #serialize()}
     * @return Deserialized SoundEventSelection
     */
    public static @Nullable SoundEventSelection deserialize(@NotNull Map<String, Object> serial) {
        if (serial == null) return null;
        try {
            return new SoundEventSelection(
                    Class.forName(serial.get("event").toString()).asSubclass(Event.class),
                    Sound.valueOf(serial.get("sound").toString()),
                    Float.parseFloat(serial.get("volume").toString()),
                    Float.parseFloat(serial.get("pitch").toString()),
                    (OfflinePlayer) serial.get("player"),
                    new Date((long) serial.get("timestamp"))
            );
        } catch (ClassNotFoundException e) {
            StarConfig.print(e);
        }

        return null;
    }

    @Override
    public Map<String, Object> serialize() {
        return ImmutableMap.<String, Object>builder()
                .put("event", event.getName())
                .put("sound", sound.name())
                .put("player", player)
                .put("timestamp", timestamp.getTime())
                .put("volume", volume)
                .put("pitch", pitch)
                .build();
    }

    /**
     * Fetches the SoundEventSelection Builder.
     * @return SoundEventSelection Builder
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Builder class for {@link SoundEventSelection}
     */
    public static final class Builder {

        private OfflinePlayer player;
        private Sound sound;
        private float volume;
        private float pitch;
        private Class<? extends Event> event;

        private Builder() {}

        /**
         * Sets the player that this SoundEventSelection will belong to.
         * @param player OfflinePlayer
         * @return this class, for chaining
         * @throws IllegalArgumentException if player is null
         */
        @NotNull
        public Builder player(@NotNull OfflinePlayer player) throws IllegalArgumentException {
            if (player == null) throw new IllegalArgumentException("Player cannot be null");
            this.player = player;
            return this;
        }

        /**
         * Sets the Sound that this SoundEventSelection will play.
         * @param sound Sound
         * @param volume Volume of Sound
         * @param pitch Pitch of Sound
         * @return this class, for chaining
         * @throws IllegalArgumentException if sound is null
         */
        @NotNull
        public Builder sound(@NotNull Sound sound, float volume, float pitch) throws IllegalArgumentException {
            if (sound == null) throw new IllegalArgumentException("Sound cannot be null");
            this.sound = sound;
            this.volume = volume;
            this.pitch = pitch;
            return this;
        }

        /**
         * Sets the Event that this SoundEventSelection will listen for.
         * @param event Class of the Event
         * @return this class, for chaining
         * @throws IllegalArgumentException if event is null
         */
        @NotNull
        public Builder event(@NotNull Class<? extends Event> event) throws IllegalArgumentException {
            if (event == null) throw new IllegalArgumentException("Event cannot be null");
            this.event = event;
            return this;
        }

        /**
         * Builds the SoundEventSelection.
         * @return SoundEventSelection
         * @throws IllegalStateException if any of the required fields are null
         */
        @NotNull
        public SoundEventSelection build() throws IllegalStateException {
            if (player == null) throw new IllegalStateException("Player cannot be null");
            if (sound == null) throw new IllegalStateException("Sound cannot be null");
            if (event == null) throw new IllegalStateException("Event cannot be null");
            if (volume < 0) throw new IllegalStateException("Volume cannot be negative");
            if (pitch < 0 || pitch > 2) throw new IllegalStateException("Pitch must be between 0 and 2: " + pitch);

            return new SoundEventSelection(event, sound, volume, pitch, player, new Date());
        }


    }
}
