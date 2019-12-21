package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.Infinity;
import lombok.NonNull;
import org.frontear.infinity.Infinity;
import org.frontear.infinity.commands.Command;

public final class Help extends Command {
    public Help() {
        super("Displays useful information about the commands.");
    }

    @Override
    public void process(@NonNull final String[] args) throws Exception {
        Infinity.inst().getCommands().getObjects().forEach(x -> {
            sendMessage("${x.getName()}: ${x.getDescription()}");
        });
    }
}
