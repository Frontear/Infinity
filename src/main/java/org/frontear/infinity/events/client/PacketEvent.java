package org.frontear.infinity.events.client;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE) public final class PacketEvent extends Event {
	@Getter @Setter Packet packet;

	public PacketEvent(@NonNull Packet packet) {
		this.packet = packet;
	}
}
