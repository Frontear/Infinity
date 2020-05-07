package com.github.frontear.infinity.commands.impl;

import com.github.frontear.infinity.InfinityMod;
import com.github.frontear.infinity.commands.*;
import lombok.*;

@CommandInfo(desc = "Identifies the ping value for a connected player", args = 1)
public final class Ping extends Command {
    public Ping(@NonNull final InfinityMod infinity) {
        super(infinity);
    }

    @Override
    public void process(final String[] args) {
        if (client.player != null) {
            val info = client.player.networkHandler.getPlayerListEntry(args[0]);

            if (info != null) {
                println(info.getProfile().getName() + "'s ping is " + info.getLatency());
            }
            else {
                println("Could not find player (case-sensitive)");
            }
        }
    }

    @Override
    public String getUsage() {
        return "ping <username>";
    }
}
