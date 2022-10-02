package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticKey;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_17 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            .add(new TrailSelection("deepslate", BaseTrail.PROJECTILE_TRAIL, Material.DEEPSLATE,
                    CompletionCriteria.fromMined(370, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), CosmeticRarity.OCCASIONAL))

            .add(new TrailSelection("copper", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.COPPER_BLOCK, Material.WEATHERED_COPPER, Material.EXPOSED_COPPER, Material.OXIDIZED_COPPER),
                    CompletionCriteria.fromMined(340, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE), CosmeticRarity.UNCOMMON))

            .add(new TrailSelection("raw_ores", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.RAW_IRON, Material.RAW_GOLD, Material.RAW_COPPER),
                    CompletionCriteria.fromMined(500, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), CosmeticRarity.RARE))
            
            .add(new TrailSelection("amethyst", BaseTrail.PROJECTILE_TRAIL, Material.AMETHYST_SHARD,
                    CompletionCriteria.fromMined(2000, Material.AMETHYST_BLOCK), CosmeticRarity.EPIC))

            .add(new TrailSelection("super_amethyst", BaseTrail.PROJECTILE_TRAIL, Material.AMETHYST_BLOCK,
                    CompletionCriteria.fromMined(5000, Material.AMETHYST_BLOCK), CosmeticRarity.LEGENDARY))
            
            .build();

    // Selections

    private static final Map<CosmeticKey, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<CosmeticKey, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_16").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<CosmeticKey, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }
}
