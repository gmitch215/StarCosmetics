package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper1_18_R1;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.protocol.game.ClientboundAddEntityPacket;
import net.minecraft.network.protocol.game.ClientboundRemoveEntitiesPacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.item.ItemEntity;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_18_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_18_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_18_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Wrapper1_18_R1 implements Wrapper {

    @Override
    public int getCommandVersion() {
        return 2;
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_18_R1(item);
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
        nmsEntity.setNoPickUpDelay();

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

}
