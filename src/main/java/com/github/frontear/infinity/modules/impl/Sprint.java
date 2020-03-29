package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import lombok.*;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(bind = GLFW.GLFW_KEY_LEFT_SHIFT, friendly = true, category = ModuleCategory.PLAYER)
public final class Sprint extends Module {
    public Sprint(final @NonNull InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public boolean toggle() {
        val toggled = super.toggle();

        if (toggled) {
            client.options.sprintToggled = isActive();
        }

        return toggled;
    }
}
