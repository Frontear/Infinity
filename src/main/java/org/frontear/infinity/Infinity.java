package org.frontear.infinity;

import lombok.Getter;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.client.impl.Client;
import org.frontear.infinity.commands.CommandManager;
import org.frontear.infinity.events.client.ShutdownEvent;
import org.frontear.infinity.events.client.StartupEvent;
import org.frontear.infinity.modules.ModuleManager;
import org.lwjgl.opengl.Display;

public final class Infinity extends Client {
	private static Infinity inst;

	@Getter private ModuleManager modules;
	@Getter private CommandManager commands;

	private Infinity() {
		super();

		MinecraftForge.EVENT_BUS.register(this.modules = new ModuleManager(getConfig()));
		MinecraftForge.EVENT_BUS.register(this.commands = new CommandManager(getInfo()));
	}

	public static Infinity inst() {
		return inst == null ? inst = new Infinity() : inst;
	}

	@SubscribeEvent public void onStartup(StartupEvent event) {
		getLogger().debug("Hello %s!", getInfo().getName());
		Display.setTitle(getInfo().getFullname());

		getConfig().load();
	}

	@SubscribeEvent public void onShutdown(ShutdownEvent event) {
		getLogger().debug("Goodbye %s!", getInfo().getName());

		getConfig().save();
	}
}
