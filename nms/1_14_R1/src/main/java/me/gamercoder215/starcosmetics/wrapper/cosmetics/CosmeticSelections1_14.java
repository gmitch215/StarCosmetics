package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseHat;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Particle;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.*;

public final class CosmeticSelections1_14 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails
    private static final List<CosmeticSelection<?>> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("lantern", BaseTrail.PROJECTILE_TRAIL, Material.LANTERN,
                    fromCrafted(30, Material.TORCH), COMMON))
            .add(new TrailSelection("smoke", BaseTrail.PROJECTILE_TRAIL, Particle.CAMPFIRE_COSY_SMOKE,
                    fromCrafted(50, Material.TORCH), COMMON))

            .add(new TrailSelection("bamboo", BaseTrail.PROJECTILE_TRAIL, Material.BAMBOO,
                    fromMined(225, Material.BAMBOO), UNCOMMON))
            .add(new TrailSelection("fletching_table", BaseTrail.PROJECTILE_TRAIL, "fancy_block:fletching_table",
                    fromCrafted(15, Material.FLETCHING_TABLE), UNCOMMON))

            .add(new TrailSelection("crossbow", BaseTrail.PROJECTILE_TRAIL, "fancy_item:crossbow",
                    fromKilled(550, EntityType.PILLAGER), EPIC))
            .add(new TrailSelection("loom", BaseTrail.PROJECTILE_TRAIL, "fancy_block:loom",
                    fromCrafted(25, Material.LOOM), EPIC))
            
            .add(new TrailSelection("wither_rose", BaseTrail.PROJECTILE_TRAIL, Material.WITHER_ROSE,
                    fromKilled(1000, EntityType.WITHER), MYTHICAL))
            
            .add(new TrailSelection("jigsaw", BaseTrail.PROJECTILE_TRAIL, "fancy_block:jigsaw",
                    fromBlocksMined(1000000), SPECIAL))
            
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAILS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("bamboo", BaseTrail.GROUND_TRAIL, Material.BAMBOO,
                    fromMined(240, Material.BAMBOO), UNCOMMON))

            .add(new TrailSelection("campfire", BaseTrail.GROUND_TRAIL, Material.CAMPFIRE,
                    fromCrafted(70, Material.CAMPFIRE), OCCASIONAL))
            
            .add(new TrailSelection("lantern", BaseTrail.GROUND_TRAIL, Material.LANTERN,
                    fromCrafted(500, Material.TORCH), RARE))
            
            .build();

    // Hats

    // Normal Hats

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("scaffholding", Material.SCAFFOLDING,
                    fromCrafted(60, Material.SCAFFOLDING), COMMON))

            .add(new HatSelection("jigsaw", Material.JIGSAW,
                    fromBlocksMined(750000), SPECIAL))

            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAILS, BaseTrail.PROJECTILE_TRAIL, "1_13"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAILS, BaseTrail.GROUND_TRAIL, "1_13"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_13"))

            .put(BaseShape.ALL, getForVersion(BaseShape.ALL, "1_13"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_13"))
            .put(BaseHat.ANIMATED, getForVersion(BaseHat.ANIMATED, "1_13"))
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }

    @Override
    public void loadPets() {
        PET_MAP.putAll(
                ImmutableMap.<PetType, PetInfo>builder()
                        .put(PetType.GORILLA, of(
                                "Gorilla", RARE,
                                petIcon("gorilla_pet", "Gorilla"), fromKilled(640, EntityType.COW), stand ->
                                        w.spawnFakeItem(new ItemStack(Material.BAMBOO), head(stand), 20)
                        ))
                        .put(PetType.FOX, of(
                                "Fox", RARE,
                                petIcon("fox_pet", "Fox"), fromKilled(150, EntityType.PILLAGER), stand ->
                                    stand.getWorld().spawnParticle(Particle.CRIT, head(stand), 1, 0, 0, 0, 0)

                        ))

                        .build()
        );

        loadExternalPets("1_13");
    }

}
