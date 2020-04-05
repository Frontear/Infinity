package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.Fullbright;
import lombok.val;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.render.LightmapTextureManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(LightmapTextureManager.class)
abstract class LightmapTextureManagerMixin {
    @Redirect(method = "update",
        at = @At(value = "FIELD", target = "Lnet/minecraft/client/options/GameOptions;gamma:D"))
    private double update(final GameOptions instance) {
        val fullbright = InfinityLoader.getMod().getModules().get(Fullbright.class);

        return fullbright.isActive() ? 1.0D : instance.gamma;
    }
}
