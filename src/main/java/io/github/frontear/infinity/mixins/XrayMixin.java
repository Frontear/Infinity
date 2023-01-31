package io.github.frontear.infinity.mixins;

import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.LiquidBlockRenderer;
import net.minecraft.client.renderer.chunk.ChunkRenderDispatcher;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RenderShape;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.material.Fluids;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class XrayMixin {
    @Mixin(Minecraft.class)
    static abstract class AOBypassMixin {
        // TODO: implication of disabling at a global level
        @Inject(method = "useAmbientOcclusion", at = @At("HEAD"), cancellable = true)
        private static void disallowAmbientOcclusion(CallbackInfoReturnable<Boolean> info) {
            if (TweakManager.get(Xray.class).isEnabled()) {
                info.setReturnValue(false);
            }
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

    @Mixin(Block.class)
    static abstract class BlockSideMixin {
        @Inject(method = "shouldRenderFace", at = @At("HEAD"), cancellable = true)
        private static void renderAllFaces(BlockState state, BlockGetter level, BlockPos offset, Direction face, BlockPos pos, CallbackInfoReturnable<Boolean> info) {
            if (TweakManager.get(Xray.class).isEnabled()) {
                info.setReturnValue(true);
            }
        }
    }

    @Mixin(ChunkRenderDispatcher.RenderChunk.RebuildTask.class)
    static abstract class RenderDispatcherMixin {
        private final Xray xray = TweakManager.get(Xray.class);

        @Redirect(method = "compile", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;"))
        private RenderShape skipBlockRendering(BlockState instance) {
            if (xray.isEnabled() && !xray.isExcluded(instance.getBlock())) {
                return RenderShape.INVISIBLE;
            }

            return instance.getRenderShape();
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
