package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.event.state.PacketEvent;
import com.github.frontear.internal.Nullable;
import io.netty.util.concurrent.*;
import lombok.*;
import net.minecraft.network.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientConnection.class)
@SuppressWarnings("UnresolvedMixinReference")
abstract class ClientConnectionMixin {
    @Shadow
    protected abstract void sendImmediately(final Packet<?> packet,
        final GenericFutureListener<? extends Future<? super Void>> callback);

    @Redirect(method = "*", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/network/ClientConnection;sendImmediately(Lnet/minecraft/network/Packet;Lio/netty/util/concurrent/GenericFutureListener;)V"))
    private void sendImmediately(@NonNull final ClientConnection connection,
        @NonNull Packet<?> packet,
        @Nullable final GenericFutureListener<? extends Future<? super Void>> callback) {
        val infinity = InfinityLoader.getMod();

        packet = infinity.getExecutor().fire(new PacketEvent(packet)).getPacket();

        if (packet != null) {
            this.sendImmediately(packet, callback);
        }
    }
}