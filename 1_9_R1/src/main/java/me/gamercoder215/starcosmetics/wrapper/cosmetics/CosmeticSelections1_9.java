package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.StarSound;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
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

public final class CosmeticSelections1_9 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails

    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("red_flowers", BaseTrail.PROJECTILE_TRAIL, StarMaterial.POPPY.find(),
                    CompletionCriteria.fromMined(30, StarMaterial.POPPY.find()), Rarity.COMMON))
            .add(new TrailSelection("ghast", BaseTrail.PROJECTILE_TRAIL, Material.GHAST_TEAR,
                    CompletionCriteria.fromKilled(35, EntityType.GHAST), Rarity.COMMON))
            .add(new TrailSelection("wheat", BaseTrail.PROJECTILE_TRAIL, Material.WHEAT,
                    CompletionCriteria.fromMined(60, Material.WHEAT), Rarity.COMMON))
            .add(new TrailSelection("apples", BaseTrail.PROJECTILE_TRAIL, Material.APPLE,
                    CompletionCriteria.fromMined(70, StarMaterial.OAK_LOG.find()), Rarity.COMMON))
            .add(new TrailSelection("beef", BaseTrail.PROJECTILE_TRAIL, Material.COOKED_BEEF,
                    CompletionCriteria.fromKilled(55, EntityType.COW), Rarity.COMMON))
            .add(new TrailSelection("flint", BaseTrail.PROJECTILE_TRAIL, Material.FLINT,
                    CompletionCriteria.fromMined(80, Material.GRAVEL), Rarity.COMMON))
            .add(new TrailSelection("glass", BaseTrail.PROJECTILE_TRAIL, "fancy_block:glass",
                    CompletionCriteria.fromMined(100, Material.SAND), Rarity.COMMON))
            .add(new TrailSelection("stone_bricks", BaseTrail.PROJECTILE_TRAIL, "fancy_block:stone_bricks",
                    CompletionCriteria.fromMined(150, Material.STONE), Rarity.COMMON))
            
            
            .add(new TrailSelection("iron", BaseTrail.PROJECTILE_TRAIL, Material.IRON_INGOT,
                    CompletionCriteria.fromMined(185, Material.IRON_ORE), Rarity.OCCASIONAL))
            .add(new TrailSelection("redstone", BaseTrail.PROJECTILE_TRAIL, Material.REDSTONE,
                    CompletionCriteria.fromMined(145, Material.REDSTONE_ORE), Rarity.OCCASIONAL))
            .add(new TrailSelection("gold", BaseTrail.PROJECTILE_TRAIL, Material.GOLD_INGOT,
                    CompletionCriteria.fromMined(115, Material.GOLD_ORE), Rarity.OCCASIONAL))
            .add(new TrailSelection("ice",  BaseTrail.PROJECTILE_TRAIL, Material.ICE,
                    CompletionCriteria.fromMined(135, Material.ICE), Rarity.OCCASIONAL))

            .add(new TrailSelection("diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND,
                    CompletionCriteria.fromMined(110, Material.DIAMOND_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD,
                    CompletionCriteria.fromMined(90, Material.EMERALD_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("super_redstone", BaseTrail.PROJECTILE_TRAIL, Material.REDSTONE_BLOCK,
                    CompletionCriteria.fromMined(310, Material.REDSTONE_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("super_gold", BaseTrail.PROJECTILE_TRAIL, Material.GOLD_BLOCK,
                    CompletionCriteria.fromMined(255, Material.GOLD_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("ender_eye", BaseTrail.PROJECTILE_TRAIL, StarMaterial.ENDER_EYE.find(),
                    CompletionCriteria.fromKilled(250, EntityType.ENDERMAN), Rarity.UNCOMMON))
            .add(new TrailSelection("nether_brick", BaseTrail.PROJECTILE_TRAIL, Material.NETHER_BRICK,
                    CompletionCriteria.fromMined(500, Material.NETHERRACK), Rarity.UNCOMMON))
            .add(new TrailSelection("prismarine", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.PRISMARINE_CRYSTALS, Material.PRISMARINE_SHARD),
                    CompletionCriteria.fromMined(100, Material.PRISMARINE), Rarity.UNCOMMON))
            
            .add(new TrailSelection("super_diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND_BLOCK,
                    CompletionCriteria.fromMined(285, Material.DIAMOND_ORE), Rarity.RARE))
            .add(new TrailSelection("super_emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD_BLOCK,
                    CompletionCriteria.fromMined(230, Material.EMERALD_ORE), Rarity.RARE))
            .add(new TrailSelection("stone_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:stone_sword",
                    CompletionCriteria.fromMined(1200, Material.STONE, Material.COBBLESTONE), Rarity.RARE))
            .add(new TrailSelection("rabbits", BaseTrail.PROJECTILE_TRAIL, Material.RABBIT_FOOT,
                    CompletionCriteria.fromKilled(220, EntityType.RABBIT), Rarity.RARE))
            .add(new TrailSelection("magma_cream", BaseTrail.PROJECTILE_TRAIL, Material.MAGMA_CREAM,
                    CompletionCriteria.fromKilled(360, EntityType.MAGMA_CUBE), Rarity.RARE))
            .add(new TrailSelection("packed_ice",  BaseTrail.PROJECTILE_TRAIL, Material.PACKED_ICE,
                    CompletionCriteria.fromMined(260, Material.PACKED_ICE), Rarity.RARE))
            .add(new TrailSelection("experience_bottle", BaseTrail.PROJECTILE_TRAIL, StarMaterial.EXPERIENCE_BOTTLE.find(),
                    CompletionCriteria.fromStatistic(Statistic.ITEM_ENCHANTED, 450), Rarity.RARE))

            .add(new TrailSelection("chorus_fruit", BaseTrail.PROJECTILE_TRAIL, Material.CHORUS_FRUIT,
                    CompletionCriteria.fromMined(1000, StarMaterial.END_STONE.find()), Rarity.EPIC))
            .add(new TrailSelection("golden_carrot", BaseTrail.PROJECTILE_TRAIL, Material.GOLDEN_CARROT,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 1500), Rarity.EPIC))
            .add(new TrailSelection("ender_pearl", BaseTrail.PROJECTILE_TRAIL, Material.ENDER_PEARL,
                    CompletionCriteria.fromKilled(900, EntityType.ENDERMAN), Rarity.EPIC))
            .add(new TrailSelection("enchanted_book", BaseTrail.PROJECTILE_TRAIL, Material.ENCHANTED_BOOK,
                    CompletionCriteria.fromKilled(600, EntityType.WITCH), Rarity.EPIC))
            
            .add(new TrailSelection("diamond_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:diamond_sword",
                    CompletionCriteria.fromKilled(75, EntityType.WITHER), Rarity.LEGENDARY))
            .add(new TrailSelection("nether_star", BaseTrail.PROJECTILE_TRAIL, Material.NETHER_STAR,
                    CompletionCriteria.fromKilled(100, EntityType.WITHER), Rarity.LEGENDARY))
            .add(new TrailSelection("cookie", BaseTrail.PROJECTILE_TRAIL, Material.COOKIE,
                    CompletionCriteria.fromCrafted(2500, Material.COOKIE), Rarity.LEGENDARY))

            .add(new TrailSelection("ender_crystals", BaseTrail.PROJECTILE_TRAIL, Material.END_CRYSTAL,
                    CompletionCriteria.fromKilled(120, EntityType.ENDER_DRAGON), Rarity.MYTHICAL))
            .add(new TrailSelection("beacon", BaseTrail.PROJECTILE_TRAIL, "fancy_item:beacon",
                    CompletionCriteria.fromKilled(130, EntityType.WITHER), Rarity.MYTHICAL))
            
            .add(new TrailSelection("dragon_egg", BaseTrail.PROJECTILE_TRAIL, "fancy_block:dragon_egg",
                    CompletionCriteria.fromMined(165800, StarMaterial.END_STONE.find()), Rarity.ULTRA))
            .add(new TrailSelection("structure_block", BaseTrail.PROJECTILE_TRAIL, "fancy_block:structure_block",
                    CompletionCriteria.fromBlocksMined(500000), Rarity.ULTRA))
            
            .add(new TrailSelection("command_blocks", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(StarMaterial.COMMAND_BLOCK.find(), StarMaterial.CHAIN_COMMAND_BLOCK.find(), StarMaterial.REPEATING_COMMAND_BLOCK.find()),
                    CompletionCriteria.fromCompletion(PlayerCompletion.NETHER_ROOF), Rarity.SPECIAL))

            // Particles
            .add(new TrailSelection("heart", BaseTrail.PROJECTILE_TRAIL, Particle.HEART,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 15), Rarity.COMMON))

            .add(new TrailSelection("flame", BaseTrail.PROJECTILE_TRAIL, Particle.FLAME,
                    CompletionCriteria.fromKilled(65, EntityType.BLAZE), Rarity.OCCASIONAL))

            .add(new TrailSelection("lava", BaseTrail.PROJECTILE_TRAIL, Particle.LAVA,
                    CompletionCriteria.fromMined(400, Material.NETHERRACK), Rarity.UNCOMMON))
            .add(new TrailSelection("notes", BaseTrail.PROJECTILE_TRAIL, Particle.NOTE,
                    CompletionCriteria.fromCrafted(85, Material.NOTE_BLOCK, Material.JUKEBOX), Rarity.UNCOMMON))
            
            .add(new TrailSelection("enchantment", BaseTrail.PROJECTILE_TRAIL, Particle.ENCHANTMENT_TABLE,
                    CompletionCriteria.fromStatistic(Statistic.ITEM_ENCHANTED, 125), Rarity.RARE))
            .add(new TrailSelection("anger", BaseTrail.PROJECTILE_TRAIL, Particle.VILLAGER_ANGRY, 
                    CompletionCriteria.fromKilled(65, EntityType.IRON_GOLEM), Rarity.RARE))
            
            .add(new TrailSelection("dragon_breath", BaseTrail.PROJECTILE_TRAIL, Particle.DRAGON_BREATH,
                    CompletionCriteria.fromKilled(10, EntityType.ENDER_DRAGON), Rarity.EPIC))

            // Entities
            .add(new TrailSelection("chickens", BaseTrail.PROJECTILE_TRAIL, EntityType.CHICKEN,
                    CompletionCriteria.fromKilled(200, EntityType.CHICKEN), Rarity.OCCASIONAL))
            .add(new TrailSelection("pigs", BaseTrail.PROJECTILE_TRAIL, EntityType.PIG,
                    CompletionCriteria.fromKilled(200, EntityType.PIG), Rarity.OCCASIONAL))

            .add(new TrailSelection("endermite", BaseTrail.PROJECTILE_TRAIL, EntityType.ENDERMITE,
                    CompletionCriteria.fromKilled(150, EntityType.ENDERMITE), Rarity.RARE))

            .add(new TrailSelection("spider", BaseTrail.PROJECTILE_TRAIL, EntityType.SPIDER,
                    CompletionCriteria.fromKilled(500, EntityType.SPIDER), Rarity.EPIC))
            .add(new TrailSelection("guardian", BaseTrail.PROJECTILE_TRAIL, EntityType.GUARDIAN,
                    CompletionCriteria.fromKilled(300, EntityType.GUARDIAN), Rarity.EPIC))

            .add(new TrailSelection("shulker", BaseTrail.PROJECTILE_TRAIL, EntityType.SHULKER,
                    CompletionCriteria.fromMined(15000, StarMaterial.END_STONE.find()), Rarity.LEGENDARY))

            .build();
    
    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("stone", BaseTrail.GROUND_TRAIL, "ground_block:stone",
                    CompletionCriteria.fromMined(100, Material.STONE), Rarity.COMMON))
            .add(new TrailSelection("cobblestone", BaseTrail.GROUND_TRAIL, "ground_block:cobblestone",
                    CompletionCriteria.fromMined(100, Material.COBBLESTONE), Rarity.COMMON))
            .add(new TrailSelection("sand", BaseTrail.GROUND_TRAIL, "ground_block:sand",
                    CompletionCriteria.fromMined(125, Material.SAND), Rarity.COMMON))
            .add(new TrailSelection("gravel", BaseTrail.GROUND_TRAIL, "ground_block:gravel",
                    CompletionCriteria.fromCrafted(50, Material.ARROW), Rarity.COMMON))
            .add(new TrailSelection("hay_bale", BaseTrail.GROUND_TRAIL, "ground_block:hay_block",
                    CompletionCriteria.fromCrafted(60, Material.BREAD), Rarity.COMMON))

            .add(new TrailSelection("poppy", BaseTrail.GROUND_TRAIL, StarMaterial.POPPY.find(),
                    CompletionCriteria.fromMined(120, StarMaterial.POPPY.find()), Rarity.OCCASIONAL))
            .add(new TrailSelection("glass", BaseTrail.GROUND_TRAIL, "ground_block:glass",
                    CompletionCriteria.fromMined(340, Material.SAND), Rarity.OCCASIONAL))
            .add(new TrailSelection("coal_ore", BaseTrail.GROUND_TRAIL, "ground_block:coal_ore",
                    CompletionCriteria.fromMined(80, Material.COAL_ORE), Rarity.OCCASIONAL))

            .add(new TrailSelection("torch", BaseTrail.GROUND_TRAIL, Material.TORCH,
                    CompletionCriteria.fromMined(110, Material.COAL_ORE), Rarity.UNCOMMON))
    
            .add(new TrailSelection("lava", BaseTrail.GROUND_TRAIL, Particle.LAVA,
                    CompletionCriteria.fromKilled(525, EntityType.BLAZE), Rarity.RARE))
            .add(new TrailSelection("water", BaseTrail.GROUND_TRAIL, Particle.WATER_SPLASH,
                    CompletionCriteria.fromKilled(625, EntityType.SQUID), Rarity.RARE))
            .add(new TrailSelection("iron_ore", BaseTrail.GROUND_TRAIL, "ground_block:iron_ore",
                    CompletionCriteria.fromMined(250, Material.IRON_ORE), Rarity.RARE))
            .add(new TrailSelection("sponge", BaseTrail.GROUND_TRAIL, "ground_block:sponge",
                    CompletionCriteria.fromKilled(100, EntityType.GUARDIAN), Rarity.RARE))
            .add(new TrailSelection("anvil", BaseTrail.GROUND_TRAIL, Material.ANVIL,
                    CompletionCriteria.fromCrafted(50, Material.IRON_BLOCK), Rarity.RARE))

            .add(new TrailSelection("gold_ore", BaseTrail.GROUND_TRAIL, "ground_block:gold_ore",
                    CompletionCriteria.fromMined(320, Material.GOLD_ORE), Rarity.EPIC))

            .add(new TrailSelection("diamond_ore", BaseTrail.GROUND_TRAIL, "ground_block:diamond_ore",
                    CompletionCriteria.fromMined(475, Material.DIAMOND_ORE), Rarity.LEGENDARY))

            .add(new TrailSelection("emerald_ore", BaseTrail.GROUND_TRAIL, "ground_block:emerald_ore",
                    CompletionCriteria.fromMined(325, Material.EMERALD_ORE), Rarity.MYTHICAL))

            .add(new TrailSelection("bedrock", BaseTrail.GROUND_TRAIL, "ground_block:bedrock",
                    CompletionCriteria.fromMined(2000000, Material.OBSIDIAN), Rarity.ULTRA))
            
            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("netherrack", BaseTrail.SOUND_TRAIL, Material.NETHERRACK,
                    CompletionCriteria.fromMined(80, Material.NETHERRACK), Rarity.COMMON))

            .add(new TrailSelection("slime_place", BaseTrail.SOUND_TRAIL, StarSound.BLOCK_SLIME_PLACE.find(),
                    CompletionCriteria.fromKilled(100, EntityType.SLIME), Rarity.OCCASIONAL))
            .add(new TrailSelection("anvil_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_ANVIL_PLACE,
                    CompletionCriteria.fromKilled(30, EntityType.IRON_GOLEM), Rarity.OCCASIONAL))

            .add(new TrailSelection("bat_death", BaseTrail.SOUND_TRAIL, Sound.ENTITY_BAT_DEATH,
                    CompletionCriteria.fromKilled(40, EntityType.BAT), Rarity.RARE))

            .add(new TrailSelection("tnt_prime", BaseTrail.SOUND_TRAIL, Sound.ENTITY_TNT_PRIMED,
                    CompletionCriteria.fromKilled(550, EntityType.CREEPER), Rarity.EPIC))

            .build();

    // Shapes

    // Small Rings

    private static final List<CosmeticSelection<?>> SMALL_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("heart", BaseShape.SMALL_RING, Particle.HEART,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 15), Rarity.COMMON))

            .add(new ParticleSelection("flame", BaseShape.SMALL_RING, Particle.FLAME,
                    CompletionCriteria.fromKilled(80, EntityType.BLAZE), Rarity.OCCASIONAL))
            .build();

    // Small Detailed Rings
    private static final List<CosmeticSelection<?>> SMALL_DETAILED_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("lava", BaseShape.SMALL_DETAILED_RING, Particle.LAVA,
                    CompletionCriteria.fromMined(400, Material.NETHERRACK), Rarity.UNCOMMON))

            .build();

    // Large Rings

    private static final List<CosmeticSelection<?>> LARGE_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("note", BaseShape.LARGE_RING, Particle.NOTE,
                    CompletionCriteria.fromCrafted(200, Material.NOTE_BLOCK), Rarity.RARE))
            .build();

    // Large Detailed Rings

    private static final List<CosmeticSelection<?>> LARGE_DETAILED_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("enchantment", BaseShape.LARGE_DETAILED_RING, Particle.ENCHANTMENT_TABLE,
                    CompletionCriteria.fromStatistic(Statistic.ITEM_ENCHANTED, 385), Rarity.EPIC))
            .build();

    // Selection Map

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, PROJECTILE_TRAILS)
            .put(BaseTrail.GROUND_TRAIL, GROUND_TRAILS)
            .put(BaseTrail.SOUND_TRAIL, SOUND_TRAILS)

            .put(BaseShape.SMALL_RING, SMALL_RINGS)
            .put(BaseShape.SMALL_DETAILED_RING, SMALL_DETAILED_RINGS)
            .put(BaseShape.LARGE_RING, LARGE_RINGS)
            .put(BaseShape.LARGE_DETAILED_RING, LARGE_DETAILED_RINGS)
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
}
