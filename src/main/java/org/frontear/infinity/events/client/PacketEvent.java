package org.frontear.infinity.events.client;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class PacketEvent extends Event {
	private static Packet packet;

	public PacketEvent(@NonNull Packet packet) {
		PacketEvent.packet = packet;
	}

	public Packet getPacket() {
		return packet;
	}

	public void setPacket(Packet packet) {
		PacketEvent.packet = packet;
	}
}
