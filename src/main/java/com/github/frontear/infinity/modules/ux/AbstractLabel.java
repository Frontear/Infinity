package com.github.frontear.infinity.modules.ux;

import java.awt.Color;
import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.*;

public abstract class AbstractLabel implements Drawable, Element {
    protected final int x, y, width, height;

    protected final String text;
    protected final TextRenderer renderer;
    protected final int text_width, text_height, text_color;

    public AbstractLabel(@NonNull final String text, final int x, final int y, final int width,
        final int height) {
        this.renderer = MinecraftClient.getInstance().textRenderer;
        this.text = text;
        this.text_width = renderer.getStringWidth(text);
        this.text_height = renderer.fontHeight;
        this.text_color = Color.WHITE.getRGB();

        this.height = height;
        this.width = width;
        this.x = x;
        this.y = y;
    }

    @Override
    public void render(final int mouseX, final int mouseY, final float delta) {
        renderer
            .draw(text, (x + (x + width) - text_width) / 2f, (y + (y + height) - text_height) / 2f,
                text_color);
    }
}
