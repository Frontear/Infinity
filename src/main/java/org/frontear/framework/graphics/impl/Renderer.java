package org.frontear.framework.graphics.impl;

import static org.lwjgl.opengl.GL11.*;

import com.google.common.base.Preconditions;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.frontear.framework.graphics.IRenderer;

@FieldDefaults(level = AccessLevel.PRIVATE)
public final class Renderer implements IRenderer {
    @Getter boolean active;
    boolean blend, tex_2d, cull_face;

    @Override
    public void begin() throws IllegalArgumentException {
        Preconditions.checkArgument(!active, "Renderer is already active [can't begin]");

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
        }

        this.active = true;
    }

    @Override
    public void escapeContext(final @NonNull Runnable render) {
        this.end();
        {
            render.run();
        }
        this.begin();
    }

    @Override
    public void end() throws IllegalArgumentException {
        Preconditions.checkArgument(active, "Render is already inactive [cant end]");

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
