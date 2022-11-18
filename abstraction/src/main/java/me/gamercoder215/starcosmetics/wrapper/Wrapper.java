package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.api.cosmetics.Cosmetic;
import me.gamercoder215.starcosmetics.util.Constants;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.util.selection.CosmeticSelection;
import me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.List;

public interface Wrapper {

    SecureRandom r = Constants.r;

    static boolean isCompatible() {
        return getWrapper() != null;
    }

    default void registerEvents() {}

    static String get(String key) {
        return StarConfig.getConfig().get(key);
    }

    static String get(String key, String def) {
        return StarConfig.getConfig().get(key, def);
    }

    static String getWithArgs(String key, Object... args) {
        return StarConfig.getConfig().getWithArgs(key, args);
    }

    static String getMessage(String key) {
        return StarConfig.getConfig().getMessage(key);
    }

    static void send(CommandSender sender, String key) {
        sender.sendMessage(get(key));
    }

    static void sendMessage(CommandSender sender, String key) {
        sendMessage(sender, key, "");
    }

    static void sendMessage(CommandSender sender, String key, Object prefix) {
        sender.sendMessage(prefix.toString() + getMessage(key));
    }

    static void sendError(CommandSender sender, String key) {
        sendMessage(sender, key, ChatColor.RED);
    }

    static void sendWithArgs(CommandSender sender, String key, Object... args) {
        sender.sendMessage(String.format(get(key), args));
    }

    static String getServerVersion() {
        if (Bukkit.getServer() == null) return ""; // Using Test Server
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
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
            return Class.forName("me.gamercoder215.starcosmetics.wrapper.cosmetics.CosmeticSelections" + cosmeticV)
                    .asSubclass(CosmeticSelections.class)
                    .getConstructor()
                    .newInstance();
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
            return null;
        }
    }

    static <T extends Enum<T> & Cosmetic> List<CosmeticSelection<?>> allFor(Class<T> clazz) {
        List<CosmeticSelection<?>> selections = new ArrayList<>();
        for (T cosmetic : clazz.getEnumConstants()) selections.addAll(getCosmeticSelections().getSelections(cosmetic));

        return selections;
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

    // Other Utilities

}
