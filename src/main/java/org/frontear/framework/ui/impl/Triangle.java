package org.frontear.framework.ui.impl;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Color;
import org.frontear.framework.ui.Drawable;

@Deprecated
public final class Triangle extends Drawable {
    /**
     * Creates a triangle {@link Drawable} object, which is rendered via {@link
     * org.lwjgl.opengl.GL11#GL_TRIANGLES}. This implementation does not support complex triangles,
     * such as <i>equilaterals, scalene, or any others</i>
     *
     * @param x      The x-coordinate of the {@link Drawable}
     * @param y      The y-coordinate of the {@link Drawable}
     * @param width  The width of the {@link Drawable}
     * @param height The height of the {@link Drawable}
     * @param color  The color of the {@link Drawable}
     */
    public Triangle(final int x, final int y, final int width, final int height,
        final Color color) {
        super(x, y, width, height, color);
    }

    @Override
    @Deprecated
    protected void render(final int x, final int y, final int width, final int height) {
        glBegin(GL_TRIANGLES);
        {
            glVertex2d(x, y);
            glVertex2d(x - width / 2f, y + height);
            glVertex2d(x + width / 2f, y + height);
        }
        glEnd();
    }

    @Override
    protected void click(final int mouseX, final int mouseY, final boolean hover,
        final int button) {
        throw new UnsupportedOperationException();
    }
}
