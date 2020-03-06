package com.github.frontear.infinity;

import com.github.frontear.efkolia.impl.mod.MinecraftMod;
import com.github.frontear.infinity.modules.ModuleContainer;
import com.google.gson.JsonObject;
import lombok.*;

public final class InfinityMod extends MinecraftMod {
    @Getter private final ModuleContainer modules;

    public InfinityMod(@NonNull final JsonObject object) {
        super(object.get("name").getAsString(), object.get("version").getAsString(),
            object.getAsJsonArray("authors").get(0).getAsString());

        this.modules = new ModuleContainer(this);
    }
}
