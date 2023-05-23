package me.gamercoder215.starcosmetics.api.cosmetics.hat;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;

import me.gamercoder215.starcosmetics.api.StarConfig;

/**
 * <p>Utility class for describing instructions creating an Animated Hat</p>
 * <strong>NOTE: Starting this hat will NOT automatically and safely remove/replace existing helmets or hats.</strong>
 */
public final class AnimatedHatData implements Cloneable {

    private final List<Map.Entry<Long, ItemStack>> frames = new ArrayList<>();

    private Player player = null;
    private boolean started;

    private AnimatedHatData(List<Map.Entry<Long, ItemStack>> frames) {
        this.frames.addAll(frames);
        this.started = false;
    }

    /**
     * Fetches whether this Animated Hat has been started.
     * @return true if started, false otherwise
     */
    public boolean isStarted() {
        return started;
    }

    /**
     * Fetches the player that this Animated Hat is started for.
     * @return Player using Animated Hat, or null if not started
     */
    @Nullable
    public Player getPlayer() {
        return player;
    }

    /**
     * Fetches an immutable copy of the frames for this Animated Hat.
     * @return Animated Hat Frames
     */
    @NotNull
    public List<Map.Entry<Long, ItemStack>> getFrames() {
        return ImmutableList.copyOf(frames);
    }

    /**
     * Utility method for shortcutting to {@link #getFrames()} stream.
     * @return Animated Hat Frames Stream
     */
    @NotNull
    public Stream<Map.Entry<Long, ItemStack>> stream() {
        return frames.stream();
    }

    /**
     * Maps the itemstacks in the frames of this Animated Hat Data to a new Animated Hat Data.
     * @param mapper function to map itemstacks in frames
     * @return new Animated Hat Data with mapped itemstack hats 
     */
    @NotNull
    public AnimatedHatData map(@NotNull Function<ItemStack, ItemStack> mapper) {
        List<Map.Entry<Long, ItemStack>> map = new ArrayList<>();
        for (Map.Entry<Long, ItemStack> frame : this.frames)
            map.add(
                new AbstractMap.SimpleEntry<>(frame.getKey(), mapper.apply(frame.getValue()))
            );

        return new AnimatedHatData(map);
    }

    /**
     * Attempts to start and finish this Animated Hat for the given player. This action performs once cycle of the animated hat, then marks it as stopped.
     * @param p the player to start the Animated Hat for
     */
    public void tryStart(@NotNull Player p) {
        if (isStarted()) return;

        this.player = p;
        this.started = true;
        performCycle();
    }

    /**
     * Stops this Animated Hat, if it is started, making {@link #getPlayer()} return null and {@link #isStarted()} return false.
     */
    public void stop() {
        if (!isStarted()) return;

        this.player = null;
        this.started = false;
    }

    private void performCycle() {
        int i = 0;
        for (Map.Entry<Long, ItemStack> frame : frames) {
            ItemStack item = frame.getValue();

            ItemStack item0 = item.clone();
            ItemMeta meta0 = item0.getItemMeta();
            meta0.setDisplayName(" ");
            item0.setItemMeta(meta0);

            if (i == 0) player.getInventory().setHelmet(item0);
            else {
                long duration = frames.get(i - 1).getKey();
                new BukkitRunnable() {
                    @Override
                    public void run() {
                        if (!isStarted()) {
                            cancel();
                            return;
                        }

                        player.getInventory().setHelmet(item0);
                    }
                }.runTaskLater(StarConfig.getPlugin(), duration);
            }

            i++;
        }

        player.getEquipment().setHelmet(null);
    }

    /**
     * Constructs a new Animated Hat Data with the given frames.
     * @param frames Frames to use, a list of map entries describing duration (key) and hat item to use (value)
     * @return Constructed AnimatedHatData
     * @throws IllegalArgumentException if frames is null or less than 2 frames
     */
    public static AnimatedHatData of(@NotNull List<Map.Entry<Long, ItemStack>> frames) throws IllegalArgumentException {
        if (frames == null) throw new IllegalArgumentException("Cannot build Animated Hat Data with null frames.");
        if (frames.size() < 2) throw new IllegalArgumentException("Cannot build Animated Hat Data with 1 or less frames.");
        
        return new AnimatedHatData(frames);
    }

    /**
     * Constructs a new Builder for Animated Hat Data.
     * @return AnimatedHatData Builder
     */
    @NotNull
    public static Builder builder() {
        return new Builder();
    }

    /**
     * Represents a Builder for Animated Hat Data.
     */
    public static final class Builder {

        private final List<Map.Entry<Long, ItemStack>> frames = new ArrayList<>();

        Builder() {}

        /**
         * Clears and sets the frames for this Animated Hat Data.
         * @param frames the frames to set
         * @return this class, for chaining
         */
        @NotNull
        public Builder setFrames(@NotNull Collection<Map.Entry<Long, ItemStack>> frames) {
            this.frames.clear();
            this.frames.addAll(frames);
            return this;
        }

        /**
         * Adds a frame to this Animated Hat Data.
         * @param frames list of frames to add
         * @return this class, for chaining
         */
        @NotNull
        public Builder addFrames(@NotNull Iterable<? extends Map.Entry<Long, ItemStack>> frames) {
            if (frames == null) return this;
            this.frames.addAll(ImmutableSet.copyOf(frames));
            return this;
        }

        /**
         * Adds a frame to this Animated Hat Data.
         * @param duration duration of frame, in ticks
         * @param item itemstack hat of frame
         * @return this class, for chaining
         */
        @NotNull
        public Builder addFrame(long duration, ItemStack item) {
            Map.Entry<Long, ItemStack> frame = new AbstractMap.SimpleEntry<>(duration, item);
            this.frames.add(frame);
            return this;
        }

        /**
         * Adds a frame to this Animated Hat Data.
         * @param index index to add frame at
         * @param duration duration of frame, in ticks
         * @param item itemstack hat of frame
         * @return this class, for chaining
         */
        @NotNull
        public Builder addFrame(int index, long duration, ItemStack item) {
            Map.Entry<Long, ItemStack> frame = new AbstractMap.SimpleEntry<>(duration, item);
            this.frames.add(index, frame);
            return this;
        }

        /**
         * Sets a frame in this Animated Hat Data.
         * @param duration duration of frame, in ticks
         * @param item itemstack hat of frame
         * @param index index to set frame at
         * @return this class, for chaining
         */
        @NotNull
        public Builder setFrame(int index, long duration, ItemStack item) {
            Map.Entry<Long, ItemStack> frame = new AbstractMap.SimpleEntry<>(duration, item);
            this.frames.set(index, frame);
            return this;
        }

        /**
         * Builds a new Animated Hat Data with the given frames.
         * @return Built AnimatedHatData
         * @throws IllegalStateException if there are less than 2 frames
         */
        @NotNull
        public AnimatedHatData build() throws IllegalStateException{
            if (frames.size() < 2) throw new IllegalStateException("Cannot build Animated Hat Data with 1 or less frames.");

            return new AnimatedHatData(frames);
        }

    }
    
}
