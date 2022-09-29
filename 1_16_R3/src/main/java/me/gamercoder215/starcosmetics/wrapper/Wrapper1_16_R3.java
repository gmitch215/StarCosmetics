package me.gamercoder215.starcosmetics.wrapper;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_16_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_16_R3.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_16_R3.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper1_16_R3;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_16_R3.Entity;
import net.minecraft.server.v1_16_R3.EntityArmorStand;
import net.minecraft.server.v1_16_R3.EntityItem;
import net.minecraft.server.v1_16_R3.EntityPlayer;
import net.minecraft.server.v1_16_R3.PacketPlayOutEntityDestroy;
import net.minecraft.server.v1_16_R3.PacketPlayOutSpawnEntity;

public class Wrapper1_16_R3 implements Wrapper {

    @Override
    public int getCommandVersion() {
        return 2;
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_16_R3(item);
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

        PacketPlayOutSpawnEntity add = new PacketPlayOutSpawnEntity(nmsEntity);
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
        CraftWorld cw = (CraftWorld) loc.getWorld();
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        EntityItem nmsEntity = new EntityItem(cw.getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        nmsEntity.o();

        PacketPlayOutSpawnEntity add = new PacketPlayOutSpawnEntity(nmsEntity);
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
    public void attachRiptide(org.bukkit.entity.Entity en) {
        EntityArmorStand as = ((CraftArmorStand) en.getWorld().spawnEntity(en.getLocation(), EntityType.ARMOR_STAND)).getHandle();
        as.setInvulnerable(true);
        as.setInvisible(true);
        as.setMarker(true);

        new BukkitRunnable() {
            public void run() {
                if (en.isDead() || !en.isValid() || en.hasMetadata("stopped")) {
                    cancel();
                    return;
                }

                as.enderTeleportTo(en.getLocation().getX(), en.getLocation().getY(), en.getLocation().getZ());
                as.r(3);
            }
        }.runTaskTimer(StarConfig.getPlugin(), 0, 2);
    }

}
