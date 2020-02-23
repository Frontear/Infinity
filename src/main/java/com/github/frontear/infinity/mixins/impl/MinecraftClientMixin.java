package com.github.frontear.infinity.mixins.impl;

import com.github.frontear.infinity.mixins.IMinecraftClient;
import net.minecraft.client.MinecraftClient;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.*;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin implements IMinecraftClient {
    @Shadow @Final private static Logger LOGGER;

    @Override
    public Logger getLogger() {
        LOGGER.info("Hello Mixin!");
        return LOGGER;
    }
}
