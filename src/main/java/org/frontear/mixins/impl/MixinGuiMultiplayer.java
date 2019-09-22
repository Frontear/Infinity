package org.frontear.mixins.impl;

import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import org.frontear.framework.threading.InfiniteThread;
import org.frontear.framework.utils.time.TimeUnit;
import org.frontear.framework.utils.time.Timer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GuiMultiplayer.class) public abstract class MixinGuiMultiplayer extends GuiScreen {
	private static final Timer timer = new Timer();
	private Thread refresh;

	@Inject(method = "<init>",
			at = @At("RETURN")) private void init(GuiScreen parentScreen, CallbackInfo info) {
		this.refresh = new InfiniteThread(() -> {
			if (timer.hasElapsed(TimeUnit.SECOND, 5)) {
				timer.reset();
				refreshServerList();
			}
		});

		refresh.start();
	}

	@Shadow protected abstract void refreshServerList();

	@Inject(method = "onGuiClosed",
			at = @At("TAIL")) private void onGuiClosed(CallbackInfo info) {
		refresh.interrupt();
	}
}
