package org.frontear.infinity;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.client.impl.Client;
import org.frontear.infinity.events.execution.ShutdownEvent;
import org.frontear.infinity.events.execution.StartupEvent;
import org.lwjgl.opengl.Display;

public class Infinity extends Client {
	private static Infinity inst;

	private Infinity() {
		super();
	}

	public static Infinity inst() {
		return inst == null ? inst = new Infinity() : inst;
	}

	@SubscribeEvent public void onStartup(StartupEvent event) {
		getLogger().debug("Hello %s!", getModInfo().getName());
		Display.setTitle(getModInfo().getFullname());

		getConfig().load();
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		getLogger().debug("Goodbye %s!", getModInfo().getName());

		getConfig().save();
	}
}
