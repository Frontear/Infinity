package io.github.frontear.infinity.mixins.esp;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.ESP;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
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
    private EntityRenderDispatcher entityRenderDispatcher;

    @Inject(method = "renderLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/renderer/LevelRenderer;renderEntity(Lnet/minecraft/world/entity/Entity;DDDFLcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/MultiBufferSource;)V", shift = At.Shift.AFTER))
    private void renderHitBoxes(PoseStack poseStack, float partialTick, long finishNanoTime, boolean renderBlockOutline, Camera camera, GameRenderer gameRenderer, LightTexture lightTexture, Matrix4f projectionMatrix, CallbackInfo info) {
        var esp = TweakManager.get(ESP.class);

        if (minecraft.level != null && esp.isEnabled() && !entityRenderDispatcher.shouldRenderHitBoxes()) {
            var tesselator = Tesselator.getInstance();
            var buffer = tesselator.getBuilder();

            RenderSystem.setShader(GameRenderer::getRendertypeLinesShader);
            RenderSystem.lineWidth(2.5f);
            buffer.begin(VertexFormat.Mode.LINES, DefaultVertexFormat.POSITION_COLOR_NORMAL);

            poseStack.pushPose();
            poseStack.translate(-camera.getPosition().x(), -camera.getPosition().y(), -camera.getPosition().z());

            RenderSystem.applyModelViewMatrix();

            RenderSystem.enableBlend();
            RenderSystem.blendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);

            //if (Minecraft.useShaderTransparency() && minecraft.levelRenderer.getItemEntityTarget() != null) {
            //    minecraft.levelRenderer.getItemEntityTarget().bindWrite(false);
            //}

            //RenderSystem.depthMask(false);
            //RenderSystem.colorMask(false, false, false, false);

            RenderSystem.disableCull();

            for (var entity : minecraft.level.entitiesForRendering()) {
                if (entity == minecraft.getCameraEntity())
                    continue;

                var x = Mth.lerp(partialTick, entity.xOld, entity.getX());
                var y = Mth.lerp(partialTick, entity.yOld, entity.getY());
                var z = Mth.lerp(partialTick, entity.zOld, entity.getZ());

                var bound = entity.getType().getAABB(x, y, z);
                var color = esp.getColor(entity);

                LevelRenderer.renderLineBox(poseStack, buffer, bound, color[0], color[1], color[2], color[3]);
            }

            tesselator.end();
            poseStack.popPose();

            RenderSystem.lineWidth(1.0f);

            RenderSystem.applyModelViewMatrix();

            RenderSystem.disableBlend();
            RenderSystem.defaultBlendFunc();

            //if (Minecraft.useShaderTransparency()) {
            //    minecraft.getMainRenderTarget().bindWrite(false);
            //}

            //RenderSystem.depthMask(true);
            //RenderSystem.colorMask(true, true, true, true);

            RenderSystem.enableCull();
        }
    }
}