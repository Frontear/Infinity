package org.frontear.infinity.modules.impl;

import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

public final class Panic extends Module {
	public Panic() {
		super(Keyboard.KEY_P, true);
	}

	@Override public void toggle() {
		Infinity.inst().getModules().getObjects().filter(Module::isActive).forEach(Module::toggle);

		super.setActive(false);
	}
}
