package org.frontear.infinity.events.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class KeyEvent extends Event {
	@Getter int key;
	@Getter boolean pressed;

	public KeyEvent(int key, boolean pressed) {
		this.key = key;
		this.pressed = pressed;
	}
}
