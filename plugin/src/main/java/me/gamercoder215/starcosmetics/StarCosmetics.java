package me.gamercoder215.starcosmetics;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.Gson;
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
import me.gamercoder215.starcosmetics.util.selection.ParticleSelection;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapper;
import me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bstats.bukkit.Metrics;
import org.bstats.charts.SimplePie;
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
import org.bukkit.metadata.MetadataValue;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.plugin.java.JavaPluginLoader;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.jetbrains.annotations.VisibleForTesting;

import java.io.*;
import java.lang.reflect.Constructor;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import static me.gamercoder215.starcosmetics.api.CompletionCriteria.*;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseShape.circle;
import static me.gamercoder215.starcosmetics.api.cosmetics.BaseShape.polygon;
import static me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo.of;
import static me.gamercoder215.starcosmetics.util.Constants.w;
import static me.gamercoder215.starcosmetics.util.Generator.cw;
import static me.gamercoder215.starcosmetics.wrapper.Wrapper.*;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.head;
import static me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections.petIcon;

public final class StarCosmetics extends JavaPlugin implements StarConfig, CosmeticRegistry {

    private static final Gson GSON = new Gson();

    public StarCosmetics() {
        super();
    }

    @VisibleForTesting
    StarCosmetics(@NotNull final JavaPluginLoader loader, @NotNull final PluginDescriptionFile description, @NotNull final File dataFolder, @NotNull final File file) {
        super(loader, description, dataFolder, file);
    }

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

    @VisibleForTesting
    static FileConfiguration config;
    private static FileConfiguration cosmeticsFile;

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
        cosmeticsFile = StarConfig.loadCosmeticsFile();
        getLogger().info("Loaded Files...");

        registerEvents();
        getAvailableStructures(); // Load Structure Cache
        cw = getCommandWrapper();
        SERIALIZABLE.forEach(ConfigurationSerialization::registerClass);
        loadConstructors();
        for (Player p : Bukkit.getOnlinePlayers()) {
            StarPlayerUtil.getCached(p); // Load Player Cache
            w.addPacketInjector(p);
        }

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
        Metrics m = new Metrics(this, BSTATS_ID);
        m.addCustomChart(new SimplePie("language", this::getLanguage));

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
        StarPlayerUtil.onDisable();
        for (Player p : Bukkit.getOnlinePlayers()) w.removePacketInjector(p);

        getLogger().info("Removed Data...");

        getLogger().info("Done!");
    }

    // Config Implementation

    @Override
    public String getLanguage() {
        return config.getString("language", "en");
    }

    @Override
    public void setLanguage(String lang) {
        config.set("language", lang);
        saveConfig();
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
        STRUCTURE_CACHE.clear();
    }

    @Override
    public long getEntityDisappearTime() {
        return config.getLong("cosmetics.entity-disappear-time", 15);
    }

    @Override
    public void setEntityDisappearTime(long time) throws IllegalArgumentException {
        if (time <= 0) throw new IllegalArgumentException("Time must be positive!");

        config.set("cosmetics.entity-disappear-time", time);
        saveConfig();
    }

    @Override
    public long getItemDisappearTime() {
        return config.getLong("cosmetics.item-disappear-time", 10);
    }

    @Override
    public void setItemDisappearTime(long time) throws IllegalArgumentException {
        if (time <= 0) throw new IllegalArgumentException("Time must be positive!");

        config.set("cosmetics.item-disappear-time", time);
        saveConfig();
    }

    @Override
    public long getBlockDisappearTime() {
        return config.getLong("cosmetics.block-disappear-time", 4);
    }

    @Override
    public void setBlockDisappearTime(long time) throws IllegalArgumentException {
        if (time <= 0) throw new IllegalArgumentException("Time must be positive!");

        config.set("cosmetics.block-disappear-time", time);
        saveConfig();
    }

    @Override
    public StructureReader getStructureReader(InputStream stream) {
        return Wrapper.getStructureReader(stream);
    }

    @Override
    public StructureReader getStructureReader(Reader reader) {
        return Wrapper.getStructureReader(reader);
    }

    @Override
    public StructureReader getStructureReader(File file) {
        return Wrapper.getStructureReader(file);
    }

    @Override
    public @NotNull Set<CosmeticLocation<?>> getDisabledCosmetics() {
        List<String> disabled = config.getStringList("cosmetics.disabled");

        return ImmutableSet.copyOf(disabled.stream()
                .map(StarConfig.getRegistry()::getByFullKey)
                .filter(Objects::nonNull)
                .collect(Collectors.toSet()));
    }

    @Override
    public boolean isAmbientPetSoundEnabled() {
        return config.getBoolean("cosmetics.pets.ambient-sound", true);
    }

    @Override
    public void setAmbientPetSoundEnabled(boolean enabled) {
        config.set("cosmetics.pets.ambient-sound", enabled);
        saveConfig();
    }

    @Override
    public double getRequirementMultiplier() {
        return config.getDouble("cosmetics.requirement-multiplier.global", 1.0);
    }

    @Override
    public double getRequirementMultiplier(@Nullable CosmeticLocation<?> loc) {
        if (loc == null) return getRequirementMultiplier();
        return config.getConfigurationSection("cosmetics.requirement-multiplier.cosmetics")
                .getValues(false)
                .entrySet()
                .stream()
                .filter(entry -> entry.getKey().equalsIgnoreCase(loc.getFullKey()) && entry.getValue() instanceof Number)
                .mapToDouble(entry -> ((Number) entry.getValue()).doubleValue())
                .findFirst()
                .orElse(getRequirementMultiplier());
    }

    @Override
    public void setRequirementMultiplier(double multiplier) {
        config.set("cosmetics.requirement-multiplier.global", multiplier);
        saveConfig();
    }

    @Override
    public void setRequirementMultiplier(@Nullable CosmeticLocation<?> loc, double multiplier) {
        if (loc == null) return;
        config.set("cosmetics.requirement-multiplier.cosmetics." + loc.getFullKey(), multiplier);
        saveConfig();
    }

    @Override
    public @NotNull List<OfflinePlayer> getBlacklistedPlayers() {
        return config.getStringList("cosmetics.blacklisted-players").stream()
                .map(StarCosmetics::getPlayer)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @Override
    public void setBlacklistedPlayers(@NotNull Iterable<? extends OfflinePlayer> players) {
        config.set("cosmetics.blacklisted-players", ImmutableSet.copyOf(players).stream()
                .map(OfflinePlayer::getName)
                .collect(Collectors.toList())
        );
        saveConfig();
    }

    @Override
    public @NotNull Set<Sound> getBlacklistedSounds() {
        return ImmutableSet.copyOf(config.getStringList("cosmetics.custom-sound-events.blacklisted-sounds")
                .stream()
                .map(s -> Arrays.stream(Sound.values())
                        .filter(sound -> Pattern.compile(s).matcher(w.getKey(sound)).find())
                        .findFirst()
                        .orElse(null)
                )
                .filter(Objects::nonNull)
                .collect(Collectors.toSet())
        );
    }

    @Override
    public void setBlacklistedSounds(@NotNull Iterable<Sound> sounds) {
        config.set("cosmetics.custom-sound-events.blacklisted-sounds", ImmutableSet.copyOf(sounds).stream()
                .map(w::getKey)
                .collect(Collectors.toList())
        );
        saveConfig();
    }

    @Override
    public @NotNull Set<CosmeticLocation<?>> getCustomCosmetics() {
        Set<CosmeticLocation<?>> cosmetics = new HashSet<>();

        // Particle Shapes
        List<Map<String, Object>> particleShapes = cosmeticsFile.getMapList("particle-shapes").stream()
                .map(map -> map.entrySet().stream()
                        .map(entry -> new AbstractMap.SimpleEntry<>(entry.getKey().toString(), (Object) entry.getValue()))
                        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue))
                )
                .collect(Collectors.toList());

        for (Map<String, Object> map : particleShapes)
            cosmetics.add(new ParticleSelection(map));

        return cosmetics;
    }

    @Override
    public int getInternalMaxHologramLimit() {
        return w.isLegacy() ? 16 : 48;
    }

    @Override
    public int getMaxHologramLimit() {
        return config.getInt("cosmetics.max-hologram-limit", getInternalMaxHologramLimit());
    }

    @Override
    public void setMaxHologramLimit(int limit) {
        if (limit < 5 || limit > getInternalMaxHologramLimit()) throw new IllegalArgumentException("Limit must be between 5 and " + getInternalMaxHologramLimit() + "!");

        config.set("cosmetics.max-hologram-limit", limit);
        saveConfig();
    }

    @Override
    public @NotNull Set<Structure> getCustomStructures() {
        List<String> files = config.getStringList("cosmetics.structures");
        if (files.isEmpty()) return ImmutableSet.of();

        Set<Structure> structures = new HashSet<>();

        for (String file : files) {
            File f = new File(getDataFolder(), file);
            StructureReader r = getStructureReader(f);
            if (r == null) continue;

            structures.add(r.read());
        }

        return ImmutableSet.copyOf(structures);
    }

    @Override
    public boolean isInPvP(@NotNull Player p) {
        AtomicBoolean pvp = new AtomicBoolean(false);
        if (p.hasMetadata("pvp"))
            pvp.compareAndSet(false, p.getMetadata("pvp").stream().anyMatch(MetadataValue::asBoolean));

        if (Bukkit.getPluginManager().getPlugin("PvPManager") != null)
            pvp.compareAndSet(false, StarPvP.isInPvP(p));

        return pvp.get();
    }

    @Override
    public boolean getCanEmoteInPvP() {
        return config.getBoolean("cosmetics.emote-in-pvp");
    }

    @Override
    public void setCanEmoteInPvP(boolean canEmoteInPvP) {
        config.set("cosmetics.emote-in-pvp", canEmoteInPvP);
        saveConfig();
    }

    @Override
    public boolean getCanEmoteInPvE() {
        return config.getBoolean("cosmetics.emote-in-pve");
    }

    @Override
    public void setCanEmoteInPvE(boolean canEmoteInPvE) {
        config.set("cosmetics.emote-in-pve", canEmoteInPvE);
        saveConfig();
    }

    // Other Utilities

    private static OfflinePlayer getPlayer(String name) {
        if (Bukkit.getOnlineMode()) {
            try {
                URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();

                connection.setRequestMethod("GET");
                connection.setRequestProperty("User-Agent", "GamerCoder215/StarCosmetics Java 8");

                int code = connection.getResponseCode();

                if (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_ACCEPTED) {
                    BufferedReader input = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                    StringBuilder builder = new StringBuilder();
                    String line;
                    while ((line = input.readLine()) != null) builder.append(line);

                    return Bukkit.getOfflinePlayer(untrim(
                            String.valueOf(GSON.fromJson(builder.toString(), Map.class).get("id"))
                    ));
                }
            } catch (IOException e) {
                print(e);
            }
        } else
            return Bukkit.getOfflinePlayer(UUID.nameUUIDFromBytes(("OfflinePlayer:" + name).getBytes(StandardCharsets.UTF_8)));
        return null;
    }

    public static UUID untrim(String old) {
        if (old == null || old.length() != 32) return null;
        return UUID.fromString(
                old.substring(0, 8) + "-" +
                old.substring(8, 12) + "-" +
                old.substring(12, 16) + "-" +
                old.substring(16, 20) + "-" +
                old.substring(20, 32)
        );
    }

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
            case "1": cmdV = 1;break;
            case "2": cmdV = 2; break;
            default: cmdV = w.getCommandVersion(); break;
        }

        try {
            Constructor<? extends CommandWrapper> constr = Class.forName("me.gamercoder215.starcosmetics.wrapper.commands.CommandWrapperV" + cmdV)
                    .asSubclass(CommandWrapper.class)
                    .getDeclaredConstructor(Plugin.class);

            constr.setAccessible(true);
            return constr.newInstance(StarConfig.getPlugin());
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

    public static void print(Throwable t) {
        StarConfig.print(t);
    }

    public static final Runnable ASYNC_TICK_TASK = () -> {
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (!STAR_PLAYER_CACHE.containsKey(p.getUniqueId()))
                STAR_PLAYER_CACHE.put(p.getUniqueId(), new StarPlayer(p));

            if (NBTWrapper.of(p.getItemOnCursor()).getBoolean("hat"))
                p.setItemOnCursor(new ItemStack(Material.AIR));

            Arrays.stream(p.getInventory().getStorageContents())
                    .filter(i -> i != null && NBTWrapper.of(i).getBoolean("hat"))
                    .forEach(i -> p.getInventory().remove(i));

            ItemStack offhand = p.getInventory().getItemInOffHand();
            if (NBTWrapper.of(offhand).hasString("gadget")) {
                p.getInventory().setItemInOffHand(null);
                p.getInventory().addItem(offhand);
            }
        }

        for (StarPlayer sp : STAR_PLAYER_CACHE.values()) {
            if (!sp.isOnline()) {
                STAR_PLAYER_CACHE.remove(sp.getUniqueId());
                continue;
            }

            sp.tick();
        }

        for (Map.Entry<UUID, Pet> entry : StarPlayerUtil.getPets().entrySet()) {
            Player p = Bukkit.getPlayer(entry.getKey());
            if (p == null) continue;

            Pet pet = entry.getValue();
            PetType type = pet.getPetType();

            LivingEntity petEntity = pet.getEntity();
            if (StarConfig.getConfig().isAmbientPetSoundEnabled() && r.nextInt(600) == 1 && type.getAmbientSound() != null)
                p.getWorld().playSound(petEntity.getLocation(), type.getAmbientSound(), 1F, type.getAmbientPitch());
        }
    };

    public static final BukkitRunnable ASYNC_TICK_RUNNABLE = new BukkitRunnable() {
        @Override
        public void run() {
            ASYNC_TICK_TASK.run();
        }
    };

    public static final Runnable SYNC_TICK_TASK = () -> {
        for (World w : Bukkit.getWorlds())
            for (Item i : w.getEntitiesByClass(Item.class)) {
                NBTWrapper nbt = NBTWrapper.of(i.getItemStack());
                if (nbt.getBoolean("hat") || nbt.hasString("gadget"))
                    i.remove();
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

        if (Cosmetic.class.equals(parentClass)) {
            List<CosmeticLocation<?>> all = getCosmeticSelections().getAllSelections()
                    .entrySet()
                    .stream()
                    .flatMap(e -> e.getValue().stream())
                    .collect(Collectors.toList());

            all.addAll(getCustomCosmetics());

            return all;
        }

        Map<Cosmetic, List<CosmeticSelection<?>>> selections = getCosmeticSelections().getAllSelections();
        for (Map.Entry<Cosmetic, List<CosmeticSelection<?>>> entry : selections.entrySet())
            if (parentClass.isInstance(entry.getKey())) locs.addAll(entry.getValue());

        for (CosmeticLocation<?> loc : getCustomCosmetics())
            if (parentClass.isInstance(loc.getParent())) locs.add(loc);

        Function<CosmeticLocation<?>, Rarity> c = CosmeticLocation::getRarity;
        locs.sort(Comparator.comparing(c).thenComparing(CosmeticLocation::getDisplayName));

        return ImmutableList.copyOf(locs.stream()
                .filter(l -> !getDisabledCosmetics().contains(l))
                .collect(Collectors.toList())
        );
    }

    @Override
    @NotNull
    public List<CosmeticLocation<?>> getAllFor(Cosmetic parent) {
        List<CosmeticLocation<?>> locs = new ArrayList<>();
        if (parent == null) return locs;

        Map<Cosmetic, List<CosmeticSelection<?>>> selections = getCosmeticSelections().getAllSelections();
        for (Map.Entry<Cosmetic, List<CosmeticSelection<?>>> entry : selections.entrySet())
            if (entry.getKey().equals(parent)) locs.addAll(entry.getValue());

        Function<CosmeticLocation<?>, Rarity> c = CosmeticLocation::getRarity;
        locs.sort(Comparator.comparing(c).thenComparing(CosmeticLocation::getDisplayName));

        return ImmutableList.copyOf(locs.stream()
                .filter(l -> !getDisabledCosmetics().contains(l))
                .collect(Collectors.toList())
        );
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
            "flowers",
            "letter_t",
            "letter_c",
            "gold",
            "iron",
            "diamond",
            "netherite",
            "lodestone_pillar",
            "emerald",
            "letter_y",
            "letter_j",
            "letter_h",
            "letter_o",
            "netherite_beacon"
    };

    public static final Set<StructureInfo> STRUCTURE_CACHE = new HashSet<>();

    @Override
    public @NotNull Set<StructureInfo> getAvailableStructures() {
        if (!STRUCTURE_CACHE.isEmpty()) return ImmutableSet.copyOf(STRUCTURE_CACHE);

        Set<Structure> set = new HashSet<>();

        try {
            for (String structF : STRUCTURE_FILES) {
                InputStream struct = StarCosmetics.class.getResourceAsStream("/structures/" + structF + ".scs");
                StructureReader reader = Wrapper.getStructureReader(struct);

                Structure read = reader.read();
                if (read == null) {
                    reader.close();
                    continue;
                }

                set.add(read);
                reader.close();
            }
        } catch (IOException e) { StarConfig.print(e); }

        set.addAll(getCustomStructures());

        STRUCTURE_CACHE.addAll(set.stream()
                .map(Structure::getInfo)
                .collect(Collectors.toSet()));

        return ImmutableSet.copyOf(STRUCTURE_CACHE);
    }

    @Override
    public void addStructure(@NotNull InputStream stream) {
        StructureReader reader = Wrapper.getStructureReader(stream);

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
                .put(PetType.GEKKO, of(
                        "Gekko", Rarity.RARE,
                        petIcon("gekko_pet", "Gekko"), fromKilled(550, EntityType.SLIME), stand -> {
                            if (r.nextInt(100) < 5) circle(head(stand), Particle.CRIT_MAGIC, 2, 0.25);
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
                .put(PetType.TARANTULA, of(
                        "Tarantula", Rarity.MYTHICAL,
                        petIcon("tarantula_pet", "Tarantula"), fromKilled(1500000, EntityType.SPIDER), stand -> {
                            if (r.nextInt(100) < 10) circle(head(stand), StarMaterial.WHITE_WOOL.find(), 8, 1);
                            if (r.nextInt(100) < 10) polygon(head(stand), new ItemStack(Material.STRING), 6, 0.75);
                        }
                ))

                .put(PetType.UNICORN, of(
                        "Unicorn", Rarity.ULTRA,
                        petIcon("unicorn_pet", "Unicorn"), fromStatistic(Statistic.ANIMALS_BRED, 9000), stand -> {
                            if (r.nextInt(100) < 5) circle(head(stand), Particle.HEART, 3, 0.5);
                            if (r.nextInt(100) < 15) stand.getWorld().spawnParticle(Particle.DRAGON_BREATH, head(stand), 1, 0, 0, 0, 0);
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
            STAR_PLAYER_CACHE.remove(p.getUniqueId());
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
