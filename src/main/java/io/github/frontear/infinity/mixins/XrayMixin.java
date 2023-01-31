package io.github.frontear.infinity.mixins;

import com.google.common.collect.ImmutableMap;
import com.mojang.serialization.MapCodec;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.resources.model.MultiPartBakedModel;
import net.minecraft.client.resources.model.SimpleBakedModel;
import net.minecraft.client.resources.model.WeightedBakedModel;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Fluid;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collections;
import java.util.List;

// TODO: the implications of such broad, high-level changes
abstract class XrayMixin {
    @Mixin(BlockState.class)
    static abstract class BlockMixin extends BlockBehaviour.BlockStateBase {
        protected BlockMixin(Block block, ImmutableMap<Property<?>, Comparable<?>> immutableMap, MapCodec<BlockState> mapCodec) {
            super(block, immutableMap, mapCodec);
        }

        @Override
        public boolean canOcclude() {
            if (TweakManager.get(Xray.class).isEnabled()) {
                return false;
            }

            return super.canOcclude();
        }
    }

    @Mixin(FluidState.class)
    static abstract class FluidMixin {
        @Shadow
        public abstract boolean is(Fluid fluid);

        @Inject(method = "isEmpty", at = @At("HEAD"), cancellable = true)
        private void skipRendering(CallbackInfoReturnable<Boolean> info) {
            if (TweakManager.get(Xray.class).isEnabled() && (is(Fluids.WATER) || is(Fluids.FLOWING_WATER))) {
                info.setReturnValue(true);
            }
        }
    }

    @Mixin({ MultiPartBakedModel.class, SimpleBakedModel.class, WeightedBakedModel.class })
    static abstract class BakedModelMixin {
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
}
