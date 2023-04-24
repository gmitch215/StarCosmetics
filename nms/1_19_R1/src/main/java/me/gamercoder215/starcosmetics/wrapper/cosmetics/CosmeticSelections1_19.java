package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public final class CosmeticSelections1_19 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("mud", BaseTrail.PROJECTILE_TRAIL, Material.MUD,
                    fromCrafted(30, Material.TORCH), Rarity.COMMON))

            .add(new TrailSelection("horns", BaseTrail.PROJECTILE_TRAIL, Material.GOAT_HORN,
                    fromMined(100, Material.GOAT_HORN), Rarity.RARE))
            .add(new TrailSelection("allay", BaseTrail.PROJECTILE_TRAIL, EntityType.ALLAY,
                    fromKilled(450, EntityType.PILLAGER), Rarity.RARE))
            .add(new TrailSelection("sonic_boom", BaseTrail.PROJECTILE_TRAIL, Particle.SONIC_BOOM,
                    fromMined(500, Material.SCULK), Rarity.RARE))

            .add(new TrailSelection("froglight", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.OCHRE_FROGLIGHT, Material.PEARLESCENT_FROGLIGHT, Material.VERDANT_FROGLIGHT),
                    fromKilled(780, EntityType.MAGMA_CUBE), Rarity.EPIC))
            .add(new TrailSelection("sculk_soul", BaseTrail.PROJECTILE_TRAIL, Particle.SCULK_CHARGE,
                    fromKilled(5, EntityType.WARDEN), Rarity.EPIC))

            .add(new TrailSelection("echo_shard", BaseTrail.PROJECTILE_TRAIL, Material.ECHO_SHARD,
                    fromCompletion(PlayerCompletion.SONIC_BOOM_DEATH), Rarity.LEGENDARY))
            
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("mangrove_planks", BaseTrail.GROUND_TRAIL, Material.MANGROVE_PLANKS,
                    fromCrafted(50, Material.MANGROVE_PLANKS), Rarity.COMMON))

            .add(new TrailSelection("packed_mud", BaseTrail.GROUND_TRAIL, "ground_block:packed_mud",
                    fromMined(115, Material.MUD), Rarity.OCCASIONAL))

            .add(new TrailSelection("mangrove_saplings", BaseTrail.GROUND_TRAIL, Material.MANGROVE_PROPAGULE,
                    fromMined(75, Material.MANGROVE_LEAVES), Rarity.RARE))
            .add(new TrailSelection("sculk", BaseTrail.GROUND_TRAIL, "ground_block:sculk",
                    fromMined(500, Material.SCULK), Rarity.RARE))
            .add(new TrailSelection("sculk_sensor", BaseTrail.GROUND_TRAIL, "side_block:sculk_sensor",
                    fromMined(200, Material.SCULK_SENSOR), Rarity.RARE))

            .add(new TrailSelection("echo_shard", BaseTrail.GROUND_TRAIL, Material.ECHO_SHARD,
                    fromCompletion(PlayerCompletion.SONIC_BOOM_DEATH), Rarity.LEGENDARY))

            .add(new TrailSelection("five", BaseTrail.GROUND_TRAIL, Material.MUSIC_DISC_5,
                    fromKilled(12, EntityType.WARDEN), Rarity.MYTHICAL))

            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("mud_break", BaseTrail.SOUND_TRAIL, Sound.BLOCK_MUD_BREAK,
                    fromMined(30, Material.MUD), Rarity.COMMON))

            .add(new TrailSelection("candle_break", BaseTrail.SOUND_TRAIL, Sound.BLOCK_CANDLE_BREAK,
                    fromCrafted(25, Material.CANDLE), Rarity.OCCASIONAL))

            .add(new TrailSelection("shrieker_shriek", BaseTrail.SOUND_TRAIL, Sound.BLOCK_SCULK_SHRIEKER_SHRIEK,
                    fromKilled(3, EntityType.WARDEN), Rarity.EPIC))

            .build();

    // Shapes

    private static final List<CosmeticSelection<?>> PARTICLE_SHAPES = ImmutableList.<CosmeticSelection<?>>builder()
            // Small Rings
            .add(new ParticleSelection("sculk_soul", BaseShape.SMALL_RING, Particle.SCULK_SOUL,
                    fromMined(400, Material.SCULK), Rarity.EPIC))

            // Small Detailed Rings
            .add(new ParticleSelection("mud", BaseShape.SMALL_DETAILED_RING, Material.MUD,
                    fromMined(60, Material.DIRT), Rarity.COMMON))

            // Pentagon
            .add(new ParticleSelection("sculk", BaseShape.PENTAGON, Material.SCULK,
                    fromMined(270, Material.SCULK), Rarity.UNCOMMON))

            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("mud", Material.MUD,
                    fromMined(45, Material.DIRT), Rarity.COMMON))
            .add(new HatSelection("mangrove_planks", Material.MANGROVE_PLANKS,
                    fromCrafted(50, Material.MANGROVE_PLANKS), Rarity.COMMON))

            .add(new HatSelection("packed_mud", Material.PACKED_MUD,
                    fromMined(145, Material.MUD), Rarity.OCCASIONAL))

            .add(new HatSelection("sculk", Material.SCULK,
                    fromMined(300, Material.SCULK), Rarity.UNCOMMON))

            .add(new HatSelection("mangrove_fence", Material.MANGROVE_FENCE,
                    fromCrafted(250, Material.MANGROVE_FENCE), Rarity.RARE))

            .add(new HatSelection("mangrove_fence_gate", Material.MANGROVE_FENCE_GATE,
                    fromCrafted(680, Material.MANGROVE_FENCE), Rarity.EPIC))

            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_18"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_18"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAILS, BaseTrail.SOUND_TRAIL, "1_18"))

            .put(BaseShape.ALL, join(PARTICLE_SHAPES, BaseShape.ALL, "1_18"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_18"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        CosmeticSelections.loadExternalPets("1_18");
    }
}
