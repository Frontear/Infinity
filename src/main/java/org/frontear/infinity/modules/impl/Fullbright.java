package org.frontear.infinity.modules.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

// implementation in MixinEntityRenderer
@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Fullbright extends Module {
    public Fullbright() {
        super(Keyboard.KEY_B, true, Category.RENDER);
    }
}
