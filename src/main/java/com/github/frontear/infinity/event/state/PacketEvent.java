package com.github.frontear.infinity.event.state;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.*;
import net.minecraft.network.Packet;

public final class PacketEvent extends Event {
    @Getter @Setter private Packet<?> packet;

    public PacketEvent(@NonNull final Packet<?> packet) {
        this.packet = packet;
    }
}
