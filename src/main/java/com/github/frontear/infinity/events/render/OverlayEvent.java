package com.github.frontear.infinity.events.render;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class OverlayEvent extends Event {
    @Getter float partialTicks;
    @Getter ScaledResolution resolution;
    @Getter boolean debugging;

    public OverlayEvent(final float partialTicks, final boolean debugging) {
        this.partialTicks = partialTicks;
        this.resolution = new ScaledResolution(Minecraft.getMinecraft());
        this.debugging = debugging;
    }
}
