package io.github.frontear.infinity.mixins.esp;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Matrix4f;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.ESP;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
import net.minecraft.util.Mth;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
abstract class LevelRendererMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Shadow
    @Final
    private RenderBuffers renderBuffers;

    @Shadow
    @Final
    private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderEntity(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V"))
    private void renderHitBoxes(PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, CallbackInfo info) {
        if (minecraft.level != null && TweakManager.get(ESP.class).isEnabled() && !entityRenderDispatcher.shouldRenderHitBoxes()) {
            var buffer = renderBuffers.bufferSource().getBuffer(RenderType.lines());

            poseStack.pushPose();
            poseStack.translate(-camera.getPosition().x(), -camera.getPosition().y(), -camera.getPosition().z());

            for (var entity : minecraft.level.entitiesForRendering()) {
                if (entity == minecraft.getCameraEntity())
                    continue;

                var x = Mth.lerp(partialTick, entity.xOld, entity.getX());
                var y = Mth.lerp(partialTick, entity.yOld, entity.getY());
                var z = Mth.lerp(partialTick, entity.zOld, entity.getZ());

                var bound = entity.getType().getAABB(x, y, z);
                LevelRenderer.renderLineBox(poseStack, buffer, bound, 1.0f, 1.0f, 1.0f, 1.0f);
            }

            poseStack.popPose();
        }
    }
}