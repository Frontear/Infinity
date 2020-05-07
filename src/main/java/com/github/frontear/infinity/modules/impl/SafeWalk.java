package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.NonNull;

@ModuleInfo(bind = Keyboard.KEY_M, friendly = true, category = ModuleCategory.PLAYER)
public final class SafeWalk extends Module {
    public SafeWalk(@NonNull final InfinityMod infinity) {
        super(infinity);
    }
}
