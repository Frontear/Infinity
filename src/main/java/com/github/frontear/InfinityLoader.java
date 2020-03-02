package com.github.frontear;

import com.github.frontear.efkolia.api.loader.ILoaderMod;
import com.github.frontear.efkolia.utilities.file.JavaExecutable;
import com.github.frontear.infinity.InfinityMod;
import com.google.gson.JsonParser;
import lombok.*;

public final class InfinityLoader implements ILoaderMod<Void> {
    @Override
    public Void init(@NonNull final String... args) {
        val executable = new JavaExecutable(InfinityLoader.class);

        val inst = new InfinityMod(
            new JsonParser().parse(executable.getResource("fabric.mod.json")).getAsJsonObject());

        return null;
    }
}
