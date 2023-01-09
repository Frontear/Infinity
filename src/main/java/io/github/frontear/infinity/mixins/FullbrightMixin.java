package io.github.frontear.infinity.mixins;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Fullbright;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(LightTexture.class)
abstract class FullbrightMixin {
    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;hasEffect(Lnet/minecraft/world/effect/MobEffect;)Z", ordinal = 0), method = "updateLightTexture")
    private boolean forceFalseNightVisionEffect(LocalPlayer player, MobEffect night_vision) {
        return TweakManager.isTweakEnabled(Fullbright.class) || player.hasEffect(night_vision);
    }

    @Redirect(at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/GameRenderer;getNightVisionScale(Lnet/minecraft/world/entity/LivingEntity;F)F"), method = "updateLightTexture")
    private float preventGameRendererCalculationForFalseNightVision(LivingEntity player, float partialTicks) {
        return TweakManager.isTweakEnabled(Fullbright.class) ? 1.0F : GameRenderer.getNightVisionScale(player, partialTicks); // 1.0F pulled from getNightVisionScale
    }
}
