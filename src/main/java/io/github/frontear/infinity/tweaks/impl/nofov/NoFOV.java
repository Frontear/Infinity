package io.github.frontear.infinity.tweaks.impl.nofov;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import org.lwjgl.glfw.GLFW;

public final class NoFOV extends AbstractTweak {
    public NoFOV() {
        super(GLFW.GLFW_KEY_V);
    }
}
