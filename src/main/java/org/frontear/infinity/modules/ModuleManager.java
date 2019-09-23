package org.frontear.infinity.modules;

import lombok.NonNull;
import lombok.val;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.config.impl.Config;
import org.frontear.framework.manager.impl.Manager;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.input.KeyEvent;
import org.frontear.infinity.events.render.OverlayEvent;
import org.frontear.infinity.modules.impl.Ghost;
import org.frontear.infinity.ui.renderer.TextPositions;

import java.awt.*;

public final class ModuleManager extends Manager<Module> {
	public ModuleManager(@NonNull Config config) {
		super("org.frontear.infinity.modules.impl");

		getObjects().forEach(config::register);
	}

	@SubscribeEvent public void onKey(KeyEvent event) {
		val ghost = get(Ghost.class).isActive();

		if (event.isPressed()) {
			getObjects().filter(x -> x.getBind() == event.getKey()).filter(x -> !ghost || x.isSafe())
					.forEach(Module::toggle);
		}
	}

	@SubscribeEvent public void onRender(OverlayEvent event) {
		if (!event.isDebugging()) {
			getObjects().filter(Module::isActive).forEach(x -> Infinity.inst().getTextRenderer()
					.render(TextPositions.RIGHT, "${x.getName()} [${org.lwjgl.input.Keyboard.getKeyName(x.getBind())}]", Color.WHITE, false, 1f));
		}
	}
}
