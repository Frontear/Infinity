package org.frontear.infinity.modules.impl;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.awt.*;
import java.util.stream.IntStream;

import static org.lwjgl.opengl.GL11.*;

public class ESP extends Module {
	public ESP() {
		super(Keyboard.KEY_H, false, Category.RENDER);
	}

	@SubscribeEvent public void onRender(RenderWorldLastEvent event) {
		for (Entity entity : mc.getWorld().getLoadedEntityList()) {
			final Color color = entity instanceof EntityPlayer ? Color.WHITE : entity instanceof EntityAnimal ? Color.YELLOW : null;
			if (color != null) {
				this.renderESP(entity, color, event.partialTicks);
			}
		}
	}

	private void renderESP(Entity entity, Color color, float partialTicks) {
		glPushMatrix();
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glColor4f(color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
			glLineWidth(2f);

			glEnable(GL_BLEND);
			glEnable(GL_LINE_SMOOTH);
			glDisable(GL_TEXTURE_2D);
			glDisable(GL_DEPTH_TEST);

			glBegin(GL_LINES);
			{
				// normalize using partial ticks
				double x = (entity.lastTickPosX + (entity.posX - entity.lastTickPosX) * partialTicks) - mc
						.getRenderManager().renderPosX;
				double y = (entity.lastTickPosY + (entity.posY - entity.lastTickPosY) * partialTicks) - mc
						.getRenderManager().renderPosY;
				double z = (entity.lastTickPosZ + (entity.posZ - entity.lastTickPosZ) * partialTicks) - mc
						.getRenderManager().renderPosZ;

				drawBox(AxisAlignedBB.fromBounds(x - entity.width / 2, y, z - entity.width / 2, x + entity.width / 2, y + entity.height, z + entity.width / 2)); // this sanitizes the bounds
			}
			glEnd();

			glEnable(GL_DEPTH_TEST);
			glEnable(GL_TEXTURE_2D);
			glDisable(GL_LINE_SMOOTH);
			glDisable(GL_BLEND);
		}
		glPopMatrix();
	}

	private void drawBox(AxisAlignedBB bound) {
		glVertex3d(bound.minX, bound.minY, bound.minZ);
		glVertex3d(bound.maxX, bound.minY, bound.minZ);

		glVertex3d(bound.maxX, bound.minY, bound.minZ);
		glVertex3d(bound.maxX, bound.minY, bound.maxZ);

		glVertex3d(bound.maxX, bound.minY, bound.maxZ);
		glVertex3d(bound.minX, bound.minY, bound.maxZ);

		glVertex3d(bound.minX, bound.minY, bound.maxZ);
		glVertex3d(bound.minX, bound.minY, bound.minZ);

		glVertex3d(bound.minX, bound.minY, bound.minZ);
		glVertex3d(bound.minX, bound.maxY, bound.minZ);

		glVertex3d(bound.maxX, bound.minY, bound.minZ);
		glVertex3d(bound.maxX, bound.maxY, bound.minZ);

		glVertex3d(bound.maxX, bound.minY, bound.maxZ);
		glVertex3d(bound.maxX, bound.maxY, bound.maxZ);

		glVertex3d(bound.minX, bound.minY, bound.maxZ);
		glVertex3d(bound.minX, bound.maxY, bound.maxZ);

		glVertex3d(bound.minX, bound.maxY, bound.minZ);
		glVertex3d(bound.maxX, bound.maxY, bound.minZ);

		glVertex3d(bound.maxX, bound.maxY, bound.minZ);
		glVertex3d(bound.maxX, bound.maxY, bound.maxZ);

		glVertex3d(bound.maxX, bound.maxY, bound.maxZ);
		glVertex3d(bound.minX, bound.maxY, bound.maxZ);

		glVertex3d(bound.minX, bound.maxY, bound.maxZ);
		glVertex3d(bound.minX, bound.maxY, bound.minZ);
	}
}
