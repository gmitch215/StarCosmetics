package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_13 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("kelp", BaseTrail.PROJECTILE_TRAIL, Material.KELP,
                    CompletionCriteria.fromKilled(25, EntityType.SALMON), Rarity.COMMON))

            .add(new TrailSelection("shells", BaseTrail.PROJECTILE_TRAIL, Material.NAUTILUS_SHELL,
                    CompletionCriteria.fromKilled(80, EntityType.DROWNED), Rarity.OCCASIONAL))
            .add(new TrailSelection("gunpowder", BaseTrail.PROJECTILE_TRAIL, Material.GUNPOWDER,
                    CompletionCriteria.fromKilled(90, EntityType.CREEPER), Rarity.OCCASIONAL))
            .add(new TrailSelection("melon", BaseTrail.PROJECTILE_TRAIL, Material.MELON_SLICE,
                    CompletionCriteria.fromMined(100, Material.MELON), Rarity.OCCASIONAL))

            .add(new TrailSelection("coral", BaseTrail.PROJECTILE_TRAIL, 
                    Arrays.asList(Material.FIRE_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK),
                    CompletionCriteria.fromCrafted(15, Material.CONDUIT), Rarity.RARE))
            .add(new TrailSelection("membranes", BaseTrail.PROJECTILE_TRAIL, Material.PHANTOM_MEMBRANE,
                    CompletionCriteria.fromKilled(100, EntityType.PHANTOM), Rarity.RARE))
            .add(new TrailSelection("colored_glass", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.RED_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PINK_STAINED_GLASS),
                    CompletionCriteria.fromMined(100, Material.SAND), Rarity.RARE))
            .add(new TrailSelection("charcoal", BaseTrail.PROJECTILE_TRAIL, Material.CHARCOAL,
                    CompletionCriteria.fromMined(2300, StarMaterial.OAK_LOG.find()), Rarity.RARE))
            .add(new TrailSelection("polished_andesite", BaseTrail.PROJECTILE_TRAIL, Material.POLISHED_ANDESITE,
                    CompletionCriteria.fromMined(1000, Material.ANDESITE), Rarity.RARE))
                    
            .add(new TrailSelection("sea_heart", BaseTrail.PROJECTILE_TRAIL, Material.HEART_OF_THE_SEA,
                    CompletionCriteria.fromKilled(400, EntityType.ELDER_GUARDIAN), Rarity.EPIC))
            .add(new TrailSelection("scutes", BaseTrail.PROJECTILE_TRAIL, Material.SCUTE,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 500), Rarity.EPIC))
            .add(new TrailSelection("sea_pickle", BaseTrail.PROJECTILE_TRAIL, Material.SEA_PICKLE,
                    CompletionCriteria.fromMined(500, Material.CLAY), Rarity.EPIC))
            .add(new TrailSelection("turtle_egg", BaseTrail.PROJECTILE_TRAIL, Material.TURTLE_EGG,
                    CompletionCriteria.fromMined(650, Material.KELP), Rarity.EPIC))

            .add(new TrailSelection("riptide", BaseTrail.PROJECTILE_TRAIL, "riptide",
                    CompletionCriteria.fromKilled(1000, EntityType.DROWNED), Rarity.LEGENDARY))
            .add(new TrailSelection("blue_ice", BaseTrail.PROJECTILE_TRAIL, Material.BLUE_ICE,
                    CompletionCriteria.fromMined(685, Material.BLUE_ICE), Rarity.LEGENDARY))

            .add(new TrailSelection("notch_apple", BaseTrail.PROJECTILE_TRAIL, Material.ENCHANTED_GOLDEN_APPLE,
                    CompletionCriteria.fromKilled(150, EntityType.WITHER), Rarity.MYTHICAL))

            // Entities
            .add(new TrailSelection("salmon", BaseTrail.PROJECTILE_TRAIL, EntityType.SALMON,
                    CompletionCriteria.fromKilled(45, EntityType.SALMON), Rarity.COMMON))

            .add(new TrailSelection("squid", BaseTrail.PROJECTILE_TRAIL, EntityType.SQUID,
                    CompletionCriteria.fromKilled(100, EntityType.SQUID), Rarity.OCCASIONAL))
            .add(new TrailSelection("cod", BaseTrail.PROJECTILE_TRAIL, EntityType.COD,
                    CompletionCriteria.fromKilled(110, EntityType.COD), Rarity.OCCASIONAL))


            .build();

    // Ground Trails
    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("oak_planks", BaseTrail.GROUND_TRAIL, "ground_block:oak_planks",
                    CompletionCriteria.fromMined(50, Material.OAK_LEAVES), Rarity.COMMON))
            .add(new TrailSelection("birch_planks", BaseTrail.GROUND_TRAIL, "ground_block:birch_planks",
                    CompletionCriteria.fromMined(50, Material.BIRCH_LEAVES), Rarity.COMMON))
            .add(new TrailSelection("spruce_planks", BaseTrail.GROUND_TRAIL, "ground_block:spruce_planks",
                    CompletionCriteria.fromCrafted(50, Material.SPRUCE_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("jungle_planks", BaseTrail.GROUND_TRAIL, "ground_block:jungle_planks",
                    CompletionCriteria.fromCrafted(50, Material.JUNGLE_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("acacia_planks", BaseTrail.GROUND_TRAIL, "ground_block:acacia_planks",
                    CompletionCriteria.fromCrafted(50, Material.DARK_OAK_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("dark_oak_planks", BaseTrail.GROUND_TRAIL, "ground_block:dark_oak_planks",
                    CompletionCriteria.fromCrafted(50, Material.DARK_OAK_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("bubble_coral", BaseTrail.GROUND_TRAIL, "ground_block:bubble_coral_block",
                    CompletionCriteria.fromKilled(35, EntityType.SALMON), Rarity.COMMON))

            .add(new TrailSelection("sea_pickle", BaseTrail.GROUND_TRAIL, Material.SEA_PICKLE,
                    CompletionCriteria.fromKilled(70, EntityType.COD), Rarity.OCCASIONAL))

            .add(new TrailSelection("oak_sapling", BaseTrail.GROUND_TRAIL, Material.OAK_SAPLING,
                    CompletionCriteria.fromMined(75, Material.OAK_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("birch_sapling", BaseTrail.GROUND_TRAIL, Material.BIRCH_SAPLING,
                    CompletionCriteria.fromMined(75, Material.BIRCH_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("acacia_sapling", BaseTrail.GROUND_TRAIL, Material.ACACIA_SAPLING,  
                    CompletionCriteria.fromMined(75, Material.ACACIA_LEAVES), Rarity.UNCOMMON))
            
            .add(new TrailSelection("spruce_sapling", BaseTrail.GROUND_TRAIL, Material.SPRUCE_SAPLING,
                    CompletionCriteria.fromMined(75, Material.SPRUCE_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("jungle_sapling", BaseTrail.GROUND_TRAIL, Material.JUNGLE_SAPLING,
                    CompletionCriteria.fromMined(75, Material.JUNGLE_LEAVES), Rarity.RARE))
            .add(new TrailSelection("dark_oak_sapling", BaseTrail.GROUND_TRAIL, Material.DARK_OAK_SAPLING,
                    CompletionCriteria.fromMined(75, Material.DARK_OAK_LEAVES), Rarity.RARE))

            .add(new TrailSelection("heart_of_the_sea", BaseTrail.GROUND_TRAIL, Material.HEART_OF_THE_SEA,
                    CompletionCriteria.fromKilled(85, EntityType.ELDER_GUARDIAN), Rarity.LEGENDARY))

            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()


            .add(new TrailSelection("riptide", BaseTrail.SOUND_TRAIL, StarSound.ITEM_TRIDENT_RIPTIDE_1.find(),
                    CompletionCriteria.fromKilled(500, EntityType.DROWNED), Rarity.EPIC))

            .build();

    // Shapes

    // Small Detailed Rings

    private static final List<CosmeticSelection<?>> SMALL_DETAILED_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("nautilus", BaseShape.SMALL_DETAILED_RING, Particle.NAUTILUS,
                    CompletionCriteria.fromKilled(2650, EntityType.DROWNED), Rarity.LEGENDARY))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_12"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_12"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAILS, BaseTrail.SOUND_TRAIL, "1_12"))

            .put(BaseShape.SMALL_RING, getForVersion(BaseShape.SMALL_RING, "1_12"))
            .put(BaseShape.SMALL_DETAILED_RING, join(SMALL_DETAILED_RINGS, BaseShape.SMALL_DETAILED_RING, "1_12"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_12"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_12"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
