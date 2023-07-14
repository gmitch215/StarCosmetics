package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

final class CosmeticSelections1_11 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("totems", BaseTrail.PROJECTILE_TRAIL, StarMaterial.TOTEM_OF_UNDYING.find(),
                    fromKilled(350, EntityType.EVOKER), Rarity.EPIC))
            .add(new TrailSelection("shulker_shell", BaseTrail.PROJECTILE_TRAIL, Material.SHULKER_SHELL,
                    fromKilled(390, EntityType.SHULKER), Rarity.EPIC))

            .add(new TrailSelection("totem_particle", BaseTrail.PROJECTILE_TRAIL, Particle.TOTEM,
                    fromKilled(350, EntityType.VINDICATOR), Rarity.EPIC))

            .add(new TrailSelection("vex", BaseTrail.PROJECTILE_TRAIL, EntityType.VEX,
                    fromKilled(350, EntityType.VEX), Rarity.EPIC))
            
            .add(new TrailSelection("observer", BaseTrail.PROJECTILE_TRAIL, StarMaterial.OBSERVER,
                    fromMined(14000, Material.REDSTONE_ORE), Rarity.LEGENDARY))
            
            .build();

    // Ground Trails
    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("shulker_shell", BaseTrail.GROUND_TRAIL, Material.SHULKER_SHELL,
                    fromKilled(390, EntityType.SHULKER), Rarity.EPIC))
            .add(new TrailSelection("totem_particle", BaseTrail.GROUND_TRAIL, Particle.TOTEM,
                    fromKilled(350, EntityType.VINDICATOR), Rarity.EPIC))

            .build();

    // Shapes

    private static final List<CosmeticSelection<?>> PARTICLE_SHAPES = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new ParticleSelection("totem", BaseShape.SMALL_RING, Particle.TOTEM,
                    fromKilled(350, EntityType.EVOKER), Rarity.EPIC))
            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("observer", Material.OBSERVER,
                    fromCrafted(140, Material.OBSERVER), Rarity.RARE))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_10"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_10"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_10"))

            .put(BaseShape.ALL, join(PARTICLE_SHAPES, BaseShape.ALL, "1_10"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_10"))
            .put(BaseHat.ANIMATED, getForVersion(BaseHat.ANIMATED, "1_10"))

            .put(BaseGadget.INSTANCE, getForVersion(BaseGadget.INSTANCE, "1_10"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        PET_MAP.putAll(
                ImmutableMap.<PetType, PetInfo>builder()
                        .put(PetType.EAGLE, of(
                                "Eagle", Rarity.LEGENDARY,
                                petIcon("eagle_pet", "Eagle"), fromKilled(15000, EntityType.VEX)
                        ))
                        .build()
        );

        loadExternalPets("1_10");
    }

}
