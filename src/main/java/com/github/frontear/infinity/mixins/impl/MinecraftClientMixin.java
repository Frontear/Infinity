package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.mixins.IMinecraftClient;
import com.github.frontear.infinity.modules.impl.Ghost;
import lombok.*;
import net.minecraft.SharedConstants;
import net.minecraft.client.MinecraftClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(MinecraftClient.class)
abstract class MinecraftClientMixin implements IMinecraftClient {
    private InfinityMod infinity;

    @Override
    public InfinityMod getInfinityInstance() {
        return infinity;
    }

    @Override
    public void setInfinityInstance(@NonNull final InfinityMod infinity) {
        if (this.infinity == null) {
            this.infinity = infinity;
        }
    }

    /**
     * @author Frontear
     * @reason Controlling the title for the sake of renaming Infinity
     */
    @SuppressWarnings("UnresolvedMixinReference")
    @Redirect(method = "*", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/MinecraftClient;getWindowTitle()Ljava/lang/String;"))
    private String getWindowTitle(@NonNull final MinecraftClient client) {
        var title = "Minecraft " + SharedConstants.getGameVersion().getName();

        if (infinity != null) {
            val ghost = infinity.getModules().get(Ghost.class).isActive();

            if (!ghost) {
                title = infinity.getMetadata().getFullName();
            }
        }

        return title;
    }
}
