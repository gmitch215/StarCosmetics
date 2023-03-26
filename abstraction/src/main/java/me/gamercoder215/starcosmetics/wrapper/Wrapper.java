package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.api.cosmetics.CosmeticLocation;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.HeadInfo;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.Pet;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.PetType;
import me.gamercoder215.starcosmetics.api.cosmetics.pet.StarPet;
import me.gamercoder215.starcosmetics.api.cosmetics.structure.StructureReader;
import me.gamercoder215.starcosmetics.api.player.StarPlayer;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.*;
import org.bukkit.block.BlockState;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.jetbrains.annotations.NotNull;

import java.io.*;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.security.SecureRandom;
import java.util.*;

import static me.gamercoder215.starcosmetics.util.Constants.w;

public interface Wrapper {

    SecureRandom r = Constants.r;

    Map<UUID, StarPlayer> STAR_PLAYER_CACHE = new HashMap<>();

    static boolean isCompatible() {
        return w != null && !isOutdatedSubversion();
    }

    // Only includes Sub-Versions that won't compile
    List<String> OUTDATED_SUBVERSIONS = Arrays.asList(
            "1.17",
            "1.19",
            "1.19.1"
    );

    static boolean isOutdatedSubversion() {
        String ver = Bukkit.getBukkitVersion().split("-")[0];
        return OUTDATED_SUBVERSIONS.contains(ver);
    }

    default void registerEvents() {}

    static String getServerVersion() {
        if (Bukkit.getServer() == null) return ""; // Using Test Server
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    static DataWrapper getDataWrapper() {
        try {
            if (w.isLegacy())
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.LegacyDataWrapper")
                    .asSubclass(DataWrapper.class)
                    .getConstructor()
                    .newInstance();
            else
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.ModernDataWrapper")
                    .asSubclass(DataWrapper.class)
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

    static Wrapper getWrapper() {
        try {
            return Class.forName("me.gamercoder215.starcosmetics.wrapper.Wrapper" + getServerVersion())
                    .asSubclass(Wrapper.class)
                    .getConstructor()
                    .newInstance();
        } catch (ArrayIndexOutOfBoundsException | NoSuchMethodException ignored) { // Using test server
            return new TestWrapper();
        } catch (ClassNotFoundException e) { // Using unsupported version
            return null;
        } catch (Exception e) {
            StarConfig.print(e);
        }
        return null;
    }

    static CosmeticSelections getCosmeticSelections() {
        String cosmeticV = getServerVersion().split("_")[0] + "_" + getServerVersion().split("_")[1];

        try {
            // Attempt to load Special Cosmetic Selections
            return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + getServerVersion())
                    .asSubclass(CosmeticSelections.class)
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException ignored) {
            // Load Normal Cosmetic Selections
            try {
                return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + cosmeticV)
                        .asSubclass(CosmeticSelections.class)
                        .getConstructor()
                        .newInstance();
            } catch (ReflectiveOperationException e) {
                StarConfig.print(e);
                return null;
            }
        }
    }

    static StructureReader getStructureReader(File file) {
        try {
            return getStructureReader(new FileReader(file));
        } catch (FileNotFoundException e) {
            StarConfig.print(e);
            return null;
        }
    }

    static StructureReader getStructureReader(InputStream stream) {
        return getStructureReader(new InputStreamReader(stream));
    }

    static StructureReader getStructureReader(Reader r) {
        try {
            if (w.isLegacy())
                return Class.forName("me.gamercoder215.starcosmetics.api.cosmetics.structure.LegacyStructureReader")
                        .asSubclass(StructureReader.class)
                        .getConstructor(Reader.class)
                        .newInstance(r);
            else
                return Class.forName("me.gamercoder215.starcosmetics.api.cosmetics.structure.ModernStructureReader")
                        .asSubclass(StructureReader.class)
                        .getConstructor(Reader.class)
                        .newInstance(r);
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

    int getCommandVersion();

    boolean isItem(Material m);

    default boolean isLegacy() {
        return getCommandVersion() == 1;
    }

    NBTWrapper getNBTWrapper(ItemStack item);

    StarInventory createInventory(String key, int size, String title);

    default ItemStack withID(Material m, int amount, String id) {
        NBTWrapper wrapper = getNBTWrapper(new ItemStack(m, amount));
        wrapper.setID(id);
        return wrapper.getItem();
    }

    default ItemStack withID(Material m, String id) {
        return withID(m, 1, id);
    }

    void sendActionbar(Player p, String message);

    void spawnFakeEntity(Player p, EntityType type, Location loc, long deathTicks);

    void spawnFakeItem(ItemStack item, Location loc, long deathTicks);

    void attachRiptide(Entity en);

    void setRotation(Entity en, float yaw, float pitch);

    String getKey(Sound s);

    void stopSound(Player p);

    void sendBlockChange(Player p, Location loc, Material m, BlockState data);

    default void sendBlockChange(Player p, Location loc, Material m) {
        sendBlockChange(p, loc, m, null);
    }

    String getAdvancementDescription(String s);

    default boolean hasFeatureFlag(String flag) { return false; }

    // Other Utilities

    @NotNull
    static Pet createPet(@NotNull PetType type, Player owner, Location loc) {
        return new StarPet(owner, loc, type, (HeadInfo) StarConfig.getRegistry().getPetInfo(type));
    }

    static <T extends Cosmetic> List<CosmeticLocation<?>> allFor(Class<T> clazz) {
        List<CosmeticLocation<?>> selections = new ArrayList<>();
        try {
            if (clazz.isEnum()) {
                for (T c : clazz.getEnumConstants()) selections.addAll(StarConfig.getRegistry().getAllFor(c));
                return selections;
            }

            for (Field f : clazz.getDeclaredFields()) {
                if (!Modifier.isStatic(f.getModifiers())) continue;
                if (!Modifier.isFinal(f.getModifiers())) continue;
                if (!Modifier.isPublic(f.getModifiers())) continue;

                if (!clazz.isAssignableFrom(f.getType())) continue;

                Cosmetic c = (Cosmetic) f.get(null);
                selections.addAll(StarConfig.getRegistry().getAllFor(c));
            }
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        }
        return selections;
    }

    static String get(String key) {
        return StarConfig.getConfig().get(key);
    }

    static String get(String key, String def) {
        return StarConfig.getConfig().get(key, def);
    }

    static String getWithArgs(String key, Object... args) {
        return StarConfig.getConfig().getWithArgs(key, args);
    }

    static String prefix() {
        return get("plugin.prefix");
    }

    static String getMessage(String key, Object prefix) {
        return prefix() + prefix + get(key);
    }

    static void send(CommandSender sender, String key) {
        sender.sendMessage(get(key));
    }

    static void sendError(CommandSender sender, String key) {
        sender.sendMessage(getMessage(key, ChatColor.RED));
    }

    static void sendWithArgs(CommandSender sender, String key, Object... args) {
        sender.sendMessage(String.format(get(key), args));
    }

}
