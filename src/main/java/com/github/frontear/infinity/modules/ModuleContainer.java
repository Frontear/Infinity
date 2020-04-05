package com.github.frontear.infinity.modules;

import com.github.frontear.efkolia.impl.container.Container;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.input.KeyEvent;
import com.github.frontear.infinity.event.render.OverlayEvent;
import com.github.frontear.infinity.modules.impl.Ghost;
import java.awt.Color;
import lombok.*;
import net.minecraft.client.MinecraftClient;

public final class ModuleContainer extends Container<Module> {
    public ModuleContainer(@NonNull final InfinityMod mod) {
        super(mod, ModuleContainer.class.getPackage().getName() + ".impl", mod);

        stream().forEach(mod.getConfig()::register);

        mod.getExecutor().register(KeyEvent.class, e -> {
            val client = MinecraftClient.getInstance();

            if (!client.inGameHud.getChatHud().isChatFocused() && e.isPressed()) {
                val ghost = get(Ghost.class).isActive();

                stream().filter(x -> x.getBind().getGLFWCode() == e.getKey())
                    .filter(x -> !ghost || x.isFriendly()).forEach(Module::toggle);
            }
        });

        mod.getExecutor().register(OverlayEvent.class, e -> {
            if (get(Ghost.class).isActive()) {
                return;
            }

            val iter = new int[] { 0 }; // lambda
            val renderer = MinecraftClient.getInstance().textRenderer;

            stream().filter(Module::isActive).forEach(x -> {
                val key = x.getBind().toString();
                val text = x.getPropertyName() + " " + "[" + key.toUpperCase() + "]";

                renderer
                    .draw(text, e.getWindow().getScaledWidth() - renderer.getStringWidth(text) - 1,
                        1 + renderer.fontHeight * iter[0]++,
                        Color.WHITE.getRGB());
            });
        });
    }
}
