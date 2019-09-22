package org.frontear.infinity;

import lombok.*;
import lombok.experimental.FieldDefaults;
import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.client.impl.Client;
import org.frontear.infinity.commands.CommandManager;
import org.frontear.infinity.events.client.ShutdownEvent;
import org.frontear.infinity.events.client.StartupEvent;
import org.frontear.infinity.events.render.OverlayEvent;
import org.frontear.infinity.modules.ModuleManager;
import org.frontear.infinity.modules.impl.Ghost;
import org.lwjgl.opengl.Display;

import java.awt.*;

import static org.lwjgl.opengl.GL11.glScalef;

@FieldDefaults(level = AccessLevel.PRIVATE) public final class Infinity extends Client {
	private static Infinity inst;

	@Getter ModuleManager modules;
	@Getter CommandManager commands;

	private Infinity() {
		super();

		MinecraftForge.EVENT_BUS.register(this.modules = new ModuleManager(getConfig()));
		MinecraftForge.EVENT_BUS.register(this.commands = new CommandManager(getInfo()));
	}

	public static Infinity inst() {
		return inst == null ? inst = new Infinity() : inst;
	}

	@SubscribeEvent public void onOverlay(OverlayEvent event) {
		if (!event.isDebugging() && !modules.get(Ghost.class).isActive()) {
			val scale = 2.25f;
			glScalef(scale, scale, 1);
			{
				Minecraft.getMinecraft().fontRendererObj
						.drawStringWithShadow(getInfo().getName(), 2 / scale, 2 / scale, Color.WHITE.getRGB());
			}
			glScalef(1 / scale, 1 / scale, 1);
		}
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
