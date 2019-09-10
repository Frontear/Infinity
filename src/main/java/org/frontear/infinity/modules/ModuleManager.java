package org.frontear.infinity.modules;

import lombok.NonNull;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.config.impl.Config;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.modules.impl.Ghost;
import org.frontear.wrapper.IMinecraftWrapper;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.Iterator;

public final class ModuleManager extends Manager<Module> {
	public ModuleManager(@NonNull Config config) {
		super("org.frontear.infinity.modules.impl");

		getObjects().forEach(config::register);
	}

	@SubscribeEvent public void onKey(KeyEvent event) {
		final boolean ghost = get(Ghost.class).isActive();

		if (event.isPressed()) {
			getObjects().filter(x -> x.getBind() == event.getKey()).filter(x -> !ghost || x.isSafe())
					.forEach(Module::toggle);
		}
	}

	@SubscribeEvent public void onRender(RenderGameOverlayEvent.Post event) {
		if (!get(Ghost.class).isActive() && event.type == RenderGameOverlayEvent.ElementType.TEXT) {
			Iterator<Module> modules = getObjects().filter(Module::isActive).iterator();
			int iter = 0;

			while (modules.hasNext()) {
				final Module module = modules.next();
				final String name = String.format("%s [%s]", module.getName(), Keyboard.getKeyName(module.getBind()));
				final FontRenderer renderer = IMinecraftWrapper.getMinecraft().getFontRenderer();

				renderer.drawString(name, event.resolution.getScaledWidth() - renderer
						.getStringWidth(name) - 1, 1 + renderer.FONT_HEIGHT * iter++, Color.WHITE.getRGB());
			}
		}
	}
}
