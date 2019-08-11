package org.frontear.mixins;

import net.minecraft.client.gui.GuiOptions;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.EntityRenderer;
import org.frontear.infinity.ui.UITestScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(GuiOptions.class) public abstract class MixinGuiOptions extends GuiScreen {
	@Redirect(method = "actionPerformed",
			at = @At(value = "INVOKE",
					target = "Lnet/minecraft/client/renderer/EntityRenderer;activateNextShader()V")) private void actionPerformed(EntityRenderer renderer) {
		this.mc.gameSettings.saveOptions();
		this.mc.displayGuiScreen(new UITestScreen(this));
	}
}
