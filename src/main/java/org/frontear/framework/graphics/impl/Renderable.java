package org.frontear.framework.graphics.impl;

import static org.lwjgl.opengl.GL11.glColor4f;

import com.google.common.base.Preconditions;
import java.awt.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.graphics.IRenderable;
import org.frontear.framework.graphics.IRenderer;

@FieldDefaults(level = AccessLevel.PROTECTED)
public abstract class Renderable implements IRenderable {
    @Getter @Setter int x, y, width, height;
    @Setter @NonNull IRenderer renderer;
    @Setter @NonNull private Color color;

    public Renderable() {
        this(0, 0, 0, 0, null);
    }

    public Renderable(final int x, final int y, final int width, final int height,
        final Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    @Override
    public void render() throws IllegalArgumentException {
        Preconditions.checkArgument(renderer != null && renderer.isActive(),
            renderer == null ? "Renderer is null" : "Renderer is not active");
        if (color == null) {
            throw new NullPointerException("Renderable#color is null!");
        }

        glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
            color.getAlpha() / 255.0f);
    }
}
