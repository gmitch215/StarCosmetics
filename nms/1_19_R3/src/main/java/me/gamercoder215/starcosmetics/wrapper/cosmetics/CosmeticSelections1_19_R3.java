package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.StarParticle;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.Sound;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromCrafted;
import static me.gamercoder215.starcosmetics.api.CompletionCriteria.fromMined;
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

@SuppressWarnings("UnstableApiUsage")
final class CosmeticSelections1_19_R3 implements CosmeticSelections {

    // Trails

    // Projectile Trails

    private static final List<CosmeticSelection<?>> PROJECTILE_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("cherry_leaves", BaseTrail.PROJECTILE_TRAIL, StarParticle.CHERRY_LEAVES.find(),
                    fromMined(20, Material.CHERRY_LOG), COMMON))

            .add(new TrailSelection("bamboo_block", BaseTrail.PROJECTILE_TRAIL, Material.BAMBOO_BLOCK,
                    fromMined(100, Material.BAMBOO), OCCASIONAL))

            .add(new TrailSelection("stripped_bamboo_block", BaseTrail.PROJECTILE_TRAIL, Material.STRIPPED_BAMBOO_BLOCK,
                    fromMined(240, Material.BAMBOO), RARE))
            .add(new TrailSelection("cherry_sapling", BaseTrail.PROJECTILE_TRAIL, Material.CHERRY_SAPLING,
                    fromMined(195, Material.CHERRY_LOG), RARE))
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("bamboo_block", BaseTrail.GROUND_TRAIL, "ground_block:bamboo_block",
                    fromMined(115, Material.BAMBOO), OCCASIONAL))
            .add(new TrailSelection("cherry_pressure_plate", BaseTrail.GROUND_TRAIL, "ground_block:cherry_pressure_plate",
                    fromMined(125, Material.CHERRY_LOG), OCCASIONAL))

            .add(new TrailSelection("stripped_bamboo_block", BaseTrail.GROUND_TRAIL, "ground_block:stripped_bamboo_block",
                    fromMined(240, Material.BAMBOO), RARE))
            .add(new TrailSelection("cherry_sapling", BaseTrail.GROUND_TRAIL, Material.CHERRY_SAPLING,
                    fromMined(215, Material.CHERRY_LOG), RARE))

            .add(new TrailSelection("bamboo_mosaic", BaseTrail.GROUND_TRAIL, "ground_block:bamboo_mosaic",
                    fromMined(310, Material.BAMBOO_MOSAIC), EPIC))
            .add(new TrailSelection("bamboo_mosaic_slab", BaseTrail.GROUND_TRAIL, "ground_block:bamboo_mosaic_slab",
                    fromMined(375, Material.BAMBOO), EPIC))
            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("decorated_pot_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_DECORATED_POT_PLACE,
                    fromCrafted(50, Material.DECORATED_POT), COMMON))

            .build();

    // Shapes

    private static final List<CosmeticSelection<?>> PARTICLE_SHAPES = ImmutableList.<CosmeticSelection<?>>builder()
            // Small Rings
            .add(new ParticleSelection("cherry_leaves", BaseShape.SMALL_RING, StarParticle.CHERRY_LEAVES.find(),
                    fromMined(20, Material.CHERRY_LOG), COMMON))
        
            // Combinations
            .add(new ParticleSelection("cherry_log", BaseShape.PENTAGON_RING, Material.CHERRY_LOG,
                    fromMined(125, Material.CHERRY_LOG), UNCOMMON))

            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("bamboo_block", Material.BAMBOO_BLOCK,
                    fromMined(100, Material.BAMBOO), OCCASIONAL))
            .add(new HatSelection("stripped_bamboo_block", Material.STRIPPED_BAMBOO_BLOCK,
                    fromMined(160, Material.BAMBOO), OCCASIONAL))

            .add(new HatSelection("decorated_pot", Material.DECORATED_POT,
                    fromCrafted(90, Material.DECORATED_POT), RARE))

            .add(new HatSelection("cherry_leaves", Material.CHERRY_LEAVES,
                    fromMined(500, Material.CHERRY_LOG), EPIC))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAIL, BaseTrail.PROJECTILE_TRAIL, "1_19"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAIL, BaseTrail.GROUND_TRAIL, "1_19"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAIL, BaseTrail.SOUND_TRAIL, "1_19"))

            .put(BaseShape.ALL, join(PARTICLE_SHAPES, BaseShape.ALL, "1_19"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_19"))
            .put(BaseHat.ANIMATED, getForVersion(BaseHat.ANIMATED, "1_19"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        if (Wrapper.getServerVersion().equalsIgnoreCase("1_19_R3") && !w.hasFeatureFlag("c")) return CosmeticSelections.getForVersion("1_19");

        return SELECTIONS;
    }

    @Override
    public void loadPets() { CosmeticSelections.loadExternalPets("1_19"); }
    
}
