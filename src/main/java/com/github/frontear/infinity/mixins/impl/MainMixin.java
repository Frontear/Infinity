package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.event.state.LoadEvent;
import com.github.frontear.internal.NotNull;
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
    @Redirect(method = "main",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;run()V"))
    private static void main(@NotNull final MinecraftClient client, @NonNull final String[] args) {
        val infinity = new InfinityLoader().init(args);
        client.updateWindowTitle();

        infinity.getExecutor().fire(new LoadEvent());
        client.run();
    }

    /**
     * @author Frontear
     * @reason Saves the configuration file for Infinity on client stop. This is necessary for
     * future use of the client
     */
    @Inject(method = "main",
        at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;stop()V"))
    private static void main(@NotNull final String[] args, @NotNull final CallbackInfo info) {
        InfinityLoader.getMod().getConfig().save();
    }
}
