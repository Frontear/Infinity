package org.frontear.mixins;

import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.Potion;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.impl.Ghost;
import org.lwjgl.opengl.GL11;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityRenderer.class) public abstract class MixinEntityRenderer {
	/**
	 * @param angle The angle to turn the screen
	 * @param x     The x-coordinate to rotate from
	 * @param y     The y-coordinate to rotate from
	 * @param z     The z-coordinate to rotate from
	 *
	 * @author Frontear
	 * @reason Reduce screen rotate effect by 3x, will return to normal if {@link Ghost#active()}
	 */
	@Redirect(method = "hurtCameraEffect",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/GlStateManager;rotate(FFFF)V")) private void rotate(float angle, float x, float y, float z) {
		GL11.glRotatef(angle / (!Infinity.inst().getModules().get(Ghost.class).isActive() ? 3f : 1f), x, y, z);
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
					ordinal = 0)) private boolean isPotionActive(EntityLivingBase entity, Potion potion) {
		return Infinity.inst().getModules().get(Ghost.class).isActive() && entity.isPotionActive(potion); // todo: remove sky blacking
	}
}
