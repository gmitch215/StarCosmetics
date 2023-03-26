package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromMined;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

public final class CosmeticSelections1_10 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails

    // Projectile Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("magma_block", BaseTrail.PROJECTILE_TRAIL, StarMaterial.MAGMA_BLOCK.find(),
                    fromMined(175, StarMaterial.MAGMA_BLOCK.find()), Rarity.COMMON))

            .add(new TrailSelection("structure_void", BaseTrail.PROJECTILE_TRAIL, "fancy_item:structure_void",
                    fromMined(12000, Material.OBSIDIAN), Rarity.MYTHICAL))
            
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("magma_block", BaseTrail.GROUND_TRAIL, StarMaterial.MAGMA_BLOCK.find(),
                    fromMined(205, StarMaterial.MAGMA_BLOCK.find()), Rarity.COMMON))

            .add(new TrailSelection("structure_void", BaseTrail.GROUND_TRAIL, Material.STRUCTURE_VOID,
                    fromMined(12500, Material.OBSIDIAN), Rarity.MYTHICAL))

            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("magma_block", StarMaterial.MAGMA_BLOCK.find(),
                    fromMined(200, StarMaterial.MAGMA_BLOCK.find()), Rarity.OCCASIONAL))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_9"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_9"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_9"))

            .put(BaseShape.SMALL_RING, getForVersion(BaseShape.SMALL_RING, "1_9"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_9"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_9"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_9"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_9"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        loadExternalPets("1_9");
    }

}
