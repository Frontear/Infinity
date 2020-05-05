package com.github.frontear.infinity.commands.impl;

import com.github.frontear.InfinityLoader;
import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.commands.Command;
import com.github.frontear.infinity.commands.CommandInfo;
import lombok.NonNull;

@CommandInfo(desc = "Displays the uptime of the client")
public final class Uptime extends Command {
    public Uptime(@NonNull InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public void process(String[] args) throws Exception {
        println("Running for " + InfinityLoader.UPTIME);
    }
}
