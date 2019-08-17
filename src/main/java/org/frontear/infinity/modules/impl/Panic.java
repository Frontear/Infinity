package org.frontear.infinity.modules.impl;

import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

public class Panic extends Module {
	public Panic() {
		super(Keyboard.KEY_P);
	}

	@Override public void setActive(boolean active) {
		Infinity.inst().getModules().getObjects().filter(Module::isActive).forEach(x -> x.setActive(!x.isActive()));

		super.setActive(false);
	}
}
