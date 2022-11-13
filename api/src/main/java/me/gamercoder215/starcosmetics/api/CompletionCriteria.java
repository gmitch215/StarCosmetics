package me.gamercoder215.starcosmetics.api;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

/**
 * Represents Criteria needed to unlock a Cosmetic
 */
public final class CompletionCriteria {

    private final Predicate<Player> criteria;
    private final Predicate<Player> unlockCriteria;
    private final String displayKey;

    private final Object[] displayArguments;

    private CompletionCriteria(Predicate<Player> criteria, Predicate<Player> unlockCriteria, String displayKey, Object... displayArguments) {
        if (criteria == null) throw new IllegalArgumentException("Criteria cannot be null!");
        if (unlockCriteria == null) throw new IllegalArgumentException("Unlock Criteria cannot be null!");
        if (displayKey == null) throw new IllegalArgumentException("Display Key cannot be null!");

        this.criteria = p -> p.hasPermission("starcosmetics.admin.bypasscheck") || criteria.test(p);
        this.displayKey = displayKey;
        this.displayArguments = displayArguments;
        this.unlockCriteria = unlockCriteria;
    }

    private CompletionCriteria(Predicate<Player> criteria, Predicate<Player> unlockCriteria, String displayKey, Object firstArg, Object[] displayArguments) {
        this(criteria, unlockCriteria, displayKey, ImmutableList.builder().add(firstArg).add(displayArguments).build().toArray());
    }

    private CompletionCriteria(Predicate<Player> criteria, String displayKey, Object... displayArguments) {
        this(criteria, criteria, displayKey, displayArguments);
    }

    private static String comma(int i) {
        return String.format("%,.0f", (double) i);
    }

    /**
     * Fetches the criteria needed to unlock a Cosmetic.
     * @return Predicate representing the criteria
     */
    @NotNull
    public Predicate<Player> getCriteria() {
        return criteria;
    }

    /**
     * <p>Fetches the criteria needed to unlock a Cosmetic for the first time.</p>
     * <p>This method is used for things like X number of blocks mined/entities killed in unlock messages.</p>
     * @return Predicate representing the criteria to unlock for the first time
     */
    @NotNull
    public Predicate<Player> getUnlockCriteria() {
        return unlockCriteria;
    }

    /**
     * Fetches the display message of this criteria.
     * @return Display Message
     */
    @NotNull
    public String getDisplayMessage() {
        return String.format(StarConfig.getConfig().get(displayKey), displayArguments);
    }

    // Static Generators

    /**
     * Generates a CompletionCriteria from a {@link Completion}.
     * @param completion Completion to Check for
     * @return CompletionCriteria with the given criteria
     * @throws IllegalArgumentException if criteria is null
     */
    @NotNull
    public static CompletionCriteria fromCompletion(@NotNull Completion completion) throws IllegalArgumentException {
        return new CompletionCriteria(
                p -> new StarPlayer(p).hasCompleted(completion),
                "criteria.completion." + completion.getKey()
        );
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#MINE_BLOCK}.
     * @param amount The amount of blocks to mine
     * @param m The material to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromMined(int amount, Material m) {
        return new CompletionCriteria(
                p -> p.getStatistic(Statistic.MINE_BLOCK, m) >= amount,
                p -> p.getStatistic(Statistic.MINE_BLOCK, m) == amount,
                "criteria.amount.mined", comma(amount), WordUtils.capitalizeFully(m.name().replace("_", " ")));
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#MINE_BLOCK}, using multiple. Materials mined will count EITHER and amounts will not be added together.
     * @param amount The amount of blocks to mine
     * @param materials Collection of Materials to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromMined(int amount, Collection<Material> materials) {
        return new CompletionCriteria(p -> {
            int count = 0;
            for (Material m : materials) count += p.getStatistic(Statistic.MINE_BLOCK, m);
            return count >= amount;
        }, p -> {
            int count = 0;
            for (Material m : materials) count += p.getStatistic(Statistic.MINE_BLOCK, m);
            return count >= amount;
        }, "criteria.amount.mined.list." + materials.size(), comma(amount), materials.stream()
                .map(m -> WordUtils.capitalizeFully(m.name().replace("_", " "))).toArray());
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#MINE_BLOCK}, using multiple. Materials mined will count EITHER and amounts will not be added together.
     * @param amount The amount of blocks to mine
     * @param materials Array of Materials to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromMined(int amount, Material... materials) {
        return fromMined(amount, Arrays.asList(materials));
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#KILL_ENTITY}.
     * @param amount The amount of kills to check for
     * @param type The type of entity to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromKilled(int amount, EntityType type) {
        return new CompletionCriteria(
                p -> p.getStatistic(Statistic.KILL_ENTITY, type) >= amount,
                p -> p.getStatistic(Statistic.KILL_ENTITY, type) == amount,
                "criteria.amount.killed", comma(amount), WordUtils.capitalizeFully(type.name().replace("_", " ")));
    }

    /**
     * <p>Generates a CompletionCriteria with a Bukkit {@link Statistic}.</p>
     * <p><strong>NOTE:</strong> Statistics that require addition input in {@link Player#getStatistic(Statistic)} will not work properly.</p>
     * @param stat The statistic to check for
     * @param amount The amount of kills to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromStatistic(Statistic stat, int amount) {
        return new CompletionCriteria(
                p -> p.getStatistic(stat) >= amount,
                p -> p.getStatistic(stat) == amount,"criteria.amount." + stat.name().toLowerCase(), comma(amount));
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#CRAFT_ITEM}.
     * @param amount The amount of items crafted
     * @param m The material to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromCrafted(int amount, Material m) {
        return new CompletionCriteria(
                p -> p.getStatistic(Statistic.CRAFT_ITEM, m) >= amount,
                p -> p.getStatistic(Statistic.CRAFT_ITEM, m) == amount,
                "criteria.amount.crafted", comma(amount), WordUtils.capitalizeFully(m.name().replace("_", " ")));
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#CRAFT_ITEM}, using multiple. Materials crafted will count EITHER and amounts will not be added together.
     * @param amount The amount of items crafted
     * @param materials Array of Materials to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromCrafted(int amount, Material... materials) {
        return fromCrafted(amount, Arrays.asList(materials));
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#CRAFT_ITEM}, using multiple. Materials crafted will count EITHER and amounts will not be added together.
     * @param amount The amount of items crafted
     * @param materials Collection of Materials to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromCrafted(int amount, Collection<Material> materials) {
        return new CompletionCriteria(p -> {
            int count = 0;
            for (Material m : materials) count += p.getStatistic(Statistic.CRAFT_ITEM, m);
            return count >= amount;
        }, p -> {
            int count = 0;
            for (Material m : materials) count += p.getStatistic(Statistic.CRAFT_ITEM, m);
            return count == amount;
        },"criteria.amount.crafted.list." + materials.size(), comma(amount), materials.stream()
                .map(m -> WordUtils.capitalizeFully(m.name().replace("_", " "))).toArray());
    }

    private static Statistic getPlayStatistic() {
        try {
            return Statistic.valueOf("PLAY_ONE_MINUTE");
        } catch (IllegalArgumentException e) {
            return Statistic.valueOf("PLAY_ONE_TICK");
        }
    }

    /**
     * Generates a CompletionCrtieria with the player's playtime.
     * @param ticks The amount of ticks the player must play for
     * @return CompletionCriteria with the given criteria
     */
    @NotNull
    public static CompletionCriteria fromPlaytime(long ticks) {
        Statistic stat = getPlayStatistic();

        return new CompletionCriteria(p -> {
            long time = p.getStatistic(stat);
            return time >= ticks;
        }, p -> {
            long time = p.getStatistic(stat);
            return time == ticks;
        }, "criteria.amount.playtime", formatTime(ticks));
    }

    private static String formatTime(long ticks) {
        double seconds = (double) ticks / 20D;

        if (seconds < 60) return String.format(StarConfig.getConfig().get("constants.time.seconds"), String.format("%,.0f", seconds));

        double minutes = seconds / 60D;
        if (minutes < 60) return String.format(StarConfig.getConfig().get("constants.time.minutes"), String.format("%,.0f", minutes));

        double hours = minutes / 60D;
        if (hours < 24) return String.format(StarConfig.getConfig().get("constants.time.hours"), String.format("%,.0f", hours));

        double days = hours / 24D;
        return String.format(StarConfig.getConfig().get("constants.time.days"), String.format("%,.0f", days));
    }

}
