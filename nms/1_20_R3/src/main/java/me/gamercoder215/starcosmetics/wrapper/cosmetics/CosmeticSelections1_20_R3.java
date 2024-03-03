package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.util.selection.HatSelection.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

@SuppressWarnings("UnstableApiUsage")
final class CosmeticSelections1_20_R3 implements CosmeticSelections {

    // Trails

    // Projectile Trails

    public static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("gust", BaseTrail.PROJECTILE_TRAIL, Particle.GUST,
                    fromKilled(1, EntityType.BREEZE), UNCOMMON))

            .add(new TrailSelection("wind_charge", BaseTrail.PROJECTILE_TRAIL, EntityType.WIND_CHARGE,
                    fromKilled(45, EntityType.BREEZE), EPIC))
            .build();

    // Ground Trails

    public static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("gust", BaseTrail.GROUND_TRAIL, Particle.GUST,
                    fromKilled(1, EntityType.BREEZE), UNCOMMON))
            .add(new TrailSelection("polished_tuff", BaseTrail.GROUND_TRAIL, Material.POLISHED_TUFF,
                    fromCrafted(32, Material.POLISHED_TUFF), UNCOMMON))
            .add(new TrailSelection("tuff_bricks", BaseTrail.GROUND_TRAIL, Material.TUFF_BRICKS,
                    fromCrafted(32, Material.TUFF_BRICKS), UNCOMMON))

            .add(new TrailSelection("copper_trapdoor", BaseTrail.GROUND_TRAIL, Material.COPPER_TRAPDOOR,
                    fromCrafted(40, Material.COPPER_TRAPDOOR), RARE))
            .build();

    // Hats

    // Normal Hats

    public static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("copper_grate", Material.COPPER_GRATE,
                    fromCrafted(10, Material.COPPER_GRATE), COMMON))

            .add(new HatSelection("copper_bulb", Material.COPPER_BULB,
                    fromCrafted(25, Material.COPPER_BULB), OCCASIONAL))
            .add(new HatSelection("chiseled_copper", Material.CHISELED_COPPER,
                    fromCrafted(25, Material.CHISELED_COPPER), OCCASIONAL))

            .add(new HatSelection("crafter", Material.CRAFTER,
                    fromCrafted(5, Material.CRAFTER), RARE))

            .add(new HatSelection("trial_key", Material.TRIAL_KEY,
                    fromStatistic(Statistic.MOB_KILLS, 450000), LEGENDARY))
            
            .add(new HatSelection("trial_spawner", Material.TRIAL_SPAWNER,
                    fromStatistic(Statistic.MOB_KILLS, 1100000), MYTHICAL))
            .build();

    // Animated Hats

    public static final List<CosmeticSelection<?>> ANIMATED_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("copper_grates",
                    of(15, Material.COPPER_GRATE, Material.EXPOSED_COPPER_GRATE,
                            Material.WEATHERED_COPPER_GRATE, Material.OXIDIZED_COPPER_GRATE),
                    fromCrafted(100, Material.COPPER_GRATE), UNCOMMON))

            .add(new HatSelection("copper_bulbs",
                    of(15, Material.COPPER_BULB, Material.EXPOSED_COPPER_BULB,
                            Material.WEATHERED_COPPER_BULB, Material.OXIDIZED_COPPER_BULB),
                    fromCrafted(250, Material.COPPER_BULB), RARE))
            .build();

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_20"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_20"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_20"))

            .put(BaseShape.ALL, getForVersion(BaseShape.ALL, "1_20"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_20"))
            .put(BaseHat.ANIMATED, join(ANIMATED_HATS, BaseHat.ANIMATED, "1_20"))

            .put(BaseGadget.INSTANCE, getForVersion(BaseGadget.INSTANCE, "1_20"))

            .put(BaseCape.NORMAL, getForVersion(BaseCape.NORMAL, "1_20"))
            .put(BaseCape.ANIMATED, getForVersion(BaseCape.ANIMATED, "1_20"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        if (Wrapper.getServerVersion().equalsIgnoreCase("1_20_R3") && !w.hasFeatureFlag("c")) return CosmeticSelections.getForVersion("1_20");

        return SELECTIONS;
    }
    
}
