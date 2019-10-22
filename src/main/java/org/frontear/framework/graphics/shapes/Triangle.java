package org.frontear.framework.graphics.shapes;

import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glVertex2d;

import org.frontear.framework.graphics.impl.Renderable;

public final class Triangle extends Renderable {
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
