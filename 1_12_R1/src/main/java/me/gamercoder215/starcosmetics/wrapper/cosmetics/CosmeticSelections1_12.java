package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;

import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticKey;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.api.cosmetics.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.api.cosmetics.selection.TrailSelection;

public class CosmeticSelections1_12 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            .add(new TrailSelection("concrete", BaseTrail.PROJECTILE_TRAIL, ImmutableList.of(
                    Material.RED_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA,
                    Material.GREEN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA,
                    Material.MAGENTA_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA
            ), CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 650), CosmeticRarity.RARE))

            .add(new TrailSelection("nuggets", BaseTrail.PROJECTILE_TRAIL, ImmutableList.of(Material.GOLD_NUGGET, Material.IRON_NUGGET),
                    CompletionCriteria.fromKilled(1000, EntityType.IRON_GOLEM), CosmeticRarity.LEGENDARY))

            .build();

    // Selections

    private static final Map<CosmeticKey, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<CosmeticKey, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_11").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<CosmeticKey, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }

}
