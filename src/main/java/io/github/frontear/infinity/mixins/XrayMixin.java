package io.github.frontear.infinity.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class XrayMixin {
    @Mixin(ModelBlockRenderer.class)
    static abstract class AOBypassMixin {
        @Redirect(method = "tesselateBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;useAmbientOcclusion()Z"))
        private boolean disallowAmbientOcclusion(BakedModel instance) {
            return !TweakManager.isTweakEnabled(Xray.class) && instance.useAmbientOcclusion();
        }
    }

    @Mixin(LiquidBlockRenderer.class)
    static abstract class LiquidSideMixin {
        @Inject(method = "isFaceOccludedByState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/Shapes;blockOccudes(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/core/Direction;)Z"), cancellable = true)
        private static void renderAllFaces(BlockGetter level, Direction face, float height, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> info) {
            if (TweakManager.isTweakEnabled(Xray.class)) {
                info.setReturnValue(false);
            }
        }
    }

    @Mixin(BlockRenderDispatcher.class)
    static abstract class RenderDispatcherMixin {
        @Redirect(method = "renderBatched", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/ModelBlockRenderer;tesselateBlock(Lnet/minecraft/world/level/BlockAndTintGetter;Lnet/minecraft/client/resources/model/BakedModel;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;JI)V"))
        private void skipBlockRendering(ModelBlockRenderer instance, BlockAndTintGetter level, BakedModel model, BlockState state, BlockPos pos, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, RandomSource random, long seed, int packedOverlay) {
            if ((TweakManager.isTweakEnabled(Xray.class) && Xray.isExcluded(state.getBlock())) || !TweakManager.isTweakEnabled(Xray.class)) {
                instance.tesselateBlock(level, model, state, pos, poseStack, consumer, !TweakManager.isTweakEnabled(Xray.class) && checkSides, random, seed, packedOverlay);
            }
        }

        // TODO: consider the danger of not seeing lava when trying to xray
        @Inject(method = "renderLiquid", at = @At("HEAD"), cancellable = true)
        private void ignoreLiquidRendering(BlockPos pos, BlockAndTintGetter level, VertexConsumer consumer, BlockState blockState, FluidState fluidState, CallbackInfo info) {
            if (TweakManager.isTweakEnabled(Xray.class) && !(fluidState.is(Fluids.LAVA) || fluidState.is(Fluids.FLOWING_LAVA))) {
                info.cancel();
            }
        }
    }
}
