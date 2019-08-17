package org.frontear.infinity.modules.impl;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import org.frontear.framework.utils.TimeUnit;
import org.frontear.framework.utils.Timer;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.concurrent.ThreadLocalRandom;

public class AutoClicker extends Module {
	private final Timer timer = new Timer();
	private final int[] cps = { 11, 13 }; // autoclicker does not actually hit these speeds, since they serve more so as a range

	public AutoClicker() {
		super(Keyboard.KEY_C, true);
	}

	@SubscribeEvent public void onTick(TickEvent.PlayerTickEvent event) {
		if (event.phase == TickEvent.Phase.START && event.player instanceof EntityPlayerSP && !event.player
				.isUsingItem()) {
			final boolean elapsed = timer
					.hasElapsed(TimeUnit.MILLISECOND, 1000 / ThreadLocalRandom.current().nextInt(cps[0], cps[1] + 1));
			final boolean attacking = mc.getGameSettings().keyBindAttack.isKeyDown();

			if (!attacking || elapsed) {
				timer.reset();

				if (attacking) {
					mc.clickMouse(true);
				}
			}
		}
	}
}
