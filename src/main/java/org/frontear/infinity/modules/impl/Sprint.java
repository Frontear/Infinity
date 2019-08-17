package org.frontear.infinity.modules.impl;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

public class Sprint extends Module {
	public Sprint() {
		super(Keyboard.KEY_LSHIFT);
	}

	@Override protected void onToggle(boolean active) {
		Infinity.inst().getLogger().info("Active: %b", active);
	}

	@SubscribeEvent public void onKey(KeyEvent event) {
		Infinity.inst().getLogger()
				.info("Key: %s, Pressed: %b", Keyboard.getKeyName(event.getKey()), event.isPressed());
	}
}
