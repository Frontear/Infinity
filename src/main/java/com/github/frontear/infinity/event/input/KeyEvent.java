package com.github.frontear.infinity.event.input;

import com.github.frontear.efkolia.impl.events.Event;
import lombok.Getter;
import net.minecraft.client.MinecraftClient;

public final class KeyEvent extends Event {
    @Getter private final boolean pressed;
    private final MinecraftClient client;
    @Getter private final int key;

    public KeyEvent(final int key, final boolean pressed) {
        this.client = MinecraftClient.getInstance();
        this.pressed = pressed;
        this.key = key;
    }

    // if the ux is focused onto a different element (textbox, different screen)
    public boolean isFocused() {
        return client.currentScreen != null;
    }
}
