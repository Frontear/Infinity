package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import lombok.*;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
abstract class MainMixin {
    /**
     * @author Frontear
     * @reason This sets up the loading for Infinity. It is able to grab the main arguments given to
     * the application, so that Infinity may make use of them.
     */
    @Redirect(method = "Lnet/minecraft/client/main/Main;main([Ljava/lang/String;Z)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;run()V"))
    private static void main(@NonNull final MinecraftClient client, @NonNull final String[] args, final boolean optimizeDataFixer) {
        val infinity = new InfinityLoader().init(args);
        client.updateWindowTitle();

        client.run();
    }

    /**
     * @author Frontear
     * @reason Saves the configuration file for Infinity on client stop. This is necessary for
     * future use of the client
     */
    @Inject(method = "Lnet/minecraft/client/main/Main;main([Ljava/lang/String;Z)V",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;stop()V"))
    private static void main(@NonNull final String[] args, final boolean optimizeDataFixer, @NonNull final CallbackInfo info) {
        InfinityLoader.getMod().getConfig().save();
    }
}
