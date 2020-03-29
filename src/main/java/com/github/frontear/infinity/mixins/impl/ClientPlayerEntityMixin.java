package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.Sprint;
import lombok.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.options.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin {
    /**
     * @author Frontear
     * @reason Force sprinting if {@link Sprint} is active.
     */
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/options/KeyBinding;isPressed()Z"))
    private boolean tickMovement(@NonNull final KeyBinding instance) {
        val sprint = InfinityLoader.getMod().getModules().get(Sprint.class);

        return sprint.isActive() || instance.isPressed();
    }
}
