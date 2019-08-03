package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import org.frontear.infinity.events.ShutdownEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class) public abstract class MixinMinecraft {
	@Shadow @Final private static Logger logger;

	@Inject(method = "startGame",
			at = @At("TAIL")) private void startGame(CallbackInfo info) {
		logger.info("Hello Mixin!");
	}

	@Inject(method = "shutdownMinecraftApplet",
			at = @At("HEAD")) private void shutdownMinecraftApplet(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new ShutdownEvent());
	}
}
