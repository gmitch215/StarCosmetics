package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.StarAdvancement;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.CompletionCriteria1_12_R1.fromAdvancement;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

public final class CosmeticSelections1_12 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("concrete", BaseTrail.PROJECTILE_TRAIL, ImmutableList.of(
                    Material.RED_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA,
                    Material.GREEN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA,
                    Material.MAGENTA_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA
            ), fromStatistic(Statistic.ANIMALS_BRED, 650), Rarity.RARE))

            .add(new TrailSelection("nuggets", BaseTrail.PROJECTILE_TRAIL, ImmutableList.of(Material.GOLD_NUGGET, Material.IRON_NUGGET),
                    fromKilled(1000, EntityType.IRON_GOLEM), Rarity.LEGENDARY))

            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("knowledge_book", BaseTrail.GROUND_TRAIL, Material.KNOWLEDGE_BOOK,
                    fromCrafted(100, StarMaterial.CRAFTING_TABLE.find()), Rarity.RARE))

            .add(new TrailSelection("concrete", BaseTrail.GROUND_TRAIL, ImmutableList.of(
                Material.RED_GLAZED_TERRACOTTA, Material.ORANGE_GLAZED_TERRACOTTA, Material.YELLOW_GLAZED_TERRACOTTA,
                Material.GREEN_GLAZED_TERRACOTTA, Material.CYAN_GLAZED_TERRACOTTA, Material.BLUE_GLAZED_TERRACOTTA,
                Material.MAGENTA_GLAZED_TERRACOTTA, Material.PURPLE_GLAZED_TERRACOTTA, Material.PINK_GLAZED_TERRACOTTA
            ), fromStatistic(Statistic.ANIMALS_BRED, 850), Rarity.EPIC))
            .add(new TrailSelection("grass_block", BaseTrail.GROUND_TRAIL, StarMaterial.GRASS_BLOCK.find(),
                    fromAdvancement(StarAdvancement.ADVENTURING_TIME), Rarity.EPIC))

            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_11"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_11"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_11"))

            .put(BaseShape.SMALL_RING, getForVersion(BaseShape.SMALL_RING, "1_11"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_11"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_11"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_11"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        loadExternalPets("1_11");
    }

}
