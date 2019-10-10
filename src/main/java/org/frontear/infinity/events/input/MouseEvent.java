package org.frontear.infinity.events.input;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import net.minecraftforge.fml.common.eventhandler.Event;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class MouseEvent extends Event {
    int button;
    @Getter boolean pressed;

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
