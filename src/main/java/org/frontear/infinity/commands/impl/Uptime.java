package org.frontear.infinity.commands.impl;

import org.frontear.framework.client.impl.Client;
import org.frontear.infinity.commands.Command;

public class Uptime extends Command {
	public Uptime() {
		super("Informs you of how long the client has been running");
	}

	@Override protected void process(String[] args) throws Exception {
		sendMessage(String.format("Running for: %s", Client.UPTIME));
	}
}
