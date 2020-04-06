package com.github.frontear.infinity.event.state;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.Getter;

public final class TickEvent extends Event {
    @Getter private final boolean pre;

    public TickEvent(final boolean pre) {
        this.pre = pre;
    }

    public boolean isPost() {
        return !pre;
    }
}
