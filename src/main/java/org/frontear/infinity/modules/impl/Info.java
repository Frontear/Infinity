package org.frontear.infinity.modules.impl;

import lombok.val;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.render.OverlayEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.ui.renderer.TextPositions;
import org.lwjgl.input.Keyboard;

import java.awt.*;

public class Info extends Module {
	private NetworkPlayerInfo info = null;

	public Info() {
		super(Keyboard.KEY_O, false, Category.RENDER);
	}

	@SubscribeEvent public void onOverlay(OverlayEvent event) {
		val renderer = Infinity.inst().getTextRenderer();

		if (info == null) {
			info = mc.getNetHandler().getPlayerInfo(mc.thePlayer.getUniqueID());
		}

		renderer.render(TextPositions.LEFT, "Ping: ${info.getResponseTime()}ms", Color.WHITE, false, 1f);
		renderer.render(TextPositions.LEFT, "FPS: ${net.minecraft.client.Minecraft.getDebugFPS()}", Color.WHITE, false, 1f);

	}
}
