package org.frontear.infinity.modules.impl;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CURRENT_BIT;
import static org.lwjgl.opengl.GL11.GL_LINE_SMOOTH;
import static org.lwjgl.opengl.GL11.GL_LINE_STRIP;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBegin;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glEnd;
import static org.lwjgl.opengl.GL11.glLineWidth;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;
import static org.lwjgl.opengl.GL11.glVertex3d;

import com.google.common.collect.Queues;
import java.util.Deque;
import lombok.AccessLevel;
import lombok.experimental.FieldDefaults;
import lombok.val;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.events.entity.UpdateEvent;
import org.frontear.infinity.modules.Category;
import org.frontear.infinity.modules.Module;
import org.lwjgl.input.Keyboard;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public final class Breadcrumbs extends Module {
    Deque<Vec3> positions = Queues.newArrayDeque();

    public Breadcrumbs() {
        super(Keyboard.KEY_J, false, Category.RENDER);
    }

    @Override
    protected void onToggle(final boolean active) {
        if (!active) {
            positions.clear();
        }
    }

    @SubscribeEvent
    public void onUpdate(final UpdateEvent event) {
        if (event.getEntity() instanceof EntityPlayerSP && event.isPost()) {
            val player = (EntityPlayerSP) event.getEntity();

            if (player.motionX != 0 || Math
                .max(0, player.motionY) != 0 || player.motionZ
                != 0) { // motionY is never 0, because the game treats gravity as a continuous downward force, which it is
                val vector = player.getPositionVector();
                logger.debug("Adding player vector: $vector");
                positions.add(vector);
            }
        }
    }

    @SubscribeEvent
    public void onRender(final RenderWorldLastEvent event) {
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
                for (val pos : positions) {
                    glVertex3d(pos.xCoord - mc
                            .getRenderManager().renderPosX, (pos.yCoord + (width / 200f))
                            - mc // raise line above the ground, so that half of it isn't inside a block
                            .getRenderManager().renderPosY,
                        pos.zCoord - mc.getRenderManager().renderPosZ);
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
