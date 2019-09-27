package org.frontear.infinity.modules.impl;

import lombok.val;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.render.OverlayEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.ui.renderer.TextPositions;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Info extends Module {
	public Info() {
		super(Keyboard.KEY_O, false, Category.RENDER);
	}

	@SubscribeEvent public void onOverlay(OverlayEvent event) {
		val renderer = Infinity.inst().getTextRenderer();

		val player = mc.thePlayer;
		if (player != null && player.hasPlayerInfo()) {
			renderer.render(TextPositions.LEFT, "Ping: ${player.getPlayerInfo().getResponseTime()}ms", Color.WHITE, false, 1f);
			renderer.render(TextPositions.LEFT, "FPS: ${net.minecraft.client.Minecraft.getDebugFPS()}", Color.WHITE, false, 1f);
		}
	}
}
