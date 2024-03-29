package io.github.frontear.infinity.mixins.tweaks.xray;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.xray.Xray;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

// FIXME: very dangerous high-level changes. be on the lookout for possible issues in extraneous locations
@Mixin({ MultiPartBakedModel.class, SimpleBakedModel.class, WeightedBakedModel.class })
abstract class BakedModelMixin {
    @Inject(method = "getQuads", at = @At("HEAD"), cancellable = true)
    private void returnNoRenderingQuads(@Nullable BlockState state, @Nullable Direction direction, RandomSource random, CallbackInfoReturnable<List<BakedQuad>> info) {
        if (state != null) {
            if (TweakManager.get(Xray.class).isEnabled() && !TweakManager.get(Xray.class).isExcluded(state.getBlock())) {
                info.setReturnValue(Collections.emptyList());
            }
        }
    }

    @Inject(method = "useAmbientOcclusion", at = @At("HEAD"), cancellable = true)
    private void disableAmbientOcclusion(CallbackInfoReturnable<Boolean> info) {
        if (TweakManager.get(Xray.class).isEnabled()) {
            info.setReturnValue(false);
        }
    }
}
