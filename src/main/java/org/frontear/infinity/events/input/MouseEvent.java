package org.frontear.infinity.events.input;

import lombok.Getter;
import net.minecraftforge.fml.common.eventhandler.Event;

public final class MouseEvent extends Event {
	private final int button;
	@Getter private final boolean pressed;

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
}
