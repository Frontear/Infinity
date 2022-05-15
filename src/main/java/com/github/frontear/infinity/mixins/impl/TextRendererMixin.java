package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.NameProtect;
import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.util.math.Matrix4f;
import org.apache.commons.lang3.StringUtils;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(TextRenderer.class)
@SuppressWarnings("UnresolvedMixinReference")
abstract class TextRendererMixin {
    @Shadow
    protected abstract int drawInternal(final String text, final float x, final float y,
        final int color, final boolean shadow,
        final Matrix4f matrix, final VertexConsumerProvider vertexConsumerProvider,
        final boolean seeThrough,
        final int backgroundColor, final int light);

    @Redirect(method = "*", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/font/TextRenderer;drawInternal(Ljava/lang/String;FFIZLnet/minecraft/client/util/math/Matrix4f;Lnet/minecraft/client/render/VertexConsumerProvider;ZII)I"))
    private int drawInternal(@NonNull final TextRenderer instance, @NonNull String text,
        final float x, final float y, final int color, final boolean shadow,
        @NonNull final Matrix4f matrix,
        @NonNull final VertexConsumerProvider vertexConsumerProvider, final boolean seeThrough,
        final int backgroundColor, final int light) {
        val name_protect = InfinityLoader.getMod().getModules().get(NameProtect.class);
        if (name_protect.isActive()) {
            val username = MinecraftClient.getInstance().getSession().getUsername();

            if (StringUtils.containsIgnoreCase(text, username)) {
                text = StringUtils.replacePattern(text, "(?i)" + username, "InfinityUser");
            }
        }

        return this
            .drawInternal(text, x, y, color, shadow, matrix, vertexConsumerProvider, seeThrough,
                backgroundColor, light);
    }
}
