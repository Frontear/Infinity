package org.frontear.infinity.modules.impl;

import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

public class SafeWalk extends Module {
	public SafeWalk() {
		super(Keyboard.KEY_M, true); // is this really safe?
	}
}
