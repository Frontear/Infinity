package org.frontear.infinity.modules.impl;

import lombok.val;
import manifold.ext.api.Jailbreak;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;

import static org.lwjgl.opengl.GL11.*;

public final class ESP extends Module {
	public ESP() {
		super(Keyboard.KEY_H, false, Category.RENDER);
	}

	@SubscribeEvent public void onRender(RenderWorldLastEvent event) {
		mc.theWorld.getLoadedEntityList().stream().filter(x -> !x.equals(mc.thePlayer)).forEach(x -> {
			//noinspection RedundantCast
			val color = x instanceof EntityPlayer ? Color.WHITE : x instanceof EntityLivingBase && x
					.isInvisible() ? Color.PINK : x instanceof EntityAnimal ? Color.YELLOW : x instanceof EntityMob ? Color.RED : (Color) null;
			if (color != null) {
				this.renderESP(x, color, event.partialTicks);
			}
		});
	}

	private void renderESP(Entity entity, Color color, float partialTicks) {
		glPushAttrib(GL_CURRENT_BIT);
		glPushMatrix();
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glLineWidth(2f);

			glEnable(GL_BLEND);
			glEnable(GL_LINE_SMOOTH);
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_DEPTH_TEST);
			{
				// normalize using partial ticks
				@Jailbreak RenderManager renderManager = mc.getRenderManager();
				val x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks) - renderManager.renderPosX;
				val y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) - renderManager.renderPosY;
				val z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks) - renderManager.renderPosZ;

				RenderGlobal.drawOutlinedBoundingBox(AxisAlignedBB
						.fromBounds(x - entity.width / 2, y, z - entity.width / 2, x + entity.width / 2, y + entity.height, z + entity.width / 2), color
						.getRed(), color.getGreen(), color.getBlue(), color.getAlpha());
			}
			glEnable(GL_DEPTH_TEST);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_LINE_SMOOTH);
			glDisable(GL_BLEND);
		}
		glPopMatrix();
		glPopAttrib();
	}
}
