package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_14 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            // Items + FFancy Items
            .add(new TrailSelection("lantern", BaseTrail.PROJECTILE_TRAIL, Material.LANTERN,
                    CompletionCriteria.fromCrafted(30, Material.TORCH), CosmeticRarity.COMMON))

            .add(new TrailSelection("wither_roses", BaseTrail.PROJECTILE_TRAIL, Material.WITHER_ROSE,
                    CompletionCriteria.fromKilled(1000, EntityType.WITHER), CosmeticRarity.MYTHICAL))

            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_13").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }

}
