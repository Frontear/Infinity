package org.frontear.infinity.commands.impl;

import lombok.NonNull;
import net.minecraft.client.network.NetworkPlayerInfo;
import org.frontear.infinity.commands.Command;

public final class Ping extends Command {
	public Ping() {
		super("Retrieves the latency value of a player. Name is case-sensitive", 1);
	}

	@Override public void process(@NonNull String[] args) throws Exception {
		NetworkPlayerInfo info = mc.getPlayer().sendQueue.getPlayerInfo(args[0]);
		if (info != null) {
			sendMessage(String.format("%s: %dms", info.getGameProfile().getName(), info.getResponseTime()));
		}
		else {
			sendMessage(String.format("Could not find %s (are they in-game)", args[0]));
		}
	}
}
