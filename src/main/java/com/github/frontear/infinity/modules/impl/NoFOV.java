package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.NonNull;

@ModuleInfo(bind = Keyboard.KEY_V, friendly = true, category = ModuleCategory.RENDER)
public final class NoFOV extends Module {
    public NoFOV(@NonNull final InfinityMod infinity) {
        super(infinity);
    }
}
