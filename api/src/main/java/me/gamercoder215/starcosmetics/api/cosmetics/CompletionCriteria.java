package me.gamercoder215.starcosmetics.api.cosmetics;

import com.google.common.collect.ImmutableList;
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
 * @since 1.0.0
 */
public final class CompletionCriteria {

    private final Predicate<Player> criteria;
    private final Predicate<Player> unlockCriteria;
    private final String displayKey;

    private final Object[] displayArguments;

    /**
     * Creates a new CompletionCriteria.
     * @param criteria The criteria to check for
     * @param unlockCriteria The criteria to check when unlocking
     * @param displayKey The display key in a language file for the criteria
     * @param displayArguments The arguments to use when displaying the criteria
     * @since 1.0.0
     */
    public CompletionCriteria(Predicate<Player> criteria, Predicate<Player> unlockCriteria, String displayKey, Object... displayArguments) {
        this.criteria = p -> p.hasPermission("starcosmetics.admin.bypasscheck") || criteria.test(p);
        this.displayKey = displayKey;
        this.displayArguments = displayArguments;
        this.unlockCriteria = unlockCriteria;
    }

    /**
     * Creates a new CompletionCriteria with no display and unlock criteria.
     * @param criteria The criteria to check for
     */
    public CompletionCriteria(Predicate<Player> criteria) {
        this(criteria, criteria);
    }

    /**
     * Creates a new CompletionCriteria with no display.
     * @param criteria The criteria to check for
     * @param unlockCriteria The criteria to check when unlocking
     * @since 1.0.0
     */
    public CompletionCriteria(Predicate<Player> criteria, Predicate<Player> unlockCriteria) {
        this(criteria, unlockCriteria, null);
    }

    /**
     * Creates a new CompletionCriteria with no display and unlock criteria.
     * @param criteria The criteria to check for
     * @param unlockCriteria The criteria to check when unlocking
     * @param displayKey The display key in a language file for the criteria
     * @param firstArg The first argument to use when displaying the criteria
     * @param displayArguments The arguments to use when displaying the criteria
     * @since 1.0.0
     */
    public CompletionCriteria(Predicate<Player> criteria, Predicate<Player> unlockCriteria, String displayKey, Object firstArg, Object[] displayArguments) {
        this(criteria, unlockCriteria, displayKey, ImmutableList.builder().add(firstArg).add(displayArguments).build().toArray());
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
     * Fetches the display key for the completion message.
     * @return Display key for Completion
     */
    @NotNull
    public String getDisplayKey() {
        return displayKey;
    }

    /**
     * Fetches the arguments to use when displaying the completion message.
     * @return Arguments to use when displaying the completion message
     */
    public Object[] getDisplayArguments() {
        return displayArguments;
    }

    // Static Generators

    /**
     * Generates a CompletionCriteria with {@link CompletionCriteria#CompletionCriteria(Predicate)}.
     * @param criteria The criteria to check for
     * @return CompletionCriteria with the given criteria
     * @since 1.0.0
     */
    public static CompletionCriteria of(Predicate<Player> criteria) {
        return new CompletionCriteria(criteria);
    }

    /**
     * Generates a CompletionCriteria with {@link CompletionCriteria#CompletionCriteria(Predicate, Predicate, String, Object, Object[])}, with unlock criteria and criteria the same.
     * @param criteria The criteria to check for
     * @param displayKey The display key in a language file for the criteria
     * @param args The arguments to use when displaying the criteria
     * @return CompletionCriteria with the given criteria
     * @since 1.0.0
     */
    public static CompletionCriteria of(Predicate<Player> criteria, String displayKey, Object... args) {
        return new CompletionCriteria(criteria, criteria, displayKey, args);
    }

    /**
     * Generates a CompletionCriteria with {@link CompletionCriteria#CompletionCriteria(Predicate, Predicate, String, Object, Object[])}.
     * @param criteria The criteria to check for
     * @param unlockCriteria The criteria to check when unlocking
     * @param displayKey The display key in a language file for the criteria
     * @param args The arguments to use when displaying the criteria
     * @return CompletionCriteria with the given criteria
     * @since 1.0.0
     */
    public static CompletionCriteria of(Predicate<Player> criteria, Predicate<Player> unlockCriteria, String displayKey, Object... args) {
        return new CompletionCriteria(criteria, unlockCriteria, displayKey, args);
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#MINE_BLOCK}.
     * @param amount The amount of blocks to mine
     * @param m The material to check for
     * @return CompletionCriteria with the given criteria
     * @since 1.0.0
     */
    public static CompletionCriteria fromMined(int amount, Material m) {
        return new CompletionCriteria(
                p -> p.getStatistic(Statistic.MINE_BLOCK, m) >= amount,
                p -> p.getStatistic(Statistic.MINE_BLOCK, m) == amount,
                "criteria.amount.mined", amount, WordUtils.capitalizeFully(m.name().replace("_", " ")));
    }

    /**
     * Generates a CompletionCriteria with {@link Statistic#MINE_BLOCK}, using multiple. Materials mined will count EITHER and amounts will not be added together.
     * @param amount The amount of blocks to mine
     * @param materials Collection of Materials to check for
     * @return CompletionCriteria with the given criteria
     * @since 1.0.0
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
        }, "criteria.amount.mined.list." + materials.size(), amount, materials.toArray());
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
     * @since 1.0.0
     */
    public static CompletionCriteria fromKilled(int amount, EntityType type) {
        return new CompletionCriteria(
                p -> p.getStatistic(Statistic.KILL_ENTITY, type) >= amount,
                p -> p.getStatistic(Statistic.KILL_ENTITY, type) == amount,
                "criteria.amount.killed", amount, WordUtils.capitalizeFully(type.name().replace("_", " ")));
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
                p -> p.getStatistic(stat) == amount,"criteria.amount." + stat.name().toLowerCase(), amount);
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
                "criteria.amount.crafted", amount, WordUtils.capitalizeFully(m.name().replace("_", " ")));
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
        },"criteria.amount.crafted.list." + materials.size(), amount, materials.toArray());
    }

}
