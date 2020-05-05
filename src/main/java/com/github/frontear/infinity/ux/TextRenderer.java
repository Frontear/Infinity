package com.github.frontear.infinity.ux;

import com.github.frontear.efkolia.api.events.*;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.render.OverlayEvent;
import com.github.frontear.infinity.modules.impl.Ghost;
import java.awt.Color;
import lombok.*;
import net.minecraft.client.MinecraftClient;

public final class TextRenderer {
    private static final int OFFSET = 2;

    private final MinecraftClient client;
    private final InfinityMod infinity;

    private int right = 0, left = 0; // y-coords for left and right

    public TextRenderer(@NonNull final InfinityMod infinity,
        @NonNull final MinecraftClient client) {
        this.infinity = infinity;
        this.client = client;

        infinity.getExecutor().register(this);
    }

    public void renderRight(@NonNull final String text, @NonNull final Color color,
        final boolean shadow) {
        if (!infinity.getModules().get(Ghost.class).isActive() && !client.options.debugEnabled) {
            val renderer = client.textRenderer;
            val x = client.getWindow().getScaledWidth() - renderer.getStringWidth(text) - OFFSET;

            if (shadow) {
                renderer.drawWithShadow(text, x, right + OFFSET, color.getRGB());
            }
            else {
                renderer.draw(text, x, right + OFFSET, color.getRGB());
            }

            right += renderer.fontHeight + OFFSET;
        }
    }

    public void renderLeft(@NonNull final String text, @NonNull final Color color,
        final boolean shadow) {
        if (!infinity.getModules().get(Ghost.class).isActive() && !client.options.debugEnabled) {
            val renderer = client.textRenderer;
            val x = OFFSET;

            if (shadow) {
                renderer.drawWithShadow(text, x, left + OFFSET, color.getRGB());
            }
            else {
                renderer.draw(text, x, left + OFFSET, color.getRGB());
            }

            left += renderer.fontHeight + OFFSET;
        }
    }

    @Listener(Priority.LOW)
    private void onOverlay(@NonNull final OverlayEvent event) {
        right = left = 0;
    }
}
