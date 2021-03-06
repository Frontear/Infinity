package com.github.frontear.mixins.impl;

import com.github.frontear.infinity.events.client.PacketEvent;
import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.*;
import lombok.val;
import net.minecraft.network.*;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(NetworkManager.class)
public abstract class MixinNetworkManager {
    /**
     * @param inPacket        The packet that will be dispatched
     * @param futureListeners Listens to the result of the channel, {@link ChannelFutureListener}
     *
     * @author Frontear
     * @reason {@link com.github.frontear.infinity.events.client.PacketEvent}
     */
    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "*",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/network/NetworkManager;dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void dispatchPacket(final NetworkManager manager, final Packet inPacket,
        final GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
        val event = new PacketEvent(inPacket);
        MinecraftForge.EVENT_BUS.post(event);

        if (event.getPacket() != null) {
            manager.dispatchPacket(event.getPacket(), futureListeners);
        }
    }
}
