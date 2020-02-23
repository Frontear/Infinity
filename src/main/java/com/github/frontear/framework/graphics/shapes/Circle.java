package com.github.frontear.framework.graphics.shapes;

import static java.lang.Math.*;
import static org.lwjgl.opengl.GL11.*;

import com.github.frontear.framework.graphics.impl.Renderable;
import com.google.common.base.Preconditions;
import java.awt.Color;
import lombok.*;

public final class Circle extends Renderable {
    private static final byte FACTOR = 8; // consider it a smoothing factor

    public Circle(final int x, final int y, final int width, final int height,
        final @NonNull Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void render() throws IllegalArgumentException {
        super.render();
        Preconditions.checkArgument(width == height,
            "Width and height of the circle are different, they must be the same [either %d or %d]",
            width, height); // todo: better way to handle this?

        glBegin(GL_TRIANGLE_FAN);
        {
            glVertex2d(x, y);
            for (var i = 0; i <= FACTOR; i++) {
                glVertex2d((x + (width * cos(i * (PI * 2f) / FACTOR))),
                    (y + (width * sin(i * (PI * 2f) / FACTOR))));
            }
        }
        glEnd();
    }
}
