package org.frontear.infinity.events.render;

import lombok.*;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class FontEvent extends Event {
	@Getter @Setter private String text;
	@Getter @Setter private float x, y;
	@Getter @Setter private int color;
	@Getter @Setter private boolean dropShadow;

	public FontEvent(@NonNull String text, float x, float y, int color, boolean dropShadow) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
		this.dropShadow = dropShadow;
	}
}
