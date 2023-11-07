package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.AnimatedItem;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.HatSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.Material;
import org.bukkit.Statistic;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.Rarity.*;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.getForVersion;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.join;

final class CosmeticSelections1_20 implements CosmeticSelections {

    // Trails

    // Projectile Trails

    private static final List<CosmeticSelection<?>> PROJECTILE_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("bamboo_planks", BaseTrail.PROJECTILE_TRAIL, Material.BAMBOO_PLANKS,
                    fromMined(200, Material.BAMBOO), OCCASIONAL))

            .add(new TrailSelection("relic", BaseTrail.PROJECTILE_TRAIL, Material.MUSIC_DISC_RELIC,
                    fromStatistic(Statistic.ANIMALS_BRED, 5500), EPIC))

            .add(new TrailSelection("armor_trims", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.stream(Material.values()).filter(m -> m.name().endsWith("_TRIM_SMITHING_TEMPLATE")).toList(),
                    fromCrafted(4780, Material.IRON_CHESTPLATE), LEGENDARY))
            .build();

    // Ground Trails

    private static final List<CosmeticSelection<?>> GROUND_TRAIL = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new TrailSelection("pink_petals", BaseTrail.GROUND_TRAIL, Material.PINK_PETALS,
                    fromMined(100, Material.CHERRY_SAPLING), OCCASIONAL))

            .add(new TrailSelection("cherry_pressure_plate", BaseTrail.GROUND_TRAIL, Material.CHERRY_PRESSURE_PLATE,
                    fromMined(120, Material.CHERRY_LOG), UNCOMMON))
            .build();

    // Hats

    // Normal Hats

    private static ItemStack decoratedPot(Material sherd) {
        return decoratedPot(sherd, sherd, sherd, sherd);
    }

    private static ItemStack decoratedPot(Material front, Material back) {
        return decoratedPot(front, back, front, back);
    }

    private static ItemStack decoratedPot(Material... materials) {
        if (materials.length != 4) throw new IllegalArgumentException("Must have 4 materials");
        return w.createDecoratedPot(materials);
    }

    private static final List<CosmeticSelection<?>> NORMAL_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("empty_pot", decoratedPot(Material.BRICK),
                    fromStatistic(Statistic.TRADED_WITH_VILLAGER, 25), COMMON))

            .add(new HatSelection("cherry_leaves", Material.CHERRY_LEAVES,
                    fromMined(65, Material.CHERRY_LEAVES), OCCASIONAL))

            .add(new HatSelection("angler_pot", decoratedPot(Material.ANGLER_POTTERY_SHERD),
                    fromCrafted(50, Material.DECORATED_POT), UNCOMMON))
            .add(new HatSelection("heart_pot", decoratedPot(Material.HEART_POTTERY_SHERD),
                    fromStatistic(Statistic.ANIMALS_BRED, 200), UNCOMMON))
            .add(new HatSelection("tree_pot", decoratedPot(Material.SHELTER_POTTERY_SHERD),
                    fromMined(80, Material.OAK_LOG), UNCOMMON))

            .add(new HatSelection("mourner_pot", decoratedPot(Material.MOURNER_POTTERY_SHERD),
                    fromKilled(1, EntityType.WARDEN), RARE))
            .add(new HatSelection("skull_pot", decoratedPot(Material.SKULL_POTTERY_SHERD),
                    fromKilled(160, EntityType.SKELETON), RARE))
            .add(new HatSelection("howl_pot", decoratedPot(Material.HOWL_POTTERY_SHERD),
                    fromKilled(175, EntityType.SHEEP), RARE))

            .add(new HatSelection("miner_pot", decoratedPot(Material.MINER_POTTERY_SHERD, Material.PRIZE_POTTERY_SHERD),
                    fromMined(600, Material.COAL_ORE), EPIC))
            .add(new HatSelection("hands_up_pot", decoratedPot(Material.ARMS_UP_POTTERY_SHERD),
                    fromStatistic(Statistic.JUMP, 100000), EPIC))

            .add(new HatSelection("blade_pot", decoratedPot(Material.BLADE_POTTERY_SHERD),
                    fromKilled(100, EntityType.PIGLIN_BRUTE), LEGENDARY))
            .build();

    // Animated Hats

    private static final List<CosmeticSelection<?>> ANIMATED_HATS = ImmutableList.<CosmeticSelection<?>>builder()
            .add(new HatSelection("miner_pot_animated", AnimatedItem.of(20,
                    decoratedPot(Material.MINER_POTTERY_SHERD), decoratedPot(Material.PRIZE_POTTERY_SHERD)
            ), fromMined(15, Material.EMERALD_ORE), LEGENDARY))
            .build();

    // Selections

    private static final Map<Cosmetic, List<CosmeticSelection<?>>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection<?>>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, join(PROJECTILE_TRAIL, BaseTrail.PROJECTILE_TRAIL, "1_19_R3"))
            .put(BaseTrail.GROUND_TRAIL, join(GROUND_TRAIL, BaseTrail.GROUND_TRAIL, "1_19_R3"))
            .put(BaseTrail.SOUND_TRAIL, getForVersion(BaseTrail.SOUND_TRAIL, "1_19_R3"))

            .put(BaseShape.ALL, getForVersion(BaseShape.ALL, "1_19_R3"))

            .put(BaseHat.NORMAL, join(NORMAL_HATS, BaseHat.NORMAL, "1_19_R3"))
            .put(BaseHat.ANIMATED, join(ANIMATED_HATS, BaseHat.ANIMATED, "1_19_R3"))

            .put(BaseGadget.INSTANCE, getForVersion(BaseGadget.INSTANCE, "1_19_R3"))
            .build();


    @Override
    public Map<Cosmetic, List<CosmeticSelection<?>>> getAllSelections() {
        return SELECTIONS;
    }
}
