package org.frontear.framework.graphics;

import java.awt.Color;
import lombok.NonNull;

/**
 * Represents a global rendering manager which renders a group of {@link IRenderable}
 */
public interface IRenderer {
    /**
     * Creates an OpenGL context for use by {@link IRenderable}. This <b>must</b> throw an {@link
     * IllegalArgumentException} if {@link IRenderer#isActive()} is true
     */
    void begin(@NonNull final Color color) throws IllegalArgumentException;

    /**
     * Closes the previously made OpenGL context by {@link IRenderer#begin(Color)}. This should
     * ideally reset the modifications made, and the context should be in the same state as it was
     * before {@link IRenderer#begin(Color)} was invoked. This <b>must</b> throw an {@link
     * IllegalArgumentException} if {@link IRenderer#isActive()} is false
     */
    void end() throws IllegalArgumentException;

    /**
     * @return Whether {@link IRenderer#begin(Color)} was invoked, and subsequently a valid OpenGL
     * context was created through it
     */
    boolean isActive();
}
