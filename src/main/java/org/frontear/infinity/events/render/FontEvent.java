package org.frontear.infinity.events.render;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE) public final class FontEvent extends Event {
	@Getter @Setter String text;
	@Getter @Setter float x, y;
	@Getter @Setter int color;
	@Getter @Setter boolean dropShadow;

	public FontEvent(@NonNull String text, float x, float y, int color, boolean dropShadow) {
		this.text = text;
		this.x = x;
		this.y = y;
		this.color = color;
		this.dropShadow = dropShadow;
	}
}
