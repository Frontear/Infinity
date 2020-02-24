package com.github.frontear.infinity;

import com.github.frontear.efkolia.impl.mod.MinecraftMod;
import com.github.frontear.internal.NotNull;
import com.google.gson.JsonObject;
import lombok.NonNull;

public final class InfinityMod extends MinecraftMod {
    public InfinityMod(@NonNull final JsonObject object) {
        super(object.get("name").getAsString(), object.get("version").getAsString(),
            object.getAsJsonArray("authors").get(0).getAsString());

        logger.info("Hello Infinity!");
    }
}
