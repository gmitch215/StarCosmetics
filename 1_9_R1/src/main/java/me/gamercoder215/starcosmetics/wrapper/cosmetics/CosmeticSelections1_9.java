package me.gamercoder215.starcosmetics.wrapper.cosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.*;
import me.gamercoder215.starcosmetics.api.player.PlayerCompletion;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.util.selection.GadgetSelection;
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.util.selection.TrailSelection;
import org.bukkit.*;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.metadata.FixedMetadataValue;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

public final class CosmeticSelections1_9 implements CosmeticSelections {

    // Cosmetic Maps

    // Trails

    private static final List<CosmeticSelection> PROJECTILE_TRAILS = ImmutableList.<CosmeticSelection>builder()
            // Items + Fancy Items
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
            .add(new TrailSelection("flint", BaseTrail.PROJECTILE_TRAIL, Material.FLINT,
                    CompletionCriteria.fromMined(80, Material.GRAVEL), CosmeticRarity.COMMON))

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
            .add(new TrailSelection("ender_eye", BaseTrail.PROJECTILE_TRAIL, Material.EYE_OF_ENDER,
                    CompletionCriteria.fromKilled(250, EntityType.ENDERMAN), CosmeticRarity.UNCOMMON))
            .add(new TrailSelection("nether_brick", BaseTrail.PROJECTILE_TRAIL, Material.NETHER_BRICK,
                    CompletionCriteria.fromMined(500, Material.NETHERRACK), CosmeticRarity.UNCOMMON))
            .add(new TrailSelection("prismarine", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(Material.PRISMARINE_CRYSTALS, Material.PRISMARINE_SHARD),
                    CompletionCriteria.fromMined(100, Material.PRISMARINE), CosmeticRarity.UNCOMMON))
            
            .add(new TrailSelection("super_diamond", BaseTrail.PROJECTILE_TRAIL, Material.DIAMOND_BLOCK,
                    CompletionCriteria.fromMined(265, Material.DIAMOND_ORE), CosmeticRarity.RARE))
            .add(new TrailSelection("super_emerald", BaseTrail.PROJECTILE_TRAIL, Material.EMERALD_BLOCK,
                    CompletionCriteria.fromMined(190, Material.EMERALD_ORE), CosmeticRarity.RARE))
            .add(new TrailSelection("stone_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:stone_sword",
                    CompletionCriteria.fromMined(1200, Material.STONE, Material.COBBLESTONE), CosmeticRarity.RARE))
            .add(new TrailSelection("rabbits", BaseTrail.PROJECTILE_TRAIL, Material.RABBIT_FOOT,
                    CompletionCriteria.fromKilled(120, EntityType.RABBIT), CosmeticRarity.RARE))

            .add(new TrailSelection("chorus_fruit", BaseTrail.PROJECTILE_TRAIL, Material.CHORUS_FRUIT,
                    CompletionCriteria.fromMined(1000, StarMaterial.END_STONE.find()), CosmeticRarity.EPIC))
            
            .add(new TrailSelection("diamond_sword", BaseTrail.PROJECTILE_TRAIL, "fancy_item:diamond_sword",
                    CompletionCriteria.fromKilled(75, EntityType.WITHER), CosmeticRarity.LEGENDARY))
            .add(new TrailSelection("nether_star", BaseTrail.PROJECTILE_TRAIL, Material.NETHER_STAR,
                    CompletionCriteria.fromKilled(100, EntityType.WITHER), CosmeticRarity.LEGENDARY))

            .add(new TrailSelection("ender_crystals", BaseTrail.PROJECTILE_TRAIL, Material.END_CRYSTAL,
                    CompletionCriteria.fromKilled(120, EntityType.ENDER_DRAGON), CosmeticRarity.MYTHICAL))

            .add(new TrailSelection("command_blocks", BaseTrail.PROJECTILE_TRAIL,
                    Arrays.asList(StarMaterial.COMMAND_BLOCK.find(), StarMaterial.CHAIN_COMMAND_BLOCK.find(), StarMaterial.REPEATING_COMMAND_BLOCK.find()),
                    CompletionCriteria.of(p -> new StarPlayer(p).hasCompleted(PlayerCompletion.NETHER_ROOF)), CosmeticRarity.SPECIAL))

            // Particles
            .add(new TrailSelection("heart", BaseTrail.PROJECTILE_TRAIL, Particle.HEART,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 15), CosmeticRarity.COMMON))

            .add(new TrailSelection("flame", BaseTrail.PROJECTILE_TRAIL, Particle.FLAME,
                    CompletionCriteria.fromKilled(65, EntityType.BLAZE), CosmeticRarity.OCCASIONAL))

            .add(new TrailSelection("lava", BaseTrail.PROJECTILE_TRAIL, Particle.LAVA,
                    CompletionCriteria.fromMined(400, Material.NETHERRACK), CosmeticRarity.UNCOMMON))
            .add(new TrailSelection("notes", BaseTrail.PROJECTILE_TRAIL, Particle.NOTE,
                    CompletionCriteria.fromCrafted(85, Material.NOTE_BLOCK, Material.JUKEBOX), CosmeticRarity.UNCOMMON))
            
            .add(new TrailSelection("enchantment", BaseTrail.PROJECTILE_TRAIL, Particle.ENCHANTMENT_TABLE,
                    CompletionCriteria.fromStatistic(Statistic.ITEM_ENCHANTED, 125), CosmeticRarity.RARE))
            .add(new TrailSelection("anger", BaseTrail.PROJECTILE_TRAIL, Particle.VILLAGER_ANGRY, 
                    CompletionCriteria.fromKilled(65, EntityType.IRON_GOLEM), CosmeticRarity.RARE))
            
            .add(new TrailSelection("dragon_breath", BaseTrail.PROJECTILE_TRAIL, Particle.DRAGON_BREATH,
                    CompletionCriteria.fromKilled(10, EntityType.ENDER_DRAGON), CosmeticRarity.EPIC))
            // Entities
            .add(new TrailSelection("chickens", BaseTrail.PROJECTILE_TRAIL, EntityType.CHICKEN,
                    CompletionCriteria.fromKilled(200, EntityType.CHICKEN), CosmeticRarity.OCCASIONAL))
            .build();
    
    // Ground Trails

    private static final List<CosmeticSelection> GROUND_TRAILS = ImmutableList.<CosmeticSelection>builder()
            .add(new TrailSelection("poppy", BaseTrail.GROUND_TRAIL, StarMaterial.POPPY.find(),
                    CompletionCriteria.fromMined(120, StarMaterial.POPPY.find()), CosmeticRarity.OCCASIONAL))
            
            .add(new TrailSelection("lava", BaseTrail.GROUND_TRAIL, Particle.LAVA,
                    CompletionCriteria.fromKilled(525, EntityType.BLAZE), CosmeticRarity.RARE))
            
            .add(new TrailSelection("water", BaseTrail.GROUND_TRAIL, Particle.WATER_SPLASH,
                    CompletionCriteria.fromKilled(625, EntityType.SQUID), CosmeticRarity.RARE))
            .build();

    // Sound Trails

    private static final List<CosmeticSelection> SOUND_TRAILS = ImmutableList.<CosmeticSelection>builder()
            .add(new TrailSelection("slime", BaseTrail.SOUND_TRAIL, Sound.BLOCK_SLIME_PLACE,
                    CompletionCriteria.fromKilled(100, EntityType.SLIME) ,CosmeticRarity.OCCASIONAL))
            .build();

    // Shapes

    // Small Rings

    private static final List<CosmeticSelection> SMALL_RINGS = ImmutableList.<CosmeticSelection>builder()
            .add(new ParticleSelection("heart", BaseShape.SMALL_RING, Particle.HEART,
                    CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 15), CosmeticRarity.COMMON))
            .add(new ParticleSelection("flame", BaseShape.SMALL_RING, Particle.FLAME,
                    CompletionCriteria.fromKilled(80, EntityType.BLAZE), CosmeticRarity.OCCASIONAL))
            .build();

    // Gadgets

    // Click Gadgets

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

    private static final Map<Cosmetic, List<CosmeticSelection>> SELECTIONS = ImmutableMap.<Cosmetic, List<CosmeticSelection>>builder()
            .put(BaseTrail.PROJECTILE_TRAIL, PROJECTILE_TRAILS)
            .put(BaseTrail.GROUND_TRAIL, GROUND_TRAILS)
            .put(BaseTrail.SOUND_TRAIL, SOUND_TRAILS)

            .put(BaseShape.SMALL_RING, SMALL_RINGS)

            .put(BaseGadget.CLICK_GADGET, CLICK_GADGETS)
            .build();

    @Override
    public Map<Cosmetic, List<CosmeticSelection>> getAllSelections() {
        return SELECTIONS;
    }
}
