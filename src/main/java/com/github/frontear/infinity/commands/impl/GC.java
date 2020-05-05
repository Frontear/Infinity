package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.commands.Command;
import com.github.frontear.infinity.commands.CommandInfo;
import lombok.NonNull;
import lombok.val;

import java.lang.management.ManagementFactory;

@CommandInfo(desc = "Attempts to invoke the garbage collector")
public final class GC extends Command {
    private final boolean disabled = ManagementFactory.getRuntimeMXBean().getInputArguments().contains("-XX:+DisableExplicitGC");

    public GC(@NonNull InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public void process(String[] args) throws Exception {
        if (disabled) {
            println("Explicit GC has been disabled");
        } else {
            val last = getMemory();
            System.gc();
            val current = getMemory();
            println("Freed " + Math.abs(current - last) + " MB");
        }
    }

    private long getMemory() {
        val runtime = Runtime.getRuntime();

        return (runtime.totalMemory() - runtime.freeMemory()) / (1024L * 1024L);
    }
}
