package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import lombok.NonNull;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(bind = GLFW.GLFW_KEY_ESCAPE, category = ModuleCategory.NONE)
public final class TestModule extends Module {
    public TestModule(final @NonNull InfinityMod infinity) {
        super(infinity);
    }
}
