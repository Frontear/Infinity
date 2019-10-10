package org.frontear.infinity.commands.impl;

import lombok.NonNull;
import org.frontear.infinity.commands.Command;

public final class Uptime extends Command {
    public Uptime() {
        super("Informs you of how long the client has been running");
    }

    @Override
    public void process(@NonNull String[] args) throws Exception {
        sendMessage("Running for: ${org.frontear.framework.client.impl.Client.UPTIME}");
    }
}
