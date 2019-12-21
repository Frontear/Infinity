package org.frontear.infinity.modules.impl;

import lombok.NonNull;
import org.frontear.infinity.modules.*;
import org.lwjgl.input.Keyboard;

// implementation in MixinRender
public final class HealthTag extends Module {
    public HealthTag() {
        super(Keyboard.KEY_NONE, false, Category.RENDER);
    }
}
