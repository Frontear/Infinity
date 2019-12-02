package org.frontear.framework.graphics.shapes;

import static java.lang.Math.PI;
import static java.lang.Math.cos;
import static java.lang.Math.sin;
import static org.lwjgl.opengl.GL11.GL_TRIANGLE_FAN;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import com.google.common.base.Preconditions;
import java.awt.Color;
import lombok.NonNull;
import lombok.var;
import org.frontear.framework.graphics.impl.Renderable;

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
