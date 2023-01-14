package io.github.frontear.infinity.tweaks.impl;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import org.lwjgl.glfw.GLFW;

public final class SafeWalk extends AbstractTweak {
    public SafeWalk() {
        super(GLFW.GLFW_KEY_M);
    }
}
