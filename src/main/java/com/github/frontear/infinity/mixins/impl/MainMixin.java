package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.internal.NotNull;
import lombok.NonNull;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
abstract class MainMixin {
    private static InfinityMod infinity;

    /**
     * @author Frontear
     * @reason This sets up the loading for Infinity. It is able to grab the main arguments given to
     * the application, so that Infinity may make use of them.
     */
    @Redirect(method = "main",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;run()V"))
    private static void main(@NotNull final MinecraftClient client, @NonNull final String[] args) {
        infinity = new InfinityLoader().init(args);
        client.updateWindowTitle();
        client.run();
    }

    @Inject(method = "main",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;stop()V"))
    private static void main(@NotNull final String[] args, @NotNull final CallbackInfo info) {
        infinity.getConfig().save();
    }
}
