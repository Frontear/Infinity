package com.github.frontear.mixins.impl;

import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.modules.impl.HealthTag;
import lombok.NonNull;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.entity.*;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.*;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.impl.HealthTag;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.*;

@Mixin(Render.class)
public abstract class MixinRender {
    @Shadow @Final protected RenderManager renderManager;

    @Shadow
    public abstract FontRenderer getFontRendererFromRenderManager();

    /**
     * @author Frontear
     * @reason To display Health Tags
     */
    @Overwrite
    protected <T extends Entity> void renderLivingLabel(
        @NonNull final T entityIn, @NonNull String str, final double x, final double y,
        final double z,
        final int maxDistance) {
        double d0 = entityIn.getDistanceSqToEntity(this.renderManager.livingPlayer);

        if (entityIn instanceof EntityLivingBase && Infinity.inst().getModules()
            .get(HealthTag.class).isActive()) {
            str += " HP: " + (int) (((EntityLivingBase) entityIn).getHealth() + 0.5f);
        }

        if (d0 <= (double) (maxDistance * maxDistance)) {
            FontRenderer fontrenderer = this.getFontRendererFromRenderManager();
            float f = 1.6F;
            float f1 = 0.016666668F * f;
            GlStateManager.pushMatrix();
            GlStateManager
                .translate((float) x + 0.0F, (float) y + entityIn.height + 0.5F, (float) z);
            GL11.glNormal3f(0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
            GlStateManager.rotate(this.renderManager.playerViewX, 1.0F, 0.0F, 0.0F);
            GlStateManager.scale(-f1, -f1, f1);
            GlStateManager.disableLighting();
            GlStateManager.depthMask(false);
            GlStateManager.disableDepth();
            GlStateManager.enableBlend();
            GlStateManager.tryBlendFuncSeparate(770, 771, 1, 0);
            Tessellator tessellator = Tessellator.getInstance();
            WorldRenderer worldrenderer = tessellator.getWorldRenderer();
            int i = 0;

            if (str.equals("deadmau5")) {
                i = -10;
            }

            int j = fontrenderer.getStringWidth(str) / 2;
            GlStateManager.disableTexture2D();
            worldrenderer.begin(7, DefaultVertexFormats.POSITION_COLOR);
            worldrenderer.pos((double) (-j - 1), (double) (-1 + i), 0.0D)
                .color(0.0F, 0.0F, 0.0F, 0.25F)
                .endVertex();
            worldrenderer.pos((double) (-j - 1), (double) (8 + i), 0.0D)
                .color(0.0F, 0.0F, 0.0F, 0.25F)
                .endVertex();
            worldrenderer.pos((double) (j + 1), (double) (8 + i), 0.0D)
                .color(0.0F, 0.0F, 0.0F, 0.25F)
                .endVertex();
            worldrenderer.pos((double) (j + 1), (double) (-1 + i), 0.0D)
                .color(0.0F, 0.0F, 0.0F, 0.25F)
                .endVertex();
            tessellator.draw();
            GlStateManager.enableTexture2D();
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, 553648127);
            GlStateManager.enableDepth();
            GlStateManager.depthMask(true);
            fontrenderer.drawString(str, -fontrenderer.getStringWidth(str) / 2, i, -1);
            GlStateManager.enableLighting();
            GlStateManager.disableBlend();
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.popMatrix();
        }
    }
}
