package io.github.frontear.infinity.mixins.tweaks.freecam;

import io.github.frontear.infinity.tweaks.TweakManager;
import io.github.frontear.infinity.tweaks.impl.freecam.FreeCam;
import io.github.frontear.infinity.tweaks.impl.freecam.FreeCamPlayer;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.world.entity.player.Inventory;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(MouseHandler.class)
abstract class MouseHandlerMixin {
    @Shadow
    @Final
    private Minecraft minecraft;

    @Redirect(method = "turnPlayer", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/player/LocalPlayer;turn(DD)V"))
    private void turnCameraEntity(LocalPlayer instance, double yRot, double xRot) {
        if (TweakManager.get(FreeCam.class).isEnabled() && minecraft.getCameraEntity() != null) {
            minecraft.getCameraEntity().turn(yRot, xRot);
        }
        else {
            instance.turn(yRot, xRot);
        }
    }

    @Redirect(method = "onScroll", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/player/Inventory;swapPaint(D)V"))
    private void changeFakeInventory(Inventory instance, double direction) {
        if (TweakManager.get(FreeCam.class).isEnabled() && minecraft.getCameraEntity() instanceof FreeCamPlayer player) {
            player.getInventory().swapPaint(direction);
        }
        else {
            instance.swapPaint(direction);
        }
    }
}
