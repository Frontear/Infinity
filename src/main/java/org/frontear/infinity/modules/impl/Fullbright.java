package org.frontear.infinity.modules.impl;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.execution.ShutdownEvent;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

public class Fullbright extends Module {
	private float last_gamma;

	public Fullbright() {
		super(Keyboard.KEY_B);
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		mc.getGameSettings().gammaSetting = last_gamma; // we don't want 100f to be stored
		mc.getGameSettings().saveOptions();
	}

	@Override protected void onToggle(boolean active) {
		if (active) {
			last_gamma = mc.getGameSettings().gammaSetting;
		}

		mc.getGameSettings().gammaSetting = active ? 100f : last_gamma;
	}
}
