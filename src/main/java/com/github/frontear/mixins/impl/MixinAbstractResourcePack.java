package com.github.frontear.mixins.impl;

import java.awt.image.BufferedImage;
import java.io.InputStream;
import lombok.val;
import net.minecraft.client.renderer.texture.TextureUtil;
import net.minecraft.client.resources.AbstractResourcePack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(AbstractResourcePack.class)
public abstract class MixinAbstractResourcePack {
    private static final int SIZE = 64; // the scaled resolution size

    /**
     * @author prplz
     * @reason Please see https://prplz.io/memoryfix/ for more information
     */
    @Redirect(method = "getPackImage", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/renderer/texture/TextureUtil;readBufferedImage(Ljava/io/InputStream;)Ljava/awt/image/BufferedImage;"))
    private BufferedImage getPackImage(final InputStream stream) {
        val image = TextureUtil.readBufferedImage(stream);
        if (image != null) {
            val scaled = new BufferedImage(SIZE, SIZE, BufferedImage.TYPE_INT_ARGB);
            val graphics = scaled.createGraphics();
            graphics.drawImage(image, 0, 0, SIZE, SIZE, null);
            graphics.dispose();

            return scaled;
        }

        return null;
    }
}
