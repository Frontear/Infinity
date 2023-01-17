package io.github.frontear.infinity.mixins;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.Xray;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.BlockRenderDispatcher;
import net.minecraft.client.renderer.block.ModelBlockRenderer;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.BlockAndTintGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

abstract class XrayMixin {
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
            if (TweakManager.isTweakEnabled(Xray.class)) {
                info.cancel();
            }
        }
    }

    @Mixin(Minecraft.class)
    static abstract class AOMixin {
        // TODO: implications to rendering outside of xray, could perhaps be better to just bypass AO rendering for blocks in the renderer.tessellateBlock
        @Inject(method = "useAmbientOcclusion", at = @At("RETURN"), cancellable = true)
        private static void disableAO(CallbackInfoReturnable<Boolean> info) {
            if (TweakManager.isTweakEnabled(Xray.class)) {
                info.setReturnValue(false);
            }
        }
    }
}
