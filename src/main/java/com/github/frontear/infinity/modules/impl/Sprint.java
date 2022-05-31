package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.NonNull;

@ModuleInfo(bind = Keyboard.KEY_LEFT_SHIFT, friendly = true, category = ModuleCategory.PLAYER)
public final class Sprint extends Module {
    public Sprint(@NonNull final InfinityMod infinity) {
        super(infinity);
    }
}
