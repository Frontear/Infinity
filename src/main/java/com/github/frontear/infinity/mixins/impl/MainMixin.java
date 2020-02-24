package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.internal.NotNull;
import lombok.NonNull;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public abstract class MainMixin {
    /**
     * @author Frontear
     * @reason This sets up the loading for Infinity. It is able to grab the main arguments given to
     * the application, so that Infinity may make use of them.
     */
    @Inject(method = "main",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;run()V"))
    private static void main(@NonNull final String[] args, @NonNull final CallbackInfo info) {
        new InfinityLoader().init(args);
    }
}
