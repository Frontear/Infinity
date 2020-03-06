package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import lombok.*;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(bind = GLFW.GLFW_KEY_G, category = ModuleCategory.RENDER)
public final class Ghost extends Module {
    public Ghost(final @NonNull InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public boolean toggle() {
        val toggled = super.toggle();

        if (toggled) {
            client.updateWindowTitle();
        }

        return toggled;
    }
}
