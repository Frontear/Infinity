package org.frontear.infinity;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.ShutdownEvent;
import org.frontear.infinity.events.StartupEvent;

public class Infinity {
	private static Infinity inst;

	private Infinity() {}

	public static Infinity inst() {
		return inst == null ? inst = new Infinity() : inst;
	}

	@SubscribeEvent public void onStartup(StartupEvent event) {
		event.getLogger().info("Hello Infinity!");
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		event.getLogger().info("Goodbye Infinity!");
	}
}
