package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.event.state.TickEvent;
import com.github.frontear.infinity.modules.impl.Ghost;
import lombok.*;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin {
    /**
     * @author Frontear
     * @reason Controlling the title for the sake of renaming Infinity
     */
    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "*", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/MinecraftClient;getWindowTitle()Ljava/lang/String;"))
    private String getWindowTitle(@NonNull final MinecraftClient client) {
        var title = "Minecraft " + SharedConstants.getGameVersion().getName();
        val infinity = InfinityLoader.getMod();

        if (infinity != null) {
            val ghost = infinity.getModules().get(Ghost.class).isActive();

            if (!ghost) {
                title = infinity.getMetadata().getFullName();
            }
        }

        return title;
    }

    /**
     * @author Frontear
     * @reason Injects the {@link TickEvent} for timed callbacks. Fires twice, once before tick() is
     * fired, and once after
     */
    @Redirect(method = "render",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;tick()V"))
    private void render(@NonNull final MinecraftClient client, final boolean tick) {
        val infinity = InfinityLoader.getMod();

        infinity.getExecutor().fire(new TickEvent(true));
        client.tick();
        infinity.getExecutor().fire(new TickEvent(false));
    }
}
