package org.frontear.framework.graphics;

import java.awt.Color;
import lombok.NonNull;
import org.lwjgl.opengl.GL11;

/**
 * Represents a small object which will render some type of graphic through OpenGL.
 */
public interface IRenderable {
    /**
     * Renders a graphic using OpenGL {@link org.lwjgl.opengl.GL11#glBegin(int)} and {@link
     * GL11#glEnd()}. This <b>must</b> throw an {@link IllegalArgumentException} if {@link
     * IRenderer#isActive()} is false
     */
    void render() throws IllegalArgumentException;

    /**
     * Sets the renderer that will be used by this {@link IRenderable}. This should be stored in the
     * object, and manually checked and worked with.
     *
     * @param renderer The renderer that will manage this {@link IRenderable}
     */
    void setRenderer(@NonNull final IRenderer renderer);

    /**
     * @param color The new color of the specified render that will be automatically applied when
     *              {@link IRenderer#begin()} is called
     */
    void setColor(@NonNull final Color color); // todo: getColor?
}
