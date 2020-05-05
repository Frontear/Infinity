package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.commands.*;
import lombok.NonNull;

@CommandInfo(desc = "Displays helpful information for commands")
public final class Help extends Command {
    public Help(final @NonNull InfinityMod infinity) {
        super(infinity);
    }

    // todo: detailed help per command
    @Override
    public void process(final String[] args) throws Exception {
        if (args.length > 0) {
            //noinspection OptionalGetWithoutIsPresent
            println(infinity.getCommands().stream().filter(x -> x.getPropertyName().equalsIgnoreCase(args[0]))
                .findFirst().get().getUsage());
        }
        else {
            infinity.getCommands().stream().forEach(x -> {
                println(x.getPropertyName() + ": " + x.getDesc());
            });
        }
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }
}
