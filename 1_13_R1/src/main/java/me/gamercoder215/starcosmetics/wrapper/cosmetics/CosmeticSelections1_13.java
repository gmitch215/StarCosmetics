package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseShape.circle;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

public class CosmeticSelections1_13 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("kelp", BaseTrail.PROJECTILE_TRAIL, Material.KELP,
                    fromKilled(25, EntityType.SALMON), Rarity.COMMON))

            .add(new TrailSelection("shells", BaseTrail.PROJECTILE_TRAIL, Material.NAUTILUS_SHELL,
                    fromKilled(80, EntityType.DROWNED), Rarity.OCCASIONAL))
            .add(new TrailSelection("gunpowder", BaseTrail.PROJECTILE_TRAIL, Material.GUNPOWDER,
                    fromKilled(90, EntityType.CREEPER), Rarity.OCCASIONAL))
            .add(new TrailSelection("melon", BaseTrail.PROJECTILE_TRAIL, Material.MELON_SLICE,
                    fromMined(100, Material.MELON), Rarity.OCCASIONAL))

            .add(new TrailSelection("coral", BaseTrail.PROJECTILE_TRAIL, 
                    Arrays.asList(Material.FIRE_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK),
                    fromCrafted(15, Material.CONDUIT), Rarity.RARE))
            .add(new TrailSelection("membranes", BaseTrail.PROJECTILE_TRAIL, Material.PHANTOM_MEMBRANE,
                    fromKilled(100, EntityType.PHANTOM), Rarity.RARE))
            .add(new TrailSelection("colored_glass", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.RED_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PINK_STAINED_GLASS),
                    fromMined(100, Material.SAND), Rarity.RARE))
            .add(new TrailSelection("charcoal", BaseTrail.PROJECTILE_TRAIL, Material.CHARCOAL,
                    fromMined(2300, StarMaterial.OAK_LOG.find()), Rarity.RARE))
            .add(new TrailSelection("polished_andesite", BaseTrail.PROJECTILE_TRAIL, Material.POLISHED_ANDESITE,
                    fromMined(1000, Material.ANDESITE), Rarity.RARE))
                    
            .add(new TrailSelection("sea_heart", BaseTrail.PROJECTILE_TRAIL, Material.HEART_OF_THE_SEA,
                    fromKilled(400, EntityType.ELDER_GUARDIAN), Rarity.EPIC))
            .add(new TrailSelection("scutes", BaseTrail.PROJECTILE_TRAIL, Material.SCUTE,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 500), Rarity.EPIC))
            .add(new TrailSelection("sea_pickle", BaseTrail.PROJECTILE_TRAIL, Material.SEA_PICKLE,
                    fromMined(500, Material.CLAY), Rarity.EPIC))
            .add(new TrailSelection("turtle_egg", BaseTrail.PROJECTILE_TRAIL, Material.TURTLE_EGG,
                    fromMined(650, Material.KELP), Rarity.EPIC))

            .add(new TrailSelection("riptide", BaseTrail.PROJECTILE_TRAIL, "riptide",
                    fromKilled(1000, EntityType.DROWNED), Rarity.LEGENDARY))
            .add(new TrailSelection("blue_ice", BaseTrail.PROJECTILE_TRAIL, Material.BLUE_ICE,
                    fromMined(685, Material.BLUE_ICE), Rarity.LEGENDARY))

            .add(new TrailSelection("notch_apple", BaseTrail.PROJECTILE_TRAIL, Material.ENCHANTED_GOLDEN_APPLE,
                    fromKilled(150, EntityType.WITHER), Rarity.MYTHICAL))

            // Entities
            .add(new TrailSelection("salmon", BaseTrail.PROJECTILE_TRAIL, EntityType.SALMON,
                    fromKilled(45, EntityType.SALMON), Rarity.COMMON))

            .add(new TrailSelection("squid", BaseTrail.PROJECTILE_TRAIL, EntityType.SQUID,
                    fromKilled(100, EntityType.SQUID), Rarity.OCCASIONAL))
            .add(new TrailSelection("cod", BaseTrail.PROJECTILE_TRAIL, EntityType.COD,
                    fromKilled(110, EntityType.COD), Rarity.OCCASIONAL))


            .build();

    // Ground Trails
    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("oak_planks", BaseTrail.GROUND_TRAIL, "ground_block:oak_planks",
                    fromMined(50, Material.OAK_LEAVES), Rarity.COMMON))
            .add(new TrailSelection("birch_planks", BaseTrail.GROUND_TRAIL, "ground_block:birch_planks",
                    fromMined(50, Material.BIRCH_LEAVES), Rarity.COMMON))
            .add(new TrailSelection("spruce_planks", BaseTrail.GROUND_TRAIL, "ground_block:spruce_planks",
                    fromCrafted(50, Material.SPRUCE_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("jungle_planks", BaseTrail.GROUND_TRAIL, "ground_block:jungle_planks",
                    fromCrafted(50, Material.JUNGLE_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("acacia_planks", BaseTrail.GROUND_TRAIL, "ground_block:acacia_planks",
                    fromCrafted(50, Material.DARK_OAK_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("dark_oak_planks", BaseTrail.GROUND_TRAIL, "ground_block:dark_oak_planks",
                    fromCrafted(50, Material.DARK_OAK_PLANKS), Rarity.COMMON))
            .add(new TrailSelection("bubble_coral", BaseTrail.GROUND_TRAIL, "ground_block:bubble_coral_block",
                    fromKilled(35, EntityType.SALMON), Rarity.COMMON))
            .add(new TrailSelection("potato", BaseTrail.GROUND_TRAIL, Material.POTATO,
                    fromMined(50, Material.POTATOES), Rarity.COMMON))

            .add(new TrailSelection("sea_pickle", BaseTrail.GROUND_TRAIL, Material.SEA_PICKLE,
                    fromKilled(70, EntityType.COD), Rarity.OCCASIONAL))
            .add(new TrailSelection("rail", BaseTrail.GROUND_TRAIL, "side_block:rail",
                    fromCrafted(60, Material.RAIL), Rarity.OCCASIONAL))

            .add(new TrailSelection("oak_sapling", BaseTrail.GROUND_TRAIL, Material.OAK_SAPLING,
                    fromMined(75, Material.OAK_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("birch_sapling", BaseTrail.GROUND_TRAIL, Material.BIRCH_SAPLING,
                    fromMined(75, Material.BIRCH_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("acacia_sapling", BaseTrail.GROUND_TRAIL, Material.ACACIA_SAPLING,  
                    fromMined(75, Material.ACACIA_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("iron_pressure_plate", BaseTrail.GROUND_TRAIL, "side_block:heavy_weighted_pressure_plate",
                    fromCrafted(30, Material.HEAVY_WEIGHTED_PRESSURE_PLATE), Rarity.UNCOMMON))

            .add(new TrailSelection("spruce_sapling", BaseTrail.GROUND_TRAIL, Material.SPRUCE_SAPLING,
                    fromMined(75, Material.SPRUCE_LEAVES), Rarity.UNCOMMON))
            .add(new TrailSelection("jungle_sapling", BaseTrail.GROUND_TRAIL, Material.JUNGLE_SAPLING,
                    fromMined(75, Material.JUNGLE_LEAVES), Rarity.RARE))
            .add(new TrailSelection("dark_oak_sapling", BaseTrail.GROUND_TRAIL, Material.DARK_OAK_SAPLING,
                    fromMined(75, Material.DARK_OAK_LEAVES), Rarity.RARE))
            .add(new TrailSelection("golden_pressure_plate", BaseTrail.GROUND_TRAIL, "side_block:light_weighted_pressure_plate",
                    fromCrafted(30, Material.LIGHT_WEIGHTED_PRESSURE_PLATE), Rarity.RARE))

            .add(new TrailSelection("heart_of_the_sea", BaseTrail.GROUND_TRAIL, Material.HEART_OF_THE_SEA,
                    fromKilled(85, EntityType.ELDER_GUARDIAN), Rarity.LEGENDARY))

            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()


            .add(new TrailSelection("riptide", BaseTrail.SOUND_TRAIL, StarSound.ITEM_TRIDENT_RIPTIDE_1.find(),
                    fromKilled(500, EntityType.DROWNED), Rarity.EPIC))

            .build();

    // Shapes

    // Small Detailed Rings

    private static final List<CosmeticSelection<?>> SMALL_DETAILED_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("nautilus", BaseShape.SMALL_DETAILED_RING, Particle.NAUTILUS,
                    fromKilled(2650, EntityType.DROWNED), Rarity.LEGENDARY))
            .build();

    private static final List<CosmeticSelection<?>> LARGE_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("kelp", BaseShape.LARGE_RING, Material.KELP,
                    fromMined(250, Material.KELP), Rarity.UNCOMMON))
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

    @Override
    public void loadPets() {
        PET_MAP.putAll(
                ImmutableMap.<PetType, PetInfo>builder()
                        .put(PetType.PUFFERFISH, of(
                                "Pufferfish", Rarity.UNCOMMON,
                                petIcon("pufferfish_pet", "Pufferfish"), fromKilled(160, EntityType.COD), stand ->
                                    w.spawnFakeItem(new ItemStack(Material.PUFFERFISH), head(stand), 10)
                        ))

                        .put(PetType.WHALE, of(
                                "Whale", Rarity.EPIC,
                                petIcon("whale_pet", "Whale"), fromMined(3000, Material.BRAIN_CORAL_BLOCK), stand -> {
                                    circle(head(stand), Particle.WATER_BUBBLE, 5, 0.5);
                                    stand.getWorld().spawnParticle(Particle.WATER_SPLASH, head(stand), 3, 0.1, 0.1, 0.1, 0.1);
                                }
                        ))

                        .put(PetType.NARWHAL, of(
                                "Narwhal", Rarity.LEGENDARY,
                                petIcon("narwhal_pet", "Narwhal"), fromKilled(2000, EntityType.DROWNED), stand -> {
                                    circle(head(stand), Material.PRISMARINE, 10, 0.5);
                                    stand.getWorld().spawnParticle(Particle.DRIP_WATER, head(stand), 2, 0, 0, 0, 0.1);
                                }
                        ))

                        .put(PetType.JELLYFISH, of(
                                "Jellyfish", Rarity.ULTRA,
                                petIcon("jellyfish_pet", "Jellyfish"), fromKilled(500000, EntityType.DROWNED), stand ->
                                    stand.getWorld().spawnParticle(Particle.WATER_BUBBLE, head(stand), 2, 0, 0, 0, 0.1)
                        ))


                        .build()
        );

        loadExternalPets("1_12");
    }

}
