package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.Infinity;
import java.util.concurrent.*;
import lombok.*;
import org.apache.commons.lang3.ArrayUtils;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.commands.Command;

public final class Execute extends Command {
    private static final Executor async = Executors.newSingleThreadExecutor();

    public Execute() {
        super("Executes a command after a specified amount of milliseconds", 2);
    }

    @Override
    public void process(@NonNull final String[] args) throws Exception {
        async.execute(() -> {
            Thread.sleep(Long.parseLong(args[0]));
            Infinity.inst().getCommands().getObjects()
                .filter(x -> x.getName().equalsIgnoreCase(args[1])).findFirst()
                .ifPresent(x -> {
                    val arguments = ArrayUtils.removeAll(args, 0, 1);
                    x.process(arguments);
                });
        });
    }
}
