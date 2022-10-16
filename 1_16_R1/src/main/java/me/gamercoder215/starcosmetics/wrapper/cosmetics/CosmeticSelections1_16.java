package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_16 implements CosmeticSelections {


    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("shroomlight", BaseTrail.PROJECTILE_TRAIL, Material.SHROOMLIGHT,
                    CompletionCriteria.fromMined(120, Material.CRIMSON_STEM, Material.WARPED_STEM), CosmeticRarity.COMMON))

            .add(new TrailSelection("crying_obsidian", BaseTrail.PROJECTILE_TRAIL, Material.CRYING_OBSIDIAN,
                    CompletionCriteria.fromMined(250, Material.OBSIDIAN), CosmeticRarity.UNCOMMON))

            .add(new TrailSelection("netherite", BaseTrail.PROJECTILE_TRAIL, Material.NETHERITE_INGOT,
                    CompletionCriteria.fromMined(120, Material.ANCIENT_DEBRIS), CosmeticRarity.RARE))
            
            .add(new TrailSelection("super_netherite", BaseTrail.PROJECTILE_TRAIL, Material.NETHERITE_BLOCK,
                    CompletionCriteria.fromMined(260, Material.ANCIENT_DEBRIS), CosmeticRarity.EPIC))
            .add(new TrailSelection("pigstep", BaseTrail.PROJECTILE_TRAIL, Material.MUSIC_DISC_PIGSTEP,
                    CompletionCriteria.fromKilled(500, EntityType.PIGLIN), CosmeticRarity.EPIC))
            
            .add(new TrailSelection("netherite_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:netherite_sword",
                    CompletionCriteria.fromKilled(1250, EntityType.WITHER_SKELETON), CosmeticRarity.MYTHICAL))
            
            .build();

    private static final List<CosmeticSelection<?>> BLOCK_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("soul_flame", BaseTrail.GROUND_TRAIL, Particle.SOUL_FIRE_FLAME,
                    CompletionCriteria.fromMined(800, Material.SOUL_SAND, Material.SOUL_SOIL), CosmeticRarity.RARE))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_15").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .put(BaseTrail.GROUND_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.GROUND_TRAIL, "1_15").stream(),
                    BLOCK_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
