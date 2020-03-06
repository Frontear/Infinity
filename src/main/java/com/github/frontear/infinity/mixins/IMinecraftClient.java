package com.github.frontear.infinity.mixins;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.internal.NotNull;
import net.minecraft.client.MinecraftClient;

public interface IMinecraftClient {
    static InfinityMod getInfinity() {
        return ((IMinecraftClient) MinecraftClient.getInstance()).getInfinityInstance();
    }

    InfinityMod getInfinityInstance();

    void setInfinityInstance(@NotNull final InfinityMod infinity);
}
