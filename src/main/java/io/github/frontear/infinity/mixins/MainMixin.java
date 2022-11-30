package io.github.frontear.infinity.mixins;

import io.github.frontear.infinity.InfinityMod;
import net.minecraft.client.main.Main;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Main.class)
public class MainMixin {
    @Inject(at = @At("HEAD"), method = "main([Ljava/lang/String;)V")
    private static void main(String[] args, CallbackInfo info) {
        InfinityMod.LOGGER.info("Mixin in the main class");
    }
}
