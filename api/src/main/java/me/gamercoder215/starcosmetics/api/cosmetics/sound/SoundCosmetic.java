package me.gamercoder215.starcosmetics.api.cosmetics.sound;

import org.bukkit.event.player.PlayerEvent;
import org.jetbrains.annotations.NotNull;

import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;

/**
 * Represents a sound, playable on any {@link PlayerEvent}.
 * @param <T> Player Event Type
 */
public interface SoundCosmetic<T extends PlayerEvent> extends Cosmetic {
    
    /**
     * Fetches the Event Class used in this SoundCosmetic.
     * @return Event Class
     */
    @NotNull
    Class<? extends T> getEventClass();

}
