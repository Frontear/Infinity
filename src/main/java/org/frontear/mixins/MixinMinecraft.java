package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraftforge.common.MinecraftForge;
import org.apache.logging.log4j.Logger;
import org.frontear.infinity.events.ShutdownEvent;
import org.frontear.infinity.events.StartupEvent;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class) public abstract class MixinMinecraft {
	@Shadow @Final private static Logger logger;

	@Inject(method = "startGame",
			at = @At(value = "TAIL")) private void startGame(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new StartupEvent(logger));
	}

	@Inject(method = "shutdownMinecraftApplet",
			at = @At(value = "HEAD")) private void shutdownMinecraftApplet(CallbackInfo info) {
		MinecraftForge.EVENT_BUS.post(new ShutdownEvent(logger));
	}
}
