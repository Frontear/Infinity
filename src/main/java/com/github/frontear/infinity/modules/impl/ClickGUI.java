package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.modules.*;
import com.github.frontear.infinity.utils.keyboard.Keyboard;
import lombok.NonNull;

@ModuleInfo(bind = Keyboard.KEY_RIGHT_SHIFT, friendly = false, category = ModuleCategory.NONE)
public final class ClickGUI extends Module {
    private final ModuleScreen screen;

    public ClickGUI(final @NonNull InfinityMod infinity) {
        super(infinity);
        this.screen = new ModuleScreen(infinity);
    }

    @Override
    public boolean toggle() {
        client.openScreen(screen);

        return false;
    }
}
