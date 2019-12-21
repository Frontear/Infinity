package com.github.frontear.mixins.impl;

import com.github.frontear.framework.config.IConfig;
import com.github.frontear.infinity.Infinity;
import net.minecraft.client.settings.GameSettings;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.*;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {
    /**
     * @author Frontear
     * @reason Allow {@link IConfig} to {@link IConfig#save()}
     */
    @Inject(method = "saveOptions",
        at = @At("TAIL"))
    private void saveOptions(final CallbackInfo info) {
        Infinity.inst().getConfig().save();
    }
}
