package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_15 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("honeycomb", BaseTrail.PROJECTILE_TRAIL, Material.HONEYCOMB,
                    CompletionCriteria.fromMined(240, Material.OAK_LOG, Material.BIRCH_LOG), CosmeticRarity.COMMON))

            .add(new TrailSelection("super_honeycomb", BaseTrail.PROJECTILE_TRAIL, Material.HONEYCOMB_BLOCK,
                    CompletionCriteria.fromMined(1345, Material.OAK_LOG, Material.BIRCH_LOG), CosmeticRarity.RARE))

            // Particles
            .add(new TrailSelection("honey", BaseTrail.PROJECTILE_TRAIL, Particle.DRIPPING_HONEY,
                    CompletionCriteria.fromCrafted(80, Material.BEEHIVE), CosmeticRarity.UNCOMMON))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_14").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
