package dev.thalia.events;

import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public class PacketReceivedEvent extends Event {
    public Packet<?> packet;

    public ChannelHandlerContext context;

    public PacketReceivedEvent(Packet<?> packet, ChannelHandlerContext context) {
        this.packet = packet;
        this.context = context;
    }
}
