package me.gamercoder215.starcosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.jeff_media.updatechecker.UpdateCheckSource;
import com.jeff_media.updatechecker.UpdateChecker;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRegistry;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.Structure;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.api.player.StarPlayerUtil;
import me.gamercoder215.starcosmetics.events.ClickEvents;
import me.gamercoder215.starcosmetics.events.CompletionEvents;
import me.gamercoder215.starcosmetics.events.CosmeticEvents;
import me.gamercoder215.starcosmetics.placeholders.StarPlaceholders;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.StarMaterial;
import me.gamercoder215.starcosmetics.util.inventory.InventorySelector;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapper;
import me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections;
import org.bstats.bukkit.Metrics;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.player.PlayerStatisticIncrementEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseShape.circle;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.util.Generator.cw;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.head;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.petIcon;

public final class StarCosmetics extends JavaPlugin implements StarConfig, CosmeticRegistry {

    private static final Wrapper w = getWrapper();

    private boolean checkCompatible() {
        if (!Wrapper.isCompatible()) {
            getLogger().severe("StarCosmetics is not compatible with: " + Bukkit.getBukkitVersion() + " (Expected Wrapper" + Wrapper.getServerVersion() + ")");
            if (Wrapper.isOutdatedSubversion()) {
                getLogger().severe("-----------------------");
                String ver = Bukkit.getBukkitVersion().split("-")[0];
                getLogger().severe("You are using an outdated subversion of MC (" + ver + ").");
                getLogger().severe("Please update to the latest version of Minecraft.");
            }
            Bukkit.getPluginManager().disablePlugin(this);
            return false;
        }

        return true;
    }

    private void registerEvents() {
        new ClickEvents(this);
        new CompletionEvents(this);
        new CosmeticEvents(this);
        new InternalEvents(this);

        w.registerEvents();
    }

    private static FileConfiguration config;

    private static final List<Class<? extends ConfigurationSerializable>> SERIALIZABLE = ImmutableList.<Class<? extends ConfigurationSerializable>>builder()
            .add(SoundEventSelection.class)
            .build();

    private void loadConstructors() {
        ItemBuilder.loadItems();
        InventorySelector.loadInventories();
        loadPetIcons();
    }

    private static final int BSTATS_ID = 17108;

    @Override
    public void onEnable() {
        if (!checkCompatible()) return;
        saveDefaultConfig();

        config = StarConfig.loadConfig();
        getLogger().info("Loaded Files...");

        registerEvents();
        getAvailableStructures(); // Load Structure Cache
        cw = getCommandWrapper();
        SERIALIZABLE.forEach(ConfigurationSerialization::registerClass);
        loadConstructors();

        getLogger().info("Loaded Classes...");

        ASYNC_TICK_RUNNABLE.runTaskTimerAsynchronously(this, 0, 1);
        SYNC_TICK_RUNNABLE.runTaskTimer(this, 0, 1);
        getLogger().info("Loaded Tasks...");

        // Update Checker
        new UpdateChecker(this, UpdateCheckSource.GITHUB_RELEASE_TAG, "GamerCoder215/StarCosmetics")
                .setDownloadLink("https://github.com/GamerCoder215/StarCosmetics/releases/latest")
                .setNotifyOpsOnJoin(true)
                .setChangelogLink("https://github.com/GamerCoder215/StarCosmetics/releases/latest")
                .setUserAgent("Java 8 StarCosmetics User Agent")
                .setColoredConsoleOutput(true)
                .setDonationLink("https://www.patreon.com/teaminceptus")
                .setNotifyRequesters(true)
                .checkEveryXHours(1)
                .checkNow();

        // bStats
        new Metrics(this, BSTATS_ID);

        getLogger().info("Loaded Dependencies...");

        loadPlaceholders();
        getLogger().info("Loaded Addons...");

        getLogger().info("Done!");

        postChecks();
    }

    private void postChecks() {
        try {
            Properties props = new Properties();
            props.load(Files.newInputStream(Paths.get("server.properties")));

            boolean flight = Boolean.parseBoolean(props.getProperty("allow-flight"));
            if (!flight) getLogger().warning("allow-flight is disabled in server.properties; This may cause issues with some cosmetics. Please enable it.");

        } catch (IOException e) {
            StarConfig.print(e);
        }
    }

    @Override
    public void onDisable() {
        // Remove Cosmetic Fireworks
        for (World w : Bukkit.getWorlds())
            for (Firework f : w.getEntitiesByClass(Firework.class))
                if (f.hasMetadata("cosmetic")) f.remove();

        try {
            ASYNC_TICK_RUNNABLE.cancel();
            SYNC_TICK_RUNNABLE.cancel();
        } catch (IllegalStateException ignored) {}
        getLogger().info("Cancelled Tasks...");

        StarConfig.updateCache();
        StarPlayerUtil.clearPets();

        getLogger().info("Removed Data...");

        getLogger().info("Done!");
    }

    // Config Implementation

    @Override
    public String getLanguage() {
        return config.getString("language", "en");
    }

    @Override
    public String get(String key) {
        Properties p = new Properties();
        String lang = getLanguage().equalsIgnoreCase("en") ? "" : "_" + getLanguage();

        try (InputStream str = getClass().getResourceAsStream("/lang/starcosmetics" + lang + ".properties")) {
            p.load(str);
            str.close();
            return ChatColor.translateAlternateColorCodes('&', p.getProperty(key, "Unknown Value"));
        } catch (IOException e) {
            print(e);
            return "Unknown Value";
        }
    }

    private void loadPlaceholders() {
        if (Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            getLogger().info("Placeholder API Found! Hooking...");
            new StarPlaceholders(this);
            getLogger().info("Hooked into Placeholder API!");
        }
    }

    @Override
    public void updatePluginCache() {
        STAR_PLAYER_CACHE.clear();
    }

    // Other Utilities

    public static String getServerVersion() {
        return Wrapper.getServerVersion();
    }

    public static boolean isCompatible() {
        return Wrapper.isCompatible();
    }

    public static CommandWrapper getCommandWrapper() {
        if (!isCompatible()) return null;
        final int cmdV;

        String v = config.get("functionality.command-version", "auto").toString();
        switch (v) {
            case "1":
                cmdV = 1;
                break;
            case "2":
                cmdV = 2;
                break;
            default:
                cmdV = w.getCommandVersion();
                break;
        }

        try {
            return (CommandWrapper) Class.forName("me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapperV" + cmdV)
                    .getConstructor(Plugin.class)
                    .newInstance(StarConfig.getPlugin());
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

    public static void print(Throwable t) {
        StarConfig.print(t);
    }


    public static StarPlayer getCached(@NotNull Player p) {
        StarPlayer sp = STAR_PLAYER_CACHE.get(p.getUniqueId());
        if (sp == null) {
            sp = new StarPlayer(p);
            STAR_PLAYER_CACHE.put(p.getUniqueId(), sp);
        }
        return sp;
    }

    public static final Map<UUID, StarPlayer> STAR_PLAYER_CACHE = new HashMap<>();

    public static final Runnable ASYNC_TICK_TASK = () -> {
        for (Player p : Bukkit.getOnlinePlayers())
            if (!STAR_PLAYER_CACHE.containsKey(p.getUniqueId()))
                STAR_PLAYER_CACHE.put(p.getUniqueId(), new StarPlayer(p));

        for (StarPlayer sp : STAR_PLAYER_CACHE.values()) {
            if (!sp.isOnline()) {
                STAR_PLAYER_CACHE.remove(sp.getUniqueId());
                continue;
            }

            sp.tick();
        }
    };

    public static final BukkitRunnable ASYNC_TICK_RUNNABLE = new BukkitRunnable() {
        @Override
        public void run() {
            ASYNC_TICK_TASK.run();
        }
    };

    public static final Runnable SYNC_TICK_TASK = () -> {
        for (Map.Entry<UUID, Pet> entry : StarPlayerUtil.getPets().entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p == null) continue;

            Pet pet = entry.getValue();
            PetType type = pet.getPetType();

            LivingEntity petEntity = pet.getEntity();
            if (r.nextInt(600) == 1 && type.getAmbientSound() != null)
                p.getWorld().playSound(petEntity.getLocation(), type.getAmbientSound(), 3F, type.getAmbientPitch());
        }
    };

    public static final BukkitRunnable SYNC_TICK_RUNNABLE = new BukkitRunnable() {
        @Override
        public void run() {
            SYNC_TICK_TASK.run();
        }
    };

    @Override
    @NotNull
    public List<CosmeticLocation<?>> getAllFor(Class<? extends Cosmetic> parentClass) {
        List<CosmeticLocation<?>> locs = new ArrayList<>();
        if (parentClass == null) return locs;

        if (Cosmetic.class.equals(parentClass))
            return getCosmeticSelections().getAllSelections()
                    .entrySet()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toList());

        Map<Cosmetic, List<CosmeticSelection<?>>> selections = getCosmeticSelections().getAllSelections();
        for (Map.Entry<Cosmetic, List<CosmeticSelection<?>>> entry : selections.entrySet())
            if (parentClass.isInstance(entry.getKey())) locs.addAll(entry.getValue());

        Function<CosmeticLocation<?>, Rarity> c = CosmeticLocation::getRarity;
        locs.sort(Comparator.comparing(c).thenComparing(CosmeticLocation::getDisplayName));

        return locs;
    }

    @Override
    public @NotNull List<CosmeticLocation<?>> getAllFor(@Nullable Cosmetic parent) {
        List<CosmeticLocation<?>> locs = new ArrayList<>();
        if (parent == null) return locs;

        Map<Cosmetic, List<CosmeticSelection<?>>> selections = getCosmeticSelections().getAllSelections();
        for (Map.Entry<Cosmetic, List<CosmeticSelection<?>>> entry : selections.entrySet())
            if (entry.getKey().getNamespace().equals(parent.getNamespace())) locs.addAll(entry.getValue());

        Function<CosmeticLocation<?>, Rarity> c = CosmeticLocation::getRarity;
        locs.sort(Comparator.comparing(c).thenComparing(CosmeticLocation::getDisplayName));

        return locs;
    }


    @Override
    public List<Cosmetic> getAllParents() {
        return Constants.PARENTS;
    }

    @Override
    public Cosmetic getByNamespace(@NotNull String key) {
        if (key == null) return null;
        return Constants.PARENTS
                .stream()
                .filter(c -> c.getNamespace().equalsIgnoreCase(key))
                .findFirst()
                .orElse(null);
    }

    private static final String[] STRUCTURE_FILES = {
            "ores",
            "reinforced_portal",
            "tree",
            "flowers"
    };

    public static final Set<StructureInfo> STRUCTURE_CACHE = new HashSet<>();

    @Override
    public @NotNull Set<StructureInfo> getAvailableStructures() {
        if (!STRUCTURE_CACHE.isEmpty()) return ImmutableSet.copyOf(STRUCTURE_CACHE);

        Set<Structure> set = new HashSet<>();

        for (String structF : STRUCTURE_FILES) {
            InputStream struct = StarCosmetics.class.getResourceAsStream("/structures/" + structF + ".scs");
            StructureReader reader = StructureReader.getStructureReader(struct);
            set.add(reader.read());

            try { reader.close(); } catch (IOException e) { StarConfig.print(e); }
        }

        STRUCTURE_CACHE.addAll(set.stream()
                .map(Structure::getInfo)
                .filter(StructureInfo::isCompatible)
                .collect(Collectors.toSet()));

        return ImmutableSet.copyOf(STRUCTURE_CACHE);
    }

    @Override
    public void addStructure(@NotNull InputStream stream) {
        StructureReader reader = StructureReader.getStructureReader(stream);

        Structure struct = reader.read();
        STRUCTURE_CACHE.add(struct.getInfo());

        try {
            reader.close();
        } catch (IOException e) {
            StarConfig.print(e);
        }
    }

    private static void loadPetIcons() {
        Map<PetType, PetInfo> pets = ImmutableMap.<PetType, PetInfo>builder()
                .put(PetType.ELEPHANT, of(
                        "Elephant", Rarity.COMMON,
                        petIcon("elephant_pet", "Elephant"), fromDistance(Statistic.WALK_ONE_CM, 100 * 1000)
                ))

                .put(PetType.BEE, of(
                        "Bee", Rarity.OCCASIONAL,
                        petIcon("bee_pet", "Bee"), fromPlaytime(1728000), stand -> {
                            if (r.nextInt(100) < 10) w.spawnFakeItem(StarMaterial.POPPY.findStack(), head(stand), 20);
                        }
                ))
                .put(PetType.MOUSE, of(
                        "Mouse", Rarity.OCCASIONAL,
                        petIcon("mouse_pet", "Mouse"), fromKilled(50, EntityType.SILVERFISH)
                ))

                .put(PetType.GIRAFFE, of(
                        "Giraffe", Rarity.UNCOMMON,
                        petIcon("giraffe_pet", "Giraffe"), fromMined(2000, Material.SAND), stand -> {
                            if (r.nextInt(100) < 10) w.spawnFakeItem(r.nextBoolean() ? new ItemStack(Material.SAND) : StarMaterial.RED_SAND.findStack(), head(stand), 20);
                        }
                ))
                .put(PetType.LLAMA, of(
                        "Llama", Rarity.UNCOMMON,
                        petIcon("llama_pet", "Llama"), fromDistance(Statistic.WALK_ONE_CM, 100 * 1000 * 20), stand -> {
                            if (r.nextInt(100) < 5) circle(head(stand), Particle.FLAME, 4, 0.25);
                        }
                ))

                .put(PetType.DOLPHIN, of(
                        "Dolphin", Rarity.RARE,
                        petIcon("dolphin_pet", "Dolphin"), fromKilled(100, EntityType.GUARDIAN), stand -> {
                            if (r.nextBoolean()) stand.getWorld().spawnParticle(Particle.DRIP_WATER, head(stand), 1, 0, 0, 0, 0);
                        }
                ))
                .put(PetType.SLIME, of(
                        "Slime", Rarity.RARE,
                        petIcon("slime_pet", "Slime"), fromCrafted(400, Material.SLIME_BLOCK), stand -> {
                            if (r.nextInt(100) < 10) circle(head(stand), Particle.SLIME, 3, 0.5);
                        }
                ))

                .put(PetType.RABBIT, of(
                        "Rabbit", Rarity.EPIC,
                        petIcon("rabbit_pet", "Rabbit"), fromStatistic(Statistic.ANIMALS_BRED, 600), stand -> {
                            if (r.nextInt(100) < 5) circle(head(stand), Particle.CRIT_MAGIC, 4, 0.75);
                        }
                ))
                .put(PetType.PANDA, of(
                        "Panda", Rarity.EPIC,
                        petIcon("panda_pet", "Panda"), fromStatistic(Statistic.TRADED_WITH_VILLAGER, 150), stand -> {
                            if (r.nextInt(100) < 10) circle(head(stand), Particle.HEART, 3, 0.25);
                        }
                ))
                .put(PetType.BLAZE, of(
                        "Blaze", Rarity.EPIC,
                        petIcon("blaze_pet", "Blaze"), fromKilled(700, EntityType.BLAZE), stand -> {
                            if (r.nextInt(100) < 25) stand.getWorld().spawnParticle(Particle.FLAME, head(stand), 1, 0, 0, 0, 0);
                        }
                ))

                .put(PetType.POLAR_BEAR, of(
                        "Polar Bear", Rarity.LEGENDARY,
                        petIcon("polar_bear_pet", "Polar Bear"), fromMined(50000, Material.SNOW_BLOCK), stand -> {
                            if (r.nextInt(100) < 10) circle(head(stand), Particle.SNOW_SHOVEL, 3, 0.75);
                            if (r.nextInt(100) < 5) w.spawnFakeItem(new ItemStack(Material.ICE), head(stand), 20);
                        }
                ))
                .put(PetType.TARDIGRADE, of(
                        "Tardigrade", Rarity.LEGENDARY,
                        petIcon("tardigrade_pet", "Tardigrade"), fromStatistic(Statistic.ANIMALS_BRED, 10000), stand -> {
                            if (r.nextBoolean()) stand.getWorld().spawnParticle(Particle.DRAGON_BREATH, head(stand), 1, 0, 0, 0, 0);
                        }
                ))
                .put(PetType.TIGER, of(
                        "Tiger", Rarity.LEGENDARY,
                        petIcon("tiger_pet", "Tiger"), fromKilled(5000, EntityType.SKELETON), stand -> {
                            if (r.nextInt(100) < 5) w.spawnFakeItem(new ItemStack(Material.BONE), head(stand), 5);
                        }
                ))

                .put(PetType.CAPYBARA, of(
                        "Capybara", Rarity.MYTHICAL,
                        petIcon("capybara_pet", "Capybara"), fromMined(1000000, StarMaterial.OAK_LOG.find()), stand -> {
                            if (r.nextInt(100) < 5) circle(head(stand), new ItemStack(Material.GOLDEN_CARROT), 3, 0.5);
                            if (r.nextInt(100) < 10) w.spawnFakeItem(new ItemStack(Material.APPLE), head(stand), 10);
                        }
                ))

                .build();

        CosmeticSelections.PET_MAP.putAll(pets);

        Wrapper.getCosmeticSelections().loadPets();
    }


    @Override
    public @NotNull Map<PetType, PetInfo> getAllPets() {
        return CosmeticSelections.PET_MAP;
    }

    private static final class InternalEvents implements Listener {

        private final StarCosmetics plugin;

        public InternalEvents(StarCosmetics plugin) {
            this.plugin = plugin;
            Bukkit.getPluginManager().registerEvents(this, plugin);
        }

        @EventHandler
        public void onStatistic(PlayerStatisticIncrementEvent e) {
            Player p = e.getPlayer();
            StarCosmetics.STAR_PLAYER_CACHE.remove(p.getUniqueId());
        }

        @EventHandler
        public void onLeave(PlayerQuitEvent e) {
            Player p = e.getPlayer();
            StarPlayer sp = new StarPlayer(p);
            sp.getSelectionLimit(); // Updates Config Limit

            StarPlayerUtil.removePet(p);
        }

        @EventHandler
        public void onDamage(EntityDamageEvent e) {
            Entity en = e.getEntity();

            if (en.hasMetadata("immune_fall") && e.getCause() == EntityDamageEvent.DamageCause.FALL) {
                e.setCancelled(true);
                en.removeMetadata("immune_fall", plugin);
            }
        }

    }
}
