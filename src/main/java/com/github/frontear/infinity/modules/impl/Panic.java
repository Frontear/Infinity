package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.*;

@ModuleInfo(bind = Keyboard.KEY_P, friendly = true, category = ModuleCategory.NONE)
public final class Panic extends Module {
    public Panic(@NonNull final InfinityMod infinity) {
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
