package org.frontear.infinity;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.client.impl.Client;
import org.frontear.infinity.events.execution.ShutdownEvent;
import org.frontear.infinity.events.execution.StartupEvent;
import org.frontear.infinity.modules.ModuleManager;
import org.lwjgl.opengl.Display;

public final class Infinity extends Client {
	private static Infinity inst;

	private ModuleManager module_manager;

	private Infinity() {
		super();

		MinecraftForge.EVENT_BUS.register(this.module_manager = new ModuleManager(getConfig()));
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

	public ModuleManager getModules() {
		return module_manager;
	}
}
