package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.*;
import com.mojang.authlib.GameProfile;
import lombok.*;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(ClientPlayerEntity.class)
abstract class ClientPlayerEntityMixin extends PlayerEntity {
    /**
     * @author Frontear
     * @reason To comply with the constructor definition within {@link PlayerEntity}.
     */
    public ClientPlayerEntityMixin(final World world, final BlockPos pos, final float yaw,
        final GameProfile profile) {
        super(world, pos, yaw, profile);
    }

    /**
     * @author Frontear
     * @reason Force sprinting if {@link Sprint} is active.
     */
    @Redirect(method = "tickMovement", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/option/KeyBinding;isPressed()Z"))
    private boolean tickMovement(@NonNull final KeyBinding instance) {
        val sprint = InfinityLoader.getMod().getModules().get(Sprint.class);

        return sprint.isActive() || instance.isPressed();
    }

    /**
     * @author Frontear
     * @reason Force user to remain on the edge of the block if {@link SafeWalk} is active.
     */
    @Override
    protected boolean clipAtLedge() {
        val safe_walk = InfinityLoader.getMod().getModules().get(SafeWalk.class);

        return safe_walk.isActive() || super.clipAtLedge();
    }
}
