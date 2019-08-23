package org.frontear.infinity;

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

	private ModuleManager module_manager;
	private CommandManager command_manager;

	private Infinity() {
		super();

		MinecraftForge.EVENT_BUS.register(this.module_manager = new ModuleManager(getConfig()));
		MinecraftForge.EVENT_BUS.register(this.command_manager = new CommandManager(getModInfo()));
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

	public CommandManager getCommands() {
		return command_manager;
	}
}
