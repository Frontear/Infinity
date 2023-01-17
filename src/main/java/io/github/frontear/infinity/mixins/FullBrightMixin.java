package io.github.frontear.infinity.mixins;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.FullBright;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightTexture.class)
abstract class FullBrightMixin {
    @Redirect(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z", ordinal = 0))
    private boolean forceFalseNightVisionEffect(LocalPlayer player, MobEffect night_vision) {
        return shouldDoBright() || player.hasEffect(night_vision);
    }

    @Redirect(method = "updateLightTexture", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"))
    private float preventGameRendererCalculationForFalseNightVision(LivingEntity player, float partialTicks) {
        return shouldDoBright() ? 1.0F : GameRenderer.getNightVisionScale(player, partialTicks); // 1.0F pulled from getNightVisionScale
    }

    // TODO: design implications of checking other tweaks within tweak-specific mixins
    private boolean shouldDoBright() {
        return TweakManager.isTweakEnabled(FullBright.class) || TweakManager.isTweakEnabled(Xray.class);
    }
}
