package com.github.frontear.infinity.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public abstract class MainMixin {
    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;run()V"))
    private static void main_run(String[] args, CallbackInfo info) {
        System.out.println("Infinity running on " + MinecraftClient.getInstance().getGameVersion());
    }

    @Inject(method = "main", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/MinecraftClient;stop()V"))
    private static void main_stop(String[] args, CallbackInfo info) {
        System.out.println("Infinity stopping");
    }
}
