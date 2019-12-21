package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.commands.Command;
import lombok.NonNull;

public final class Uptime extends Command {
    public Uptime() {
        super("Informs you of how long the client has been running");
    }

    @Override
    public void process(@NonNull final String[] args) throws Exception {
        sendMessage("Running for: ${com.github.frontear.framework.client.impl.Client.UPTIME}");
    }
}
