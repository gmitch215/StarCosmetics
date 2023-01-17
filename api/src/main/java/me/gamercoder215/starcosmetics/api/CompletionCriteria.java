package me.gamercoder215.starcosmetics.api;

import com.google.common.collect.ImmutableList;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * Represents Criteria needed to unlock a Cosmetic
 */
public final class CompletionCriteria {

    /**
     * Represents a CompletionCriteria that requires nothing.
     */
    public static final CompletionCriteria NONE = new CompletionCriteria(p -> true, p -> 100, "");

    private final Predicate<Player> criteria;
    private final Function<Player, Number> progressFunc;
    private final String displayKey;

    private final Object[] displayArguments;

    CompletionCriteria(Predicate<Player> criteria, Function<Player, Number> progress, String displayKey, Object... displayArguments) {
        if (criteria == null) throw new IllegalArgumentException("Criteria cannot be null!");
        if (displayKey == null) throw new IllegalArgumentException("Display Key cannot be null!");

        this.criteria = p -> p.hasPermission("starcosmetics.admin.bypasscheck") || criteria.test(p);
        this.displayKey = displayKey;
        this.displayArguments = displayArguments;
        this.progressFunc = p -> {
            if (p.hasPermission("starcosmetics.admin.bypasscheck")) return 100;
            double prog = progress.apply(p).doubleValue();
            return Math.min(100, Math.max(0, prog));
        };
    }

    CompletionCriteria(Predicate<Player> criteria, Function<Player, Number> progress, String displayKey, Object firstArg, Object[] displayArguments) {
        this(criteria, progress, displayKey, ImmutableList.builder().add(firstArg).add(displayArguments).build().toArray());
    }

    private static String comma(double i) {
        return String.format("%,.0f", i);
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
     * Whether this Player meets the criteria.
     * @param p Player to check
     * @return true if meets criteria, else false
     */
    public boolean isUnlocked(@Nullable Player p) {
        if (p == null) return false;
        return criteria.test(p);
    }

    /**
     * Fetches the display message of this criteria.
     * @return Display Message
     */
    @NotNull
    public String getDisplayMessage() {
        if (displayKey.startsWith("literal:")) return displayKey.substring(8);

        Object[] displayArgs = displayArguments.clone();

        if (displayArgs.length > 1) try {
            int num = Integer.parseInt(displayArgs[0].toString());
            if (num != 1) for (int i = 1; i < displayArgs.length; i++) {
                Object value = displayArgs[i];
                if (value instanceof String) displayArgs[i] = toPlural(value.toString());
            }
        } catch (NumberFormatException ignored) {}

        return String.format(StarConfig.getConfig().get(displayKey), displayArgs);
    }

    private static String toPlural(String base) {
        String lower = base.toLowerCase();
        String trimmed = base.substring(0, base.length() - 1);

        if (lower.endsWith("ore") || lower.endsWith("prismarine") || lower.endsWith("ice")) return base;

        if (base.endsWith("l") || base.endsWith("s") || base.endsWith("p")) return base;
        if (base.endsWith("oo")) return base + "s";

        if (base.endsWith("on")) return base.substring(0, base.length() - 2) + "en";
        if (base.endsWith("man")) return base.substring(0, base.length() - 3) + "men";

        if (base.endsWith("h") || base.endsWith("o")) return base + "es";
        if (base.endsWith("y")) return trimmed + "ies";

        return base + "s";
    }

    /**
     * Fetches the progress of a Player for this CompletionCriteria.
     * @param p Player to fetch progress for
     * @return Progress Percentage
     */
    public double getProgressPercentage(@NotNull Player p) {
        return progressFunc.apply(p).doubleValue();
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
        Predicate<Player> completed = p -> new StarPlayer(p).hasCompleted(completion);

        return new CompletionCriteria(
                completed,
                p -> completed.test(p) ? 100 : 0,
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
                p -> ((double) p.getStatistic(Statistic.MINE_BLOCK, m) / amount) * 100,
                "criteria.amount.mined", comma(amount), WordUtils.capitalizeFully(m.name().replace("_", " ")));
    }

    /**
     * Generates a CompletionCriteria from a distance statistic (e.g. {@link Statistic#WALK_ONE_CM}).
     * @param stat Statistic to check
     * @param cm The amount of centimeters to check for
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromDistance(Statistic stat, double cm) {
        String formattedDistance;

        if (cm < 100) formattedDistance = comma(cm) + "cm";
        else if (cm > 100 && cm < 100000) formattedDistance = comma(cm / 100) + "m";
        else
            formattedDistance = comma(cm / 100000) + "km";

        return new CompletionCriteria(
                p -> p.getStatistic(stat) >= cm,
                p -> ((double) p.getStatistic(stat) / cm) * 100,
                "criteria.amount.distance." + stat.name().toLowerCase(), formattedDistance);
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
           return ((double) count / amount) * 100;
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
                p -> ((double) p.getStatistic(Statistic.KILL_ENTITY, type) / amount) * 100,
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
                p -> ((double) p.getStatistic(stat) / amount) * 100,
                "criteria.amount." + stat.name().toLowerCase(), comma(amount));
    }

    /**
     * Generates a CompletionCriteria from the amount of blocks mined.
     * @param amount Blocks Mined
     * @return CompletionCriteria with the given criteria
     */
    public static CompletionCriteria fromBlocksMined(int amount) {
        return new CompletionCriteria(p -> {
            int count = 0;
            for (Material m : Material.values()) if (m.isBlock()) count += p.getStatistic(Statistic.MINE_BLOCK, m);
            return count >= amount;
        }, p -> {
           int count = 0;
           for (Material m : Material.values()) if (m.isBlock()) count += p.getStatistic(Statistic.MINE_BLOCK, m);
           return ((double) count / amount) * 100;
        }, "criteria.amount.mined.all", comma(amount));
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
                p -> ((double) p.getStatistic(Statistic.CRAFT_ITEM, m) / amount) * 100,
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
           return ((double) count / amount) * 100;
        }, "criteria.amount.crafted.list." + materials.size(), comma(amount), materials.stream()
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
        }, p -> ((double) p.getStatistic(stat) / ticks) * 100, "criteria.amount.playtime", formatTime(ticks));
    }

    /**
     * Constructs a CompletionCriteria required to have the given selection limit.
     * @param limit Selection Limit
     * @return CompletionCriteria based on selection limit
     * @throws IllegalArgumentException if the limit is not positive
     */
    @NotNull
    public static CompletionCriteria fromSelectionLimit(int limit) throws IllegalArgumentException {
        if (limit < 1) throw new IllegalArgumentException("Selection limit must be positive");

        if (limit < 5) return CompletionCriteria.fromKilled(200 + (limit * 30), EntityType.ZOMBIE);
        if (limit < 10) return CompletionCriteria.fromMined(260 + (limit * 40), Material.GOLD_ORE);
        if (limit < 20) return CompletionCriteria.fromStatistic(Statistic.ITEM_ENCHANTED, 100 + (limit * 20));
        if (limit < 30) return CompletionCriteria.fromKilled(15 + (limit * 3), EntityType.ENDER_DRAGON);

        return CompletionCriteria.fromCrafted(260, Material.JUKEBOX);
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
