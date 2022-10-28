package me.gamercoder215.starcosmetics.api.player.cosmetics;

import me.gamercoder215.starcosmetics.api.Completion;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
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

/**
 * Represents a custom completion a player has to achieve to unlock Sound Event Cosmetics.
 */
public enum SoundCompletion implements Completion {

    /**
     * Completion used for sounds when breaking a block.
     */
    BLOCK_BREAK_COMPLETION(BlockBreakEvent.class, Rarity.RARE, CompletionCriteria.fromMined(1000, Material.OBSIDIAN)),
    /**
     * Completion used for sounds when placing a block.
     */
    BLOCK_PLACE_COMPLETION(BlockPlaceEvent.class, Rarity.RARE),
    /**
     * Completion used for sounds when extracting an item from a furnace.
     */
    FURNACE_EXTRACT_COMPLETION(FurnaceExtractEvent.class, Rarity.UNCOMMON),
    /**
     * Completion used for sounds when opening an inventory.
     */
    INVENTORY_OPEN_COMPLETION(InventoryOpenEvent.class, Rarity.EPIC),
    /**
     * Completion used for sounds when completing a 1.12+ advancement.
     */
    ADVANCEMENT_COMPLETION("player.PlayerAdvancementDoneEvent", Rarity.LEGENDARY),
    /**
     * Completion used for sounds when entering a bed.
     */
    BED_ENTER_COMPLETION(PlayerBedEnterEvent.class, Rarity.UNCOMMON, CompletionCriteria.fromPlaytime(576000)), // 8 Hours
    /**
     * Completion used for sounds when going to another world (e.g. going through a nether portal)
     */
    CHANGE_WORLD_COMPLETION(PlayerChangedWorldEvent.class, Rarity.RARE, CompletionCriteria.fromKilled(500, EntityType.BLAZE)),
    /**
     * Completion used for sounds when dying.
     */
    DEATH_COMPLETION(PlayerDeathEvent.class, Rarity.UNCOMMON, CompletionCriteria.fromStatistic(Statistic.DEATHS, 75)),
    /**
     * Completion used for sounds when editing a book.
     */
    EDIT_BOOK_COMPLETION(PlayerEditBookEvent.class, Rarity.COMMON),
    /**
     * Completion used for sounds when throwing an egg.
     */
    EGG_THROW_COMPLETION(PlayerEditBookEvent.class, Rarity.MYTHICAL, CompletionCriteria.fromKilled(10000, EntityType.CHICKEN)),
    /**
     * Completion used for sounds when your experience changes.
     */
    EXPERIENCE_CHANGE_COMPLETION(PlayerExpChangeEvent.class, Rarity.UNCOMMON),
    /**
     * Completion used for sounds when fishing.
     */
    FISH_COMPLETION(PlayerFishEvent.class, Rarity.UNCOMMON, CompletionCriteria.fromStatistic(Statistic.FISH_CAUGHT, 50)),
    /**
     * Completion used for sounds when joining the server.
     */
    JOIN_COMPLETION(PlayerJoinEvent.class, Rarity.EPIC, CompletionCriteria.fromPlaytime(17568000)), // 4 Days
    /**
     * Completion used for sounds when respawning.
     */
    RESPAWN_COMPLETION(PlayerRespawnEvent.class, Rarity.RARE, CompletionCriteria.fromStatistic(Statistic.DEATHS, 175)),
    /**
     * Completion used for sounds when using a riptiding trident.
     */
    RIPTIDE_COMPLETION("player.PlayerRiptideEvent", Rarity.ULTRA, CompletionCriteria.fromKilled(10000, EntityType.GUARDIAN)),
    /**
     * Completion used for sounds when shearing a sheep.
     */
    DYE_SHEEP_COMPLETION(SheepDyeWoolEvent.class, Rarity.UNCOMMON, CompletionCriteria.fromKilled(250, EntityType.SHEEP)),
    /**
     * Completion used for sounds when changing a sign.
     */
    SIGN_CHANGE_COMPLETION(SignChangeEvent.class, Rarity.COMMON),

    ;

    private final Class<? extends Event> event;

    private final Rarity rarity;

    private final CompletionCriteria criteria;

    SoundCompletion(Class<? extends Event> event, Rarity rarity, CompletionCriteria criteria) {
        this.event = event;
        this.rarity = rarity;
        this.criteria = criteria;
    }

    SoundCompletion(Class<? extends Event> event, Rarity rarity) {
        this(event, rarity, null);
    }

    SoundCompletion(String event, Rarity rarity, CompletionCriteria criteria) {
        Class<? extends Event> tEvent;
        try {
            tEvent = Class.forName("org.bukkit.event." + event).asSubclass(Event.class);
        } catch (ClassNotFoundException e) { tEvent = null; }
        this.event = tEvent;
        this.rarity = rarity;
        this.criteria = criteria;
    }

    SoundCompletion(String event, Rarity rarity) {
        this(event, rarity, null);
    }

    /**
     * Fetches the event that this SoundCompletion is hooked to in {@link SoundEventSelection#AVAILABLE_EVENTS}.
     * @return Class of the event
     */
    @Nullable
    public Class<? extends Event> getEvent() {
        return event;
    }

    @Override
    @NotNull
    public CompletionCriteria getCriteria() {
        if (criteria != null) return criteria;
        return Completion.super.getCriteria();
    }

    @Override
    public @NotNull String getKey() {
        return name().toLowerCase();
    }

    @Override
    public @NotNull Rarity getRarity() {
        return rarity;
    }

    /**
     * Fetches a SoundCompletion by its Event.
     * @param event Event to fetch by
     * @return SoundCompletion found, or null if not found
     */
    @Nullable
    public static SoundCompletion getByEvent(@Nullable Class<? extends Event> event) {
        for (SoundCompletion completion : values()) if (completion.getEvent().equals(event)) return completion;

        return null;
    }
}
