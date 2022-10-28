package me.gamercoder215.starcosmetics.wrapper;

import me.gamercoder215.starcosmetics.api.StarConfig;
import me.gamercoder215.starcosmetics.events.CompletionEvents1_12_R1;
import me.gamercoder215.starcosmetics.util.inventory.StarInventory;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper;
import me.gamercoder215.starcosmetics.wrapper.nbt.NBTWrapper1_17_R1;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import net.minecraft.network.protocol.game.PacketPlayOutEntityDestroy;
import net.minecraft.network.protocol.game.PacketPlayOutSpawnEntity;
import net.minecraft.server.level.EntityPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.decoration.EntityArmorStand;
import net.minecraft.world.entity.item.EntityItem;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftArmorStand;
import org.bukkit.craftbukkit.v1_17_R1.entity.CraftPlayer;
import org.bukkit.craftbukkit.v1_17_R1.inventory.CraftItemStack;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

public class Wrapper1_17_R1 implements Wrapper {

    public Wrapper1_17_R1() {
        new CompletionEvents1_12_R1();
    }

    @Override
    public int getCommandVersion() {
        return 2;
    }

    @Override
    public boolean isItem(org.bukkit.Material m) {
        return m.isItem();
    }

    @Override
    public NBTWrapper getNBTWrapper(ItemStack item) {
        return new NBTWrapper1_17_R1(item);
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
        sp.b.sendPacket(add);

        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutEntityDestroy remove = new PacketPlayOutEntityDestroy(nmsEntity.getId());
                sp.b.sendPacket(remove);
            }
        }.runTaskLater(StarConfig.getPlugin(), deathTicks);
    }

    @Override
    public void spawnFakeItem(Player p, ItemStack item, Location loc, long deathTicks) {
        CraftWorld cw = (CraftWorld) loc.getWorld();
        EntityPlayer sp = ((CraftPlayer) p).getHandle();
        EntityItem nmsEntity = new EntityItem(cw.getHandle(), loc.getX(), loc.getY(), loc.getZ(), CraftItemStack.asNMSCopy(item));
        nmsEntity.p();

        PacketPlayOutSpawnEntity add = new PacketPlayOutSpawnEntity(nmsEntity);
        sp.b.sendPacket(add);

        new BukkitRunnable() {
            @Override
            public void run() {
                PacketPlayOutEntityDestroy remove = new PacketPlayOutEntityDestroy(nmsEntity.getId());
                sp.b.sendPacket(remove);
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
            @Override
            public void run() {
                if (en.isDead() || !en.isValid() || en.hasMetadata("stopped")) {
                    cancel();
                    return;
                }

                as.enderTeleportTo(en.getLocation().getX(), en.getLocation().getY(), en.getLocation().getZ());
                as.s(3);
            }
        }.runTaskTimer(StarConfig.getPlugin(), 0, 2);
    }

    @Override
    public StarInventory createInventory(String key, int size, String title) {
        return new StarInventory1_17_R1(key, size, title);
    }

}
