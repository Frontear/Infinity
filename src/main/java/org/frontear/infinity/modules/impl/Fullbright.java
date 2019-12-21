package org.frontear.infinity.modules.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.frontear.infinity.modules.*;
import org.lwjgl.input.Keyboard;

// implementation in MixinEntityRenderer
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Fullbright extends Module {
    public Fullbright() {
        super(Keyboard.KEY_B, true, Category.RENDER);
    }

    @Override
    protected void onToggle(final boolean active) {
        mc.renderGlobal.loadRenderers(); // mostly for shaders, which will break if this isn't done
    }
}
