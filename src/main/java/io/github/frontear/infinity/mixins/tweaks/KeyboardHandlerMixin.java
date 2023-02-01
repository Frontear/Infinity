package io.github.frontear.infinity.mixins.tweaks;

import io.github.frontear.infinity.tweaks.TweakManager;
import net.minecraft.client.KeyboardHandler;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
abstract class KeyboardHandlerMixin {
    @Inject(method = "keyPress", at = @At(value = "JUMP", opcode = Opcodes.LCMP, ordinal = 1))
    private void handleTweakKeyBinds(long windowPointer, int key, int scanCode, int action, int modifiers, CallbackInfo info) {
        TweakManager.handleKeyBinds(key, action);
    }
}
