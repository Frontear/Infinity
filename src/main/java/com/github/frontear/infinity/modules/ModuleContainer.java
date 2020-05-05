package com.github.frontear.infinity.modules;

import com.github.frontear.efkolia.api.events.Listener;
import com.github.frontear.efkolia.impl.container.Container;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.input.KeyEvent;
import com.github.frontear.infinity.event.render.OverlayEvent;
import com.github.frontear.infinity.modules.impl.Ghost;
import com.github.frontear.infinity.modules.ux.ModuleScreen;
import java.awt.Color;
import lombok.*;
import net.minecraft.client.MinecraftClient;
import org.lwjgl.glfw.GLFW;

public final class ModuleContainer extends Container<Module> {
    private final InfinityMod mod;
    private final ModuleScreen screen;
    private final MinecraftClient client;

    public ModuleContainer(@NonNull final InfinityMod mod) {
        super(mod, ModuleContainer.class.getPackage().getName() + ".impl", mod);

        this.mod = mod;
        this.screen = new ModuleScreen(mod);
        this.client = MinecraftClient.getInstance();

        stream().forEach(mod.getConfig()::register);
        mod.getExecutor().register(this);
    }

    @Listener
    private void onKey(@NonNull final KeyEvent event) {
        val ghost = get(Ghost.class).isActive();

        if (!event.isFocused() && event.isPressed()) {
            if (!ghost && event.getKey() == GLFW.GLFW_KEY_RIGHT_SHIFT) {
                client.openScreen(screen);
            }
            else {
                stream().filter(x -> event.getKey() == x.getBind().getGLFWCode())
                    .filter(x -> !ghost || x.isFriendly()).forEach(Module::toggle);
            }
        }
    }

    @Listener
    private void onOverlay(@NonNull final OverlayEvent event) {
        if (get(Ghost.class).isActive()) {
            return;
        }

        val renderer = mod.getRenderer();

        stream().filter(Module::isActive).forEach(x -> {
            val key = x.getBind().toString();
            val text = x.getPropertyName() + " " + "[" + key.toUpperCase() + "]";

            renderer.renderRight(text, Color.WHITE, false);
        });
    }
}
