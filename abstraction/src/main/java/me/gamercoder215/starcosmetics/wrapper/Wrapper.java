package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public interface Wrapper {

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

    // Other Utilities

}
