package org.frontear.mixins.impl;

import net.minecraft.client.settings.GameSettings;
import org.frontear.framework.config.IConfig;
import org.frontear.infinity.Infinity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(GameSettings.class)
public abstract class MixinGameSettings {
    /**
     * @author Frontear
     * @reason Allow {@link IConfig} to {@link IConfig#save()}
     */
    @Inject(method = "saveOptions",
        at = @At("TAIL"))
    private void saveOptions(CallbackInfo info) {
        Infinity.inst().getConfig().save();
    }
}
