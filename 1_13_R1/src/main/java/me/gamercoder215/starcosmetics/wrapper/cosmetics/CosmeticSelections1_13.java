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
import org.bukkit.Statistic;
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
            // Items + Fancy Items
            .add(new TrailSelection("shells", BaseTrail.PROJECTILE_TRAIL, Material.NAUTILUS_SHELL,
                    CompletionCriteria.fromKilled(80, EntityType.DROWNED), CosmeticRarity.OCCASIONAL))
            .add(new TrailSelection("gunpowder", BaseTrail.PROJECTILE_TRAIL, Material.GUNPOWDER,
                    CompletionCriteria.fromKilled(90, EntityType.CREEPER), CosmeticRarity.OCCASIONAL))

            .add(new TrailSelection("coral", BaseTrail.PROJECTILE_TRAIL, 
                    Arrays.asList(Material.FIRE_CORAL_BLOCK, Material.TUBE_CORAL_BLOCK, Material.BRAIN_CORAL_BLOCK, Material.BUBBLE_CORAL_BLOCK, Material.HORN_CORAL_BLOCK),
                    CompletionCriteria.fromCrafted(15, Material.CONDUIT), CosmeticRarity.RARE))
            .add(new TrailSelection("membranes", BaseTrail.PROJECTILE_TRAIL, Material.PHANTOM_MEMBRANE,
                    CompletionCriteria.fromKilled(100, EntityType.PHANTOM), CosmeticRarity.RARE))
            .add(new TrailSelection("colored_glass", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.RED_STAINED_GLASS, Material.ORANGE_STAINED_GLASS, Material.YELLOW_STAINED_GLASS, Material.LIME_STAINED_GLASS, Material.GREEN_STAINED_GLASS, Material.BLUE_STAINED_GLASS, Material.CYAN_STAINED_GLASS, Material.MAGENTA_STAINED_GLASS, Material.PURPLE_STAINED_GLASS, Material.PINK_STAINED_GLASS),
                    CompletionCriteria.fromMined(100, Material.SAND), CosmeticRarity.RARE))
                    
            .add(new TrailSelection("sea_heart", BaseTrail.PROJECTILE_TRAIL, Material.HEART_OF_THE_SEA,
                    CompletionCriteria.fromKilled(400, EntityType.ELDER_GUARDIAN), CosmeticRarity.EPIC))
            .add(new TrailSelection("scutes", BaseTrail.PROJECTILE_TRAIL, Material.SCUTE,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 500), CosmeticRarity.EPIC))

            .add(new TrailSelection("notch_apple", BaseTrail.PROJECTILE_TRAIL, Material.ENCHANTED_GOLDEN_APPLE,
                    CompletionCriteria.fromKilled(150, EntityType.WITHER), CosmeticRarity.MYTHICAL))
            
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, Stream.concat(
                    CosmeticSelections.getForVersion(BaseTrail.PROJECTILE_TRAIL, "1_12").stream(),
                    PROJECTILE_TRAILS.stream()).collect(Collectors.toList())
            )
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }

}
