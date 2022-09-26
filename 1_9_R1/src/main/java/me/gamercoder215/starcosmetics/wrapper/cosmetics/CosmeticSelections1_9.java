package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.StarMaterial;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseShape.ParticleSelection;
import me.gamercoder215.starcosmetics.api.cosmetics.BaseTrail.TrailSelection;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.List;
import java.util.Map;

public class CosmeticSelections1_9 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails

    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            // Items
            .add(new TrailSelection("red_flowers", BaseTrail.PROJECTILE_TRAIL, StarMaterial.POPPY.find(),
                    CompletionCriteria.fromMined(30, StarMaterial.POPPY.find()), CosmeticRarity.COMMON))
            .add(new TrailSelection("ghast", BaseTrail.PROJECTILE_TRAIL, Material.GHAST_TEAR,
                    CompletionCriteria.fromKilled(35, EntityType.GHAST), CosmeticRarity.COMMON))
            .add(new TrailSelection("wheat", BaseTrail.PROJECTILE_TRAIL, Material.WHEAT,
                    CompletionCriteria.fromMined(60, Material.WHEAT), CosmeticRarity.COMMON))
            .add(new TrailSelection("apples", BaseTrail.PROJECTILE_TRAIL, Material.APPLE,
                    CompletionCriteria.fromMined(70, StarMaterial.OAK_LOG.find()), CosmeticRarity.COMMON))
            .add(new TrailSelection("beef", BaseTrail.PROJECTILE_TRAIL, Material.COOKED_BEEF,
                    CompletionCriteria.fromKilled(55, EntityType.COW), CosmeticRarity.COMMON))

            .add(new TrailSelection("iron", BaseTrail.PROJECTILE_TRAIL, Material.IRON_INGOT,
                    CompletionCriteria.fromMined(185, Material.IRON_ORE), CosmeticRarity.OCCASIONAL))
            .add(new TrailSelection("redstone", BaseTrail.PROJECTILE_TRAIL, Material.REDSTONE,
                    CompletionCriteria.fromMined(145, Material.REDSTONE_ORE), CosmeticRarity.OCCASIONAL))
            .add(new TrailSelection("gold", BaseTrail.PROJECTILE_TRAIL, Material.GOLD_INGOT,
                    CompletionCriteria.fromMined(115, Material.GOLD_ORE), CosmeticRarity.OCCASIONAL))

            .add(new TrailSelection("diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND,
                    CompletionCriteria.fromMined(110, Material.DIAMOND_ORE), CosmeticRarity.UNCOMMON))
            .add(new TrailSelection("emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD,
                    CompletionCriteria.fromMined(90, Material.EMERALD_ORE), CosmeticRarity.UNCOMMON))
            .add(new TrailSelection("super_redstone", BaseTrail.PROJECTILE_TRAIL, Material.REDSTONE_BLOCK,
                    CompletionCriteria.fromMined(310, Material.REDSTONE_ORE), CosmeticRarity.UNCOMMON))
            .add(new TrailSelection("super_gold", BaseTrail.PROJECTILE_TRAIL, Material.GOLD_BLOCK,
                    CompletionCriteria.fromMined(255, Material.GOLD_ORE), CosmeticRarity.UNCOMMON))

            .add(new TrailSelection("super_diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND_BLOCK,
                    CompletionCriteria.fromMined(265, Material.DIAMOND_ORE), CosmeticRarity.RARE))
            .add(new TrailSelection("super_emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD_BLOCK,
                    CompletionCriteria.fromMined(190, Material.EMERALD_ORE), CosmeticRarity.RARE))

            // Particles
            .add(new TrailSelection("heart", BaseTrail.PROJECTILE_TRAIL, Particle.HEART,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 10), CosmeticRarity.COMMON))
            .add(new TrailSelection("flame", BaseTrail.PROJECTILE_TRAIL, Particle.FLAME,
                    CompletionCriteria.fromKilled(65, EntityType.BLAZE), CosmeticRarity.OCCASIONAL))

            // Entities
            .add(new TrailSelection("chickens", BaseTrail.PROJECTILE_TRAIL, EntityType.CHICKEN,
                    CompletionCriteria.fromKilled(200, EntityType.CHICKEN), CosmeticRarity.OCCASIONAL))
            .build();

    // Shapes

    private static final List<CosmeticSelection> SMALL_RINGS = ImmutableList.<CosmeticSelection>builder()
            .add(new ParticleSelection("heart", BaseShape.SMALL_RING, Particle.HEART,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 15), CosmeticRarity.COMMON))
            .add(new ParticleSelection("flame", BaseShape.SMALL_RING, Particle.FLAME,
                    CompletionCriteria.fromKilled(80, EntityType.BLAZE), CosmeticRarity.OCCASIONAL))
            .build();

    // Gadgets

    private static final List<CosmeticSelection> CLICK_GADGETS = ImmutableList.<CosmeticSelection>builder()
            .add(GadgetSelection.builder()
                    .info("firework", CompletionCriteria.fromKilled(60, EntityType.CREEPER), CosmeticRarity.COMMON)
                    .item(Material.FIREWORK)
                    .action(e -> {
                        Player p = e.getPlayer();

                        Firework fw = (Firework) p.getWorld().spawnEntity(p.getLocation(), EntityType.FIREWORK);
                        FireworkMeta meta = fw.getFireworkMeta();
                        meta.setPower(6);

                        DyeColor chosen = DyeColor.values()[r.nextInt(DyeColor.values().length)];
                        FireworkEffect effect = FireworkEffect.builder()
                                .withColor(chosen.getFireworkColor())
                                .withFade(chosen.getColor())
                                .with(FireworkEffect.Type.BALL)
                                .trail(true)
                                .build();

                        meta.addEffect(effect);

                        fw.setFireworkMeta(meta);
                        fw.setMetadata("cosmetic", new FixedMetadataValue(StarConfig.getPlugin(), true));
                    })
                    .build())
            .build();

    // Selection Map

    private static final Map<CosmeticKey, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<CosmeticKey, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, PROJECTILE_TRAILS)

            .put(BaseShape.SMALL_RING, SMALL_RINGS)
            .build();

    @Override
    public Map<CosmeticKey, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }
}
