package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

public class CosmeticSelections1_14 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            // Items + Fancy Items
            .add(new TrailSelection("lantern", BaseTrail.PROJECTILE_TRAIL, Material.LANTERN,
                    CompletionCriteria.fromCrafted(30, Material.TORCH), Rarity.COMMON))

            .add(new TrailSelection("bamboo", BaseTrail.PROJECTILE_TRAIL, Material.BAMBOO,
                    CompletionCriteria.fromMined(240, Material.BAMBOO), Rarity.UNCOMMON))

            .add(new TrailSelection("crossbow", BaseTrail.PROJECTILE_TRAIL, "fancy_item:crossbow",
                    CompletionCriteria.fromKilled(550, EntityType.PILLAGER), Rarity.EPIC))
            
            .add(new TrailSelection("wither_rose", BaseTrail.PROJECTILE_TRAIL, Material.WITHER_ROSE,
                    CompletionCriteria.fromKilled(1000, EntityType.WITHER), Rarity.MYTHICAL))
            
            .add(new TrailSelection("jigsaw", BaseTrail.PROJECTILE_TRAIL, "fancy_block:jigsaw",
                    CompletionCriteria.fromBlocksMined(1000000), Rarity.SPECIAL))
            
            // Particles
            .add(new TrailSelection("smoke", BaseTrail.PROJECTILE_TRAIL, Particle.CAMPFIRE_COSY_SMOKE,
                    CompletionCriteria.fromCrafted(50, Material.TORCH), Rarity.COMMON))
            
            .build();

    // Ground Trails
    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("bamboo", BaseTrail.GROUND_TRAIL, Material.BAMBOO,
                    CompletionCriteria.fromMined(240, Material.BAMBOO), Rarity.UNCOMMON))

            .add(new TrailSelection("campfire", BaseTrail.GROUND_TRAIL, Material.CAMPFIRE,
                    CompletionCriteria.fromCrafted(70, Material.CAMPFIRE), Rarity.OCCASIONAL))
            
            .add(new TrailSelection("lantern", BaseTrail.GROUND_TRAIL, Material.LANTERN,
                    CompletionCriteria.fromCrafted(500, Material.TORCH), Rarity.RARE))
            
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_13"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_13"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_13"))

            .put(BaseShape.SMALL_RING, getForVersion(BaseShape.SMALL_RING, "1_13"))
            .put(BaseShape.SMALL_DETAILED_RING, getForVersion(BaseShape.SMALL_DETAILED_RING, "1_13"))
            .put(BaseShape.LARGE_RING, getForVersion(BaseShape.LARGE_RING, "1_13"))
            .put(BaseShape.LARGE_DETAILED_RING, getForVersion(BaseShape.LARGE_DETAILED_RING, "1_13"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

}
