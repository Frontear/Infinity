package io.github.frontear.infinity.mixins.tweaks.esp;

import com.mojang.blaze3d.platform.GlStateManager;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.DefaultVertexFormat;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import com.mojang.blaze3d.vertex.VertexFormat;
import com.mojang.math.Matrix4f;
import com.mojang.math.Vector3f;
import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.ESP;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.LightTexture;
import net.minecraft.client.renderer.entity.EntityRenderDispatcher;
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

    // FIXME: water/ice and possibly other textures render above the lines, causing them to dim
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

                var pos = entity.getPosition(partialTick);

                var bound = entity.getType().getAABB(pos.x(), pos.y(), pos.z());
                var color = esp.getColor(entity);

                LevelRenderer.renderLineBox(poseStack, buffer, bound, color[0], color[1], color[2], color[3]);

                if (esp.shouldDrawTracers() && minecraft.player != null) {
                    var center = bound.getCenter();
                    var player_pos = minecraft.player.getPosition(partialTick);

                    var x1 = (float) player_pos.x() + camera.getLookVector().x();
                    var y1 = (float) player_pos.y() + camera.getLookVector().y() + minecraft.player.getEyeHeight();
                    var z1 = (float) player_pos.z() + camera.getLookVector().z();
                    var x2 = (float) center.x();
                    var y2 = (float) center.y();
                    var z2 = (float) center.z();

                    // FIXME: why does this work? have tried 0.0f, 1.0f, all create lines that flicker at specific angles.
                    var normal = new Vector3f(x2 - x1, y2 - y1, z2 - z1);
                    if (normal.normalize()) {
                        buffer.vertex(poseStack.last().pose(), x1, y1, z1).color(color[0], color[1], color[2], color[3]).normal(poseStack.last().normal(), normal.x(), normal.y(), normal.z()).endVertex();
                        buffer.vertex(poseStack.last().pose(), x2, y2, z2).color(color[0], color[1], color[2], color[3]).normal(poseStack.last().normal(), normal.x(), normal.y(), normal.z()).endVertex();
                    }
                }
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