package me.gamercoder215.starcosmetics.wrapper.v1_13_R2;

import com.mojang.authlib.GameProfile;
import io.netty.channel.Channel;
import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import me.gamercoder215.starcosmetics.util.entity.StarSelector;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.Wrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.server.v1_13_R2.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.NamespacedKey;
import org.bukkit.Sound;
import org.bukkit.block.BlockState;
import org.bukkit.craftbukkit.v1_13_R2.CraftServer;
import org.bukkit.craftbukkit.v1_13_R2.CraftSound;
import org.bukkit.craftbukkit.v1_13_R2.CraftWorld;
import org.bukkit.craftbukkit.v1_13_R2.advancement.CraftAdvancement;
import org.bukkit.craftbukkit.v1_13_R2.block.CraftBlockState;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_13_R2.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_13_R2.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;

import java.util.UUID;
import java.util.function.Consumer;

final class Wrapper1_13_R2 implements Wrapper {

    @Override
    public boolean isItem(org.bukkit.Material m) {
        if (m == org.bukkit.Material.AIR) return false;
        return m.isItem();
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_13_R2(item);
    }

    @Override
    public void sendActionbar(Player p, String message) {
        p.spigot().sendMessage(ChatMessageType.ACTION_BAR, new TextComponent(message));
    }

    @Override
    public String getKey(Sound s) {
        SoundEffect se = CraftSound.getSoundEffect(CraftSound.getSound(s));
        MinecraftKey key = IRegistry.SOUND_EVENT.getKey(se);

        return key.b();
    }

    @Override
    public void stopSound(Player p) {
        PacketPlayOutStopSound packet = new PacketPlayOutStopSound(null, SoundCategory.MASTER);
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
        nmsEntity.p();
        ws.addEntity(nmsEntity);

        StarRunnable.syncLater(nmsEntity::killEntity, deathTicks);
    }

    private static EntityPlayer createPlayer(Location loc) {
        try {
            MinecraftServer srv = ((CraftServer) Bukkit.getServer()).getServer();
            WorldServer sw = ((CraftWorld) loc.getWorld()).getHandle();

            UUID uid = UUID.randomUUID();
            EntityPlayer sp = new EntityPlayer(srv, sw, new GameProfile(uid, uid.toString().substring(0, 16)), new PlayerInteractManager(sw));
            sp.playerConnection = new PlayerConnection(srv, new NetworkManager(EnumProtocolDirection.SERVERBOUND), sp);
            sp.setPosition(loc.getX(), loc.getY(), loc.getZ());

            for (Player p : loc.getWorld().getPlayers()) {
                EntityPlayer sph = ((CraftPlayer) p).getHandle();
                sph.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, sp));
                sph.playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(sp));

                StarRunnable.asyncLater(() -> sph.playerConnection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, sp)),
                        1);
            }

            return sp;
        } catch (Exception e) {
            StarConfig.print(e);
            return null;
        }
    }

    @Override
    public void attachRiptide(org.bukkit.entity.Entity en) {
        EntityPlayer sp = createPlayer(en.getLocation());
        sp.setSilent(true);
        sp.setInvulnerable(true);
        sp.setNoGravity(true);
        sp.setInvisible(true);

        DataWatcher dw = sp.getDataWatcher();
        dw.set(DataWatcherRegistry.a.a(8), (byte) 0x04);
        en.addPassenger(sp.getBukkitEntity());

        for (Player p : en.getWorld().getPlayers()) {
            EntityPlayer sph = ((CraftPlayer) p).getHandle();
            sph.playerConnection.sendPacket(new PacketPlayOutEntityMetadata(sp.getId(), dw, true));
        }

        new BukkitRunnable() {
            @Override
            public void run() {
                if (StarSelector.isStopped(en)) {
                    cancel();
                    sp.die();

                    for (Player p : en.getWorld().getPlayers()) {
                        EntityPlayer sph = ((CraftPlayer) p).getHandle();
                        sph.playerConnection.sendPacket(new PacketPlayOutEntityDestroy(sp.getId()));
                    }

                    return;
                }

                Location l = en.getLocation().clone();

                Vector dir = l.toVector().subtract(new Location(en.getWorld(), sp.locX, sp.locY, sp.locZ).toVector()).normalize().multiply(-1);

                if (Double.isNaN(dir.getX())) dir.setX(0);
                if (Double.isNaN(dir.getY())) dir.setY(0);
                if (Double.isNaN(dir.getZ())) dir.setZ(0);

                l.setDirection(dir);

                sp.setLocation(l.getX(), l.getY(), l.getZ(), l.getYaw(), l.getPitch());
                sp.setHeadRotation(l.getYaw());
                for (Player p : en.getWorld().getPlayers()) {
                    EntityPlayer sph = ((CraftPlayer) p).getHandle();
                    sph.playerConnection.sendPacket(new PacketPlayOutEntityTeleport(sp));
                }
            }
        }.runTaskTimer(StarConfig.getPlugin(), 0, 2);
    }

    @Override
    public void setRotation(org.bukkit.entity.Entity en, float yaw, float pitch) {
        Entity nmsEntity = ((CraftEntity) en).getHandle();
        nmsEntity.yaw = yaw;
        nmsEntity.pitch = pitch;
        nmsEntity.lastYaw = yaw;
        nmsEntity.lastPitch = pitch;
        nmsEntity.setHeadRotation(yaw);
    }

    @Override
    public void sendBlockChange(Player p, Location loc, org.bukkit.Material m, BlockState data) {
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        BlockPosition pos = new BlockPosition(loc.getX(), loc.getY(), loc.getZ());

        if (data == null) {
            p.sendBlockChange(loc, m.createBlockData());
            return;
        }

        IBlockData nmsState = ((CraftBlockState) data).getHandle();
        PacketPlayOutBlockChange packet = new PacketPlayOutBlockChange(((CraftWorld) loc.getWorld()).getHandle(), pos);
        packet.block = nmsState;

        sp.playerConnection.sendPacket(packet);
    }

    @Override
    public StarInventory createInventory(String key, int size, String title) {
        return new StarInventory1_13_R2(key, size, title);
    }

    @Override
    public String getAdvancementDescription(String s) {
        CraftAdvancement ca = (CraftAdvancement) Bukkit.getAdvancement(NamespacedKey.minecraft(s));
        AdvancementDisplay display = ca.getHandle().c();

        return display.b().getString();
    }

    @Override
    public ItemStack cleanSkull(ItemStack item) {
        net.minecraft.server.v1_13_R2.ItemStack nmsitem = CraftItemStack.asNMSCopy(item);
        NBTTagCompound tag = nmsitem.getOrCreateTag();
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

        ch.pipeline().addAfter("decoder", PACKET_INJECTOR_ID, new PacketHandler1_13_R2(p));
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
        sent1.block = Blocks.SIGN.getBlockData();

        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sent1);

        PacketPlayOutOpenSignEditor sent2 = new PacketPlayOutOpenSignEditor(pos);
        ((CraftPlayer) p).getHandle().playerConnection.sendPacket(sent2);

        PacketHandler1_13_R2.PACKET_HANDLERS.put(p.getUniqueId(), packetO -> {
            if (!(packetO instanceof PacketPlayInUpdateSign)) return false;
            PacketPlayInUpdateSign packet = (PacketPlayInUpdateSign) packetO;

            lines.accept(packet.c());
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
