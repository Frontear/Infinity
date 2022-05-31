package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.event.render.OverlayEvent;
import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.util.math.MatrixStack;
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
        target = "Lnet/minecraft/client/gui/hud/InGameHud;renderStatusEffectOverlay(Lnet/minecraft/client/util/math/MatrixStack;)V"))
    private void render(final MatrixStack matrices, final float tickDelta,
        @NonNull final CallbackInfo info) {
        if (!client.options.debugEnabled) {
            InfinityLoader.getMod().getExecutor().fire(new OverlayEvent(matrices, tickDelta));
        }
    }
}
