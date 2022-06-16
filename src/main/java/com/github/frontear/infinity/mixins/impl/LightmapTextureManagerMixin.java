package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.Fullbright;
import lombok.val;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LightmapTextureManager.class)
abstract class LightmapTextureManagerMixin {
    /**
     * @author Frontear
     * @reason Force bright display with {@link Fullbright} is active.
     */
    @Redirect(method = "update",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/option/SimpleOption;getValue()Ljava/lang/Object;",
            ordinal = 1))
    private Object update(final SimpleOption<Double> gamma) {
        val fullbright = InfinityLoader.getMod().getModules().get(Fullbright.class);

        return fullbright.isActive() ? 10.0D : gamma.getValue();
    }
}
