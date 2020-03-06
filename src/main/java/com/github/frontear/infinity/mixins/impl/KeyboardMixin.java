package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.infinity.event.input.KeyEvent;
import com.github.frontear.infinity.mixins.IMinecraftClient;
import lombok.NonNull;
import net.minecraft.client.Keyboard;
import org.lwjgl.glfw.GLFW;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Keyboard.class)
abstract class KeyboardMixin {
    /**
     * @author Frontear
     * @reason Inject the {@link KeyEvent} for key-related callbacks. This will do a dangerous LCMP
     * evaluation, which should ideally target the debugCrashStartTime > 0L condition.
     */
    @Inject(method = "onKey", at = @At(value = "JUMP", opcode = Opcodes.LCMP, ordinal = 1))
    private void onKey(final long window, final int key, final int scancode, final int action,
        final int mods, @NonNull final
    CallbackInfo info) {
        IMinecraftClient.getInfinity().getExecutor()
            .fire(new KeyEvent(key, action == GLFW.GLFW_PRESS));
    }
}
