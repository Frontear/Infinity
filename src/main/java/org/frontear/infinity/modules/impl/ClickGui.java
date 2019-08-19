package org.frontear.infinity.modules.impl;

import org.frontear.infinity.modules.Module;
import org.frontear.infinity.modules.gui.ClickGuiScreen;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
	public ClickGui() {
		super(Keyboard.KEY_RSHIFT, false);
	}

	@Override public void toggle() {
		mc.displayGuiScreen(new ClickGuiScreen());
		super.setActive(false);
	}
}
