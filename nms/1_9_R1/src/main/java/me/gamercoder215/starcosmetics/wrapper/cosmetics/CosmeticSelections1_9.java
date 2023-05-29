package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.util.selection.HatSelection.of;

final class CosmeticSelections1_9 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails

    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("red_flowers", BaseTrail.PROJECTILE_TRAIL, StarMaterial.POPPY.find(),
                    fromMined(30, StarMaterial.POPPY.find()), COMMON))
            .add(new TrailSelection("ghast", BaseTrail.PROJECTILE_TRAIL, Material.GHAST_TEAR,
                    fromKilled(35, EntityType.GHAST), COMMON))
            .add(new TrailSelection("wheat", BaseTrail.PROJECTILE_TRAIL, Material.WHEAT,
                    fromMined(60, Material.WHEAT), COMMON))
            .add(new TrailSelection("apples", BaseTrail.PROJECTILE_TRAIL, Material.APPLE,
                    fromMined(70, StarMaterial.OAK_LOG.find()), COMMON))
            .add(new TrailSelection("beef", BaseTrail.PROJECTILE_TRAIL, Material.COOKED_BEEF,
                    fromKilled(55, EntityType.COW), COMMON))
            .add(new TrailSelection("flint", BaseTrail.PROJECTILE_TRAIL, Material.FLINT,
                    fromMined(80, Material.GRAVEL), COMMON))
            .add(new TrailSelection("glass", BaseTrail.PROJECTILE_TRAIL, "fancy_block:glass",
                    fromMined(100, Material.SAND), COMMON))
            .add(new TrailSelection("stone_bricks", BaseTrail.PROJECTILE_TRAIL, "fancy_block:stone_bricks",
                    fromMined(150, Material.STONE), COMMON))
            .add(new TrailSelection("heart", BaseTrail.PROJECTILE_TRAIL, Particle.HEART,
                    fromStatistic(Statistic.ANIMALS_BRED, 15), COMMON))
            .add(new TrailSelection("egg", BaseTrail.PROJECTILE_TRAIL, Material.EGG,
                    fromKilled(20, EntityType.CHICKEN), COMMON))

            .add(new TrailSelection("iron", BaseTrail.PROJECTILE_TRAIL, Material.IRON_INGOT,
                    fromMined(185, Material.IRON_ORE), OCCASIONAL))
            .add(new TrailSelection("redstone", BaseTrail.PROJECTILE_TRAIL, Material.REDSTONE,
                    fromMined(145, Material.REDSTONE_ORE), OCCASIONAL))
            .add(new TrailSelection("gold", BaseTrail.PROJECTILE_TRAIL, Material.GOLD_INGOT,
                    fromMined(115, Material.GOLD_ORE), OCCASIONAL))
            .add(new TrailSelection("ice",  BaseTrail.PROJECTILE_TRAIL, Material.ICE,
                    fromMined(135, Material.ICE), OCCASIONAL))
            .add(new TrailSelection("flame", BaseTrail.PROJECTILE_TRAIL, Particle.FLAME,
                    fromKilled(65, EntityType.BLAZE), OCCASIONAL))
            .add(new TrailSelection("chickens", BaseTrail.PROJECTILE_TRAIL, EntityType.CHICKEN,
                    fromKilled(200, EntityType.CHICKEN), OCCASIONAL))
            .add(new TrailSelection("pigs", BaseTrail.PROJECTILE_TRAIL, EntityType.PIG,
                    fromKilled(200, EntityType.PIG), OCCASIONAL))
            .add(new TrailSelection("glowstone_dust", BaseTrail.PROJECTILE_TRAIL, Material.GLOWSTONE_DUST,
                    fromMined(100, Material.GLOWSTONE), OCCASIONAL))

            .add(new TrailSelection("diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND,
                    fromMined(110, Material.DIAMOND_ORE), UNCOMMON))
            .add(new TrailSelection("emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD,
                    fromMined(90, Material.EMERALD_ORE), UNCOMMON))
            .add(new TrailSelection("super_redstone", BaseTrail.PROJECTILE_TRAIL, Material.REDSTONE_BLOCK,
                    fromMined(310, Material.REDSTONE_ORE), UNCOMMON))
            .add(new TrailSelection("super_gold", BaseTrail.PROJECTILE_TRAIL, Material.GOLD_BLOCK,
                    fromMined(255, Material.GOLD_ORE), UNCOMMON))
            .add(new TrailSelection("ender_eye", BaseTrail.PROJECTILE_TRAIL, StarMaterial.ENDER_EYE.find(),
                    fromKilled(250, EntityType.ENDERMAN), UNCOMMON))
            .add(new TrailSelection("nether_brick", BaseTrail.PROJECTILE_TRAIL, Material.NETHER_BRICK,
                    fromMined(500, Material.NETHERRACK), UNCOMMON))
            .add(new TrailSelection("prismarine", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.PRISMARINE_CRYSTALS, Material.PRISMARINE_SHARD),
                    fromMined(100, Material.PRISMARINE), UNCOMMON))
            .add(new TrailSelection("lava", BaseTrail.PROJECTILE_TRAIL, Particle.LAVA,
                    fromMined(400, Material.NETHERRACK), UNCOMMON))
            .add(new TrailSelection("notes", BaseTrail.PROJECTILE_TRAIL, Particle.NOTE,
                    fromCrafted(85, Material.NOTE_BLOCK, Material.JUKEBOX), UNCOMMON))
            
            .add(new TrailSelection("super_diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND_BLOCK,
                    fromMined(285, Material.DIAMOND_ORE), RARE))
            .add(new TrailSelection("super_emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD_BLOCK,
                    fromMined(230, Material.EMERALD_ORE), RARE))
            .add(new TrailSelection("stone_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:stone_sword",
                    fromMined(1200, Material.STONE, Material.COBBLESTONE), RARE))
            .add(new TrailSelection("rabbits", BaseTrail.PROJECTILE_TRAIL, Material.RABBIT_FOOT,
                    fromKilled(220, EntityType.RABBIT), RARE))
            .add(new TrailSelection("magma_cream", BaseTrail.PROJECTILE_TRAIL, Material.MAGMA_CREAM,
                    fromKilled(360, EntityType.MAGMA_CUBE), RARE))
            .add(new TrailSelection("packed_ice",  BaseTrail.PROJECTILE_TRAIL, Material.PACKED_ICE,
                    fromMined(260, Material.PACKED_ICE), RARE))
            .add(new TrailSelection("experience_bottle", BaseTrail.PROJECTILE_TRAIL, StarMaterial.EXPERIENCE_BOTTLE.find(),
                    fromStatistic(Statistic.ITEM_ENCHANTED, 450), RARE))
            .add(new TrailSelection("enchantment", BaseTrail.PROJECTILE_TRAIL, Particle.ENCHANTMENT_TABLE,
                    fromStatistic(Statistic.ITEM_ENCHANTED, 125), RARE))
            .add(new TrailSelection("anger", BaseTrail.PROJECTILE_TRAIL, Particle.VILLAGER_ANGRY,
                    fromKilled(65, EntityType.IRON_GOLEM), RARE))
            .add(new TrailSelection("endermite", BaseTrail.PROJECTILE_TRAIL, EntityType.ENDERMITE,
                    fromKilled(150, EntityType.ENDERMITE), RARE))
            .add(new TrailSelection("quartz_block", BaseTrail.PROJECTILE_TRAIL, "fancy_block:quartz_block",
                    fromMined(220, StarMaterial.NETHER_QUARTZ_ORE.find()), RARE))

            .add(new TrailSelection("chorus_fruit", BaseTrail.PROJECTILE_TRAIL, Material.CHORUS_FRUIT,
                    fromMined(1000, StarMaterial.END_STONE.find()), EPIC))
            .add(new TrailSelection("golden_carrot", BaseTrail.PROJECTILE_TRAIL, Material.GOLDEN_CARROT,
                    fromStatistic(Statistic.ANIMALS_BRED, 1100), EPIC))
            .add(new TrailSelection("ender_pearl", BaseTrail.PROJECTILE_TRAIL, Material.ENDER_PEARL,
                    fromKilled(900, EntityType.ENDERMAN), EPIC))
            .add(new TrailSelection("enchanted_book", BaseTrail.PROJECTILE_TRAIL, Material.ENCHANTED_BOOK,
                    fromKilled(600, EntityType.WITCH), EPIC))
            .add(new TrailSelection("spider", BaseTrail.PROJECTILE_TRAIL, EntityType.SPIDER,
                    fromKilled(500, EntityType.SPIDER), EPIC))
            .add(new TrailSelection("guardian", BaseTrail.PROJECTILE_TRAIL, EntityType.GUARDIAN,
                    fromKilled(300, EntityType.GUARDIAN), EPIC))
            .add(new TrailSelection("dragon_breath", BaseTrail.PROJECTILE_TRAIL, Particle.DRAGON_BREATH,
                    fromKilled(10, EntityType.ENDER_DRAGON), EPIC))
            .add(new TrailSelection("boat", BaseTrail.PROJECTILE_TRAIL, EntityType.BOAT,
                    fromCrafted(270, StarMaterial.OAK_BOAT.find()), EPIC))
            
            .add(new TrailSelection("diamond_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:diamond_sword",
                    fromKilled(75, EntityType.WITHER), LEGENDARY))
            .add(new TrailSelection("nether_star", BaseTrail.PROJECTILE_TRAIL, Material.NETHER_STAR,
                    fromKilled(100, EntityType.WITHER), LEGENDARY))
            .add(new TrailSelection("cookie", BaseTrail.PROJECTILE_TRAIL, Material.COOKIE,
                    fromCrafted(2500, Material.COOKIE), LEGENDARY))
            .add(new TrailSelection("shulker", BaseTrail.PROJECTILE_TRAIL, EntityType.SHULKER,
                    fromMined(15000, StarMaterial.END_STONE.find()), LEGENDARY))

            .add(new TrailSelection("ender_crystals", BaseTrail.PROJECTILE_TRAIL, Material.END_CRYSTAL,
                    fromKilled(120, EntityType.ENDER_DRAGON), MYTHICAL))
            .add(new TrailSelection("beacon", BaseTrail.PROJECTILE_TRAIL, "fancy_item:beacon",
                    fromKilled(130, EntityType.WITHER), MYTHICAL))
            .add(new TrailSelection("spawner", BaseTrail.PROJECTILE_TRAIL, "fancy_block:spawner",
                    fromStatistic(Statistic.MOB_KILLS, 1000000), MYTHICAL))
            
            .add(new TrailSelection("dragon_egg", BaseTrail.PROJECTILE_TRAIL, "fancy_block:dragon_egg",
                    fromMined(165800, StarMaterial.END_STONE.find()), ULTRA))
            .add(new TrailSelection("structure_block", BaseTrail.PROJECTILE_TRAIL, "fancy_block:structure_block",
                    fromBlocksMined(500000), ULTRA))
            
            .add(new TrailSelection("command_blocks", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(StarMaterial.COMMAND_BLOCK.find(), StarMaterial.CHAIN_COMMAND_BLOCK.find(), StarMaterial.REPEATING_COMMAND_BLOCK.find()),
                    fromCompletion(PlayerCompletion.NETHER_ROOF), SPECIAL))

            .build();
    
    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("stone", BaseTrail.GROUND_TRAIL, "ground_block:stone",
                    fromMined(100, Material.STONE), COMMON))
            .add(new TrailSelection("cobblestone", BaseTrail.GROUND_TRAIL, "ground_block:cobblestone",
                    fromMined(100, Material.COBBLESTONE), COMMON))
            .add(new TrailSelection("sand", BaseTrail.GROUND_TRAIL, "ground_block:sand",
                    fromMined(125, Material.SAND), COMMON))
            .add(new TrailSelection("gravel", BaseTrail.GROUND_TRAIL, "ground_block:gravel",
                    fromCrafted(50, Material.ARROW), COMMON))
            .add(new TrailSelection("hay_bale", BaseTrail.GROUND_TRAIL, "ground_block:hay_block",
                    fromCrafted(60, Material.BREAD), COMMON))

            .add(new TrailSelection("poppy", BaseTrail.GROUND_TRAIL, StarMaterial.POPPY.find(),
                    fromMined(120, StarMaterial.POPPY.find()), OCCASIONAL))
            .add(new TrailSelection("glass", BaseTrail.GROUND_TRAIL, "ground_block:glass",
                    fromMined(340, Material.SAND), OCCASIONAL))
            .add(new TrailSelection("coal_ore", BaseTrail.GROUND_TRAIL, "ground_block:coal_ore",
                    fromMined(80, Material.COAL_ORE), OCCASIONAL))
            .add(new TrailSelection("clay", BaseTrail.GROUND_TRAIL, "ground_block:clay",
                    fromMined(75, Material.CLAY), OCCASIONAL))

            .add(new TrailSelection("torch", BaseTrail.GROUND_TRAIL, Material.TORCH,
                    fromMined(110, Material.COAL_ORE), UNCOMMON))
            .add(new TrailSelection("blaze_rod", BaseTrail.GROUND_TRAIL, Material.BLAZE_ROD,
                    fromKilled(65, EntityType.BLAZE), UNCOMMON))
            .add(new TrailSelection("purpur_slab", BaseTrail.GROUND_TRAIL, "side_block:purpur_slab",
                    fromMined(155, Material.PURPUR_BLOCK), UNCOMMON))
            .add(new TrailSelection("nether_quartz", BaseTrail.GROUND_TRAIL, Material.QUARTZ,
                    fromMined(100, StarMaterial.NETHER_QUARTZ_ORE.find()), UNCOMMON))
            .add(new TrailSelection("golden_nugget", BaseTrail.GROUND_TRAIL, Material.GOLD_NUGGET,
                    fromMined(70, Material.GOLD_ORE), UNCOMMON))
            
            .add(new TrailSelection("lava", BaseTrail.GROUND_TRAIL, Particle.LAVA,
                    fromKilled(525, EntityType.BLAZE), RARE))
            .add(new TrailSelection("water", BaseTrail.GROUND_TRAIL, Particle.WATER_SPLASH,
                    fromKilled(625, EntityType.SQUID), RARE))
            .add(new TrailSelection("iron_ore", BaseTrail.GROUND_TRAIL, "ground_block:iron_ore",
                    fromMined(250, Material.IRON_ORE), RARE))
            .add(new TrailSelection("sponge", BaseTrail.GROUND_TRAIL, "ground_block:sponge",
                    fromKilled(100, EntityType.GUARDIAN), RARE))
            .add(new TrailSelection("anvil", BaseTrail.GROUND_TRAIL, Material.ANVIL,
                    fromCrafted(50, Material.IRON_BLOCK), RARE))

            .add(new TrailSelection("gold_ore", BaseTrail.GROUND_TRAIL, "ground_block:gold_ore",
                    fromMined(320, Material.GOLD_ORE), EPIC))

            .add(new TrailSelection("diamond_ore", BaseTrail.GROUND_TRAIL, "ground_block:diamond_ore",
                    fromMined(475, Material.DIAMOND_ORE), LEGENDARY))

            .add(new TrailSelection("emerald_ore", BaseTrail.GROUND_TRAIL, "ground_block:emerald_ore",
                    fromMined(325, Material.EMERALD_ORE), MYTHICAL))

            .add(new TrailSelection("bedrock", BaseTrail.GROUND_TRAIL, "ground_block:bedrock",
                    fromMined(2000000, Material.OBSIDIAN), ULTRA))

            .add(new TrailSelection("barrier", BaseTrail.GROUND_TRAIL, "ground_block:barrier",
                    fromBlocksMined(9000000), SPECIAL))
            
            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("log_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_WOOD_PLACE,
                    fromMined(30, StarMaterial.OAK_LOG.find()), COMMON))
            .add(new TrailSelection("stone_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_STONE_PLACE,
                    fromMined(35, Material.STONE), COMMON))

            .add(new TrailSelection("slime_place", BaseTrail.SOUND_TRAIL, StarSound.BLOCK_SLIME_PLACE.find(),
                    fromKilled(100, EntityType.SLIME), OCCASIONAL))
            .add(new TrailSelection("anvil_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_ANVIL_PLACE,
                    fromKilled(30, EntityType.IRON_GOLEM), OCCASIONAL))

            .add(new TrailSelection("attack", BaseTrail.SOUND_TRAIL, Sound.ENTITY_PLAYER_ATTACK_SWEEP,
                    fromKilled(70, EntityType.ZOMBIE), UNCOMMON))

            .add(new TrailSelection("bat_death", BaseTrail.SOUND_TRAIL, Sound.ENTITY_BAT_DEATH,
                    fromKilled(40, EntityType.BAT), RARE))
            .add(new TrailSelection("blaze_shoot", BaseTrail.SOUND_TRAIL, Sound.ENTITY_BLAZE_SHOOT,
                    fromKilled(85, EntityType.BLAZE), RARE))

            .add(new TrailSelection("tnt_prime", BaseTrail.SOUND_TRAIL, Sound.ENTITY_TNT_PRIMED,
                    fromKilled(550, EntityType.CREEPER), EPIC))

            .build();

    // Shapes

    private static final List<CosmeticSelection<?>> PARTICLE_SHAPES = ImmutableList.<CosmeticSelection<?>>builder()
            // Small Rings
            .add(new ParticleSelection("heart", BaseShape.SMALL_RING, Particle.HEART,
                    fromStatistic(Statistic.ANIMALS_BRED, 15), COMMON))
            .add(new ParticleSelection("apple", BaseShape.SMALL_RING, Material.APPLE,
                    fromMined(50, StarMaterial.OAK_LOG.find()), COMMON))

            .add(new ParticleSelection("flame", BaseShape.SMALL_RING, Particle.FLAME,
                    fromKilled(80, EntityType.BLAZE), OCCASIONAL))
            .add(new ParticleSelection("coal", BaseShape.SMALL_RING, Material.COAL,
                    fromCrafted(30, Material.COAL_BLOCK), OCCASIONAL))

            .add(new ParticleSelection("iron", BaseShape.SMALL_RING, Material.IRON_INGOT,
                    fromCrafted(30, Material.IRON_BLOCK), UNCOMMON))
            .add(new ParticleSelection("cactus", BaseShape.SMALL_RING, Material.CACTUS,
                    fromMined(90, Material.CACTUS), UNCOMMON))

            .add(new ParticleSelection("gold", BaseShape.SMALL_RING, Material.GOLD_INGOT,
                    fromCrafted(30, Material.GOLD_BLOCK), RARE))

            .add(new ParticleSelection("diamond", BaseShape.SMALL_RING, Material.DIAMOND,
                    fromCrafted(30, Material.DIAMOND_BLOCK), EPIC))

            .add(new ParticleSelection("emerald", BaseShape.SMALL_RING, Material.EMERALD,
                    fromCrafted(30, Material.EMERALD_BLOCK), LEGENDARY))

            // Small Detailed Rings
            .add(new ParticleSelection("stone", BaseShape.SMALL_DETAILED_RING, Material.STONE,
                    fromMined(30, Material.STONE), COMMON))

            .add(new ParticleSelection("lava", BaseShape.SMALL_DETAILED_RING, Particle.LAVA,
                    fromMined(400, Material.NETHERRACK), UNCOMMON))
            .add(new ParticleSelection("oak_log", BaseShape.SMALL_DETAILED_RING, StarMaterial.OAK_LOG.find(),
                    fromMined(100, StarMaterial.OAK_LOG.find()), UNCOMMON))

            .add(new ParticleSelection("snow", BaseShape.SMALL_DETAILED_RING, Particle.SNOW_SHOVEL,
                    fromKilled(100, EntityType.SNOWMAN), RARE))

            .add(new ParticleSelection("barrier", BaseShape.SMALL_DETAILED_RING, Material.BARRIER,
                    fromBlocksMined(750000), SPECIAL))

            // Large Rings
            .add(new ParticleSelection("sand", BaseShape.LARGE_RING, Material.SAND,
                    fromMined(80, Material.SAND), COMMON))

            .add(new ParticleSelection("note", BaseShape.LARGE_RING, Particle.NOTE,
                    fromCrafted(200, Material.NOTE_BLOCK), RARE))

            // Large Detailed Rings
            .add(new ParticleSelection("soul_sand", BaseShape.LARGE_DETAILED_RING, Material.SOUL_SAND,
                    fromMined(90, Material.SOUL_SAND), OCCASIONAL))
            .add(new ParticleSelection("end_stone", BaseShape.LARGE_DETAILED_RING, StarMaterial.END_STONE.find(),
                    fromMined(110, StarMaterial.END_STONE.find()), OCCASIONAL))

            .add(new ParticleSelection("enchantment", BaseShape.LARGE_DETAILED_RING, Particle.ENCHANTMENT_TABLE,
                    fromStatistic(Statistic.ITEM_ENCHANTED, 385), EPIC))

            // Small Triangles
            .add(new ParticleSelection("glass", BaseShape.SMALL_TRIANGLE, Material.GLASS,
                    fromCrafted(105, Material.GLASS), COMMON))

            .add(new ParticleSelection("quartz_block", BaseShape.SMALL_TRIANGLE, Material.QUARTZ_BLOCK,
                    fromCrafted(75, Material.QUARTZ_BLOCK), OCCASIONAL))

            // Medium Triangles
            .add(new ParticleSelection("water", BaseShape.MEDIUM_TRIANGLE, Particle.WATER_SPLASH,
                    fromCrafted(85, Material.WATER_BUCKET), OCCASIONAL))

            .add(new ParticleSelection("endstone", BaseShape.MEDIUM_TRIANGLE, StarMaterial.END_STONE.find(),
                    fromMined(310, StarMaterial.END_STONE.find()), UNCOMMON))

            // Large Triangles
            .add(new ParticleSelection("gravel", BaseShape.LARGE_TRIANGLE, Material.GRAVEL,
                    fromMined(95, Material.GRAVEL), UNCOMMON))

            .add(new ParticleSelection("anvil", BaseShape.LARGE_TRIANGLE, Material.ANVIL,
                    fromCrafted(160, Material.ANVIL), RARE))

            // Large Detailed Triangles
            .add(new ParticleSelection("seeds", BaseShape.LARGE_DETAILED_TRIANGLE, StarMaterial.WHEAT_SEEDS.find(),
                    fromMined(100, Material.HAY_BLOCK), UNCOMMON))

            .add(new ParticleSelection("string", BaseShape.LARGE_DETAILED_TRIANGLE, Material.STRING,
                    fromMined(350, StarMaterial.COBWEB.find()), RARE))

            // Small Squares
            .add(new ParticleSelection("cobblestone", BaseShape.SMALL_SQUARE, Material.COBBLESTONE,
                    fromMined(25, Material.COBBLESTONE), COMMON))
            .add(new ParticleSelection("netherrack", BaseShape.SMALL_SQUARE, Material.NETHERRACK,
                    fromMined(65, Material.NETHERRACK), COMMON))

            // Large Squares
            .add(new ParticleSelection("pumpkin_seeds", BaseShape.LARGE_SQUARE, Material.PUMPKIN_SEEDS,
                    fromMined(140, Material.PUMPKIN), UNCOMMON))

            .add(new ParticleSelection("redstone", BaseShape.LARGE_SQUARE, Material.REDSTONE,
                    fromCrafted(400, Material.REDSTONE_BLOCK), EPIC))

            // Pentagons
            .add(new ParticleSelection("crit", BaseShape.PENTAGON, Particle.CRIT,
                    fromStatistic(Statistic.MOB_KILLS, 7800), RARE))

            // Detailed Pentagons
            .add(new ParticleSelection("crit_magic", BaseShape.DETAILED_PENTAGON, Particle.CRIT_MAGIC,
                    fromStatistic(Statistic.MOB_KILLS, 17500), LEGENDARY))

            // Combinations
            .add(new ParticleSelection("snowball", BaseShape.SQUARE_RING, Particle.SNOWBALL,
                    fromKilled(85, EntityType.SNOWMAN), UNCOMMON))
        
            .add(new ParticleSelection("end_portal_frame", BaseShape.PENTAGON_RING, StarMaterial.END_PORTAL_FRAME.find(),
                    fromKilled(75000, EntityType.ENDERMITE), MYTHICAL))

            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("pumpkin", Material.PUMPKIN,
                    fromMined(15, Material.PUMPKIN), COMMON))
            .add(new HatSelection("grass_block", StarMaterial.GRASS_BLOCK.find(),
                    fromMined(25, StarMaterial.GRASS_BLOCK.find()), COMMON))
            .add(new HatSelection("oak_log", StarMaterial.OAK_LOG.find(),
                    fromMined(30, StarMaterial.OAK_LOG.find()), COMMON))
            .add(new HatSelection("stone", Material.STONE,
                    fromMined(40, Material.STONE), COMMON))

            .add(new HatSelection("spider", "MHF_Spider",
                    fromKilled(50, EntityType.SPIDER), OCCASIONAL))
            .add(new HatSelection("pig", "MHF_Pig",
                    fromStatistic(Statistic.ANIMALS_BRED, 35), OCCASIONAL))
            .add(new HatSelection("oak_fence", StarMaterial.OAK_FENCE.find(),
                    fromCrafted(30, StarMaterial.OAK_FENCE.find()), OCCASIONAL))
            .add(new HatSelection("spruce_fence", Material.SPRUCE_FENCE,
                    fromCrafted(30, Material.SPRUCE_FENCE), OCCASIONAL))
            .add(new HatSelection("chest", Material.CHEST,
                    fromCrafted(35, Material.CHEST), OCCASIONAL))
            .add(new HatSelection("dropper", Material.DROPPER,
                    fromCrafted(65, Material.DROPPER), OCCASIONAL))
            .add(new HatSelection("dispenser", Material.DISPENSER,
                    fromCrafted(65, Material.DISPENSER), OCCASIONAL))

            .add(new HatSelection("slime", "MHF_Slime",
                    fromKilled(85, EntityType.SLIME), UNCOMMON))
            .add(new HatSelection("brown_coconut", "MHF_CoconutB",
                    fromMined(135, StarMaterial.JUNGLE_LOG.find()), UNCOMMON))
            .add(new HatSelection("iron_golem", "MHF_Golem",
                    fromKilled(20, EntityType.IRON_GOLEM), UNCOMMON))
            .add(new HatSelection("sheep", "MHF_Sheep",
                    fromStatistic(Statistic.ANIMALS_BRED, 60), UNCOMMON))
            .add(new HatSelection("oak_fence_gate", StarMaterial.OAK_FENCE_GATE.find(),
                    fromCrafted(75, StarMaterial.OAK_FENCE_GATE.find()), UNCOMMON))
            .add(new HatSelection("spruce_fence_gate", Material.SPRUCE_FENCE_GATE,
                    fromCrafted(75, Material.SPRUCE_FENCE_GATE), UNCOMMON))
            .add(new HatSelection("acacia_fence", Material.ACACIA_FENCE,
                    fromCrafted(75, Material.ACACIA_FENCE), UNCOMMON))
            .add(new HatSelection("birch_fence", Material.BIRCH_FENCE,
                    fromCrafted(75, Material.BIRCH_FENCE), UNCOMMON))
            .add(new HatSelection("furnace", Material.FURNACE,
                    fromCrafted(90, Material.FURNACE), UNCOMMON))

            .add(new HatSelection("enderman", "MHF_Enderman",
                    fromKilled(100, EntityType.ENDERMAN), RARE))
            .add(new HatSelection("blaze", "MHF_Blaze",
                    fromKilled(135, EntityType.BLAZE), RARE))
            .add(new HatSelection("green_coconut", "MHF_CoconutG",
                    fromMined(285, StarMaterial.ACACIA_LOG.find()), RARE))
            .add(new HatSelection("astronaut", Material.GLASS,
                    fromDistance(Statistic.AVIATE_ONE_CM, 100000000), RARE))
            .add(new HatSelection("ocelot", "MHF_Ocelot",
                    fromStatistic(Statistic.ANIMALS_BRED, 115), RARE))
            .add(new HatSelection("acacia_fence_gate", Material.ACACIA_FENCE_GATE,
                    fromCrafted(250, Material.ACACIA_FENCE_GATE), RARE))
            .add(new HatSelection("birch_fence_gate", Material.BIRCH_FENCE_GATE,
                    fromCrafted(250, Material.BIRCH_FENCE_GATE), RARE))
            .add(new HatSelection("jungle_fence", Material.JUNGLE_FENCE,
                    fromCrafted(250, Material.JUNGLE_FENCE), RARE))
            .add(new HatSelection("iron_trapdoor", Material.IRON_TRAPDOOR,
                    fromCrafted(225, Material.IRON_TRAPDOOR), RARE))
            .add(new HatSelection("iron_block", Material.IRON_BLOCK,
                    fromMined(100, Material.IRON_ORE), RARE))

            .add(new HatSelection("creeper", "MHF_Creeper",
                    fromKilled(415, EntityType.CREEPER), EPIC))
            .add(new HatSelection("tnt", "MHF_TNT",
                    fromCrafted(375, Material.TNT), EPIC))
            .add(new HatSelection("jungle_fence_gate", Material.JUNGLE_FENCE_GATE,
                    fromCrafted(680, Material.JUNGLE_FENCE_GATE), EPIC))
            .add(new HatSelection("dark_oak_fence", Material.DARK_OAK_FENCE,
                    fromCrafted(680, Material.DARK_OAK_FENCE), EPIC))
            .add(new HatSelection("gold_block", Material.GOLD_BLOCK,
                    fromMined(125, Material.GOLD_ORE), EPIC))

            .add(new HatSelection("ghast", "MHF_Ghast",
                    fromKilled(360, EntityType.GHAST), LEGENDARY))
            .add(new HatSelection("dark_oak_fence_gate", Material.DARK_OAK_FENCE_GATE,
                    fromCrafted(1450, Material.DARK_OAK_FENCE_GATE), LEGENDARY))
            .add(new HatSelection("diamond_block", Material.DIAMOND_BLOCK,
                    fromMined(200, Material.DIAMOND_ORE), LEGENDARY))
            .add(new HatSelection("emerald_block", Material.EMERALD_BLOCK,
                    fromMined(180, Material.EMERALD_ORE), LEGENDARY))

            .add(new HatSelection("spawner", StarMaterial.SPAWNER.find(),
                    fromStatistic(Statistic.MOB_KILLS, 500000), MYTHICAL))
            .add(new HatSelection("end_portal_frame", StarMaterial.END_PORTAL_FRAME.find(),
                    fromKilled(100000, EntityType.ENDERMITE), MYTHICAL))

            .build();

    // Animated Hats

    private static final List<CosmeticSelection<?>> ANIMATED_HATS = ImmutableList.<CosmeticSelection<?>>builder()
        .add(new HatSelection("ores", of(30,
                        Material.COAL_ORE, Material.matchMaterial("COPPER_ORE"), Material.IRON_ORE,
                        Material.LAPIS_ORE, Material.REDSTONE_ORE, Material.GOLD_ORE,
                        Material.DIAMOND_ORE, Material.EMERALD_ORE
                ), fromMined(255, Material.COAL_ORE), RARE))
        
        .add(new HatSelection("ore_blocks", of(40,
                        Material.COAL_BLOCK, Material.matchMaterial("COPPER_BLOCK"), Material.IRON_BLOCK,
                        Material.LAPIS_BLOCK, Material.REDSTONE_BLOCK, Material.GOLD_BLOCK,
                        Material.DIAMOND_BLOCK, Material.EMERALD_BLOCK
                ), fromCrafted(565, Material.IRON_BLOCK), EPIC))
        
        .build();
        
    // Selection Map

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, PROJECTILE_TRAILS)
            .put(BaseTrail.GROUND_TRAIL, GROUND_TRAILS)
            .put(BaseTrail.SOUND_TRAIL, SOUND_TRAILS)

            .put(BaseShape.ALL, PARTICLE_SHAPES)

            .put(BaseHat.NORMAL, NORMAL_HATS)
            .put(BaseHat.ANIMATED, ANIMATED_HATS)
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
}
