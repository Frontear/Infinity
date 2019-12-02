package org.frontear.infinity.events.client;

import lombok.NonNull;
import net.minecraft.network.Packet;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class PacketEvent extends Event {
    private static Packet packet;

    public PacketEvent(@NonNull final Packet packet) {
        PacketEvent.packet = packet;
    }

    public Packet getPacket() {
        return packet;
    }

    public void setPacket(final Packet packet) {
        PacketEvent.packet = packet;
    }
}
