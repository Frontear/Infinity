package org.frontear.infinity.events.input;

import net.minecraftforge.fml.common.eventhandler.Event;

public class MouseEvent extends Event {
	private final int button;
	private final boolean pressed;

	public MouseEvent(int button, boolean pressed) {
		this.button = button;
		this.pressed = pressed;
	}

	public boolean isLeft() {
		return button == 0;
	}

	public boolean isRight() {
		return button == 1;
	}

	public boolean isPressed() {
		return pressed;
	}
}
