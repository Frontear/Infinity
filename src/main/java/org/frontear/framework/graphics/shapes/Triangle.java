package org.frontear.framework.graphics.shapes;

import static org.lwjgl.opengl.GL11.*;

import java.awt.Color;
import lombok.NonNull;
import org.frontear.framework.graphics.impl.Renderable;

public final class Triangle extends Renderable {
    public Triangle(final int x, final int y, final int width, final int height,
        final @NonNull Color color) {
        super(x, y, width, height, color);
    }

    @Override
    public void render() throws IllegalArgumentException {
        super.render();

        glBegin(GL_TRIANGLES);
        {
            glVertex2d(x, y);
            glVertex2d(x - width / 2f, y + height);
            glVertex2d(x + width / 2f, y + height);
        }
        glEnd();
    }
}
