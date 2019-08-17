package org.frontear.infinity.modules;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.config.impl.Config;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.events.input.KeyEvent;

public class ModuleManager extends Manager<Module> {
	public ModuleManager(Config config) {
		super("org.frontear.infinity.modules.impl");

		getObjects().forEachRemaining(config::register);
	}

	@SubscribeEvent public void onKey(KeyEvent event) {
		if (event.isPressed()) {
			getObjects().forEachRemaining(x -> {
				if (x.getBind() == event.getKey()) {
					x.setActive(!x.isActive());
				}
			});
		}
	}
}
