package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Wrapper {

    static boolean isCompatible() {
        return getWrapper() == null;
    }

    static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    static Wrapper getWrapper() {
        try {
            return (Wrapper) Class.forName("me.gamercoder215.starcosmetics.wrapper.Wrapper" + getServerVersion())
                    .getConstructor()
                    .newInstance();
        } catch (ClassNotFoundException e) { // Using unsupported version
            return null;
        } catch (ReflectiveOperationException e) {
            StarConfig.print(e);
        } catch (ArrayIndexOutOfBoundsException ignored) {} // Using test server

        return null;
    }

    int getCommandVersion();

    default boolean isLegacy() {
        return getCommandVersion() == 1;
    }

    NBTWrapper getNBTWrapper(ItemStack item);

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

    void spawnFakeItem(Player p, ItemStack item, Location loc, long deathTicks);

    void attachRiptide(Entity en);

    // Other Utilities

}
