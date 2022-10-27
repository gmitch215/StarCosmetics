package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class TestWrapper implements Wrapper {
    @Override
    public int getCommandVersion() {
        return 1;
    }

    @Override
    public boolean isItem(Material m) {
        return true;
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return null;
    }

    @Override
    public StarInventory createInventory(String key, int size, String title) {
        return null;
    }

    @Override
    public void sendActionbar(Player p, String message) {}

    @Override
    public void spawnFakeEntity(Player p, EntityType type, Location loc, long deathTicks) {}

    @Override
    public void spawnFakeItem(Player p, ItemStack item, Location loc, long deathTicks) {}

    @Override
    public void attachRiptide(Entity en) {}
}
