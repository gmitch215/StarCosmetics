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
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_19 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("mud", BaseTrail.PROJECTILE_TRAIL, Material.MUD,
                    CompletionCriteria.fromCrafted(30, Material.TORCH), Rarity.COMMON))

            .add(new TrailSelection("horns", BaseTrail.PROJECTILE_TRAIL, Material.GOAT_HORN,
                    CompletionCriteria.fromMined(100, Material.GOAT_HORN), Rarity.RARE))

            .add(new TrailSelection("froglight", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.OCHRE_FROGLIGHT, Material.PEARLESCENT_FROGLIGHT, Material.VERDANT_FROGLIGHT),
                    CompletionCriteria.fromKilled(780, EntityType.MAGMA_CUBE), Rarity.EPIC))

            .add(new TrailSelection("echo_shard", BaseTrail.PROJECTILE_TRAIL, Material.ECHO_SHARD,
                    CompletionCriteria.fromCompletion(PlayerCompletion.SONIC_BOOM_DEATH), Rarity.LEGENDARY))

            // Particles
            .add(new TrailSelection("sonic_boom", BaseTrail.PROJECTILE_TRAIL, Particle.SONIC_BOOM,
                    CompletionCriteria.fromMined(500, Material.SCULK), Rarity.RARE))

            .add(new TrailSelection("sculk_soul", BaseTrail.PROJECTILE_TRAIL, Particle.SCULK_CHARGE,
                    CompletionCriteria.fromKilled(5, EntityType.WARDEN), Rarity.EPIC))

            // Entities
            .add(new TrailSelection("allay", BaseTrail.PROJECTILE_TRAIL, EntityType.ALLAY,
                    CompletionCriteria.fromKilled(450, EntityType.PILLAGER), Rarity.RARE))


            
            .build();

    // Sound Trails

    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("mud_break", BaseTrail.SOUND_TRAIL, Sound.BLOCK_MUD_BREAK,
                    CompletionCriteria.fromMined(30, Material.MUD), Rarity.COMMON))

            .add(new TrailSelection("shrieker_shriek", BaseTrail.SOUND_TRAIL, Sound.BLOCK_SCULK_SHRIEKER_SHRIEK,
                    CompletionCriteria.fromKilled(3, EntityType.WARDEN), Rarity.EPIC))

            .build();

    // Shapes

    // Small Rings

    private static final List<CosmeticSelection<?>> SMALL_RINGS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("sculk_soul", BaseShape.SMALL_RING, Particle.SCULK_SOUL,
                    CompletionCriteria.fromMined(400, Material.SCULK), Rarity.EPIC))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_18"))
            .put(BaseTrail.GROUND_TRAIL, getForVersion(BaseTrail.GROUND_TRAIL, "1_18"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAILS, BaseTrail.SOUND_TRAIL, "1_18"))

            .put(BaseShape.SMALL_RING, join(SMALL_RINGS, BaseShape.SMALL_RING, "1_18"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_18"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_18"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_18"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
}
