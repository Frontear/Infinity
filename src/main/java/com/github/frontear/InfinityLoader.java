package com.github.frontear;

import com.github.frontear.efkolia.api.loader.ILoaderMod;
import com.github.frontear.efkolia.utilities.file.JavaExecutable;
import com.github.frontear.efkolia.utilities.timing.Timer;
import com.github.frontear.infinity.InfinityMod;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import java.io.*;
import java.net.URISyntaxException;
import java.nio.file.*;
import java.util.Objects;
import lombok.*;
import net.fabricmc.loader.impl.launch.knot.KnotClient;

public final class InfinityLoader implements ILoaderMod<InfinityMod> {
    public static final Timer UPTIME = new Timer();

    private static InfinityMod mod;

    public static InfinityMod getMod() {
        return mod;
    }

    @Override
    public InfinityMod init(@NonNull final String... args) {
        UPTIME.reset();

        val executable = new JavaExecutable(InfinityLoader.class);
        mod = new InfinityMod(JsonParser.parseReader(executable.getResource("fabric.mod.json")).getAsJsonObject());

        mod.getConfig().load();

        return mod;
    }
}
