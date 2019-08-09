package org.frontear.infinity;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.client.Client;
import org.frontear.infinity.events.ShutdownEvent;
import org.frontear.infinity.events.StartupEvent;
import org.lwjgl.opengl.Display;

public class Infinity extends Client {
	private static Infinity inst;

	private Infinity() {
		super("${name}");
	}

	public static Infinity inst() {
		return inst == null ? inst = new Infinity() : inst;
	}

	@SubscribeEvent public void onStartup(StartupEvent event) {
		getLogger().info("Hello ${name}!");
		Display.setTitle("${name} ${version}");
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		getLogger().info("Goodbye ${name}!");
	}
}
