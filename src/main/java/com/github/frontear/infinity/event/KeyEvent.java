package com.github.frontear.infinity.event;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.Getter;

public final class KeyEvent extends Event {
    @Getter private final int key;
    @Getter private final boolean pressed;

    public KeyEvent(final int key, final boolean pressed) {
        this.key = key;
        this.pressed = pressed;
    }
}
