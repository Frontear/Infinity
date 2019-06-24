package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Minecraft.class) public abstract class MixinMinecraft {
	@Shadow @Final private static Logger logger;

	@Inject(method = "startGame",
			at = @At("TAIL")) private void startGame(CallbackInfo info) {
		logger.info("Hello Mixin! ${modid}");
	}
}
