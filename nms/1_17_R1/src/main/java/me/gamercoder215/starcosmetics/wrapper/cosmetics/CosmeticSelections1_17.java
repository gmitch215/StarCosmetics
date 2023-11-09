package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
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

final class CosmeticSelections1_17 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("deepslate", BaseTrail.PROJECTILE_TRAIL, Material.DEEPSLATE,
                    fromMined(370, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), Rarity.OCCASIONAL))
            .add(new TrailSelection("dripstone", BaseTrail.PROJECTILE_TRAIL, "fancy_block:dripstone_block",
                    fromMined(280, Material.DRIPSTONE_BLOCK), Rarity.OCCASIONAL))

            .add(new TrailSelection("copper", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.COPPER_BLOCK, Material.WEATHERED_COPPER, Material.EXPOSED_COPPER, Material.OXIDIZED_COPPER),
                    fromMined(340, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("glow_berries", BaseTrail.PROJECTILE_TRAIL, Material.GLOW_BERRIES,
                    fromMined(200, Material.GLOW_LICHEN), Rarity.UNCOMMON))

            .add(new TrailSelection("raw_ores", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.RAW_IRON, Material.RAW_GOLD, Material.RAW_COPPER),
                    fromMined(500, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), Rarity.RARE))
            .add(new TrailSelection("candle", BaseTrail.PROJECTILE_TRAIL, Material.CANDLE,
                    fromCrafted(135, Material.CANDLE), Rarity.RARE))
            .add(new TrailSelection("copper_ingot", BaseTrail.PROJECTILE_TRAIL, Material.COPPER_INGOT,
                    fromMined(350, Material.COPPER_ORE), Rarity.RARE))
            
            .add(new TrailSelection("amethyst", BaseTrail.PROJECTILE_TRAIL, Material.AMETHYST_SHARD,
                    fromMined(2000, Material.AMETHYST_BLOCK), Rarity.EPIC))

            .add(new TrailSelection("super_amethyst", BaseTrail.PROJECTILE_TRAIL, Material.AMETHYST_BLOCK,
                    fromMined(5000, Material.AMETHYST_BLOCK), Rarity.LEGENDARY))
            
            
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("deepslate", BaseTrail.GROUND_TRAIL, "ground_block:deepslate",
                    fromMined(75, Material.DEEPSLATE), Rarity.OCCASIONAL))

            .add(new TrailSelection("copper_ore", BaseTrail.GROUND_TRAIL, "ground_block:copper_ore",
                    fromMined(250, Material.COPPER_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("tinted_glass", BaseTrail.GROUND_TRAIL, "ground_block:tinted_glass",
                    fromMined(140, Material.AMETHYST_BLOCK), Rarity.UNCOMMON))
            .add(new TrailSelection("glow_berries", BaseTrail.GROUND_TRAIL, Material.GLOW_BERRIES,
                    fromMined(200, Material.GLOW_LICHEN), Rarity.UNCOMMON))

            .add(new TrailSelection("raw_iron", BaseTrail.GROUND_TRAIL, "ground_block:raw_iron",
                    fromMined(100, Material.DEEPSLATE_IRON_ORE), Rarity.RARE))
            .add(new TrailSelection("calcite", BaseTrail.GROUND_TRAIL, "crack:calcite",
                    fromMined(125, Material.CALCITE), Rarity.RARE))
            .add(new TrailSelection("oxidized_copper", BaseTrail.GROUND_TRAIL, "ground_block:oxidized_copper",
                    fromMined(235, Material.COPPER_ORE), Rarity.RARE))
            .add(new TrailSelection("deepslate_brick_slab", BaseTrail.GROUND_TRAIL, "side_block:deepslate_brick_slab",
                    fromMined(265, Material.DEEPSLATE), Rarity.RARE))
            .add(new TrailSelection("candle", BaseTrail.GROUND_TRAIL, Material.CANDLE,
                    fromCrafted(115, Material.CANDLE), Rarity.RARE))

            .add(new TrailSelection("amethyst", BaseTrail.GROUND_TRAIL, Material.AMETHYST_SHARD,
                    fromMined(600, Material.AMETHYST_BLOCK), Rarity.EPIC))
            .add(new TrailSelection("raw_gold", BaseTrail.GROUND_TRAIL, "ground_block:raw_gold",
                    fromMined(100, Material.DEEPSLATE_GOLD_ORE), Rarity.EPIC))

            .add(new TrailSelection("chiseled_deepslate", BaseTrail.GROUND_TRAIL, "ground_block:chiseled_deepslate",
                    fromMined(85000, Material.DEEPSLATE), Rarity.LEGENDARY))

            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("dripleaf_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_BIG_DRIPLEAF_PLACE,
                    fromMined(60, Material.BIG_DRIPLEAF), Rarity.OCCASIONAL))
            .add(new TrailSelection("candle_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_CANDLE_PLACE,
                    fromCrafted(30, Material.CANDLE), Rarity.OCCASIONAL))

            .add(new TrailSelection("amethyst_step", BaseTrail.SOUND_TRAIL, Sound.BLOCK_AMETHYST_BLOCK_STEP,
                    fromMined(15, Material.AMETHYST_BLOCK), Rarity.UNCOMMON))

            .add(new TrailSelection("amethyst_cluster_place", BaseTrail.SOUND_TRAIL, Sound.BLOCK_AMETHYST_CLUSTER_PLACE,
                    fromMined(900, Material.AMETHYST_BLOCK), Rarity.EPIC))

            .build();

    // Shapes

    private static final List<CosmeticSelection<?>> PARTICLE_SHAPES = ImmutableList.<CosmeticSelection<?>>builder()
            // Small Rings
            .add(new ParticleSelection("dripstone", BaseShape.SMALL_RING, Material.DRIPSTONE_BLOCK,
                    fromMined(50, Material.DRIPSTONE_BLOCK), Rarity.OCCASIONAL))

            .add(new ParticleSelection("flash", BaseShape.SMALL_RING, Particle.FLASH,
                    fromCompletion(PlayerCompletion.LIGHTNING), Rarity.RARE))
            .add(new ParticleSelection("glow", BaseShape.SMALL_RING, Particle.GLOW,
                    fromKilled(25, EntityType.GLOW_SQUID), Rarity.RARE))
            .add(new ParticleSelection("calcite", BaseShape.SMALL_RING, Material.CALCITE,
                    fromMined(125, Material.CALCITE), Rarity.RARE))

            // Large Rings
            .add(new ParticleSelection("deepslate", BaseShape.LARGE_RING, Material.DEEPSLATE,
                    fromMined(145, Material.DEEPSLATE), Rarity.UNCOMMON))
            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("cobbled_deepslate", Material.COBBLED_DEEPSLATE,
                    fromMined(20, Material.DEEPSLATE), Rarity.COMMON))

            .add(new HatSelection("smooth_basalt", Material.SMOOTH_BASALT,
                    fromMined(80, Material.BASALT), Rarity.OCCASIONAL))

            .add(new HatSelection("copper_block", Material.COPPER_BLOCK,
                    fromMined(150, Material.COPPER_ORE), Rarity.UNCOMMON))
            .add(new HatSelection("lightning_rod", Material.LIGHTNING_ROD,
                    fromCrafted(50, Material.LIGHTNING_ROD), Rarity.UNCOMMON))

            .add(new HatSelection("azalea", Material.AZALEA,
                    fromMined(185, Material.AZALEA_LEAVES), Rarity.RARE))
            .add(new HatSelection("amethyst_block", Material.AMETHYST_BLOCK,
                    fromMined(550, Material.AMETHYST_BLOCK), Rarity.RARE))

            .add(new HatSelection("flowering_azalea", Material.FLOWERING_AZALEA,
                    fromMined(285, Material.AZALEA_LEAVES), Rarity.EPIC))

            .add(new HatSelection("sculk_sensor", Material.SCULK_SENSOR,
                    fromStatistic(Statistic.MOB_KILLS, 25000), Rarity.LEGENDARY))
            .build();
    
    // Animated Hats

    private static final List<CosmeticSelection<?>> ANIMATED_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("deepslate_ores", HatSelection.of(20,
                Material.DEEPSLATE_COAL_ORE, Material.DEEPSLATE_COPPER_ORE, Material.DEEPSLATE_IRON_ORE,
                Material.DEEPSLATE_LAPIS_ORE, Material.DEEPSLATE_REDSTONE_ORE, Material.DEEPSLATE_GOLD_ORE,
                Material.DEEPSLATE_DIAMOND_ORE, Material.DEEPSLATE_EMERALD_ORE  
            ), fromMined(400, Material.DEEPSLATE_COAL_ORE), Rarity.EPIC))
            .add(new HatSelection("animated_azalea", HatSelection.of(60,
                Material.AZALEA, Material.FLOWERING_AZALEA
            ), fromMined(500, Material.AZALEA_LEAVES), Rarity.LEGENDARY))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_16"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_16"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAILS, BaseTrail.SOUND_TRAIL, "1_16"))

            .put(BaseShape.ALL, join(PARTICLE_SHAPES, BaseShape.ALL, "1_16"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_16"))
            .put(BaseHat.ANIMATED, join(ANIMATED_HATS, BaseHat.ANIMATED, "1_16"))

            .put(BaseGadget.INSTANCE, getForVersion(BaseGadget.INSTANCE, "1_16"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        PET_MAP.putAll(
                ImmutableMap.<PetType, PetInfo>builder()
                        .put(PetType.AXOLOTL, of(
                                "Axolotl", Rarity.LEGENDARY,
                                petIcon("axolotl_pet", "Axolotl"), fromMined(10000, Material.AMETHYST_BLOCK), stand -> {
                                    if (r.nextInt(100) < 25) circle(head(stand), Material.BIG_DRIPLEAF, 5, 0.25);
                                    if (r.nextInt(100) < 5) w.spawnFakeItem(new ItemStack(Material.AMETHYST_SHARD), head(stand), 5);
                                }
                        ))

                        .build()
        );

        loadExternalPets("1_16");
    }
}
