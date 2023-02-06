package io.github.frontear.infinity.tweaks.impl.fullbright;

import io.github.frontear.infinity.tweaks.AbstractTweak;
import org.lwjgl.glfw.GLFW;

public final class FullBright extends AbstractTweak {
    public FullBright() {
        super(GLFW.GLFW_KEY_B);
    }
}
