package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.selection.*;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class CosmeticSelections1_13 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            .add(new TrailSelection("shells", BaseTrail.PROJECTILE_TRAIL, Material.NAUTILUS_SHELL,
                    CompletionCriteria.fromKilled(80, EntityType.DROWNED), CosmeticRarity.OCCASIONAL))
            .add(new TrailSelection("gunpowder", BaseTrail.PROJECTILE_TRAIL, Material.GUNPOWDER,
                    CompletionCriteria.fromKilled(90, EntityType.CREEPER), CosmeticRarity.OCCASIONAL))

            .add(new TrailSelection("coral", BaseTrail.PROJECTILE_TRAIL, 
                    Arrays.asList(Material.FIRE_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK),
                    CompletionCriteria.fromCrafted(15, Material.CONDUIT), CosmeticRarity.RARE))
            
            .add(new TrailSelection("sea_heart", BaseTrail.PROJECTILE_TRAIL, Material.HEART_OF_THE_SEA,
                    CompletionCriteria.fromKilled(400, EntityType.ELDER_GUARDIAN), CosmeticRarity.EPIC))
            
            .build();

    // Selections

    private static final Map<CosmeticKey, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<CosmeticKey, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_12").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<CosmeticKey, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }

}
