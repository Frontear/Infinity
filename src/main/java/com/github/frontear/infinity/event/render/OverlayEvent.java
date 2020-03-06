package com.github.frontear.infinity.event.render;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;

public final class OverlayEvent extends Event {
    @Getter private final float tickDelta;
    @Getter private final Window window;

    public OverlayEvent(final float tickDelta) {
        this.tickDelta = tickDelta;
        this.window = MinecraftClient.getInstance().getWindow();
    }
}
