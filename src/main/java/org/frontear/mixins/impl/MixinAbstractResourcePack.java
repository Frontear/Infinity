package org.frontear.mixins.impl;

import lombok.val;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import org.spongepowered.asm.mixin.*;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

@Mixin(AbstractResourcePack.class) public abstract class MixinAbstractResourcePack {
	private static final int SIZE = 64; // the scaled resolution size

	/**
	 * @author prplz
	 * @reason Please see https://prplz.io/memoryfix/ for more information
	 */
	@Overwrite public BufferedImage getPackImage() throws IOException {
		val image = TextureUtil.readBufferedImage(this.getInputStreamByName("pack.png"));
		if (image != null) {
			val scaled = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
			val graphics = scaled.createGraphics();
			graphics.drawImage(image, 0, 0, SIZE, SIZE, null);
			graphics.dispose();

			return scaled;
		}

		return null;
	}

	@Shadow protected abstract InputStream getInputStreamByName(String name) throws IOException;
}
