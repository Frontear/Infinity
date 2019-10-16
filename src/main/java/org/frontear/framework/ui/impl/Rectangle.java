package org.frontear.framework.ui.impl;

import static org.lwjgl.opengl.GL11.GL_QUADS;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Color;
import org.frontear.framework.ui.Drawable;

public final class Rectangle extends Drawable {
    /**
     * Creates a rectangle {@link Drawable} object, which is rendered via {@link
     * org.lwjgl.opengl.GL11#GL_QUADS}
     *
     * @param x      The x-coordinate of the {@link Drawable}
     * @param y      The y-coordinate of the {@link Drawable}
     * @param width  The width of the {@link Drawable}
     * @param height The height of the {@link Drawable}
     * @param color  The color of the {@link Drawable}
     */
    public Rectangle(final int x, final int y, final int width, final int height,
        final Color color) {
        super(x, y, width, height, color);
    }

    @Override
    protected void render(final int x, final int y, final int width, final int height) {
        glBegin(GL_QUADS);
        {
            glVertex2d(x, y);
            glVertex2d(x + width, y);
            glVertex2d(x + width, y + height);
            glVertex2d(x, y + height);
        }
        glEnd();
    }

    @Override
    protected void click(final int mouseX, final int mouseY, final boolean hover,
        final int button) {
        throw new UnsupportedOperationException();
    }
}
