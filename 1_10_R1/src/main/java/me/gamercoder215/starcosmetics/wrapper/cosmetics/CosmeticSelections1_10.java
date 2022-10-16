package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_10 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("magma_block", BaseTrail.PROJECTILE_TRAIL, StarMaterial.MAGMA_BLOCK.find(),
                    CompletionCriteria.fromMined(175, StarMaterial.MAGMA_BLOCK.find()), CosmeticRarity.COMMON))

            .add(new TrailSelection("structure_void", BaseTrail.PROJECTILE_TRAIL, "fancy_item:structure_void",
                    CompletionCriteria.fromMined(12000, Material.OBSIDIAN), CosmeticRarity.MYTHICAL))
            
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_9").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
