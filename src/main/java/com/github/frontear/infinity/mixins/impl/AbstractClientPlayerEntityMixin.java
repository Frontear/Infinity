package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.NoFOV;
import com.mojang.authlib.GameProfile;
import lombok.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.network.encryption.PlayerPublicKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractClientPlayerEntity.class)
abstract class AbstractClientPlayerEntityMixin extends PlayerEntity {
    /**
     * @author Frontear
     * @reason To comply with the constructor definition within {@link PlayerEntity}.
     */
    public AbstractClientPlayerEntityMixin(final World world, final BlockPos pos, final float yaw,
        final GameProfile profile, final PlayerPublicKey publicKey) {
        super(world, pos, yaw, profile, publicKey);
    }

    /**
     * @author Frontear
     * @reason Disable the effects of speed attributes which alter fov. This includes speed potions,
     * and is only active when {@link NoFOV} is active.
     */
    // todo: optimize
    @Inject(method = "getFovMultiplier", at = @At("RETURN"), cancellable = true)
    private void getFovMultiplier(@NonNull final CallbackInfoReturnable<Float> info) {
        val no_fov = InfinityLoader.getMod().getModules().get(NoFOV.class);

        if (no_fov.isActive()) {
            val speed = (float) ((double) info.getReturnValueF() / (
                (this.getAttributeValue(EntityAttributes.GENERIC_MOVEMENT_SPEED)
                    / (double) this.getAbilities().getWalkSpeed() + 1.0D)
                    / 2.0D));

            info.setReturnValue(speed * (this.isSprinting() ? 1.15f : 1.0f));
        }
    }
}
