package org.frontear.mixins;

import io.netty.channel.ChannelFutureListener;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import lombok.val;
import manifold.ext.api.Jailbreak;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.client.PacketEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(NetworkManager.class) public abstract class MixinNetworkManager {
	/**
	 * @param inPacket        The packet that will be dispatched
	 * @param futureListeners Listens to the result of the channel, {@link ChannelFutureListener}
	 *
	 * @author Frontear
	 * @reason {@link org.frontear.infinity.events.client.PacketEvent}
	 */
	@SuppressWarnings("UnresolvedMixinReference") @Redirect(method = "*",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/network/NetworkManager;dispatchPacket(Lnet/minecraft/network/Packet;[Lio/netty/util/concurrent/GenericFutureListener;)V")) private void dispatchPacket(NetworkManager manager, final Packet inPacket, final GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
		val event = new PacketEvent(inPacket);
		MinecraftForge.EVENT_BUS.post(event);

		if (event.getPacket() != null) {
			manager.dispatchPacket(event.getPacket(), futureListeners);
		}
	}
}
