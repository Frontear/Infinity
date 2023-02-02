package io.github.frontear.infinity.tweaks.impl;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import org.lwjgl.glfw.GLFW;

public final class ESP extends AbstractTweak {
    public ESP() {
        super(GLFW.GLFW_KEY_B);
    }
}