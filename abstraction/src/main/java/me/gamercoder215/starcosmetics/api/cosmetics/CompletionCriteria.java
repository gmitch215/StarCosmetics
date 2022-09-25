package me.gamercoder215.starcosmetics.api.cosmetics;

import com.google.common.collect.ImmutableList;
import org.apache.commons.lang.WordUtils;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;

import java.util.Arrays;
import java.util.Collection;
import java.util.function.Predicate;

public class CompletionCriteria {

    private final Predicate<Player> criteria;
    private final String displayKey;

    private final Object[] displayArguments;

    public CompletionCriteria(Predicate<Player> criteria, String displayKey, Object... displayArguments) {
        this.criteria = criteria;
        this.displayKey = displayKey;
        this.displayArguments = displayArguments;
    }

    public CompletionCriteria(Predicate<Player> criteria, String displayKey, Object firstArg, Object[] displayArguments) {
        this(criteria, displayKey, ImmutableList.builder().add(firstArg).add(firstArg).build().toArray());
    }

    public Predicate<Player> getCriteria() {
        return criteria;
    }

    public boolean hasUnlocked(Player player) {
        if (player == null) return false;
        return criteria.test(player);
    }

    public String getDisplayKey() {
        return displayKey;
    }

    public Object[] getDisplayArguments() {
        return displayArguments;
    }

    // Static Generators

    public static CompletionCriteria fromMined(int amount, Material m) {
        return new CompletionCriteria(p -> p.getStatistic(Statistic.MINE_BLOCK, m) >= amount, "criteria.amount.mined", amount, WordUtils.capitalizeFully(m.name().replace("_", " ")));
    }

    public static CompletionCriteria fromMined(int amount, Collection<Material> materials) {
        return new CompletionCriteria(p -> {
            int count = 0;
            for (Material m : materials) {
                count += p.getStatistic(Statistic.MINE_BLOCK, m);
            }
            return count >= amount;
        }, "criteria.amount.list." + materials.size(), amount, materials.toArray());
    }

    public static CompletionCriteria fromMined(int amount, Material... materials) {
        return fromMined(amount, Arrays.asList(materials));
    }

    public static CompletionCriteria fromKilled(int amount, EntityType type) {
        return new CompletionCriteria(p -> p.getStatistic(Statistic.KILL_ENTITY, type) >= amount, "criteria.amount.killed", amount, WordUtils.capitalizeFully(type.name().replace("_", " ")));
    }

    public static CompletionCriteria fromStatistic(Statistic stat, int amount) {
        return new CompletionCriteria(p -> p.getStatistic(stat) >= amount, "criteria.amount." + stat.name().toLowerCase(), amount);
    }

    public static CompletionCriteria fromCrafted(int amount, Material m) {
        return new CompletionCriteria(p -> p.getStatistic(Statistic.CRAFT_ITEM, m) >= amount, "criteria.amount.crafted", amount, WordUtils.capitalizeFully(m.name().replace("_", " ")));
    }

}
