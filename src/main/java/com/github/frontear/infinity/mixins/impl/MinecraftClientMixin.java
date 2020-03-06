package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.infinity.InfinityMod;
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
        val infinity = InfinityMod.getInstance();

        if (infinity != null) {
            val ghost = infinity.getModules().get(Ghost.class).isActive();

            if (!ghost) {
                title = InfinityMod.getInstance().getMetadata().getFullName();
            }
        }

        return title;
    }
}
