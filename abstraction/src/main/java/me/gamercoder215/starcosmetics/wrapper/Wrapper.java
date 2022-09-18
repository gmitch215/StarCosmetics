package me.gamercoder215.starcosmetics.wrapper;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Wrapper {

    int getCommandVersion();

    default boolean isLegacy() {
        return getCommandVersion() == 1;
    }

    NBTWrapper getNBTWrapper(ItemStack item);

    void sendActionbar(Player p, String message);

    void spawnFakeEntity(Player p, EntityType type, Location loc, long deathTicks);

    void spawnFakeItem(Player p, ItemStack item, Location loc, long deathTicks);

    static String getServerVersion() {
        return Bukkit.getServer().getClass().getPackage().getName().split("\\.")[3].substring(1);
    }

    static Wrapper getWrapper() {
        try {
            return (Wrapper) Class.forName("me.gamercoder215.starcosmetics.wrapper.Wrapper" + getServerVersion()).getConstructor().newInstance();
        } catch (Exception e) { throw new IllegalStateException("Wrapper not Found: " + getServerVersion()); }
    }
}
