package me.gamercoder215.starcosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import me.gamercoder215.starcosmetics.api.CompletionCriteria;
import me.gamercoder215.starcosmetics.api.Rarity;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticRegistry;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.Structure;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;
import me.gamercoder215.starcosmetics.api.player.SoundEventSelection;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.events.ClickEvents;
import me.gamercoder215.starcosmetics.events.CompletionEvents;
import me.gamercoder215.starcosmetics.events.CosmeticEvents;
import me.gamercoder215.starcosmetics.placeholders.StarPlaceholders;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.inventory.InventorySelector;
import me.gamercoder215.starcosmetics.util.inventory.ItemBuilder;
import me.gamercoder215.starcosmetics.util.inventory.StarInventoryUtil;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapper;
import org.bukkit.*;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.serialization.ConfigurationSerializable;
import org.bukkit.configuration.serialization.ConfigurationSerialization;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
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
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.util.Generator.cw;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getCosmeticSelections;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.getWrapper;

public final class StarCosmetics extends JavaPlugin implements StarConfig, CosmeticRegistry {

    private static final Wrapper w = getWrapper();

    private boolean checkCompatible() {
        if (!Wrapper.isCompatible()) {
            getLogger().severe("StarCosmetics is not compatible with: " + Bukkit.getBukkitVersion() + " (Expected Wrapper" + Wrapper.getServerVersion() + ")");
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
        InventorySelector.loadInventories();
    }

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
        getLogger().info("Loaded Tasks...");

        loadPlaceholders();
        getLogger().info("Loaded Addons...");

        getLogger().info("Done!");
    }

    @Override
    public void onDisable() {
        // Remove Cosmetic Fireworks
        for (World w : Bukkit.getWorlds())
            for (Firework f : w.getEntitiesByClass(Firework.class))
                if (f.hasMetadata("cosmetic")) f.remove();

        try { ASYNC_TICK_RUNNABLE.cancel(); } catch (IllegalStateException ignored) {}
        getLogger().info("Cancelled Tasks...");

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
    public String getMessage(String key) {
        return get("plugin.prefix") + get(key);
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

    static final Map<PetType, PetInfo> PET_MAP = ImmutableMap.<PetType, PetInfo>builder()
            .put(PetType.PIG, of(
                    Rarity.COMMON, CompletionCriteria.fromStatistic(Statistic.ANIMALS_BRED, 100),
                    petIcon(Material.PORK, "pig", "constants.pet.pig"), "Pig")
            )
            .put(PetType.GOLEM, of(
                    Rarity.RARE, CompletionCriteria.fromMined(1200, Material.IRON_ORE),
                    petIcon(Material.IRON_BLOCK, "golem", "constants.pet.golem"), "Iron Golem")
            )

            // Head Pets

            .put(PetType.BEE, of(
                    "bee_pet", "Bee", Rarity.OCCASIONAL,
                    petIcon("bee_pet", "bee", "constants.pet.bee"), CompletionCriteria.fromPlaytime(1728000)
            ))

            .build();

    private static ItemStack petIcon(Material m, String key, String displayKey) {
        return ItemBuilder.of(m)
                .name(ChatColor.GOLD + Wrapper.get(displayKey))
                .id("choose:pet")
                .nbt(nbt -> nbt.set("pet", key))
                .build();
    }

    private static ItemStack petIcon(String headKey, String key, String displayKey) {
        return ItemBuilder.of(StarInventoryUtil.getHead(headKey))
                .name(ChatColor.GOLD + Wrapper.get(displayKey))
                .id("choose:pet")
                .nbt(nbt -> nbt.set("pet", key))
                .build();
    }


    @Override
    public @NotNull Map<PetType, PetInfo> getAllPets() {
        return PET_MAP;
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
            StarConfig.updateCache();
        }

        @EventHandler
        public void onLeave(PlayerQuitEvent e) {
            Player p = e.getPlayer();
            StarPlayer sp = new StarPlayer(p);
            sp.getSelectionLimit(); // Updates Config Limit
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
