package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.Module;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.*;

@ModuleInfo(bind = Keyboard.KEY_B, friendly = true, category = ModuleCategory.RENDER)
public final class Fullbright extends Module {
    public Fullbright(@NonNull final InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public boolean toggle() {
        val toggled = super.toggle();

        if (toggled) {
            client.worldRenderer.reload();
        }

        return toggled;
    }
}
