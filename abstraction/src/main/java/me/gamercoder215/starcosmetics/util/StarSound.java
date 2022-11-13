package me.gamercoder215.starcosmetics.util;

import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.entity.Entity;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public enum StarSound {

    ENTITY_ARROW_HIT_PLAYER("SUCCESSFUL_HIT"),
    BLOCK_NOTE_BLOCK_PLING("NOTE_PLING", "BLOCK_NOTE_PLING"),
    BLOCK_ENDER_CHEST_OPEN,
    BLOCK_ANVIL_USE("ANVIL_USE"),
    ENTITY_ENDERMAN_TELEPORT("ENDERMAN_TELEPORT", "ENTITY_ENDERMEN_TELEPORT"),
    BLOCK_CHEST_OPEN("CHEST_OPEN", "ENTITY_CHEST_OPEN"),
    ITEM_BOOK_PAGE_TURN,
    BLOCK_SLIME_PLACE("block_slime_block_place"),
    ITEM_TRIDENT_RIPTIDE_1,
    ;

    private final List<String> sounds = new ArrayList<>();

    StarSound(String... sounds) {
        // Ensure names are uppercase
        this.sounds.add(name().toUpperCase());
        this.sounds.addAll(Arrays.stream(sounds).map(String::toUpperCase).collect(Collectors.toList()));
    }

    public Sound find() {
        for (String sound : sounds) try {
            return Sound.valueOf(sound);
        } catch (IllegalArgumentException ignored) {}

        return null;
    }

    public void play(Location l, float volume, float pitch) {
        Sound s = find();
        if (s == null) return;

        l.getWorld().playSound(l, s, volume, pitch);
    }

    public void play(@NotNull Location l) { play(l, 1F, 1F); }

    public void play(@NotNull Entity en, float volume, float pitch) { play(en.getLocation(), volume, pitch); }

    public void play(@NotNull Entity en) { play(en.getLocation()); }

    public void playSuccess(@NotNull Entity en) {
        play(en, 3F, 2F);
    }

    public void playFailure(@NotNull Entity en) {
        play(en, 3F, 0F);
    }

}
