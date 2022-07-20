package dev.thalia.mixins;

import dev.thalia.events.PacketReceivedEvent;
import io.netty.channel.ChannelHandlerContext;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin({NetworkManager.class})
public class NetworkManagerMixin {

    @Inject(method = "channelRead0", at = @At("HEAD"), cancellable = true)
    public void onChannelReadHead(ChannelHandlerContext context, Packet<?> packet, CallbackInfo callbackInfo) {
        if(MinecraftForge.EVENT_BUS.post(new PacketReceivedEvent(packet, context))) {
            callbackInfo.cancel();
        }
    }
}
