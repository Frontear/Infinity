package org.frontear.mixins;

import net.minecraft.client.resources.AbstractResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.awt.*;
import java.awt.image.BufferedImage;

@Mixin(AbstractResourcePack.class) public abstract class MixinAbstractResourcePack {
	private static final int SIZE = 64; // the scaled resolution size

	/**
	 * @author prplz Please see https://prplz.io/memoryfix/ for more information
	 */
	@Inject(method = "getPackImage",
			at = @At("RETURN")) private void getPackImage(CallbackInfoReturnable<BufferedImage> info) {
		final BufferedImage image = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
		final Graphics2D graphics = image.createGraphics();
		graphics.drawImage(info.getReturnValue(), 0, 0, SIZE, SIZE, null);
		graphics.dispose();

		info.setReturnValue(image);
	}
}
