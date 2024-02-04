package me.gamercoder215.starcosmetics.wrapper.v1_9_R2;

import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_9_R2.*;
import org.bukkit.Location;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_9_R2.CraftSound;
import org.bukkit.craftbukkit.v1_9_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_9_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_9_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.function.Consumer;

@SuppressWarnings("deprecation")
final class Wrapper1_9_R2 implements Wrapper {

    @Override
    public int getCommandVersion() {
        return 1;
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_9_R2(item);
    }

    @Override
    public void sendActionbar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    @Override
    public String getKey(Sound s) {
        SoundEffect se = CraftSound.getSoundEffect(CraftSound.getSound(s));
        MinecraftKey key = SoundEffect.a.b(se);

        return key.b();
    }

    @Override
    public void stopSound(Player p) {
        PacketPlayOutCustomPayload packet = new PacketPlayOutCustomPayload("MC|StopSound", new PacketDataSerializer(Unpooled.buffer()).a(""));
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(packet);
    }

    @Override
    public void spawnFakeEntity(Player p, EntityType type, Location loc, long deathTicks) {
        CraftWorld cw = (CraftWorld) loc.getWorld();
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        Entity nmsEntity = cw.createEntity(loc, type.getEntityClass());

        PacketPlayOutSpawnEntity add = new PacketPlayOutSpawnEntity(nmsEntity, 0);
        sp.playerConnection.sendPacket(add);

        StarRunnable.syncLater(() -> {
            PacketPlayOutEntityDestroy remove = new PacketPlayOutEntityDestroy(nmsEntity.getId());
            sp.playerConnection.sendPacket(remove);
        }, deathTicks);
    }

    @Override
    public void spawnFakeItem(ItemStack item, Location loc, long deathTicks) {
        WorldServer ws = ((CraftWorld) loc.getWorld()).getHandle();
        EntityItem nmsEntity = new EntityItem(ws, loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        nmsEntity.s();
        ws.addEntity(nmsEntity);

        StarRunnable.syncLater(nmsEntity::Q, deathTicks);
    }

    @Override
    public void attachRiptide(org.bukkit.entity.Entity en) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setRotation(org.bukkit.entity.Entity en, float yaw, float pitch) {
        Entity nms = ((CraftEntity) en).getHandle();

        nms.yaw = yaw % 360.0F;
        nms.pitch = pitch % 360.0F;
    }

    @Override
    public boolean isItem(org.bukkit.Material m) {
        if (m == org.bukkit.Material.AIR) return false;
        return Item.getById(m.getId()) != null;
    }

    @Override
    public void sendBlockChange(Player p, Location loc, org.bukkit.Material m, BlockState data) {
        p.sendBlockChange(loc, m, data == null ? (byte) 0 : data.getRawData());
    }

    @Override
    public StarInventory createInventory(String key, int size, String title) {
        return new StarInventory1_9_R2(key, size, title);
    }

    @Override
    public String getAdvancementDescription(String s) {
        throw new UnsupportedOperationException();
    }

    @Override
    public ItemStack cleanSkull(ItemStack item) {
        net.minecraft.server.v1_9_R2.ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.hasTag() ? nmsitem.getTag() : new NBTTagCompound();
        NBTTagCompound skullOwner = tag.getCompound("SkullOwner");

        skullOwner.remove("Id");
        skullOwner.remove("Properties");
        tag.set("SkullOwner", skullOwner);
        nmsitem.setTag(tag);
        return CraftItemStack.asBukkitCopy(nmsitem);
    }

    @Override
    public void addPacketInjector(Player p) {
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        Channel ch = sp.playerConnection.networkManager.channel;

        if (ch.pipeline().get(PACKET_INJECTOR_ID) != null) return;

        ch.pipeline().addAfter("decoder", PACKET_INJECTOR_ID, new PacketHandler1_9_R2(p));
    }

    @Override
    public void removePacketInjector(Player p) {
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        Channel ch = sp.playerConnection.networkManager.channel;

        if (ch.pipeline().get(PACKET_INJECTOR_ID) == null) return;
        ch.pipeline().remove(PACKET_INJECTOR_ID);
    }

    @Override
    public void sendSign(Player p, Consumer<String[]> lines) {
        addPacketInjector(p);

        Location l = p.getLocation();
        WorldServer ws = ((CraftWorld) l.getWorld()).getHandle();
        BlockPosition pos = new BlockPosition(l.getBlockX(), 255, l.getBlockZ());

        PacketPlayOutBlockChange sent1 = new PacketPlayOutBlockChange(ws, pos);
        sent1.block = Blocks.STANDING_SIGN.getBlockData();

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sent1);

        PacketPlayOutOpenSignEditor sent2 = new PacketPlayOutOpenSignEditor(pos);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sent2);

        PacketHandler1_9_R2.PACKET_HANDLERS.put(p.getUniqueId(), packetO -> {
            if (!(packetO instanceof PacketPlayInUpdateSign)) return false;
            PacketPlayInUpdateSign packet = (PacketPlayInUpdateSign) packetO;

            lines.accept(packet.b());
            return true;
        });

        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutBlockChange sent3 = new PacketPlayOutBlockChange(ws, pos);
                sent3.block = Blocks.AIR.getBlockData();

                ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sent3);
            }
        }.runTaskLater(StarConfig.getPlugin(), 2L);
    }

}
