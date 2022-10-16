package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRarity;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_11 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("totems", BaseTrail.PROJECTILE_TRAIL, StarMaterial.TOTEM.find(),
                    CompletionCriteria.fromKilled(350, EntityType.EVOKER), CosmeticRarity.EPIC))
            .add(new TrailSelection("shulker_shell", BaseTrail.PROJECTILE_TRAIL, Material.SHULKER_SHELL,
                    CompletionCriteria.fromKilled(350, EntityType.SHULKER), CosmeticRarity.EPIC))

            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_10").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
