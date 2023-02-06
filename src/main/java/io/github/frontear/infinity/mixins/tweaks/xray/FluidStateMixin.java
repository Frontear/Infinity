package io.github.frontear.infinity.mixins.tweaks.xray;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.xray.Xray;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// FIXME: very dangerous high-level changes. be on the lookout for possible issues in extraneous locations
@Mixin(FluidState.class)
abstract class FluidStateMixin {
    @Shadow
    public abstract boolean is(Fluid fluid);

    @Inject(method = "isEmpty", at = @At("HEAD"), cancellable = true)
    private void skipRendering(CallbackInfoReturnable<Boolean> info) {
        if (TweakManager.get(Xray.class).isEnabled() && (is(Fluids.WATER) || is(Fluids.FLOWING_WATER))) {
            info.setReturnValue(true);
        }
    }
}