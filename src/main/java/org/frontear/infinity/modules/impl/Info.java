package org.frontear.infinity.modules.impl;

import lombok.val;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.framework.utils.time.Timer;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.render.OverlayEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.frontear.infinity.ui.renderer.TextPositions;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.text.DecimalFormat;
import java.util.concurrent.TimeUnit;

public class Info extends Module {
	private final Timer timer = new Timer();
	private final float max_ticks = mc.timer.ticksPerSecond; // we don't need the precision
	private final DecimalFormat formatter = new DecimalFormat("#.##");
	private int ticks = 0;
	private String tps = "$max_ticks"; // max_ticks are the expected rate

	public Info() {
		super(Keyboard.KEY_O, false, Category.RENDER);
	}

	@SubscribeEvent public void onOverlay(OverlayEvent event) {
		val renderer = Infinity.inst().getTextRenderer();

		val player = mc.thePlayer;
		if (player != null && player.hasPlayerInfo()) {
			renderer.render(TextPositions.LEFT, "Ping: ${player.getPlayerInfo().getResponseTime()}ms", Color.WHITE, false, 1f);
			renderer.render(TextPositions.LEFT, "FPS: ${net.minecraft.client.Minecraft.getDebugFPS()}", Color.WHITE, false, 1f);
			renderer.render(TextPositions.LEFT, "TPS: $tps", Color.WHITE, false, 1f);
		}
	}

	@Override protected void onToggle(boolean active) {
		if (active) {
			reset();
		}
	}

	// intentionally does not clear tps, since we can cover up the fact that it is unknown to the user when they toggle back
	private void reset() {
		timer.reset();
		ticks = 0;
	}

	@SubscribeEvent(priority = EventPriority.LOWEST) public void onTick(TickEvent.ClientTickEvent event) { // calculate after everything is done
		if (event.phase == TickEvent.Phase.END) {
			if (++ticks == max_ticks) {
				this.tps = formatter.format(1000 * (max_ticks / timer.getElapsed(TimeUnit.MILLISECONDS)));

				reset();
			}
		}
	}
}
