package org.frontear.infinity.events.client;

import lombok.*;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class PacketEvent extends Event {
	@Getter @Setter private Packet packet;

	public PacketEvent(@NonNull Packet packet) {
		this.packet = packet;
	}
}
