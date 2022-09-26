package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.selection.*;

import org.bukkit.Material;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_17 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            .add(new TrailSelection("deepslate", BaseTrail.PROJECTILE_TRAIL, Material.DEEPSLATE,
                    CompletionCriteria.fromMined(700, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), CosmeticRarity.OCCASIONAL))
            
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
