package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import lombok.*;
import org.lwjgl.glfw.GLFW;

@ModuleInfo(bind = GLFW.GLFW_KEY_P, friendly = true, category = ModuleCategory.NONE)
public final class Panic extends Module {
    public Panic(final @NonNull InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public boolean toggle() {
        val toggled = super.toggle();

        if (toggled) {
            val mods = infinity.getModules().stream().filter(Module::isActive);
            mods.forEach(Module::toggle);
        }

        return toggled;
    }
}
