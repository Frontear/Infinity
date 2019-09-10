package org.frontear.mixins;

import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.*;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.modules.impl.Ghost;
import org.spongepowered.asm.mixin.*;

@Mixin(ItemRenderer.class) public abstract class MixinItemRenderer {
	@Shadow private float prevEquippedProgress;
	@Shadow private float equippedProgress;
	@Shadow @Final private Minecraft mc;
	@Shadow private ItemStack itemToRender;

	/**
	 * @param partialTicks The degree of how far you've gone into one tick (as a decimal percentage)
	 *
	 * @author Frontear
	 * @reason Allow 1.7 blockhit animations back into the game
	 */
	@Overwrite public void renderItemInFirstPerson(float partialTicks) {
		float f = 1.0F - (this.prevEquippedProgress + (this.equippedProgress - this.prevEquippedProgress) * partialTicks);
		EntityPlayerSP abstractclientplayer = this.mc.thePlayer;
		float f1 = abstractclientplayer.getSwingProgress(partialTicks);
		float f2 = abstractclientplayer.prevRotationPitch + (abstractclientplayer.rotationPitch - abstractclientplayer.prevRotationPitch) * partialTicks;
		float f3 = abstractclientplayer.prevRotationYaw + (abstractclientplayer.rotationYaw - abstractclientplayer.prevRotationYaw) * partialTicks;
		this.rotateArroundXAndY(f2, f3);
		this.setLightMapFromPlayer(abstractclientplayer);
		this.rotateWithPlayerRotations(abstractclientplayer, partialTicks);
		GlStateManager.enableRescaleNormal();
		GlStateManager.pushMatrix();

		if (this.itemToRender != null) {
			if (this.itemToRender.getItem() instanceof ItemMap) {
				this.renderItemMap(abstractclientplayer, f2, f, f1);
			}
			else if (abstractclientplayer.getItemInUseCount() > 0) {
				EnumAction enumaction = this.itemToRender.getItemUseAction();

				final float progress = !Infinity.inst().getModules().get(Ghost.class).isActive() ? f1 : 0.0F;

				switch (enumaction) {
					case EAT:
					case DRINK:
						this.performDrinking(abstractclientplayer, partialTicks);
						this.transformFirstPersonItem(f, progress);
						break;
					case BLOCK:
						this.transformFirstPersonItem(f, progress);
						this.doBlockTransformations();
						break;
					case BOW:
						this.transformFirstPersonItem(f, progress);
						this.doBowTransformations(partialTicks, abstractclientplayer);
						break;
					default:
						this.transformFirstPersonItem(f, 0.0F);
				}
			}
			else {
				this.doItemUsedTransformations(f1);
				this.transformFirstPersonItem(f, f1);
			}

			//noinspection deprecation
			this.renderItem(abstractclientplayer, this.itemToRender, ItemCameraTransforms.TransformType.FIRST_PERSON);
		}
		else if (!abstractclientplayer.isInvisible()) {
			this.renderPlayerArm(abstractclientplayer, f, f1);
		}

		GlStateManager.popMatrix();
		GlStateManager.disableRescaleNormal();
		RenderHelper.disableStandardItemLighting();
	}

	@Shadow protected abstract void rotateArroundXAndY(float angle, float angleY);

	@Shadow protected abstract void setLightMapFromPlayer(AbstractClientPlayer clientPlayer);

	@Shadow protected abstract void rotateWithPlayerRotations(EntityPlayerSP entityplayerspIn, float partialTicks);

	@Shadow protected abstract void renderItemMap(AbstractClientPlayer clientPlayer, float pitch, float equipmentProgress, float swingProgress);

	@Shadow protected abstract void performDrinking(AbstractClientPlayer clientPlayer, float partialTicks);

	@Shadow protected abstract void transformFirstPersonItem(float equipProgress, float swingProgress);

	@Shadow protected abstract void doBlockTransformations();

	@Shadow protected abstract void doBowTransformations(float partialTicks, AbstractClientPlayer clientPlayer);

	@Shadow protected abstract void doItemUsedTransformations(float swingProgress);

	@Shadow public abstract void renderItem(EntityLivingBase entityIn, ItemStack heldStack, ItemCameraTransforms.TransformType transform);

	@Shadow protected abstract void renderPlayerArm(AbstractClientPlayer clientPlayer, float equipProgress, float swingProgress);
}
