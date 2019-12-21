package com.github.frontear.infinity.modules.impl;

import com.github.frontear.infinity.modules.Module;
import org.frontear.infinity.modules.*;
import org.lwjgl.input.Keyboard;

// implementation in MixinRender
public final class HealthTag extends Module {
    public HealthTag() {
        super(Keyboard.KEY_NONE, false, Category.RENDER);
    }
}
