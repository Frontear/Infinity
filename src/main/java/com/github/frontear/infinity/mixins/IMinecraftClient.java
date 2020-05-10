package com.github.frontear.infinity.mixins;

import lombok.NonNull;
import net.minecraft.client.MinecraftClient;

public interface IMinecraftClient {
    static IMinecraftClient from(@NonNull final MinecraftClient client) {
        return (IMinecraftClient) client;
    }

    void resetAttackCooldown();
    void doAttack();
}
