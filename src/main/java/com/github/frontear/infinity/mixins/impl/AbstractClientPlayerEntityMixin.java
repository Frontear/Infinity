package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.modules.impl.NoFOV;
import com.mojang.authlib.GameProfile;
import lombok.*;
import net.minecraft.client.network.AbstractClientPlayerEntity;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.player.PlayerEntity;
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
    public AbstractClientPlayerEntityMixin(final World world, final GameProfile profile) {
        super(world, profile);
    }

    /**
     * @author Frontear
     * @reason Disable the effects of speed attributes which alter fov. This includes speed potions,
     * and is only active when {@link NoFOV} is active.
     */
    // todo: optimize
    @Inject(method = "getSpeed", at = @At("RETURN"), cancellable = true)
    private void getSpeed(@NonNull final CallbackInfoReturnable<Float> info) {
        val no_fov = InfinityLoader.getMod().getModules().get(NoFOV.class);

        if (no_fov.isActive()) {
            val entityAttributeInstance = this
                .getAttributeInstance(EntityAttributes.MOVEMENT_SPEED);
            val speed = (float) ((double) info.getReturnValueF() / (
                (entityAttributeInstance.getValue() / (double) this.abilities.getWalkSpeed() + 1.0D)
                    / 2.0D));

            info.setReturnValue(speed * (this.isSprinting() ? 1.15f : 1.0f));
        }
    }
}
