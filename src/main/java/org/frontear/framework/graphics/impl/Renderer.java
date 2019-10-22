package org.frontear.framework.graphics.impl;

import static org.lwjgl.opengl.GL11.GL_BLEND;
import static org.lwjgl.opengl.GL11.GL_CULL_FACE;
import static org.lwjgl.opengl.GL11.GL_CURRENT_BIT;
import static org.lwjgl.opengl.GL11.GL_ONE_MINUS_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_SRC_ALPHA;
import static org.lwjgl.opengl.GL11.GL_TEXTURE_2D;
import static org.lwjgl.opengl.GL11.glBlendFunc;
import static org.lwjgl.opengl.GL11.glColor4f;
import static org.lwjgl.opengl.GL11.glDisable;
import static org.lwjgl.opengl.GL11.glEnable;
import static org.lwjgl.opengl.GL11.glIsEnabled;
import static org.lwjgl.opengl.GL11.glPopAttrib;
import static org.lwjgl.opengl.GL11.glPopMatrix;
import static org.lwjgl.opengl.GL11.glPushAttrib;
import static org.lwjgl.opengl.GL11.glPushMatrix;

import com.google.common.base.Preconditions;
import java.awt.Color;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.graphics.IRenderer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Renderer implements IRenderer {
    @Getter boolean active;
    boolean blend, tex_2d, cull_face;

    @Override
    public void begin(@NonNull final Color color) throws IllegalArgumentException {
        Preconditions.checkArgument(!active);

        glPushAttrib(GL_CURRENT_BIT);
        glPushMatrix();
        {
            blend = !glIsEnabled(GL_BLEND);
            tex_2d = glIsEnabled(GL_TEXTURE_2D);
            cull_face = glIsEnabled(GL_CULL_FACE);

            if (blend) {
                glEnable(GL_BLEND);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }
            if (tex_2d) {
                glDisable(GL_TEXTURE_2D);
            }
            if (cull_face) {
                glDisable(GL_CULL_FACE);
            }

            glColor4f(color.getRed() / 255.0f, color.getGreen() / 255.0f, color.getBlue() / 255.0f,
                color.getAlpha() / 255.0f);
        }

        this.active = true;
    }

    @Override
    public void end() throws IllegalArgumentException {
        Preconditions.checkArgument(active);

        {
            if (blend) {
                glDisable(GL_BLEND);
            }
            if (tex_2d) {
                glEnable(GL_TEXTURE_2D);
            }
            if (cull_face) {
                glEnable(GL_CULL_FACE);
            }
        }
        glPopMatrix();
        glPopAttrib();

        this.active = false;
    }
}
