package org.frontear.infinity.modules.impl;

import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

// implementation in MixinEntity
public final class SafeWalk extends Module {
	public SafeWalk() {
		super(Keyboard.KEY_M, true, Category.PLAYER); // is this really safe?
	}
}
