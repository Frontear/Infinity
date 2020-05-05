package com.github.frontear.infinity.commands;

import com.github.frontear.efkolia.api.events.Listener;
import com.github.frontear.efkolia.impl.container.Container;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.input.KeyEvent;
import com.github.frontear.infinity.modules.impl.Ghost;
import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public final class CommandContainer extends Container<Command> {
    final CommandScreen screen;
    private final InfinityMod mod;
    private final MinecraftClient client;

    public CommandContainer(@NonNull final InfinityMod mod) {
        super(mod, CommandContainer.class.getPackage().getName() + ".impl", mod);

        this.mod = mod;
        this.client = MinecraftClient.getInstance();

        this.screen = new CommandScreen(client, this);

        mod.getExecutor().register(this);
    }

    @Listener
    private void onKey(@NonNull final KeyEvent event) {
        if (!event.isFocused() && event.isPressed() && !mod.getModules().get(Ghost.class).isActive()
            && event.getKey() == GLFW.GLFW_KEY_GRAVE_ACCENT) {
            client.openScreen(screen);
        }
    }
}
