package org.frontear.framework.ui.impl;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static manifold.collections.api.range.RangeFun.to;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import java.awt.Color;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;
import org.frontear.framework.ui.Drawable;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Circle extends Drawable {
    int factor;
    @NonFinal int radius;

    /**
     * Creates a circle {@link Drawable} object, which is rendered via {@link
     * org.lwjgl.opengl.GL11#GL_TRIANGLE_FAN}
     *
     * @param x      The x-coordinate of the {@link Drawable}
     * @param y      The y-coordinate of the {@link Drawable}
     * @param radius The radius of the {@link Drawable}
     * @param factor Defines how smooth the edges will be, higher values are smoother
     * @param color  The color of the {@link Drawable}
     */
    public Circle(final int x, final int y, final int radius, final int factor, final Color color) {
        super(x, y, 0, 0, color);

        this.radius = radius;
        this.factor = factor;
    }

    @Deprecated
    @Override
    public void setWidth(final int width) {
        this.setRadius(width);
    }

    public void setRadius(final int radius) {
        this.radius = radius;
    }

    @Deprecated
    @Override
    public void setHeight(final int height) {
        this.setRadius(height);
    }

    // https://stackoverflow.com/a/24843626/9091276
    @Override
    protected void render(final int x, final int y, final int width, final int height) {
        glBegin(GL_TRIANGLE_FAN);
        {
            glVertex2d(x, y);
            for (val i : 0to factor) {
                glVertex2d((x + (radius * cos(i * (PI * 2f) / factor))),
                    (y + (radius * sin(i * (PI * 2f) / factor))));
            }
        }
        glEnd();
    }

    @Override
    protected void click(final int mouseX, final int mouseY, final boolean hover,
        final int button) {
        throw new UnsupportedOperationException();
    }
}
