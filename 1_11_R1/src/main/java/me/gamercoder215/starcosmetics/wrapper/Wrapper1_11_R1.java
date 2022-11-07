package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper1_11_R1;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_11_R1.*;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_11_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_11_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_11_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public final class Wrapper1_11_R1 implements Wrapper {

    @Override
    public int getCommandVersion() {
        return 2;
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_11_R1(item);
    }

    @Override
    public void sendActionbar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    @Override
    public void spawnFakeEntity(Player p, EntityType type, Location loc, long deathTicks) {
        CraftWorld cw = (CraftWorld) loc.getWorld();
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        Entity nmsEntity = cw.createEntity(loc, type.getEntityClass());

        PacketPlayOutSpawnEntity add = new PacketPlayOutSpawnEntity(nmsEntity, 0);
        sp.playerConnection.sendPacket(add);

        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutEntityDestroy remove = new PacketPlayOutEntityDestroy(nmsEntity.getId());
                sp.playerConnection.sendPacket(remove);
            }
        }.runTaskLater(StarConfig.getPlugin(), deathTicks);
    }

    @Override
    public void spawnFakeItem(Player p, ItemStack item, Location loc, long deathTicks) {
        WorldServer ws = ((CraftWorld) loc.getWorld()).getHandle();
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        EntityItem nmsEntity = new EntityItem(ws, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        nmsEntity.s();
        ws.addEntity(nmsEntity);

        new BukkitRunnable() {
            @Override
            public void run() {
                nmsEntity.Q();
            }
        }.runTaskLater(StarConfig.getPlugin(), deathTicks);
    }

    @Override
    public void attachRiptide(org.bukkit.entity.Entity en) {
        throw new UnsupportedOperationException();
    }

    @Override
    @SuppressWarnings("deprecation")
    public boolean isItem(org.bukkit.Material m) {
        if (m == org.bukkit.Material.AIR) return false;
        return Item.getById(m.getId()) != null;
    }

    @Override
    public StarInventory createInventory(String key, int size, String title) {
        return new StarInventory1_11_R1(key, size, title);
    }

}
