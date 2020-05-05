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
        if (args.length > 1) {
            println(infinity.getCommands().stream().filter(x -> x.getPropertyName().equals(args[0]))
                .findFirst().get().getUsage());
        }
        else {
            infinity.getCommands().stream().forEach(x -> {
                println(x.getPropertyName() + ": " + x.getDesc());
            });

            println("tip: Use " + getUsage() + " to read the usages for commands");
        }
    }

    @Override
    public String getUsage() {
        return "help [command]";
    }
}
