package org.frontear.infinity.commands.impl;

import lombok.NonNull;
import lombok.val;
import org.frontear.infinity.commands.Command;

public final class Ping extends Command {
	public Ping() {
		super("Retrieves the latency value of a player. Name is case-sensitive", 1);
	}

	@Override public void process(@NonNull String[] args) throws Exception {
		val info = mc.thePlayer.sendQueue.getPlayerInfo(args[0]);
		if (info != null) {
			sendMessage("${info.getGameProfile().getName()}: ${info.getResponseTime()}ms");
		}
		else {
			sendMessage("Could not find ${args[0]} (are they in-game)");
		}
	}
}
