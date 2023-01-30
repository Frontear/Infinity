package io.github.frontear.infinity.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
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
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class XrayMixin {
    @Mixin(ModelBlockRenderer.class)
    static abstract class AOBypassMixin {
        @Redirect(method = "tesselateBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/resources/model/BakedModel;useAmbientOcclusion()Z"))
        private boolean disallowAmbientOcclusion(BakedModel instance) {
            return !TweakManager.get(Xray.class).isEnabled() && instance.useAmbientOcclusion();
        }
    }

    @Mixin(LiquidBlockRenderer.class)
    static abstract class LiquidSideMixin {
        @Inject(method = "isFaceOccludedByState", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/phys/shapes/Shapes;blockOccudes(Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/world/phys/shapes/VoxelShape;Lnet/minecraft/core/Direction;)Z"), cancellable = true)
        private static void renderAllFaces(BlockGetter level, Direction face, float height, BlockPos pos, BlockState state, CallbackInfoReturnable<Boolean> info) {
            if (TweakManager.get(Xray.class).isEnabled()) {
                info.setReturnValue(false);
            }
        }
    }

    @Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
    static abstract class RenderDispatcherMixin {
        private final Xray xray = TweakManager.get(Xray.class);

        // TODO: fix incompatibility with indigo renderer (or really any custom renderer)
        @Redirect(method = "compile", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderBatched(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/PoseStack;Lcom/mojang/blaze3d/vertex/VertexConsumer;ZLnet/minecraft/util/RandomSource;)V"))
        private void skipBlockRendering(BlockRenderDispatcher instance, BlockState state, BlockPos pos, BlockAndTintGetter level, PoseStack poseStack, VertexConsumer consumer, boolean checkSides, RandomSource random) {
            if (!xray.isEnabled() || xray.isExcluded(state.getBlock())) {
                instance.renderBatched(state, pos, level, poseStack, consumer, !xray.isEnabled() && checkSides, random);
            }
        }

        @Redirect(method = "compile", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/block/BlockRenderDispatcher;renderLiquid(Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/BlockAndTintGetter;Lcom/mojang/blaze3d/vertex/VertexConsumer;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;)V"))
        private void ignoreLiquidRendering(BlockRenderDispatcher instance, BlockPos pos, BlockAndTintGetter level, VertexConsumer consumer, BlockState blockState, FluidState fluidState) {
            if (xray.isEnabled() && (fluidState.is(Fluids.WATER) || fluidState.is(Fluids.FLOWING_WATER))) {
                return;
            }

            instance.renderLiquid(pos, level, consumer, blockState, fluidState);
        }
    }
}
