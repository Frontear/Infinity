package com.github.frontear.mixins.impl;

import com.github.frontear.infinity.events.render.FontEvent;
import lombok.val;
import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.spongepowered.asm.mixin.*;

@Mixin(FontRenderer.class)
public abstract class MixinFontRenderer {
    /**
     * @param text       The text which will be rendered
     * @param x          The x-coordinate of the text, measured from the left
     * @param y          The y-position of the text, measured from the top
     * @param color      The color of the text, which will be applied via {@link
     *                   org.lwjgl.opengl.GL11#glColor4f(float, float, float, float)}
     * @param dropShadow If the text should have a dark back shadow. This makes the text pop out
     *                   more
     *
     * @return The x-coordinate of the drawn text
     *
     * @author Frontear
     * @reason {@link FontEvent}
     */
    @Overwrite
    public int drawString(String text, float x, float y, int color, boolean dropShadow) {
        enableAlpha();
        this.resetStyles();
        int i;

        val event = new FontEvent(text, x, y, color, dropShadow);
        MinecraftForge.EVENT_BUS.post(event);
        {
            text = event.getText();
            x = event.getX();
            y = event.getY();
            color = event.getColor();
            dropShadow = event.isDropShadow();
        }

        if (dropShadow) {
            i = this.renderString(text, x + 1.0F, y + 1.0F, color, true);
            i = Math.max(i, this.renderString(text, x, y, color, false));
        }
        else {
            i = this.renderString(text, x, y, color, false);
        }

        return i;
    }

    @Shadow
    protected abstract void enableAlpha();

    @Shadow
    protected abstract void resetStyles();

    @Shadow
    protected abstract int renderString(String text, float x, float y, int color,
        boolean dropShadow);
}
