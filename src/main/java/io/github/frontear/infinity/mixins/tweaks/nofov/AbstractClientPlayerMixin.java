package io.github.frontear.infinity.mixins.tweaks.nofov;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.nofov.NoFOV;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(AbstractClientPlayer.class)
abstract class AbstractClientPlayerMixin {
    private double cachedSprintingFOV = -1.0;

    @Redirect(method = "getFieldOfViewModifier", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/AbstractClientPlayer;getAttributeValue(Lnet/minecraft/world/entity/ai/attributes/Attribute;)D"))
    private double preventFOVChanges(AbstractClientPlayer instance, Attribute attribute) {
        if (cachedSprintingFOV == -1.0) {
            cachedSprintingFOV = instance.getAttributeBaseValue(attribute) * (1.0 + LivingEntity.SPEED_MODIFIER_SPRINTING.getAmount()); // TODO: any other modifiers to consider?
        }

        if (TweakManager.get(NoFOV.class).isEnabled()) {
            if (instance.isSprinting()) {
                return cachedSprintingFOV;
            }

            return instance.getAttributeBaseValue(attribute);
        }

        return instance.getAttributeValue(attribute);
    }
}
