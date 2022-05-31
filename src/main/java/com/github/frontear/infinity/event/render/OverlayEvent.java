package com.github.frontear.infinity.event.render;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.util.Window;
import net.minecraft.client.util.math.MatrixStack;

public final class OverlayEvent extends Event {
    @Getter private final MatrixStack matrices;
    @Getter private final float tickDelta;
    @Getter private final Window window;

    public OverlayEvent(final MatrixStack matrices, final float tickDelta) {
        this.matrices = matrices;
        this.tickDelta = tickDelta;
        this.window = MinecraftClient.getInstance().getWindow();
    }
}
