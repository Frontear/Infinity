package org.frontear.mixins;

import net.minecraft.client.gui.FontRenderer;
import net.minecraftforge.common.MinecraftForge;
import org.frontear.infinity.events.render.FontEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(FontRenderer.class) public abstract class MixinFontRenderer {
	/**
	 * @param renderer   The font renderer instance
	 * @param text       The text to render
	 * @param x          x-position of the text
	 * @param y          x-position of the text
	 * @param color      Color of the text
	 * @param dropShadow Whether the text should have a background shadow effect
	 *
	 * @return The x-coordinate of the text, if rendering was successful, else 0
	 *
	 * @author Frontear
	 * @see FontEvent
	 */
	@SuppressWarnings("UnresolvedMixinReference") @Redirect(method = "*",
			// there are only 2 references to the drawString target, as a result, we can target all using the wildcard. This is valid mixin, MinecraftDev plugin currently doesn't seem to think so
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/gui/FontRenderer;drawString(Ljava/lang/String;FFIZ)I")) private int drawString(FontRenderer renderer, String text, float x, float y, int color, boolean dropShadow) {
		FontEvent event = new FontEvent(text, x, y, color, dropShadow);
		MinecraftForge.EVENT_BUS.post(event);

		return renderer.drawString(event.getText(), event.getX(), event.getY(), event.getColor(), event.dropShadow());
	}
}