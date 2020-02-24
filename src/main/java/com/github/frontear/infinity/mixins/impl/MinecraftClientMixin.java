package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.internal.NotNull;
import lombok.NonNull;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(MinecraftClient.class)
@SuppressWarnings("UnresolvedMixinReference")
public abstract class MinecraftClientMixin {
    /**
     * @author Frontear
     * @reason This functionality is largely useless from my perspective. Additionally, since
     * Infinity is to change the display name, I can't have it change over and over again, hence why
     * it must go.. Furthermore, it's actually performance impacting, as it creates unnecessary
     * {@link StringBuilder} instances.
     */
    @Redirect(method = "*", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/MinecraftClient;updateWindowTitle()V"))
    private void updateWindowTitle(@NonNull final MinecraftClient client) {
    }

    /**
     * @author Frontear
     * @reason Changing the window title to remain as it originally used to be formatted in previous
     * versions. This is largely for my own sanity, as Infinity is meant to change the display name
     * anyways.
     */
    @Redirect(method = "<init>", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/MinecraftClient;getWindowTitle()Ljava/lang/String;"))
    private String getWindowTitle(@NonNull final MinecraftClient client) {
        return "Minecraft " + SharedConstants.getGameVersion().getName();
    }
}
