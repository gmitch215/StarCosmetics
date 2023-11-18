package me.gamercoder215.starcosmetics.api.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import me.gamercoder215.starcosmetics.api.StarConfig;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.*;
import java.util.function.BiConsumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * <p>Utility class for describing instructions creating an Animated Item</p>
 * <strong>NOTE: Starting this hat will NOT automatically and safely remove/replace existing helmets or hats.</strong>
 */
public final class AnimatedItem implements Cloneable {

    private final List<Map.Entry<Long, ItemStack>> frames = new ArrayList<>();
    private final BiConsumer<Player, ItemStack> function;

    private Player player = null;
    private boolean started;

    private AnimatedItem(List<Map.Entry<Long, ItemStack>> frames, BiConsumer<Player, ItemStack> function) {
        this.frames.addAll(frames);
        this.function = function;
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
    public AnimatedItem map(@NotNull Function<ItemStack, ItemStack> mapper) {
        List<Map.Entry<Long, ItemStack>> map = new ArrayList<>();
        for (Map.Entry<Long, ItemStack> frame : this.frames)
            map.add(
                new AbstractMap.SimpleEntry<>(frame.getKey(), mapper.apply(frame.getValue()))
            );

        return new AnimatedItem(map, function);
    }

    /**
     * Fetches the function to run when starting this Animated Hat.
     * @return Function to run when starting this Animated Hat
     */
    @NotNull
    public BiConsumer<Player, ItemStack> getFunction() {
        return function;
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
        List<Map.Entry<BukkitRunnable, Long>> toRun = new ArrayList<>();

        long current = 0;
        for (int i = 0; i < frames.size(); i++) {
            Map.Entry<Long, ItemStack> frame = frames.get(i);
            ItemStack item = frame.getValue();

            current += i == 0 ? 0 : frames.get(i - 1).getKey();

            toRun.add(new AbstractMap.SimpleEntry<>(new BukkitRunnable() {
                @Override
                public void run() {
                    if (player != null)
                        function.accept(player, item);
                }
            }, current));
        }

        Map.Entry<BukkitRunnable, Long> last = toRun.get(toRun.size() - 1);
        toRun.set(toRun.size() - 1, new AbstractMap.SimpleEntry<>(new BukkitRunnable() {
            @Override
            public void run() {
                last.getKey().run();

                new BukkitRunnable() {
                    @Override
                    public void run() {
                        stop();
                    }
                }.runTaskLater(StarConfig.getPlugin(), 10);
            }
        }, last.getValue()));

        toRun.forEach(entry -> entry.getKey().runTaskLater(StarConfig.getPlugin(), entry.getValue()));
    }

    /**
     * Clones this Animated Hat Data.
     * @return Cloned Animated Hat Data
     */
    @NotNull
    public AnimatedItem clone() {
        try {
            return (AnimatedItem) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * Constructs a new Animated Hat Data with the given frames.
     * @param function Function to run when starting this Animated Hat
     * @param frames Frames to use, a list of map entries describing duration (key) and hat item to use (value)
     * @return Constructed AnimatedHatData
     * @throws IllegalArgumentException if frames is null or less than 2 frames
     */
    public static AnimatedItem of(@NotNull BiConsumer<Player, ItemStack> function, @NotNull List<Map.Entry<Long, ItemStack>> frames) throws IllegalArgumentException {
        if (frames == null) throw new IllegalArgumentException("Cannot build Animated Hat Data with null frames.");
        if (frames.size() < 2) throw new IllegalArgumentException("Cannot build Animated Hat Data with 1 or less frames.");
        
        return new AnimatedItem(frames, function);
    }

    /**
     * Constructs a new Builder for Animated Hat Data.
     * @param function Function to run when starting this Animated Hat
     * @return AnimatedHatData Builder
     */
    @NotNull
    public static Builder builder(@NotNull BiConsumer<Player, ItemStack> function) {
        return new Builder(function);
    }

    /**
     * Represents a Builder for Animated Hat Data.
     */
    public static final class Builder {

        private final List<Map.Entry<Long, ItemStack>> frames = new ArrayList<>();
        private BiConsumer<Player, ItemStack> function;

        Builder(@NotNull BiConsumer<Player, ItemStack> function) {
            this.function = function;
        }

        /**
         * Sets the function to run when starting this Animated Hat.
         * @param function function to run
         * @return this class, for chaining
         */
        public Builder function(@NotNull BiConsumer<Player, ItemStack> function) {
            this.function = function;
            return this;
        }

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
        public AnimatedItem build() throws IllegalStateException{
            if (frames.size() < 2) throw new IllegalStateException("Cannot build Animated Hat Data with 1 or less frames.");

            return new AnimatedItem(frames, function);
        }

    }
    
}
