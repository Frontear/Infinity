package org.frontear.infinity.events.input;

import lombok.Getter;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class KeyEvent extends Event {
	@Getter private final int key;
	@Getter private final boolean pressed;

	public KeyEvent(int key, boolean pressed) {
		this.key = key;
		this.pressed = pressed;
	}
}
