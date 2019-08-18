package org.frontear.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import org.frontear.infinity.modules.impl.Ghost;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class) public class MixinEntityRenderer {
	@Redirect(method = "hurtCameraEffect",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V")) private void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle / (!Ghost.active() ? 3f : 1f), x, y, z);
	}

	@Redirect(method = "setupFog",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/entity/EntityLivingBase;isPotionActive(Lnet/minecraft/potion/Potion;)Z",
					ordinal = 0)) private boolean isPotionActive(EntityLivingBase entity, Potion potion) {
		return Ghost.active() && entity.isPotionActive(potion);
	}
}
