package org.frontear.infinity.modules.impl;

import com.google.common.collect.Queues;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.framework.utils.time.Timer;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

import java.util.Deque;

import static org.lwjgl.opengl.GL11.*;

@FieldDefaults(level = AccessLevel.PRIVATE,
		makeFinal = true) public final class Breadcrumbs extends Module {

	Deque<Vec3> positions = Queues.newArrayDeque();
	Timer timer = new Timer();

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
			val player = (EntityPlayerSP) event.getEntity();

			if (player.motionX != 0 || player.motionY != 0 || player.motionZ != 0) {
				val vector = player.getPositionVector();
				logger.debug("Adding player vector: $vector");
				positions.add(vector);
			}
		}
	}

	@SubscribeEvent public void onRender(RenderWorldLastEvent event) {
		val width = 3.5f;
		glPushAttrib(GL_CURRENT_BIT);
		glPushMatrix();
		{
			glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
			glColor4f(0.25f, 1f, 1f, 1f); // light blue
			glLineWidth(width); // makes it easier to spot

			glEnable(GL_BLEND);
			glEnable(GL_LINE_SMOOTH);
			glDisable(GL_TEXTURE_2D);

			glBegin(GL_LINE_STRIP);
			{
				// GL cannot work with lambdas due to how GLContext handles capabilities on threads
				logger.debug("Drawing ${positions.size()} positions");
				for (val pos : positions) {
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
		glPopAttrib();
	}
}
