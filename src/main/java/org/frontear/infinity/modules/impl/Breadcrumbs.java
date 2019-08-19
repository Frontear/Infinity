package org.frontear.infinity.modules.impl;

import com.google.common.collect.Queues;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.utils.Timer;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Deque;

import static org.lwjgl.opengl.GL11.*;

public class Breadcrumbs extends Module {
	private final Deque<Vec3> positions = Queues.newArrayDeque();
	private final Timer timer = new Timer();

	public Breadcrumbs() {
		super(Keyboard.KEY_J, false, Category.RENDER);
	}

	@Override protected void onToggle(boolean active) {
		if (!active) {
			positions.clear();
		}
	}

	@SubscribeEvent public void onUpdate(UpdateEvent event) {
		if (event.getEntity() instanceof EntityPlayerSP && event.isPost()) {
			EntityPlayerSP player = (EntityPlayerSP) event.getEntity();

			if (player.motionX != 0 || player.motionY != 0 || player.motionZ != 0) {
				positions.add(player.getPositionVector());
			}
		}
	}

	@SubscribeEvent public void onRender(RenderWorldLastEvent event) {
		final float width = 3.5f;
		glPushMatrix();
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glColor4f(0.25f, 1f, 1f, 1f); // light blue
			glLineWidth(3.5f); // makes it easier to spot

			glEnable(GL_BLEND);
			glEnable(GL_LINE_SMOOTH);
			glDisable(GL_TEXTURE_2D);

			glBegin(GL_LINE_STRIP);
			{
				// GL cannot work with lambdas due to how GLContext handles capabilities on threads
				for (Vec3 pos : positions) {
					glVertex3d(pos.xCoord - mc
							.getRenderManager().renderPosX, (pos.yCoord + (width / 200f)) - mc // raise line above the ground, so that half of it isn't inside a block
							.getRenderManager().renderPosY, pos.zCoord - mc.getRenderManager().renderPosZ);
				}
			}
			glEnd();

			glEnable(GL_TEXTURE_2D);
			glDisable(GL_LINE_SMOOTH);
			glDisable(GL_BLEND);
		}
		glPopMatrix();
	}
}
