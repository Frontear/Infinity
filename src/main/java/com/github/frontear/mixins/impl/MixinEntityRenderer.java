package com.github.frontear.mixins.impl;

import com.github.frontear.infinity.Infinity;
import com.github.frontear.infinity.events.render.OverlayEvent;
import com.github.frontear.infinity.modules.impl.*;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiIngame;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.settings.GameSettings;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.lib.Opcodes;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(EntityRenderer.class)
public abstract class MixinEntityRenderer {
    @Shadow private Minecraft mc;

    /**
     * @param angle The angle to turn the screen
     * @param x     The x-coordinate to rotate from
     * @param y     The y-coordinate to rotate from
     * @param z     The z-coordinate to rotate from
     *
     * @author Frontear
     * @reason Reduce screen rotate effect by 3x, will return to normal if {@link Ghost#isActive()}
     */
    @Redirect(method = "hurtCameraEffect",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V"))
    private void rotate(final float angle, final float x, final float y, final float z) {
        GL11.glRotatef(
            angle / (!Infinity.inst().getModules().get(Ghost.class).isActive() ? 3f : 1f), x, y, z);
    }

    /**
     * @param entity The entity which may be blinded
     * @param potion The potion which causes fog/blindness
     *
     * @return If the blindness fog should be rendered
     *
     * @author Frontear
     * @reason Remove blindness fog, will return to normal if {@link Ghost#isActive()}
     */
    @Redirect(method = "setupFog",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z",
            ordinal = 0))
    private boolean isPotionActive(final EntityLivingBase entity, final Potion potion) {
        return Infinity.inst().getModules().get(Ghost.class).isActive() && entity
            .isPotionActive(potion); // todo: remove sky blacking
    }

    /**
     * @param ingame       The instance of {@link net.minecraftforge.client.GuiIngameForge}
     * @param partialTicks The percentage of how far we have gone within a single tick
     *
     * @reason Handle the rendering on the ingame gui without forge interference
     */
    @Redirect(method = "updateCameraAndRender",
        at = @At(value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiIngame;renderGameOverlay(F)V"))
    private void renderGameOverlay(final GuiIngame ingame, final float partialTicks) {
        ingame.renderGameOverlay(partialTicks);
        MinecraftForge.EVENT_BUS
            .post(new OverlayEvent(partialTicks, mc.gameSettings.showDebugInfo));
    }

    /**
     * @param settings The instance of {@link GameSettings}
     *
     * @return 100f if {@link Fullbright#isActive()}, otherwise {@link GameSettings#gammaSetting}
     *
     * @reason Sets the lightmap to a very high value, preventing any form of darkness to exist in
     * the rendered world
     */
    @Redirect(method = "updateLightmap",
        at = @At(value = "FIELD",
            opcode = Opcodes.GETFIELD,
            target = "Lnet/minecraft/client/settings/GameSettings;gammaSetting:F"))
    private float updateLightmap(final GameSettings settings) {
        return Infinity.inst().getModules().get(Fullbright.class).isActive() ? 10f
            : settings.gammaSetting;
    }
}
