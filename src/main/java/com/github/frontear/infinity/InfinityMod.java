package com.github.frontear.infinity;

import com.github.frontear.efkolia.impl.mod.MinecraftMod;
import com.github.frontear.infinity.commands.CommandContainer;
import com.github.frontear.infinity.modules.ModuleContainer;
import com.github.frontear.infinity.ux.TextRenderer;
import com.google.gson.JsonObject;
import lombok.*;
import net.minecraft.client.MinecraftClient;

public final class InfinityMod extends MinecraftMod {
    @Getter private final TextRenderer renderer;
    @Getter private final ModuleContainer modules;
    @Getter private final CommandContainer commands;

    public InfinityMod(@NonNull final JsonObject object) {
        super(object.get("name").getAsString(), object.get("version").getAsString(),
            object.getAsJsonArray("authors").get(0).getAsString());

        this.modules = new ModuleContainer(this);
        this.commands = new CommandContainer(this);
        this.renderer = new TextRenderer(this, MinecraftClient.getInstance());
    }
}
