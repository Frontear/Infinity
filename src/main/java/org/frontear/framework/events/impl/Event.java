package org.frontear.framework.events.impl;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.events.IEvent;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Event implements IEvent {
    @Getter boolean cancelled = false;

    @Override
    public void cancel() {
        this.cancelled = true;
    }
}
