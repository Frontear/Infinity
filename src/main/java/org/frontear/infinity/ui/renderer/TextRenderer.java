package org.frontear.infinity.ui.renderer;

import static org.lwjgl.opengl.GL11.glScalef;

import com.google.common.base.Preconditions;
import java.awt.Color;
import lombok.AccessLevel;
import lombok.NonNull;
import lombok.experimental.FieldDefaults;
import lombok.experimental.NonFinal;
import lombok.val;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.events.render.OverlayEvent;
import org.frontear.infinity.modules.impl.Ghost;

@FieldDefaults(level = AccessLevel.PRIVATE,
    makeFinal = true)
public class TextRenderer {
    int offset = 2;
    @NonFinal int y_left = 0;
    @NonFinal int y_right = 0;

    public void render(final byte position, @NonNull final String text, @NonNull final Color color,
        final boolean shadow, final float scale) {
        Preconditions
            .checkArgument(position == TextPositions.LEFT || position == TextPositions.RIGHT);
        Preconditions.checkArgument(scale > 0, "Scale cannot be less than 0 [is %f]", scale);

        if (!Infinity.inst().getModules().get(Ghost.class).isActive() && !Minecraft
            .getMinecraft().gameSettings.showDebugInfo) {
            val renderer = Minecraft.getMinecraft().fontRendererObj;
            val x = position == TextPositions.RIGHT ? new ScaledResolution(Minecraft.getMinecraft())
                .getScaledWidth() - renderer.getStringWidth(text) - offset : offset;
            glScalef(scale, scale, 1);
            {
                renderer.drawString(text, x / scale,
                    ((position == TextPositions.RIGHT ? y_right : y_left) + offset) / scale, color
                        .getRGB(), shadow);
                if (position == TextPositions.RIGHT) {
                    y_right += (renderer.FONT_HEIGHT + offset) * scale;
                }
                else {
                    y_left += (renderer.FONT_HEIGHT + offset) * scale;
                }
            }
            glScalef(1 / scale, 1 / scale, 1);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onOverlay(final OverlayEvent event) {
        y_right = y_left = 0; // reset for the next tick and it's drawings
    }
}
