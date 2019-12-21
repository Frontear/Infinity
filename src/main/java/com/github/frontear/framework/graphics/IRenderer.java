package com.github.frontear.framework.graphics;

import lombok.NonNull;

/**
 * Represents a global rendering manager which renders a group of {@link IRenderable}
 */
public interface IRenderer {
    /**
     * Creates an OpenGL context for use by {@link IRenderable}. This <b>must</b> throw an {@link
     * IllegalArgumentException} if {@link IRenderer#isActive()} is true
     */
    void begin() throws IllegalArgumentException;

    /**
     * Runs a {@link Runnable}, disabling the OpenGL context with {@link IRenderer#end()} and
     * re-enabling it at the end of the execution with {@link IRenderer#begin()}
     *
     * @param render The group of {@link Runnable} to run outside the OpenGL context
     */
    void escapeContext(@NonNull final Runnable render);

    /**
     * Closes the previously made OpenGL context by {@link IRenderer#begin()}. This should ideally
     * reset the modifications made, and the context should be in the same state as it was before
     * {@link IRenderer#begin()} was invoked. This <b>must</b> throw an {@link
     * IllegalArgumentException} if {@link IRenderer#isActive()} is false
     */
    void end() throws IllegalArgumentException;

    /**
     * @return Whether {@link IRenderer#begin()} was invoked, and subsequently a valid OpenGL
     * context was created through it
     */
    boolean isActive();
}
