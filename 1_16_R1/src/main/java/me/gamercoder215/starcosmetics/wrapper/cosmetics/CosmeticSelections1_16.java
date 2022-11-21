package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_16 implements CosmeticSelections {


    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("shroomlight", BaseTrail.PROJECTILE_TRAIL, Material.SHROOMLIGHT,
                    CompletionCriteria.fromMined(120, Material.CRIMSON_STEM, Material.WARPED_STEM), Rarity.COMMON))

            .add(new TrailSelection("crying_obsidian", BaseTrail.PROJECTILE_TRAIL, Material.CRYING_OBSIDIAN,
                    CompletionCriteria.fromMined(250, Material.OBSIDIAN), Rarity.UNCOMMON))

            .add(new TrailSelection("netherite", BaseTrail.PROJECTILE_TRAIL, Material.NETHERITE_INGOT,
                    CompletionCriteria.fromMined(120, Material.ANCIENT_DEBRIS), Rarity.RARE))
            
            .add(new TrailSelection("super_netherite", BaseTrail.PROJECTILE_TRAIL, Material.NETHERITE_BLOCK,
                    CompletionCriteria.fromMined(260, Material.ANCIENT_DEBRIS), Rarity.EPIC))
            .add(new TrailSelection("pigstep", BaseTrail.PROJECTILE_TRAIL, Material.MUSIC_DISC_PIGSTEP,
                    CompletionCriteria.fromKilled(500, EntityType.PIGLIN), Rarity.EPIC))
            
            .add(new TrailSelection("netherite_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:netherite_sword",
                    CompletionCriteria.fromKilled(1250, EntityType.WITHER_SKELETON), Rarity.MYTHICAL))
            
            .add(new TrailSelection("netherite_axe", BaseTrail.PROJECTILE_TRAIL, "fancy_item:netherite_axe",
                    CompletionCriteria.fromKilled(2500, EntityType.WITHER_SKELETON), Rarity.ULTRA))

            .build();

    // Gound Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("crimson_nylium", BaseTrail.GROUND_TRAIL, "ground_block:crimson_nylium",
                    CompletionCriteria.fromMined(130, Material.CRIMSON_STEM), Rarity.OCCASIONAL))
            .add(new TrailSelection("warped_nylium", BaseTrail.GROUND_TRAIL, "ground_block:warped_nylium",
                    CompletionCriteria.fromMined(130, Material.WARPED_STEM), Rarity.OCCASIONAL))

            .add(new TrailSelection("blackstone", BaseTrail.GROUND_TRAIL, "ground_block:blackstone",
                    CompletionCriteria.fromMined(200, Material.BLACKSTONE), Rarity.UNCOMMON))
            .add(new TrailSelection("polished_basalt", BaseTrail.GROUND_TRAIL, "ground_block:polished_basalt",
                    CompletionCriteria.fromMined(230, Material.BASALT), Rarity.UNCOMMON))

            .add(new TrailSelection("soul_flame", BaseTrail.GROUND_TRAIL, Particle.SOUL_FIRE_FLAME,
                    CompletionCriteria.fromMined(800, Material.SOUL_SAND, Material.SOUL_SOIL), Rarity.RARE))

            .add(new TrailSelection("crying_obsidian", BaseTrail.GROUND_TRAIL, "ground_block:crying_obsidian",
                    CompletionCriteria.fromKilled(50, EntityType.GHAST), Rarity.EPIC))

            .add(new TrailSelection("netherite_block", BaseTrail.GROUND_TRAIL, "ground_block:netherite_block",
                    CompletionCriteria.fromMined(230, Material.ANCIENT_DEBRIS), Rarity.MYTHICAL))

            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("shroomlight_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_SHROOMLIGHT_PLACE,
                    CompletionCriteria.fromMined(75, Material.CRIMSON_STEM, Material.WARPED_STEM), Rarity.COMMON))

            .add(new TrailSelection("netherite_break", BaseTrail.SOUND_TRAIL, Sound.BLOCK_NETHERITE_BLOCK_BREAK,
                    CompletionCriteria.fromKilled(490, EntityType.PIGLIN), Rarity.EPIC))

            .build();


    // Shapes

    // Small Rings

    private static final List<CosmeticSelection<?>> SMALL_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("soul_flame", BaseShape.SMALL_RING, Particle.SOUL_FIRE_FLAME,
                    CompletionCriteria.fromKilled(500, EntityType.WITHER_SKELETON), Rarity.RARE))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_15"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAILS, BaseTrail.SOUND_TRAIL, "1_15"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_15"))

            .put(BaseShape.SMALL_RING, join(SMALL_RINGS, BaseShape.SMALL_RING, "1_15"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_15"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_15"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_15"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
