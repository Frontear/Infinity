package org.frontear.infinity.modules.impl;

import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import org.frontear.infinity.modules.*;
import org.frontear.infinity.modules.gui.ClickGuiScreen;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class ClickGui extends Module {
    ClickGuiScreen screen = new ClickGuiScreen();

    public ClickGui() {
        super(Keyboard.KEY_RSHIFT, false, Category.NONE);
    }

    @Override
    public void toggle() {
        mc.displayGuiScreen(screen);

        super.setActive(false);
    }
}
