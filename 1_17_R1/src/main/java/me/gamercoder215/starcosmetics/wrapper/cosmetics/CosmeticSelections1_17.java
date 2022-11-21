package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_17 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("deepslate", BaseTrail.PROJECTILE_TRAIL, Material.DEEPSLATE,
                    CompletionCriteria.fromMined(370, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), Rarity.OCCASIONAL))

            .add(new TrailSelection("copper", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.COPPER_BLOCK, Material.WEATHERED_COPPER, Material.EXPOSED_COPPER, Material.OXIDIZED_COPPER),
                    CompletionCriteria.fromMined(340, Material.COPPER_ORE, Material.DEEPSLATE_COPPER_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("glow_berries", BaseTrail.PROJECTILE_TRAIL, Material.GLOW_BERRIES,
                    CompletionCriteria.fromMined(200, Material.GLOW_LICHEN), Rarity.UNCOMMON))  

            .add(new TrailSelection("raw_ores", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.RAW_IRON, Material.RAW_GOLD, Material.RAW_COPPER),
                    CompletionCriteria.fromMined(500, Material.DEEPSLATE, Material.COBBLED_DEEPSLATE), Rarity.RARE))
            
            .add(new TrailSelection("amethyst", BaseTrail.PROJECTILE_TRAIL, Material.AMETHYST_SHARD,
                    CompletionCriteria.fromMined(2000, Material.AMETHYST_BLOCK), Rarity.EPIC))

            .add(new TrailSelection("super_amethyst", BaseTrail.PROJECTILE_TRAIL, Material.AMETHYST_BLOCK,
                    CompletionCriteria.fromMined(5000, Material.AMETHYST_BLOCK), Rarity.LEGENDARY))
            
            
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("copper_ore", BaseTrail.GROUND_TRAIL, "ground_block:copper_ore",
                    CompletionCriteria.fromMined(250, Material.COPPER_ORE), Rarity.UNCOMMON))
            .add(new TrailSelection("tinted_glass", BaseTrail.GROUND_TRAIL, "ground_block:tinted_glass",
                    CompletionCriteria.fromMined(140, Material.AMETHYST_BLOCK), Rarity.UNCOMMON))
            .add(new TrailSelection("glow_berries", BaseTrail.GROUND_TRAIL, Material.GLOW_BERRIES,
                    CompletionCriteria.fromMined(200, Material.GLOW_LICHEN), Rarity.UNCOMMON))

            .add(new TrailSelection("raw_iron", BaseTrail.GROUND_TRAIL, "ground_block:raw_iron",
                    CompletionCriteria.fromMined(100, Material.DEEPSLATE_IRON_ORE), Rarity.RARE))

            .add(new TrailSelection("amethyst", BaseTrail.GROUND_TRAIL, Material.AMETHYST_SHARD,
                    CompletionCriteria.fromMined(600, Material.AMETHYST_BLOCK), Rarity.EPIC))
            .add(new TrailSelection("raw_gold", BaseTrail.GROUND_TRAIL, "ground_block:raw_gold",
                    CompletionCriteria.fromMined(100, Material.DEEPSLATE_GOLD_ORE), Rarity.EPIC))

            .build();

    // Shapes

    // Small Rings

    private static final List<CosmeticSelection<?>> SMALL_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("flash", BaseShape.SMALL_RING, Particle.FLASH,
                    CompletionCriteria.fromCompletion(PlayerCompletion.LIGHTNING), Rarity.RARE))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_16"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_16"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_16"))

            .put(BaseShape.SMALL_RING, join(SMALL_RINGS, BaseShape.SMALL_RING, "1_16"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_16"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_16"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_16"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
}
