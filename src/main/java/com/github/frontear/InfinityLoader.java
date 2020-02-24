package com.github.frontear;

import com.github.frontear.efkolia.api.loader.ILoaderMod;
import com.github.frontear.infinity.InfinityMod;
import com.google.gson.JsonParser;
import java.io.*;
import java.util.zip.ZipFile;
import lombok.*;

public final class InfinityLoader implements ILoaderMod {
    @Override
    @SneakyThrows(IOException.class)
    public void init(final String... args) {
        val executable = new ZipFile(new File(
            InfinityLoader.class.getProtectionDomain().getCodeSource().getLocation().getPath()));
        val input_stream = executable.getInputStream(executable.getEntry("fabric.mod.json"));

        val inst = new InfinityMod(
            new JsonParser().parse(new InputStreamReader(input_stream)).getAsJsonObject());
    }
}
