package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.NonNull;

@Deprecated
@ModuleInfo(bind = Keyboard.KEY_N, friendly = false, category = ModuleCategory.RENDER)
public final class NameProtect extends Module {
    public NameProtect(@NonNull final InfinityMod infinity) {
        super(infinity);
    }
}
