package org.frontear.infinity.modules.impl;

import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.modules.gui.ClickGuiScreen;
import org.lwjgl.input.Keyboard;

public class ClickGui extends Module {
	private final ClickGuiScreen screen = new ClickGuiScreen();

	public ClickGui() {
		super(Keyboard.KEY_RSHIFT, false, Category.NONE);
	}

	@Override public void toggle() {
		mc.displayGuiScreen(screen);
		super.setActive(false);
	}
}
