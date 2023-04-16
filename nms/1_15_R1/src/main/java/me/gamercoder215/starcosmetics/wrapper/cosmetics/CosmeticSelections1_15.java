package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Sound;
import org.bukkit.Statistic;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

public final class CosmeticSelections1_15 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("honeycomb", BaseTrail.PROJECTILE_TRAIL, Material.HONEYCOMB,
                    fromMined(240, Material.OAK_LOG, Material.BIRCH_LOG), Rarity.COMMON))

            .add(new TrailSelection("super_honeycomb", BaseTrail.PROJECTILE_TRAIL, Material.HONEYCOMB_BLOCK,
                    fromMined(1345, Material.OAK_LOG, Material.BIRCH_LOG), Rarity.RARE))

            // Particles
            .add(new TrailSelection("honey", BaseTrail.PROJECTILE_TRAIL, Particle.DRIPPING_HONEY,
                    fromCrafted(80, Material.BEEHIVE), Rarity.UNCOMMON))
            .build();

    // Ground Trails
    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("honeycomb_block", BaseTrail.GROUND_TRAIL, "ground_block:honeycomb_block",
                    fromStatistic(Statistic.ANIMALS_BRED, 1000), Rarity.EPIC))
            .build();

    // Sound Trails
    private static final List<CosmeticSelection<?>> SOUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("bee_pollinate", BaseTrail.SOUND_TRAIL, Sound.ENTITY_BEE_POLLINATE,
                    fromStatistic(Statistic.ANIMALS_BRED, 900), Rarity.EPIC))
            .build();

    // Shapes

    private static final List<CosmeticSelection<?>> PARTICLE_SHAPES = ImmutableList.<CosmeticSelection<?>>builder()
            // Small Rings
            .add(new ParticleSelection("honey", BaseShape.SMALL_RING, Particle.DRIPPING_HONEY,
                    fromCrafted(180, Material.BEEHIVE), Rarity.RARE))

            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("honey_block", Material.HONEY_BLOCK,
                    fromCrafted(5, Material.BEEHIVE), Rarity.COMMON))

            .add(new HatSelection("honeycomb_block", Material.HONEYCOMB_BLOCK,
                    fromCrafted(15, Material.BEEHIVE), Rarity.UNCOMMON))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_14"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_14"))
            .put(BaseTrail.SOUND_TRAIL, join(SOUND_TRAILS, BaseTrail.SOUND_TRAIL, "1_14"))

            .put(BaseShape.ALL, join(PARTICLE_SHAPES, BaseShape.ALL, "1_14"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_14"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        PET_MAP.putAll(ImmutableMap.<PetType, PetInfo>builder()
                .put(PetType.HUMMINGBIRD, of(
                        "Hummingbird", Rarity.EPIC,
                        petIcon("hummingbird_pet", "Hummingbird"), fromStatistic(Statistic.FISH_CAUGHT, 100), stand -> {
                            if (r.nextInt(100) < 25) stand.getWorld().spawnParticle(Particle.END_ROD, head(stand), 1, 0, 0, 0, 0);
                        }
                ))
                .build()
        );

        loadExternalPets("1_14");
    }

}
