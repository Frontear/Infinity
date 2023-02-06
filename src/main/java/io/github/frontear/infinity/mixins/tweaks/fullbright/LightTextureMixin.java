package io.github.frontear.infinity.mixins.tweaks.fullbright;

import com.mojang.blaze3d.platform.NativeImage;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.FullBright;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.texture.DynamicTexture;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LightTexture.class)
abstract class LightTextureMixin {
    @Shadow
    @Final
    private NativeImage lightPixels;

    @Shadow
    @Final
    private DynamicTexture lightTexture;

    // TODO: does this need to set each tick?
    @Inject(method = "updateLightTexture", at = @At("HEAD"), cancellable = true)
    private void skipLightingCalculations(float partialTicks, CallbackInfo info) {
        if (shouldDoBright()) {
            for (int x = 0; x < 16; ++x) {
                for (int y = 0; y < 16; ++y) {
                    lightPixels.setPixelRGBA(x, y, -1);
                }
            }

            lightTexture.upload();
            info.cancel();
        }
    }

    // TODO: design implications of checking other tweaks within tweak-specific mixins
    private boolean shouldDoBright() {
        return TweakManager.get(FullBright.class).isEnabled() || TweakManager.get(Xray.class).isEnabled();
    }
}
