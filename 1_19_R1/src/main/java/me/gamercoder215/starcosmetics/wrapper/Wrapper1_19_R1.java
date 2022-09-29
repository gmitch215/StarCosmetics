package me.gamercoder215.starcosmetics.wrapper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_19_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_19_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_19_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper1_19_R1;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.decoration.ArmorStand;
import net.minecraft.world.entity.item.ItemEntity;

public class Wrapper1_19_R1 implements Wrapper {
    @Override
    public int getCommandVersion() {
        return 2;
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_19_R1(item);
    }

    @Override
    public void sendActionbar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    @Override
    public void spawnFakeEntity(Player p, EntityType type, Location loc, long deathTicks) {
        CraftWorld cw = (CraftWorld) loc.getWorld();
        ServerPlayer sp = ((CraftPlayer) p).getHandle();
        Entity nmsEntity = cw.createEntity(loc, type.getEntityClass());

        ClientboundAddEntityPacket add = new ClientboundAddEntityPacket(nmsEntity);
        sp.connection.send(add);

        new BukkitRunnable() {
            @Override
            public void run() {
                ClientboundRemoveEntitiesPacket remove = new ClientboundRemoveEntitiesPacket(nmsEntity.getId());
                sp.connection.send(remove);
            }
        }.runTaskLater(StarConfig.getPlugin(), deathTicks);
    }

    @Override
    public void spawnFakeItem(Player p, ItemStack item, Location loc, long deathTicks) {
        CraftWorld cw = (CraftWorld) loc.getWorld();
        ServerPlayer sp = ((CraftPlayer) p).getHandle();
        ItemEntity nmsEntity = new ItemEntity(cw.getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        nmsEntity.setNeverPickUp();

        ClientboundAddEntityPacket add = new ClientboundAddEntityPacket(nmsEntity);
        sp.connection.send(add);

        new BukkitRunnable() {
            @Override
            public void run() {
                ClientboundRemoveEntitiesPacket remove = new ClientboundRemoveEntitiesPacket(nmsEntity.getId());
                sp.connection.send(remove);
            }
        }.runTaskLater(StarConfig.getPlugin(), deathTicks);
    }

    @Override
    public void attachRiptide(org.bukkit.entity.Entity en) {
        ArmorStand as = ((CraftArmorStand) en.getWorld().spawnEntity(en.getLocation(), EntityType.ARMOR_STAND)).getHandle();
        as.setInvulnerable(true);
        as.setInvisible(true);
        as.setMarker(true);

        new BukkitRunnable() {
            public void run() {
                if (en.isDead() || !en.isValid() || en.hasMetadata("stopped")) {
                    cancel();
                    return;
                }

                as.teleportTo(en.getLocation().getX(), en.getLocation().getY(), en.getLocation().getZ());
                
                try {
                    Field spinTicks = LivingEntity.class.getDeclaredField("bC");
                    spinTicks.setAccessible(true);
                    spinTicks.setInt(as, 3);

                    if (!as.level.isClientSide) {
                        Method setFlag = LivingEntity.class.getDeclaredMethod("c");
                        setFlag.setAccessible(true);
                        setFlag.invoke(as, 4);
                    }
                } catch (ReflectiveOperationException e) {
                    StarConfig.print(e);
                }

                
            }
        }.runTaskTimer(StarConfig.getPlugin(), 0, 2);
    }
}
