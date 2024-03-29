package io.github.frontear.infinity.mixins.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import io.github.frontear.infinity.gui.InfinityScreenOverlay;
import net.minecraft.client.gui.Gui;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Gui.class)
abstract class GuiMixin {
    @Inject(method = "render", at = @At("TAIL"))
    private void renderEnabledTweaks(PoseStack poseStack, float partialTick, CallbackInfo info) {
        InfinityScreenOverlay.render(poseStack);
    }
}
