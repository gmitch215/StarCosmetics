package me.gamercoder215.starcosmetics.wrapper.v1_13_R1;

import io.netty.channel.ChannelDuplexHandler;
import io.netty.channel.ChannelHandlerContext;
import me.gamercoder215.starcosmetics.util.StarRunnable;
import net.minecraft.server.v1_13_R1.Packet;
import org.bukkit.entity.Player;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.function.Predicate;

final class PacketHandler1_13_R1 extends ChannelDuplexHandler {

    public static final Map<UUID, Predicate<Packet<?>>> PACKET_HANDLERS = new HashMap<>();

    private final Player p;

    public PacketHandler1_13_R1(Player p) {
        this.p = p;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object packetO) throws Exception {
        if (!(packetO instanceof Packet<?>)) {
            super.channelRead(ctx, packetO);
            return;
        }

        Packet<?> packet = (Packet<?>) packetO;
        Predicate<Packet<?>> handler = PACKET_HANDLERS.get(p.getUniqueId());
        if (handler != null) StarRunnable.sync(() -> {
            boolean success = handler.test(packet);
            if (success) PACKET_HANDLERS.remove(p.getUniqueId());
        });

        super.channelRead(ctx, packetO);
    }

}