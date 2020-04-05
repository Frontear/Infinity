package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.NonNull;

@ModuleInfo(bind = Keyboard.KEY_V, friendly = true, category = ModuleCategory.RENDER)
public class NoFOV extends Module {
    public NoFOV(final @NonNull InfinityMod infinity) {
        super(infinity);
    }
}
