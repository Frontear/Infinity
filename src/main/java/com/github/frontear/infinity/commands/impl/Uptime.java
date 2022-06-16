package com.github.frontear.infinity.commands.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.commands.*;
import lombok.NonNull;

@Deprecated
@CommandInfo(desc = "Displays the uptime of the client")
public final class Uptime extends Command {
    public Uptime(@NonNull final InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public void process(final String[] args) throws Exception {
        println("Running for " + InfinityLoader.UPTIME);
    }

    @Override
    public String getUsage() {
        return "uptime";
    }
}
