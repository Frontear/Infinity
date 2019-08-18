package org.frontear.mixins;

import io.netty.channel.*;
import io.netty.util.AttributeKey;
import io.netty.util.concurrent.Future;
import io.netty.util.concurrent.GenericFutureListener;
import net.minecraft.network.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.network.internal.FMLProxyPacket;
import org.apache.logging.log4j.Logger;
import org.frontear.infinity.events.client.PacketEvent;
import org.spongepowered.asm.mixin.*;

@Mixin(NetworkManager.class) public abstract class MixinNetworkManager {
	@Shadow @Final public static AttributeKey<EnumConnectionState> attrKeyConnectionState;
	@Shadow @Final private static Logger logger;
	@Shadow private Channel channel;

	/**
	 * @param inPacket        The packet that will be dispatched
	 * @param futureListeners Listens to the result of the channel, {@link ChannelFutureListener}
	 *
	 * @author Frontear
	 * @see org.frontear.infinity.events.client.PacketEvent
	 */
	@Overwrite private void dispatchPacket(Packet inPacket, GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
		PacketEvent event = new PacketEvent(inPacket);
		MinecraftForge.EVENT_BUS.post(event);
		inPacket = event.getPacket();

		if (inPacket == null) {
			return;
		}

		final EnumConnectionState enumconnectionstate = EnumConnectionState.getFromPacket(inPacket);
		final EnumConnectionState enumconnectionstate1 = this.channel.attr(attrKeyConnectionState).get();

		final boolean condition = enumconnectionstate != enumconnectionstate1 && !(inPacket instanceof FMLProxyPacket);

		if (condition) {
			logger.debug("Disabled auto read");
			this.channel.config().setAutoRead(false);
		}

		if (this.channel.eventLoop().inEventLoop()) {
			write(condition, enumconnectionstate, inPacket, futureListeners);
		}
		else {
			final Packet packet = inPacket; // damn lambdas
			this.channel.eventLoop().execute(() -> {
				write(condition, enumconnectionstate, packet, futureListeners);
			});
		}
	}

	private void write(boolean condition, EnumConnectionState state, Packet packet, GenericFutureListener<? extends Future<? super Void>>[] futureListeners) {
		if (condition) {
			this.setConnectionState(state);
		}

		ChannelFuture channelfuture = this.channel.writeAndFlush(packet);

		if (futureListeners != null) {
			channelfuture.addListeners(futureListeners);
		}

		channelfuture.addListener(ChannelFutureListener.FIRE_EXCEPTION_ON_FAILURE);
	}

	@Shadow public abstract void setConnectionState(EnumConnectionState newState);
}
