package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.TestNBTWrapper;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public final class TestWrapper implements Wrapper {
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
        return new TestNBTWrapper(item);
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
    public void spawnFakeItem(ItemStack item, Location loc, long deathTicks) {}

    @Override
    public void attachRiptide(Entity en) {}

    @Override
    public void setRotation(Entity en, float yaw, float pitch) {}

    @Override
    public String getKey(Sound s) {
        return s.name().toLowerCase();
    }

    @Override
    public void stopSound(Player p) {}

    @Override
    public void sendBlockChange(Player p, Location loc, Material m, BlockState data) {}

    @Override
    public String getAdvancementDescription(String s) {
        return s;
    }

    @Override
    public ItemStack cleanSkull(ItemStack item) { return null; }


}
