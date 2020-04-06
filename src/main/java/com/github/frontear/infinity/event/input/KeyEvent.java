package com.github.frontear.infinity.event.input;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

public final class KeyEvent extends Event {
    private static final MinecraftClient client = MinecraftClient.getInstance();

    @Getter private final int key;
    @Getter private final boolean pressed;

    public KeyEvent(final int key, final boolean pressed) {
        this.key = key;
        this.pressed = pressed;
    }

    // if the ux is focused onto a different element (textbox, different screen)
    // todo: rename
    public boolean isFocused() {
        return client.currentScreen != null;
    }
}
