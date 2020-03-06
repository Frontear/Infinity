package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.event.KeyEvent;
import net.minecraft.client.Keyboard;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.*;
import org.spongepowered.asm.mixin.*;
import org.spongepowered.asm.mixin.injection.*;

@Mixin(Keyboard.class)
abstract class KeyboardMixin {
    @Shadow
    public abstract void onKey(final long window, final int key, final int scancode,
        final int i, final int j);

    @Shadow
    protected abstract void onChar(final long window, final int i, final int j);

    @Redirect(method = "setup", at = @At(value = "INVOKE",
        target = "Lnet/minecraft/client/util/InputUtil;setKeyboardCallbacks(JLorg/lwjgl/glfw/GLFWKeyCallbackI;Lorg/lwjgl/glfw/GLFWCharModsCallbackI;)V"))
    private void setup(final long handle, final GLFWKeyCallbackI key_callback,
        final GLFWCharModsCallbackI char_callback) {
        InputUtil.setKeyboardCallbacks(handle, (window, key, scancode, action, mods) -> {
            InfinityMod.getInstance().getExecutor()
                .fire(new KeyEvent(key, action == GLFW.GLFW_PRESS));
            this.onKey(window, key, scancode, action, mods);
        }, this::onChar);
    }
}
