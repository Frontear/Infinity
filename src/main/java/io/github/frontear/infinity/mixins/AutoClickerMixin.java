package io.github.frontear.infinity.mixins;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.AutoClicker;
import net.minecraft.client.Minecraft;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.phys.HitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
abstract class AutoClickerMixin {
    @Shadow @Nullable public HitResult hitResult;
    @Shadow @Nullable public LocalPlayer player;

    @Shadow protected abstract boolean startAttack();

    @Inject(at = @At("TAIL"), method = "startAttack", cancellable = true)
    private void enableContinueAttackForEntityResult(CallbackInfoReturnable<Boolean> info) {
        if (TweakManager.isModEnabled(AutoClicker.class) && hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            info.setReturnValue(true);
        }
    }

    @Inject(at = @At("TAIL"), method = "continueAttack")
    private void invokeStartAttackOnEntity(boolean leftClick, CallbackInfo info) {
        if (TweakManager.isModEnabled(AutoClicker.class) && leftClick && hitResult != null && hitResult.getType() == HitResult.Type.ENTITY) {
            if (player != null && player.getAttackStrengthScale(0.0F) >= 1.0F) {
                this.startAttack();
            }
        }
    }
}