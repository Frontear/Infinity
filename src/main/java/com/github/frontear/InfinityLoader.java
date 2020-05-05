package com.github.frontear;

import com.github.frontear.efkolia.api.loader.ILoaderMod;
import com.github.frontear.efkolia.utilities.file.JavaExecutable;
import com.github.frontear.efkolia.utilities.timing.Timer;
import com.github.frontear.infinity.InfinityMod;
import com.google.gson.JsonParser;
import lombok.*;

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

        mod = new InfinityMod(
            new JsonParser().parse(executable.getResource("fabric.mod.json")).getAsJsonObject());
        mod.getConfig().load();

        return mod;
    }
}
