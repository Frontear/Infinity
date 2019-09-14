package org.frontear.infinity.events.render;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class OverlayEvent extends Event {
	@Getter float partialTicks;
	@Getter ScaledResolution resolution;

	public OverlayEvent(float partialTicks) {
		this.partialTicks = partialTicks;
		this.resolution = new ScaledResolution(Minecraft.getMinecraft());
	}
}
