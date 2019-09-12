package org.frontear.mixins;

import lombok.var;
import net.minecraft.client.gui.GuiMultiplayer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.multiplayer.ServerList;
import net.minecraft.client.network.OldServerPinger;
import org.frontear.framework.threading.InfiniteThread;
import org.frontear.framework.utils.time.TimeUnit;
import org.frontear.framework.utils.time.Timer;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.net.UnknownHostException;

@Mixin(GuiMultiplayer.class) public abstract class MixinGuiMultiplayer extends GuiScreen {
	private static final Timer timer = new Timer();
	@Shadow private ServerList savedServerList;
	@Shadow @Final private OldServerPinger oldServerPinger;
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

	/**
	 * @author Frontear
	 * @reason Optimizations (I mean really, a new screen each time?)
	 */
	@Overwrite private void refreshServerList() {
		for (var i = 0; i < savedServerList.countServers(); i++) {
			oldServerPinger.ping(savedServerList.getServerData(i));
		}
	}

	@Inject(method = "onGuiClosed",
			at = @At("TAIL")) private void onGuiClosed(CallbackInfo info) {
		refresh.interrupt();
	}
}
