package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
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
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseHat.NORMAL;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseShape.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

public final class CosmeticSelections1_13 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("kelp", PROJECTILE_TRAIL, Material.KELP,
                    fromKilled(25, EntityType.SALMON), COMMON))

            .add(new TrailSelection("shells", PROJECTILE_TRAIL, Material.NAUTILUS_SHELL,
                    fromKilled(80, EntityType.DROWNED), OCCASIONAL))
            .add(new TrailSelection("gunpowder", PROJECTILE_TRAIL, Material.GUNPOWDER,
                    fromKilled(90, EntityType.CREEPER), OCCASIONAL))
            .add(new TrailSelection("melon", PROJECTILE_TRAIL, Material.MELON_SLICE,
                    fromMined(100, Material.MELON), OCCASIONAL))

            .add(new TrailSelection("coral", PROJECTILE_TRAIL,
                    Arrays.asList(Material.FIRE_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK),
                    fromCrafted(15, Material.CONDUIT), RARE))
            .add(new TrailSelection("membranes", PROJECTILE_TRAIL, Material.PHANTOM_MEMBRANE,
                    fromKilled(100, EntityType.PHANTOM), RARE))
            .add(new TrailSelection("colored_glass", PROJECTILE_TRAIL,
                    Arrays.asList(Material.RED_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PINK_STAINED_GLASS),
                    fromMined(100, Material.SAND), RARE))
            .add(new TrailSelection("charcoal", PROJECTILE_TRAIL, Material.CHARCOAL,
                    fromMined(2300, StarMaterial.OAK_LOG.find()), RARE))
            .add(new TrailSelection("polished_andesite", PROJECTILE_TRAIL, Material.POLISHED_ANDESITE,
                    fromMined(1000, Material.ANDESITE), RARE))
                    
            .add(new TrailSelection("sea_heart", PROJECTILE_TRAIL, Material.HEART_OF_THE_SEA,
                    fromKilled(400, EntityType.ELDER_GUARDIAN), EPIC))
            .add(new TrailSelection("scutes", PROJECTILE_TRAIL, Material.SCUTE,
                    fromStatistic(Statistic.ANIMALS_BRED, 500), EPIC))
            .add(new TrailSelection("sea_pickle", PROJECTILE_TRAIL, Material.SEA_PICKLE,
                    fromMined(500, Material.CLAY), EPIC))
            .add(new TrailSelection("turtle_egg", PROJECTILE_TRAIL, Material.TURTLE_EGG,
                    fromMined(650, Material.KELP), EPIC))

            .add(new TrailSelection("riptide", PROJECTILE_TRAIL, "riptide",
                    fromKilled(1000, EntityType.DROWNED), LEGENDARY))
            .add(new TrailSelection("blue_ice", PROJECTILE_TRAIL, Material.BLUE_ICE,
                    fromMined(685, Material.BLUE_ICE), LEGENDARY))

            .add(new TrailSelection("notch_apple", PROJECTILE_TRAIL, Material.ENCHANTED_GOLDEN_APPLE,
                    fromKilled(150, EntityType.WITHER), MYTHICAL))

            // Entities
            .add(new TrailSelection("salmon", PROJECTILE_TRAIL, EntityType.SALMON,
                    fromKilled(45, EntityType.SALMON), COMMON))

            .add(new TrailSelection("squid", PROJECTILE_TRAIL, EntityType.SQUID,
                    fromKilled(100, EntityType.SQUID), OCCASIONAL))
            .add(new TrailSelection("cod", PROJECTILE_TRAIL, EntityType.COD,
                    fromKilled(110, EntityType.COD), OCCASIONAL))


            .build();

    // Ground Trails
    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("oak_planks", GROUND_TRAIL, "ground_block:oak_planks",
                    fromMined(50, Material.OAK_LEAVES), COMMON))
            .add(new TrailSelection("birch_planks", GROUND_TRAIL, "ground_block:birch_planks",
                    fromMined(50, Material.BIRCH_LEAVES), COMMON))
            .add(new TrailSelection("spruce_planks", GROUND_TRAIL, "ground_block:spruce_planks",
                    fromCrafted(50, Material.SPRUCE_PLANKS), COMMON))
            .add(new TrailSelection("jungle_planks", GROUND_TRAIL, "ground_block:jungle_planks",
                    fromCrafted(50, Material.JUNGLE_PLANKS), COMMON))
            .add(new TrailSelection("acacia_planks", GROUND_TRAIL, "ground_block:acacia_planks",
                    fromCrafted(50, Material.DARK_OAK_PLANKS), COMMON))
            .add(new TrailSelection("dark_oak_planks", GROUND_TRAIL, "ground_block:dark_oak_planks",
                    fromCrafted(50, Material.DARK_OAK_PLANKS), COMMON))
            .add(new TrailSelection("bubble_coral", GROUND_TRAIL, "ground_block:bubble_coral_block",
                    fromKilled(35, EntityType.SALMON), COMMON))
            .add(new TrailSelection("potato", GROUND_TRAIL, Material.POTATO,
                    fromMined(50, Material.POTATOES), COMMON))

            .add(new TrailSelection("sea_pickle", GROUND_TRAIL, Material.SEA_PICKLE,
                    fromKilled(70, EntityType.COD), OCCASIONAL))
            .add(new TrailSelection("rail", GROUND_TRAIL, "side_block:rail",
                    fromCrafted(60, Material.RAIL), OCCASIONAL))

            .add(new TrailSelection("oak_sapling", GROUND_TRAIL, Material.OAK_SAPLING,
                    fromMined(75, Material.OAK_LEAVES), UNCOMMON))
            .add(new TrailSelection("birch_sapling", GROUND_TRAIL, Material.BIRCH_SAPLING,
                    fromMined(75, Material.BIRCH_LEAVES), UNCOMMON))
            .add(new TrailSelection("acacia_sapling", GROUND_TRAIL, Material.ACACIA_SAPLING,
                    fromMined(75, Material.ACACIA_LEAVES), UNCOMMON))
            .add(new TrailSelection("iron_pressure_plate", GROUND_TRAIL, "side_block:heavy_weighted_pressure_plate",
                    fromCrafted(30, Material.HEAVY_WEIGHTED_PRESSURE_PLATE), UNCOMMON))
            .add(new TrailSelection("pufferfish", GROUND_TRAIL, Material.PUFFERFISH,
                    fromKilled(10, EntityType.PUFFERFISH), UNCOMMON))

            .add(new TrailSelection("spruce_sapling", GROUND_TRAIL, Material.SPRUCE_SAPLING,
                    fromMined(75, Material.SPRUCE_LEAVES), UNCOMMON))
            .add(new TrailSelection("jungle_sapling", GROUND_TRAIL, Material.JUNGLE_SAPLING,
                    fromMined(75, Material.JUNGLE_LEAVES), RARE))
            .add(new TrailSelection("dark_oak_sapling", GROUND_TRAIL, Material.DARK_OAK_SAPLING,
                    fromMined(75, Material.DARK_OAK_LEAVES), RARE))
            .add(new TrailSelection("golden_pressure_plate", GROUND_TRAIL, "side_block:light_weighted_pressure_plate",
                    fromCrafted(30, Material.LIGHT_WEIGHTED_PRESSURE_PLATE), RARE))

            .add(new TrailSelection("heart_of_the_sea", GROUND_TRAIL, Material.HEART_OF_THE_SEA,
                    fromKilled(85, EntityType.ELDER_GUARDIAN), LEGENDARY))

            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("riptide", SOUND_TRAIL, StarSound.ITEM_TRIDENT_RIPTIDE_1.find(),
                    fromKilled(500, EntityType.DROWNED), EPIC))

            .build();

    // Shapes

    // Small Detailed Rings

    private static final List<CosmeticSelection<?>> SMALL_DETAILED_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("nautilus", SMALL_DETAILED_RING, Particle.NAUTILUS,
                    fromKilled(2650, EntityType.DROWNED), LEGENDARY))
            .build();

    // Large Rings

    private static final List<CosmeticSelection<?>> LARGE_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("kelp", LARGE_RING, Material.KELP,
                    fromMined(250, Material.KELP), UNCOMMON))
            .build();

    // Large Detailed Rings

    private static final List<CosmeticSelection<?>> LARGE_DETAILED_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("dried_kelp_block", LARGE_DETAILED_RING, Material.DRIED_KELP_BLOCK,
                    fromMined(250, Material.DRIED_KELP_BLOCK), UNCOMMON))
            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("oak_planks", Material.OAK_PLANKS,
                    fromCrafted(50, Material.OAK_PLANKS), COMMON))
            .add(new HatSelection("birch_planks", Material.BIRCH_PLANKS,
                    fromCrafted(50, Material.BIRCH_PLANKS), COMMON))
            .add(new HatSelection("spruce_planks", Material.SPRUCE_PLANKS,
                    fromCrafted(50, Material.SPRUCE_PLANKS), COMMON))
            .add(new HatSelection("jungle_planks", Material.JUNGLE_PLANKS,
                    fromCrafted(50, Material.JUNGLE_PLANKS), COMMON))
            .add(new HatSelection("acacia_planks", Material.ACACIA_PLANKS,
                    fromCrafted(50, Material.ACACIA_PLANKS), COMMON))
            .add(new HatSelection("dark_oak_planks", Material.DARK_OAK_PLANKS,
                    fromCrafted(50, Material.DARK_OAK_PLANKS), COMMON))
            .add(new HatSelection("andesite", Material.ANDESITE,
                    fromMined(45, Material.ANDESITE), COMMON))
            .add(new HatSelection("diorite", Material.DIORITE,
                    fromMined(45, Material.DIORITE), COMMON))
            .add(new HatSelection("granite", Material.GRANITE,
                    fromMined(45, Material.GRANITE), COMMON))

            .add(new HatSelection("dried_kelp_block", Material.DRIED_KELP_BLOCK,
                    fromMined(250, Material.DRIED_KELP_BLOCK), UNCOMMON))
            .add(new HatSelection("polished_andesite", Material.POLISHED_ANDESITE,
                    fromMined(120, Material.ANDESITE), UNCOMMON))
            .add(new HatSelection("polished_diorite", Material.POLISHED_DIORITE,
                    fromMined(120, Material.DIORITE), UNCOMMON))
            .add(new HatSelection("polished_granite", Material.POLISHED_GRANITE,
                    fromMined(120, Material.GRANITE), UNCOMMON))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, PROJECTILE_TRAIL, "1_12"))
            .put(GROUND_TRAIL, join(GROUND_TRAILS, GROUND_TRAIL, "1_12"))
            .put(SOUND_TRAIL, join(SOUND_TRAILS, SOUND_TRAIL, "1_12"))

            .put(SMALL_RING, getForVersion(SMALL_RING, "1_12"))
            .put(SMALL_DETAILED_RING, join(SMALL_DETAILED_RINGS, SMALL_DETAILED_RING, "1_12"))
            .put(LARGE_RING, join(LARGE_RINGS, LARGE_RING, "1_12"))
            .put(LARGE_DETAILED_RING, join(LARGE_DETAILED_RINGS, LARGE_DETAILED_RING, "1_12"))

            .put(NORMAL, join(NORMAL_HATS, NORMAL, "1_12"))
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
                                "Pufferfish", UNCOMMON,
                                petIcon("pufferfish_pet", "Pufferfish"), fromKilled(160, EntityType.COD), stand -> {
                                    if (r.nextInt(100) < 10) w.spawnFakeItem(new ItemStack(Material.PUFFERFISH), head(stand), 10);
                                }
                        ))

                        .put(PetType.WHALE, of(
                                "Whale", EPIC,
                                petIcon("whale_pet", "Whale"), fromMined(3000, Material.BRAIN_CORAL_BLOCK), stand -> {
                                    if (r.nextBoolean()) circle(head(stand), Particle.WATER_BUBBLE, 5, 0.5);
                                    if (r.nextInt(100) < 5) stand.getWorld().spawnParticle(Particle.WATER_SPLASH, head(stand), 3, 0.1, 0.1, 0.1, 0.1);
                                }
                        ))

                        .put(PetType.NARWHAL, of(
                                "Narwhal", LEGENDARY,
                                petIcon("narwhal_pet", "Narwhal"), fromKilled(2000, EntityType.DROWNED), stand -> {
                                    if (r.nextInt(100) < 10) circle(head(stand), Material.PRISMARINE, 5, 0.5);
                                    if (r.nextBoolean()) stand.getWorld().spawnParticle(Particle.DRIP_WATER, head(stand), 2, 0, 0, 0, 0.1);
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
