package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.internal.NotNull;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public abstract class MainMixin {
    @Inject(method = "main",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;run()V"))
    private static void main(@NotNull final String[] args, @NotNull final CallbackInfo info) {
        new InfinityLoader().init(args);
    }
}
