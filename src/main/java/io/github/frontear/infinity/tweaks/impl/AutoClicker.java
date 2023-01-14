package io.github.frontear.infinity.tweaks.impl;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import org.lwjgl.glfw.GLFW;

public final class AutoClicker extends AbstractTweak {
    public AutoClicker() {
        super(GLFW.GLFW_KEY_R);
    }
}
