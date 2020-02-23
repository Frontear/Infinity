package com.github.frontear.infinity;

import com.github.frontear.infinity.mixins.IMinecraftClient;
import net.fabricmc.api.ModInitializer;
import net.minecraft.client.MinecraftClient;

public final class InfinityMod implements ModInitializer {
    @Override
    public void onInitialize() {
        ((IMinecraftClient) MinecraftClient.getInstance()).getLogger().info("Hello Fabric!");
    }
}
