package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.infinity.event.render.OverlayEvent;
import com.github.frontear.infinity.mixins.IMinecraftClient;
import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
abstract class InGameHudMixin {
    @Shadow @Final private MinecraftClient client;

    /**
     * @author Frontear
     * @reason Injects the {@link OverlayEvent} for rendering callbacks. This will not fire if the
     * debugging visual is visible.
     */
    @Inject(method = "render", at = @At(value = "INVOKE",
        target = "Lcom/mojang/blaze3d/systems/RenderSystem;color4f(FFFF)V", ordinal = 3))
    private void render(final float tickDelta, @NonNull final CallbackInfo info) {
        if (!client.options.debugEnabled) {
            IMinecraftClient.getInfinity().getExecutor().fire(new OverlayEvent(tickDelta));
        }
    }
}
