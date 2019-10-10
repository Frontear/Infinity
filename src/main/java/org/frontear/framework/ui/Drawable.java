package org.frontear.framework.ui;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CURRENT_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import java.awt.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.FieldDefaults;
import lombok.val;
import org.lwjgl.input.Mouse;

/**
 * Represents an object that is to be drawn to the screen via OpenGL
 */
@FieldDefaults(level = AccessLevel.PRIVATE)
public abstract class Drawable {
    @Getter int x, y;
    @Getter @Setter int width, height;
    @Getter @Setter Color color;

    /**
     * An empty constructor for a {@link Drawable} object
     */
    public Drawable() {
        this(0, 0, 0, 0, null);
    }

    /**
     * The constructor for a {@link Drawable} object
     *
     * @param x      The x-coordinate of the {@link Drawable}
     * @param y      The y-coordinate of the {@link Drawable}
     * @param width  The width of the {@link Drawable}
     * @param height The height of the {@link Drawable}
     * @param color  The color of the {@link Drawable}
     */
    public Drawable(int x, int y, int width, int height, Color color) {
        this.x = x;
        this.y = y;
        this.width = width;
        this.height = height;
        this.color = color;
    }

    /**
     * Draws the {@link Drawable} in an OpenGL context. Color is automatically applied. {@link
     * org.lwjgl.opengl.GL11#GL_BLEND} is enabled, and {@link org.lwjgl.opengl.GL11#GL_TEXTURE_2D}
     * is disabled
     */
    public void draw() {
        glPushAttrib(GL_CURRENT_BIT);
        glPushMatrix();
        {
            val blend = !glIsEnabled(GL_BLEND);
            val texture_2d = glIsEnabled(GL_TEXTURE_2D);
            val cull_face = glIsEnabled(GL_CULL_FACE);

            if (blend) {
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }
            if (texture_2d) {
                glDisable(GL_TEXTURE_2D);
            }
            if (cull_face) {
                glDisable(GL_CULL_FACE);
            }
            glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f,
                color.getAlpha() / 255f);

            {
                render(x, y, width, height);
            }

            if (blend) {
                glDisable(GL_BLEND);
            }
            if (texture_2d) {
                glEnable(GL_TEXTURE_2D);
            }
            if (cull_face) {
                glEnable(GL_CULL_FACE);
            }
        }
        glPopMatrix();
        glPopAttrib();
    }

    /**
     * This is called after a GLContext has been created via {@link Drawable#draw()}
     *
     * @param x      The x-coordinate of the {@link Drawable}
     * @param y      The y-coordinate of the {@link Drawable}
     * @param width  The width of the {@link Drawable}
     * @param height The height of the {@link Drawable}
     */
    protected abstract void render(int x, int y, int width, int height);

    /**
     * This should be called when {@link Mouse#next()} is available, in order to allow the {@link
     * Drawable} to handle mouse input, such as clicking or hovering
     *
     * @param mouseX The x-coordinate of the {@link Mouse}
     * @param mouseY The y-coordinate of the {@link Mouse}
     * @param button The button being pressed by the {@link Mouse}, 0 == LEFT, 1 == RIGHT, else ==
     *               NONE
     */
    public void mouse(int mouseX, int mouseY, int button) {
        click(mouseX, mouseY, mouseX >= getX() && mouseY >= getY() && mouseX < getX() + getWidth()
            && mouseY < getY() + getHeight(), button);
    }

    /**
     * This is called after {@link Drawable#mouse(int, int, int)} has calculated whether this object
     * is being hovered or not
     *
     * @param mouseX The x-coordinate of the {@link Mouse}
     * @param mouseY The y-coordinate of the {@link Mouse}
     * @param hover  If the x and y coordinates are within this {@link Drawable} bounds
     * @param button The button being pressed by the {@link Mouse}, 0 == LEFT, 1 == RIGHT, else ==
     *               NONE
     */
    protected abstract void click(int mouseX, int mouseY, boolean hover, int button);

    /**
     * Sets the position of the {@link Drawable} in an x-y plane
     *
     * @param x The new x-coordinate of the {@link Drawable}
     * @param y The new y-coordinate of the {@link Drawable}
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
}
